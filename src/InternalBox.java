import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class InternalBox extends JInternalFrame {
	private FlowLayout bl;
	private JComponent inner;
	
	InternalBox() {
		super("",true,false,false,false);
		bl = new FlowLayout();
		setLayout(bl);
	}
}
