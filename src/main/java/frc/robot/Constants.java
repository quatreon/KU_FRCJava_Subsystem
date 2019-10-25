/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Constants {

    //drive motors, PWM ports
    public static int mRightMasterId = 1;
    public static int mRightFollowerId1 = 2;
    public static int mRightFollowerId2 = 3;
    public static int mLeftMasterId = 4;
    public static int mLeftFollowerId1 = 5;
    public static int mLeftFollowerId2 = 6;

    //intake motor, PWM port
    public static int ballIntakeID = 9;

    //arm motors, PWM ports
    public static int rArmID1 = 7;
    public static int lArmID2 = 8;

    //Solenoids
    public static int hatch1 = 4;
    public static int hatch2 = 5;

    //Arm PID Values
    public static double aP = 0.004;
    public static double aI = 0.0001;
    public static double aD = 0.01;  

    //Digit Input Output
    public static int eArm1 = 2;
    public static int eArm2 = 3;

    //Arm SetPoint Values
    public static double sp_cargoShip = -85;
    public static double sp_ballPickup = 0;
    public static double sp_hatchPickup = -25;
    public static double sp_hatchLevel2 = -90;
}
