package msk.android.maximFialko.Steps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import msk.android.maximFialko.Steps.network.ApiHolder;
import msk.android.maximFialko.Steps.room.Repository;
import msk.android.maximFialko.Steps.room.StepsItemDB;
import msk.android.maximFialko.Steps.utils.DensityPixelMath;
import msk.android.maximFialko.Steps.utils.StepsListMapper;
import msk.android.maximFialko.Steps.utils.RecyclerItemDecorator;
import msk.android.maximFialko.Steps.utils.*;

public class MetricsListActivity extends AppCompatActivity {

    private static int SPACE_BETWEEN_ITEMS = 80;
    private RecyclerView recyclerView;
    private MetricsListAdapter adapter;
    private Repository stepsRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepsRepository = new Repository(this);

        setupRecycler();
        loadAndShowMetrics();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
        stepsRepository = null;
    }

    public void setupRecycler() {
        recyclerView = findViewById(R.id.recycler);

        adapter = new MetricsListAdapter(this, new ArrayList<>());

        DensityPixelMath DPmath = new DensityPixelMath(this);
        RecyclerItemDecorator decoration = new RecyclerItemDecorator((int) DPmath.dpFromPx(SPACE_BETWEEN_ITEMS), 1);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(decoration);
    }

    private void loadAndShowMetrics() {
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
