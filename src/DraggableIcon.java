import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


//TODO change DraggableIcon internals so that it uses Images,
//TODO replace paint() method to paint images with custom widths and sizes

public class DraggableIcon extends JLabel implements MouseMotionListener, MouseListener, Serializable {
	private String file;
	private boolean beingDragged = false;

	protected int cX, cY = 0;
	private int mX = 0, mY = 0;
	
	DraggableIcon(String file) {
		/**
		 * DraggableIcon: take a filename as String, temporarily create an ImageIcon from this
		 * file, convert into an image, so we can convert it back to an ImageIcon, but scaled down
		 * to 96 pixels wide, in order for it to be fully visible in the image panel. Add a tooltip
		 * showing what the filename is. 
		 */
		super();
		ImageIcon tmp = new ImageIcon(file);
		Image tmpI = tmp.getImage();
		ImageIcon icon = new ImageIcon(tmpI.getScaledInstance(96, 96, java.awt.Image.SCALE_AREA_AVERAGING));
		this.setIcon(icon);
		
		this.file = file;
		setVisible(true);
		addMouseMotionListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(96,96));
		setToolTipText("File: "+new File(file).getName());
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 *
	 *	I should probably explain this as it's an eyesore to look at.
	 *	To do drag and drop you want to draw whatever you're dragging
	 *	on top of all the other interface elements. To do that in Swing
	 *	you can paint stuff in the root layer's "glass pane", which is
	 *	drawn on top of everything else.
	 *	You have to translate the mouse position to get the right effect
	 *	though - which means getting the absolute position of the dragged
	 *	icon on screen, and adding it to the mouse's X and Y coordinates,
	 *	or it'll probably appear above and to the left of your mouse.
	 *	Then you call RootWindow.setDraggableIcon(), and next time root
	 *	window runs paint() it'll draw the icon at that position.
	 *
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		beingDragged = true;
		mX = e.getX()-SystemState.rootPane.getX(); mY = e.getY()-SystemState.rootPane.getY();
		Graphics glass = SystemState.glassPane.getGraphics();
		Icon icon = this.getIcon();
		Point pos = getLocationOnScreen();
		SystemState.rootPane.setDraggableIcon(this, e.getX()+pos.x-(icon.getIconWidth()/2), e.getY()+pos.y-(icon.getIconHeight()/2));
		SystemState.rootPane.repaint();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 * mouseReleased: if icon is being dragged, create a CanvasIcon in the Canvas object
	 * where the mouse coordinates of this event are (doing translation so it lands in
	 * roughly the right place).
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(beingDragged == true) {
			Canvas canvas = SystemState.canvasPointer.getCanvas();
			Icon icon = this.getIcon();
			Point pos2 = canvas.getLocationOnScreen();
			Point pos1 = getLocationOnScreen();
			
			//dropping an icon basically creates a new object on the canvas. positions have to be translated
			//so that it shows up at the right place on canvas.
			CanvasIcon tmp = new CanvasIcon(this.file);
			tmp.resize(96,96);
			canvas.addToCanvas(tmp,
					//(mouse X coordinate + icon's X coordinate - canvases X coordinate - half the width of the icon)
					e.getX()+pos1.x-pos2.x-(icon.getIconWidth()/2),
					// same as above but with Y coordinates
					e.getY()+pos1.y-pos2.y-(icon.getIconHeight()/2)
			);
			
			//stop the dragged icon from being drawn on top of everything
			SystemState.rootPane.setDraggableIcon(null, 0, 0);
			SystemState.rootPane.repaint();
			SystemState.unsaved = true;
		}
		beingDragged = false;
	}

	//Don't need any of these events to be handle, so leave them with blank methods.
	//Drag and Drop can be implemented using just mouseDragged() and mouseReleased() methods.
	@Override
	public void mouseMoved(MouseEvent e) { }
	
	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

}
