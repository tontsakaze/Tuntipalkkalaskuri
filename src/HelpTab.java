import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
* <h1>Help Tab</h1>
* A tab for information and settings
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-18
*/
public class HelpTab extends JPanel {
	private JPanel			panel;
	private JLabel			label;
	private JCheckBox 		cb;
	
	private final Font		TITLE_FONT = new Font("", Font.BOLD, 15);
	private final Font		TEXT_FONT = new Font("", Font.PLAIN, 9);
	
	private Tooltips 		tooltips = Tooltips.getInstance();
	private FileHandler 	fileHandler = FileHandler.getInstance();
	private SystemMessage	sysMsg = SystemMessage.getInstance();
	
	private String[][]		strs = fileHandler.getHelpTexts();
	
	
	/**
	 * Constructor
	 */
	public HelpTab() {
		super();
		GridLayout layout = new GridLayout(1,3);
        //layout.setHgap(3);
        //layout.setVgap(3);
        this.setLayout(layout);
        this.setBackground(Color.black);
		
    // = = = = = = = = = = = = = = = = = = = = = = = = =
	// VERO JA PALKKAUS  							   |
    // = = = = = = = = = = = = = = = = = = = = = = = = =
		panel = new JPanel(false);
		panel.setLayout( new GridLayout(strs[0].length+2, 1) );
		panel.setBorder( new EmptyBorder(5,5,5,5) );
		
		// TITLE
		label = new JLabel("VERO JA PALKKAUS");
        label.setFont(TITLE_FONT);	
        label.setVerticalAlignment(JLabel.TOP);				
        label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
		// INFO
		for(int i=0; i<strs[0].length; i++) {
			label = new JLabel(strs[0][i]);
	        label.setFont(TEXT_FONT);				
	        label.setHorizontalAlignment(JLabel.LEFT);
			panel.add(label);
		}
		
		// SETTING
		cb = new JCheckBox("Näytä virheilmoitukset");
		cb.setSelected(true);
		cb.setHorizontalAlignment(JLabel.CENTER);
		cb.setToolTipText( tooltips.get("HELP.CHECKBOXERROR") );
		cb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if ( e.getStateChange()==1 )
            		sysMsg.setErrorMessagesState(true);
            	else
            		sysMsg.setErrorMessagesState(false);
            }});		
		panel.add(cb);
		

		add(panel);
		
		
	// = = = = = = = = = = = = = = = = = = = = = = = = =
	// TYÖAJAT							   			   |
    // = = = = = = = = = = = = = = = = = = = = = = = = =
		panel = new JPanel(false);
		panel.setLayout( new GridLayout(strs[1].length+2, 1) );
		panel.setBorder( new EmptyBorder(5,5,5,5) );
		
		label = new JLabel("TYÖAJAT");
        label.setFont(TITLE_FONT);	
        label.setVerticalAlignment(JLabel.TOP);				
        label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
		// INFO
		for(int i=0; i<strs[1].length; i++) {
			label = new JLabel(strs[1][i]);
	        label.setFont(TEXT_FONT);				
	        label.setHorizontalAlignment(JLabel.LEFT);
			panel.add(label);
		}
		
		// SETTING
		cb = new JCheckBox("Näytä yleiset ilmoitukset");
		cb.setSelected(true);
		cb.setHorizontalAlignment(JLabel.CENTER);
		cb.setToolTipText( tooltips.get("HELP.CHECKBOXMESSAGE") );
		cb.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if ( e.getStateChange()==1 )
            		sysMsg.setPlainMessagesState(true);
            	else
            		sysMsg.setPlainMessagesState(false);
            }});		
		panel.add(cb);
		
		add(panel);
		
		
	// = = = = = = = = = = = = = = = = = = = = = = = = =
	// LASKE							   			   |
    // = = = = = = = = = = = = = = = = = = = = = = = = =
		panel = new JPanel(false);
		panel.setLayout( new GridLayout(strs[2].length+2, 1) );
		panel.setBorder( new EmptyBorder(5,5,5,5) );
		
		label = new JLabel("LASKE");
        label.setFont(TITLE_FONT);	
        label.setVerticalAlignment(JLabel.TOP);				
        label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		
		// INFO
		for(int i=0; i<strs[2].length; i++) {
			label = new JLabel(strs[2][i]);
	        label.setFont(TEXT_FONT);				
	        label.setHorizontalAlignment(JLabel.LEFT);
			panel.add(label);
		}
		
		// SETTING
		panel.add(new JLabel(""));
		
		add(panel);
		
	}//... HelpTab()

}
