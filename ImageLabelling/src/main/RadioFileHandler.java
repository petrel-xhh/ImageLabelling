package main;

/**
 * format of radioFIle:
 * item1:itemValue1,itemValue2,...
 * item2:itemValue1,itemValue2,...
 * ...
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.FileHelp;
import util.IOHelp;

public class RadioFileHandler {

	private final static String RADIO_FILE_TAG = "#RadioFile";
	private List<String> itemKeys;
	private List<List<String>> itemValuesList;
	private String radioFileName;
	public RadioFileHandler(String radioFilePath) throws Exception
	{
		this.radioFileName = FileHelp.getFilename(radioFilePath);
		
		this.itemKeys = new ArrayList<>();
		this.itemValuesList = new ArrayList<>();
		List<String> lines = null;
		try
		{
			lines = IOHelp.loadLines(radioFilePath);
		}catch(Exception e)
		{
			throw e;
		}
		
		if(lines.size()==0 || lines.get(0).equals(RADIO_FILE_TAG))
		{
			throw new Exception("illegal radio file");
		}
		try
		{
			lines.forEach(line->
			{
				String[] itemArray = line.split(":");
				this.itemKeys.add(itemArray[0]);
				String[] itemValuesArray = itemArray[1].split(",");
				List<String> itemValues = new ArrayList<>();
				Arrays.stream(itemValuesArray).forEach(itemValue->
				{
					itemValues.add(itemValue);
				});
				this.itemValuesList.add(itemValues);
			});
		}catch(Exception e)
		{
			throw new Exception("illegal radio file");
		}
		
	}
	
	public List<String> getItemKeys()
	{
		return this.itemKeys;
	}
	public List<List<String>> getItemValuesList()
	{
		return this.itemValuesList;
	}
	public String getRadioFilename()
	{
		return this.radioFileName;
	}
}
