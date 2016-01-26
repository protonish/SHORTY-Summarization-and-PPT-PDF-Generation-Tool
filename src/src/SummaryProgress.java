package src;

import javax.swing.SwingWorker;
@SuppressWarnings("rawtypes")
public class SummaryProgress extends SwingWorker
{
	public static boolean stopProgress=false;
	private String filename;
	public SummaryProgress(String file)
	{
		filename = file;
	}
	@SuppressWarnings("unused")
	@Override
    public Void doInBackground() 
	{
		try 
		{
			MainApp ma = new MainApp(filename);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	public void done()
	{
		stopProgress = true;
		
		try 
		{
			MainUi.afterMainApp();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}
}