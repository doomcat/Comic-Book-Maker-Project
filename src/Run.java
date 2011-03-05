import java.awt.BorderLayout;
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
	//TODO create these classes below:
/*
	private ToolBox tools; //toolbox for drawing tools, adding speech bubbles etc
	private PaintBox paint; //choosing colours. possibly could be a child of toolbox
*/
	//TODO make canvas the background element which fills the screen,
	//rather than an extra window. this is a bit difficult to do though
	//TODO fix "save"/"save as" dialog
	
	Run() {
		//Create all the objects
		RootWindow root = new RootWindow();
		MenuBar menu = new MenuBar();
		CanvasContainer canvas = new CanvasContainer();
		ImageBox images = new ImageBox();
		ToolBox tools = new ToolBox();
		JDesktopPane desktop = new JDesktopPane();
		
		//Add menu to root layer
		root.add(menu,BorderLayout.NORTH);
		
		//All the windows need to be made visible and given
		//default sizes or they don't show up
		tools.setVisible(true);
		canvas.setVisible(true);
		images.setVisible(true);
		tools.setSize(320,72);
		canvas.setSize(640,480);
		images.setSize(480,128);
		
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
		root.setSize(new Dimension(720,600));
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Link the parts of system together - they listen
		//and react to events through custom observer-observable
		//static class (SystemState)
		SystemState.rootPane = root;
		SystemState.canvasPointer = canvas;
		SystemState.glassPane = root.getGlassPane();
		
	}
	
	public static void main(String[] args) {
		Run myApp = new Run();
	}

}
