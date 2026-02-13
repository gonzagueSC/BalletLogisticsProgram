package systemSwing;

import javax.swing.JPanel;

public class BasicGraph extends JPanel {
	
	DataPoint[] dataPoints;
	int highestVal = 0;
	int UpperBound;
	
	public BasicGraph(DataPoint[] dots) {
		
		dataPoints = dots;
		
		for (DataPoint p: dots) {
			
			if (p.getValue() > highestVal) highestVal = p.getValue();
			
		}
		
		UpperBound = (int)(highestVal * 1.05);
		
	}

}
