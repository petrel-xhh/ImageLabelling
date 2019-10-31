package main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class ImageLabelling {

	private JFrame frame;
	private JLabel statusLabel;
	private JLabel imageLabel;
	private JButton previousButton;
	private JButton nextButton;
	private JPanel radioPanel;
	private JMenuItem openDirMenuItem;
	private JMenuItem openRadioFileMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	private Map<JLabel, java.util.List<JRadioButton>> radioGroups;
	private List<String> imageAbsolutePathList;
	private ImageFileHandler imageFileHandler;
	private RadioFileHandler radioFileHandler;
	private final static String[] FORMAT_STRINGS = {"jpg"};
	
	public void prepareUI()
	{
		//instance
		this.frame = new JFrame();
		this.statusLabel = new JLabel();
		this.imageLabel = new JLabel();
		this.previousButton = new JButton("Previous");
		this.nextButton = new JButton("Next");
		this.radioPanel = new JPanel();
		
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
		btnBox.add(Box.createRigidArea(new Dimension(10, 20)));//put a fixed gap between the right side and the nextButton
		
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));	//show the border of imageLabel
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		Box imageInnerBox = Box.createHorizontalBox();
		imageInnerBox.add(Box.createRigidArea(new Dimension(10, 400)));// fix the height of imageLabel
		imageInnerBox.add(imageLabel);
		imageInnerBox.add(Box.createRigidArea(new Dimension(10, 400)));

		imageBox.add(imageInnerBox);
		//imageBox.add(Box.createVerticalGlue());
		imageBox.add(btnBox);
		//imageBox.setMaximumSize(new Dimension(600, 500));
		
		Box statusBox = Box.createHorizontalBox();
		statusBox.add(Box.createRigidArea(new Dimension(10, 20)));	//put a fixed gap between the right size and the statusLabel
		statusBox.add(statusLabel);
		statusBox.add(Box.createHorizontalGlue());
		leftBox.add(Box.createRigidArea(new Dimension(500, 2)));	//fix the width of leftBox
		leftBox.add(imageBox);
		leftBox.add(Box.createVerticalStrut(10));
		leftBox.add(statusBox);
		
		//Box rightBox = Box.createVerticalBox();
		Box radioBox = Box.createHorizontalBox();
		radioBox.add(this.radioPanel);
		radioBox.add(Box.createRigidArea(new Dimension(10, 600)));
		
		mainBox.add(leftBox);
		mainBox.add(radioBox);
		
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
		
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600, 500);
		this.frame.setVisible(true);
	}
	private void initRadioPanel(List<String> itemKeys, List<List<String>> itemValuesList)
	{
		//TODO save file
		
		
		//clean the old radio groups at first.
		this.radioPanel.removeAll();
		this.radioGroups = new HashMap<>();
		
		//start to add new radio groups
		for(int i=0;i<itemKeys.size();i++)
		{
			String item = itemKeys.get(i);
			List<String> itemValues = itemValuesList.get(i);
			JLabel itemLabel = new JLabel(item);
			java.util.List<JRadioButton> itemValueButtonList = new java.util.ArrayList<>(); 
			final ButtonGroup itemValueButtonGroup = new ButtonGroup();
			itemValues.forEach(itemValue->
			{
				JRadioButton itemValueButton = new JRadioButton(itemValue);
				itemValueButtonGroup.add(itemValueButton);
				itemValueButtonList.add(itemValueButton);
			});
			this.radioGroups.put(itemLabel, itemValueButtonList);
		}
		
		
		
	}
	
	private void setRadioPanelSelectionState(Map<String, String> imageItemValues)
	{
		
	}
	private Map<String, String> getRadioPanelSelectionState()
	{
		return null;
	}
	private void saveRecord(String imageId, Map<String, String> itemValues)
	{
		
		
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
					int result = dirChooser.showOpenDialog(frame);
					switch(result)
					{
					case JFileChooser.APPROVE_OPTION:
						File fileChoosed = dirChooser.getSelectedFile();
						if(fileChoosed.isDirectory())
						{
							//load images
							String dir = fileChoosed.getAbsolutePath();
							imageFileHandler = new ImageFileHandler(dir, FORMAT_STRINGS);
							imageAbsolutePathList = imageFileHandler.getImageIdList();
							//TODO init imageLabel
							
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
					final JFileChooser radioFileChooser = new JFileChooser();
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
								//TODO init radioPanel and init current Image selection status
								initRadioPanel(radioFileHandler.getItemKeys(), radioFileHandler.getItemValuesList());
								
							}catch(Exception e)
							{
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
					break;
				case "save":
					
					break;
				case "exit":
					
					break;
				}
			}
		};
		this.openDirMenuItem.addActionListener(menuItemActionListener);
		this.openRadioFileMenuItem.addActionListener(menuItemActionListener);
		saveMenuItem.addActionListener(menuItemActionListener);
		exitMenuItem.addActionListener(menuItemActionListener);
		
		//TODO add action to buttons
		
	}
}
