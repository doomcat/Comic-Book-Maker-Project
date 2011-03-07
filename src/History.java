import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class History {
	private Vector<byte[]> history;
	private int historyIndex;
	private ByteArrayInputStream bis;
	private ObjectInputStream in;
	private ByteArrayOutputStream bos;
	private ObjectOutputStream out;
	
	History() {
		history = new Vector<byte[]>();
		historyIndex = -1;
	}
	
	private Canvas getCanvasAt(int i) throws ArrayIndexOutOfBoundsException {
		try{
			bis = new ByteArrayInputStream(
					history.elementAt(i)
			);
			in = new ObjectInputStream(bis);
			return (Canvas) in.readObject();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void addToHistory(Canvas c) {
		bos = new ByteArrayOutputStream();
		try {
			out = new ObjectOutputStream(bos);
			if(history.size() > 0) {
				for(int i=history.size()-1; i>historyIndex; i--) {
					System.out.println("Removing deprecated history at "+i+",size: "+history.size());
					history.remove(i);
				}
			}	
			out.writeObject(c);
			history.add(bos.toByteArray());
			historyIndex++;
			System.out.println("Adding to history, index: "+historyIndex+", size: "+history.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Canvas undo() {
		System.out.println("Calling undo, index: "+historyIndex+", size: "+history.size());
		if(historyIndex > 0) historyIndex--;
		return getCanvasAt(historyIndex);
	}
	
	public Canvas redo() {
		try{ 
			System.out.println("Calling redo, index: "+historyIndex+", size: "+history.size());
			if(historyIndex < history.size()) historyIndex++;
			else{ historyIndex = history.size()-1; }
			return getCanvasAt(historyIndex);
		} catch(ArrayIndexOutOfBoundsException e) { return getCanvasAt(history.size()-1); }
	}
	
	public void clear() {
		history.clear();
		historyIndex = -1;
	}
}
