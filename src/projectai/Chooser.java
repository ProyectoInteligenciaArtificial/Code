package projectai;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;



public class Chooser {
	
	public String ChooseMap() throws FileNotFoundException{
		//Create variable file type
		File Name = null;
		//put the direction to the home directory
		JFileChooser ch = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());	
		//pick only txt files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		String dir = System.getProperty("user.dir");
		String sep = System.getProperty("file.separator");
		ch.setCurrentDirectory(new File(dir + sep + "Mapas"));
		ch.setFileFilter(filter);
		//Open the windows and put it in a int to get to the if 
		int Value = ch.showOpenDialog(null);
		//If value = 0 
		if (Value == JFileChooser.APPROVE_OPTION) {
			//choose a file and save direction and name into variable Name 
			Name = ch.getSelectedFile();			
			//Print it for if something is wrong
			//System.out.println(selectedFile.getAbsolutePath());			
		}
                
                return Name.getAbsolutePath();
	}
	public File ChooseCharacter(){
		//Create variable file type
		File Name = null;
		//put the direction to the home directory
		JFileChooser ch = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());	
		//pick only png and jpg files
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png");
		ch.setFileFilter(filter);
		//Open the windows and put it in a int to get to the if 
		int Value = ch.showOpenDialog(null);
		//If value = 0 
		if (Value == JFileChooser.APPROVE_OPTION) {
			//choose a file and save direction and name into variable Name 
			Name = ch.getSelectedFile();			
			//Print it for if something is wrong
			//System.out.println(selectedFile.getAbsolutePath());			
		}
		
		return Name ;
	}
}
