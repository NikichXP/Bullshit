import java.awt.*;
import java.io.*;
import javax.swing.*;


public class NewUI extends JFrame{
	private static final long serialVersionUID = -225295995033620554L;
	public FilesPanel rList, lList;
	public JPanel filesPanel;
	public JPanel buttonPanel;
	
	
	public void init() {
		this.setSize(800, 640);
		this.filesPanel.setLayout(new BoxLayout (filesPanel, BoxLayout.Y_AXIS));
		this.buttonPanel.setLayout(new BoxLayout (buttonPanel, BoxLayout.Y_AXIS));
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.filesPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);
	}
	
	class FilesPanel {
		public JTable table;
		public FileList tableModel;
		public File location;
		public JPanel panel;
	}
}
