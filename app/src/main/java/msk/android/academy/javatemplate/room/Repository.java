package msk.android.academy.javatemplate.room;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class Repository {
    public final Context mContext;

    public Repository(Context mContext) {
        this.mContext = mContext;
    }

    public Single<List<StepsItemDB>> getMetrics() {
        return Single.fromCallable(() -> {
            AppDatabase db = AppDatabase.getAppDatabase(mContext);

            return db.stepsItemDAO().getAll();
        });
    }

    public Completable saveData(final List<StepsItemDB> stepsItemDBs) {
        Log.d("room", "SAVE DATA to DB");
        return Completable.fromCallable((Callable<Void>) () -> {
            AppDatabase db = AppDatabase.getAppDatabase(mContext);
            db.stepsItemDAO().deleteAll();
            StepsItemDB[] items = stepsItemDBs.toArray(new StepsItemDB[stepsItemDBs.size()]);
            db.stepsItemDAO().insertAll(items);
            return null;
        });
    }

//    public Observable<List<StepsItemDB>> getDataObservable() {
//        AppDatabase db = AppDatabase.getAppDatabase(mContext);
//        return db.stepsItemDAO().getAllObservable();
//    }

}
