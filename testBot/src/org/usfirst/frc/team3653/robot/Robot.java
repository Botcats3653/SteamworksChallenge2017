
package org.usfirst.frc.team3653.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;
    Joystick stick1,stickRotate;
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    SendableChooser chooser;
    private ADXRS450_Gyro gyro;

    public Robot() {
        myRobot = new RobotDrive(0,1,2,3);
        myRobot.setExpiration(0.1);
        stick1 = new Joystick(0);
        stickRotate = new Joystick(1);
        gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    }
    
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto modes", chooser);
    }

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the if-else structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomous() {
    	String autoSelected = (String) chooser.getSelected();
//		String autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    	
    	switch(autoSelected) {
    	case customAuto:
            myRobot.setSafetyEnabled(false);
            myRobot.drive(-0.5, 1.0);	// spin at half speed
            Timer.delay(2.0);		//    for 2 seconds
            myRobot.drive(0.0, 0.0);	// stop robot
            break;
    	case defaultAuto:
    	default:
            myRobot.setSafetyEnabled(false);
            myRobot.drive(-0.5, 0.0);	// drive forwards half speed
            Timer.delay(2.0);		//    for 2 seconds
            myRobot.drive(0.0, 0.0);	// stop robot
            break;
    	}
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        double gyroAngle = gyro.getAngle();
        while (isOperatorControl() && isEnabled()) {
        	myRobot.mecanumDrive_Cartesian(stick1.getY(), stick1.getX(), stickRotate.getX(), gyroAngle);
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    	double gyroAngle = gyro.getAngle();
    	myRobot.mecanumDrive_Cartesian(stick1.getY(), stick1.getX(), stickRotate.getX(), gyroAngle);
    	gyro.reset();
    	while(isTest()) {
    		double angle = gyro.getAngle();
    		System.out.println(angle);
    		Timer.delay(1);
    	}
    }
}
