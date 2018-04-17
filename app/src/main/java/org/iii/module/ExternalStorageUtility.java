package org.iii.module;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by Jugo on 2018/4/17
 */
public abstract class ExternalStorageUtility
{
    //============ 檢查 ================//
    public static boolean isExternalStorageReadOnly()
    {
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }
    
    public static boolean isExternalStorageMounted()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
    
    //=============== Private External Storage =======================//
    public static String getPrivateExternalStorageBaseDir(Context context, String dirType)
    {
        if (isExternalStorageMounted())
        {
            return context.getExternalFilesDir(dirType).getAbsolutePath();
        }
        return null;
    }
    
    public static String getPrivateCacheExternalStorageBaseDir(Context context)
    {
        if (isExternalStorageMounted())
        {
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return null;
    }
    
    public static String getPublicExternalStorageBaseDir(String dirType)
    {
        if (isExternalStorageMounted())
        {
            if (null == dirType)
            {
                return Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            else
            {
                return Environment.getExternalStoragePublicDirectory(dirType).getAbsolutePath();
            }
        }
        return null;
    }
    
    public static long getExternalStorageSpace()
    {
        long ret = 0;
        if (isExternalStorageMounted())
        {
            StatFs fileState = new StatFs(getPublicExternalStorageBaseDir(null));
            long count = fileState.getBlockCountLong();
            long size = fileState.getBlockSizeLong();
            ret = count * size / 1024 / 1024;
        }
        return ret;
    }
    
    public static long getExternalStorageLeftSpace()
    {
        long ret = 0;
        if (isExternalStorageMounted())
        {
            StatFs fileState = new StatFs(getPublicExternalStorageBaseDir(null));
            long count = fileState.getFreeBlocksLong();
            long size = fileState.getBlockSizeLong();
            ret = count * size / 1024 / 1024;
        }
        return ret;
    }
    
    public static long getExternalStorageAvailableSpace()
    {
        long ret = 0;
        if (isExternalStorageMounted())
        {
            StatFs fileState = new StatFs(getPublicExternalStorageBaseDir(null));
            long count = fileState.getAvailableBlocksLong();
            long size = fileState.getBlockSizeLong();
            ret = count * size / 1024 / 1024;
        }
        return ret;
    }
}
