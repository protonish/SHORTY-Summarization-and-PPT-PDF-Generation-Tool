package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

class Counter
{
	private int count = 0;
	Counter(int a)
	{
		count = a;
	}
	Counter() { }
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int a)
	{
		count = a;
	}
}

public class TextStatistics
{
	private int noOfWords = 0;
	private int noOfUniqueWords = 0;
	HashMap<String, Counter> wordCount;
	public TextStatistics()
	{}
	
	public TextStatistics(String filename) throws FileNotFoundException
	{
		String line, word;
		Scanner tsScan = new Scanner(new File(filename));
		wordCount = new HashMap<String, Counter>();
		//wordCount.put(inputWord, new Counter(900));
		int n = 0;
		while(tsScan.hasNext())
		{
			line = tsScan.nextLine();
			StringTokenizer ss = new StringTokenizer(line);
			while(ss.hasMoreTokens())
			{
				word = ss.nextToken().trim().toLowerCase().replaceAll("[^A-Za-z0-9\\-]", "").replaceAll("-", "\n");
				n = 0;
				if(wordCount.containsKey(word))
				{
					n = wordCount.get(word).getCount();
					wordCount.remove(word);
					noOfUniqueWords -= 1;
				}
				noOfUniqueWords += 1;
				noOfWords += 1;
				n += 1;
				wordCount.put(word, new Counter(n));
			}
		}
	}
	
	public boolean contains(String word)
	{
		return wordCount.containsKey(word);
	}
	
	
	public int getWordCountOf(String word)
	{
		if(wordCount.containsKey(word))
		{
			return wordCount.get(word).getCount();
		}
		return -1;
	}
	
	public void getWords()
	{
		for(String x : wordCount.keySet())
		{
			System.out.println(x + ":" + wordCount.get(x).getCount() + "\n");
		}
		System.out.println("No. of Words: " + noOfUniqueWords);
	}
}
