package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SentenceObj
{
	public SentenceObj() { }
	public SentenceObj(int seqNo, String sentence, float score, int head)
	{
		this.seqNo = seqNo;
		this.sentence = sentence;
		this.score = score;
		this.head = head;
	}
	
	private int seqNo;
	private String sentence;
	private float score;
	private int head;
	
	public int getHead()
	{
		return head;
	}
	
	public int getSeqNo()
	{
		return seqNo;
	}
	
	public String getSentence()
	{
            sentence=sentence.replace("\\\"","");
             sentence=sentence.replace("\\\'","");
		return sentence;
	}
	
	public float getScore()
	{
		return score;
	}
	
	public void setScore(float a)
	{
		score = a;
	}
}

public class DB {
	
	
	private Connection con;
	
	private static int nextSeqNo = 0;
	
	private ResultSet rs;
	
	public DB()
	{
	}
	
	
	
	public Connection connectDB(String driver_string) 
	{
		try {
			
                        con = DriverManager.getConnection("jdbc:odbc:AccessDB");
			System.out.println("Connected");
			initialize();
			return con;
		}
		catch(Exception e)
		{
			System.out.println(  e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	private void initialize()
	{
		Statement stmt;
		try 
		{
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate("DELETE FROM sentence_table");
		} 
		catch (SQLException e) 
		{
			System.out.println("Error: DB.java Couldn't clear the table. Function initialize() Line 99");
			e.printStackTrace();
		}
	}

	public void addSentence(String sentence, int heading)
	{
			try 
			{
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				sentence = sentence.replaceAll("[\"]{1}", "\\\\\"\"").replaceAll("[\']{1}", "\\\\\'\'");
				String concat_statement = "INSERT INTO sentence_table VALUES (" + (nextSeqNo++) + " ,'  "+ sentence +" '," + heading + ", 0 )";	
				
				stmt.executeUpdate(concat_statement);
			} 
			catch (SQLException e) 
			{
				System.out.println("Error: DB.java Function addSentence() Line 118");
				System.out.println(e.toString());
				e.printStackTrace();
			}
	}
	
	public void getResultSet(float score)
	{
		Statement stmt;
		try 
		{
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String concat_statement;
			if(score == -1)
				concat_statement = "SELECT * FROM sentence_table";
			else
				concat_statement = "SELECT * FROM sentence_table WHERE score > " + score;
			rs = stmt.executeQuery(concat_statement);
		} 
		catch (SQLException e) 
		{
			System.out.println("Error: DB.java Function getResultSet() Line 139");
			e.printStackTrace();
		}
	}
	
	public SentenceObj getNextSentence()
	{
		try 
		{
			if(!(rs.next())) 
				return null;
			SentenceObj sobj = new SentenceObj(rs.getInt("seqNo"), rs.getString("sentence"), rs.getInt("score"), rs.getInt("head"));
			return sobj;
		}
		catch (SQLException e) 
		{	
			e.printStackTrace();
			return null;
		}
	}
		
	public void updateScore(SentenceObj so)
	{
		String concat_statement = "UPDATE sentence_table SET score = " + so.getScore() + " where seqNo = " + so.getSeqNo() ;
		Statement stmt;
		try 
		{
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(concat_statement);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void getSummary() throws SQLException, IOException
	{
		String concat_statement = "SELECT * FROM sentence_table WHERE score > 20";
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet res = stmt.executeQuery(concat_statement);
		
		File newFile = new File("summary.txt");
		if(!newFile.exists())
			newFile.createNewFile();
		
		FileWriter writer = new FileWriter("summary.txt");
		BufferedWriter out = new BufferedWriter(writer);
		while(res.next())
		{
			out.write(res.getString("sentence"));
		}
		out.close();
		res.close();
	}
	
	public float getAverageScore() throws SQLException
	{
		String concat_statement = "SELECT avg(score) AS average FROM sentence_table";
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet res = stmt.executeQuery(concat_statement);
		float x = 0;
		if(res.next())
		{
			x = res.getFloat(1);
		}
		return x;
	}
	
	public void disconnectDB() 
	{
		try
		{
			con.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public int noOfSentences() throws SQLException
	{
		int current_row = rs.getRow();
		int no_of_sent = 0;
		rs.last();
		no_of_sent = rs.getRow();
		if(current_row == 0) 
			rs.beforeFirst();
		else
			rs.absolute(current_row);
		return no_of_sent;
	}
}
