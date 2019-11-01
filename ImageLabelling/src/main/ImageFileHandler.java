package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

	final private static String RESULT_FILENAME_PREFIX="LabelResult_";
	private String dir;
	private List<String> itemKeys;	//attention the sort of itemKeys while save result file
	private Map<String, Map<String, String>> result;
	private String radioNetFilename;
	
	public ImageFileHandler(String dir, String[] formatStrings)
	{
		this.dir = dir;
		this.result = new HashMap<>();
		
		//load all of images
		List<String> imagePathList = FileHelp.getFileAbsolutePathFromDirectory(dir, formatStrings);
		imagePathList.forEach(imagePath->
		{
			//System.out.println(imagePath);
			this.result.put(imagePath, null);
		});
		
		
	}
	
	public void setRadioFileInformation(RadioFileHandler radioFileHandler)
	{
		//添加保存文件的后缀并尝试读取以前保存的文件
		String radioFilename = radioFileHandler.getRadioFilename();
		String suffix = FileHelp.getNetFilename(radioFilename);
		this.radioNetFilename = suffix;
		String resultFilePath = dir+File.separator+RESULT_FILENAME_PREFIX+suffix+".csv";
		File resultFile = new File(resultFilePath);
		if(resultFile.exists())
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
					br.close();
					fr.close();
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
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//添加itemKeys
		this.itemKeys = radioFileHandler.getItemKeys();
	}
	
	/**
	 * Get output line of an image
	 * @param imageId	The absolute path of an image. 
	 * @return null if result do not has imageId as a key
	 * @throws Exception	when this class or itemKeys has not been initialized
	 */
	private String getImageOutputLine(String imageId) throws Exception
	{
		if(this.result == null || this.result.isEmpty() || this.itemKeys == null || this.itemKeys.isEmpty() || this.radioNetFilename ==null)
		{
			throw new Exception("illegal state: object result or itemKeys is null or empty, can not get output line");
		}
		else
		{
			if(this.result.containsKey(imageId))
			{
				Map<String, String> tmpItemValues = this.result.get(imageId);
				StringBuffer sb = new StringBuffer();
				String relativePath = imageId.replace(this.dir, "");
				sb.append(relativePath);
				for(int i = 0; i < this.itemKeys.size(); i++)
				{
					String tmpItemKey = this.itemKeys.get(i);
					if(tmpItemValues.containsKey(tmpItemKey))
					{
						String tmpItemValue = tmpItemValues.get(tmpItemKey);
						if(tmpItemValue != null)
						{
							sb.append(","+tmpItemValue);
						}
						else//如果某个选项没有选择则留空
						{
							sb.append(",");
						}
					}
					else
					{
						sb.append(",");
					}
				}
				sb.append("\n");
				return sb.toString();
			}
			else
			{
				return null;
			}
		}
	}
	public void saveFile() throws Exception
	{
		//TODO 保存的时候一定要注意按照itemKeys的顺序保存。如果某些图像没有某些item，则留空。输出的图像的路径为相对路径
		if(this.result == null || this.result.isEmpty() || this.itemKeys == null || this.itemKeys.isEmpty())
		{
			throw new Exception("illegal state: object result or itemKeys is null or empty, can not get output line");
		}
		
		String resultFilePath = dir+File.separator+RESULT_FILENAME_PREFIX+this.radioNetFilename+".csv";
		try
		{
			FileWriter fw = new FileWriter(resultFilePath);
			//get the headline of csv output file
			StringBuffer sb = new StringBuffer();
			sb.append("relativePath");
			this.itemKeys.forEach(itemKey->
			{
				sb.append(","+itemKey);
			});
			sb.append("\n");
			fw.write(sb.toString());
			
			//write all values
			Iterator<String> imageIdIterator = this.result.keySet().iterator();
			while(imageIdIterator.hasNext())
			{
				String tmpImageId = imageIdIterator.next();
				String tmpImageOutputLine = this.getImageOutputLine(tmpImageId);
				if(tmpImageOutputLine != null)
				{
					fw.write(tmpImageOutputLine);
				}
			}
			
			//finished writing, close filestream
			fw.close();
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public Map<String, String> getImageItemValues(String imageId)
	{
		if(this.result.containsKey(imageId))
		{
			return this.result.get(imageId);
		}
		return null;
	}
	public boolean isLabelled(String imageId)
	{
		return getImageItemValues(imageId) == null;
	}
	public List<String> getImageIdList()
	{
		List<String> result = new ArrayList<>();
		this.result.forEach((k ,v)->
		{
			result.add(k);
		});
		return result;
	}
	public List<String> getImageIdUnlabelledList()
	{
		List<String> result = new ArrayList<>();
		this.result.forEach((k ,v)->
		{
			if(v == null)
			{
				result.add(k);
			}
		});
		return result;
	}
	public void setImageItemValues(String imageId, Map<String, String> itemValues)
	{
		this.result.put(imageId, itemValues);
	}
}
