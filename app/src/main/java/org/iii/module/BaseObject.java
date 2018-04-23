package org.iii.module;

import android.app.Activity;
import android.renderscript.BaseObj;
import android.view.ViewGroup;

import org.json.JSONObject;

/**
 * Created by Jugo on 2018/4/23
 */
public abstract class BaseObject
{
    public int x = 0;
    public int y = 0;
    public int width = ViewGroup.LayoutParams.MATCH_PARENT;
    public int height = ViewGroup.LayoutParams.MATCH_PARENT;
    protected Activity theActivity = null;
    protected ViewGroup theViewGroup = null;
    
    public BaseObject(Activity activity)
    {
        theActivity = activity;
    }
    
    protected abstract void create(JSONObject jsonConfig);
    
    public void setViewGroup(ViewGroup viewGroup)
    {
        theViewGroup = viewGroup;
    }
}
