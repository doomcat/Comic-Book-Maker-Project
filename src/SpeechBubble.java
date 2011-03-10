import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class SpeechBubble extends CanvasIcon {
	private String text;
	private Font font;
	private int w = 120, h = 120, tW = 0, tH = 0; //text width and height
	
	SpeechBubble(String text) {
		//this.text = text
		//String tmpTxt = text;
		this.text = JOptionPane.showInputDialog("Speech bubble text:");
		font = new Font("Dialog", Font.PLAIN, 14);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(font);
		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D bounds = g2.getFont().getStringBounds(text,frc);
		tW = (int)bounds.getWidth(); tH = (int)bounds.getHeight();
		g2.setColor(fgColor);
		g2.drawOval(0, 0, w-1, h-1);
		g2.drawString(text, (w/2)-(tW/2), (h/2)-(tH/2));
	}
	
	public void resize(int w, int h) {
		super.resize(w,h);
	}
	
	public BufferedImage getImage() {
		repaint();
		BufferedImage bufImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		this.paint(bufImage.getGraphics());
		return bufImage;
	}
}
