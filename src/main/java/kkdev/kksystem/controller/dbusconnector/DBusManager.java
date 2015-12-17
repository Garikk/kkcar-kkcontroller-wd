/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.dbusconnector;

import org.freedesktop.DBus.Properties;
import org.freedesktop.NetworkManagerIface;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;


/**
 *
 * @author sayma_000
 */
public abstract class DBusManager {
    static NetworkManagerIface nm;
    static DBusConnection conn;
    
    public static void Init() throws DBusException
    {
        conn = DBusConnection.getConnection(DBusConnection.SYSTEM);
		Properties nmProp;
		nmProp = (Properties)conn.getRemoteObject(NetworkManagerIface._NM_IFACE, 
				NetworkManagerIface._NM_PATH, 
				Properties.class);
		nm = (NetworkManagerIface)conn.getRemoteObject(NetworkManagerIface._NM_IFACE, 
				NetworkManagerIface._NM_PATH, 
				NetworkManagerIface.class);
		String version = nmProp.Get( NetworkManagerIface._NM_IFACE, "Version");
                
           System.out.println(version);
   
    }
    
    
    
    
    
}
