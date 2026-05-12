

import com.phidget22.*;
import javax.swing.*;
import java.awt.*;



public class TurningSelfDrive extends JPanel {

    static int front = 500;
    static int back = 500;
    
    public static void main(String[] args) throws Exception {

        //connect and setup
        Net.addServer("", "192.168.100.1", 5661, "", 0);

        DistanceSensor frontsonar = new DistanceSensor();
        DistanceSensor backsonar = new DistanceSensor();
     
        
        frontsonar.setHubPort(4);
        backsonar.setHubPort(3);
        
        
     
        

        frontsonar.open(5000);
        backsonar.open(5000);
        
        //Create
        DCMotor leftMotors = new DCMotor();
        DCMotor rightMotors = new DCMotor();
        VoltageRatioInput vAxis = new VoltageRatioInput(); 
        VoltageRatioInput hAxis = new VoltageRatioInput(); 

        //Address
        leftMotors.setChannel(0);
        rightMotors.setChannel(1);
        vAxis.setChannel(0);
        hAxis.setChannel(1);
       

        //Open
        leftMotors.open(5000);
        rightMotors.open(5000);
        vAxis.open(5000);
        hAxis.open(5000);


        //Increase acceleration
        leftMotors.setAcceleration(leftMotors.getMaxAcceleration());
        rightMotors.setAcceleration(rightMotors.getMaxAcceleration());
        
        

        // window init
        JFrame frame = new JFrame("Rover pos back and front");
        SelfDrive panel = new SelfDrive();

        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        
        
        while (true) {
            front = frontsonar.getDistance();
            back = backsonar.getDistance();
            
            if (front < 250) {
            	 leftMotors.setTargetVelocity(0.25);
                 rightMotors.setTargetVelocity(-0.5);

                
                 Thread.sleep(1000);

                 //Stop motors
                 leftMotors.setTargetVelocity(0);
                 rightMotors.setTargetVelocity(0);
            }
            //Get data from vertical axis (value between -1 and 1 and convert to -0.5 to 0.5)
            double verticalAxis = vAxis.getVoltageRatio()/2;
            double horizontalAxis = hAxis.getVoltageRatio()/2;
           
            
            //Use Thumbstick position set motor controller target velocity
            leftMotors.setTargetVelocity(verticalAxis - horizontalAxis);
            rightMotors.setTargetVelocity(verticalAxis + horizontalAxis);
           
            
            panel.repaint();

            Thread.sleep(100);
        }
    }
   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int midX = getWidth() / 2;

        // scale mm to pix
        int scale = 5;

        int frontWallY = midX - (front / scale);
        int backWallY  = midX + (back / scale);

        // bg
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // rc
        g.setColor(Color.YELLOW);
        g.fillOval(midX - 10, midX - 10, 20, 20);

        // front
        g.setColor(Color.RED);
        g.fillRect(50, frontWallY, getWidth() - 100, 10);

        // back
        g.setColor(Color.BLUE);
        g.fillRect(50, backWallY, getWidth() - 100, 10);

        // labels
        g.setColor(Color.WHITE);
        g.drawString("Front: " + front + " mm", 10, 20);
        g.drawString("Back: " + back + " mm", 10, 40);
    }
}