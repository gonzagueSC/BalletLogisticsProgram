package systemSwing;


import java.awt.*;

public class LogScroller extends ScrollPanel{

    int height = 50;

    public void setLogHeight(int logHeight) {

        height = logHeight;

    }

    public void receiveLog(String[] logs) {

        for (String logRecord: logs) {

            GeneralLabel log = new GeneralLabel(logRecord, 0, 0, this.getWidth(), height, height/15, Color.black);
            this.addItem(log);

        }

    }

}
