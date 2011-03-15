import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class Caption extends ProtoBubble {
	
	Caption(String text) {
		super(text);
	}
	
	Caption(String text, int w, int h) {
		super(text,w,h);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, w, h);
		g2.setColor(fgColor);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(1, 1, super.w-2, super.h-2);
		calculateFontSize(g2);
		g2.drawString(text, (w/2)-(tW/2), (h/2)+(tH/4));
	}
}
