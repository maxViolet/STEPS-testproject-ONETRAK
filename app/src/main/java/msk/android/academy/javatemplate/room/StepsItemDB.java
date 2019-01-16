package msk.android.academy.javatemplate.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stepsItemDB")
public class StepsItemDB {

    public StepsItemDB(int date, int walk, int aerobic, int run) {
        this.date = date;
        this.walk = walk;
        this.aerobic = aerobic;
        this.run = run;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "date")
    private int date;
    @ColumnInfo(name = "goal")
    private int goal;
    @ColumnInfo(name = "walk")
    private int walk;
    @ColumnInfo(name = "aerobic")
    private int aerobic;
    @ColumnInfo(name = "run")
    private int run;

    public int getId() {
        return id;
    }
    public int getDate() {
        return date;
    }
    public int getGoal() {
        return goal;
    }
    public int getWalk() {
        return walk;
    }
    public int getAerobic() {
        return aerobic;
    }
    public int getRun() {
        return run;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDate(int date) {
        this.date = date;
    }
    public void setGoal(int goal) {
        this.goal = goal;
    }
    public void setWalk(int walk) {
        this.walk = walk;
    }
    public void setAerobic(int aerobic) {
        this.aerobic = aerobic;
    }
    public void setRun(int run) {
        this.run = run;
    }

}
