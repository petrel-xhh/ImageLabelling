package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHelp {

	public static List<String> getFileAbsolutePathFromDirectory(String dir, String[] formatStrings)
	{
		List<String> result = new ArrayList<>();
		File dirFile = new File(dir);
		if(dirFile.exists())
		{
			File[] subFiles = dirFile.listFiles();
			Arrays.stream(subFiles).forEach(subFile->
			{
				if(!subFile.isHidden() && isFilenameEndsWidth(subFile, formatStrings, true))
				{
					if(subFile.isFile())
					{
						result.add(subFile.getAbsolutePath());
					}
					else if(subFile.isDirectory())
					{
						result.addAll(FileHelp.getFileAbsolutePathFromDirectory(subFile.getAbsolutePath(), formatStrings));
					}
				}
			});
		}
		return result;
	}
	
	public static boolean isFilenameEndsWidth(File file, String[] strings, boolean ignoreCase)
	{
		if(strings == null)
		{
			return false;
		}
		for(int i = 0; i< strings.length;i++)
		{
			String filename = (ignoreCase ? file.getName().toLowerCase() : file.getName());
			if(filename.endsWith(strings[i].toLowerCase()))
			{
				return true;
			}
		}
		return false;
	}
	
	public static String getFilename(String filePath)
	{
		return new File(filePath).getName();
	}
	public static String getExtname(String filePath)
	{
		if(!filePath.contains("\\.") || filePath.startsWith("\\."))
		{
			return "";
		}
		String[] filePathArray = filePath.split("\\.");
		String extName = filePathArray[filePathArray.length-1];
		if(extName.length() == 0)
		{
			return "";
		}
		else
		{
			return extName;
		}
	}
	public static String getNetFilename(String filePath)
	{
		String filename = new File(filePath).getName();
		//TODO there may result in bugs when deal with string like "alike.jpg.jpg"
		return filename.replaceAll(filePath, getExtname(filePath));
	}
}


