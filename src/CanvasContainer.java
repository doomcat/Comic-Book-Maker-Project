import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;


public class CanvasContainer extends InternalBox {
	private Canvas canvas;
	private JScrollPane sp;
	
	CanvasContainer() {
		super();
		setTitle("Canvas");
		//setBackground(new Color(255,0,0));
		//canvas = new JLabel(new ImageIcon("beardyman.png"),SwingConstants.CENTER);
		canvas = new Canvas();
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		sp = new JScrollPane(canvas);
		add(sp,BorderLayout.CENTER);
	}
	
	public void setCanvas(Canvas canvas) {
		sp.setViewportView(canvas);
		sp.revalidate();
		this.canvas = canvas;
		canvas.repaint();
		sp.repaint();
		repaint();
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
	public JScrollPane getScrollPane() {
		return sp;
	}
	
	public void addToCanvas(DraggableIcon item, int x, int y) {
		canvas.addToCanvas(item, x, y);
	}
}
