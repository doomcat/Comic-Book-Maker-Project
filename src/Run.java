import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 	COMIC BOOK MAKER PROJECT
 	Work of:
 		Dean Allen			dea1@aber.ac.uk
 		Matthew Hathaway	mah19@aber.ac.uk
 		Owain Jones 		odj@aber.ac.uk
 		
 	Run class: contains the main method which
 	instantiates all the interface elements.
 	All of the program logic is done within
 	these classes, Run.main() simply starts
 	everything.
 	
*/

public class Run {
	//TODO make canvas the background element which fills the screen, rather than an extra window. this is a bit difficult to do though
	//TODO images as background
	//TODO being able to paint on canvas
	//TODO prompt to save unsaved changes when closing program
	//TODO comic strip FRAMES - important
	//TODO comic strip frame dimension dialog on "File > New"
	//TODO locking images to frame boundaries
	
	private CanvasContainer canvas;
	private JDesktopPane desktop;
	private RootWindow root;
	
	Run() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Create all the objects
		root = new RootWindow();
		MenuBar menu = new MenuBar();

		canvas = new CanvasContainer();
		SystemState.canvasPointer = canvas;
		
		ImageBox images = new ImageBox();
		ToolBox tools = new ToolBox();
		desktop = new JDesktopPane();
		desktop.setBackground(new Color(153,217,234));
		History history = new History(canvas.getCanvas());
		
		//Add menu to root layer
		root.add(menu,BorderLayout.NORTH);
		
		//The toolboxes and canvas then need to be added
		//to the DesktopPane
		root.desktop = desktop;
		root.canvas = canvas;
		desktop.add(canvas,1);
		desktop.add(tools,0);
		desktop.add(images,0);
		
		//All the windows need to be made visible and given
		//default sizes or they don't show up
		canvas.setVisible(true);
		tools.setVisible(true);
		images.setVisible(true);
		tools.setSize(440,270);
		canvas.setSize(desktop.getSize());
		images.setSize(960,142);
		tools.move(570, 440);
		
		//Then add desktop to root and make sure it takes
		//up as much space as it can (BorderLayout.CENTER)
		root.add(desktop,BorderLayout.CENTER);
		
		//MAKE IT HAPPEN BIZZATCH.
		root.setTitle("Comic Book Maker");
		root.setVisible(true);
		root.setSize(new Dimension(1024,768));
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Link the parts of system together - they listen
		//and react to events through custom observer-observable
		//static class (SystemState)
		SystemState.rootPane = root;
		SystemState.glassPane = root.getGlassPane();
		SystemState.history = history;

	}
	
	public static void main(String[] args) {
			Run myApp = new Run();
	}

}
