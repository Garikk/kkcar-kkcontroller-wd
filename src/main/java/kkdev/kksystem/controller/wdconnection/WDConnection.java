package kkdev.kksystem.controller.wdconnection;

import static java.lang.System.out;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author blinov_is
 */
public class WDConnection extends UnicastRemoteObject implements IKKWDConnection {

    @Override
    public WDSystemState GetWDState() {
        try {
            IKKWDConnection c = (IKKWDConnection) Naming.lookup("rmi://localhost/KKCarRMI");
            return c.GetWDState();
        } catch (NotBoundException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public WDSystemState SetWDState() {
        try {
            IKKWDConnection c = (IKKWDConnection) Naming.lookup("rmi://localhost/KKCarRMI");
            return c.SetWDState();
        } catch (NotBoundException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public WDConnection() throws RemoteException {
        System.setProperty("java.security.policy", "security.policy");
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            Naming.bind("rmi://localhost/KKCarRMI_WD", this);
        } catch (ConnectException ex) {
            out.println("!!NOT FOUND WD!!");
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WDConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
