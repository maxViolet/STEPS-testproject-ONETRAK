package msk.android.academy.Steps;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import msk.android.academy.Steps.network.ApiHolder;
import msk.android.academy.Steps.room.Repository;
import msk.android.academy.Steps.room.StepsItemDB;
import msk.android.academy.Steps.utils.CustomProgressBar;
import msk.android.academy.Steps.utils.DensityPixelMath;
import msk.android.academy.Steps.utils.StepsListMapper;
import msk.android.academy.Steps.utils.RecyclerItemDecorator;
import msk.android.academy.Steps.utils.*;

public class MetricsListActivity extends AppCompatActivity {

    private static int MARGIN_RECYCLERVIEW = 80;
    private RecyclerView recyclerView;
    private MetricsListAdapter adapter;
    private Repository stepsRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
//    private LinearLayout testView;
//    private CustomProgressBar customProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepsRepository = new Repository(this);

//        customProgressBar = new CustomProgressBar(this);
//        customProgressBar.setI(33);
//        customProgressBar.setJ(22);

        setupRecycler();
        loadAndShowMetrics();

//        testView = findViewById(R.id.test_view);
//        testView.addView(customProgressBar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    public void setupRecycler() {
        recyclerView = findViewById(R.id.recycler);

        adapter = new MetricsListAdapter(this, new ArrayList<>());

        DensityPixelMath DPmath = new DensityPixelMath(this);
        RecyclerItemDecorator decoration = new RecyclerItemDecorator((int) DPmath.dpFromPx(MARGIN_RECYCLERVIEW), 1);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(decoration);
    }

    private void loadAndShowMetrics() {
//        Log.d("loadAndShowMetrics", "load metrics START");
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
                .subscribe(items -> showMetrics(items),
                        throwable -> Log.d("loadAndShowMetrics", throwable.toString())
                );
//        Log.d("loadAndShowMetrics", "load metrics END");
        compositeDisposable.add(loadDisposable);
    }

    private void showMetrics(List<StepsItem> stepsItems) {
        if (adapter != null) adapter.replaceItems(stepsItems);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
}
