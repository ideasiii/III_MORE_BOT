package org.iii.module;

import android.app.Activity;
import android.renderscript.BaseObj;
import android.view.ViewGroup;

import org.iii.bot.MainApplication;
import org.json.JSONObject;

/**
 * Created by Jugo on 2018/4/23
 */
public abstract class BaseObject
{
    int x = 0;
    int y = 0;
    int width = ViewGroup.LayoutParams.MATCH_PARENT;
    int height = ViewGroup.LayoutParams.MATCH_PARENT;
    Activity theActivity = null;
    ViewGroup theViewGroup = null;
    MainApplication mainApplication = null;
    
    BaseObject(Activity activity)
    {
        theActivity = activity;
        mainApplication = (MainApplication) activity.getApplication();
    }
    
    protected abstract void create(JSONObject jsonConfig);
    
    protected abstract void run();
    
    public void setViewGroup(ViewGroup viewGroup)
    {
        theViewGroup = viewGroup;
    }
}
