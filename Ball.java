import java.util.ArrayList;
import java.awt.*;
import java.lang.Math;

//ball that serves as a molecule, bounces off of walls and other balls
public class Ball {

    //holds all balls
    public static ArrayList<Ball> balls = new ArrayList<Ball>();

    //private vars
    private double radius;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double mass;
    private double diameter;
    private Color color;

    //constructor, takes original position, initial velocity, and radius
    public Ball(double xI, double yI, double vxI, double vyI, double radiusI){

        radius = radiusI;
        diameter = radius*2;
        x = xI;
        y = yI;
        vx = vxI;
        vy = vyI;

        mass = 10;
        color = null;

        balls.add(this);
    }

    //alternate constructor, takes original position, initial velocity, radius, and mass(mass is untested and may be buggy)
    public Ball(double xI, double yI, double vxI, double vyI, double radiusI, double massI){

        radius = radiusI;
        diameter = radius*2;
        x = xI;
        y = yI;
        vx = vxI;
        vy = vyI;
        
        mass = massI;

        balls.add(this);
    }

    //draws ball with color
    public void draw(Graphics2D g2d){
        int xCentered = (int)(x-radius);
        int yCentered = (int)(y-radius);

        if(color != null){
            g2d.setColor(color);
        }
        g2d.drawOval(xCentered, yCentered, (int)diameter, (int)diameter);
        g2d.fillOval(xCentered, yCentered, (int)diameter, (int)diameter);

        g2d.setColor(java.awt.Color.DARK_GRAY);

    }

    //moves ball according to velocity
    public void move(double deltaTime){
        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    //returns distance to another ball
    public double distanceTo(Ball ball){
        return Math.sqrt( Math.pow(ball.getX() - x , 2) + Math.pow(ball.getY() - y , 2));
    }

    //returns distance to a point
    public double distanceTo(double[] point){
        return Math.sqrt( Math.pow(point[0] - x , 2) + Math.pow(point[1] - y , 2));
    }

    //getters
    public double getRadius(){return radius;}
    public double getX(){return x;}
    public double getY(){return y;}
    public double getVX(){return vx;}
    public double getVY(){return vy;}
    public double getMass(){return mass;}
    public Color getColor(){return color;}
    public double getVLength(){return Math.sqrt( Math.pow(vx , 2) + Math.pow(vy , 2));}
    public double getKE(){return .5 * mass * Math.pow(getVLength(), 2);}
    
    public static double getAverageKE(){
        double sum = 0;
        for(Ball ball: balls){
            sum += ball.getKE();
        }
        return sum / (double)balls.size();
    }
    public static double getLeftAverageKE(){
        double sum = 0;
        int count = 0;
        for(Ball ball: balls){
            if(ball.getX() < 800){
                sum += ball.getKE();
                count++;
            }
        }
        return sum / count;
    }

    public static double getRightAverageKE(){
        double sum = 0;
        int count = 0;
        for(Ball ball: balls){
            if(ball.getX() > 800){
                sum += ball.getKE();
                count++;
            }
        }
        return sum / count;
    }

    //setters
    public void setRadius(double radiusI){radius = radiusI;}
    public void setX(double xI){x = xI;}
    public void setY(double yI){y = yI;}
    public void setVX(double vxI){vx = vxI;}
    public void setVY(double vyI){vy = vyI;}
    public void setMass(double massI){mass = massI;}
    public void setColor(Color colorI){color = colorI;}
    
}
