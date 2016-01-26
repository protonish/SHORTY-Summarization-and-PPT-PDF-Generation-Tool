package src;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Screen extends JFrame {
	

	Screen() {
        this.setLocation(0, 0);

        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.setSize(screenSize);
        this.setVisible(true);
    }
}