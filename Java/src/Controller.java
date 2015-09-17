import java.awt.Frame;

import javax.swing.*;

public class Controller {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewUI frame = new NewUI();
				frame.setTitle("New UI");
				frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
				frame.init();
				frame.setVisible(true);
			}
		});
		
	}
}
