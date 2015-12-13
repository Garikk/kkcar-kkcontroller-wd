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
    public static final String KK_BASE_UPDATE_TEMP = "./tmp/";
    public static final String KK_BASE_UPDATE_TEMP_PLUGINS = KK_BASE_UPDATE_TEMP + "/plugins/";
    public static final String KK_BASE_UPDATE_TEMP_BASE = KK_BASE_UPDATE_TEMP + "/base/";
    public static final String KK_BASE_UPDATE_TEMP_CONF = KK_BASE_UPDATE_TEMP + "/conf/";
    public static final String KK_BASE_UPDATE_TEMP_EXTCONF = KK_BASE_UPDATE_TEMP + "/extconf/";

    public static void main(String[] args) {
        System.out.println("KKSystem Watchdog");
        System.out.println("KKSystem Check system status");
        System.out.println("KKSystem Update");
        //
        File TempPath = new java.io.File(KK_BASE_UPDATE_TEMP);
        //
        if (!TempPath.exists()) {
            System.out.println("Not found Tmp folder..exitting");
            return;
        }
        //
        
        TempPath = new java.io.File(KK_BASE_UPDATE_TEMP_PLUGINS);
        //
        if (TempPath.exists()) {
            try {                    
                System.out.println("Move new plugin files");
                for (File F : TempPath.listFiles()) {
                    System.out.println(F.getCanonicalPath());
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get(KK_BASE_PLUGINPATH+F.getName()), REPLACE_EXISTING);
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
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get("./"+F.getName()), REPLACE_EXISTING);
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
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get(KK_BASE_CONFPATH+F.getName()), REPLACE_EXISTING);
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
                    Files.move(Paths.get(F.getCanonicalPath()), Paths.get(KK_BASE_CONFPATH+F.getName()), REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
