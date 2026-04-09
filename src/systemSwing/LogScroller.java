package systemSwing;


import control.PromptsService;

import javax.swing.*;
import java.awt.*;

import static util.SwingConstants.MainGray;

public class LogScroller extends ScrollPanel {

    String[] logs;

    int height = 200;

    public void setLogHeight(int logHeight) {

        height = logHeight;

    }

    public void receiveLog(String[] logs) {

        this.logs = logs;

        this.clearItems();

        for (String logRecord : logs) {

            Button log = new Button(logRecord, (e -> {

                PromptsService.DataPrompt(logRecord.split(":\\s")[1]);

            }), new Dimension(0, height), 0, 0, MainGray, 0, height / 3);
            log.setHorizontalAlignment(JLabel.LEFT);
            this.addItem(log);

        }

    }

    public void receiveLog(String[] logs, String query) {

        this.logs = logs;

        if (query.isBlank() || query.isEmpty()) {

            receiveLog(logs);
            return;

        }

        this.clearItems();

        for (String logRecord : logs) {

            if (logRecord.toLowerCase().contains(query.toLowerCase())) {

                Button log = new Button(logRecord, (e -> {

                    PromptsService.DataPrompt(logRecord.split(":\\s")[1]);

                }), new Dimension(0, height), 0, 0, MainGray, 0, height / 3);
                log.setHorizontalAlignment(JLabel.LEFT);
                this.addItem(log);

            }

        }

    }

    public void narrowResults(String query) {

        this.clearItems();

        for (String logRecord : logs) {

            if (logRecord.toLowerCase().contains(query.toLowerCase())) {

                Button log = new Button(logRecord, (e -> {

                    PromptsService.DataPrompt(logRecord.split(":\\s")[1]);

                }), new Dimension(0, height), 0, 0, MainGray, 0, height / 3);
                log.setHorizontalAlignment(JLabel.LEFT);
                this.addItem(log);

            }

        }

    }

}
