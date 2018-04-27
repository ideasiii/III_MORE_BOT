package org.iii.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Jugo on 2018/4/27
 */
public class FileHandler
{
    public FileHandler()
    {
    
    }
    
    public String readFile(File parent, String child) throws IOException
    {
        String strContent = null;
        File myFile = new File(parent, child);
        InputStreamReader fstream = new InputStreamReader(new FileInputStream(myFile), "UTF-8");
        BufferedReader sbuffer = new BufferedReader(fstream);
        String line;
        strContent = "";
        while ((line = sbuffer.readLine()) != null)
        {
            strContent += line;
        }
        return strContent;
    }
}
