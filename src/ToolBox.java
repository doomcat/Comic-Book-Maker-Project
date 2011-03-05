import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class ToolBox extends InternalBox {
	ToolBox() {
		super();
		setTitle("Tools");
		JLabel test = new JLabel("Hello",SwingConstants.CENTER);
		add(test,BorderLayout.CENTER);
	}
}
