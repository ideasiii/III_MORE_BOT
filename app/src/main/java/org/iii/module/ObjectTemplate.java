package org.iii.module;

import android.app.Activity;
import android.view.ViewGroup;

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
    
    public void create()
    {
        if (null != this.t)
        {
            this.t.create();
        }
    }
    
    public void setViewGroup(ViewGroup viewGroup)
    {
        this.t.setViewGroup(viewGroup);
    }
    
}
