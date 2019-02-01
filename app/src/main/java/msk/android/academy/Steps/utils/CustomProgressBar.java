package msk.android.academy.Steps.utils;

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
        //координата Х последнего элемента
        float tempX = 0;
        //кривизна краев прогресс бара (визуально отображается только для функции drawCircle)
        float curve = (float) 1.4;
        //величина разделителя сегментов
        float gapSize = (getWidth() / 110);
        //координата Х для отрисовки круга/арки
        float circleCenterX = (getWidth() / 100);
        float h = getHeight();
        float w = getWidth();
        //радиус круга (закругдения краев)
        float r = circleCenterX * curve;
        Integer currentColor;

        for (k = 0; k < SEGMENT_NUMBER; k++) {
            if (k == 0) {
                //первый сегмент
                currentColor = getColor();
                paint.setColor(ContextCompat.getColor(context, R.color.color_walk));
                //отрисовка arc
                drawArc(canvas, 0, 0, 2 * r, h, 90);
                tempX = r;
                //отрисовка прямоугольника того же цвета
                drawRectangle(canvas, tempX, tempX + (w * valueList.get(k) / 100), h);
                tempX = tempX + (w * valueList.get(k) / 100);
                //gap
                drawGap(canvas, tempX, gapSize, h);
                tempX += gapSize;

            } else if (k == SEGMENT_NUMBER - 1) {
                //последний сегмент
                currentColor = getColor();
                paint.setColor(ContextCompat.getColor(context, R.color.color_run));
                //отрисовка прямоугольника
                drawRectangle(canvas, tempX, w - r, h);
                tempX = w - r;
                //отрисовка arc того же цвета
                drawArc(canvas, tempX - r, 0, w, h, 270);
            } else {
                //промежуточный сегмент
                currentColor = getColor();
                paint.setColor(ContextCompat.getColor(context, R.color.color_aerobic));
                //отрисовка прямоугольника
                drawRectangle(canvas, tempX, tempX + (w * valueList.get(k) / 100), h);
                tempX += (w * valueList.get(k) / 100);
                //gap
                paint.setColor(ContextCompat.getColor(context, R.color.white));
                drawGap(canvas, tempX, gapSize, h);
                tempX += gapSize;
            }
        }
    }

    private void drawCircle(Canvas canvas, float centerX, float centerY, float radius) {
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    private void drawArc(Canvas canvas, float left, float top, float right, float h, int startAngle) {
        canvas.drawArc(left, top, right, h, startAngle, 180, true, paint);
    }

    private void drawRectangle(Canvas canvas, float start, float end, float h) {
        canvas.drawRect(start, 0, end, h, paint);
    }

    private void drawGap(Canvas canvas, float start, float gap, float h) {
        paint.setColor(ContextCompat.getColor(context, R.color.white));
        canvas.drawRect(start, 0, start + gap, h, paint);
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

        //заполнить список
        for (int h = 1; h <= SEGMENT_NUMBER; h++) {
            colorList.add(COLOR1);
            colorList.add(COLOR2);
            colorList.add(COLOR3);
        }
        //заполнить очередь
        for (int t = 0; t < colorList.size(); t++) {
            if (colorQueue != null) {
                colorQueue.offer(colorList.get(t));

            }
        }
    }

}
