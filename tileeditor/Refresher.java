package tileeditor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class Refresher extends Timer implements ActionListener {

	private static final long serialVersionUID = 1L;
	private List<Component> components;

	public Refresher(int delay) {
		super(delay, null);
		components = new ArrayList<>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Component component : components) {
			component.repaint();
		}
	}

	public void register(Component component) {
		components.add(component);
	}

	public void unregister(Component component) {
		components.remove(component);
	}

}
