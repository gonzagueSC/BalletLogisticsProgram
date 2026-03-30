package systemSwing;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import SwingCards.TeacherPanelView;

public class AuditLog extends Panel {

    int leftMarginX;
    int rightMarginX;

    int topMarginY;
    int bottomMarginY;

    LogScroller auditLog;

    private String ID;

    public AuditLog(TeacherPanelView parent) {

        super(true, true, true, true);

        this.setLayout(null);

        auditLog = new LogScroller();
        auditLog.setLogHeight(this.getHeight() / 30);
        this.add(auditLog);

    }

    public void setSize(int X, int Y) {

        leftMarginX = this.getSize().width / 20;
        rightMarginX = this.getSize().width - this.getSize().width / 20;

        topMarginY = (int) (this.getSize().height / 5.0);
        bottomMarginY = this.getSize().height - (int) (this.getSize().height / 10.0);

        this.setBounds(this.getBounds().x, this.getBounds().y, X, Y);

        auditLog.setLogHeight(this.getHeight() / 30);

    }

    public void setPosition(int X, int Y) {

        this.setBounds(X, Y, this.getSize().width, this.getSize().height);

    }

    public void setStudent(String studentID) {

        this.ID = studentID;

    }


    @Override

    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        super.paint(g);

    }

}
