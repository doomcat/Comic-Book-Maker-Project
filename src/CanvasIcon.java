import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class CanvasIcon extends JComponent implements Serializable {
	transient private BufferedImage image;
	private String file;
	private int[] serializableImage;
	protected int w = 0, h = 0, x = 0, y = 0, dW = 0, dH = 0;
	private boolean selected;
	protected Color fgColor = new Color(0,0,0);
	
	CanvasIcon() { } //for SpeechBubble, ThoughtBubble etc. to override
	
	CanvasIcon(String file) {
		this.file = file;
		try {
			image = ImageIO.read(new File(file));
			//serializableImage = ImageIO.
			dW = image.getWidth(); dH = image.getHeight();
			w = dW; h = dH;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getRootPane(), "Sorry! The file couldn't be opened :(");
		}
		//image.
	}
	
	/*public void readObject(ObjectInputStream aStream) throws IOException, ClassNotFoundException {
		aStream.defaultReadObject();
		//BufferedImage
	}*/
	
	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;
	}
	
	public BufferedImage getImage() { return image; }
	public int getWidth() { return w; }
	public int getDefaultWidth() { return dW; } 
	public int getHeight() { return h; }
	public int getDefaultHeight() { return dH; }
	public boolean isSelected() { return selected; }
	public void setSelected(boolean s) { selected = s; }
	public void setFgColor(Color c) { fgColor = c; }
	
	public int getcX() { return x; }
	public void setcX(int x) { this.x = x; }
	public int getcY() { return y; }
	public void setcY(int y) { this.y = y; }
}
