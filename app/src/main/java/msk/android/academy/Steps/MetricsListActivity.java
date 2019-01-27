package msk.android.academy.Steps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import msk.android.academy.Steps.network.Api;
import msk.android.academy.Steps.room.Repository;
import msk.android.academy.Steps.utils.DensityPixelMath;
import msk.android.academy.Steps.utils.Mapper;
import msk.android.academy.Steps.utils.Margins;
import msk.android.academy.Steps.utils.StepsItem;

public class MetricsListActivity extends AppCompatActivity {

    private static int MARGIN_RECYCLERVIEW = 80;
    private RecyclerView rv;
    private MetricsListAdapter adapter;
    private Repository stepsRepository;
    private Toolbar toolbar;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lifecycle", "onCreate");
        setContentView(R.layout.activity_main);
        stepsRepository = new Repository(this);

//        toolbar=findViewById(R.id.);

        setupRecycler();
        loadMetricsToDb();
        showMetrics();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop");
        compositeDisposable.clear();
    }

    public void setupRecycler() {
        rv = (RecyclerView) findViewById(R.id.recycler);
        adapter = new MetricsListAdapter(this, new ArrayList<StepsItem>());

        DensityPixelMath DPmath = new DensityPixelMath(this);
        Margins decoration = new Margins((int) DPmath.dpFromPx(MARGIN_RECYCLERVIEW), 1);

        rv.setAdapter(adapter);
        rv.addItemDecoration(decoration);

    }

    private void loadMetricsToDb() {
        Log.d("database", "load metrics START");
        final Disposable loadDisposable = Api.getInstance()
                .stepsEndpoint()
                .getMetrics()
                .map(list -> Mapper.DtoToDb(list))
                .flatMapCompletable(StepsItemDB -> stepsRepository.saveData(StepsItemDB))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        Log.d("database", "load metrics END");
        compositeDisposable.add(loadDisposable);
    }

    private void showMetrics() {
        Log.d("rv", "showMetrics");
        Disposable showDisposable = stepsRepository.getFlowableMetrics()
                .map(items -> Mapper.DbToItem(items))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> replaceItems(items),
                        throwable -> Log.d("rv", throwable.toString()));
        compositeDisposable.add(showDisposable);
    }

    private void replaceItems(List<StepsItem> stepsItems) {
        if (adapter != null) adapter.replaceItems(stepsItems);
    }
}
