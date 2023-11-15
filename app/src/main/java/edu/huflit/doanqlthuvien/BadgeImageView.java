package edu.huflit.doanqlthuvien;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

public class BadgeImageView extends ImageView {
    private int badgeCount = 0;
    private Paint badgePaint;
    private Paint textPaint;
    private Rect textRect;
    public BadgeImageView(Context context)
    {
        super(context);

    }
    private void init()
    {
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
        badgePaint = new Paint();
        badgePaint.setColor(getResources().getColor(android.R.color.white));
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        textRect = new Rect();
        textPaint.getTextBounds("99", 0, 2, textRect);
    }
    public void setBadgeCount(int count)
    {
        badgeCount = count;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (badgeCount > 0 )
        {
            float radius = (Math.max(getWidth(), getHeight())/2)/2;
            float centerX = getWidth() - radius -2;
            float centerY = radius + 2;

            canvas.drawCircle(centerX, centerY, radius, badgePaint);

            textPaint.getTextBounds(String.valueOf(badgeCount), 0, String.valueOf(badgeCount).length(), textRect);
            float textX = centerX - textRect.width()/2f;
            float textY = centerY + textRect.height()/2f;
            canvas.drawText(String.valueOf(badgeCount), textX, textY, textPaint);
        }
    }
}
