package msk.android.academy.Steps.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.annotations.Nullable;

public class CustomProgressBar extends View {

    private static int MIMINAL_VALUE = 2;
    private int i;
    private int j;
    private int k;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int i, int j) {
        super(context, attrs, i, j);
        this.i = i;
        this.j = j;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        j = i + j;
        float gap = (getWidth() / 170);
        double curve = 1.4;

        //WALK //COLOR1
        paint.setColor(Color.BLUE);
        //CIRCLE //COLOR1
        canvas.drawCircle((float) (gap * curve), getHeight() / 2, getHeight() / 2, paint);
        canvas.drawRect(gap, 0, (getWidth() * i / 100), getHeight(), paint);

        //DIVIDER //WHITE
        paint.setColor(Color.WHITE);
        canvas.drawRect((getWidth() * i / 100), 0, (getWidth() * i / 100) + gap, getHeight(), paint);

        //AEROBIC //COLOR2
        paint.setColor(Color.RED);
        canvas.drawRect((getWidth() * i / 100) + gap, 0, (getWidth() * j / 100), getHeight(), paint);

        //DIVIDER //WHITE
        paint.setColor(Color.WHITE);
        canvas.drawRect((getWidth() * j / 100), 0, (getWidth() * j / 100) + gap, getHeight(), paint);

        //RUN //COLOR3
        paint.setColor(Color.BLACK);
        canvas.drawRect((getWidth() * j / 100) + gap, 0, (float) (getWidth() - gap * curve), getHeight(), paint);
        //CIRCLE //COLOR3
        canvas.drawCircle((float) (getWidth() - gap * 1.5), getHeight() / 2, getHeight() / 2, paint);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

}
