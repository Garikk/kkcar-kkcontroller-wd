/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.watchdog;

import com.google.gson.Gson;
import java.io.File;

/**
 *
 * @author blinov_is
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static final String KK_BASE_UPDATE_TEMP="./tmp";
    public static final String KK_BASE_UPDATE_TEMP_PLUGINS=KK_BASE_UPDATE_TEMP+"/plugins";
    public static final String KK_BASE_UPDATE_TEMP_BASE=KK_BASE_UPDATE_TEMP+"/base";
    
    public static final String KK_BASE_UPDATE_WDJOB_FILE="kkcar_wdjob.json";
    
    
    public static void main(String[] args) {
        //
        System.out.println("KK System Watchdog");
        //
        //
        File WDJob = new java.io.File(KK_BASE_UPDATE_WDJOB_FILE);   
        //
        if (!WDJob.exists())
            return;
        //
        File TempPath = new java.io.File(KK_BASE_UPDATE_TEMP);   
        //
        if (!TempPath.exists())
            TempPath.mkdir();
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_PLUGINS);   
        //
        if (!TempPath.exists())
            TempPath.mkdir();

        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_BASE);   
        //
        if (!TempPath.exists())
            TempPath.mkdir();
        //
        WatchdogJob WDJ=WatchdogJob.LoadWatchdogJob(KK_BASE_UPDATE_WDJOB_FILE);
        //
    }
    
}
