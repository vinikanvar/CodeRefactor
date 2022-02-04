package jpt;

import java.io.IOException;
import java.sql.Timestamp;

import javax.swing.JFileChooser;

import com.github.javaparser.ParseException;

public class MainClass {
	public static String file[] = new String[3];
	public static void main(String args[]) throws IOException, ParseException, org.json.simple.parser.ParseException
	{
		Timestamp timestampbegin = new Timestamp(System.currentTimeMillis());	
		//select the project folder
		JFileChooser f = new JFileChooser();
		f.setDialogTitle("Select Project");
	    f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
	    f.showSaveDialog(null);

		//select the jars folder
	    JFileChooser jarFolder = new JFileChooser();
		jarFolder.setDialogTitle("Select JARs folder");
	    jarFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
	    jarFolder.showSaveDialog(null);
	    
	    //select json files folder
	    JFileChooser jsonFolder = new JFileChooser();
        jsonFolder.setDialogTitle("Select the folder files are stored where json");
        jsonFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        jsonFolder.showSaveDialog(null);
	    
	    //pass selected folders as args 
	    Test t = new Test();
	    ClusterCreator cc = new ClusterCreator();
	    MoveSubgraphMain msm = new MoveSubgraphMain();
	    file[0] = f.getSelectedFile().getPath().toString();
	    file[1] = jarFolder.getSelectedFile().getPath().toString();
	    file[2] = jsonFolder.getSelectedFile().getPath().toString();
	    t.main(file);
	    cc.main(file);
	    msm.main(file);
	    Timestamp timestampend = new Timestamp(System.currentTimeMillis());	
	    System.out.println("Time taken in millisec: "+(timestampend.getTime() - timestampbegin.getTime()));
	    System.out.println("Time taken in sec: "+((timestampend.getTime() - timestampbegin.getTime())/1000.0));
	    System.out.println("Time taken in min: "+((timestampend.getTime() - timestampbegin.getTime())/60000.0));
	    
	}
	public static void main1(String args[])
	{
	    String file[] = new String[3];
	    file[0] = "/Users/srikanthtamilselvam/Desktop/hybrid-cloud/test/daytrader-rewrite/";
	    file[1] = "/Users/srikanthtamilselvam/Downloads/resources";
	    file[2] = "/Users/srikanthtamilselvam/Desktop/hybrid-cloud/paper/icse/dataset/DayTrader/temp";
	    
	    Test t = new Test();
	    ClusterCreator cc = new ClusterCreator();
	    MoveSubgraphMain msm = new MoveSubgraphMain();
	    
	    try {
			t.main(file);
			cc.main(file);
			msm.main(file);
		} catch (IOException | ParseException | org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
