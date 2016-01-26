 package src;

import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class StopwordsEliminator {
	HashSet<String> stops;
	
	public StopwordsEliminator(String filename) throws IOException					
, SAXException, ParserConfigurationException
	{
		Scanner scanner = new Scanner(new FileReader(filename));   
		File newFile = new File("temporary2.dat");
		if(!newFile.exists())
			newFile.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("temporary2.dat"));
		
		String sentence = new String();
		Dictionary SEDict = new Dictionary();
		while(scanner.hasNextLine())
		{
			sentence = scanner.nextLine();
			String[] a = sentence.split(" ");
			for(int i = 0; i < a.length; i++)
			{
				if(SEDict.isStopword(a[i].trim().toLowerCase()))
				{
					sentence = sentence.replaceAll(" "+a[i]+" ", " ");
				}
			}
			bw.write(sentence);
		}
		bw.close();
		scanner.close();
	}
}