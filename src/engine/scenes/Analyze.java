package engine.scenes;

import javax.swing.BoxLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Analyze extends JPanel {
	
	public Analyze() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(new JTextField("yay", 25));
	}
}
