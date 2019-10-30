package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JFileChooserTest {

	private JFrame frame;
	private JPanel controlPanel;
	private JLabel headLabel;
	private JLabel statusLabel;
	
	public static void main(String[] args)
	{
		JFileChooserTest fileChooserTest = new JFileChooserTest();
		fileChooserTest.prepare();
		fileChooserTest.showFileChooser();
	}
	public void prepare()
	{
		this.frame = new JFrame();
		this.controlPanel = new JPanel();
		this.headLabel = new JLabel();
		this.statusLabel = new JLabel();
		
		this.headLabel.setHorizontalAlignment(JLabel.CENTER);
		this.controlPanel.setLayout(new FlowLayout());
		this.frame.setSize(400, 400);
		this.frame.setLayout(new GridLayout(3, 1));
		this.frame.addWindowListener(new WindowAdapter()
				{
			@Override
			public void windowClosing(WindowEvent windowEvent)
			{
				int result = JOptionPane.showConfirmDialog(frame, "保存文件吗", "灵魂拷问", JOptionPane.YES_NO_CANCEL_OPTION);
				switch(result)
				{
				case JOptionPane.OK_OPTION:
					System.out.println("File saved");
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
					System.out.println("File not saved");
					System.exit(0);
					break;
				case JOptionPane.CANCEL_OPTION:
					frame.setDefaultCloseOperation(JFrame.FRAMEBITS);
					break;
				}
			}
				});
		
		this.frame.add(headLabel);
		this.frame.add(controlPanel);
		this.frame.add(statusLabel);
		this.frame.setVisible(true);
		
	}
	public void showFileChooser()
	{
		JButton btnOpenFile = new JButton();
		btnOpenFile.setText("打开文件");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		btnOpenFile.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent arg0) {
						int result = fileChooser.showOpenDialog(frame);
						switch(result)
						{
						case JFileChooser.APPROVE_OPTION:
							statusLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
							break;
						case JFileChooser.CANCEL_OPTION:
							statusLabel.setText("selection cancelled");
							break;
						}
					}
			
				});
		this.controlPanel.add(btnOpenFile);
		this.frame.setVisible(true);
	}
}
