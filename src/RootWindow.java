import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class RootWindow extends JFrame {
	private DraggableIcon icon = null;
	private int iX = 0, iY = 0;
	
	RootWindow() {
		super();
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		this.setBackground(Color.BLUE);
	}
	
	public void setDraggableIcon(DraggableIcon i, int x, int y) {
		icon = i; iX = x; iY = y;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(icon != null) {
			icon.getIcon().paintIcon(this.getGlassPane(), this.getGlassPane().getGraphics(), iX, iY);
		}
	}
}
