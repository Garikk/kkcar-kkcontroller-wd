/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.watchdog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.controller.dbusconnector.DBusManager;
import kkdev.kksystem.controller.watchdog.updater.WDUpdater;
import org.freedesktop.dbus.exceptions.DBusException;

/**
 *
 * @author blinov_is
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static final String KK_BASE_CONFPATH = "./etc/";
    public static final String KK_BASE_PLUGINPATH = "./plugins/";
    public static final String KK_BASE_EXCHANGEPATH = "./exchange/";
    public static final String KK_BASE_BACKUP= "./backup/";
    public static final String KK_BASE_UPDATE_TEMP = "./tmp/";
    public static final String KK_BASE_UPDATE_TEMP_PLUGINS = KK_BASE_UPDATE_TEMP + "/plugins/";
    public static final String KK_BASE_UPDATE_TEMP_BASE = KK_BASE_UPDATE_TEMP + "/base/";
    public static final String KK_BASE_UPDATE_TEMP_CONF = KK_BASE_UPDATE_TEMP + "/conf/";
    public static final String KK_BASE_UPDATE_TEMP_EXTCONF = KK_BASE_UPDATE_TEMP + "/extconf/";
    public static final String KK_BASE_UPDATE_REPAIRBACKUP_MAIN = KK_BASE_BACKUP+"/backup_main/";
    public static final String KK_BASE_UPDATE_REPAIRBACKUP_SECONDARY = KK_BASE_BACKUP+"/backup_sec/";
    public static final String KK_BASE_EMERGENCYREPAIR = KK_BASE_BACKUP+"/emergencyrepair/";

    public static void main(String[] args) {
        WDConfiguration Config=new WDConfiguration();
    
        System.out.println("KKSystem Watchdog");
        System.out.println("KKSystem Update");
        //
        if (Config.CheckUpdateOnStart)
            WDUpdater.ExecUpdate();
        //
        System.out.println("KKSystem check network");        
        try {
            DBusManager.Init();
        } catch (DBusException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (java.lang.UnsatisfiedLinkError Ex)
        {
            System.out.println("DBus load error (not found?)");        
        }
        
        System.out.println("KKSystem check kkcontroller");        
        System.out.println("KKSystem start kkcontroller");
        
        

    
    }

}
