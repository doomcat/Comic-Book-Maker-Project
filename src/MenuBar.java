
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class MenuBar extends JMenuBar implements ActionListener,ItemListener {
	
	MenuBar() {
		JMenu mFile = new JMenu("File");
		JMenuItem oNew = new JMenuItem("New");
		
		JMenuItem omSave = new JMenu("Save");
		JMenuItem oExport = new JMenuItem("Export");
		JMenuItem oSave = new JMenuItem("Save");
		JMenuItem oSaveAs = new JMenuItem("Save As...");

		JMenuItem oOpen = new JMenuItem("Open");
		JMenuItem oExit = new JMenuItem("Exit");
		
		mFile.add(oNew);
		oNew.addActionListener(this);
		mFile.add(omSave);
		omSave.add(oSave);
		oSave.addActionListener(this);
		omSave.add(oSaveAs);
		oSaveAs.addActionListener(this);
		omSave.add(oExport);
		omSave.addActionListener(this);
		mFile.add(oOpen);
		oOpen.addActionListener(this);
		mFile.add(oExit);
		oExit.addActionListener(this);
		
		JMenu mEdit = new JMenu("Edit");
		JMenuItem oUndo = new JMenuItem("Undo");
		JMenuItem oRedo = new JMenuItem("Redo");
		mEdit.add(oUndo);
		oUndo.addActionListener(this);
		mEdit.add(oRedo);
		oRedo.addActionListener(this);
		
		JMenu mHelp = new JMenu("Help");
		JMenuItem oHate = new JMenuItem("Haters Gonna Hate");
		mHelp.add(oHate);
		
		add(mFile);
		add(mEdit);
		add(mHelp);
		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		System.out.println(arg0.getStateChange());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO Add file loading/saving functionality
		//TODO Add ability to export canvas as .png
		String action = e.getActionCommand();
		if(action == "Exit") {
			int n = 0;
			if(SystemState.unsaved) {
				n = JOptionPane.showConfirmDialog(
						JOptionPane.getRootFrame(),
						"You have unsaved work!\nAre you sure you want to quit?",
						"Wait!",
						JOptionPane.YES_NO_OPTION
				);
			}
			if(n == 0) { System.exit(0); }
		} else if(action == "Undo") {
			System.out.println("Doing undo");
			SystemState.canvasPointer.setCanvas(
					SystemState.history.undo()
			);
		} else if(action == "Redo") {
			SystemState.canvasPointer.setCanvas(
					SystemState.history.redo()
			);
		} else if(action == "New") {
			SystemState.canvasPointer.getCanvas().clear();
			SystemState.history.clear();
			SystemState.unsaved = true;
		} else if(action == "Save") {
			JFileChooser fc = new JFileChooser();
			fc.showSaveDialog(SystemState.rootPane);
			File f = fc.getSelectedFile();
			SystemState.canvasPointer.getCanvas().saveCanvasAs(f.getAbsolutePath());
			SystemState.unsaved = false;	
		} else if(action == "Open") {
			JFileChooser fc = new JFileChooser();
			fc.showOpenDialog(SystemState.rootPane);
			try {
				FileInputStream f = new FileInputStream(fc.getSelectedFile().getAbsolutePath());
				ObjectInputStream in = new ObjectInputStream(f);
				Canvas canvas = (Canvas)in.readObject();
				SystemState.canvasPointer.setCanvas(canvas);
				in.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			SystemState.unsaved = true;
		}
	}
	
}
