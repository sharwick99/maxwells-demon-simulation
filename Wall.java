import java.util.ArrayList;
import java.awt.*;
import java.lang.Math;

//wall that balls bounce off of
public class Wall {

    //list that holds all walls
    public static ArrayList<Wall> walls = new ArrayList<Wall>();
    //list of all points in a wall
    public ArrayList<double[]> points = new ArrayList<double[]>();

    //private variables
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    //constructor, params are start and end point (x,y)
    public Wall(double x1I, double y1I, double x2I, double y2I){
        x1 = x1I;
        y1 = y1I;
        x2 = x2I;
        y2 = y2I;

        walls.add(this);

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

    //draws wall
    public void draw(Graphics2D g2d){
        g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

    //draws all points on a wall (used for debug)
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
