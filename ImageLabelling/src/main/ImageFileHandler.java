package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.FileHelp;

/**
 * 用于图像与图像标记结果的索引，存储等工作
 * @author petrel
 *
 */

//import org.json.*;

public class ImageFileHandler {

	final private static String RESULT_FILENAME="LabelResult.csv";
	private String dir;
	private List<String> itemKeys;	//the sort of itemKeys while save result file
	private Map<String, Map<String, String>> result;
	private FileWriter writer;
	
	public ImageFileHandler(String dir, String format)
	{
		this.result = new HashMap<>();
		
		//load all of images
		List<String> imagePathList = FileHelp.getFileAbsolutePathFromDirectory(dir, format);
		imagePathList.forEach(imagePath->
		{
			this.result.put(imagePath, null);
		});
		
		String resultFilePath = dir+File.separator+RESULT_FILENAME;
		File resultFile = new File(resultFilePath);
		if(!resultFile.exists())
		{
			try
			{
				resultFile.createNewFile();
			}catch(Exception e)
			{
				//TODO 
				e.printStackTrace();
			}
		}
		else
		{
			try {
				FileReader fr = new FileReader(resultFile);
				BufferedReader br = new BufferedReader(fr);
				String title = br.readLine().trim();
				String[] itemKeys = title.split(",");
				List<String> tmpItemKeys = new ArrayList<String>();
				if(itemKeys.length > 1)
				{
					for(int i=1; i<itemKeys.length; i++)
					{
						tmpItemKeys.add(itemKeys[i]);
					}
				}
				else
				{
					//TODO do not have title
					return;
				}
				
				//read the old result
				String line = null;
				while((line = br.readLine()) != null)
				{
					String[] tmpItemValues = line.trim().split(",");
					String absolutePath = this.dir+tmpItemValues[0];	//路径样式为"/dir/picture.jpg"
					
					Map<String, String> tmpImageItemValues = new HashMap<>();
					for(int i=1;i<tmpItemValues.length;i++)
					{
						int itemKeyIndex = i - 1;
						String tmpItemKey = tmpItemKeys.get(itemKeyIndex);
						String tmpItemValue = tmpItemValues[i];
						tmpImageItemValues.put(tmpItemKey, tmpItemValue);
					}
					this.result.put(absolutePath, tmpImageItemValues);
				}
				
				//finished
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setLabelItems(List<String> itemKeys)
	{
		this.itemKeys = itemKeys;
	}
	
	public void saveFile()
	{
		//TODO 保存的时候一定要注意按照itemKeys的顺序保存。如果某些图像没有某些item，则留空。输出的图像的路径为相对路径
	}
	
}
