package control;

import static util.SwingConstants.*;

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

}
