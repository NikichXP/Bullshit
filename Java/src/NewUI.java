import java.io.*;
import javax.swing.*;


public class NewUI extends JFrame{
	private static final long serialVersionUID = -225295995033620554L;
	public FilesPanel rList, lList;
	
	
	public void init() {
		
	}
	
	class FilesPanel {
		public JTable table;
		public FileList tableModel;
		public File location;
		public JPanel panel;
	}
}
