package src;

import javax.swing.SwingWorker;
@SuppressWarnings("rawtypes")
public class OpenProgress extends SwingWorker
{
	private String filename;
	public OpenProgress(String file)
	{
		filename = file;
	}
	public static boolean openProgress=false;
	@Override
    public Void doInBackground() 
	{
		try 
		{
			MainApp.initialStep(filename);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	public void done()
	{
		openProgress = true;
		try 
		{
			MainUi.afterFileOpen();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}
}