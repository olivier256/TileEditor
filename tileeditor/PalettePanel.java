package tileeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class PalettePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int VSYNC = 16;
	private int zoom;
	private transient Image offScreenImage;

	public PalettePanel() {
		zoom = 40;
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		if (offScreenImage == null) {
			offScreenImage = createImage(getWidth(), getHeight());
		}
		Graphics offscreenGraphics = offScreenImage.getGraphics();
		offscreenGraphics.clearRect(0, 0, getWidth(), getHeight());

		offscreenGraphics.setColor(Color.BLACK);
		for (int row = 0; row < 17; row++) {
			offscreenGraphics.drawLine(0, row * zoom, 8 * zoom, row * zoom);
		}
		for (int col = 0; col < 5; col++) {
			offscreenGraphics.drawLine(col * zoom, 0, col * zoom, 8 * 2 * zoom);
		}
		g.drawImage(offScreenImage, 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(4 * zoom + 1, 16 * zoom + 1);

	}

	public void buildFrame() {
		JFrame frame = new JFrame("TileEditor");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		new Timer(VSYNC, ae -> repaint()).start();
		zoom = 40;
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PalettePanel().buildFrame());
	}
}
