/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Add your docs here.
 */
public class Arm {
    private PWMVictorSPX mRTArm;
    private PWMVictorSPX mLTArm;
    private SpeedControllerGroup mArm;

    private Encoder eArm;
    private PIDController aPidController;

    private static Arm m_Instance;

    private void setup(){
        //initialize the motor
        mRTArm = new PWMVictorSPX(Constants.rArmID1);
        mLTArm = new PWMVictorSPX(Constants.lArmID2);
        mArm = new SpeedControllerGroup(mRTArm, mLTArm);
        
        //initialize the Encorder
        eArm = new Encoder(Constants.eArm1, Constants.eArm2, true, Encoder.EncodingType.k4X);
        eArm.reset();
        eArm.setPIDSourceType(PIDSourceType.kDisplacement); //position or rate
        eArm.setDistancePerPulse(360.0/1024.0); //set measurment to degrees - 360 degrees/1024 encoder ticks 

        //initialize PID 
        aPidController = new PIDController(Constants.aP, Constants.aI, Constants.aD, eArm, mArm); //PID values, sensor value, output motors
        aPidController.setOutputRange(-0.5, 0.25);  //limit min and max speed of motors
        aPidController.setInputRange(-160, 0);      //limit motion range from 0 to -160 degrees

        //start with PID disable
        aPidController.disable();
    }

    private Arm(){
        setup();
    }

    public static Arm getInstance(){
        if (m_Instance == null){
            m_Instance = new Arm();
        }
        return m_Instance;
    }

    //set manual arm speed
    public void setManualArmSpeed(double speed){ 
        mArm.set(speed); 
    }

    //Set arm position using PID
    public void setArmPosition(double Distance){
        aPidController.enable();
        aPidController.setSetpoint(Distance);
    }

    //check Arm PID is Enable
    public boolean isArmPIDEnabld(){
        return aPidController.isEnabled();
    }

    //disable Arm PID
    public void disableArmPID(){
        if(aPidController.isEnabled()) aPidController.disable();
    }

    public void adjustArmPosition(double adjust){
        double newPosition = aPidController.getSetpoint() + adjust;
        aPidController.setSetpoint(newPosition);
    }

    //reset the encoder
    public void armEncoderReset(){
        eArm.reset();
    }

    /* defense mode, stay in package 
        set arm to lowest position and keep it in position 
        by setting motor to minimum power */
    public void defenseMode(boolean mSwitch){
        if(mSwitch){
            setArmPosition(0);
            disableArmPID();
            setManualArmSpeed(0.05);
        }
    }

    public void update(){
        SmartDashboard.putNumber("ArmEncoder Distance", eArm.getDistance());
        SmartDashboard.putNumber("ArmEncoder Rate", eArm.getRate());      
    }


}
