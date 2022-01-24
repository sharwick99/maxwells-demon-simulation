import java.util.ArrayList;
import java.awt.*;

//text display at given location
public class Text {

    //stores all texts in a list
    public static ArrayList<Text> texts = new ArrayList<Text>();

    //private vars
    private String text;
    private int x;
    private int y;
    private Color color;

    //constructor, params are text string, and x,y of text
    public Text(String textI, int xI, int yI){

        text = textI;
        x = xI;
        y = yI;
        color = java.awt.Color.BLACK;

        texts.add(this);
    }

    //alternate constructor with color
    public Text(String textI, int xI, int yI, Color colorI){

        text = textI;
        x = xI;
        y = yI;
        color = colorI;

        texts.add(this);
    }

    //draws to screen with certain color
    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    //setter
    public void setText(String textI){text = textI;}

}