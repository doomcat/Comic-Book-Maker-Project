import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JToolTip;

//SystemState - so we don't have to pass different objects
//to each other in their constructors, anything that is intended
//to be publically accessible (global variables or objects that
//need access from other objects), can be referenced here.

//TODO make serializable, when program runs, restore this state?

public class SystemState {
	protected static boolean unsaved = false;
	protected static CanvasContainer canvasPointer;
	protected static Component glassPane;
	protected static RootWindow rootPane;
	protected static History history;
	protected static File currentFile;
	protected static boolean retainAspect = false;
	protected static Vector<String> errors = new Vector<String>();
	protected static boolean paintMode = false;
}
