package engine;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import engine.scenes.Analyze;
import engine.scenes.History;
import engine.scenes.Play;

public class Window extends JFrame {
	private int height = 860;
	private int width = 920;
	private Dimension winDim; // dimensions of the window
	
	//Variables for the scene menu
	private	Container contentPane;
	private JTabbedPane tabs;
	private JPanel playScene;
	private JPanel historyScene;
	private JPanel analyzeScene;
	
	public Window() {
		winDim = new Dimension(width, height);
		setPreferredSize(winDim); // sets the size
		setTitle("Chess Trainer V1.0");
		setLayout(new CardLayout());
		//setLocationRelativeTo(null); // centers the frame
		setResizable(false); // make the window not resizable
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // on close it frees the memory
		init();
		addComponents();
		pack();
		setVisible(true);
	}
	
	private void addComponents()  {
		/**
		 * This function simply adds all of the necessary JComonents
		 * */
		tabs.add("Play" ,playScene);
		tabs.add("Analyze", analyzeScene);
		tabs.add("History", historyScene);
		contentPane.add(tabs, BorderLayout.NORTH);
	}
	
	private void init() {
		/**
		 * This function simply initializes some of the components needed for the chess game such as the clock, etc.
		 * */
		tabs = new JTabbedPane();
		contentPane = getContentPane();
		playScene = new Play();
		historyScene = new History();
		analyzeScene = new Analyze();
	}
		
}
