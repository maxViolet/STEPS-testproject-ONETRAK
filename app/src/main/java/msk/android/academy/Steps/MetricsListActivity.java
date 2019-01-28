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
import msk.android.academy.Steps.network.ApiHolder;
import msk.android.academy.Steps.room.Repository;
import msk.android.academy.Steps.room.StepsItemDB;
import msk.android.academy.Steps.utils.DensityPixelMath;
import msk.android.academy.Steps.utils.StepsListMapper;
import msk.android.academy.Steps.utils.RecyclerItemDecorator;
import msk.android.academy.Steps.utils.StepsItem;

public class MetricsListActivity extends AppCompatActivity {

    private static int MARGIN_RECYCLERVIEW = 80;
    private RecyclerView rv;
    private MetricsListAdapter adapter;
    private Repository stepsRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepsRepository = new Repository(this);

        setupRecycler();
        loadMetricsToDb();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    public void setupRecycler() {
        rv = findViewById(R.id.recycler);
        adapter = new MetricsListAdapter(this, new ArrayList<StepsItem>());

        DensityPixelMath DPmath = new DensityPixelMath(this);
        RecyclerItemDecorator decoration = new RecyclerItemDecorator((int) DPmath.dpFromPx(MARGIN_RECYCLERVIEW), 1);

        rv.setAdapter(adapter);
        rv.addItemDecoration(decoration);

    }

    private void loadMetricsToDb() {
//        Log.d("database", "load metrics START");
        final Disposable loadDisposable = ApiHolder.getInstance()
                .stepsApi()
                .getMetrics()
                .map(list -> {
                    List<StepsItemDB> steps = StepsListMapper.DtoToDb(list);
                    stepsRepository.saveData(steps);
                    return steps;
                })
                .map(stepsItems -> StepsListMapper.DbToItem(stepsItems))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> replaceItems(items),
                        throwable -> Log.d("rv", throwable.toString()));
//        Log.d("database", "load metrics END");
        compositeDisposable.add(loadDisposable);
    }

    private void replaceItems(List<StepsItem> stepsItems) {
        if (adapter != null) adapter.replaceItems(stepsItems);
    }
}
