import java.lang.Math;

class Main {
    public static void main(String[] args)
    {
        //creates window
        new Frame();

        //creates square
        new Wall(400, 50, 1200, 50);
        new Wall(400, 850, 1200, 850);
        new Wall(400, 50, 400, 850);
        new Wall(1200, 50, 1200, 850);

        //creates center gate
        new Wall(800, 50, 800, 350);
        new Wall(800, 850, 800, 550);
        new Gate(800, 350, 800, 550);

        //creates all of the balls with random velocities
        for(int i = 0; i < 25; i++){

            double x = (Math.random() * 800) + 400;
            double y = (Math.random() * 800) + 50;

            double vx = (Math.random() * 1000) - 500;
            double vy = (Math.random() * 1000) - 500;

            new Ball(x, y, vx, vy, 10);
        }
    }
}