package msk.android.maximFialko.Steps.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;
import msk.android.maximFialko.Steps.R;

public class CustomProgressBar extends View {

    private static int SEGMENT_NUMBER = 3;
    private int i;
    private int j;
    private int k;
    private List<Integer> valueList;

    private static final int COLOR1 = 8510197;
    private static final int COLOR2 = 36281;
    private static final int COLOR3 = 1837667;
    private ArrayList<Integer> colorList;
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

    public void setI(int i) {
        this.i = i;
        invalidate();
    }

    public void setJ(int j) {
        this.j = j;
        invalidate();
    }

    public void setK(int k) {
        this.k = k;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initValues();
        //координата Х последнего элемента
        float tempX = 0;
        //кривизна краев прогресс бара
        float curve = (float) 1.4;
        //величина разделителя сегментов
        float gapSize = (getWidth() / 110);
        //координата Х для отрисовки круга/арки
        float circleCenterX = (getWidth() / 100);
        float h = getHeight();
        float w = getWidth();
        //радиус круга(влияет на закруления краев)
        float r = circleCenterX * curve;

        for (k = 0; k < SEGMENT_NUMBER; k++) {
            if (k == 0) {
                //первый сегмент
                paint.setColor(ContextCompat.getColor(context, R.color.color_walk));
                //отрисовка arc
                drawArc(canvas, 0, 2 * r, h, 90);
                tempX = r;
                //отрисовка прямоугольника того же цвета
                drawRectangle(canvas, tempX, tempX + (w * valueList.get(k) / 100));
                tempX = tempX + (w * valueList.get(k) / 100);

            } else if (k == SEGMENT_NUMBER - 1) {
                //последний сегмент
                paint.setColor(ContextCompat.getColor(context, R.color.color_run));
                //отрисовка прямоугольника
                drawRectangle(canvas, tempX, w - r);
                tempX = w - r;
                //отрисовка arc того же цвета
                drawArc(canvas, tempX - r, w, h, 270);
            } else {
                //промежуточные сегменты
                //gap
                drawGap(canvas, tempX, gapSize);
                tempX += gapSize;
                paint.setColor(ContextCompat.getColor(context, R.color.color_aerobic));
                //отрисовка прямоугольника
                drawRectangle(canvas, tempX, tempX + (w * valueList.get(k) / 100) + gapSize);
                tempX += (w * valueList.get(k) / 100) + gapSize;
                //draw gap
                paint.setColor(ContextCompat.getColor(context, R.color.white));
                drawGap(canvas, tempX, gapSize);
                tempX += gapSize;
            }
        }
    }

    private void drawRectangle(Canvas canvas, float start, float end) {
        canvas.drawRect(start, 0, end, getHeight(), paint);
    }

    private void drawArc(Canvas canvas, float start, float end, float h, int startAngle) {
        canvas.drawArc(start, 0, end, h, startAngle, 180, true, paint);
    }

    private void drawGap(Canvas canvas, float start, float gap) {
        paint.setColor(ContextCompat.getColor(context, R.color.white));
        canvas.drawRect(start, 0, start + gap, getHeight(), paint);
    }

    private Integer getColor() {
        //возвращаем цвет и добавляем его обратно в очередь
        Integer temp = colorQueue.poll();
        colorQueue.offer(temp);
        return temp;
    }

    private void initValues() {
        colorList = new ArrayList<>();
        colorQueue = new ArrayDeque<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valueList = new ArrayList<>();

        //список значений для сегментов
        valueList.add(i);
        valueList.add(j);
        valueList.add(k);

        //заполнить список цветов сегментов
        for (int h = 1; h <= SEGMENT_NUMBER; h++) {
            colorList.add(COLOR1);
            colorList.add(COLOR2);
            colorList.add(COLOR3);
        }
        //заполнить очередь с цветами из списка (если кол-во цветом меньше, чем кол-во сегментов, повторит ту же гамму цветов
        for (int t = 0; t < colorList.size(); t++) {
            if (colorQueue != null) {
                colorQueue.offer(colorList.get(t));
            }
        }
    }
}
