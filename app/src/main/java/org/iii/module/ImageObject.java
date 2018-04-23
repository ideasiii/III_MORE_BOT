package org.iii.module;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.iii.more.common.Logs;

/**
 * Created by Jugo on 2018/4/23
 */
public class ImageObject extends BaseObject
{
    private ImageView imageView = null;
    
    public ImageObject(Activity activity)
    {
        super(activity);
    }
    
    @Override
    protected void create()
    {
        Logs.showTrace("[ImageObject] create");
        imageView = new ImageView(theActivity);
        imageView.setBackgroundColor(Color.RED);
        x = 100;
        y = 100;
        width = 300;
        height = 300;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        imageView.setLayoutParams(layoutParams);
        imageView.setX(x);
        imageView.setY(y);
        theViewGroup.addView(imageView);
    }
}
