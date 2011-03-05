import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel(String file) {
       try {                
          image = ImageIO.read(new File(file));
          setSize(image.getWidth(), image.getHeight());
       } catch (IOException ex) {
    	   JOptionPane.showMessageDialog(getRootPane(), "Error: File does not exist.");
       }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

    }

}