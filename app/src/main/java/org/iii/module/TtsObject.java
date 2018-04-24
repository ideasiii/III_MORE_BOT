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
    private MainApplication mainApplication = null;
    
    public TtsObject(Activity activity)
    {
        super(activity);
        mainApplication = (MainApplication) activity.getApplication();
    }
    
    @Override
    protected void create(JSONObject jsonConfig)
    {
        try
        {
            String strText = jsonConfig.getString("text");
            Logs.showTrace("[TtsObject] create TTS text: " + strText);
            mainApplication.tts(strText, "111");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
