/****************************************************************
 This is a garbage project.
 The project will build a robot.
 All modules are like garbage.
 So do not use this application.
 You will cry..........
 Date:2018-04-16
 ****************************************************************/

package org.iii.bot;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import org.iii.iii_more_bot.R;
import org.iii.module.Utility;
import org.iii.more.common.Logs;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity
{
    private RobotHead robotHead = null;
    private final int ID_CALLBACK_READ_EXTERNAL_STORAGE = 1000;
    private final String SCENARIO_FILE_NAME = "/more/more_bot.txt";
    private ScenarioHandler scenarioHandler = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Utility.fullScreenNoBar(this);
        robotHead = new RobotHead(this);
        setContentView(robotHead);
        scenarioHandler = new ScenarioHandler(this);
        scenarioHandler.setViewGroup(robotHead);
        
        //======= Run Time Permission =========//
        // Android6.0以上的版本，必須讓使用者自行勾選是否開放這些權限
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ID_CALLBACK_READ_EXTERNAL_STORAGE);
            }
            else
            {
                //=======先去找JSON描述檔===========//
                loadScenario();
            }
        }
        
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case ID_CALLBACK_READ_EXTERNAL_STORAGE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //=======先去找JSON描述檔===========//
                    loadScenario();
                }
                else
                {
                    new AlertDialog.Builder(MainActivity.this).setMessage("我真的沒有要做壞事, 給我權限吧?").setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ID_CALLBACK_READ_EXTERNAL_STORAGE);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            finish();
                        }
                    }).show();
                }
            }
        }
    }
    
    private void loadScenario()
    {
        try
        {
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File myFile = new File(folder, SCENARIO_FILE_NAME);
            FileInputStream fstream = new FileInputStream(myFile);
            StringBuffer sbuffer = new StringBuffer();
            int i;
            while ((i = fstream.read()) != -1)
            {
                sbuffer.append((char) i);
            }
            fstream.close();
            Logs.showTrace("[MainActivity] loadScenario Context: " + sbuffer.toString());
            if (0 < sbuffer.length())
            {
                scenarioHandler.init(sbuffer.toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logs.showError("[MainActivity] loadScenario Exception: " + e.getMessage());
            new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    finish();
                }
            }).show();
        }
    }
}
