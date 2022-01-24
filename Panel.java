import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Panel extends JPanel{

    //deltatime ensures that the simulation runs independent of frame rate
    double deltaTime = 0;

    //stores all pairs of balls that are colliding
    ArrayList<Ball[]> collidingBalls = new ArrayList<Ball[]>();

    //two text boxes used to display average kinetic energy of each side
    Text leftText = new Text("Average KE:", 400, 875);
    Text rightText = new Text("Average KE:", 800, 875);

    //constructor, parameters are width and height
    Panel(int x, int y){

        this.setPreferredSize(new Dimension(x,y));
    }

    //rotates a vector, used to calculate bouncing off walls
    public static double[] rotate(double xI, double yI, double radians){

        double x = xI * Math.cos(radians) - yI * Math.sin(radians);
        double y = xI * Math.sin(radians) - yI * Math.cos(radians);
        return new double[]{x, y};
    }

    //draws to the panel each frame
    public void paint(Graphics g) {

        //first calculation of deltatime
        long frameStart = System.nanoTime();   

        //sets variables required for the panel to work
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));

        //resets the list of all colliding balls
        collidingBalls.clear();
        
        //main loop for all balls
        for(Ball ball : Ball.balls){
            ball.draw(g2d);
            ball.move(deltaTime);

            //colors the ball based on its KE relative to the average
            if(ball.getKE() > Ball.getAverageKE()){
                ball.setColor(java.awt.Color.RED);
            }
            else{
                ball.setColor(java.awt.Color.BLUE);
            }

            //tests for wall collisions
            for(Wall wall : Wall.walls){
                
                //checks each point on the wall to determine if a collision has occured
                for(double[] point : wall.points){

                    if(ball.distanceTo(point) <= ball.getRadius()){

                        //static collision
                        double distance = ball.distanceTo(point);
                        double overlap = ball.getRadius() - distance;
                        ball.setX(ball.getX() + overlap * ((ball.getX() - point[0]) / distance));
                        ball.setY(ball.getY() + overlap * ((ball.getY() - point[1]) / distance));

                        //bounce
                        double[] rotatedVelocity = rotate(ball.getVX(), ball.getVY(), -wall.getAngle());
                        rotatedVelocity[1] *= -1;
                        rotatedVelocity = rotate(rotatedVelocity[0], rotatedVelocity[1], wall.getAngle());
                        ball.setVX(rotatedVelocity[0]);
                        ball.setVY(rotatedVelocity[1]);

                    }
                }
            }
        
            //tests for gate interactions
            for(Gate gate : Gate.gates){

                for(double[] point : gate.points){

                    if(ball.distanceTo(point) <= ball.getRadius()){

                        //only lets balls of high KE to the left side and balls of low KE to the right side (somewhat glitchy, but functional)
                        if((ball.getKE() > Ball.getAverageKE() && ball.getX() > point[0]) || (ball.getKE() < Ball.getAverageKE() && ball.getX() < point[0])){
                            System.out.println("success");
                        }
                        else{

                            //static collision
                            double distance = ball.distanceTo(point);
                            double overlap = ball.getRadius() - distance;
                            ball.setX(ball.getX() + overlap * ((ball.getX() - point[0]) / distance));
                            ball.setY(ball.getY() + overlap * ((ball.getY() - point[1]) / distance));

                            //bounce
                            double[] rotatedVelocity = rotate(ball.getVX(), ball.getVY(), -gate.getAngle());
                            rotatedVelocity[1] *= -1;
                            rotatedVelocity = rotate(rotatedVelocity[0], rotatedVelocity[1], gate.getAngle());
                            ball.setVX(rotatedVelocity[0]);
                            ball.setVY(rotatedVelocity[1]);

                        }
                    }
                }
            }

            //checks for collisions with other balls
            for(Ball ball2 : Ball.balls){
                if(ball == ball2){continue;}
                if(ball.distanceTo(ball2) <= ball.getRadius() + ball2.getRadius()){

                    Ball[] pair = {ball, ball2};
                    collidingBalls.add(pair);

                    //static collision
                    double distance = ball.distanceTo(ball2);
                    double overlap = ball.getRadius() + ball2.getRadius() - distance;
                    ball.setX(ball.getX() + (overlap) * ((ball.getX() - ball2.getX()) / distance));
                    ball.setY(ball.getY() + (overlap) * ((ball.getY() - ball2.getY()) / distance));
                    ball2.setX(ball2.getX() + (overlap) * ((ball2.getX() - ball.getX()) / distance));
                    ball2.setY(ball2.getY() + (overlap) * ((ball2.getY() - ball.getY()) / distance));
                           
                }
            }
        } 

        for(Ball[] pair : collidingBalls){
            Ball ball1 = pair[0];
            Ball ball2 = pair[1];
            double distance = ball1.distanceTo(ball2);

            //dynamic collision code 

            //normal vector between the two balls
            double nx = (ball2.getX() - ball1.getX()) / distance;
            double ny = (ball2.getY() - ball1.getY()) / distance;

            //tangent vector
            double tx = -ny;
            double ty = nx;

            //dot product tangential vector (dot product of balls velocity and tangent vector)
            double dotTanBall1 = ball1.getVX() * tx + ball1.getVY() * ty;
            double dotTanBall2 = ball2.getVX() * tx + ball2.getVY() * ty;

            //dot product normal vector (dot product of balls velocity and normal vector)
            double dotNorBall1 = ball1.getVX() * nx + ball1.getVY() * ny;
            double dotNorBall2 = ball2.getVX() * nx + ball2.getVY() * ny;

            //conservation of momentum
            double m1 = (dotNorBall1 * (ball1.getMass() - ball2.getMass()) + 2.0 * ball2.getMass() * dotNorBall2) / (ball1.getMass() + ball2.getMass());
			double m2 = (dotNorBall2 * (ball2.getMass() -ball1.getMass()) + 2.0 * ball1.getMass() * dotNorBall1) / (ball1.getMass() + ball2.getMass());

            //update velocities
            ball1.setVX(dotTanBall1 * tx + nx * m1);
            ball1.setVY(dotTanBall1 * ty + ny * m1);
            ball2.setVX(dotTanBall2 * tx + nx * m2);
            ball2.setVY(dotTanBall2 * ty + ny * m2);

        }
        
        //draws walls
        for(Wall wall : Wall.walls){
            //wall.drawPoints(g2d);
            wall.draw(g2d);
        }

        //draws gates
        for(Gate gate : Gate.gates){
            //gate.drawPoints(g2d);
            gate.draw(g2d);
        }
        
        //updates text
        leftText.setText("Average KE: " + (int)Ball.getLeftAverageKE());
        rightText.setText("Average KE: " + (int)Ball.getRightAverageKE());

        //draws text
        for(Text text : Text.texts){
            //gate.drawPoints(g2d);
            text.draw(g2d);
        }

        //calculation for deltatime
        deltaTime = (double)(System.nanoTime() - frameStart) / 1000000000.0;

        repaint();
    }
}