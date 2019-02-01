package msk.android.academy.Steps.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ComplexColorCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import io.reactivex.annotations.Nullable;
import msk.android.academy.Steps.R;

public class CustomProgressBar extends View {

    private static int SEGMENT_NUMBER = 3;
    private int i;
    private int j;
    private int k;
    private List<Integer> valueList;

    private static final int COLOR1 = 8510197;
    private static final int COLOR2 = 36281;
    private static final int COLOR3 = 1837667;
    private ArrayList<Integer> colorList = new ArrayList<>();
    private ArrayDeque<Integer> colorQueue;

    private Paint paint;
    private Context context;

    public CustomProgressBar(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int i, int j) {
        super(context, attrs, i, j);
        this.context = context;
        this.i = i;
        this.j = j;
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

    //TODO масштабируемая версия
    @Override
    protected void onDraw(Canvas canvas) {
        initValues();
        float tempX = 0;
        //кривизна краев прогресс бара (визуально отображается только для функции drawCircle)
        float curve = (float) 1.4;
        //величина разделителя сегментов
        float gap = (getWidth() / 110);
        //координата Х для отрисовки круга/арки
        float circleCenterX = (getWidth() / 100);
        float h = getHeight();
        float w = getWidth();
        //радиус круга
        float r = circleCenterX * curve;
        Integer currentColor;

        for (k = 0; k < SEGMENT_NUMBER; k++) {
            if (k == 0) {
                //первый сегмент
                currentColor = getColor();
                paint.setColor(ContextCompat.getColor(context, R.color.color_walk));
                //отрисовка circle
                drawArc(canvas,0,0, 2*r, h,90,180 );
                tempX = (float) (circleCenterX * curve);

                //отрисовка прямоугольника того же цвета
                canvas.drawRect(tempX, 0, tempX + (w * valueList.get(k) / 100), h, paint);
                tempX = tempX + (w * valueList.get(k) / 100);
                //gap
                paint.setColor(ContextCompat.getColor(context, R.color.white));
                canvas.drawRect(tempX, 0, tempX + gap, h / 2, paint);
                tempX += gap;

            } else if (k == SEGMENT_NUMBER - 1) {
                //последний сегмент
                currentColor = getColor();
                paint.setColor(ContextCompat.getColor(context, R.color.color_run));
                //отрисовка прямоугольника
                canvas.drawRect(tempX, 0, (float) (w - (circleCenterX * curve)), h, paint);
                tempX = (float) (w - r);
                //отрисовка circle того же цвета
                drawArc(canvas,tempX-r,0, w, h,270,180 );
            } else {
                //промежуточный сегмент
                currentColor = getColor();
                paint.setColor(ContextCompat.getColor(context, R.color.color_aerobic));
                //отрисовка прямоугольника
                canvas.drawRect(tempX, 0, tempX + (w * valueList.get(k) / 100), h, paint);
                tempX += (w * valueList.get(k) / 100);
                //gap
                paint.setColor(ContextCompat.getColor(context, R.color.white));
                canvas.drawRect(tempX, 0, tempX + gap, h / 2, paint);
                tempX += gap;
            }

        }
    }

    private void drawCircle(Canvas canvas, float centerX, float centerY, float radius) {
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    private void drawArc(Canvas canvas, float left, float top, float right, float h, int startAngle, int sweep) {
        canvas.drawArc(left, top, right, h, startAngle, sweep, true, paint);
    }

    private void drawRectangle() {
        //TODO
    }

    private void drawGap() {
        //TODO
    }

    private Integer getColor() {

        //возвращаем цвет и добавляем его обратно в очередь
        Integer temp = colorQueue.poll();
        colorQueue.offer(temp);
        return temp;
    }

    private void initValues() {
        colorQueue = new ArrayDeque<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valueList = new ArrayList<>();

        valueList.add(i);
        valueList.add(j);
        valueList.add(k);

//        if (colorQueue == null) {
        //заполнить список
        for (int h = 1; h <= SEGMENT_NUMBER; h++) {
            colorList.add(COLOR1);
            colorList.add(COLOR2);
            colorList.add(COLOR3);
//            }
            //заполнить очередь
            for (int t = 0; t < colorList.size(); t++) {
                if (colorQueue != null) {
                    colorQueue.offer(colorList.get(t));
                }
            }
        }
    }

}
