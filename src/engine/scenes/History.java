package engine.scenes;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class History extends JPanel{
	public History() {
		setLayout(new BoxLayout((Container) this, BoxLayout.PAGE_AXIS));
		add(new JTextField("yay", 25));
	}
}
