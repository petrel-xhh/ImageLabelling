package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHelp {

	public static List<String> getFileAbsolutePathFromDirectory(String dir, String format)
	{
		List<String> result = new ArrayList<>();
		File dirFile = new File(dir);
		if(dirFile.exists())
		{
			File[] subFiles = dirFile.listFiles();
			Arrays.stream(subFiles).forEach(subFile->
			{
				if(!subFile.isHidden() && subFile.getName().endsWith(format))
				{
					if(subFile.isFile())
					{
						result.add(subFile.getAbsolutePath());
					}
					else if(subFile.isDirectory())
					{
						result.addAll(FileHelp.getFileAbsolutePathFromDirectory(subFile.getAbsolutePath(), format));
					}
				}
			});
		}
		return result;
	}
}


