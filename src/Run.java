import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/*
 	COMIC BOOK MAKER PROJECT
 	Work of:
 		Dean Allen			dea@aber.ac.uk
 		Matthew Hathaway	mah@aber.ac.uk
 		Owain Jones 		odj@aber.ac.uk
 		
 	Run class: contains the main method which
 	instantiates all the interface elements.
 	All of the program logic is done within
 	these classes, Run.main() simply starts
 	everything.
 	
*/

public class Run {

	//TODO make canvas the background element which fills the screen, rather than an extra window. this is a bit difficult to do though
	//TODO speech bubbles with foreground & background colours, custom fonts, etc.
	//TODO prompt to save unsaved changes when closing program
	//TODO button to add speech bubbles
	//TODO thought bubbles and captions
	//TODO flipping images?
	//TODO comic strip FRAMES - important
	//TODO comic strip frame dimension dialog on "File > New"
	//TODO locking images to frame boundaries
	
	Run() {
		//Create all the objects
		RootWindow root = new RootWindow();
		MenuBar menu = new MenuBar();
		
		CanvasContainer canvas = new CanvasContainer();
		SystemState.canvasPointer = canvas;
		
		ImageBox images = new ImageBox();
		ToolBox tools = new ToolBox();
		JDesktopPane desktop = new JDesktopPane();
		desktop.setBackground(new Color(153,217,234));
		History history = new History(canvas.getCanvas());
		
		//Add menu to root layer
		root.add(menu,BorderLayout.NORTH);
		
		//All the windows need to be made visible and given
		//default sizes or they don't show up
		canvas.setVisible(true);
		tools.setVisible(true);
		images.setVisible(true);
		tools.setSize(440,228);
		canvas.setSize(640,480);
		images.setSize(960,142);
		canvas.move(0, 140);
		tools.move(570, 480);
		
		//The toolboxes and canvas then need to be added
		//to the DesktopPane
		desktop.add(tools);
		desktop.add(images);
		desktop.add(canvas);
		
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
		
		//canvas.getCanvas().addToCanvas(new SpeechBubble("Hello",120,120), 40, 40);
		//canvas.getCanvas().addToCanvas(new ComicFrame(120,120),40,40);
		
	}
	
	public static void main(String[] args) {
		Run myApp = new Run();
	}

}
