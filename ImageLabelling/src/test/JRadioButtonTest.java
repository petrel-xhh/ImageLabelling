package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JRadioButtonTest {

	
	private JFrame frame;
	private JPanel controlPanel;
	private JLabel headLabel;
	private JLabel statusLabel;
	
	public static void main(String[] args)
	{
		JRadioButtonTest rabtnTest = new JRadioButtonTest();
		rabtnTest.prepare();
		rabtnTest.showRadioButton();
	}
	
	public void prepare()
	{
		this.frame = new JFrame();
		this.controlPanel = new JPanel();
		this.headLabel = new JLabel();
		this.statusLabel = new JLabel();
		
		this.headLabel.setText("JRadioButtonTest");
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
	public void showRadioButton()
	{
		JRadioButton rabtnApple = new JRadioButton("Apple");
		JRadioButton rabtnLemon = new JRadioButton("Lemon");
		JRadioButton rabtnMongo = new JRadioButton("Mongo");
		ButtonGroup btnGroupFriut = new ButtonGroup();
		btnGroupFriut.add(rabtnApple);
		btnGroupFriut.add(rabtnLemon);
		btnGroupFriut.add(rabtnMongo);
		rabtnApple.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						statusLabel.setText("apple selected.");
					}
			
				});
		rabtnLemon.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						statusLabel.setText("lemon selected.");
					}
				});
		rabtnMongo.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						statusLabel.setText("mongo selected");
					}
				});
		this.controlPanel.add(rabtnApple);
		this.controlPanel.add(rabtnLemon);
		this.controlPanel.add(rabtnMongo);
		
		this.frame.setVisible(true);
	}
}
