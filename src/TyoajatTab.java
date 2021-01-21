import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
* <h1>Työajat -tab</h1>
* A tab where working hours can be filled
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-07
*/
public class TyoajatTab  extends JPanel implements ActionListener, FocusListener {
	private static int 				TITLE_SIZE = 14,
									HGAP_TAB = 3;
	
	private JFormattedTextField[][] tf_days = new JFormattedTextField[5][28];
	private JButton[]				clearButtons = new JButton[5];
	
	private	Border 					border_gray = BorderFactory.createLineBorder(Color.gray),
									border_green = BorderFactory.createLineBorder(Color.green);
	
	private String[]				dayNames = {"MA","TI","KE","TO","PE","LA","SU"};
	
	private Tooltips 				tooltips = Tooltips.getInstance();
	private SystemMessage			sysMsg = SystemMessage.getInstance();
	
	
	/**
	 * Constructor
	 */
	public TyoajatTab() {
		super();
		
		GridLayout gridlayout = new GridLayout(1, 5);
		gridlayout.setHgap(HGAP_TAB);
        this.setLayout(gridlayout);
        this.setBackground(Color.black);

        
	    for(int i=0; i<5; i++) {
	        JPanel weekPanel = new JPanel();
	        weekPanel.setLayout(new GridLayout(23, 3));
	        weekPanel.setBorder( new EmptyBorder(0,0,0,10) );
	        
	        // TITLE
	        addPadding(weekPanel, 1);
	        JLabel label = new JLabel("VIIKKO " + (i+1));
	        label.setFont(new Font("", Font.PLAIN, TITLE_SIZE));	
	        label.setVerticalAlignment(JLabel.TOP);				
	        label.setHorizontalAlignment(JLabel.CENTER);
	        weekPanel.add(label);
	        addPadding(weekPanel, 1);
	        
	        // DAYS
	        tf_days[i] = new JFormattedTextField[28];
	        int idx=0;
	        
	        while( idx < 28 ) {
	        	label = new JLabel(dayNames[idx/4]);
	        	label.setHorizontalAlignment(JLabel.CENTER);
		        weekPanel.add( label );
		        
		        tf_days[i][idx] = new JFormattedTextField( new String("") );
		        tf_days[i][idx].setToolTipText( tooltips.get("TYOAJAT.BEGINTIME") );
		    	tf_days[i][idx].setHorizontalAlignment(SwingConstants.CENTER);
		    	tf_days[i][idx].setBorder(border_gray);
		    	tf_days[i][idx].addFocusListener(this);
		    	weekPanel.add( tf_days[i][idx] );
		    	tf_days[i][idx+1] = new JFormattedTextField( new String("") );
		    	tf_days[i][idx+1].setToolTipText( tooltips.get("TYOAJAT.ENDTIME") );
		    	tf_days[i][idx+1].setHorizontalAlignment(SwingConstants.CENTER);
		    	tf_days[i][idx+1].setBorder(border_gray);
		    	tf_days[i][idx+1].addFocusListener(this);
		    	weekPanel.add( tf_days[i][idx+1] );
		    	
		    	addPadding(weekPanel, 1);
		    	
		    	tf_days[i][idx+2] = new JFormattedTextField( new String("") );
		    	tf_days[i][idx+2].setToolTipText( tooltips.get("TYOAJAT.BEGINTIME") );
		    	tf_days[i][idx+2].setHorizontalAlignment(SwingConstants.CENTER);
		    	tf_days[i][idx+2].setBorder(border_gray);
		    	tf_days[i][idx+2].addFocusListener(this);
		    	weekPanel.add( tf_days[i][idx+2] );
		    	tf_days[i][idx+3] = new JFormattedTextField( new String("") );
		    	tf_days[i][idx+3].setToolTipText( tooltips.get("TYOAJAT.ENDTIME") );
		    	tf_days[i][idx+3].setHorizontalAlignment(SwingConstants.CENTER);
		    	tf_days[i][idx+3].setBorder(border_gray);
		    	tf_days[i][idx+3].addFocusListener(this);
		    	weekPanel.add( tf_days[i][idx+3] );
	    	
		    	addPadding(weekPanel, 3);
		    	
		    	idx=idx+4;
		    	
			}
	
	        
	        // CLEAR BUTTON
	        addPadding(weekPanel, 1);
	        
	        clearButtons[i] = new JButton("X");
	        clearButtons[i].setToolTipText( tooltips.get("TYOAJAT.BUTTONCLEAR") );
	        clearButtons[i].setActionCommand("CLEAR");
	        clearButtons[i].addActionListener(this);
	        weekPanel.add( clearButtons[i] );
	        
	        addPadding(weekPanel, 1);
	        

	        // ADD WEEKPANEL TO TAB
	        add(weekPanel);
	    }
	    
	}//...public TyoajatTab()
	
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if( src==clearButtons[0] ) {
			for(int i=0; i<28; i++) {
				tf_days[0][i].setValue( "" );
				tf_days[0][i].setBorder(border_gray);
			}
				
		} else if( src==clearButtons[1] ) {
			for(int i=0; i<28; i++) {
				tf_days[1][i].setValue( "" );
				tf_days[1][i].setBorder(border_gray);
			}
				
		} else if( src==clearButtons[2] ) {
			for(int i=0; i<28; i++) {
				tf_days[2][i].setValue( "" );
				tf_days[2][i].setBorder(border_gray);
			}
				
		} else if( src==clearButtons[3] ) {
			for(int i=0; i<28; i++) {
				tf_days[3][i].setValue( "" );
				tf_days[3][i].setBorder(border_gray);
			}
				
		} else if( src==clearButtons[4] ) {
			for(int i=0; i<28; i++) {
				tf_days[4][i].setValue( "" );
				tf_days[4][i].setBorder(border_gray);
			}
		}
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
				String value = tf.getText();
				
				value = value.replace(" ", "");
				value = value.replace(":", ".");
				value = value.replace(",", ".");
				
				if( value.equals("")) {
					tf.setBorder(border_gray);
					return;
				}
				
				value = getCheckedValue(value, tf);
				
				if ( value.equals("") ) {
					tf.setText(value);
					tf.setBorder(border_gray);
				
				} else {
					tf.setText(value);
					tf.setBorder(border_green);
				}
				
        }	});
		
	}
	
	/**
	 * Checks if work hour value is in correct form and returns edited value.
	 * Creates error message if value is incorrect form.
	 * 
	 * @param str - Value that is in TextField
	 * @param tf - JFormattedTextField where value originally is
	 * @return String edited value if correct form, otherwise empty String
	 */
	private String getCheckedValue(String str, JFormattedTextField tf) {
		Boolean BeginTime = false;
		JFormattedTextField tf_cmp = new JFormattedTextField();
		double value = 0.00;
		int mins = 0;
		
		// IF NUMBER
		try {
			value = Double.parseDouble(str);
			mins = Integer.parseInt((str+":0").replace(".", ":").split(":")[1]);
			
		
		} catch (Exception e) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("TYOAJAT.ERROR_NOTNUMBER"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		// IS BEGINTIME?
		for(int i=0; i<5; i++) {
			for(int j=0; j<28; j++) {
				if(j%2==0 && tf==tf_days[i][j]) {
					BeginTime = true;
					tf_cmp = tf_days[i][j+1];
				
				} else if(j%2==1 && tf==tf_days[i][j]) {
					tf_cmp = tf_days[i][j-1];
		}	}	}
		
		// MINUTES CAN'T GO OVER 60
		if( mins<0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("TYOAJAT.ERROR_MINUTES"), 
									"VIRHE: Verotiedot");
			return "";
		
		}else if ( mins>59 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("TYOAJAT.ERROR_MINUTES"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		// NOT NEGATIVE
		if( Double.compare(value,0.00 )<0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("TYOAJAT.ERROR_NEG"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		// BEGINTIME
		if( BeginTime && Double.compare(value,24.00 )==0 ) {
			return "00:00";
		
		} else if( BeginTime && Double.compare(value,24.00 )>0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("TYOAJAT.ERROR_BEGIN"), 
									"VIRHE: Verotiedot");
			return "";
		}
		
		// ENDTIME
		if( !BeginTime && Double.compare(value,00.00 )==0 ) {
			return "24:00";
		
		} else if( !BeginTime && Double.compare(value,24.00 )>0 ) {
			sysMsg.newErrorMessage(	this,
									tooltips.get("TYOAJAT.ERROR_END"), 
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
											tooltips.get("TYOAJAT.ERROR_BEGINGREATERTHANEND"), 
											"VIRHE: Verotiedot");
					return "";
				
			}} else {
				if( Double.compare(value, value_cmp)<0 || Double.compare(value, value_cmp)==0 ) {
					sysMsg.newErrorMessage(	this,
											tooltips.get("TYOAJAT.ERROR_ENDLOWERTHANBEGIN"), 
											"VIRHE: Verotiedot");
					return "";
					
			}	}
		
		} catch (Exception e) {
				
		}
		
		
		str = String.format("%.2f", value);
		str = str.replace('.', ':');
		str = str.replace(',', ':');
		
		return str;
		
	}//...String getCheckedValue(String, JFormattedTextField)
	
	
	/**
	 * Gathers every TextFields' data as a String to an array
	 * and returns it
	 * 
	 * @return String array[140] - TextFields' data
	 */
	public String[] getValues() {
		String[] values = new String[140];
		int idx = 0;
		
		while(idx<140) {
			for(int i=0; i<5; i++) {
				for(int j=0; j<27; j++) {
					
					// BOTH BEGIN AND END TIMES HAVE TO BE ACCEPTABLE
					values[idx] = tf_days[i][j].getText().replace(':', '.').replace(',', '.');
					values[idx+1] = tf_days[i][j+1].getText().replace(':', '.').replace(',', '.');
					
					if(values[idx].equals("") || values[idx+1].equals("")) {
						values[idx]="";
						values[idx+1]="";
					}
					
					idx++; idx++; j++;
		}	}	}
		
		return values;
	}//...String[] getValues()
	
	
}//...class TyoajatTab
