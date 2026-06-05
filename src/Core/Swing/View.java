package Core.Swing;

import Core.Utilities.AppWarning;

import javax.swing.*;
import java.awt.*;

import static Core.Utilities.SwingConstants.*;

public class View extends UIComponent<JPanel> {
	
	private UIComponent<?>[][] viewObjects;
	private final int[] RATIOS_X;
	private final int[] RATIOS_Y;
	private int TOTAL_WEIGHT_X;
	private int TOTAL_WEIGHT_Y;
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private double PADDING_X;
	private double PADDING_Y;
	private double VIEW_PADDING_X;
	private double VIEW_PADDING_Y;
	private double TOTAL_AREA_X;
	private double TOTAL_AREA_Y;
	
	public View ( UIComponent<?> parent, int[] X, int[] Y ) {
		
		viewObjects = new UIComponent<?>[X.length][Y.length];
		RATIOS_X = X;
		RATIOS_Y = Y;
		
		super(parent);
		
		calculate();
		
		this.component.setLayout(null);
		
		
	}
	
	public int getXLength() {
		return RATIOS_X.length;
	}
	public int getYLength() {
		return RATIOS_Y.length;
	}
	
	public View ( UIComponent<?> parent, UIComponent<?>[][] components, int[] X, int[] Y ) {
		
		this(parent, X, Y);
		
		viewObjects = components;
		
	}
	
	public void refreshLayout () throws AppWarning {
		
		this.clear();
		
		for ( int x = 0; x < RATIOS_X.length; x++ ) {
			
			for ( int y = 0; y < RATIOS_Y.length; y++ ) {
				
				UIComponent<?> comp = viewObjects[x][y];
				if ( comp != null ) {
					
					comp.setPos(calculateX(x + 1), calculateY(y + 1));
					comp.setSize(calculateWidth(x + 1), calculateHeight(y + 1));
					this.getComponent().add(comp.getComponent());
					
				}
				
			}
			
		}
		
	}
	
	public void calculate () {
		
		int calculateWeightX = 0;
		
		for ( int weight : RATIOS_X ) {
			
			calculateWeightX += weight;
			
		}
		
		TOTAL_WEIGHT_X = calculateWeightX;
		
		int calculateWeightY = 0;
		
		for ( int weight : RATIOS_Y ) {
			
			calculateWeightY += weight;
			
		}
		
		TOTAL_WEIGHT_Y = calculateWeightY;
		
		SCREEN_WIDTH = this.getComponent().getWidth();
		SCREEN_HEIGHT = this.getComponent().getHeight();
		
		PADDING_X = (PADDING_PERCENTAGE * SCREEN_WIDTH) / 100.0;
		PADDING_Y = (PADDING_PERCENTAGE * SCREEN_HEIGHT) / 100.0;
		
		VIEW_PADDING_X = (BORDER_PADDING * SCREEN_WIDTH) / 100.0;
		VIEW_PADDING_Y = (BORDER_PADDING * SCREEN_HEIGHT) / 100.0;
		TOTAL_AREA_X = SCREEN_WIDTH - 2 * PADDING_X - VIEW_PADDING_X * (RATIOS_X.length - 1);
		TOTAL_AREA_Y = SCREEN_HEIGHT - 2 * PADDING_Y - VIEW_PADDING_Y * (RATIOS_Y.length - 1);
		
	}
	
	public void add ( UIComponent<?> newComponent, int X, int Y ) throws AppWarning {
		
		newComponent.setPos(calculateX(X), calculateY(Y));
		newComponent.setSize(calculateWidth(X), calculateHeight(Y));
		
		viewObjects[X - 1][Y - 1] = newComponent;
		
		this.clear();
		
		for ( UIComponent<?>[] viewComponentRow : viewObjects ) {
			
			for ( UIComponent<?> viewComponent : viewComponentRow ) {
				
				if ( viewComponent != null ) this.add(viewComponent.getComponent());
				
			}
			
		}
		
	}
	
	public int calculatePos ( boolean hor, int Pos ) throws AppWarning {
		
		ValidatePos(hor, Pos);
		
		double totalArea = (hor) ? TOTAL_AREA_X : TOTAL_AREA_Y;
		
		double totalWeightBefore = 0;
		
		for ( int i = 0; i < Pos - 1; i++ ) {
			
			totalWeightBefore += (hor) ? RATIOS_X[i] : RATIOS_Y[i];
			
		}
		
		double areaToAdd = (totalArea * totalWeightBefore) / ((hor) ? TOTAL_WEIGHT_X : TOTAL_WEIGHT_Y);
		
		return (int) (((hor) ? PADDING_X : PADDING_Y) + ((hor) ? VIEW_PADDING_X : VIEW_PADDING_Y) * (Pos - 1) + areaToAdd);
		
	}
	
	public int calculateX ( int X ) throws AppWarning {
		
		return calculatePos(true, X);
		
	}
	
	public int calculateY ( int Y ) throws AppWarning {
		
		return calculatePos(false, Y);
		
	}
	
	public int calculateSize ( boolean hor, int Pos ) throws AppWarning {
		
		int correctedPos = Pos - 1;
		
		ValidatePos(hor, Pos);
		
		double totalArea = (hor) ? TOTAL_AREA_X : TOTAL_AREA_Y;
		
		int totalWeight = hor ? TOTAL_WEIGHT_X : TOTAL_WEIGHT_Y;
		
		double individualWeightSize = totalArea / totalWeight;
		
		return (int) (individualWeightSize * (hor ? RATIOS_X[correctedPos] : RATIOS_Y[correctedPos]));
		
	}
	
	public int calculateWidth ( int X ) throws AppWarning {
		
		return calculateSize(true, X);
		
	}
	
	public int calculateHeight ( int Y ) throws AppWarning {
		
		return calculateSize(false, Y);
		
	}
	
	public void ValidatePos ( boolean hor, int Pos ) throws AppWarning {
		
		if ( hor && Pos > RATIOS_X.length || !hor && Pos > RATIOS_Y.length || Pos < 1 )
			throw new AppWarning("Position is out of Bounds");
		
	}
	
	@Override
	public void stylize () {
		
		//TODO Any extras needed
		
	}
	
	@Override
	protected JPanel createCustomObject () {
		
		return new JPanel() {
			
			@Override
			public void paintComponent ( Graphics g ) {
				
				super.paintComponent(g);
				buildComponent(g);
				
			}
			
		};
	}
	
	@Override
	public synchronized void Update () throws AppWarning {
		
		int oldWidth = SCREEN_WIDTH;
		int oldHeight = SCREEN_HEIGHT;
		
		this.calculate();
		
		if ( SCREEN_HEIGHT != oldHeight || SCREEN_WIDTH != oldWidth ) refreshLayout();
		
		super.Update();
		
	}
	
	public void buildComponent ( Graphics g ) {
		
		//TODO (Build a cleaner UI)
		
	}
	
}
