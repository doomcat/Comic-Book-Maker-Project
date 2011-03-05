import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Canvas extends JPanel implements Serializable {
	private Color bgColor = new Color(255,255,255);
	Vector<DraggableIcon> items;
	int w = 0, h = 0;
	
	Canvas() {
		super();
		items = new Vector<DraggableIcon>();
	}
	public void addToCanvas(DraggableIcon item, int x, int y) {
		item.cX = x; item.cY = y;
		if(item.cX+item.getIcon().getIconWidth() > w) w = item.cX+item.getIcon().getIconWidth();
		if(item.cY+item.getIcon().getIconHeight() > h) h = item.cY+item.getIcon().getIconHeight();
		setPreferredSize(new Dimension(w,h));
		items.add(item);
		SystemState.canvasPointer.getScrollPane().revalidate();
		repaint();
	}
	
	public void setBgColor(Color c) {
		bgColor = c;
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
		for(DraggableIcon i : items) {
			i.getIcon().paintIcon(this, g, i.cX, i.cY);
		}
		this.getRootPane().revalidate();
	}
}
