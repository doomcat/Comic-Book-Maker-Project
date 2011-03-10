import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;


public class CanvasContainer extends InternalBox implements MouseListener, MouseMotionListener {
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
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DELETE"), "delImg");
		getActionMap().put("delImg", new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				canvas.deleteSelectedElement();
			}
		});
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
		item.addMouseListener(this);
		item.addMouseMotionListener(this);
		canvas.addToCanvas(item, x, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
