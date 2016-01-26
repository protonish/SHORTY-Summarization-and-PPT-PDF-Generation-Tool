package src;

import java.io.File;
import java.io.IOException;
import org.w3c.dom.*;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException; 


public class Dictionary {
	
	HashSet<String> stopwords;
	HashSet<String> abbreviations;
	HashSet<String> cuephrases;
	public static HashSet<String> user_stops = new HashSet<String>();
	public String[] sw = new String[1000];
	public static String userDefined = null;
	int s;
	
	public Dictionary() throws SAXException, IOException, ParserConfigurationException
	{
		abbreviations = new HashSet<String>();	
		stopwords = new HashSet<String>();
		cuephrases = new HashSet<String>();
		File file = new File("dictionary.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = (Document) db.parse(file);
		document.getDocumentElement().normalize();
		
		NodeList listOfStopwords = document.getElementsByTagName("stopwords");
		Node node = listOfStopwords.item(0);
		Element e = (Element) node;
		String stopword;
		NodeList nodelist = e.getElementsByTagName("word");

		for(s= 0; s < nodelist.getLength(); s++)
		{
			stopword = nodelist.item(s).getChildNodes().item(0).getNodeValue().trim().toLowerCase();
			stopwords.add(stopword);
			sw[s] = stopword;
		}
		
		NodeList listOfAbb = document.getElementsByTagName("abbreviations");
		node = listOfAbb.item(0);
		e = (Element)node;
		String abbreviation;
		nodelist = e.getElementsByTagName("word");
		for(int i = 0; i < nodelist.getLength(); i++)
		{
			abbreviation = nodelist.item(i).getChildNodes().item(0).getNodeValue().trim();
			abbreviations.add(abbreviation);
		}
		
		NodeList listOfCP = document.getElementsByTagName("CuePhrases");
		node = listOfCP.item(0);
		e = (Element)node;
		String cuephrase;
		nodelist = e.getElementsByTagName("phrase");
		for(int i = 0; i < nodelist.getLength(); i++)
		{
			cuephrase = nodelist.item(i).getChildNodes().item(0).getNodeValue().trim();
			cuephrases.add(cuephrase);
		}
	}
	
	public String[] getWords()
	{
		return sw;
	}
	public int getSwLength()
	{
		return s;
	}
	
	public boolean isAbbreviation(String abbr)
	{
		if(abbreviations.contains(abbr))
			return true;
		return false;
	}
	
	public boolean isStopword(String abbr)
	{
		if(stopwords.contains(abbr.trim().toLowerCase()))
			return true;
		if(user_stops.contains(abbr.trim().toLowerCase()))
			return true;
		return false;
	}

	public boolean endsWithAbbreviation(String toPrint) 
	{
		Iterator<String> itr = abbreviations.iterator();
		while(itr.hasNext())
		{
			if(toPrint.endsWith(itr.next() + " "))
				return true;
		}
		return false;
	}
	
	public boolean isCuePhrase(String phrase)
	{
		if(cuephrases.contains(phrase.trim())) 
			return true;
		else
			return false;
	}
	public static void addStopword(String stopword)
    {
		user_stops.add(stopword);
    }
	
	public static void removeUserStopword(String stopword)
	{
		user_stops.remove(stopword);
	}
	
}
