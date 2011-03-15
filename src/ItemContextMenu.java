import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class ItemContextMenu extends JPopupMenu implements ActionListener {
	private CanvasIcon object;
	private boolean isVisible = false;
	private JMenuItem text;
	
	
	ItemContextMenu() {
		super();
		JMenuItem delete = new JMenuItem("Delete");
		text = new JMenuItem("Change Text");
		add(delete); delete.addActionListener(this);
		add(text); text.addActionListener(this);
		text.setVisible(false);
	}
	
	public void popup(CanvasIcon object, int x, int y) {
		if(object instanceof SpeechBubble) text.setVisible(true);
		else { text.setVisible(false); }
		this.object = object;
		this.setLocation(x,y);
		isVisible = true;
		setVisible(true);
	}
	
	public void hide() {
		isVisible = false;
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Delete") {
			SystemState.canvasPointer.getCanvas().deleteSelectedElement();
		}
		hide();
	}


}
