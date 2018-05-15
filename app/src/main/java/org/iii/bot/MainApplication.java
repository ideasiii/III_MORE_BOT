package org.iii.bot;

import android.app.Activity;
import android.app.Application;
import android.speech.tts.UtteranceProgressListener;

import org.iii.module.TtsHandler;
import org.iii.module.VoiceRecognitionHandler;
import org.iii.more.common.Logs;

/**
 * Created by Jugo on 2018/4/24
 */
public class MainApplication extends Application
{
    private static OnTtsStateListener ttsState = null;
    
    public static interface OnTtsStateListener
    {
        public void onState(int nState, String id);
    }
    
    public void SetOnTtsStateListener(OnTtsStateListener listener)
    {
        if (null != listener)
        {
            ttsState = listener;
            Logs.showTrace("[MainApplication] SetOnTtsStateListener");
        }
    }
    
    private TtsHandler ttsHandler = null;
    private VoiceRecognitionHandler voiceRecognitionHandler = null;
    
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
                ttsHandler.SetOnUtteranceProgressListener(new UtteranceProgressListener()
                {
                    @Override
                    public void onStart(String s)
                    {
                        if (null != ttsState)
                        {
                            ttsState.onState(0, s);
                        }
                    }
                    
                    @Override
                    public void onDone(String s)
                    {
                        if (null != ttsState)
                        {
                            Logs.showTrace("[MainApplication] TTS onDone");
                            ttsState.onState(1, s);
                        }
                    }
                    
                    @Override
                    public void onError(String s)
                    {
                        if (null != ttsState)
                        {
                            ttsState.onState(2, s);
                        }
                    }
                });
                activity.startScenario();
            }
        });
        ttsHandler.createTTS();
        
        voiceRecognitionHandler = new VoiceRecognitionHandler();
        voiceRecognitionHandler.initSpeechRecognizer(activity);
    }
    
    public void release()
    {
        ttsHandler.release();
        voiceRecognitionHandler.release();
        Logs.showTrace("[MainApplication] Release Resource");
    }
    
    public void tts(String strText, String strCallback)
    {
        ttsHandler.speack(strText, strCallback);
    }
    
    public void speechStart()
    {
        voiceRecognitionHandler.start();
    }
    
    public void speechStop()
    {
        voiceRecognitionHandler.stop();
    }
    
}
