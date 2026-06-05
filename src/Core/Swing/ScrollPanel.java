package Core.Swing;

import org.intellij.lang.annotations.JdkConstants;

import javax.swing.*;
import java.awt.*;

public class ScrollPanel extends UIComponent<JScrollPane>{
	
	View viewport;
	
	public ScrollPanel(UIComponent<?> parent) {
	
		super(parent);
	
	}
	
	public void setViewport(View viewport) {
		
		viewport.setSize(this.component.getWidth(), viewport.getComponent().getHeight());
	
		component.setViewportView(viewport.getComponent());
	
	}
	
	public void setWidthLock(boolean lock) {
		
		viewport.setSize((lock)?this.component.getWidth():viewport.getComponent().getWidth(),
			   viewport.getComponent().getHeight());
		setViewport(viewport);
	
	}
	
	public void setHeightLock(boolean lock) {
		
		viewport.setSize(viewport.getComponent().getWidth(),
			   (lock)?this.component.getHeight():viewport.getComponent().getHeight());
		setViewport(viewport);
		
	}
	
	@Override
	public void stylize () {
		
		this.component.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.component.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
	}
	
	@Override
	protected JScrollPane createCustomObject () {
		
		return new JScrollPane() {
			
			@Override
			public void paintComponent ( Graphics g ) {
				
				super.paintComponent(g);
				buildComponent(g);
				
			}
		
		};
	
	}
	
	@Override
	public void buildComponent ( Graphics g ) {
	
	}
	
}
