package tileeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import logger.SystemOutLogger;

public class ColorChooser extends JPanel implements MouseListener, MouseMotionListener {
	private static final Logger logger = new SystemOutLogger("ColorChooser");

	private static final long serialVersionUID = 1L;
	private static final int VSYNC = 16;
	private static final int[] COLOR_INTENSITY = { 0, 37, 73, 110, 146, 183, 219, 255 };
	private int zoom;
	private Color lastColor;
	private final transient Consumer<Color> colorConsumer;
	private ColorFrame colorFrame;

	public ColorChooser(Consumer<Color> colorConsumer) {
		zoom = 16;
		addMouseListener(this);
		addMouseMotionListener(this);
		this.colorConsumer = colorConsumer;
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		Image offScreenImage = createImage(getWidth(), getHeight());
		Graphics offscreenGraphics = offScreenImage.getGraphics();
		offscreenGraphics.clearRect(0, 0, getWidth(), getHeight());

		int x = 0;
		int y = 0;
		for (int red : COLOR_INTENSITY) {
			for (int green : COLOR_INTENSITY) {
				for (int blue : COLOR_INTENSITY) {
					Color currentColor = new Color(red, green, blue);
					offscreenGraphics.setColor(currentColor);
					offscreenGraphics.fillRect(x * zoom, y * zoom, zoom, zoom);
					x++;
					if (x > 31) {
						y++;
						x = 0;
					}
				}
			}
		}
		g.drawImage(offScreenImage, 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(32 * zoom, 16 * zoom);

	}

	public void buildFrame() {
		JFrame frame = new JFrame("ColorChooser");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		new Timer(VSYNC, ae -> repaint()).start();
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ColorChooser(c -> logger.info(c.toString())).buildFrame());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			lastColor = getColor(e.getX() / zoom, e.getY() / zoom);
			colorFrame = new ColorFrame(lastColor);
			colorFrame.setVisible(true);
		}
	}

	private Color getColor(int x, int y) {
		int red = COLOR_INTENSITY[y / 2];
		int green = 0;
		if (y % 2 == 0) {
			green = COLOR_INTENSITY[x / 8];
		} else {
			green = COLOR_INTENSITY[4 + x / 8];
		}
		int blue = COLOR_INTENSITY[x % 8];
		return new Color(red, green, blue);
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		colorConsumer.accept(lastColor);
		colorFrame.dispose();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		lastColor = getColor(e.getX() / zoom, e.getY() / zoom);
		colorFrame.setColor(lastColor);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
