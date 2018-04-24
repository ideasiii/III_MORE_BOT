package org.iii.bot;

import android.util.SparseArray;

/**
 * Created by Jugo on 2018/4/23
 */
public class ScenarioData
{
    public static class CObject
    {
        public int Type;
        public int x;
        public int y;
        public int width;
        public int height;
        
        public CObject()
        {
            Type = -1;
            x = 0;
            y = 0;
            width = -1;
            height = -1;
        }
    }
    
    public int id;
    public SparseArray<CObject> objects;
    
    public ScenarioData()
    {
        id = -1;
        objects = new SparseArray<CObject>();
    }
    
    public static final int ID_OBJECT_IMAGE_LOCAL = 1;
    public static final int ID_OBJECT_IMAGE_REMOTE = 2;
    public static final int ID_OBJECT_BUTTON = 3;
    public static final int ID_OBJECT_TTS = 4;
    public static final int ID_OBJECT_SPEECH_NAVIGATION = 5;
    
    
    public static enum ENUM_OBJECT
    {
        image_local(ID_OBJECT_IMAGE_LOCAL),
        image_remote(ID_OBJECT_IMAGE_REMOTE),
        button(ID_OBJECT_BUTTON),
        tts(ID_OBJECT_TTS),
        speech_navigation(ID_OBJECT_SPEECH_NAVIGATION);
        private int value;
        
        ENUM_OBJECT(int value)
        {
            this.value = value;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public String getName()
        {
            return this.name();
        }
    }
    
    public static ENUM_OBJECT getObjectName(int objvalue)
    {
        for (ENUM_OBJECT aid : ENUM_OBJECT.values())
        {
            if (aid.value == objvalue)
            {
                return aid;
            }
        }
        return null;
    }
    
}
