package org.iii.module;

import android.app.Activity;

import org.iii.more.common.Logs;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jugo on 2018/5/15
 */
public class VoiceRecognitionObject extends BaseObject
{
    private boolean mbRepeat;
    
    public VoiceRecognitionObject(Activity activity)
    {
        super(activity);
    }
    
    @Override
    protected void create(JSONObject jsonConfig)
    {
        try
        {
            mbRepeat = jsonConfig.getBoolean("repeat");
            
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void run()
    {
    
    }
}
