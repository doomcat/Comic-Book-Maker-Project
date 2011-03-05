import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageBox extends InternalBox implements ListSelectionListener {
	private FlowLayout fl;
	private BorderLayout bl;
	private JComponent images;
	private JScrollPane sp;
	
	ImageBox() {
		super();
		setTitle("Props");
		
		bl = new BorderLayout();
		fl = new FlowLayout();
		setLayout(bl);
		
		images = new JPanel();
		images.setLayout(fl);
		
		Vector<String> cats = new Vector<String>();
		File categories = new File("assets");
		for(File f : categories.listFiles()) {
			if(f.isDirectory()) {
				cats.add(f.getName());
			}
		}
		JList catList = new JList(cats);
		sp = new JScrollPane(images);
		showCategory(cats.firstElement());
		JScrollPane sp2 = new JScrollPane(catList);
		add(sp2,BorderLayout.WEST);
		add(sp,BorderLayout.CENTER);
		catList.addListSelectionListener(this);
	}

	public void showCategory(String category) {
		System.out.println("Showing category "+category);
		images.removeAll();
		File directory = new File("assets/"+category);
		for(File f : directory.listFiles()) {
			images.add(new DraggableIcon("assets/"+category+"/"+f.getName()));
			System.out.println("Loading "+f.getPath());
		}
		sp.revalidate();
		repaint();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		String category = (String) list.getSelectedValue();
		showCategory(category);
	}
}
