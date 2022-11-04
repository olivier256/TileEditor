package tileeditor;

import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ColorFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public ColorFrame(Color color) throws HeadlessException {
		super();
		getContentPane().setBackground(color);
		setSize(160, 160);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	public void setColor(Color color) {
		getContentPane().setBackground(color);
		repaint();
	}

}
