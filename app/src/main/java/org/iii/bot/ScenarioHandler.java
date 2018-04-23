package org.iii.bot;

import android.app.Activity;
import android.util.SparseArray;
import android.view.ViewGroup;

import org.iii.bot.ScenarioData.ENUM_OBJECT;
import org.iii.module.ImageObject;
import org.iii.module.ObjectTemplate;
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
    private static SparseArray<ObjectTemplate<ImageObject>> ObjectTemps = new SparseArray<>();
    
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
                    switch (nType)
                    {
                        case ScenarioData.ID_OBJECT_IMAGE_LOCAL:
                            createObject(image_local, jObject);
                            break;
                    }
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
        int nId;
        switch (enumObject)
        {
            case image_local:
                nId = ObjectTemps.size();
                ObjectTemps.put(nId, new ObjectTemplate<ImageObject>());
                if (null != ObjectTemps.get(nId))
                {
                    ObjectTemps.get(nId).setObject(new ImageObject(theActivity));
                    ObjectTemps.get(nId).setViewGroup(theViewGroup);
                    ObjectTemps.get(nId).create(jObject);
                }
                Logs.showTrace("[ScenarioHandler] createObject ImageObject");
                break;
            case image_remote:
                break;
            case button:
                break;
            case tts:
                break;
            case speech:
                break;
        }
    }
}
