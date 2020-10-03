/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
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
    private boolean isArmPIDEnabled = false;
    private double armSetPoint;

    private void setup(){
        //initialize the motor
        mRTArm = new PWMVictorSPX(Constants.rArmID1);
        mLTArm = new PWMVictorSPX(Constants.lArmID2);
        mArm = new SpeedControllerGroup(mRTArm, mLTArm);
        
        //initialize the Encorder
        eArm = new Encoder(Constants.eArm1, Constants.eArm2, true, Encoder.EncodingType.k4X);
        eArm.reset();
        eArm.setDistancePerPulse(360.0/1024.0); //set measurment to degrees - 360 degrees/1024 encoder ticks 

        //initialize PID 
        aPidController = new PIDController(Constants.aP, Constants.aI, Constants.aD); //PID values, sensor value, output motors
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

    //set arm speed
    public void setArmSpeed(double speed){ 
        mArm.set(speed); 
    }

    //Set arm position using PID
    public void setArmPosition(double distance){
        enableArmPID();
        armSetPoint = distance;
    }

    public void adjustArmPosition(double adjust){
        armSetPoint = armSetPoint + adjust;
    }

    //enable PID
    public void enableArmPID(){
        isArmPIDEnabled = true;
    }

    //disable PID
    public void disableArmPID(){
        isArmPIDEnabled = false;
    }

    //check if PID is enabled
    public boolean isArmPIDEnabled(){
        return isArmPIDEnabled;
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
            if(eArm.getDistance() > 5){
                setArmPosition(0);
            }else{
                setArmSpeed(0.05);
            }
        }
    }

    public void update(){
        SmartDashboard.putNumber("ArmEncoder Distance", eArm.getDistance());
        SmartDashboard.putNumber("ArmEncoder Rate", eArm.getRate());     
    
        //ARM PID 
        if(isArmPIDEnabled()){
            double pid_output = aPidController.calculate(eArm.getDistance(), armSetPoint);
            setArmSpeed(pid_output);
        }
    }


}
