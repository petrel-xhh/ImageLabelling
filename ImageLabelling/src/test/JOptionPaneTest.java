package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JOptionPaneTest {

	private JFrame frame;
	private JPanel controlPanel;
	private JLabel headLabel;
	private JLabel statusLabel;
	
	public static void main(String[] args)
	{
		JOptionPaneTest optionPaneTest = new JOptionPaneTest();
		optionPaneTest.prepare();
		optionPaneTest.addButton();
	}
	public void prepare()
	{
		this.frame = new JFrame();
		this.controlPanel = new JPanel();
		this.headLabel = new JLabel();
		this.statusLabel = new JLabel();
		
		this.headLabel.setText("JOptionPaneTest");
		this.headLabel.setHorizontalAlignment(JLabel.CENTER);
		this.controlPanel.setLayout(new FlowLayout());
		this.frame.setSize(400, 400);
		this.frame.setLayout(new GridLayout(3, 1));
		this.frame.addWindowListener(new WindowAdapter()
				{
			@Override
			public void windowClosing(WindowEvent windowEvent)
			{
				System.out.println("File saved");
				System.exit(0);
			}
				});
		
		this.frame.add(headLabel);
		this.frame.add(controlPanel);
		this.frame.add(statusLabel);
		this.frame.setVisible(true);
	}
	public void addButton()
	{
		JButton btn = new JButton();
		btn.setText("Click me");
		this.controlPanel.add(btn);
		btn.addActionListener(new ActionListener()
				{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "文件已保存");
				
			}
				});
		this.frame.setVisible(true);
	}
}
