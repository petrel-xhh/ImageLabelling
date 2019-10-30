package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JMenuBarTest {

	private JFrame frame;
	private JPanel controlPanel;
	private JLabel headLabel;
	private JLabel statusLabel;
	
	public static void main(String[] args)
	{
		JMenuBarTest menuBarTest = new JMenuBarTest();
		menuBarTest.prepare();
		menuBarTest.showMenu();
	}
	public void prepare()
	{
		this.frame = new JFrame();
		this.controlPanel = new JPanel();
		this.headLabel = new JLabel();
		this.statusLabel = new JLabel();
		
		this.headLabel.setText("JRadioButtonTest2");
		this.headLabel.setHorizontalAlignment(JLabel.CENTER);
		this.controlPanel.setLayout(new GridLayout(2, 1));
		this.frame.setSize(400, 400);
		this.frame.setLayout(new GridLayout(3, 1));
		this.frame.addWindowListener(new WindowAdapter()
				{
			@Override
			public void windowClosing(WindowEvent windowEvent)
			{
				System.out.println("window closing");
				System.exit(0);
			}
				});
		this.frame.add(headLabel);
		this.frame.add(controlPanel);
		this.frame.add(statusLabel);
		this.frame.setVisible(true);
	}
	public void showMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu aboutMenu = new JMenu("About");
		
		JMenuItem openMenuItem = new JMenuItem("open");
		JMenuItem saveMenuItem = new JMenuItem("save");
		JMenuItem exitMenuItem = new JMenuItem("exit");
		
		ActionListener menuItemActionListener = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				statusLabel.setText(((JMenuItem)arg0.getSource()).getText());
			}
		};
		openMenuItem.addActionListener(menuItemActionListener);
		saveMenuItem.addActionListener(menuItemActionListener);
		exitMenuItem.addActionListener(menuItemActionListener);
		
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		
		this.frame.setJMenuBar(menuBar);
		this.frame.setVisible(true);
	}
}
