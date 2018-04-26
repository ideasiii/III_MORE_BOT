package org.iii.bot;

import android.app.Activity;
import android.util.SparseArray;
import android.view.ViewGroup;

import org.iii.bot.ScenarioData.ENUM_OBJECT;
import org.iii.module.ImageObject;
import org.iii.module.ObjectTemplate;
import org.iii.module.TtsObject;
import org.iii.more.common.Logs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jugo on 2018/4/19
 */
public class ScenarioHandler
{
    private int mnCurrentScenario = -1;
    private ViewGroup theViewGroup = null;
    private Activity theActivity = null;
    private static SparseArray<ObjectTemplate> ObjectTemps = new SparseArray<>();
    private static SparseArray<SparseArray<ObjectTemplate>> scenarios = new SparseArray<SparseArray<ObjectTemplate>>();
    
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
            if (!jsonScenario.isNull("scenarios"))
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
            int nScenarioId = -1;
            int nType;
            String strObjectName = null;
            JSONArray jaScenario = jobj.getJSONArray("scenarios");
            JSONObject jstage = null;
            JSONArray jaObjects = null;
            JSONObject jObject = null;
            
            for (int i = 0; i < jaScenario.length(); ++i)
            {
                jstage = jaScenario.getJSONObject(i);
                nScenarioId = jstage.getInt("id");
                Logs.showTrace("[ScenarioHandler] parseScenarioJson id: " + nScenarioId);
                jaObjects = jstage.getJSONArray("objects");
                SparseArray<ObjectTemplate> ObjectTemps = new SparseArray<>();
                for (int j = 0; j < jaObjects.length(); ++j)
                {
                    jObject = jaObjects.getJSONObject(j);
                    nType = jObject.getInt("type");
                    createObject(ScenarioData.getObjectName(nType), jObject, ObjectTemps);
                }
                scenarios.put(i, ObjectTemps);
                Logs.showTrace("[ScenarioHandler] add scenario index: " + i);
            }
            runScenario(scenarios);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Logs.showError("[ScenarioHandler] parseScenarioJson Exception: " + e.getMessage());
        }
    }
    
    private void createObject(ENUM_OBJECT enumObject, JSONObject jObject, SparseArray<ObjectTemplate> ObjectTemps)
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
            Logs.showTrace("[ScenarioHandler] createObject id: " + nId);
        }
    }
    
    private void runScenario(SparseArray<SparseArray<ObjectTemplate>> scenarios)
    {
        Logs.showTrace("[ScenarioHandler] runScenario size: " + scenarios.size());
        SparseArray<ObjectTemplate> ots = scenarios.get(0);
        Logs.showTrace("[ScenarioHandler] ObjectTemplate size: " + ots.size());
        for (int i = 0; i < ots.size(); ++i)
        {
            Logs.showTrace("[ScenarioHandler] ObjectTemplate run: " + i + " hashcode" + ots.get(i).hashCode());
            ots.get(i).run();
        }
    }
    
}
