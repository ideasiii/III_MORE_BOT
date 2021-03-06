package org.iii.module;

import android.app.Activity;

import org.iii.bot.MainApplication;
import org.iii.more.common.Logs;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jugo on 2018/4/24
 */
public class TtsObject extends BaseObject
{
    private String mstrText = "";
    
    public TtsObject(Activity activity)
    {
        super(activity);
    }
    
    @Override
    protected void create(JSONObject jsonConfig)
    {
        try
        {
            mstrText = jsonConfig.getString("text");
            Logs.showTrace("[TtsObject] create TTS text: " + mstrText);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void run()
    {
        Logs.showTrace("[TtsObject] run TTS text: " + mstrText);
        mainApplication.tts(mstrText, "111");
    }
}
