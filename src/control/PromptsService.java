package control;

import static util.SwingConstants.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import systemSwing.Prompt;

public class PromptsService {

    public static void FailurePrompt(String s) {

        Prompt prompt = new Prompt(s, RedColor, s.length() * (PROMPTFONTSIZE - 10) + 20, PROMPTHEIGHT, PROMPTFONTSIZE, Main.frame);

        ActionListener removePrompt = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                prompt.dispose();

            }
        };

        Timer timer = new Timer(1500, removePrompt);

        timer.setRepeats(false);
        timer.start();

        prompt.setVisible(true);

    }

    public static void FailurePrompt(String s, JDialog dialog) {

        Prompt prompt = new Prompt(s, RedColor, s.length() * (PROMPTFONTSIZE - 10) + 20, PROMPTHEIGHT, PROMPTFONTSIZE, dialog);

        ActionListener removePrompt = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                prompt.dispose();

            }
        };

        Timer timer = new Timer(1500, removePrompt);

        timer.setRepeats(false);
        timer.start();

        prompt.setVisible(true);

    }

    public static void SuccessPrompt(String s) {

        Prompt prompt = new Prompt(s, GreenColor, s.length() * (PROMPTFONTSIZE - 10) + 20, PROMPTHEIGHT, PROMPTFONTSIZE, Main.frame);

        ActionListener removePrompt = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                prompt.dispose();

            }
        };

        Timer timer = new Timer(1500, removePrompt);

        timer.setRepeats(false);
        timer.start();

        prompt.setVisible(true);

    }

    public static void SuccessPrompt(String s, JDialog dialog) {

        Prompt prompt = new Prompt(s, GreenColor, s.length() * (PROMPTFONTSIZE - 10) + 20, PROMPTHEIGHT, PROMPTFONTSIZE, dialog);

        ActionListener removePrompt = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                prompt.dispose();

            }
        };

        Timer timer = new Timer(1500, removePrompt);

        timer.setRepeats(false);
        timer.start();

        prompt.setVisible(true);

    }

    public static void DataPrompt(String s) {

        Prompt prompts = new Prompt(s, MainGray, s.length() * (PROMPTFONTSIZE - 10) + 20, PROMPTHEIGHT, PROMPTFONTSIZE - 10, Main.frame);

        FontMetrics fontMetrics = prompts.getFontMetrics(new Font(Font.DIALOG, Font.ITALIC, PROMPTFONTSIZE - 10));

        int width = fontMetrics.stringWidth(s);

        Prompt prompt = new Prompt(s, MainGray.brighter(), width + 40, PROMPTHEIGHT, PROMPTFONTSIZE - 10, Main.frame);

        MouseListener clickOut = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                prompt.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                prompt.dispose();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                prompt.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        prompt.addMouseListener(clickOut);

        prompt.setVisible(true);

    }

}
