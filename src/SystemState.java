import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JComponent;

public class SystemState {
	protected static boolean unsaved = false;
	protected static File currentFile;
	protected static CanvasContainer canvasPointer;
	protected static Component glassPane;
	protected static RootWindow rootPane;
	protected static History history;
}
