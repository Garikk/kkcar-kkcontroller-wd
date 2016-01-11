/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.watchdog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.controller.dbusconnector.DBusManager;
import kkdev.kksystem.controller.watchdog.updater.WDUpdater;
import kkdev.kksystem.controller.wdconnection.WDSystemState;
import kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates;
import kkdev.kksystem.controller.wdconnection.WatchDogService;
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
    public static final String KK_BASE_BACKUP = "./backup/";
    public static final String KK_BASE_UPDATE_TEMP = "./tmp/";
    public static final String KK_BASE_UPDATE_TEMP_PLUGINS = KK_BASE_UPDATE_TEMP + "/plugins/";
    public static final String KK_BASE_UPDATE_TEMP_BASE = KK_BASE_UPDATE_TEMP + "/base/";
    public static final String KK_BASE_UPDATE_TEMP_CONF = KK_BASE_UPDATE_TEMP + "/conf/";
    public static final String KK_BASE_UPDATE_TEMP_EXTCONF = KK_BASE_UPDATE_TEMP + "/extconf/";
    public static final String KK_BASE_UPDATE_REPAIRBACKUP_MAIN = KK_BASE_BACKUP + "/backup_main/";
    public static final String KK_BASE_UPDATE_REPAIRBACKUP_SECONDARY = KK_BASE_BACKUP + "/backup_sec/";
    public static final String KK_BASE_EMERGENCYREPAIR = KK_BASE_BACKUP + "/emergencyrepair/";

    public static final String KK_UPDATE_PACK = "kksupdate.zip";
    public static final String KK_UPDATE_PACK_SIG = "kksupdate.zip.sig";

    private static Process KKProcess;

    public static void main(String[] args) throws InterruptedException {

        WDConfiguration Config = new WDConfiguration();

        System.out.println("KKSystem Watchdog");
        System.out.println("==================");
        System.out.println("Update");
        //
        if (Config.CheckUpdateOnStart) {
            WDUpdater.ExecUpdate();
        }
        //
        System.out.println("Check network");
        try {
            DBusManager.Init();
        } catch (DBusException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.UnsatisfiedLinkError Ex) {

            System.out.println("DBus load error (not found?)");
        }

        System.out.println("Init Watchdog and start KK");
        //
        StartKK();
        //
        WatchDogService.getInstance().StartWDS();
        int i = 0;
        boolean Shutdown = false;

        WDStates WDStateTarget;
        WDStates WDStateCurrent;
        while (!Shutdown) {
            sleep(1000 + WatchDogService.getInstance().getCurrentSystemState().WaitTimer);
            WDStateTarget = WatchDogService.getInstance().getCurrentSystemState().TargetState;
            WDStateCurrent = WatchDogService.getInstance().getCurrentSystemState().CurrentState;

            WatchDogService.getInstance().CheckWatchDog();

            switch (WDStateTarget) {
                case WD_SysState_POWEROFF:
                    Shutdown = true;
                    WatchDogService.getInstance().ChangeWDStateCurrent(WDStates.WD_SysState_POWEROFF);
                    KillKK();
                    break;
                case WD_SysState_REBOOT:
                    Shutdown=true;
                    WatchDogService.getInstance().ChangeWDStateCurrent(WDStates.WD_SysState_REBOOT);
                    KillKK();
                    break;
                case WD_SysState_REBOOT_KK:
                    WatchDogService.getInstance().ChangeWDStateCurrent(WDStates.WD_SysState_REBOOT_KK);
                    KillKK();
                    StartKK();
                    WatchDogService.getInstance().ChangeWDStateCurrent(WDStates.WD_SysState_ACTIVE);
                    break;
                case WD_SysState_NEEDRESTORE_EMERG:
                    EmergencyRestore();
                    break;
            }
        }
        //
        //
        if (WatchDogService.getInstance().getCurrentSystemState().CurrentState==WDStates.WD_SysState_POWEROFF)
        {
            out.println("POWER OFF SYSTEM");
        }
        else if (WatchDogService.getInstance().getCurrentSystemState().CurrentState==WDStates.WD_SysState_REBOOT)
        {
            out.println("REBOOT SYSTEM");
        }
        
        out.println("Stop");
        exit(0);
    }

    private static void StartKK() {
        System.out.println("KKSystem start kkcontroller");
        StartKKProcess();
    }

    private static void KillKK() {
       if (KKProcess!=null)
       {
           KKProcess.destroyForcibly();
       }
        
        
    }

    private static void StartKKProcess() {
            Thread KKThread = new Thread(new Runnable() {
            Process Proc;
            @Override
            public void run() {
                Runtime runTime = Runtime.getRuntime();
                WDSystemState WState=WatchDogService.getInstance().getCurrentSystemState();
                System.out.println(WState.CurrentState);
                while (WState.CurrentState!=WDStates.WD_SysState_POWEROFF)
                {
                    WState=WatchDogService.getInstance().getCurrentSystemState();
                    try {
                        System.out.println("Exec kkcontroller");
                        if (WState.CurrentState!=WDStates.WD_SysState_NEEDRESTORE_EMERG)
                        {
                          //  Proc = runTime.exec("java -jar kkcontroller-1.0.jar service <& 2> kklog.log");
                            Proc = runTime.exec("java -jar kkcontroller-1.0.jar > kklog.log");
                            Proc.waitFor();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        });
         KKThread.start();
    }
    
    
    private static void EmergencyRestore()
    {
       
        WatchDogService.getInstance().ChangeWDStateCurrent(WDStates.WD_SysState_NEEDRESTORE_EMERG);
    }
}
