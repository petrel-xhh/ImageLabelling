package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class JRadioButtonTest2 {

	private JFrame frame;
	private JPanel controlPanel;
	private JLabel headLabel;
	private JLabel statusLabel;
	private JLabel itemTitleLabel;
	
	public static void main(String[] args)
	{
		JRadioButtonTest2 radioButtonTest2 = new JRadioButtonTest2();
		radioButtonTest2.prepare();
		radioButtonTest2.setRadioButtonGroup("Nothing", new String[]{});
		radioButtonTest2.setRadioButtonGroup("Friut", new String[]{"Apple", "Lemon", "Mongo"});
	}
	public void prepare()
	{
		this.frame = new JFrame();
		this.controlPanel = new JPanel();
		this.headLabel = new JLabel();
		this.statusLabel = new JLabel();
		this.itemTitleLabel = new JLabel();
		
		this.headLabel.setText("JRadioButtonTest2");
		this.headLabel.setHorizontalAlignment(JLabel.CENTER);
		this.controlPanel.setLayout(new GridLayout(2, 1));
		this.itemTitleLabel.setHorizontalAlignment(JLabel.LEFT);
		this.controlPanel.add(itemTitleLabel);
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
	/**
	 * 根据字符串数组来生成RadioButton
	 * @param items
	 */
	public void setRadioButtonGroup(String itemTitle, String[] items)
	{
		ButtonGroup btnGroup = new ButtonGroup();
		
		this.itemTitleLabel.setText(itemTitle);
		JRadioButton[] rabtns = new JRadioButton[items.length];
		
		for(int i = 0; i<items.length;i++)
		{
			rabtns[i]=new JRadioButton(items[i]);
			
			final String item = items[i];
			rabtns[i].addActionListener(new ActionListener()
					{

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							statusLabel.setText(item);
						}
					});
		}
		if(this.controlPanel.getComponentCount()==2)
		{
			this.controlPanel.remove(1);
		}
		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new GridLayout(items.length, 1));
		
		for(JRadioButton rabtn : rabtns)
		{
			itemPanel.add(rabtn);
			btnGroup.add(rabtn);
		}
		this.controlPanel.add(itemPanel);
		this.frame.setVisible(true);
	}
}
