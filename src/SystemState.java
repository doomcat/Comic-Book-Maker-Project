import java.awt.Component;
import java.io.File;
import java.io.Serializable;
import javax.swing.JComponent;

public class SystemState {
	protected static boolean unsaved = false;
	protected static File currentFile;
	protected static CanvasContainer canvasPointer;
	protected static Component glassPane;
	protected static Component rootPane;
}
