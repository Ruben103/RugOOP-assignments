import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 *
 * @author OOP 2015 TA team.
 */
public class FractalWindow extends JFrame {
	
	private FractalPanel panel;
	
	/**
	 * Constructor of the class. This will create the main window.
	 * @param fractal
	 */
	public FractalWindow(Mandelbrot fractal) {
		super("Mandelbrot fractal"); // Add title.
		
		panel = new FractalPanel(fractal); //Create panel.
		add(panel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE); //What happens when the frame closes
		
		setSize(panel.getWidth() + 32, panel.getHeight() + 32); //Set frame size
		setVisible(true); //make it visible
	}
	
	/**
	 * This class will create the window's panel.
	 */
	private class FractalPanel extends JPanel {
		
		private final int canvasWidth = 512, canvasHeight = 512; //Assign values for width and height.
		private final double xMin = -2, xMax = 1, yMin = -1.5, yMax = 1.5;
		
		private BufferedImage image;
		/**
		 * Panel constructor.
		 * @param f
		 */
		public FractalPanel(Mandelbrot f) {
			
			setSize(canvasWidth, canvasHeight); //Set panel size
			
			image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB); //Create the image
			
			for (int i = 0; i < canvasHeight; i++) { //Loop throught every pixel
				for (int j = 0; j < canvasWidth; j++) {
					double x = xMin + j * (xMax - xMin) / canvasWidth;
					double y = yMin + (canvasHeight - i) * (yMax - yMin) / canvasHeight;
					Color c = f.colorAt(x, y);
					image.setRGB(j, i, c.getRGB()); //Color the pixel
				}
			}
		}
		/**
		 * Draw on the panel.
		 * @param g 
		 */
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, null);
		}
	}
}
