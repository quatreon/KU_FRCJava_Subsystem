/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import frc.robot.subsystem.Drive;
import frc.robot.subsystem.Intake;
import frc.robot.subsystem.Arm;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.DriverStation;


/**
 * Add your docs here.
 */
public class OI {
    private Drive drive = Drive.getInstance();
    private Intake intake = Intake.getInstance();
    private Arm arm = Arm.getInstance();

    public OI(){
    }

    public void driverControl(Joystick driver_control){
        /*Drive control*/
        drive.setSpeed(driver_control.getRawAxis(5), driver_control.getRawAxis(1));

        /*Ball intake contol*/

        //Right trigger turns intake forward
        if(driver_control.getRawAxis(2) > 0.1){ 
            intake.setBallIntake(1);
         }
         //Left trigger turns intake backwards
         else if(driver_control.getRawAxis(3) > 0.1){ 
            intake.setBallIntake(-1);
         }
         else{
            intake.setBallIntake(0);  
         }
 
         /*Hatch control*/

         //Right bumper release hatch
         if(driver_control.getRawButton(5)){
            intake.setHatch(false);
         }
         //Left bumper retains hatch 
         else if(driver_control.getRawButton(6)){
            intake.setHatch(true);
         }

         //Match Timer - Alert Driver at 30 seconds, duration 1 second
         driverMatchAlert(driver_control, 30, 1);
     }

     public void operatorControl(Joystick operator_control){

        /*Arm Manual Control*/

        //Manual arm control, only run this if PID is disabled
        if(!arm.isArmPIDEnabled()) {
            if(operator_control.getRawAxis(1) > 0.1 || operator_control.getRawAxis(1) < -0.1){
                arm.setArmSpeed(operator_control.getRawAxis(1)*.5);
            }
            else{
                arm.setArmSpeed(0);  
            }
        }

        //Disable arm PID as soon a operator moves the left stick
        if(operator_control.getRawAxis(1) > 0.2 || operator_control.getRawAxis(1) < -0.2) 
              arm.disableArmPID();

        /*Arm Setpoints (PID) */

        //cargo cargoship, A
        if(operator_control.getRawButton(1)){
            arm.setArmPosition(Constants.sp_cargoShip);
        } 
        //ball pickup, B
        else if(operator_control.getRawButton(2)){
            arm.setArmPosition(Constants.sp_ballPickup);
        }
        //hatch pickup, Y
        else if(operator_control.getRawButton(4)){
            arm.setArmPosition(Constants.sp_hatchPickup);
        }
        //level two hatch, X
        else if(operator_control.getRawButton(3)) {
            arm.setArmPosition(Constants.sp_hatchLevel2);
        }
          
        /*Arm PID position adjust*/

        //Ajust arm up on right trigger
        if(operator_control.getRawButton(5)){ 
            arm.adjustArmPosition(3); //Add 3 degrees 
        }
        //Ajust arm down on left trigger
        else if(operator_control.getRawButton(6)){
            arm.adjustArmPosition(-3); //Subtract 3 degrees 
        }

        //Match Timer - Alert Driver at 30 seconds, duration 1 second
        driverMatchAlert(operator_control, 30, 1);
     }

     /*Function: Alerts Driver of Match time through JoyStick Rumble*/
     private void driverMatchAlert(Joystick joy, double alertTime, double duration){
        //Get Current Match Time
        double time = DriverStation.getInstance().getMatchTime();
    
        //Is it time to alert the driver?
        if(time >= alertTime && time <= alertTime+duration){
            //turn on rumble
            joy.setRumble(RumbleType.kLeftRumble, 1);
            joy.setRumble(RumbleType.kRightRumble, 1);
        }else{
            //turn off rumber
            joy.setRumble(RumbleType.kLeftRumble, 0);
            joy.setRumble(RumbleType.kRightRumble, 0);
        }
    }

}


