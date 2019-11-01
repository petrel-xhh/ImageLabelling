package test.util;

import util.FileHelp;

public class FileHelpTest {

	public static void main(String[] args)
	{
		String path = "/home/petrel/Picture/radioFile.csv";
		System.out.println(FileHelp.getFilename(path));
		System.out.println(FileHelp.getExtname(path));
		System.out.println(FileHelp.getNetFilename(path));
		System.out.println(path.contains("."));
	}
}
