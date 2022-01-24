import java.util.ArrayList;
import java.awt.*;
import java.lang.Math;


//wall like structure that only allows high KE balls to go to the left and low KE to go to the right
public class Gate {

    //gates stores all gates in the simulation
    public static ArrayList<Gate> gates = new ArrayList<Gate>();
    //points store all points in the gate, which are used to check for collisions
    public ArrayList<double[]> points = new ArrayList<double[]>();

    //private variables for the class
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    //constructor, parameters are start x,y and end x,y
    public Gate(double x1I, double y1I, double x2I, double y2I){
        x1 = x1I;
        y1 = y1I;
        x2 = x2I;
        y2 = y2I;

        gates.add(this);

        //creates all of the points on the line
        int pointDistance = 1;

        double normalizedX = (x2-x1)/getLength();
        double normalizedY = (y2-y1)/getLength();

        for(int i = 0; i < getLength() / pointDistance; i++){
            double x = x1 + (i * normalizedX * pointDistance);
            double y = y1 + (i * normalizedY * pointDistance);

            points.add(new double[]{x, y});
        }
        points.add(new double[]{x2, y2});

    }

    //draw the gate with 2 circles on each end
    public void draw(Graphics2D g2d){

        int radius = 10;

        g2d.setPaint(java.awt.Color.RED);
        g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        g2d.setPaint(java.awt.Color.DARK_GRAY);

        g2d.drawOval((int)(x1-(radius/2.0)), (int)(y1-(radius/2.0)), radius, radius);
        g2d.fillOval((int)(x1-(radius/2.0)), (int)(y1-(radius/2.0)), radius, radius);

        g2d.drawOval((int)(x2-(radius/2.0)), (int)(y2-(radius/2.0)), radius, radius);
        g2d.fillOval((int)(x2-(radius/2.0)), (int)(y2-(radius/2.0)), radius, radius);
    }

    //draw all points on the line, used for debug
    public void drawPoints(Graphics2D g2d){
        for(double[] point : points){

            int radius = 3;

            int x = (int)(point[0]-(radius/2));
            int y = (int)(point[1]-(radius/2));

            g2d.drawOval(x, y, radius, radius);
            g2d.fillOval(x, y, radius, radius);
        }
    }

    //getters
    public double getLength(){return Math.sqrt( Math.pow(x2-x1 , 2) + Math.pow(y2-y1 , 2));}
    public double getAngle(){return Math.atan( (y2-y1) / ((x2-x1)));}
    
}
