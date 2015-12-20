/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.dbusconnector;

import java.util.List;
import java.util.Map;
import org.freedesktop.DBus.Properties;
import org.freedesktop.NetworkManager.Constants.NM_DEVICE_TYPE;
import org.freedesktop.NetworkManagerIface;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.Variant;
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
                
           System.out.println("DBus ok");
           System.out.println("NetworkManager version..." + version);
   
    }
    
    public static void StartNetworkInterfce() throws DBusException
    {
        Activate3GModem();
    
    	
	nm.ActivateConnection(nm, null, null);
    }
    
    private static void Activate3GModem() throws DBusException
    {
       List<Path> devList = nm.GetDevices();
        for (Path devInterface : devList) {
                System.out.println(devInterface);
                Properties props = (Properties) conn.getRemoteObject(NetworkManagerIface._NM_IFACE, devInterface.getPath(),  Properties.class);

                Map<String, Variant> propsMap = props.GetAll(NetworkManagerIface._DEVICE_IFACE);
                UInt32 type = (UInt32)propsMap.get("DeviceType").getValue();
                System.out.println("DBus NM Dev type: " + type) ;
                if(type.equals(NM_DEVICE_TYPE.MODEM)){
                   DBusInterface Dev=(DBusInterface) conn.getRemoteObject(NetworkManagerIface._NM_IFACE, devInterface.getPath(),  DBusInterface.class);
                   nm.ActivateConnection(nm,Dev, null);
                   return;
                }
        }
    }
    
    
    
}
