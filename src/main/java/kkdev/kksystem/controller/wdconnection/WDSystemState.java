/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.controller.wdconnection;

import static kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates.WD_SysState_ACTIVE;
import static kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates.WD_SysState_IDLE;
import static kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates.WD_SysState_NEEDRESTORE_EMERG;
import static kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates.WD_SysState_POWEROFF;
import static kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates.WD_SysState_REBOOT;
import static kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates.WD_SysState_REBOOT_KK;
import static kkdev.kksystem.controller.wdconnection.WDSystemState.WDStates.WD_SysState_SLEEP;

/**
 *
 * @author blinov_is
 */
public class WDSystemState {

    public  static enum WDStates {
        WD_SysState_ACTIVE, //engine active or extpower
        WD_SysState_IDLE, //engine stop security system inactive
        WD_SysState_POWEROFF, //full system shutdown
        WD_SysState_SLEEP, //security system active, sleep and monitoring state
        WD_SysState_REBOOT, //reboot system
        WD_SysState_REBOOT_KK, //reboot controller
        WD_SysState_NEEDRESTORE_EMERG  //reboot controller and emerg estore
    }

    public WDStates TargetState=WD_SysState_ACTIVE;     //Default - Active,
    public WDStates CurrentState=WD_SysState_ACTIVE;

    public int TargetSleepInterval;
    public int CurrentSleepInterval;
    
    public int DogWatchCounter=5;
    public int EmergencyCounter=5;
    public int WaitTimer=1;
    
    public static final int TIME_WAIT_FOR_RESTARTKK=15000;
    
    public static final int EMERG_COUNTER_NEEDRESTARTKK=4;
    public static final int EMERG_COUNTER_NEEDRESTARTKK_2=3;
    public static final int EMERG_COUNTER_NEEDRESTARTKK_3=2;
    public static final int EMERG_COUNTER_NEEDREBOOTSYS=1;
    public static final int EMERG_COUNTER_EMERGENCY=0;
    

    public byte GetTargetStateB() {
        return (GetByteState(TargetState));
    }

    public byte GetCurrentStateB() {
        return (GetByteState(CurrentState));
    }

    public static byte GetByteState(WDStates State) {
        switch (State) {
            case WD_SysState_ACTIVE:
                return 1;
            case WD_SysState_IDLE:
                return 2;
            case WD_SysState_POWEROFF:
                return 3;
            case WD_SysState_SLEEP:
                return 4;
            case WD_SysState_REBOOT:
                return 5;
            case WD_SysState_REBOOT_KK:
                return 6;
            case WD_SysState_NEEDRESTORE_EMERG:
                return 7;
            default:
                return 0;
        }
    }
    public static WDStates GetStateFromByte(int State)
    {
      switch (State) {
            case 1:
                return WD_SysState_ACTIVE;
            case 2:
                return WD_SysState_IDLE;
            case 3:
                return WD_SysState_POWEROFF;
            case 4:
                return WD_SysState_SLEEP;
            case 5:
                return WD_SysState_REBOOT;
            case 6:
                return WD_SysState_REBOOT_KK;
            case 7:
                return WD_SysState_NEEDRESTORE_EMERG;
            default:
                return WD_SysState_IDLE;
        }
    }
}
