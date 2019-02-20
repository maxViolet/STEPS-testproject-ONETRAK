package msk.android.maximFialko.Steps.customProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class CustomProgressBar extends View {
    //draw round edges of view
    private boolean SHOW_ROUND_EDGES = true;
    //show GAPS
    private boolean SHOW_GAPS = true;
    //minimal section value to be shown in % (meaning: values between >0% and <2% will be shown as 2% section)
    private static final int MINIMAL_SEGMENT_VALUE = 2;

    //COLORs for segments
    private String COLOR1 = "#81DAF5";
    private String COLOR2 = "#008db9";
    private String COLOR3 = "#1c0a63";

    //fill the list with colors in the order you want them to see
    private final List<String> colorList = Arrays.asList(COLOR3, COLOR2, COLOR1);

    //GAPs color
    private String GAP_COLOR = "#ffffff";

    public List<Integer> percentValueList;
    private Paint paint;
    private ArrayDeque<String> colorQueue;

    public CustomProgressBar(@NonNull Context context, @NonNull int... values) {
        super(context);
        initValues(values);
    }

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // setters to animate custom view via external ObjectAnimator
    public void setFirstSegment(int i) {
        this.percentValueList.set(0, i);
        //redraw custom view on every argument change via external ObjectAnimator
        invalidate();
    }

    public void setSecondSegment(int j) {
        this.percentValueList.set(1, j);
        //redraw custom view on every argument change via external ObjectAnimator
        invalidate();
    }

    public void setThirdSegment(int k) {
        this.percentValueList.set(2, k);
        //redraw custom view on every argument change via external ObjectAnimator
        invalidate();
    }

    private void initValues(int... v) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentValueList = new ArrayList<>();
        colorQueue = new ArrayDeque<>();

        //fill colorQueue with predefined colors
        for (String i : colorList) colorQueue.push(i);

        //process the list to get proportions (each argument / sum)
        int[] k = getPercentValues(v);
        //final list for segment drawing
        for (int i = 0; i < (getPercentValues(v)).length; i++) percentValueList.add(k[i]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //X coordinate of the last element
        float tempX = 0;
        float h = getHeight();
        float w = getWidth();
        //size of gaps (depends from container view width and denominator)
        float gapSize = (getWidth() / 110);
        //X coordinate of rounded edges, stands for radius of ark (depends from container view width and denominator)
        float circleCenterX = (getWidth() / 100);
        //curve of the round edges of the custom view
        float curve = (float) 1.4;
        //arc radius
        float r = circleCenterX * curve;

        for (int k = 0; k < percentValueList.size(); k++) {
            if (k == 0) {
                //FIRST segment
                paint.setColor(Color.parseColor(getColor(colorQueue)));
                //draw arc
                if (SHOW_ROUND_EDGES) {
                    drawArc(canvas, 0, 2 * r, h, 90);
                    tempX = r;
                }
                //draw rectangle
                drawRectangle(canvas, tempX, tempX + (w * percentValueList.get(k) / 100));
                tempX = tempX + (w * percentValueList.get(k) / 100);

            } else if (k == percentValueList.size() - 1) {
                //LAST segment
                //draw gap
                if (SHOW_GAPS) {
                    drawGap(canvas, tempX, gapSize);
                    tempX += gapSize;
                }
                //draw rectangle
                paint.setColor(Color.parseColor(getColor(colorQueue)));
                drawRectangle(canvas, tempX, w - r);
                tempX = w - r;
                //draw arc
                if (SHOW_ROUND_EDGES) {
                    drawArc(canvas, tempX - r, w, h, 270);
                }
            } else {
                //MID segments
                //draw gap
                if (SHOW_GAPS) {
                    drawGap(canvas, tempX, gapSize);
                    tempX += gapSize;
                }
                //draw rectangle
                paint.setColor(Color.parseColor(getColor(colorQueue)));
                drawRectangle(canvas, tempX, tempX + (w * percentValueList.get(k) / 100) + gapSize);
                tempX += (w * percentValueList.get(k) / 100) + gapSize;
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
        paint.setColor(Color.parseColor(GAP_COLOR));
        canvas.drawRect(start, 0, start + gap, getHeight(), paint);
    }

    private int[] getPercentValues(int... val) {
        int sum = 0;
        //get sum of all arguments
        for (int iterator : val) {
            sum += iterator;
        }

        int[] percentValues = new int[val.length];
        //divide each element by summ to get % values
        for (int v = 0; v < val.length; v++) {
            //check for MINIMAL_SEGMENT_VALUE
            if ((val[v] * 100 / sum) != 0 && (val[v] * 100 / sum) < MINIMAL_SEGMENT_VALUE) {
                percentValues[v] = MINIMAL_SEGMENT_VALUE;
            } else {
                percentValues[v] = val[v] * 100 / sum;
            }
        }
        return percentValues;
    }

    private String getColor(ArrayDeque<String> arr) {
        //return color and push it's value to array's tail
        String temp = arr.pop();
        arr.offer(temp);
        return temp;
    }
}
