package msk.android.academy.Steps.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import io.reactivex.annotations.Nullable;

public class CustomProgressBar extends View {

    private int i;
    private int j;
    private int k;
    private float gap;

    private Paint paint;
    private Context context;

    public CustomProgressBar(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        i = 33;
        j = 33;
        k = 100 - i - j;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        j = i + j;
        gap = (getWidth() / 100);

        paint.setColor(Color.BLUE);
        canvas.drawCircle((float) (gap * 1.5), getHeight() / 2, getHeight() / 2, paint);
        canvas.drawRect(gap, 0, (getWidth() * i / 100), getHeight(), paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect((getWidth() * i / 100), 0, (getWidth() * i / 100) + gap, getHeight(), paint);
        paint.setColor(Color.RED);
        canvas.drawRect((getWidth() * i / 100) + gap, 0, (getWidth() * j / 100), getHeight(), paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect((getWidth() * j / 100), 0, (getWidth() * j / 100) + gap, getHeight(), paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect((getWidth() * j / 100) + gap, 0, (float) (getWidth() - gap * 1.5), getHeight(), paint);
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
