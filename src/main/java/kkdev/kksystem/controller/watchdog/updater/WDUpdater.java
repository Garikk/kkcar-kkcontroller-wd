/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.watchdog.updater;

import kkdev.kksystem.controller.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_BACKUP;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_CONFPATH;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_EMERGENCYREPAIR;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_PLUGINPATH;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_UPDATE_REPAIRBACKUP_MAIN;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_UPDATE_REPAIRBACKUP_SECONDARY;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_UPDATE_TEMP;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_UPDATE_TEMP_BASE;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_UPDATE_TEMP_CONF;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_UPDATE_TEMP_EXTCONF;
import static kkdev.kksystem.controller.watchdog.Main.KK_BASE_UPDATE_TEMP_PLUGINS;

/**
 *
 * @author sayma_000
 */
public abstract class WDUpdater {

    public static void ExecUpdate() {
        //
        File TempPath = new java.io.File(KK_BASE_UPDATE_TEMP);
        //
        if (!TempPath.exists()) {
            System.out.println("Not found update Tmp folder..not updates? Ok");
            return;
        }
        //
        PrepareFolders();
        //

        //Backup old config
        System.out.println("Save current config");
        try {
            System.out.println("Move old backup");
            FileUtils.deleteRecursive(new File(KK_BASE_UPDATE_REPAIRBACKUP_SECONDARY));
            Files.move(Paths.get(KK_BASE_UPDATE_REPAIRBACKUP_MAIN), Paths.get(KK_BASE_UPDATE_REPAIRBACKUP_SECONDARY));
            System.out.println("Backup existing config");
            FileUtils.deleteRecursive(new File(KK_BASE_UPDATE_REPAIRBACKUP_MAIN));
            (new FileUtils()).CopyDirectory(new File(".//"), new File(KK_BASE_UPDATE_REPAIRBACKUP_MAIN),"backup");
        } catch (IOException ex) {
            Logger.getLogger(WDUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }

        //
        //Move update to production
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_PLUGINS);
        //
        if (TempPath.exists()) {
            try {
                System.out.println("Move new plugin files");
                for (File F : TempPath.listFiles()) {
                    System.out.println(F.getCanonicalPath());
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get(KK_BASE_PLUGINPATH + F.getName()), REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_BASE);
        //
        if (TempPath.exists()) {
            try {
                System.out.println("Move new main program");
                for (File F : TempPath.listFiles()) {
                    System.out.println(F.getCanonicalPath());
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get("./" + F.getName()), REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_CONF);
        //
        if (TempPath.exists()) {
            try {
                System.out.println("Move new config files");
                for (File F : TempPath.listFiles()) {
                    System.out.println(F.getCanonicalPath());
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get(KK_BASE_CONFPATH + F.getName()), REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_EXTCONF);
        //
        if (TempPath.exists()) {
            try {
                System.out.println("Move External config files");
                for (File F : TempPath.listFiles()) {
                    System.out.println(F.getCanonicalPath());
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get(KK_BASE_CONFPATH + F.getName()), REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        //Remove temp update folder
        try {
            //
            FileUtils.deleteRecursive(new File(KK_BASE_UPDATE_TEMP));
        } catch (IOException ex) {
            Logger.getLogger(WDUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
           

    }

    private static void PrepareFolders() {
        File TempPath = new java.io.File(KK_BASE_UPDATE_TEMP);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        // Main backup folder
        //
        TempPath = new java.io.File(KK_BASE_BACKUP);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        // Backup 1
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_REPAIRBACKUP_MAIN);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        // Second backup folder
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_REPAIRBACKUP_SECONDARY);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        // Backup 2
        //
        TempPath = new java.io.File(KK_BASE_EMERGENCYREPAIR);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        //
        // Emergency backup folder
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_PLUGINS);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_BASE);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_CONF);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_EXTCONF);
        //
        if (!TempPath.exists()) {
            TempPath.mkdir();
        }
        //
    }
   
}
