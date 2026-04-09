package systemSwing;

import static util.SwingConstants.SecondaryPurple;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class ScrollPanel extends JScrollPane {

    int y = 0;

    int gap = 0;

    int minimumHeight = 0;

    final Panel ScrollPanel;

    public ScrollPanel() {

        this(false);

    }

    @Override

    public void setSize(Dimension d) {

        ScrollPanel.setSize(new Dimension((int)(d.getWidth()),(int)d.getHeight()));

        super.setSize(d);

    }

    public ScrollPanel(boolean bordered) {

        this.ScrollPanel = new Panel(bordered, bordered, bordered, bordered);

        this.setViewportView(ScrollPanel);

        ScrollPanel.setLayout(null);
        ScrollPanel.setVisible(true);

        this.setVisible(true);

        this.stylize();
        this.revalidate();
        this.repaint();

    }

    public void stylize() {

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.getVerticalScrollBar().setUnitIncrement(5);
        this.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));

    }

    public void setGapHeight(int height) {

        this.gap = height;

    }

    public void addItem(JComponent item) {

        ScrollPanel.add(item);
        item.setBounds(0, y, (int)(ScrollPanel.getSize().getWidth()), item.getHeight());
        int itemHeight = item.getHeight();
        if (y + itemHeight + gap > minimumHeight)
            y += itemHeight + gap;
        ScrollPanel.setPreferredSize(new Dimension((int)(ScrollPanel.getSize().getWidth()), y));
        item.setVisible(true);

        ScrollPanel.revalidate();
        ScrollPanel.repaint();

        this.revalidate();
        this.repaint();

    }

    public void clearItems() {

        ScrollPanel.removeAll();
        y = 0;
        revalidate();
        repaint();

    }

}
