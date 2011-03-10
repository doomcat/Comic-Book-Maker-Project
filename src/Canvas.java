import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Canvas extends JPanel implements Serializable, MouseMotionListener, MouseListener, KeyListener {
	private Color bgColor;
	Vector<CanvasIcon> items;
	int w = 0, h = 0;
	private CanvasIcon draggedItem;
	private boolean beingDragged = false;
	
	Canvas() {
		super();
		bgColor = new Color(255,255,255);
		items = new Vector<CanvasIcon>();
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		//this.setF
	}
	
	public void addToCanvas(CanvasIcon item, int x, int y) {
		item.setcX(x); item.setcY(y);
		if(item.getcX()+item.getWidth() > w) w = item.getcX()+item.getWidth();
		if(item.getcY()+item.getHeight() > h) h = item.getcY()+item.getHeight();
		setPreferredSize(new Dimension(w,h));
		//item.addMouseMotionListener(this);
		//item.addMouseListener(this);
		items.add(item);
		SystemState.canvasPointer.getScrollPane().revalidate();
		repaint();
		SystemState.history.addToHistory(this);
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
	
	public void deleteSelectedElement() {
		if(draggedItem != null) {
			items.remove(draggedItem);
			draggedItem = null;
			repaint();
		}
	}
	
	public void paint(Graphics g) {
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
			g.drawImage(i.getImage(), i.getcX(), i.getcY(), i.getWidth(), i.getHeight(), this);
		}
		this.getRootPane().revalidate();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		CanvasIcon i = draggedItem;
		//for(DraggableIcon i : items) {
		//	if(draggedItem == i) {
			if(draggedItem != null) {
				if((x >= i.getcX() && x <= i.getcX()+i.getWidth()) &&
					(y >= i.getcY() && y <= i.getcY()+i.getHeight())) {
					i.setcX(x-(i.getWidth()/2));
					i.setcY(y-(i.getWidth()/2));
				}
			}
		//}
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
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Key Pressed");
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_DELETE) { deleteSelectedElement(); }
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) { }
	@Override
	public void mouseExited(MouseEvent arg0) { }
	@Override
	public void mousePressed(MouseEvent arg0) { }
	@Override
	public void mouseReleased(MouseEvent arg0) { }
}
