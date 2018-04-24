package org.iii.bot;

import android.app.Activity;
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

import static org.iii.bot.ScenarioData.ENUM_OBJECT.*;

/**
 * Created by Jugo on 2018/4/19
 */
public class ScenarioHandler
{
    private ViewGroup theViewGroup = null;
    private Activity theActivity = null;
    public static SparseArray<ScenarioData> Scenarios = new SparseArray<ScenarioData>();
    private static SparseArray<ObjectTemplate> ObjectTemps = new SparseArray<>();
    
    ScenarioHandler(Activity activity)
    {
        theActivity = activity;
    }
    
    public void setViewGroup(ViewGroup viewGroup)
    {
        theViewGroup = viewGroup;
    }
    
    public void init(String strScenario)
    {
        try
        {
            JSONObject jsonScenario = new JSONObject(strScenario);
            if (!jsonScenario.isNull("scenarios") && !jsonScenario.isNull("stages"))
            {
                Logs.showTrace("[ScenarioHandler] init Scenario: " + jsonScenario.toString());
                parseScenarioJson(jsonScenario);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ScenarioHandler] init Exception: " + e.getMessage());
        }
    }
    
    private void parseScenarioJson(JSONObject jobj)
    {
        try
        {
            int nStage = jobj.getInt("stages");
            int nType;
            String strObjectName = null;
            JSONArray jaScenario = jobj.getJSONArray("scenarios");
            JSONObject jstage = null;
            JSONArray jaObjects = null;
            JSONObject jObject = null;
            
            for (int i = 0; i < jaScenario.length(); ++i)
            {
                jstage = jaScenario.getJSONObject(i);
                Logs.showTrace("[ScenarioHandler] parseScenarioJson id: " + jstage.getInt("id"));
                jaObjects = jstage.getJSONArray("objects");
                for (int j = 0; j < jaObjects.length(); ++j)
                {
                    jObject = jaObjects.getJSONObject(j);
                    nType = jObject.getInt("type");
                    createObject(ScenarioData.getObjectName(nType), jObject);
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    private void createObject(ENUM_OBJECT enumObject, JSONObject jObject)
    {
        final int nId = ObjectTemps.size();
        JSONObject jsonObject = null;
        try
        {
            jsonObject = jObject.getJSONObject(enumObject.name());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ScenarioHandler] createObject Exception: " + e.getMessage());
        }
        
        switch (enumObject)
        {
            case image_local:
                ObjectTemps.put(nId, new ObjectTemplate<ImageObject>());
                ObjectTemps.get(nId).setObject(new ImageObject(theActivity));
                break;
            case image_remote:
                break;
            case button:
                break;
            case tts:
                ObjectTemps.put(nId, new ObjectTemplate<TtsObject>());
                ObjectTemps.get(nId).setObject(new TtsObject(theActivity));
                break;
            case speech_navigation:
                break;
            default:
                Logs.showError("[ScenarioHandler] createObject Over ENUM_OBJECT");
                return;
        }
        
        if (null != ObjectTemps.get(nId))
        {
            ObjectTemps.get(nId).setViewGroup(theViewGroup);
            ObjectTemps.get(nId).create(jsonObject);
        }
    }
}
