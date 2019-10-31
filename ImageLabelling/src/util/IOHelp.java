package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOHelp {

	public static String loadString(String path) throws FileNotFoundException,IOException
	{
		File file = new File(path);
		if(!file.exists())
		{
			throw new FileNotFoundException();
		}
		else if (!file.canRead())
		{
			throw new IOException("can not read file");
		}
		else
		{
			try
			{
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				StringBuffer sb = new StringBuffer();
				String line = null;
				while((line = br.readLine())!=null)
				{
					sb.append(line+"\n");
				}
				br.close();
				fr.close();
				return sb.toString();
			}catch(IOException ioe)
			{
				throw ioe;
			}
		}
	}
	public static List<String> loadLines(String path) throws FileNotFoundException,IOException
	{
		List<String> result = new ArrayList<>();
		File file = new File(path);
		if(!file.exists())
		{
			throw new FileNotFoundException();
		}
		else if (!file.canRead())
		{
			throw new IOException("can not read file");
		}
		else
		{
			try
			{
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while((line = br.readLine())!=null)
				{
					result.add(line);
				}
				
				br.close();
				fr.close();
				return result;
				
			}catch(IOException ioe)
			{
				throw ioe;
			}
		}
	}
	
}
