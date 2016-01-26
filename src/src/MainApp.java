package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
	

public class MainApp {
	public static DB db = new DB();
	public static int numberOfSentences;
	
	public MainApp(){}
	@SuppressWarnings("unused")
	public MainApp(String chosenFile) throws SQLException, SAXException, ParserConfigurationException
	{
		String filename = "";		
		filename = chosenFile;
		try 
		{
			PreProcessor.caseFolder("temporary2.txt");
			new StopwordsEliminator("temporaryx.txt");
			Stemmer s = new Stemmer();
			s.stemFile("temporary2.dat");
			
			SentenceScorer ss = new SentenceScorer("temp_stemmer.txt");
			SentenceObj sent;
			float score;
			db.getResultSet(-1);
			while((sent = db.getNextSentence())!=null)
			{
				score = ss.getSentenceScore(sent);
				if(MainUi.text!=null && sent.getSentence().toLowerCase().contains(MainUi.text.trim().toLowerCase()))
				{
					sent.setScore(score+20);
					System.out.println("Key word");
				}
				else
					sent.setScore(score);
				db.updateScore(sent);
			}
			int original_no = db.noOfSentences();
			float required_score = db.getAverageScore();
			db.getResultSet(required_score);
			System.out.println("Required score="+required_score);
			int summary_no = db.noOfSentences();
			System.out.println("kums "+summary_no);
			System.out.println(original_no + "******" + summary_no);
			float required_no = 0;
			double compression_ratio;
			compression_ratio = MainUi.getSliderValue();
			compression_ratio = compression_ratio/100;
			System.out.println("Compression ratio is"+compression_ratio);
			
			required_no = (float)compression_ratio * original_no;
			System.out.println(required_no);
                        while(summary_no>required_no)
                        {
                                required_score += 0.05;
				db.getResultSet(required_score);
				summary_no = db.noOfSentences();
				System.out.println(original_no + " " + summary_no);
                        }
			while(summary_no < required_no)
			{
				required_score -= 0.05;
				db.getResultSet(required_score);
				summary_no = db.noOfSentences();
				System.out.println(original_no + " " + summary_no);
			}

			File newFile = new File("summary.txt");
			if(!newFile.exists())
				newFile.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
			System.out.println(required_score);
			while((sent = db.getNextSentence())!=null)
			{ 
                           
				bw.write(sent.getSentence());
			}
			
			bw.close();
                       
		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void initialStep(String filename) throws IOException, SAXException, ParserConfigurationException
	{
		PreProcessor.mark(filename, "temporary.txt");
		
		splitSentences("temporary.txt");
	}

	private static void splitSentences(String source) throws IOException, SAXException, ParserConfigurationException 
	{
		SentenceSplitter2 ss = new SentenceSplitter2(source);
		numberOfSentences=0;
		String toadd;
		db.connectDB("jdbc:odbc:AccessDB");
		File fw = new File("temporary2.txt");
		if(!fw.exists())
		{
			fw.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(fw));
		while(ss.hasNextSentence())
		{
			String sent = ss.nextSentence();
			if(sent.contains("@@"))
			{
				toadd = sent.replaceAll("@@", "\n");
				toadd += "\n"; 
				db.addSentence(toadd, 1);
			}
			else
			{
				toadd = sent;
				db.addSentence(sent, 0);
			}
			bw.write(toadd);
			numberOfSentences+=1;
		}
		bw.close();
	
	}
}

