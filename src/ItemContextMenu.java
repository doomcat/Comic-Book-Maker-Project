import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class ItemContextMenu extends JPopupMenu implements ActionListener {

	ItemContextMenu(Canvas c) {
		super();
		JMenuItem delete = new JMenuItem("Delete");
		add(delete);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
