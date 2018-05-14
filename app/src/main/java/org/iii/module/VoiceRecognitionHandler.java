package org.iii.module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import org.iii.more.common.Logs;

import java.util.ArrayList;

/**
 * Created by Jugo on 2018/5/14
 */
public class VoiceRecognitionHandler
{
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    
    public VoiceRecognitionHandler()
    {
    }
    
    public void initSpeechRecognizer(Context context)
    {
        if (null == speech && null != context)
        {
            speech = SpeechRecognizer.createSpeechRecognizer(context);
            Logs.showTrace("[VoiceRecognitionHandler] initSpeechRecognizer isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(context));
            if (SpeechRecognizer.isRecognitionAvailable(context))
            {
                speech.setRecognitionListener(recognitionListener);
                recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh");
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
            }
            else
            {
                speech = null;
            }
        }
    }
    
    public void start()
    {
        if (null != speech && null != recognizerIntent)
        {
            speech.startListening(recognizerIntent);
        }
    }
    
    public void stop()
    {
        if (null != speech)
        {
            speech.stopListening();
        }
    }
    
    public void release()
    {
        if (speech != null)
        {
            speech.destroy();
            speech = null;
        }
    }
    
    
    private RecognitionListener recognitionListener = new RecognitionListener()
    {
        @Override
        public void onReadyForSpeech(Bundle bundle)
        {
            Logs.showTrace("[VoiceRecognitionHandler] onReadyForSpeech");
        }
        
        @Override
        public void onBeginningOfSpeech()
        {
        
        }
        
        @Override
        public void onRmsChanged(float v)
        {
        
        }
        
        @Override
        public void onBufferReceived(byte[] bytes)
        {
        
        }
        
        @Override
        public void onEndOfSpeech()
        {
        
        }
        
        @Override
        public void onError(int i)
        {
        
        }
        
        @Override
        public void onResults(Bundle bundle)
        {
            Logs.showTrace("[VoiceRecognitionHandler] onResults");
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String text = "";
            for (String result : matches)
                text += result + "\n";
            
            // returnedText.setText(text);
        }
        
        @Override
        public void onPartialResults(Bundle bundle)
        {
        
        }
        
        @Override
        public void onEvent(int i, Bundle bundle)
        {
        
        }
    };
}
