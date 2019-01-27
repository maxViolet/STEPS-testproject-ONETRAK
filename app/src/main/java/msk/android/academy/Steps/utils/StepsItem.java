package msk.android.academy.Steps.utils;

public class StepsItem {

    private final int id;
    private final String date;
    private final int goal;
    private final int walk;
    private final int aerobic;
    private final int run;

    public StepsItem(int id, String date, int goal, int walk, int aerobic, int run) {
        this.id = id;
        this.date = date;
        this.goal = goal;
        this.walk = walk;
        this.aerobic = aerobic;
        this.run = run;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
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
}
