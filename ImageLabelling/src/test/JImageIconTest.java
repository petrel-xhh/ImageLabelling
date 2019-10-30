package test;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

public class JImageIconTest {

	private JFrame frame;
	private JPanel controlPanel;
	private JLabel headLabel;
	private JLabel statusLabel;
	private JLabel imageLabel;
	private JPanel imageControlPanel;
	private JButton btnPrevious;
	private JButton btnNext;
	private int index;
	private java.util.List<String> imagePathList;
	
	public static void main(String[] args)
	{
		JImageIconTest imageIconTest = new JImageIconTest();
		imageIconTest.prepare();
		String path = "/home/petrel/Documents/project/WMS search/data/WMS/800x400/layer_continent_800_400_transparent=false/Africa";
		imageIconTest.loadImage(path);
	}
	public void prepare()
	{
		this.frame = new JFrame();
		this.controlPanel = new JPanel();
		this.headLabel = new JLabel();
		this.statusLabel = new JLabel();
		this.imageLabel = new JLabel();
		this.imageControlPanel = new JPanel();
		this.btnPrevious = new JButton();
		this.btnNext = new JButton();
		
		this.headLabel.setText("JImageIconTest");
		this.headLabel.setHorizontalAlignment(JLabel.CENTER);
		this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
		this.imageLabel.setMinimumSize(new Dimension(300, 300));

		this.btnPrevious.setText("Previous");
		this.btnPrevious.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						updateImage("Previous");
					}
			
				});
		this.btnNext.registerKeyboardAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				updateImage("Previous");
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.btnNext.setText("Next");
		this.btnNext.registerKeyboardAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				updateImage("Next");
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		this.btnNext.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				updateImage("Next");
			}
	
		});	
		this.imageControlPanel.setLayout(new GridLayout(1, 2));
		this.imageControlPanel.add(btnPrevious);
		this.imageControlPanel.add(btnNext);
		this.controlPanel.setLayout(new GridLayout(2, 1));
		this.controlPanel.add(imageLabel);
		this.controlPanel.add(imageControlPanel);
		
		this.frame.setSize(500, 500);
		this.frame.setLayout(new GridLayout(3, 1));
		this.frame.addWindowListener(new WindowAdapter()
				{
			@Override
			public void windowClosing(WindowEvent windowEvent)
			{
				System.out.println("window closing.");
				System.exit(0);
			}
				});
		
		
		this.frame.add(headLabel);
		this.frame.add(controlPanel);
		this.frame.add(statusLabel);
		this.frame.setVisible(true);
	}
	public void updateImage(String controlType)
	{
		if(imagePathList != null && imagePathList.size()>0)
		{
			ImageIcon icon = new ImageIcon();
			
			if(null==controlType)
			{
				index = 0;
			}
			else if(controlType.equals("Previous"))
			{
				index = (--index + imagePathList.size()) % imagePathList.size();
			}
			else if(controlType.equals("Next"))
			{
				index = (++index) % imagePathList.size();
			}else
			{
				index = 0;
			}
			BufferedImage image = getImage(imagePathList.get(index));
			icon.setImage(image);
			imageLabel.setIcon(icon);
			statusLabel.setText(imagePathList.get(index));
			frame.setVisible(true);
		}
	}
	public BufferedImage getImage(String path)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	public void loadImage(String dir)
	{
		File imageDir = new File(dir);
		if(!imageDir.exists())
		{
			this.statusLabel.setText("dir do not exist.");
			return;
		}
		java.util.List<String> imagePathList = new java.util.ArrayList<String>();
		for(File file : imageDir.listFiles())
		{
			if(file.getName().toString().toLowerCase().endsWith("jpg"))
			{
				imagePathList.add(file.getAbsolutePath());
			}
		}
		if(imagePathList.size() == 0)
		{
			this.statusLabel.setText("dir do not have jpg file");
		}
		else
		{
			Collections.sort(imagePathList);
			this.index = 0;
			this.imagePathList = imagePathList;
			updateImage(null);
		}
		
	}
	
}
