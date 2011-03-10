import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ToolBox extends InternalBox implements ChangeListener {
	private JColorChooser colours;
	
	ToolBox() {
		super();
		setTitle("Tools");
		//JLabel test = new JLabel("Hello",SwingConstants.CENTER);
		//add(test,BorderLayout.CENTER);
		colours = new JColorChooser(SystemState.canvasPointer.getCanvas().getBgColor());
		colours.setPreviewPanel(new JPanel());
		//AbstractColorChooserPanel panels[] = { new DefaultColorChooserPanel() };
		//colours.setChooserPanels(panels);
		colours.setPreferredSize(new Dimension(420,140));
		add(colours,BorderLayout.WEST);
		colours.getSelectionModel().addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		CanvasIcon selected = SystemState.canvasPointer.getCanvas().getSelectedElement();
		if(selected != null) {
			selected.setFgColor(colours.getColor());
		} else {
			SystemState.canvasPointer.getCanvas().setBgColor(colours.getColor());
		}
		SystemState.canvasPointer.getCanvas().repaint();
	}
}
