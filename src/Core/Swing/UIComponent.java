package Core.Swing;

import Core.Utilities.AppWarning;

import javax.swing.*;
import java.awt.*;

public abstract class UIComponent<T extends JComponent>{
	
	protected final T component;
	protected final UIComponent<?> parent;
	
	public UIComponent (UIComponent<?> parent ) {
		
		this.component = createCustomObject();
		this.parent = parent;
		
	}
	
	public synchronized void Update () throws AppWarning {
		
		this.stylize();
		
		component.revalidate();
		component.repaint();
		component.setVisible(true);
		
		if (parent != null) parent.Update();
		
	}
	
	public UIComponent<?> getParent () {
		
		return parent;
		
	}
	
	public void setPos(int X, int Y) {
		
		this.component.setBounds(X, Y, component.getWidth(), component.getHeight());
		
	}
	
	public void setSize(int width, int height) {
		
		this.component.setBounds(component.getX(), component.getY(), width, height);
	
	}
	
	public void add(JComponent newComponent) {
		
		this.component.add(newComponent);
		
	}
	
	public void clear() {
		
		this.component.removeAll();
		
	}
	
	public T getComponent() {
		
		return component;
		
	}
	
	public abstract void stylize();
	
	protected abstract T createCustomObject () ;
	
	public abstract void buildComponent(Graphics g);
	
}