package msk.android.academy.Steps.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface StepsItemDAO {

    @Query("SELECT * FROM stepsItemDB")
    List<StepsItemDB> getAllMetrics();

//    @Query("SELECT * FROM stepsItemDB")
//    Observable<List<StepsItemDB>> getMetricsObservable();

    @Query("SELECT * FROM stepsItemDB")
    Flowable<List<StepsItemDB>> getMetricsFlowable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(StepsItemDB... stepsItemDBs);

    @Query("DELETE FROM stepsItemDB")
    void deleteAll();
}
