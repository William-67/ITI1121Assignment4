package SEG2106.core;

//%% NEW FILE newTrafficLight BEGINS HERE %%

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 2 "model.ump"
public class newTrafficLight implements EventHandler
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //newTrafficLight State Machines
    public enum Mode { low, moderate, high }
    public enum ModeLow { Null, status }
    public enum ModeLowStatus { Null, northAndSouthLeftGreen, northAndSouthGreen, northAndSouthYellow, northAndSouthRed, westAndEastYellow }
    public enum ModeModerate { Null, status }
    public enum ModeModerateStatus { Null, northAndLeftGreen, northSideYellow, northSideRed, southSideYellow, southSideRed, westAndEastYellow }
    public enum ModeHigh { Null, status }
    public enum ModeHighStatus { Null, northAndLeftGreen, northSideYellow, northSideRed, southSideYellow, southSideRed, westAndEastGreen, westAndEastYellow }
    private Mode mode;
    private ModeLow modeLow;
    private ModeLowStatus modeLowStatus;
    private ModeModerate modeModerate;
    private ModeModerateStatus modeModerateStatus;
    private ModeHigh modeHigh;
    private ModeHighStatus modeHighStatus;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    private TrafficLightManager trafficLightManager;
    public newTrafficLight(TrafficLightManager trafficLightManager)
    {
        this.trafficLightManager = trafficLightManager;

        setModeLow(ModeLow.Null);
        setModeLowStatus(ModeLowStatus.Null);
        setModeModerate(ModeModerate.Null);
        setModeModerateStatus(ModeModerateStatus.Null);
        setModeHigh(ModeHigh.Null);
        setModeHighStatus(ModeHighStatus.Null);
        setMode(Mode.low);

        trafficLightManager.addEventHandler(this);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public String getModeFullName()
    {
        String answer = mode.toString();
        if (modeLow != ModeLow.Null) { answer += "." + modeLow.toString(); }
        if (modeLowStatus != ModeLowStatus.Null) { answer += "." + modeLowStatus.toString(); }
        if (modeModerate != ModeModerate.Null) { answer += "." + modeModerate.toString(); }
        if (modeModerateStatus != ModeModerateStatus.Null) { answer += "." + modeModerateStatus.toString(); }
        if (modeHigh != ModeHigh.Null) { answer += "." + modeHigh.toString(); }
        if (modeHighStatus != ModeHighStatus.Null) { answer += "." + modeHighStatus.toString(); }
        return answer;
    }

    public Mode getMode()
    {
        return mode;
    }

    public ModeLow getModeLow()
    {
        return modeLow;
    }

    public ModeLowStatus getModeLowStatus()
    {
        return modeLowStatus;
    }

    public ModeModerate getModeModerate()
    {
        return modeModerate;
    }

    public ModeModerateStatus getModeModerateStatus()
    {
        return modeModerateStatus;
    }

    public ModeHigh getModeHigh()
    {
        return modeHigh;
    }

    public ModeHighStatus getModeHighStatus()
    {
        return modeHighStatus;
    }

    public boolean moderateTraffic()
    {
        boolean wasEventProcessed = false;

        Mode aMode = mode;
        switch (aMode)
        {
            case low:
                exitMode();
                setMode(Mode.moderate);
                wasEventProcessed = true;
                break;
            case high:
                exitMode();
                setMode(Mode.moderate);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean highTraffic()
    {
        boolean wasEventProcessed = false;

        Mode aMode = mode;
        switch (aMode)
        {
            case low:
                exitMode();
                setMode(Mode.high);
                wasEventProcessed = true;
                break;
            case moderate:
                exitMode();
                setMode(Mode.high);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean lowTraffic()
    {
        boolean wasEventProcessed = false;

        Mode aMode = mode;
        switch (aMode)
        {
            case moderate:
                exitMode();
                setMode(Mode.low);
                wasEventProcessed = true;
                break;
            case high:
                exitMode();
                setMode(Mode.low);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean timerGreen()
    {
        boolean wasEventProcessed = false;

        ModeLowStatus aModeLowStatus = modeLowStatus;
        ModeModerateStatus aModeModerateStatus = modeModerateStatus;
        ModeHighStatus aModeHighStatus = modeHighStatus;
        switch (aModeLowStatus)
        {
            case northAndSouthLeftGreen:
                exitModeLowStatus();
                setModeLowStatus(ModeLowStatus.northAndSouthGreen);
                wasEventProcessed = true;
                break;
            case northAndSouthGreen:
                exitModeLowStatus();
                setModeLowStatus(ModeLowStatus.northAndSouthYellow);
                wasEventProcessed = true;
                break;
            case northAndSouthRed:
                exitModeLowStatus();
                setModeLowStatus(ModeLowStatus.westAndEastYellow);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        switch (aModeModerateStatus)
        {
            case northAndLeftGreen:
                exitModeModerateStatus();
                setModeModerateStatus(ModeModerateStatus.northSideYellow);
                wasEventProcessed = true;
                break;
            case northSideRed:
                exitModeModerateStatus();
                setModeModerateStatus(ModeModerateStatus.southSideYellow);
                wasEventProcessed = true;
                break;
            case southSideRed:
                exitMode();
                setModeLowStatus(ModeLowStatus.westAndEastYellow);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        switch (aModeHighStatus)
        {
            case northAndLeftGreen:
                exitMode();
                setModeModerateStatus(ModeModerateStatus.northSideYellow);
                wasEventProcessed = true;
                break;
            case northSideRed:
                exitMode();
                setModeModerateStatus(ModeModerateStatus.southSideYellow);
                wasEventProcessed = true;
                break;
            case southSideRed:
                exitModeHighStatus();
                setModeHighStatus(ModeHighStatus.westAndEastGreen);
                wasEventProcessed = true;
                break;
            case westAndEastGreen:
                exitMode();
                setModeLowStatus(ModeLowStatus.westAndEastYellow);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    public boolean timerYellow()
    {
        boolean wasEventProcessed = false;

        ModeLowStatus aModeLowStatus = modeLowStatus;
        ModeModerateStatus aModeModerateStatus = modeModerateStatus;
        ModeHighStatus aModeHighStatus = modeHighStatus;
        switch (aModeLowStatus)
        {
            case northAndSouthYellow:
                exitModeLowStatus();
                setModeLowStatus(ModeLowStatus.northAndSouthRed);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        switch (aModeModerateStatus)
        {
            case northSideYellow:
                exitModeModerateStatus();
                setModeModerateStatus(ModeModerateStatus.northSideRed);
                wasEventProcessed = true;
                break;
            case southSideYellow:
                exitModeModerateStatus();
                setModeModerateStatus(ModeModerateStatus.southSideRed);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        switch (aModeHighStatus)
        {
            case northSideYellow:
                exitMode();
                setModeModerateStatus(ModeModerateStatus.northSideRed);
                wasEventProcessed = true;
                break;
            case southSideYellow:
                exitMode();
                setModeModerateStatus(ModeModerateStatus.southSideRed);
                wasEventProcessed = true;
                break;
            default:
                // Other states do respond to this event
        }

        return wasEventProcessed;
    }

    private void exitMode()
    {
        switch(mode)
        {
            case low:
                exitModeLow();
                break;
            case moderate:
                exitModeModerate();
                break;
            case high:
                exitModeHigh();
                break;
        }
    }

    private void setMode(Mode aMode)
    {
        mode = aMode;

        // entry actions and do activities
        switch(mode)
        {
            case low:
                if (modeLow == ModeLow.Null) { setModeLow(ModeLow.status); }
                break;
            case moderate:
                if (modeModerate == ModeModerate.Null) { setModeModerate(ModeModerate.status); }
                break;
            case high:
                if (modeHigh == ModeHigh.Null) { setModeHigh(ModeHigh.status); }
                break;
        }
    }

    private void exitModeLow()
    {
        switch(modeLow)
        {
            case status:
                exitModeLowStatus();
                setModeLow(ModeLow.Null);
                break;
        }
    }

    private void setModeLow(ModeLow aModeLow)
    {
        modeLow = aModeLow;
        if (mode != Mode.low && aModeLow != ModeLow.Null) { setMode(Mode.low); }

        // entry actions and do activities
        switch(modeLow)
        {
            case status:
                if (modeLowStatus == ModeLowStatus.Null) { setModeLowStatus(ModeLowStatus.northAndSouthLeftGreen); }
                break;
        }
    }

    private void exitModeLowStatus()
    {
        switch(modeLowStatus)
        {
            case northAndSouthLeftGreen:
                setModeLowStatus(ModeLowStatus.Null);
                break;
            case northAndSouthGreen:
                setModeLowStatus(ModeLowStatus.Null);
                break;
            case northAndSouthYellow:
                setModeLowStatus(ModeLowStatus.Null);
                break;
            case northAndSouthRed:
                setModeLowStatus(ModeLowStatus.Null);
                break;
            case westAndEastYellow:
                setModeLowStatus(ModeLowStatus.Null);
                break;
        }
    }

    private void setModeLowStatus(ModeLowStatus aModeLowStatus)
    {
        modeLowStatus = aModeLowStatus;
        if (modeLow != ModeLow.status && aModeLowStatus != ModeLowStatus.Null) { setModeLow(ModeLow.status); }

        // entry actions and do activities
        switch(modeLowStatus)
        {
            case northAndSouthLeftGreen:
                // line 8 "model.ump"
                trafficLightManager.northArrow();
                // line 9 "model.ump"
                trafficLightManager.southArrow();
                // line 10 "model.ump"
                trafficLightManager.westRed();
                // line 11 "model.ump"
                trafficLightManager.eastRed();
                break;
            case northAndSouthGreen:
                // line 15 "model.ump"
                trafficLightManager.northGreen();
                // line 16 "model.ump"
                trafficLightManager.southGreen();
                // line 17 "model.ump"
                trafficLightManager.westRed();
                // line 18 "model.ump"
                trafficLightManager.eastRed();
                break;
            case northAndSouthYellow:
                // line 22 "model.ump"
                trafficLightManager.northYellow();
                // line 23 "model.ump"
                trafficLightManager.southYellow();
                // line 24 "model.ump"
                trafficLightManager.westRed();
                // line 25 "model.ump"
                trafficLightManager.eastRed();
                break;
            case northAndSouthRed:
                // line 29 "model.ump"
                trafficLightManager.northRed();
                // line 30 "model.ump"
                trafficLightManager.southRed();
                // line 31 "model.ump"
                trafficLightManager.westGreen();
                // line 32 "model.ump"
                trafficLightManager.eastGreen();
                break;
            case westAndEastYellow:
                // line 36 "model.ump"
                trafficLightManager.northRed();
                // line 37 "model.ump"
                trafficLightManager.southRed();
                // line 38 "model.ump"
                trafficLightManager.westYellow();
                // line 39 "model.ump"
                trafficLightManager.eastYellow();
                break;
        }
    }

    private void exitModeModerate()
    {
        switch(modeModerate)
        {
            case status:
                exitModeModerateStatus();
                setModeModerate(ModeModerate.Null);
                break;
        }
    }

    private void setModeModerate(ModeModerate aModeModerate)
    {
        modeModerate = aModeModerate;
        if (mode != Mode.moderate && aModeModerate != ModeModerate.Null) { setMode(Mode.moderate); }

        // entry actions and do activities
        switch(modeModerate)
        {
            case status:
                if (modeModerateStatus == ModeModerateStatus.Null) { setModeModerateStatus(ModeModerateStatus.northAndLeftGreen); }
                break;
        }
    }

    private void exitModeModerateStatus()
    {
        switch(modeModerateStatus)
        {
            case northAndLeftGreen:
                setModeModerateStatus(ModeModerateStatus.Null);
                break;
            case northSideYellow:
                setModeModerateStatus(ModeModerateStatus.Null);
                break;
            case northSideRed:
                setModeModerateStatus(ModeModerateStatus.Null);
                break;
            case southSideYellow:
                setModeModerateStatus(ModeModerateStatus.Null);
                break;
            case southSideRed:
                setModeModerateStatus(ModeModerateStatus.Null);
                break;
            case westAndEastYellow:
                setModeModerateStatus(ModeModerateStatus.Null);
                break;
        }
    }

    private void setModeModerateStatus(ModeModerateStatus aModeModerateStatus)
    {
        modeModerateStatus = aModeModerateStatus;
        if (modeModerate != ModeModerate.status && aModeModerateStatus != ModeModerateStatus.Null) { setModeModerate(ModeModerate.status); }

        // entry actions and do activities
        switch(modeModerateStatus)
        {
            case northAndLeftGreen:
                // line 49 "model.ump"
                trafficLightManager.northGreen();
                // line 50 "model.ump"
                trafficLightManager.northArrow();
                // line 51 "model.ump"
                trafficLightManager.westRed();
                // line 52 "model.ump"
                trafficLightManager.eastRed();
                // line 53 "model.ump"
                trafficLightManager.southRed();
                break;
            case northSideYellow:
                // line 57 "model.ump"
                trafficLightManager.northYellow();
                // line 58 "model.ump"
                trafficLightManager.westRed();
                // line 59 "model.ump"
                trafficLightManager.eastRed();
                // line 60 "model.ump"
                trafficLightManager.southRed();
                break;
            case northSideRed:
                // line 64 "model.ump"
                trafficLightManager.northRed();
                // line 65 "model.ump"
                trafficLightManager.southGreen();
                // line 66 "model.ump"
                trafficLightManager.southArrow();
                // line 67 "model.ump"
                trafficLightManager.westRed();
                // line 68 "model.ump"
                trafficLightManager.eastRed();
                break;
            case southSideYellow:
                // line 72 "model.ump"
                trafficLightManager.northYellow();
                // line 73 "model.ump"
                trafficLightManager.northRed();
                // line 74 "model.ump"
                trafficLightManager.westRed();
                // line 75 "model.ump"
                trafficLightManager.eastRed();
                break;
            case southSideRed:
                // line 79 "model.ump"
                trafficLightManager.southRed();
                // line 80 "model.ump"
                trafficLightManager.northRed();
                // line 81 "model.ump"
                trafficLightManager.westGreen();
                // line 82 "model.ump"
                trafficLightManager.eastGreen();
                break;
            case westAndEastYellow:
                // line 86 "model.ump"
                trafficLightManager.northRed();
                // line 87 "model.ump"
                trafficLightManager.southRed();
                // line 88 "model.ump"
                trafficLightManager.westYellow();
                // line 89 "model.ump"
                trafficLightManager.eastYellow();
                break;
        }
    }

    private void exitModeHigh()
    {
        switch(modeHigh)
        {
            case status:
                exitModeHighStatus();
                setModeHigh(ModeHigh.Null);
                break;
        }
    }

    private void setModeHigh(ModeHigh aModeHigh)
    {
        modeHigh = aModeHigh;
        if (mode != Mode.high && aModeHigh != ModeHigh.Null) { setMode(Mode.high); }

        // entry actions and do activities
        switch(modeHigh)
        {
            case status:
                if (modeHighStatus == ModeHighStatus.Null) { setModeHighStatus(ModeHighStatus.northAndLeftGreen); }
                break;
        }
    }

    private void exitModeHighStatus()
    {
        switch(modeHighStatus)
        {
            case northAndLeftGreen:
                setModeHighStatus(ModeHighStatus.Null);
                break;
            case northSideYellow:
                setModeHighStatus(ModeHighStatus.Null);
                break;
            case northSideRed:
                setModeHighStatus(ModeHighStatus.Null);
                break;
            case southSideYellow:
                setModeHighStatus(ModeHighStatus.Null);
                break;
            case southSideRed:
                setModeHighStatus(ModeHighStatus.Null);
                break;
            case westAndEastGreen:
                setModeHighStatus(ModeHighStatus.Null);
                break;
            case westAndEastYellow:
                setModeHighStatus(ModeHighStatus.Null);
                break;
        }
    }

    private void setModeHighStatus(ModeHighStatus aModeHighStatus)
    {
        modeHighStatus = aModeHighStatus;
        if (modeHigh != ModeHigh.status && aModeHighStatus != ModeHighStatus.Null) { setModeHigh(ModeHigh.status); }

        // entry actions and do activities
        switch(modeHighStatus)
        {
            case northAndLeftGreen:
                // line 99 "model.ump"
                trafficLightManager.northGreen();
                // line 100 "model.ump"
                trafficLightManager.northArrow();
                // line 101 "model.ump"
                trafficLightManager.westRed();
                // line 102 "model.ump"
                trafficLightManager.eastRed();
                // line 103 "model.ump"
                trafficLightManager.southRed();
                break;
            case northSideYellow:
                // line 107 "model.ump"
                trafficLightManager.northYellow();
                // line 108 "model.ump"
                trafficLightManager.westRed();
                // line 109 "model.ump"
                trafficLightManager.eastRed();
                // line 110 "model.ump"
                trafficLightManager.southRed();
                break;
            case northSideRed:
                // line 114 "model.ump"
                trafficLightManager.northRed();
                // line 115 "model.ump"
                trafficLightManager.southGreen();
                // line 116 "model.ump"
                trafficLightManager.southArrow();
                // line 117 "model.ump"
                trafficLightManager.westRed();
                // line 118 "model.ump"
                trafficLightManager.eastRed();
                break;
            case southSideYellow:
                // line 122 "model.ump"
                trafficLightManager.northYellow();
                // line 123 "model.ump"
                trafficLightManager.northRed();
                // line 124 "model.ump"
                trafficLightManager.westRed();
                // line 125 "model.ump"
                trafficLightManager.eastRed();
                break;
            case southSideRed:
                // line 129 "model.ump"
                trafficLightManager.southRed();
                // line 130 "model.ump"
                trafficLightManager.northRed();
                // line 131 "model.ump"
                trafficLightManager.westGreen();
                // line 132 "model.ump"
                trafficLightManager.westArrow();
                // line 133 "model.ump"
                trafficLightManager.eastRed();
                break;
            case westAndEastGreen:
                // line 137 "model.ump"
                trafficLightManager.northRed();
                // line 138 "model.ump"
                trafficLightManager.southRed();
                // line 139 "model.ump"
                trafficLightManager.westGreen();
                // line 140 "model.ump"
                trafficLightManager.eastGreen();
                break;
            case westAndEastYellow:
                // line 144 "model.ump"
                trafficLightManager.northRed();
                // line 145 "model.ump"
                trafficLightManager.southRed();
                // line 146 "model.ump"
                trafficLightManager.westYellow();
                // line 147 "model.ump"
                trafficLightManager.eastYellow();
                break;
        }
    }

    public void delete()
    {}

}
