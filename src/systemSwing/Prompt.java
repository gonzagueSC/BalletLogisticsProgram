package systemSwing;

import java.awt.*;

import javax.swing.*;
import static util.SwingConstants.*;

public class Prompt extends JDialog {

	public Prompt(String s, Color c, int width, int height, int fontSize, Frame parent) {

		super(parent, "Prompt", true);
		this.setSize(new Dimension(width, height));
		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getRootPane().setBackground(TransparentBackground);
		Panel main = new Panel(true, true, true, true);
		main.setBackground(PromptMain);
		JLabel text = new JLabel();
		text.setBounds(0, 0, width, height);
		text.setText(s);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setBackground(c);
		text.setForeground(c);
		text.setVerticalAlignment(JLabel.CENTER);
		this.setLocationRelativeTo(parent);
		this.setForeground(c);
		text.setFont(new Font(Font.DIALOG, Font.ITALIC, fontSize));
		main.add(text);
		this.add(main);
		main.revalidate();
		main.repaint();

	}

	public Prompt(String s, Color c, int width, int height, int fontSize, JDialog parent) {

		super(parent, "Prompt", true);
		this.setSize(new Dimension(width, height));
		this.setUndecorated(true);
		this.setBackground(TransparentBackground);
		this.getRootPane().setBackground(TransparentBackground);
		Panel main = new Panel(true, true, true, true);
		main.setBackground(PromptMain);
		JLabel text = new JLabel();
		text.setBounds(0, 0, width, height);
		text.setText(s);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setBackground(c);
		text.setForeground(c);
		text.setVerticalAlignment(JLabel.CENTER);
		this.setLocationRelativeTo(parent);
		this.setForeground(c);
		text.setFont(new Font(Font.DIALOG, Font.ITALIC, fontSize));
		main.add(text);
		this.add(main);
		main.revalidate();
		main.repaint();

	}

}