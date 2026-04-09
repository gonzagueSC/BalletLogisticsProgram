package systemSwing;


import javax.swing.*;
import java.awt.*;

public class LogScroller extends ScrollPanel{

    int height = 200;

    public void setLogHeight(int logHeight) {

        height = logHeight;

    }

    public void receiveLog(String[] logs) {

        this.clearItems();

        for (String logRecord: logs) {

            GeneralLabel log = new GeneralLabel(logRecord, 0, 0, 0, height, height/3, Color.black);
            log.setHorizontalAlignment(JLabel.LEFT);
            this.addItem(log);

        }

    }

}
