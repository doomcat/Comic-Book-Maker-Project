import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ToolBox extends InternalBox implements ChangeListener, ActionListener {
	private JColorChooser colours;
	
	ToolBox() {
		super();
		setTitle("Tools");
		
		FlowLayout fl = new FlowLayout();
		
		JPanel bC = new JPanel(); //container for the buttons
		bC.setLayout(fl);
		JButton bAddFrame = new JButton("Frame");
		bC.add(bAddFrame); bAddFrame.addActionListener(this);
		JButton bAddBubble = new JButton("Speech Bubble");
		bC.add(bAddBubble);
		JButton bAddThought = new JButton("Thought Bubble");
		bC.add(bAddThought);
		JButton bAddCaption = new JButton("Caption");
		bC.add(bAddCaption);
		add(bC,BorderLayout.NORTH);
		
		colours = new JColorChooser(SystemState.canvasPointer.getCanvas().getBgColor());
		colours.setPreviewPanel(new JPanel());
		colours.setPreferredSize(new Dimension(420,140));
		add(colours,BorderLayout.SOUTH);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Frame") SystemState.canvasPointer.getCanvas().addToCanvas(new ComicFrame(100,100), -1, -1);
	}
}
