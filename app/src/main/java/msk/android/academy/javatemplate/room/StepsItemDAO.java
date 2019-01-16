package msk.android.academy.javatemplate.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface StepsItemDAO {

    @Query("SELECT * FROM stepsItemDB")
    List<StepsItemDB> getAll();

//    @Query("SELECT * FROM stepsItemDB")
//    Observable<List<StepsItemDB>> getAllObservable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(StepsItemDB... stepsItemDBs);

    @Query("DELETE FROM stepsItemDB")
    void deleteAll();
}
