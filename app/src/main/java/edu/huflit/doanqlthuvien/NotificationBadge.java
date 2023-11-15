package edu.huflit.doanqlthuvien;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class NotificationBadge extends GradientDrawable {
    private int badgeCount;
    public NotificationBadge(Context context)
    {
        super();
        setColor(Color.RED);
        setShape(GradientDrawable.OVAL);
        setDpSize(context);
    }
    private void setDpSize(Context context)
    {
        float density = context.getResources().getDisplayMetrics().density;
        int size = Math.round(12*density);
        setSize(size, size);
    }
    private void setBadgeCount(int count)
    {
        this.badgeCount = count;
    }
    private int getBadgeCount()
    {
        return badgeCount;
    }
}
