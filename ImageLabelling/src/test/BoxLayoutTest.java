package test;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BoxLayoutTest {

	private JFrame frame;
	private JLabel statusLabel;
	private JLabel imageLabel;
	private JButton previousButton;
	private JButton nextButton;
	private java.util.List<String> imagePathList;
	private int index;
	
	public static void main(String[] args)
	{
		BoxLayoutTest blt = new BoxLayoutTest();
		blt.prepare();
		blt.init();
	}
	public void prepare()
	{
		this.frame = new JFrame();
		
		this.statusLabel = new JLabel();
		this.imageLabel = new JLabel();
		this.previousButton = new JButton("Previous");
		this.nextButton = new JButton("Next");
		
		
		Box mainBox = Box.createVerticalBox();
		Box imageBox = Box.createVerticalBox();
		
		Box btnBox = Box.createHorizontalBox();
		//btnBox.add(Box.createHorizontalStrut(10));
		btnBox.add(Box.createRigidArea(new Dimension(10, 20)));
		btnBox.add(previousButton);
		btnBox.add(Box.createHorizontalGlue());
		btnBox.add(nextButton);
		btnBox.add(Box.createRigidArea(new Dimension(10, 20)));
		
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		Box imageInnerBox = Box.createHorizontalBox();
		imageInnerBox.add(Box.createRigidArea(new Dimension(10, 400)));
		imageInnerBox.add(imageLabel);
		imageInnerBox.add(Box.createRigidArea(new Dimension(10, 400)));

		imageBox.add(imageInnerBox);
		//imageBox.add(Box.createVerticalGlue());
		imageBox.add(btnBox);
		//imageBox.setMaximumSize(new Dimension(600, 500));
		
		Box statusBox = Box.createHorizontalBox();
		statusBox.add(Box.createRigidArea(new Dimension(10, 20)));
		statusBox.add(statusLabel);
		statusBox.add(Box.createHorizontalGlue());
		mainBox.add(imageBox);
		mainBox.add(Box.createVerticalStrut(10));
		mainBox.add(statusBox);
		
		this.frame.setContentPane(mainBox);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600, 500);
		this.frame.setVisible(true);
	}
	public void init()
	{
		//load images
		String path = "/home/petrel/Documents/project/WMS search/data/WMS/800x400/layer_continent_800_400_transparent=false/Africa";
		File file = new File(path);
		if(!file.exists())
		{
			this.statusLabel.setText("dir not exist.");
		}
		else
		{
			this.imagePathList = new java.util.ArrayList<>();
			for(File tmp_file : file.listFiles())
			{
				String tmp_path = tmp_file.getAbsolutePath();
				if(tmp_path.endsWith("jpg"))
				{
					this.imagePathList.add(tmp_path);
				}
			}
			if(this.imagePathList.isEmpty())
			{
				this.statusLabel.setText("no jpg file found in "+path);
			}
			else
			{
				updateImage(null);
			}
			
		}
		
		this.previousButton.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent arg0) {
						updateImage("P");
					}
			
				});
		this.nextButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateImage("N");
			}
	
		});
	}
	public void updateImage(String type)
	{
		if(this.imagePathList==null || this.imagePathList.isEmpty())
			return;
		if(type==null)
		{
			this.index = 0;
		}
		else if(type.equals("P"))
		{
			this.index = ((--this.index) + this.imagePathList.size()) % this.imagePathList.size();
		}
		else if(type.equals("N"))
		{
			this.index = (++this.index) % this.imagePathList.size();
		}
		else
		{
			return;
		}
		
		String tmp_path = this.imagePathList.get(index);
		BufferedImage tmp_image = this.getImage(tmp_path, 560, 300);
		if(null == tmp_image)
		{
			this.statusLabel.setText("can not load image: "+tmp_path);
		}
		else
		{
			ImageIcon icon = new ImageIcon();
			icon.setImage(tmp_image);
			this.imageLabel.setIcon(icon);
			this.statusLabel.setText("..."+tmp_path.substring(Math.max(0, tmp_path.length()-60)));
			
		}
		this.frame.setVisible(true);
	}
	private BufferedImage getImage(String path, int width, int height)
	{
		BufferedImage image = this.getImage(path);
		if(null == image)
		{
			return null;
		}
		return this.resize(image, width, height);
	}
	private BufferedImage getImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private BufferedImage resize(BufferedImage image, int width, int height)
	{
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		result.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_FAST), 0, 0, null);
		return result;
	}
}
