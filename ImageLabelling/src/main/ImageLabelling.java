package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import util.FileHelp;

public class ImageLabelling {

	private final static String[] FORMAT_STRINGS = {"jpg"};
	private final Dimension IMAGE_SIZE = new Dimension(640, 400);
	private final Dimension FRAME_SIZE = new Dimension(900, 520);
	private JFrame frame;
	private JLabel statusLabel;
	private JLabel imageLabel;
	private JButton previousButton;
	private JButton nextButton;
	private JButton nextUnlabelledButton;
	private Box radioBox;
	private JMenuItem openDirMenuItem;
	private JMenuItem openRadioFileMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	private Map<JLabel, java.util.List<JRadioButton>> radioGroups;
	private List<String> imageAbsolutePathList;
	private int imageIndex;
	private ImageFileHandler imageFileHandler;
	private RadioFileHandler radioFileHandler;

	public static void main(String[] args)
	{
		ImageLabelling imageLabelling = new ImageLabelling();
		imageLabelling.prepareUI();
		imageLabelling.addLogic();
	}
	
	private void prepareUI()
	{
		//instance
		this.frame = new JFrame("图片标注");
		this.statusLabel = new JLabel();
		this.imageLabel = new JLabel();
		this.previousButton = new JButton("Previous");
		this.nextButton = new JButton("Next");
		this.nextUnlabelledButton = new JButton("Next Unlabelled");
		this.radioBox = Box.createVerticalBox();
		
		//setLayout
		Box mainBox = Box.createHorizontalBox();
		Box leftBox = Box.createVerticalBox();
		Box imageBox = Box.createVerticalBox();
		
		Box btnBox = Box.createHorizontalBox();
		//btnBox.add(Box.createHorizontalStrut(10));
		btnBox.add(Box.createRigidArea(new Dimension(10, 20)));	//put a gap between the left side and the previousButton
		btnBox.add(previousButton);
		btnBox.add(Box.createHorizontalGlue());
		btnBox.add(nextButton);
		btnBox.add(Box.createHorizontalGlue());
		btnBox.add(nextUnlabelledButton);
		btnBox.add(Box.createRigidArea(new Dimension(10, 20)));//put a fixed gap between the right side and the nextButton
		
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));	//show the border of imageLabel
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		Box imageInnerBox = Box.createHorizontalBox();
		imageInnerBox.add(Box.createRigidArea(new Dimension(10, IMAGE_SIZE.height)));// fix the height of imageLabel
		imageInnerBox.add(imageLabel);
		imageInnerBox.add(Box.createRigidArea(new Dimension(10, IMAGE_SIZE.height)));

		imageBox.add(imageInnerBox);
		//imageBox.add(Box.createVerticalGlue());
		imageBox.add(btnBox);
		//imageBox.setMaximumSize(new Dimension(600, 500));
		
		Box statusBox = Box.createHorizontalBox();
		//statusBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		statusBox.add(Box.createRigidArea(new Dimension(10, 20)));	//put a fixed gap between the right size and the statusLabel
		statusBox.add(statusLabel);
		statusBox.add(Box.createHorizontalGlue());
		leftBox.add(Box.createRigidArea(new Dimension((int)(FRAME_SIZE.width*0.65), 2)));	//fix the width of leftBox
		leftBox.add(imageBox);
		leftBox.add(Box.createVerticalStrut(10));
		leftBox.add(statusBox);
		
		Box rightBox = Box.createVerticalBox();
		//Box rightBox = Box.createHorizontalBox();
		//this.radioBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		rightBox.add(this.radioBox);
		rightBox.add(Box.createVerticalGlue());
		//rightBox.add(Box.createHorizontalGlue());
		//rightBox.add(Box.createRigidArea(new Dimension((int)(FRAME_SIZE.width*0.35), 10)));
		rightBox.setAlignmentX(Box.LEFT_ALIGNMENT);
		
		mainBox.add(leftBox);

		mainBox.add(Box.createRigidArea(new Dimension(10, FRAME_SIZE.height)));
		mainBox.add(rightBox);
		mainBox.add(Box.createHorizontalGlue());
		
		this.frame.setContentPane(mainBox);
		
		//add menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu aboutMenu = new JMenu("About");
		
		this.openDirMenuItem = new JMenuItem("openImageDir");
		this.openRadioFileMenuItem = new JMenuItem("openRadioFile");
		this.saveMenuItem = new JMenuItem("save");
		this.exitMenuItem = new JMenuItem("exit");
		
		fileMenu.add(this.openDirMenuItem);
		fileMenu.add(this.openRadioFileMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		this.frame.setJMenuBar(menuBar);
		
		this.frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent windowEvent)
			{
				if(imageFileHandler != null)
				{
					try {
						imageFileHandler.saveFile();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
		//this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(FRAME_SIZE);
		this.frame.setLocationRelativeTo(null);
		this.statusLabel.setText("Welcome");
		this.frame.setVisible(true);
	}
	private void initRadioPanel(List<String> itemKeys, List<List<String>> itemValuesList)
	{
		//TODO save file
		
		
		//clean the old radio groups at first.
		this.radioBox.removeAll();
		this.radioBox.setAlignmentX(Box.LEFT_ALIGNMENT);
		this.radioGroups = new HashMap<>();
		
		//start to add new radio groups
		for(int i=0;i<itemKeys.size();i++)
		{
			String item = itemKeys.get(i);
			List<String> itemValues = itemValuesList.get(i);
			JLabel itemLabel = new JLabel(item);
			java.util.List<JRadioButton> itemValueButtonList = new java.util.ArrayList<>(); 
			final ButtonGroup itemValueButtonGroup = new ButtonGroup();
			
			Box tmpItemBox = Box.createVerticalBox();
			Box itemLabelBox = Box.createHorizontalBox();
			itemLabelBox.add(Box.createHorizontalStrut(2));
			itemLabelBox.add(itemLabel);
			itemLabelBox.add(Box.createHorizontalGlue());
			tmpItemBox.add(itemLabelBox);
			itemValues.forEach(itemValue->
			{
				JRadioButton itemValueButton = new JRadioButton(itemValue);
				itemValueButton.setSelected(false);
				itemValueButtonGroup.add(itemValueButton);
				itemValueButtonList.add(itemValueButton);
				
				Box tmpItemValueBox = Box.createHorizontalBox();
				tmpItemValueBox.add(Box.createHorizontalStrut(20));
				tmpItemValueBox.add(itemValueButton);
				tmpItemValueBox.add(Box.createHorizontalGlue());
				tmpItemBox.add(tmpItemValueBox);
				//tmpItemBox.add(Box.createVerticalStrut(2));
				
			});
			this.radioGroups.put(itemLabel, itemValueButtonList);
			this.radioBox.add(tmpItemBox);
		}

		this.radioBox.add(Box.createVerticalGlue());
		this.frame.setVisible(true);
	}
	private java.util.List<JRadioButton> getItemValueRadioButtonGroup(String radioLabelText)
	{
		Iterator<JLabel> radioLabelIterator = this.radioGroups.keySet().iterator();
		while(radioLabelIterator.hasNext())
		{
			JLabel tmpRadioLabel = radioLabelIterator.next();
			if(tmpRadioLabel.getText().equals(radioLabelText))
			{
				return this.radioGroups.get(tmpRadioLabel);
			}
		}
		return null;
		
	}
	private JRadioButton getItemValueRadioButton(String radioLabelText, String radioButtonText)
	{
		java.util.List<JRadioButton> radioButtonGroup = getItemValueRadioButtonGroup(radioLabelText);
		if(radioButtonGroup == null)
		{
			return null;
		}
		for(int i=0;i<radioButtonGroup.size();i++)
		{
			JRadioButton tmpRadioButton = radioButtonGroup.get(i);
			if(tmpRadioButton.getText().equals(radioButtonText))
			{
				return tmpRadioButton;
			}
		}
		return null;
	}
	private void setRadioPanelSelectionState(Map<String, String> imageItemValues)
	{
		if(this.radioBox.getComponentCount() == 0 || imageItemValues == null)
		{
			return;
		}
		imageItemValues.forEach((tmpItem, tmpItemValue)->
		{
			JRadioButton tmpRadioButton = getItemValueRadioButton(tmpItem, tmpItemValue);
			if(tmpRadioButton != null)
			{
				tmpRadioButton.setSelected(true);
			}
		});
	}
	private Map<String, String> getRadioPanelSelectionState()
	{
		if(this.radioGroups == null)
		{
			return null;
		}
		Map<String, String> result = new HashMap<>();
		this.radioGroups.forEach((itemLabel, itemButtonGroup)->
		{
			String itemLabelText = itemLabel.getText();
			String itemButtonText = null;
			for(int i = 0;i<itemButtonGroup.size();i++)
			{
				JRadioButton tmpRadioButton = itemButtonGroup.get(i);
				if(tmpRadioButton.isSelected())
				{
					itemButtonText = tmpRadioButton.getText();
					break;
				}
			}
			result.put(itemLabelText, itemButtonText);
		});
		return result;
	}
	private void passImageSelectionState()
	{
		if(this.imageFileHandler == null)
		{
			return;
		}
		
		String imageId = this.imageAbsolutePathList.get(this.imageIndex);
		Map<String, String> imageSelectionState = this.getRadioPanelSelectionState();
		if(imageSelectionState != null)
		{
			this.imageFileHandler.setImageItemValues(imageId, imageSelectionState);
		}
	}
	private void updateImage(String updateType)
	{
		
		if(this.imageAbsolutePathList.isEmpty())
		{
			return;
		}
		
		Map<String, String> prevoiusImageItemSelectState = getRadioPanelSelectionState();
		passImageSelectionState();//首先保存当前图像的选择状态
		
		switch(updateType)
		{
		case "Previous":
			this.imageIndex = (--imageIndex + this.imageAbsolutePathList.size()) % this.imageAbsolutePathList.size();
			break;
		case "Next":
			this.imageIndex = ++imageIndex % this.imageAbsolutePathList.size();
			break;
		case "NextUnlabelled":
			//TODO 目前只能找出当前图片之后的没有被标注的图片
			for(; ++imageIndex<this.imageAbsolutePathList.size();)
			{
				String imageId = this.imageAbsolutePathList.get(imageIndex);
				if(this.imageFileHandler.isUnlabelled(imageId))
				{ 
					break;
				}
			}
			this.imageIndex = imageIndex % this.imageAbsolutePathList.size();
			break;
		default:
			this.imageIndex = 0;
			break;
		}
		String imagePath = this.imageAbsolutePathList.get(this.imageIndex);
		//update imageLabel
		ImageIcon icon = new ImageIcon();
		BufferedImage image = FileHelp.getImage(imagePath, IMAGE_SIZE.width, IMAGE_SIZE.height);
		icon.setImage(image);
		this.imageLabel.setIcon(icon);
		//update radioPanel
		String imageId = imageAbsolutePathList.get(imageIndex);
		if(this.imageFileHandler != null)
		{
			Map<String, String> imageItemSelectState = imageFileHandler.getImageItemValues(imageId);
			if(imageItemSelectState == null)
			{
				setRadioPanelSelectionState(prevoiusImageItemSelectState);
			}
			else
			{
				setRadioPanelSelectionState(imageItemSelectState);
			}
		}
		this.statusLabel.setText(imageId);
		this.frame.setVisible(true);
		
	}
	private void addLogic()
	{
		//menuItems
		ActionListener menuItemActionListener = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String itemText = ((JMenuItem)arg0.getSource()).getText();
				switch(itemText)
				{
				case "openImageDir":
					final JFileChooser dirChooser = new JFileChooser();
					dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					dirChooser.setMultiSelectionEnabled(false);
					int result = dirChooser.showOpenDialog(frame);
					switch(result)
					{
					case JFileChooser.APPROVE_OPTION:
						System.out.println(dirChooser.getSelectedFile().getAbsolutePath());
						File fileChoosed = dirChooser.getSelectedFile();
						if(fileChoosed.isDirectory())
						{
							//load images
							String dir = fileChoosed.getAbsolutePath();
							imageFileHandler = new ImageFileHandler(dir, FORMAT_STRINGS);
							imageAbsolutePathList = imageFileHandler.getImageIdList();
							//TODO init imageLabel
							imageIndex = 0;
							updateImage("Init");
						}
						else
						{
							statusLabel.setText("you must select a directory.");
						}
						break;
					case JFileChooser.CANCEL_OPTION:
						statusLabel.setText("selection cancelled");
						break;
					}
					break;
				case "openRadioFile":
					
					if(imageFileHandler == null)
					{
						statusLabel.setText("open directory firstlly.");
					}
					else
					{
						final JFileChooser radioFileChooser = new JFileChooser();
						radioFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						radioFileChooser.setMultiSelectionEnabled(false);
						int result2 = radioFileChooser.showOpenDialog(frame);
						switch(result2)
						{
						case JFileChooser.APPROVE_OPTION:
							File fileChoosed = radioFileChooser.getSelectedFile();
							if(fileChoosed.isFile())
							{
								//load radio file and init radio panel
								String filePath = fileChoosed.getAbsolutePath();
								try
								{
									radioFileHandler = new RadioFileHandler(filePath);
									imageFileHandler.setRadioFileInformation(radioFileHandler);
									//init radioPanel and init current Image selection status
									initRadioPanel(radioFileHandler.getItemKeys(), radioFileHandler.getItemValuesList());
									String imageId = imageAbsolutePathList.get(imageIndex);
									Map<String, String> imageItemSelectState = imageFileHandler.getImageItemValues(imageId);
									setRadioPanelSelectionState(imageItemSelectState);
								}catch(Exception e)
								{
									e.printStackTrace();
									statusLabel.setText("open radio file exception: "+e.getMessage());
								}
							}
							else
							{
								statusLabel.setText("you must select a file.");
							}
							break;
						case JFileChooser.CANCEL_OPTION:
							statusLabel.setText("selection cancelled");
							break;
						}
					}
					
					break;
				case "save":
					//先传递本次结果
					passImageSelectionState();
					//然后保存
					frame.setEnabled(false);
					statusLabel.setText("saving...");
					try {
						imageFileHandler.saveFile();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						statusLabel.setText(e.getMessage());
					}
					statusLabel.setText("saved");
					frame.setEnabled(true);
					break;
				case "exit":
					try {
						imageFileHandler.saveFile();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						statusLabel.setText(e.getMessage());
					}
					statusLabel.setText("saved");
					System.exit(0);
					break;
				}
			}
		};
		this.openDirMenuItem.addActionListener(menuItemActionListener);
		this.openRadioFileMenuItem.addActionListener(menuItemActionListener);
		saveMenuItem.addActionListener(menuItemActionListener);
		exitMenuItem.addActionListener(menuItemActionListener);
		
		//TODO add action to buttons
		this.previousButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateImage("Previous");
			}
		});
		this.nextButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateImage("Next");
			}
		});
		this.nextUnlabelledButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateImage("NextUnlabelled");
			}
		});
	}
}
