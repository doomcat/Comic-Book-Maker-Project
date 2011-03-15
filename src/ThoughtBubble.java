import java.awt.Color;
import java.awt.Graphics;


public class ThoughtBubble extends ProtoBubble {

	ThoughtBubble(String text) {
		super(text);
	}
	
	ThoughtBubble(String text, int w, int h) {
		super(text,w,h);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		super.g2.setColor(Color.WHITE);
		super.g2.fillOval(0, h-10, 10, 10);
		if(w > 100 && h > 100) {
			super.g2.fillOval(15, h-25, 15, 15);
		}
	}

}
