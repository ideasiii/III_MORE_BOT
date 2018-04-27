package org.iii.module;

import android.app.Activity;
import android.view.ViewGroup;

import org.json.JSONObject;

/**
 * Created by Jugo on 2018/4/23
 */
public class ObjectTemplate<T extends BaseObject>
{
    private T t;
    
    public T getObject()
    {
        return t;
    }
    
    public void setObject(T t)
    {
        this.t = t;
    }
    
    public void create(JSONObject jsonConfig)
    {
        this.t.create(jsonConfig);
    }
    
    public void setViewGroup(ViewGroup viewGroup)
    {
        this.t.setViewGroup(viewGroup);
    }
    
    public void run()
    {
        this.t.run();
    }
    
    public boolean isValid()
    {
        return null != this.t;
    }
}
