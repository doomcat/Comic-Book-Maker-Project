package comicApplet;
import CanvasContainer;
import History;
import ImageBox;
import MenuBar;
import SystemState;
import ToolBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Applet extends JApplet {
	private CanvasContainer canvas;
	private JDesktopPane desktop;
	private JPanel root;
	private int canvasW = 0, canvasH = 0;
	
	public void createGUI() {
		root = new JPanel();
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
		root.setVisible(true);
		root.setSize(new Dimension(1024,768));
		
		//Link the parts of system together - they listen
		//and react to events through custom observer-observable
		//static class (SystemState)
		SystemState.rootPane = this;
		SystemState.glassPane = this.getGlassPane();
		SystemState.history = history;
		
		//Applet version loads resources differently.
		SystemState.isApplet = true;
	}
	
    public void init() {

        //Execute a job on the event-dispatching thread:
        //creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println("createGUI didn't successfully complete");
        }
    }
}
