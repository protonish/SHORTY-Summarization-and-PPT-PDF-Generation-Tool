package src;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class SentenceSplitter2 	
{
	private Scanner scanner;

	public SentenceSplitter2(String filename) throws IOException, SAXException, ParserConfigurationException
	{
		scanner = new Scanner(new FileReader(filename));
		FileWriter writer = new FileWriter("temporary.dat");
		BufferedWriter out = new BufferedWriter(writer);

		String sentence = new String();
		
		BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.US);
		
		Dictionary SentSpDict = new Dictionary();
		
		while(scanner.hasNextLine())
		{
			sentence = scanner.nextLine();
			boundary.setText(sentence);
			int start = boundary.first();
			for(int end = boundary.next(); end != BreakIterator.DONE ; start = end, end = boundary.next())
			{
				String toPrint = sentence.substring(start, end);
				while(SentSpDict.endsWithAbbreviation(toPrint))
				{
					end = boundary.next();
					toPrint = sentence.substring(start, end);
				}
				out.write(toPrint + "\n");
			}

		}
		out.close();
		writer.close();
		scanner.close();
		
		scanner = new Scanner(new FileReader("temporary.dat"));
	}
	
	public boolean hasNextSentence()
	{
		return scanner.hasNextLine();
	}
	
	public String nextSentence()
	{
		return scanner.nextLine();
	}
	
	public void close()
	{
		scanner.close();
	}

}
