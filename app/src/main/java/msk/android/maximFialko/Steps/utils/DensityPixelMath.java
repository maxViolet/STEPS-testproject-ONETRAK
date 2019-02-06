package msk.android.maximFialko.Steps.utils;

import android.content.Context;

public class DensityPixelMath {
    private Context dpContext;

    public DensityPixelMath(Context context) {
        dpContext = context;
    }

    public float dpFromPx(final int px) {
        return (int) (px / dpContext.getResources().getDisplayMetrics().density);
    }

    public float pxFromDp(final float dp) {
        return (int) (dpContext.getResources().getDisplayMetrics().density);
    }
}
