package org.iii.bot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Message;
import android.util.SparseArray;
import android.view.ViewGroup;

import org.iii.bot.ScenarioData.ENUM_OBJECT;
import org.iii.module.BaseObject;
import org.iii.module.ImageObject;
import org.iii.module.ObjectTemplate;
import org.iii.module.TtsObject;
import org.iii.more.common.Logs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import static org.iii.bot.ScenarioData.ID_NEXT_EVENT_SPEECH;
import static org.iii.bot.ScenarioData.ID_NEXT_EVENT_TTS;
import static org.iii.bot.ScenarioData.ID_SPEECH_FINISH;
import static org.iii.bot.ScenarioData.ID_TTS_FINISH;

/**
 * Created by Jugo on 2018/4/19
 */
public class ScenarioHandler
{
    private SparseArray<JSONObject> jscenario;
    private int mnCurrentScenario = -1;
    private static ViewGroup theViewGroup = null;
    private Activity theActivity;
    private MainApplication theApp;
    private final int MSG_GO_NEXT = 1;
    
    
    private static SparseArray<ObjectTemplate> ObjectTemps = new SparseArray<>();
    private static SparseArray<SparseArray<ObjectTemplate>> scenarios = new SparseArray<SparseArray<ObjectTemplate>>();
    
    ScenarioHandler(Activity activity)
    {
        theActivity = activity;
        theApp = (MainApplication) theActivity.getApplication();
        jscenario = new SparseArray<JSONObject>();
    }
    
    public void setViewGroup(ViewGroup viewGroup)
    {
        theViewGroup = viewGroup;
    }
    
    public boolean init(String strScenario)
    {
        try
        {
            JSONObject jsonScenario = new JSONObject(strScenario);
            if (!jsonScenario.isNull("scenarios"))
            {
                JSONArray jaScenarios = jsonScenario.getJSONArray("scenarios");
                int nScenarioId = -1;
                if (null != jaScenarios && 0 < jaScenarios.length())
                {
                    jscenario.clear();
                    for (int i = 0; i < jaScenarios.length(); ++i)
                    {
                        nScenarioId = jaScenarios.getJSONObject(i).getInt("id");
                        jscenario.put(nScenarioId, jaScenarios.getJSONObject(i));
                        Logs.showTrace("[ScenarioHandler] init Scenario JSONObject: " + jscenario.get(nScenarioId).toString() + " id: " + nScenarioId);
                    }
                    return true;
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ScenarioHandler] init Exception: " + e.getMessage());
        }
        return false;
    }
    
    public void run()
    {
        theApp.SetOnTtsStateListener(new MainApplication.OnTtsStateListener()
        {
            @Override
            public void onState(int nState, String id)
            {
                switch (nState)
                {
                    case 0: // onStart
                        break;
                    case 1: // onDone
                        theHandler.sendEmptyMessage(MSG_GO_NEXT);
                        break;
                    case 2: // onError
                        break;
                }
            }
        });
        
        // 垃圾程式一啟動 就先執行id = 1的設定喔!!
        runScenario(1);
    }
    
    private void runScenario(int id)
    {
        mnCurrentScenario = id;
        JSONObject jroot = jscenario.get(id);
        
        // 初始物件列表囉
        try
        {
            for (int i = 0; i < jroot.getJSONArray("objects").length(); ++i)
            {
                createObject(jroot.getJSONArray("objects").getJSONObject(i));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ScenarioHandler] run Exception: " + e.getMessage());
        }
    }
    
    private void createObject(JSONObject jObject)
    {
        try
        {
            ENUM_OBJECT enumObject = ScenarioData.getObjectName(jObject.getInt("type"));
            JSONObject jsonObject = jObject.getJSONObject(enumObject.name());
            ObjectTemplate<BaseObject> tobject = new ObjectTemplate<BaseObject>();
            switch (enumObject)
            {
                case image_local:
                    tobject.setObject(new ImageObject(theActivity));
                    break;
                case image_remote:
                    break;
                case button:
                    break;
                case tts:
                    tobject.setObject(new TtsObject(theActivity));
                    break;
                case speech_navigation:
                    break;
                default:
                    Logs.showError("[ScenarioHandler] createObject Over ENUM_OBJECT");
                    return;
            }
            
            if (tobject.isValid())
            {
                tobject.setViewGroup(theViewGroup);
                tobject.create(jsonObject);
                tobject.run();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ScenarioHandler] createObject Exception: " + e.getMessage());
        }
    }
    
    private void goNextScenario(int nEvent)
    {
        JSONObject jroot = jscenario.get(mnCurrentScenario);
        int nNext_Event = -1;
        int nNext_Id = -1;
        
        try
        {
            nNext_Event = jroot.getInt("next_event");
            nNext_Id = jroot.getInt("next_id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        
        // Event come~
        switch (nEvent)
        {
            case ID_TTS_FINISH:
                if (nNext_Event == ID_NEXT_EVENT_TTS)
                {
                    runScenario(nNext_Id);
                }
                break;
            case ID_SPEECH_FINISH:
                break;
        }
    }
    
    @SuppressLint("HandlerLeak")
    private Handler theHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_GO_NEXT:
                    goNextScenario(ID_TTS_FINISH);
                    break;
            }
        }
    };
}
