package SwingCards;

import static util.SwingConstants.*;

import swingConstants.*;
import systemSwing.*;

public class LoadingView extends Panel {

	public LoadingView() {

		TitleLabel title = new TitleLabel("Loading...", 0, 0, CoreVariables.SCREENWIDTH, CoreVariables.SCREENHEIGHT,
				TITLELABELFONTSIZE, TextColor);
		
		this.add(title);
		
		this.revalidate();
		this.repaint();

	}

}
