import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;


public class DraggableIcon extends JLabel implements MouseMotionListener, MouseListener, Serializable {
	private String file;
	private boolean beingDragged = false;
	protected int cX, cY = 0;
	private int mX = 0, mY = 0;
	
	DraggableIcon(String file) {
		super(new ImageIcon(file));
		this.file = file;
		setVisible(true);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		beingDragged = true;
		mX = e.getX(); mY = e.getY();
		//SystemState.rootPane.repaint();
		// TODO Implement GlassPane dragging and dropping
		//this.getRootPane().getGlassPane().paint(getGraphics());
		//Graphics glass = SystemState.glassPane.getGraphics();
		//Icon icon = this.getIcon();
		//Point pos = getLocationOnScreen();
		//int sz = 4;
		//glass.fillOval(e.getX()+pos.x-(sz*2), e.getY()+pos.y-(sz*2), sz, sz);
		//this.getIcon().paintIcon(SystemState.glassPane, glass, e.getX()+pos.x-(icon.getIconWidth()/2), e.getY()+pos.y-(icon.getIconHeight()/2));
	}

	public void paint(Graphics g) {
		super.paint(g);
		if(beingDragged) {
			Graphics glass = SystemState.glassPane.getGraphics();
			Icon icon = this.getIcon();
			Point pos = getLocationOnScreen();
			this.getIcon().paintIcon(SystemState.glassPane, glass, mX+pos.x-(icon.getIconWidth()/2), mY+pos.y-(icon.getIconHeight()/2));
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) { }
	
	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(beingDragged == true) {
			Canvas canvas = SystemState.canvasPointer.getCanvas();
			Icon icon = this.getIcon();
			Point pos2 = canvas.getLocationOnScreen();
			Point pos1 = getLocationOnScreen();
			canvas.addToCanvas(new DraggableIcon(this.file), e.getX()+pos1.x-pos2.x-(icon.getIconWidth()/2), e.getY()+pos1.y-pos2.y-(icon.getIconHeight()/2));
			//this.getIcon().paintIcon(canvas, canvas.getGraphics(), e.getX()+pos1.x-pos2.x-(icon.getIconWidth()/2), e.getY()+pos1.y-pos2.y-(icon.getIconHeight()/2));
		}
		beingDragged = false;
	}

}
