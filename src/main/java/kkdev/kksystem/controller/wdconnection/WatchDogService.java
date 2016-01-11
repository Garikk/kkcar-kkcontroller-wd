package kkdev.kksystem.controller.wdconnection;

import kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author blinov_is
 */
public class WatchDogService  {
    WatchDogUDPConnection UDC;
    WDSystemState CurrentSystemState;
    private static WatchDogService WDS;
    
    public static WatchDogService getInstance()
    {
        if (WDS==null)
            WDS=new WatchDogService();
        
        return WDS;
    }

    public void StartWDS()
    {
        if (UDC==null)
        {
            UDC=new WatchDogUDPConnection();
        }
        UDC.StartUDPServer();
    }
    public void StopWDS()
    {
        if (UDC!=null)
            UDC.Stop();
    }
    
      
    public synchronized WDSystemState getCurrentSystemState()
    {
        if (CurrentSystemState==null)
            CurrentSystemState=new WDSystemState();
            
        return CurrentSystemState;
    }
    
    
    public synchronized boolean CheckWatchDog()
    {
        if (CurrentSystemState.DogWatchCounter>0)
            CurrentSystemState.DogWatchCounter--;
        else
            RestoreCommand();
        
        return CurrentSystemState.DogWatchCounter==0;
    }
    
    public void WatchDogOk(byte CurrentState,byte TargetState)
    {
        ChangeWDStateTarget(WDSystemState.GetStateFromByte(TargetState));
        CurrentSystemState.DogWatchCounter=5;
        CurrentSystemState.EmergencyCounter=5;
    }
    
    private  void RestoreCommand()
    {
        if (    CurrentSystemState.CurrentState!=WDSystemState.WDStates.WD_SysState_NEEDRESTORE_EMERG && 
                CurrentSystemState.CurrentState!=WDSystemState.WDStates.WD_SysState_REBOOT_KK
                )
        {
            if (CurrentSystemState.EmergencyCounter>WDSystemState.EMERG_COUNTER_NEEDREBOOTSYS)
            {
                ChangeWDStateTarget(WDStates.WD_SysState_REBOOT_KK);
            }
            else if (CurrentSystemState.EmergencyCounter==WDSystemState.EMERG_COUNTER_NEEDREBOOTSYS)
            {
                ChangeWDStateTarget(WDStates.WD_SysState_REBOOT);
            }
            else if (CurrentSystemState.EmergencyCounter==WDSystemState.EMERG_COUNTER_EMERGENCY)
            {
             ChangeWDStateTarget(WDStates.WD_SysState_NEEDRESTORE_EMERG);
            }
        }
    }
            
    
    private void ChangeWDStateTarget(WDStates NewState)
    {
        if (NewState!=CurrentSystemState.TargetState)
        {
            System.out.println("TARGET STATE: " +NewState);
            CurrentSystemState.TargetState=NewState;
        }
    }
    
    public void ChangeWDStateCurrent(WDStates NewState)
    {
        if (NewState!=CurrentSystemState.CurrentState)
        {
            System.out.println("CURRENT STATE: " +NewState);
            CurrentSystemState.CurrentState=NewState;
        }
    }
}
