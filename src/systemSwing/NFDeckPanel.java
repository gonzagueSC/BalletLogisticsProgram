package systemSwing;

import java.awt.*;
import javax.swing.JPanel;

public class NFDeckPanel extends JPanel {

    public NFDeckPanel(CardLayout cl) {
        super(cl); 
        
        setDoubleBuffered(false);
        setOpaque(false);
        
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}