package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JLabelTest {

	private JFrame frame;
	private JPanel contextPanel;
	private JLabel headLabel;
	private JLabel statusLabel;
	private JButton btnAddLabel;
	
	public static void main(String[] args)
	{
		
		JLabelTest labelTest=new JLabelTest();
		labelTest.prepare();
		labelTest.addLabel();
		
	}
	public void prepare()
	{
		this.frame=new JFrame();
		this.contextPanel=new JPanel();
		this.headLabel=new JLabel("head");
		this.statusLabel=new JLabel("status");
		this.btnAddLabel=new JButton("Add Label");
		this.btnAddLabel.setMaximumSize(new Dimension(50, 40));
		
		this.frame.setSize(500, 400);
		this.frame.setLayout(new GridLayout(4, 1));
		this.frame.addWindowListener(new WindowAdapter()
				{
			public void windowClosing(WindowEvent windowEvent)
			{
				System.out.println("File saved.");
				System.exit(0);
			}
				});
	
		this.btnAddLabel.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						System.out.println(arg0);
						addLabel();
						statusLabel.setText("btn clicked.");
					}
			
				});
		
		this.contextPanel.setLayout(new FlowLayout());
		this.frame.add(headLabel);
		this.frame.add(contextPanel);
		this.frame.add(statusLabel);
		this.frame.add(btnAddLabel);
		this.frame.setVisible(true);
	}
	
	public void addLabel()
	{
		JLabel label=new JLabel("a label added.");
		this.contextPanel.add(label);
		this.frame.setVisible(true);//refresh?
	}
}
