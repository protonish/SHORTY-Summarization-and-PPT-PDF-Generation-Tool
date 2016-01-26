package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;

public class PreProcessor 
{	
    PreProcessor()
    {
        super();
    }
	 
	
	public static void mark(String filename, String destination) throws IOException
	{
		Scanner scanner1 = new Scanner(new FileReader(filename));
		String sentence = new String();
		File newFile = new File(destination);
		if(!newFile.exists())
			newFile.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
		while(scanner1.hasNextLine())
		{
			sentence = scanner1.nextLine();
			if(!sentence.isEmpty())
				bw.write("\n@@");
			bw.write(sentence);
		}
		bw.close();
	}
       
	public static void caseFolder(String filename) throws IOException
	{
		Scanner scn = new Scanner(new FileReader(filename));
		File newFile = new File("temporaryx.txt");
		if(!newFile.exists())
		{
			newFile.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
		while(scn.hasNextLine())
		{
			bw.write(scn.nextLine().toLowerCase());
		}
		bw.close();
		scn.close();
	}
}
