import javax.swing.*;

//creates frame
public class Frame extends JFrame{

    Panel panel;

    Frame(){
        panel = new Panel(1600, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
 
}
