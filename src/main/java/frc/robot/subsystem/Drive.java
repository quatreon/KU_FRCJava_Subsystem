/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * Add your docs here.
 */
public class Drive {
    //Define ALL drive motors
    private PWMVictorSPX mRightMaster;
    private PWMVictorSPX mRightFollower1;
    private PWMVictorSPX mRightFollower2;
    private PWMVictorSPX mLeftMaster;
    private PWMVictorSPX mLeftFollower1;
    private PWMVictorSPX mLeftFollower2;

    private SpeedControllerGroup mRight;
    private SpeedControllerGroup mLeft;

    //Drive instance
    private static Drive m_Instance;

    private void setup(){
        mRightMaster = new PWMVictorSPX(Constants.mRightMasterId);
        mRightFollower1 = new PWMVictorSPX(Constants.mRightFollowerId1);
        mRightFollower2 = new PWMVictorSPX(Constants.mRightFollowerId2);
        mLeftMaster = new PWMVictorSPX(Constants.mLeftMasterId);
        mLeftFollower1 = new PWMVictorSPX(Constants.mLeftFollowerId1);
        mLeftFollower2 = new PWMVictorSPX(Constants.mLeftFollowerId2);

        mRight = new SpeedControllerGroup(mRightMaster, mRightFollower1, mRightFollower2);
        mLeft = new SpeedControllerGroup(mLeftMaster, mLeftFollower1, mLeftFollower2);
    }

    private Drive(){
        setup();
    }

    public static Drive getInstance(){
        if (m_Instance == null) {
            m_Instance = new Drive();
        }
        return m_Instance;
    }

    public void setSpeed(double right, double left){
        mRight.set(right);
        mLeft.set(left);
    }

    public void update(){
    }

}
