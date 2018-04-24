package org.iii.module;

import android.app.Activity;
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
    
    public ImageObject(Activity activity)
    {
        super(activity);
    }
    
    @Override
    protected void create(JSONObject jsonConfig)
    {
        Logs.showTrace("[ImageObject] create");
        ImageView imageView = new ImageView(theActivity);
        
        try
        {
            x = jsonConfig.getInt("x");
            y = jsonConfig.getInt("y");
            width = jsonConfig.getInt("w");
            height = jsonConfig.getInt("h");
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
            imageView.setLayoutParams(layoutParams);
            imageView.setX(x);
            imageView.setY(y);
            
            String strFile = jsonConfig.getString("file");
            File myFile = new File(Utility.DownloadFold, strFile);
            Logs.showTrace("[ImageObject] create File: " + myFile.toString());
            Utility.loadImage(theActivity, myFile, imageView);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ImageObject] create Exception: " + e.getMessage());
        }
        
        theViewGroup.addView(imageView);
    }
}
