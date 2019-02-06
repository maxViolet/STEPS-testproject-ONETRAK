package msk.android.maximFialko.Steps.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StepsItemDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singleton;

    private static final String DATABASE_NAME = "stepsItemsDatabase.db";

    public abstract StepsItemDAO stepsItemDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (singleton == null) {
            synchronized (AppDatabase.class) {
                if (singleton == null) {
                    singleton = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return singleton;
    }
}