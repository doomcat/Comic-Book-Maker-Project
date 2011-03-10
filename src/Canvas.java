import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class Canvas extends JPanel implements Serializable, MouseMotionListener, MouseListener {
	private Color bgColor;
	protected Vector<CanvasIcon> items;
	int w = 0, h = 0, lastX = 0, lastY = 0;
	private CanvasIcon draggedItem;
	private CanvasIcon resizedItem;
	private boolean beingResized = false;
	
	Canvas() {
		super();
		bgColor = new Color(255,255,255);
		items = new Vector<CanvasIcon>();
		addMouseMotionListener(this);
		addMouseListener(this);
		//this.setF
	}
	
	public void addToCanvas(CanvasIcon item, int x, int y) {
		item.setcX(x); item.setcY(y);
		items.add(item);
		resizeCanvas();
		SystemState.history.addToHistory(this);
		repaint();
	}
	
	public void resizeCanvas() {
		for(CanvasIcon i : items) {
			if(i.getcX()+i.getWidth() > w) w = i.getcX()+i.getWidth()+10;
			if(i.getcY()+i.getHeight() > h) h = i.getcY()+i.getHeight()+10;
		}
		setPreferredSize(new Dimension(w,h));
		SystemState.canvasPointer.getScrollPane().revalidate();
	}
	
	public void setBgColor(Color c) {
		bgColor = c;
		repaint();
	}
	
	public Color getBgColor() {
		return bgColor;
	}
	
	public void clear() {
		items.clear();
		w = 0; h = 0;
		repaint();
	}
	
	public void saveCanvasAs(String filename) {
		try{
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		} catch(IOException e) {
			JOptionPane.showMessageDialog(SystemState.rootPane, "There was an error saving your file! Sorry :(");
			e.printStackTrace();
		}
	}
	
	public CanvasIcon getSelectedElement() {
		if(draggedItem != null) {
			return draggedItem;
		}
		for(CanvasIcon i : items) {
			if(i.isSelected()) return i;
		}
		return null;
	}
	
	public void flipSelectedElement(String direction) {
		if(draggedItem != null) {
			if(direction == "horizontal") draggedItem.setFlippedH(true);
			if(direction == "vertical") draggedItem.setFlippedV(true);
		}
	}
	
	public void deleteSelectedElement() {
		if(draggedItem != null) {
			items.remove(draggedItem);
			draggedItem = null;
			repaint();
			SystemState.history.addToHistory(this);
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int tW = 0, tH = 0;
		super.paintComponent(g);
		g.setColor(bgColor);
		Rectangle size = SystemState.canvasPointer.getScrollPane().getVisibleRect();
		if(size.width > w) tW = size.width;
		else tW = w;
		if(size.height > h) tH = size.height;
		else tH = h;
		g.fillRect(0, 0, tW, tH);
		//g.setColor(new Color(0,0,0));
		for(CanvasIcon i : items) {
			if(i.isSelected()) {
				g.setColor(new Color(255,255,255));
				g.setXORMode(new Color(0,0,0));
				g.drawRect(i.getcX()-10, i.getcY()-10, i.getWidth()+20, i.getHeight()+20);
				g.fillRect((i.getcX()+i.getWidth()), (i.getcY()+i.getHeight()), 10, 10);
			}
			g.setPaintMode();
			int tmpW = i.getWidth(); int tmpH = i.getHeight();
			if(i.isFlippedV()) tmpH = -tmpH;
			if(i.isFlippedH()) tmpW = -tmpW;
			g.drawImage(i.getImage(), i.getcX(), i.getcY(), tmpW, tmpH, this);
		}
		this.getRootPane().revalidate();
	}
	
	//taken straight from http://stackoverflow.com/questions/4530736/how-to-capture-a-swing-gui-element
	//(used for exporting comic as .png image - didn't need to change anything)
	public void exportAsPNG(File imageFile) throws IOException {
	    BufferedImage bufImage = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_RGB);
	    for(CanvasIcon i : items) { i.setSelected(false); }
	    this.paint(bufImage.createGraphics());   
	    imageFile.createNewFile();  
	    ImageIO.write(bufImage, "png", imageFile);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int dX = 0, dY = 0;
		CanvasIcon i = draggedItem;
			if(draggedItem != null) {
				if((x >= i.getcX() && x <= i.getcX()+i.getWidth()) &&
					(y >= i.getcY() && y <= i.getcY()+i.getHeight()) && beingResized == false) {
					i.setcX(x-(i.getWidth()/2));
					i.setcY(y-(i.getHeight()/2));
					lastX = 0; lastY = 0;
				}
				else if((x >= (i.getcX()+i.getWidth()) && x <= (i.getcX()+i.getWidth())+10) &&
						(y >= i.getcY()+i.getHeight()) && (y <= i.getcY()+i.getHeight()+10)) {
					resizedItem = i;
				}
			}
			if(resizedItem != null) {
				if(beingResized == false) { dX = 0; dY = 0; lastX = 0; lastY = 0; }
				else { dX = x-lastX; dY = y-lastY; }
				beingResized = true;
				if(SystemState.retainAspect == true) {
					i.resize(i.getWidth()+dX, i.getHeight()*((i.getDefaultWidth())/i.getDefaultHeight())+dX);
				} else {
					i.resize(i.getWidth()+dX, i.getHeight()+dY);
				}
				lastX = x; lastY = y;
			}
		resizeCanvas();
		repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) { }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(); int y = e.getY();
		boolean somethingSelected = false;
		for(CanvasIcon i : items) {
			i.setSelected(false);
			if((x >= i.getcX() && x <= i.getcX()+i.getWidth()) &&
				(y >= i.getcY() && y <= i.getcY()+i.getHeight())) {
				draggedItem = i; i.setSelected(true);
				somethingSelected = true;
			}
		}
		if(somethingSelected == false) draggedItem = null;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }
	@Override
	public void mouseExited(MouseEvent arg0) { }
	@Override
	public void mousePressed(MouseEvent arg0) { }
	@Override
	public void mouseReleased(MouseEvent e) {
		if(beingResized == true || resizedItem != null || draggedItem != null) SystemState.history.addToHistory(this);
		lastX = 0; lastY = 0; beingResized = false; resizedItem = null;
	}
}
