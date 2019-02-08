package msk.android.maximFialko.Steps.customProgressBar;

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

    //минимальная величина сегмента в %
    private static final int MINIMAL_SEGMENT_VALUE = 2;

    public List<Integer> percentValueList;
    private Paint paint;
    private Context context;

    public CustomProgressBar(Context context, int... values) {
        super(context);
        this.context = context;
        initValues(values);
    }

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

    // сеттеры для взаимодействия с прогресс баром через внешний ObjectAnimator
    public void setFirstSegment(int i) {
        this.percentValueList.set(0, i);
        invalidate();
    }

    public void setSecondSegment(int j) {
        this.percentValueList.set(1, j);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //координата Х последнего элемента
        float tempX = 0;
        //кривизна краев прогресс бара
        float curve = (float) 1.4;
        //величина разделителя сегментов (зависит от ширины parent view)
        float gapSize = (getWidth() / 110);
        //координата Х для отрисовки круга/арки (зависит от ширины parent view)
        float circleCenterX = (getWidth() / 100);
        float h = getHeight();
        float w = getWidth();
        //радиус круга(влияет на закруления краев)
        float r = circleCenterX * curve;

        for (int k = 0; k < percentValueList.size(); k++) {
            if (k == 0) {
                //first segment
                paint.setColor(ContextCompat.getColor(context, R.color.color_walk));
                //draw arc
                drawArc(canvas, 0, 2 * r, h, 90);
                tempX = r;
                //draw rectangle
                drawRectangle(canvas, tempX, tempX + (w * percentValueList.get(k) / 100));
                tempX = tempX + (w * percentValueList.get(k) / 100);

            } else if (k == percentValueList.size() - 1) {
                //last segment
                paint.setColor(ContextCompat.getColor(context, R.color.color_run));
                //draw rectangle
                drawRectangle(canvas, tempX, w - r);
                tempX = w - r;
                //draw arc
                drawArc(canvas, tempX - r, w, h, 270);
            } else {
                //mid segments
                //draw gap
                drawGap(canvas, tempX, gapSize);
                tempX += gapSize;
                paint.setColor(ContextCompat.getColor(context, R.color.color_aerobic));
                //draw rectangle
                drawRectangle(canvas, tempX, tempX + (w * percentValueList.get(k) / 100) + gapSize);
                tempX += (w * percentValueList.get(k) / 100) + gapSize;
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

    private void initValues(int... v) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentValueList = new ArrayList<>();

        //преобразовать список в пропорции относительно суммы
        int[] kk = getPercentValues(v);
        //список конечных значений для отрисовки сегментов
        for (int i = 0; i < (getPercentValues(v)).length; i++) percentValueList.add(kk[i]);
    }

    //преобразует аргументы, переданные в конструкторе, в % от общей суммы аргументов
    private int[] getPercentValues(int... val) {
        int sum = 0;
        //get sum of all elements
        for (int iterator : val) {
            sum += iterator;
        }

        int[] percentValues = new int[val.length];
        //divide each element by summ for % values
        for (int v = 0; v < val.length; v++) {
            //проверка на MINIMAL_SEGMENT_VALUE
            if ((val[v] * 100 / sum) != 0 && (val[v] * 100 / sum) < MINIMAL_SEGMENT_VALUE) {
                percentValues[v] = MINIMAL_SEGMENT_VALUE;
            } else {
                percentValues[v] = val[v] * 100 / sum;
            }
        }
        return percentValues;
    }
}
