package org.iii.bot;

import android.app.Activity;
import android.app.Application;

import org.iii.module.TtsHandler;
import org.iii.more.common.Logs;

/**
 * Created by Jugo on 2018/4/24
 */
public class MainApplication extends Application
{
    private TtsHandler ttsHandler = null;
    
    public MainApplication()
    {
        super();
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        
    }
    
    public void init(final MainActivity activity)
    {
        ttsHandler = new TtsHandler(activity);
        ttsHandler.setOnTTSStartedListener(new TtsHandler.OnTTSStartedListener()
        {
            @Override
            public void OnStarted()
            {
                activity.startScenario();
            }
        });
        ttsHandler.createTTS();
    }
    
    public void release()
    {
        ttsHandler.release();
        Logs.showTrace("[MainApplication] Release Resource");
    }
    
    public void tts(String strText, String strCallback)
    {
        ttsHandler.speack(strText, strCallback);
    }
    
}
