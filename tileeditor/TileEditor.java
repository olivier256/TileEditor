package tileeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class TileEditor extends JFrame {

	private static final int JFRAME_WIDTH = 640;
	private static final int JFRAME_HEIGHT = 480;
	private static final int VSYNC = 16;
	private static final long serialVersionUID = 1L;
	private final Thread refresher;
	private int zoom;

	public TileEditor() {
		super("TileEditor");
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		refresher = new Thread(() -> {
			while (true) {
				repaint();
				sleep(VSYNC);
			}
		});
		zoom = 40;
		setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
		setVisible(true);
		refresher.start();
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void paint(Graphics g) {
		getContentPane().setBackground(Color.WHITE);
		Rectangle bounds = getContentPane().getBounds();
		int contentPaneWidth = getContentPane().getWidth();
		int contentPaneHeight = getContentPane().getHeight();
		Image offScreenImage = getContentPane().createImage(contentPaneWidth, contentPaneHeight);
		Graphics offscreenGraphics = offScreenImage.getGraphics();
		offscreenGraphics.setColor(Color.GRAY);
		offscreenGraphics.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(),
				(int) bounds.getHeight());

		offscreenGraphics.setColor(Color.BLACK);
		for (int row = 0; row < 9; row++) {
			offscreenGraphics.drawLine(0, row * zoom, 8 * zoom, row * zoom);
		}
		for (int col = 0; col < 9; col++) {
			offscreenGraphics.drawLine(col * zoom, 0, col * zoom, 8 * zoom);
		}

		int x = JFRAME_WIDTH - contentPaneWidth;
		int y = JFRAME_HEIGHT - contentPaneHeight;
		g.drawImage(offScreenImage, x, y, null);
	}

	public static void main(String[] args) {
		new TileEditor();
	}

}
