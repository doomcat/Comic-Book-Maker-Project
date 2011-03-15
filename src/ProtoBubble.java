import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class ProtoBubble extends CanvasIcon {
	protected String text;
	private Font fontSmall;
	private Font fontBig;
	private Font fontHuge;
	private Font font;
	protected int w = 120; //text width and height
	protected int h = 120;
	protected int tW = 0;
	protected int tH = 0;
	protected Color fgColor = new Color(0,0,0);
	transient private Graphics g;
	protected transient Graphics2D g2;
	
	ProtoBubble(String text, int w, int h) {
		super();
		this.w = w; this.h = h;
		this.text = text;
		fontSmall = new Font("Dialog", Font.PLAIN, 12);
		fontBig = new Font("Dialog", Font.PLAIN, 18);
		fontHuge = new Font("Dialog", Font.PLAIN, 26);
		font = fontSmall;
	}
	
	ProtoBubble(String text) {
		super();
		w = 120; h = 120;
		this.text = text;
		fontSmall = new Font("Dialog", Font.PLAIN, 12);
		fontBig = new Font("Dialog", Font.PLAIN, 18);
		fontHuge = new Font("Dialog", Font.PLAIN, 26);
		font = fontSmall;
	}
	
	public void paint(Graphics g) {
		this.g = g;
		g2 = (Graphics2D) this.g;
		g2.setColor(Color.WHITE);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		calculateFontSize(g2);
		g2.fillOval(0, 0, w-1, h-1);
		g2.setColor(fgColor);
		g2.drawString(text, (w/2)-(tW/2), (h/2)+(tH/4));
	}
	
	public void calculateFontSize(Graphics2D graphics) {
		//I'm sorry, this code has a headache right now and isn't a damn machine
		//Consult Ben the grinding machine on how to make things big
		setFont(fontHuge);
		FontRenderContext frc = graphics.getFontRenderContext();
		Rectangle2D bounds = graphics.getFont().getStringBounds(text,frc);
		tW = (int)bounds.getWidth(); tH = (int)bounds.getHeight();
		if((int)bounds.getWidth() > w-20 || (int)bounds.getHeight() > h-20) setFont(fontBig);
		bounds = graphics.getFont().getStringBounds(text,frc);
		tW = (int)bounds.getWidth(); tH = (int)bounds.getHeight();
		if((int)bounds.getWidth() > w-20 || (int)bounds.getHeight() > h-20) setFont(fontSmall);
		bounds = graphics.getFont().getStringBounds(text,frc);
		tW = (int)bounds.getWidth(); tH = (int)bounds.getHeight();
	}
	
	@Override
	public void resize(int w, int h) {
		super.resize(w,h);
		if(w > 10 && h > 10) {
			this.w = w; this.h = h;
		}
		else{ this.w = 10; this.h = 10; }
	}
	
	public BufferedImage getImage() {
		BufferedImage bufImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		this.paint(bufImage.getGraphics());
		return bufImage;
	}
	
	public void popupChangeText() {
		String input = JOptionPane.showInputDialog("What text should this speech bubble have?");
		if(input != null && input != "") text = input;
		repaint();
		
	}
}
