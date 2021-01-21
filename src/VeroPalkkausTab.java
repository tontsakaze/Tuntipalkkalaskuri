import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
* <h1>Verotiedot -tab</h1>
* A tab where information about tax etc. can be filled
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-07
*/
public class VeroPalkkausTab extends JPanel implements ActionListener, FocusListener, PropertyChangeListener {
	private static int 				TITLE_SIZE = 20,
									HGAP_TAB = 3,
									VGAP_TAB = 3;
	
	private FileHandler 			fileHandler = FileHandler.getInstance();
	
	private JFormattedTextField[] 	tf_verotiedot = new JFormattedTextField[8];
	private JFormattedTextField[] 	tf_lisat = new JFormattedTextField[13];
	
	private JButton 				button_clear, button_save, button_load, button_default;
	
	private NumberFormat 			numFormat; 
	
	private JComboBox<String> 		tessit;
	
	private JCheckBox[] 			cb_omalisa = new JCheckBox[8];
	private JFormattedTextField[] 	tf_omalisa = new JFormattedTextField[3];
	
	private Tooltips 				tooltips = Tooltips.getInstance();
	private SystemMessage			sysMsg = SystemMessage.getInstance();
	
	/**
	 * Constructor
	 */
	public VeroPalkkausTab() {
		super();
		setUpFormats();
		
        this.setLayout(new GridLayout(1, 1));
        
        // Content that is added to the panel
        JPanel contents = new JPanel();
        contents.setBackground(Color.black);
        GridLayout contents_layout = new GridLayout(3,2);
        contents_layout.setHgap(HGAP_TAB);
        contents_layout.setVgap(VGAP_TAB);
        contents.setLayout( contents_layout );
        
        
// - - - - - - - - - - - -
//		VEROTIEDOT PANEL |
// - - - - - - - - - - - -
        JPanel panel_verotiedot = new JPanel();
        panel_verotiedot.setLayout( new GridLayout(9,2) );
        
        JLabel title_verotiedot = new JLabel("VEROTIEDOT");				// 1,1
        title_verotiedot.setFont(new Font("", Font.PLAIN, TITLE_SIZE));	
        title_verotiedot.setVerticalAlignment(JLabel.TOP);				
        title_verotiedot.setHorizontalAlignment(JLabel.CENTER);			
        panel_verotiedot.add(title_verotiedot);							
        panel_verotiedot.add( new JLabel(" ") );						// 1,2
        
        panel_verotiedot.add( new JLabel("ANSAITTU (€)") );				// 2,1
        tf_verotiedot[0] = new JFormattedTextField(numFormat);
        tf_verotiedot[0].setToolTipText( tooltips.get("VEROP.ANSAITTU") );
        tf_verotiedot[0].setValue(new Double(0.00));
        tf_verotiedot[0].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[0].addPropertyChangeListener(this);
        tf_verotiedot[0].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[0]);							// 2,2
        
        panel_verotiedot.add( new JLabel("TULORAJA (€)") );				// 3,1
        tf_verotiedot[1] = new JFormattedTextField(numFormat);
        tf_verotiedot[1].setToolTipText( tooltips.get("VEROP.TULORAJA") );
        tf_verotiedot[1].setValue(new Double(0.00));
        tf_verotiedot[1].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[1].addPropertyChangeListener(this);
        tf_verotiedot[1].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[1]);							// 3,2
        
        panel_verotiedot.add( new JLabel("PERUSVERO (%)") );			// 4,1
        tf_verotiedot[2] = new JFormattedTextField(numFormat);
        tf_verotiedot[2].setToolTipText( tooltips.get("VEROP.PERUSVERO") );
        tf_verotiedot[2].setValue(new Double(0.00));
        tf_verotiedot[2].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[2].addPropertyChangeListener(this);
        tf_verotiedot[2].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[2]);							// 4,2
        
        panel_verotiedot.add( new JLabel("LISÄVERO (%)") );				// 5,1
        tf_verotiedot[3] = new JFormattedTextField(numFormat);
        tf_verotiedot[3].setToolTipText( tooltips.get("VEROP.LISAVERO") );
        tf_verotiedot[3].setValue(new Double(0.00));
        tf_verotiedot[3].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[3].addPropertyChangeListener(this);
        tf_verotiedot[3].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[3]);							// 5,2
        
        panel_verotiedot.add( new JLabel("ELÄKEMAKSU (%)") );			// 6,1
        tf_verotiedot[4] = new JFormattedTextField(numFormat);
        tf_verotiedot[4].setToolTipText( tooltips.get("VEROP.ELAKEMAKSU") );
        tf_verotiedot[4].setValue(new Double(7.15));
        tf_verotiedot[4].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[4].addPropertyChangeListener(this);
        tf_verotiedot[4].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[4]);							// 6,2
        
        panel_verotiedot.add( new JLabel("TYÖTTÖMYYSMAKSU (%)") );		// 7,1
        tf_verotiedot[5] = new JFormattedTextField(numFormat);
        tf_verotiedot[5].setToolTipText( tooltips.get("VEROP.TYOTTOMYYSMAKSU") );
        tf_verotiedot[5].setValue(new Double(1.25));
        tf_verotiedot[5].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[5].addPropertyChangeListener(this);
        tf_verotiedot[5].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[5]);							// 7,2
        
        panel_verotiedot.add( new JLabel("AY-MAKSU (%)") );				// 8,1
        tf_verotiedot[6] = new JFormattedTextField(numFormat);
        tf_verotiedot[6].setToolTipText( tooltips.get("VEROP.AYMAKSUP") );
        tf_verotiedot[6].setValue(new Double(0.00));
        tf_verotiedot[6].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[6].addPropertyChangeListener(this);
        tf_verotiedot[6].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[6]);							// 8,2
        
        panel_verotiedot.add( new JLabel("AY-MAKSU (€)") );				// 9,1
        tf_verotiedot[7] = new JFormattedTextField(numFormat);
        tf_verotiedot[7].setToolTipText( tooltips.get("VEROP.AYMAKSUE") );
        tf_verotiedot[7].setValue(new Double(0.00));
        tf_verotiedot[7].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_verotiedot[7].addPropertyChangeListener(this);
        tf_verotiedot[7].addFocusListener(this);
        panel_verotiedot.add(tf_verotiedot[7]);							// 9,2
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// - - - - - - - - - - - - - - - -
//		TYÖEHTOSOPIMUKSET PANEL	 |
// - - - - - - - - - - - - - - - - 
        JPanel panel_tyoehtosopimukset = new JPanel();
        panel_tyoehtosopimukset.setLayout( new GridLayout(4,1) );
        
        JLabel title_tyoehtosopimukset = new JLabel("TYÖEHTOSOPIMUKSET");
        title_tyoehtosopimukset.setFont(new Font("", Font.PLAIN, TITLE_SIZE));
        title_tyoehtosopimukset.setVerticalAlignment(JLabel.TOP);
        title_tyoehtosopimukset.setHorizontalAlignment(JLabel.CENTER);
        panel_tyoehtosopimukset.add(title_tyoehtosopimukset);

        
        tessit = new JComboBox<String>();
        fileHandler.fillTESBox(tessit);
        tessit.setToolTipText( tooltips.get("VEROP.TESSIT") );
        panel_tyoehtosopimukset.add(tessit);
        
        
        JButton button_set = new JButton("ASETA");
        button_set.setToolTipText( tooltips.get("VEROP.TESSET") );
        button_set.setActionCommand("SET");
        button_set.addActionListener(this);
        panel_tyoehtosopimukset.add(button_set);
        
        panel_tyoehtosopimukset.add(new JLabel(""));
        
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// - - - - - - - - - -
//		LISÄT PANEL  |
// - - - - - - - - - - 
        JPanel panel_lisat = new JPanel();
        panel_lisat.setLayout( new GridLayout(13,4) );
        JLabel lLine = new JLabel("-");
        lLine.setHorizontalAlignment(JLabel.CENTER);
        
        // TUNTIPALKKA
        panel_lisat.add( new JLabel("TUNTIPALKKA (€)") );			// 1,1
        addPadding(panel_lisat, 2);									// 1,2-3
        
        tf_lisat[0] = new JFormattedTextField(numFormat);
        tf_lisat[0].setToolTipText( tooltips.get("VEROP.TUNTIPALKKA") );
        tf_lisat[0].setValue(new Double(0.00));
        tf_lisat[0].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_lisat[0].addPropertyChangeListener(this);
        tf_lisat[0].addFocusListener(this);
        panel_lisat.add(tf_lisat[0]);								// 1,4
        
        addPadding(panel_lisat, 4);									// 2,
        
        // ILTALISÄ
        panel_lisat.add( new JLabel("ILTALISÄ") );					// 3,1
        addPadding(panel_lisat, 1);									// 3,2
        tf_lisat[1] = new JFormattedTextField(new String("18:00"));
        tf_lisat[1].setToolTipText( tooltips.get("VEROP.ILTALISAB") );
        tf_lisat[1].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[1].addFocusListener(this);
        panel_lisat.add(tf_lisat[1]);								// 3,3
        tf_lisat[2] = new JFormattedTextField(new String("24:00"));
        tf_lisat[2].setToolTipText( tooltips.get("VEROP.ILTALISAE") );
        tf_lisat[2].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[2].addFocusListener(this);
        panel_lisat.add(tf_lisat[2]);								// 3,4
        
        panel_lisat.add( new JLabel("ILTALISÄ (€)") );				// 4,1
        addPadding(panel_lisat, 2);									// 4,2-3
        tf_lisat[3] = new JFormattedTextField(numFormat);
        tf_lisat[3].setToolTipText( tooltips.get("VEROP.ILTALISA") );
        tf_lisat[3].setValue(new Double(0.00));
        tf_lisat[3].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_lisat[3].addPropertyChangeListener(this);
        tf_lisat[3].addFocusListener(this);
        panel_lisat.add(tf_lisat[3]);								// 4,4
        
        addPadding(panel_lisat, 4);									// 5,
        
        // YÖLISÄ
        panel_lisat.add( new JLabel("YÖLISÄ") );					// 6,1
        addPadding(panel_lisat, 1);									// 6,2
        tf_lisat[4] = new JFormattedTextField(new String("00:00"));
        tf_lisat[4].setToolTipText( tooltips.get("VEROP.YOLISAB") );
        tf_lisat[4].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[4].addFocusListener(this);
        panel_lisat.add(tf_lisat[4]);								// 6,3
        tf_lisat[5] = new JFormattedTextField(new String("06:00"));
        tf_lisat[5].setToolTipText( tooltips.get("VEROP.YOLISAE") );
        tf_lisat[5].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[5].addFocusListener(this);
        panel_lisat.add(tf_lisat[5]);								// 6,4
        
        panel_lisat.add( new JLabel("YÖLISÄ (€)") );				// 7,1
        addPadding(panel_lisat, 2);									// 7,2-3
        tf_lisat[6] = new JFormattedTextField(numFormat);
        tf_lisat[6].setToolTipText( tooltips.get("VEROP.YOLISA") );
        tf_lisat[6].setValue(new Double(0.00));
        tf_lisat[6].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_lisat[6].addPropertyChangeListener(this);
        tf_lisat[6].addFocusListener(this);
        panel_lisat.add(tf_lisat[6]);								// 7,4
        
        addPadding(panel_lisat, 4);									// 8,
        
        // LAUANTAILISÄ
        panel_lisat.add( new JLabel("LAUANTAILISÄ") );				// 9,1
        addPadding(panel_lisat, 1);									// 9,2
        tf_lisat[7] = new JFormattedTextField(new String("13:00"));
        tf_lisat[7].setToolTipText( tooltips.get("VEROP.LALISAB") );
        tf_lisat[7].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[7].addFocusListener(this);
        panel_lisat.add(tf_lisat[7]);								// 9,3
        tf_lisat[8] = new JFormattedTextField(new String("24:00"));
        tf_lisat[8].setToolTipText( tooltips.get("VEROP.LALISAE") );
        tf_lisat[8].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[8].addFocusListener(this);
        panel_lisat.add(tf_lisat[8]);								// 9,4
        
        panel_lisat.add( new JLabel("LAUANTAILISÄ (€)") );			// 10,1
        addPadding(panel_lisat, 2);									// 10,2-3
        tf_lisat[9] = new JFormattedTextField(numFormat);
        tf_lisat[9].setToolTipText( tooltips.get("VEROP.LALISA") );
        tf_lisat[9].setValue(new Double(0.00));
        tf_lisat[9].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_lisat[9].addPropertyChangeListener(this);
        tf_lisat[9].addFocusListener(this);
        panel_lisat.add(tf_lisat[9]);								// 10,4
        
        addPadding(panel_lisat, 4);									// 11,
        
        // SUNNUNTAILISÄ
        panel_lisat.add( new JLabel("SUNNUNTAILISÄ") );				// 12,1
        addPadding(panel_lisat, 1);									// 12,2
        tf_lisat[10] = new JFormattedTextField(new String("00:00"));
        tf_lisat[10].setToolTipText( tooltips.get("VEROP.SULISAB") );
        tf_lisat[10].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[10].addFocusListener(this);
        panel_lisat.add(tf_lisat[10]);								// 12,3
        tf_lisat[11] = new JFormattedTextField(new String("24:00"));
        tf_lisat[11].setToolTipText( tooltips.get("VEROP.SULISAE") );
        tf_lisat[11].setHorizontalAlignment(SwingConstants.CENTER);
        tf_lisat[11].addFocusListener(this);
        panel_lisat.add(tf_lisat[11]);								// 12,4
        
        panel_lisat.add( new JLabel("SUNNUNTAILISÄ (€)") );			// 13,1
        addPadding(panel_lisat, 2);									// 13,2-3
        tf_lisat[12] = new JFormattedTextField(numFormat);
        tf_lisat[12].setToolTipText( tooltips.get("VEROP.SULISA") );
        tf_lisat[12].setValue(new Double(0.00));
        tf_lisat[12].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_lisat[12].addPropertyChangeListener(this);
        tf_lisat[12].addFocusListener(this);
        panel_lisat.add(tf_lisat[12]);								// 13,4
        
        
        
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// - - - - - - - - - - -
//		OMALISÄ PANEL  |
// - - - - - - - - - - -
        JPanel panel_omatlisat = new JPanel();
        panel_omatlisat.setLayout( new GridLayout(10,3) );
        
        addPadding(panel_omatlisat, 1);
        JLabel title_omatlisat = new JLabel("OMA LISÄ");
        title_omatlisat.setFont(new Font("", Font.PLAIN, TITLE_SIZE));
        title_omatlisat.setVerticalAlignment(JLabel.TOP);
        title_omatlisat.setHorizontalAlignment(JLabel.CENTER);
        panel_omatlisat.add(title_omatlisat);
        addPadding(panel_omatlisat, 1);					// 1,
        
        cb_omalisa[0] = new JCheckBox("MA");		
        panel_omatlisat.add(cb_omalisa[0]);				// 2,1
        addPadding(panel_omatlisat, 1);					// 2,2
        cb_omalisa[4] = new JCheckBox("PE");		
        panel_omatlisat.add(cb_omalisa[4]);				// 2,3
        
        cb_omalisa[1] = new JCheckBox("TI");		
        panel_omatlisat.add(cb_omalisa[1]);				// 3,1
        addPadding(panel_omatlisat, 1);					// 3,2
        cb_omalisa[5] = new JCheckBox("LA");
        panel_omatlisat.add(cb_omalisa[5]);				// 3,3
        
        cb_omalisa[2] = new JCheckBox("KE");
        panel_omatlisat.add(cb_omalisa[2]);				// 4,1
        addPadding(panel_omatlisat, 1);					// 4,2
        cb_omalisa[6] = new JCheckBox("SU");
        panel_omatlisat.add(cb_omalisa[6]);				// 4,3
        
        cb_omalisa[3] = new JCheckBox("TO");
        panel_omatlisat.add(cb_omalisa[3]);				// 5,1
        addPadding(panel_omatlisat, 2);					// 5,2-3
        
        for(int i=0; i<7; i++)
        	cb_omalisa[i].setToolTipText( tooltips.get("VEROP.OMALISAPAIVAT") );
        
        addPadding(panel_omatlisat, 3);					// 6,
        
        panel_omatlisat.add( new JLabel("AIKAVÄLI") );	// 7,1
        tf_omalisa[0] = new JFormattedTextField(new String(""));
        tf_omalisa[0].setToolTipText( tooltips.get("VEROP.OMALISAB") );
        tf_omalisa[0].setHorizontalAlignment(SwingConstants.CENTER);
        tf_omalisa[0].addFocusListener(this);
        panel_omatlisat.add(tf_omalisa[0]);		// 7,2
        tf_omalisa[1] = new JFormattedTextField(new String(""));
        tf_omalisa[1].setToolTipText( tooltips.get("VEROP.OMALISAE") );
        tf_omalisa[1].setHorizontalAlignment(SwingConstants.CENTER);
        tf_omalisa[1].addFocusListener(this);
        panel_omatlisat.add(tf_omalisa[1]);		// 7,3
        
        addPadding(panel_omatlisat, 3);					// 8,
        
        panel_omatlisat.add( new JLabel("MÄÄRÄ") );		// 9,1
        addPadding(panel_omatlisat, 1);					// 9,2
        tf_omalisa[2] = new JFormattedTextField(numFormat);
        tf_omalisa[2].setToolTipText( tooltips.get("VEROP.OMALISA") );
        tf_omalisa[2].setValue( new Double(0.0) );
        tf_omalisa[2].setHorizontalAlignment(SwingConstants.RIGHT);
        tf_omalisa[2].addPropertyChangeListener(this);
        tf_omalisa[2].addFocusListener(this);
        panel_omatlisat.add(tf_omalisa[2]);				// 9,3
        
        cb_omalisa[7] = new JCheckBox("Prosentteina palkasta");
        cb_omalisa[7].setToolTipText( tooltips.get("VEROP.OMALISAPROSENTTI") );
        panel_omatlisat.add(cb_omalisa[7]);				// 10,1
    //- SETS ITEMLISTENER TO CHECKBOX - - - - - - - - - - - - -
        cb_omalisa[7].addItemListener(new ItemListener() {	//
            public void itemStateChanged(ItemEvent e) {     //
            	if ( e.getStateChange()==1 ) {				//
            		tf_omalisa[0].setEnabled(false);		//
            		tf_omalisa[1].setEnabled(false);		//
            		for(int i=0; i<7; i++)					//
        			cb_omalisa[i].setEnabled(false);		//
            												//
            	} else {									//
            		tf_omalisa[0].setEnabled(true);			//
            		tf_omalisa[1].setEnabled(true);			//
        		for(int i=0; i<7; i++)						//
            			cb_omalisa[i].setEnabled(true);		//
            	}											//
            }});											//
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        addPadding(panel_omatlisat, 2);					// 10,2-3
        
        
        
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
        
        // CLEAR BUTTON
        JPanel panel_buttons1 = new JPanel();
        panel_buttons1.setLayout( new GridLayout(1,3) );
        
        button_clear = new JButton("TYHJENNÄ");
        button_clear.setToolTipText( tooltips.get("VEROP.BUTTONCLEAR") );
        button_clear.setActionCommand("CLEAR");
        button_clear.addActionListener(this);

        panel_buttons1.add(button_clear);
        panel_buttons1.add(new JLabel(""));
        panel_buttons1.add(new JLabel(""));
        
        
        // DEFAULT&LOAD&SAVE BUTTONS
        JPanel panel_buttons2 = new JPanel();
        panel_buttons2.setLayout( new GridLayout(1,3) );
        
        button_default = new JButton("OLETUS");
        button_default.setToolTipText( tooltips.get("VEROP.BUTTONDEFAULT") );
        button_default.setActionCommand("DEFAULT");
        button_default.addActionListener(this);
        
        button_load = new JButton("LATAA");
        button_load.setToolTipText( tooltips.get("VEROP.BUTTONLOAD") );
        button_load.setActionCommand("LOAD");
        button_load.addActionListener(this);
        
        button_save = new JButton("TALLENNA");
        button_save.setToolTipText( tooltips.get("VEROP.BUTTONSAVE") );
        button_save.setActionCommand("SAVE");
        button_save.addActionListener(this);
        
        panel_buttons2.add(button_default);
        panel_buttons2.add(button_load);
        panel_buttons2.add(button_save);
        
        
        contents.add(panel_verotiedot);
        contents.add(panel_tyoehtosopimukset);
        contents.add(panel_lisat);
        contents.add(panel_omatlisat);
        contents.add(panel_buttons1);
        contents.add(panel_buttons2);
        
        this.add(contents);
        
	}//...public VeroPalkkausTab()


	public void actionPerformed(ActionEvent e) {
		if ("CLEAR".equals( e.getActionCommand() )) {
			for(int i=0;i<8;i++) {
				tf_verotiedot[i].setValue(0.00);
			
			} for(int i=0;i<13;i++) {
				if( i%3==0 )
					tf_lisat[i].setValue(0.00);
				else
					tf_lisat[i].setText( "" );
			
			} for(int i=0;i<8;i++) {
				cb_omalisa[i].setSelected(false);
			}
			
			tf_omalisa[0].setText("");
			tf_omalisa[1].setText("");
			tf_omalisa[2].setValue(0.00);
			
	    
		} else if ("LOAD".equals( e.getActionCommand() )) {
			FileHandler.loadSettings(tf_verotiedot, tf_lisat, cb_omalisa, tf_omalisa, false);
			sysMsg.newPlainMessage(	this,
									tooltips.get("VEROP.NOTIFY_LOAD"), 
									"Verotiedot");
			
	    } else if ("SAVE".equals( e.getActionCommand() )) {
	    	FileHandler.saveSettings(tf_verotiedot, tf_lisat, cb_omalisa, tf_omalisa);
	    	sysMsg.newPlainMessage(	this,
									tooltips.get("VEROP.NOTIFY_SAVE"), 
									"Verotiedot");
	    	
	    } else if ("SET".equals( e.getActionCommand() )) {
			FileHandler.loadTessitData( tf_lisat, (String)tessit.getSelectedItem() );
			sysMsg.newPlainMessage(	this,
									tooltips.get("VEROP.NOTIFY_TESSIT"), 
									"Verotiedot");
	    
	    } else if ("DEFAULT".equals( e.getActionCommand() )) {
	    	FileHandler.loadSettings(tf_verotiedot, tf_lisat, cb_omalisa, tf_omalisa, true);
	    	sysMsg.newPlainMessage(	this,
									tooltips.get("VEROP.NOTIFY_DEFAULT"), 
									"Verotiedot");
	    	
	    }
		
	}
	
	public void focusGained(FocusEvent fe) {
		SwingUtilities.invokeLater( new Runnable() {

	        public void run() {
	         ((JFormattedTextField)fe.getSource()).selectAll();  
	        }
       });
	}


	public void focusLost(FocusEvent fe) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				JFormattedTextField tf = ((JFormattedTextField)fe.getSource());
				// CHECK IF TIME FIELD
				if( tf==tf_lisat[1] || tf==tf_lisat[4] || tf==tf_lisat[7] || tf==tf_lisat[10] || tf==tf_omalisa[0]) {
					tf.setText( getCheckedValue(tf, true) );
					return;
				
				} else if( tf==tf_lisat[2] || tf==tf_lisat[5] || tf==tf_lisat[8] || tf==tf_lisat[11] || tf==tf_omalisa[1]) {
					tf.setText( getCheckedValue(tf, false) );
					return;
				}
								
        }	});
		
	}
	
	
	/**
	 * Checks if time value is in correct form and returns edited value.
	 * Creates error message if value is incorrect form.
	 * 
	 * @param tf - JFormattedTextField which value is checked
	 * @param BeginTime - True if TextField is for beginning of time frame
	 * @return String edited value if correct form, otherwise empty String
	 */
	private String getCheckedValue(JFormattedTextField tf, boolean BeginTime) {
		JFormattedTextField tf_cmp = new JFormattedTextField();
		String str = tf.getText().replace(':', '.').replace(',', '.').replace(" ", "");
		double value = 0.00;
		int mins = 0;
		
		if( str.equals("") )
			return "";
		
		// IF NUMBER
		try {
			value = Double.parseDouble(str);
			mins = Integer.parseInt((str+":0").replace(".", ":").split(":")[1]);
		
		} catch (Exception e) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("VEROP.ERROR_NOTNUMBER"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		// GET COMPARABLE TEXTFIELD
		if( tf==tf_omalisa[0] ) {
			tf_cmp = tf_omalisa[1];
		
		} else if ( tf==tf_omalisa[1] ) {
			tf_cmp = tf_omalisa[0];
		
		} else {
			int i = 1;
			while(i<11) {
				if( tf==tf_lisat[i] ) {
					tf_cmp = tf_lisat[i+1];
				
				} else if( tf==tf_lisat[i+1] ) {
					tf_cmp = tf_lisat[i];
				}
				
				i=i+3;
		}	}
		
		// MINUTES CAN'T GO OVER 60
		if( mins<0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("VEROP.ERROR_MINUTES"), 
									"VIRHE: Verotiedot");
			return "";
		
		}else if ( mins>59 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("VEROP.ERROR_MINUTES"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		
		// NOT NEGATIVE
		if( Double.compare(value,0.00 )<0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("VEROP.ERROR_NEGVALUE"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		// BEGINTIME
		if( BeginTime && Double.compare(value,24.00 )==0 ) {
			return "00:00";
		
		} else if( BeginTime && Double.compare(value,24.00 )>0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("VEROP.ERROR_BEGIN"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		// ENDTIME
		if( !BeginTime && Double.compare(value,00.00 )==0 ) {
			return "24:00";
		
		} else if( !BeginTime && Double.compare(value,24.00 )>0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("VEROP.ERROR_END"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		
		// COMPARABLE VALUE
		try {
			double value_cmp = Double.parseDouble( (tf_cmp.getText().replace(':', '.')) );
			
			// IF BEGINTIME
			if ( BeginTime ) {
				if( Double.compare(value, value_cmp)>0 || Double.compare(value, value_cmp)==0 ) {
					sysMsg.newErrorMessage(	this,
											tooltips.get("VEROP.ERROR_BEGINGREATERTHANEND"), 
											"VIRHE: Verotiedot");
					return "";
				
			}} else {
				if( Double.compare(value, value_cmp)<0 || Double.compare(value, value_cmp)==0 ) {
					sysMsg.newErrorMessage(	this,
											tooltips.get("VEROP.ERROR_ENDLOWERTHANBEGIN"), 
											"VIRHE: Verotiedot");
					return "";
					
			}	}
		
		} catch (Exception e) {
				
		}
		
		
		str = String.format("%.2f", value);
		str = str.replace('.', ':');
		str = str.replace(',', ':');
		
		return str;
	
	}//...String getCheckedValue(JFormattedTextField, boolean)
	
	
	public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        
        // VEROTIEDOT
        for(int i=0;i<8;i++) {
        	if ( ((Number)tf_verotiedot[i].getValue()).doubleValue() < 0.00 ) {
        		tf_verotiedot[i].setValue(0.00);
        		sysMsg.newErrorMessage(	this,
										tooltips.get("VEROP.ERROR_NEGVALUE"), 
										"VIRHE: Verotiedot");
        		break; 
        	}
        	
        	if ( source == tf_verotiedot[i] && source != tf_verotiedot[0] && source != tf_verotiedot[1] && source != tf_verotiedot[6] ) {
        		if ( ((Number)tf_verotiedot[i].getValue()).doubleValue() > 100.00 ) {
        			tf_verotiedot[i].setValue(100.00);
        			sysMsg.newErrorMessage(	this,
											tooltips.get("VEROP.ERROR_OVER100PERCENT"), 
											"VIRHE: Verotiedot");
            		break; 
        } 	} 	}

        // LISÄT
        for(int i=0;i<13;i++) {
        	if ( i%3==0 && ((Number)tf_lisat[i].getValue()).doubleValue() < 0.00 ) {
        		tf_lisat[i].setValue(0.00);
        		sysMsg.newErrorMessage(	this,
        								tooltips.get("VEROP.ERROR_NEGVALUE"), 
        								"VIRHE: Verotiedot");
        		break; 
    	} 	}
        
        // OMA LISÄ
        if ( source == tf_omalisa[2] && cb_omalisa[7].isSelected() ) {
        	if ( ((Number)tf_omalisa[2].getValue()).doubleValue() < 0.00 ) {
        		tf_omalisa[2].setValue(0.00);
        		sysMsg.newErrorMessage(	this,
										tooltips.get("VEROP.ERROR_NEGVALUE"), 
										"VIRHE: Verotiedot");
        		
        	} else if ( ((Number)tf_omalisa[2].getValue()).doubleValue() > 100.00 ) {
        		tf_omalisa[2].setValue(100.00);
        		sysMsg.newErrorMessage(	this,
        								tooltips.get("VEROP.ERROR_OVER100PERCENT"), 
        								"VIRHE: Verotiedot");
        	}
        	
        } else if ( source == tf_omalisa[2] && !cb_omalisa[7].isSelected() ) {
        	if ( ((Number)tf_omalisa[2].getValue()).doubleValue() < 0.00 ) {
        		tf_omalisa[2].setValue(0.00);
        		sysMsg.newErrorMessage(	this,
        								tooltips.get("VEROP.ERROR_NEGVALUE"), 
    									"VIRHE: Verotiedot");
        		
		} 	}
        
      
    }//...propertyChange(PropertyChangeEvent)
	
	
	/**
	 * Gathers every TextFields' and CheckBoxes' data as 
	 * a String to an array and returns it
	 * 
	 * @return String array[32] - TextFields' & CheckBoxes' data
	 */
	public String[] getValues() {
		String[] values = new String[32];
		
		for(int i=0; i<8; i++)
			values[i] = tf_verotiedot[i].getText().replace(":",".").replace(",", ".").replaceAll("\\s+", "");
		
		for(int i=8; i<21; i++)
			values[i] = tf_lisat[(i+5)%13].getText().replace(":",".").replace(",", ".").replaceAll("\\s+", "");
		
		for(int i=21; i<28; i++) {
			values[i] = "false";
			if( cb_omalisa[i%7].isSelected() )
				values[i] = "true";
		}
		
		values[28] = tf_omalisa[0].getText().replace(":",".").replace(",", ".").replaceAll("\\s+", "");
		values[29] = tf_omalisa[1].getText().replace(":",".").replace(",", ".").replaceAll("\\s+", "");
		if ( values[28].equals("") || values[29].equals("") ) {
			values[28] = "";
			values[29] = "";
		}
		
		values[30] = tf_omalisa[2].getText().replace(":",".").replace(",", ".").replaceAll("\\s+", "");
		values[31] = "false";
		if( cb_omalisa[7].isSelected() )
			values[31] = "true";
		
		return values;
	}//...String[] getValues()
	
	
	/**
	 * Sets up format that is used for double based TextFields
	 */
	private void setUpFormats() {
		numFormat = NumberFormat.getNumberInstance(new Locale("fi","FI"));
		numFormat.setMaximumFractionDigits(2);
		numFormat.setMinimumFractionDigits(2);
	}
	
	
	/**
	 * Adds padding (JLabels) to given JPanel
	 * 
	 * @param panel - JPanel that is filled with empty JLabels
	 * @param n - How many empty JLabels are added
	 */
	private void addPadding(JPanel panel, int n) {
		while(n>0) {
			panel.add( new JLabel("") );
			n--;
		}
	}


	
}//...class VeroPalkkausTab