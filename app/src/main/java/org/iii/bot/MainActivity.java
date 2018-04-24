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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.iii.module.Utility;
import org.iii.more.common.Logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity
{
    private final int ID_CALLBACK_READ_EXTERNAL_STORAGE = 1000;
    private ScenarioHandler scenarioHandler = null;
    private MainApplication mainApplication = null;
    private String mstrScenario = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Utility.fullScreenNoBar(this);
        RobotHead robotHead = new RobotHead(this);
        setContentView(robotHead);
        scenarioHandler = new ScenarioHandler(this);
        scenarioHandler.setViewGroup(robotHead);
        mainApplication = (MainApplication) this.getApplication();
        
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
    protected void onDestroy()
    {
        mainApplication.release();
        super.onDestroy();
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
            String SCENARIO_FILE_NAME = "/more/more_bot.txt";
            File myFile = new File(Utility.DownloadFold, SCENARIO_FILE_NAME);
            InputStreamReader fstream = new InputStreamReader(new FileInputStream(myFile), "UTF-8");
            BufferedReader sbuffer = new BufferedReader(fstream);
            String line;
            mstrScenario = "";
            while ((line = sbuffer.readLine()) != null)
            {
                mstrScenario += line;
            }
            mainApplication.init(this);
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
    
    public void startScenario()
    {
        scenarioHandler.init(mstrScenario);
    }
}
