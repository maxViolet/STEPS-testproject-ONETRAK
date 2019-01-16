package msk.android.academy.javatemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import msk.android.academy.javatemplate.network.Api;
import msk.android.academy.javatemplate.network.StepsItemDTO;
import msk.android.academy.javatemplate.utils.DensityPixelMath;
import msk.android.academy.javatemplate.utils.Mapper;
import msk.android.academy.javatemplate.utils.Margins;

public class MetricsListActivity extends AppCompatActivity {

    private static int MARGIN = 30;
    private RecyclerView rv;
    private MetricsListAdapter adapter;
//    private Repository stepsRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        stepsRepository = new Repository(this);

        setupRecycler();
    }

    public void setupRecycler() {
        rv = (RecyclerView) findViewById(R.id.recycler);
        adapter = new MetricsListAdapter(this, new ArrayList<StepsItemDTO>());

        DensityPixelMath DPmath = new DensityPixelMath(this);
        Margins decoration = new Margins((int) DPmath.dpFromPx(MARGIN), 1);

        rv.setAdapter(adapter);
        rv.addItemDecoration(decoration);
    }

    private void loadMetricsToDb() {
        Log.d("room", "load metrics START");
        final Disposable searchDisposable = Api.getInstance()
                .stepsEndpoint()
                .getMetrics()
//                .map(response -> Mapper.DtoToDb(response.getStepsDTO()))
//                .flatMapCompletable(StepsItemDB -> stepsRepository.saveData(StepsItemDB))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        Log.d("room", "load metrics END");
        compositeDisposable.add(searchDisposable);
    }
}
