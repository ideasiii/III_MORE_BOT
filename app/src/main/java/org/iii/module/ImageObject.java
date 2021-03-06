package org.iii.module;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.iii.more.common.Logs;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Jugo on 2018/4/23
 */
public class ImageObject extends BaseObject
{
    private ImageView imageView = null;
    private String mstrImage = null;
    
    public ImageObject(Activity activity)
    {
        super(activity);
    }
    
    @Override
    protected void create(JSONObject jsonConfig)
    {
        Logs.showTrace("[ImageObject] create");
        imageView = new ImageView(theActivity);
        
        try
        {
            x = jsonConfig.getInt("x");
            y = jsonConfig.getInt("y");
            width = jsonConfig.getInt("w");
            height = jsonConfig.getInt("h");
            if (-1 == width)
            {
                width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            if (-1 == height)
            {
                height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
            imageView.setLayoutParams(layoutParams);
            imageView.setX(x);
            imageView.setY(y);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            
            String strFile = jsonConfig.getString("file");
            File myFile = new File(Utility.DownloadFold, strFile);
            mstrImage = myFile.toString();
            Logs.showTrace("[ImageObject] create File: " + mstrImage);
//            File imgFile = new File(mstrImage);
//
//            if (imgFile.exists())
//            {
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                imageView.setImageBitmap(myBitmap);
//            }
            
            Utility.loadImage(theActivity, myFile, imageView);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ImageObject] create Exception: " + e.getMessage());
        }
        Logs.showTrace("[ImageObject] class hashCode: " + this.hashCode());
        //theViewGroup.addView(imageView);
    }
    
    @Override
    protected void run()
    {
        Logs.showTrace("[ImageObject] run " + mstrImage);
        if (null != imageView)
        {
            theViewGroup.addView(imageView);
            Logs.showTrace("[ImageObject] run " + mstrImage);
        }
    }
}
