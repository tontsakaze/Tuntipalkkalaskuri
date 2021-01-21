import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
* <h1>Palkkalaskuri</h1>
* A program that can calculate wage slips
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-07
*/
public class Palkkalaskuri {
	private static JFrame 		mainFrame;
	private static TabPanel 	tabpanel;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainFrame = new JFrame("Palkkalaskuri");
				mainFrame.setLayout( new GridLayout() );
				mainFrame.setSize(1100, 750);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				tabpanel = new TabPanel();
				mainFrame.add( tabpanel );
				
				mainFrame.setVisible(true);
				
			}
		});
		
	
	}
	
		

}
