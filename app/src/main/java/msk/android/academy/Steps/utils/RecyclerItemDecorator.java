package msk.android.academy.Steps.utils;

import android.graphics.Rect;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerItemDecorator extends RecyclerView.ItemDecoration {
    private final int columns;
    private int margin;

    public RecyclerItemDecorator(@IntRange(from = 0) int margin, @IntRange(from = 0) int columns) {
        this.margin = margin;
        this.columns = columns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        //set bottom margin to all
        outRect.bottom = margin;
        //we only add top margin to the first row
        if (position < columns) {
            outRect.top = margin * 2;
        }
    }
}
