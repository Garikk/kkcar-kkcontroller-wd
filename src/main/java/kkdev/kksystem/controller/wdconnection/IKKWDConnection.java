/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.wdconnection;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author blinov_is
 */
public interface IKKWDConnection extends Remote { 
   public WDSystemState GetWDState() throws RemoteException;
    public WDSystemState SetWDState() throws RemoteException;

    
}
