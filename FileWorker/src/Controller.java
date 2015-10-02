import java.awt.Frame;

import javax.swing.*;

public class Controller {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewUI frame = new NewUI();
				frame.setTitle("NFM v1.9");
				frame.init();
				frame.setVisible(true);
			}
		});
		
	}
}
