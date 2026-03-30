package systemSwing;

import static util.SwingConstants.SecondaryPurple;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class ScrollPanel extends JScrollPane {

    int y = 0;

    int gap = 0;

    int minimumHeight = 0;

    static Panel ScrollPanel;

    public ScrollPanel() {

        super(ScrollPanel = new Panel(false, false, false, false));

    }

    public ScrollPanel(boolean bordered) {

        super(ScrollPanel = new Panel(bordered, bordered, bordered, bordered));

    }

    public void stylize() {

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.getVerticalScrollBar().setUnitIncrement(5);
        this.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, SecondaryPurple.darker()));

    }

    public void setSize(Dimension d) {

        this.setSize(d);

    }

    public void setGapHeight(int height) {

        gap = height;

    }

    public void addItem(JComponent item) {

        ScrollPanel.add(item);
        item.setBounds(0, y, this.getWidth(), item.getHeight());
        int itemHeight = item.getHeight();
        if (y + itemHeight + gap > minimumHeight)
            y += itemHeight + gap;
        ScrollPanel.setPreferredSize(new Dimension(this.getWidth(), y));

    }

}
