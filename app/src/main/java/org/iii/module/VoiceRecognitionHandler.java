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
            Logs.showTrace("[VoiceRecognitionHandler] RecognitionListener onReadyForSpeech");
        }
        
        @Override
        public void onBeginningOfSpeech()
        {
            Logs.showTrace("[VoiceRecognitionHandler] RecognitionListener onBeginningOfSpeech");
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
            Logs.showTrace("[VoiceRecognitionHandler] RecognitionListener onEndOfSpeech");
        }
        
        @Override
        public void onError(int errorCode)
        {
            Logs.showTrace("[VoiceRecognitionHandler] RecognitionListener onError: " + getErrorText(errorCode));
        }
        
        @Override
        public void onResults(Bundle bundle)
        {
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String text = "";
            for (String result : matches)
                text += result + "\n";
            Logs.showTrace("[VoiceRecognitionHandler] RecognitionListener onResults: " + text);
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
    
    public static String getErrorText(int errorCode)
    {
        String message;
        switch (errorCode)
        {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}
