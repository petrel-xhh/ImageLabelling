package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

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
				if(!subFile.isHidden())
				{
					if(subFile.isFile() && isFilenameEndsWidth(subFile, formatStrings, true))
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
	
	public static BufferedImage getImage(String path, int width, int height)
	{
		BufferedImage image = getImage(path);
		if(null == image)
		{
			return null;
		}
		return resize(image, width, height);
	}
	public static BufferedImage getImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static BufferedImage resize(BufferedImage image, int width, int height)
	{
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		result.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_FAST), 0, 0, null);
		return result;
	}
}


