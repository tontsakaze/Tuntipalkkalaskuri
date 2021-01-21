import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
* <h1>Laske Tab</h1>
* A tab where wage slip can be calculated
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-18
*/
public class LaskeTab extends JPanel implements ActionListener {
	private static int 				TITLE_SIZE = 15,
									TEXT_SIZE = 14;
	
	private static String			FONT_NAME = "Times New Roman";
	
	// How many full hours of perus,ilta,yö,la,su,oma
	private double[]				d_kpls =   {0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
	// The "price" of that hour
									d_ahinta = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	
	private JLabel[]				l_kpls = new JLabel[6],
									l_ahinta = new JLabel[6],
									l_summa = new JLabel[6],
									l_muut = new JLabel[7];
	
	private JFormattedTextField[]	tf = new JFormattedTextField[4];
	private NumberFormat			currencyFormat = NumberFormat.getCurrencyInstance(new Locale("FI", "fi"));
	
	private JButton					button_save, button_calculate;
	
	private TyoajatTab				tyoajatTab;
	private VeroPalkkausTab			veropalkkausTab;
	
	private FileHandler 			fileHandler = FileHandler.getInstance();
	private Tooltips 				tooltips = Tooltips.getInstance();

	/**
	 * Constructor
	 * @param t1 - VeroPalkkausTab (used in calculate-function)
	 * @param t2 - TyoajatTab (used in calculate-function)
	 */
	public LaskeTab(VeroPalkkausTab t1, TyoajatTab t2) {
		super();
		veropalkkausTab = t1;
		tyoajatTab = t2;
		init();
		//setUpTooltips();
		
		GridLayout gridlayout = new GridLayout(1, 5);
        this.setLayout(gridlayout);
        this.setBackground(Color.black);
        
        JPanel panel;
        JLabel label;
        
        // 1. PANEL
        panel = new JPanel();
        panel.setLayout( new GridLayout(18, 1) );
        panel.setBorder( new EmptyBorder(0,20,0,0) );
        
        String str[] = {"Perustunnit", "Iltalisä", "Yölisä",
						"Lauantailisä", "Sunnuntailisä", "Oma lisä",
						"YHTEENSÄ", "", "Ay-Maksu", "Työttömyysvakuutusmaksu",
						"Työeläkemaksu", "Ennakkopidätys", "YHTEENSÄ",
						" ", "MAKSETAAN"};
        
        panel.add( createTitle("SELITE", true) );		//0,0
        for(int i=0; i<15; i++) {				//1-15
        	if(i==6 || i==12 || i==14)
        		panel.add( createText(str[i], true) );
        	else
        		panel.add( createText(str[i], false) );
        }
        addPadding(panel, 1);					// 16
        button_calculate = new JButton("LASKE");
        button_calculate.addActionListener(this);
        button_calculate.setToolTipText( tooltips.get("LASKE.BUTTONCALC") );
        panel.add( button_calculate );		//11
        
        add(panel);
        
        
        // 2. PANEL
        panel = new JPanel();
        panel.setLayout( new GridLayout(18, 1) );
        
        label = createTitle("KPL", true);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add( label );		//0,0
        for(int i=0; i<6; i++) { 					//1-6
        	l_kpls[i].setHorizontalAlignment(SwingConstants.RIGHT);
        	panel.add( l_kpls[i] );
        }
        addPadding(panel, 11);					//7-17
        
        add(panel);
        
        
        // 3. PANEL
        panel = new JPanel();
        panel.setLayout( new GridLayout(18, 1) );
        
        label = createTitle("A-HINTA", true);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add( label );	//0,0
        for(int i=0; i<6; i++){ 					//1-6
        	l_ahinta[i].setHorizontalAlignment(SwingConstants.RIGHT);
        	panel.add( l_ahinta[i] );
        }
        addPadding(panel, 11);					//7-17
        
        add(panel);
        
        
        // 4. PANEL
        panel = new JPanel();
        panel.setLayout( new GridLayout(18, 1) );
        panel.setBorder( new EmptyBorder(0,0,0,20) );
        
        label = createTitle("SUMMA (€)", true);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add( label );	//0
        for(int i=0; i<6; i++) {	
        	l_summa[i].setHorizontalAlignment(SwingConstants.RIGHT);//1-6
        	panel.add( l_summa[i] );
        }
        
        l_muut[0].setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add( l_muut[0] );					//7
        addPadding(panel, 1);					//8
        
        for(int i=1; i<6; i++) 	{				//9-13
        	l_muut[i].setHorizontalAlignment(SwingConstants.RIGHT);
        	panel.add( l_muut[i] );
        }
        
        addPadding(panel, 1);					//14
        l_muut[6].setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add( l_muut[6] );					//15
        
        addPadding(panel, 2);					//16-17
        
        add(panel);
        
        
        // 5. Panel
        panel = new JPanel();
        panel.setLayout( new GridLayout(12, 1) );
        panel.setBorder( new EmptyBorder(0,20,0,20) );
        
        label = createTitle("MAKSETAAN", false);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setToolTipText( tooltips.get("LASKE.MAKSETAANFIELD") );
        panel.add( label );				//0
        
        tf[0].setToolTipText( tooltips.get("LASKE.MAKSETAANFIELD") );
        panel.add( tf[0] );				//1
        addPadding(panel, 1);			//2
        
        label = createTitle("PERUSTUNNIT", false);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setToolTipText( tooltips.get("LASKE.PERUSTUNNITFIELD") );
        panel.add( label );				//3
        
        tf[1].setToolTipText( tooltips.get("LASKE.PERUSTUNNITFIELD") );
        panel.add( tf[1] );				//4
        
        label = createTitle("LISÄT", false);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setToolTipText( tooltips.get("LASKE.LISATFIELD") );
        panel.add( label );				//5
        
        tf[2].setToolTipText( tooltips.get("LASKE.LISATFIELD") );
        panel.add( tf[2] );				//6
        addPadding(panel, 1);			//7
        
        label = createTitle("MAKSUT", false);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setToolTipText( tooltips.get("LASKE.MAKSUTFIELD") );
        panel.add( label );				//8
        
        tf[3].setToolTipText( tooltips.get("LASKE.MAKSUTFIELD") );
        panel.add( tf[3] );				//9
        addPadding(panel, 1);			//10
        
        button_save = new JButton("TALLENNA");
        button_save.addActionListener(this);
        button_save.setToolTipText( tooltips.get("LASKE.BUTTONSAVE") );
        panel.add( button_save );		//11
        
        add(panel);
	}//...public LaskeTab()
	

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==button_calculate )
			calculate();
			
		if( e.getSource()==button_save ) {
			String[] kpl = new String[15];
			String[] ahinta = new String[15];
			String[] summa = new String[15];
			
			//KPLS
			for(int i=0; i<6; i++)
				kpl[i]=l_kpls[i].getText();
			for(int i=6; i<15; i++)
				kpl[i]=" ";
			
			//AHINTA
			for(int i=0; i<6; i++)
				ahinta[i]=l_ahinta[i].getText();
			for(int i=6; i<15; i++)
				ahinta[i]=" ";
			
			//SUMMA
			for(int i=0; i<6; i++)
				summa[i]=l_summa[i].getText();
			summa[6]=l_muut[0].getText();
			summa[7]=" ";
			for(int i=1; i<6; i++)
				summa[i+7]=l_muut[i].getText();
			summa[13]=" ";
			summa[14]=l_muut[6].getText();
			
			
			fileHandler.saveCalculations(kpl, ahinta, summa);
		}
	}
	
	
	/**
	 * Takes data from Verotiedot & Työajat -tabs in a String array form, 
	 * uses it to calculate salary etc. and updates this tab
	 */
	private void calculate() {
		// TODO: ADD calculations
		// TODO: ADD HINNAT
		
		String temp[] = {"",""};
		double diff;

		
		try {
			// = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			// CALCULATE KPLS										 |
			// = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			for(int i=0; i<6; i++)
				d_kpls[i]=0;
			
			String[] verotiedot = veropalkkausTab.getValues();
			String[] tyoajat = tyoajatTab.getValues();
			
			// GO THROUGH EVERY TYOAJAT FIELD
			for(int i=0; i<tyoajat.length; i+=2) {
				if( !tyoajat[i].equals("") && !tyoajat[i+1].equals("") ) {
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// PERUSTUNNIT									 |
					// = = = = = = = = = = = = = = = = = = = = = = = =
					temp = tyoajat[i+1].replace(".",":").split(":");
					diff = s2d(temp[0])+(s2d(temp[1])/60.0);
	
					temp = tyoajat[i].replace(".",":").split(":");
					diff -= s2d(temp[0])+(s2d(temp[1])/60.0);
	
					if( !(Double.compare(diff, 0.00)<0) )
						d_kpls[0]+=diff;
					
					
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// ILTALISÄT									 |
					// = = = = = = = = = = = = = = = = = = = = = = = =
					if( !verotiedot[9].equals("") && !verotiedot[10].equals("") ) {
						diff = getComparedValue( s2d(tyoajat[i+1]), s2d(verotiedot[10]), true )
								- getComparedValue( s2d(tyoajat[i]), s2d(verotiedot[9]), false );
						
						if( !(Double.compare(diff, 0.00)<0) )
							d_kpls[1]+=diff;
					}
					
					
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// YÖLISÄT									 	 |
					// = = = = = = = = = = = = = = = = = = = = = = = =
					if( !verotiedot[12].equals("") && !verotiedot[13].equals("") ) {
						diff = getComparedValue( s2d(tyoajat[i+1]), s2d(verotiedot[13]), true )
								- getComparedValue( s2d(tyoajat[i]), s2d(verotiedot[12]), false );
						
						if( !(Double.compare(diff, 0.00)<0) )
							d_kpls[2]+=diff;
					}
					
					
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// LAUANTAILISÄT								 |
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// CHECK IF COMPARED FIELD IS LAUANTAI
					if( ((i-20)>=0 && (i-20)%28==0) || ((i-22)>=0 && (i-22)%28==0) ) {
						if( !verotiedot[15].equals("") && !verotiedot[16].equals("") ) {
							diff = getComparedValue( s2d(tyoajat[i+1]), s2d(verotiedot[16]), true )
									- getComparedValue( s2d(tyoajat[i]), s2d(verotiedot[15]), false );
							
							if( !(Double.compare(diff, 0.00)<0) )
								d_kpls[3]+=diff;
					}	}
					
					
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// SUNNUNTAILISÄT								 |
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// CHECK IF COMPARED FIELD IS SUNNUNTAI
					if( ((i-24)>=0 && (i-24)%28==0) || ((i-26)>=0 && (i-26)%28==0) ) {
						if( !verotiedot[18].equals("") && !verotiedot[19].equals("") ) {
							diff = getComparedValue( s2d(tyoajat[i+1]), s2d(verotiedot[19]), true )
									- getComparedValue( s2d(tyoajat[i]), s2d(verotiedot[18]), false );
							
							if( !(Double.compare(diff, 0.00)<0) )
								d_kpls[4]+=diff;
					}	}
					
					
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// OMA LISÄT									 |
					// = = = = = = = = = = = = = = = = = = = = = = = =
					// CHECK IF NOT PERCENT AND AIKA HAS VALUE
					if( verotiedot[31].equals("false") && !verotiedot[28].equals("") 
													   && !verotiedot[29].equals("") ) {
						
						// GO THROUGH MA-SU CHECKBOXES
						for(int j=0; j<7; j++) {
							// CHECK IF DAY IS CHECKED IN CHECKBOX
							if( verotiedot[j+21].equals("true") ) {
								//CHECK IF IT IS THE DAY
								if( ((i-(j*4))>=0 && (i-(j*4))%28==0) || 
								    ((i-((j*4)+2))>=0 && (i-((j*4)+2))%28==0) ) {
										diff = getComparedValue( s2d(tyoajat[i+1]), s2d(verotiedot[29]), true )
											 - getComparedValue( s2d(tyoajat[i]), s2d(verotiedot[28]), false );
									
									if( !(Double.compare(diff, 0.00)<0) )
										d_kpls[5]+=diff;
					}	}	}	}
				}
			}
			// = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			// CALCULATE A-HINTA									 |
			// = = = = = = = = = = = = = = = = = = = = = = = = = = = =
			for(int i=0; i<6; i++)
				d_ahinta[i]=0;
			
			for(int i=0; i<5; i++) {
				if( !verotiedot[(i*3)+8].equals("") )
					d_ahinta[i] = s2d(verotiedot[(i*3)+8]);
			}
			
			if( !verotiedot[30].equals("") )
				d_ahinta[5] = s2d(verotiedot[30]);

		
		} catch (Exception e) {  }
		
		
		updateLabels();
		
	}//...calculate()
	
	
	/**
	 * Updates JLabels to match the calculation.
	 */
	private void updateLabels() {
		double temp, yhteensa;
		
		// PERUS JA LISÄT
		for(int i=0; i<5; i++) {
			l_kpls[i].setText( String.format("%.2f", d_kpls[i]) );
			
			l_ahinta[i].setText( String.format("%.2f", d_ahinta[i]) );
			
			l_summa[i].setText( String.format("%.2f", (d_kpls[i]*d_ahinta[i]) ) );
		}
		
		// OMA LISÄ	
		if( veropalkkausTab.getValues()[31].equals("true") ) {
			l_kpls[5].setText( " " );
			l_ahinta[5].setText( String.format("%.2f", d_ahinta[5])+"%" );
			
			temp = 0;
			for(int i=0; i<5; i++)
				temp += d_kpls[i]*d_ahinta[i];
			
			l_summa[5].setText( String.format("%.2f", (temp*d_ahinta[5])/100 ) );
		}
		else {
			l_kpls[5].setText( String.format("%.2f", d_kpls[5]) );
			l_ahinta[5].setText( String.format("%.2f", d_ahinta[5]) );
			l_summa[5].setText( String.format("%.2f", (d_kpls[5]*d_ahinta[5]) ) );
		}
		
		// YHTEENSÄ
		yhteensa=0;
		for(int i=0; i<6; i++)
			yhteensa += s2d(l_summa[i].getText());
		
		l_muut[0].setText( String.format("%.2f", yhteensa ) );
		
		// AY-MAKSU
		temp = 0;
		if( !veropalkkausTab.getValues()[6].equals("") )
			temp += ( yhteensa*s2d(veropalkkausTab.getValues()[6]) ) / 100;
		if( !veropalkkausTab.getValues()[7].equals("") )
			temp += s2d(veropalkkausTab.getValues()[7]);
		
		l_muut[1].setText( String.format("%.2f", -1*temp ) );
		
		
		//TYÖTTÖMYYSVAKUUTUS
		temp = 0;
		if( !veropalkkausTab.getValues()[5].equals("") )
			temp += ( yhteensa*s2d(veropalkkausTab.getValues()[5]) ) / 100;
		l_muut[2].setText( String.format("%.2f", -1*temp ) );
		
		//TYÖELÄKEMAKSU
		temp = 0;
		if( !veropalkkausTab.getValues()[4].equals("") )
			temp += ( yhteensa*s2d(veropalkkausTab.getValues()[4]) ) / 100;
		l_muut[3].setText( String.format("%.2f", -1*temp ) );
		
		
		//VEROT
		double ansaittu=0;
		double tuloraja=0;
		double perusVero = 0;
		double lisaVero = 0;
		
		// GET ANSAITTU
		if(!veropalkkausTab.getValues()[0].equals(""))
			ansaittu = s2d(veropalkkausTab.getValues()[0]);
		
		// GET TULORAJA
		if(!veropalkkausTab.getValues()[0].equals(""))
			tuloraja = s2d(veropalkkausTab.getValues()[1]);
		
		// GET PERUSVERO
		if(!veropalkkausTab.getValues()[2].equals(""))
			perusVero = s2d(veropalkkausTab.getValues()[2]) / 100;
		
		// GET LISAVERO
		if(!veropalkkausTab.getValues()[3].equals(""))
			lisaVero = s2d(veropalkkausTab.getValues()[3]) / 100;
		
		
		// QOL: IF ANSAITTU & TULORAJA == 0.00 THEN IGNORE LISÄPROSENTTI
		if( Double.compare(ansaittu, 0.00)==0 && Double.compare(tuloraja, 0.00)==0 ){
			l_muut[4].setText( String.format("%.2f", -1*(yhteensa*perusVero) ) );
		
		// IF BRUTTOPALKKA+ANSAITTU IS SMALLER THAN TULORAJA
		}else if( Double.compare(yhteensa+ansaittu, tuloraja) <= 0 ){
			l_muut[4].setText( String.format("%.2f", -1*(yhteensa*perusVero) ) );
		
		}else{
			double perus =	tuloraja-ansaittu;
			double lisa = yhteensa - perus;
			
			perus *= perusVero;
			lisa *= lisaVero;
			
			l_muut[4].setText( String.format("%.2f", -1*(perus+lisa) ) );
		}
		
		
		//YHTEENSÄ2
		temp = 0;
		for(int i=1; i<5; i++)
			temp -= s2d( l_muut[i].getText() );
		
		l_muut[5].setText( String.format("%.2f", temp ) );
		tf[3].setText( String.format("%.2f", temp )+" €" );
		if( Double.compare(temp, 0.00)>0 )
			tf[3].setForeground( new Color(0,150,0) );
		else if( Double.compare(temp, 0.00)<0 )
			tf[3].setForeground( new Color(150,0,0) );
		else
			tf[3].setForeground( new Color(0,0,0) );
		
		
		//MAKSETAAN
		temp = s2d( l_muut[0].getText() ) - s2d( l_muut[5].getText() );
		l_muut[6].setText( String.format("%.2f", temp ) );
		tf[0].setText( String.format("%.2f", temp )+" €" );
		if( Double.compare(temp, 0.00)>0 )
			tf[0].setForeground( new Color(0,150,0) );
		else if( Double.compare(temp, 0.00)<0 )
			tf[0].setForeground( new Color(150,0,0) );
		else
			tf[0].setForeground( new Color(0,0,0) );
			
		
		//REST OF THE TEXTFIELDS
		//PERUSTUNNIT
		tf[1].setText( String.format("%.2f", (d_kpls[0]*d_ahinta[0]) )+" €" );
		
		//LISÄT
		temp=0;
		for(int i=1; i<6; i++)
			temp += s2d(l_summa[i].getText());
		tf[2].setText( String.format("%.2f", temp )+" €" );
		
		
	}//...updateLabels()
	
	
	/**
	 * Compares two double values and returns either
	 * @param value1 - double
	 * @param value2 - double
	 * @param lessThan - boolean
	 * @return Returns the value which is smallest if lessThan is true. 
	 * If lessThan is false, returns the value which is largest.
	 */
	private double getComparedValue(double value1, double value2, boolean lessThan) {
		if(lessThan) {
			if( value1 < value2 )
				return value1;
			else
				return value2;
		
		}else{
			if( value1 > value2 )
				return value1;
			else
				return value2;
		}
	}//...getComparedValue(double, double, boolean)
	
	
	/**
	 * Makes String convertible to double and returns the double value of it.
	 * @param str - String that is converted
	 * @return double value of str
	 */
	private static double s2d(String str) {
		String str2 = "";
		str = str.replace(",", ".").replace(":", ".");
		
		for(int i=0; i<str.length(); i++) {
			if( str.charAt(i)=='.' || str.charAt(i)==',' || str.charAt(i)==':' ) {
				str2 += ".";
				
			} else if( Character.isDigit(str.charAt(i)) ) {
				str2 += str.charAt(i); 
			}
		}
		
		return Double.parseDouble(str2);
	}//...s2d(String)
	
	
	/**
	 * Creates JLabel for text
	 * @param text
	 * @param bolded - true if text is bolded
	 * @return JLabel
	 */
	private JLabel createText(String text, boolean bolded) {
		Font font;
		
		if(bolded)
			font = new Font("Times New Roman",Font.BOLD, TEXT_SIZE);
		else
			font = new Font("Times New Roman",Font.PLAIN, TEXT_SIZE);
		
		JLabel label = new JLabel(text);
		label.setFont( font );
		
		return label;
	}//...createText(String,boolean)
	
	
	/**
	 * Creates JLabel for title
	 * @param text - title
	 * @param underlined - true if title is underlined
	 * @return JLabel
	 */
	private JLabel createTitle(String text, boolean underlined) {
		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Font font = new Font(FONT_NAME,Font.BOLD, TITLE_SIZE);
		
		JLabel label = new JLabel(text);
		if(underlined)
			label.setFont( font.deriveFont(fontAttributes) );
		else
			label.setFont( font );
		
		return label;
	}//...createTitle(text,boolean)
	
	
	/**
	 * Crates JLabels for this tab and sets values to zero
	 */
	private void init() {
		Font f = new Font(FONT_NAME,Font.PLAIN, TEXT_SIZE);
		Font f2 = new Font(FONT_NAME,Font.BOLD, TEXT_SIZE);
		Font f3 = new Font(FONT_NAME,Font.BOLD, TEXT_SIZE+3);
		
		for(int i=0; i<6; i++) {
			l_kpls[i] = new JLabel( String.format("%.2f", d_kpls[i]) );
			l_kpls[i].setFont(f);
			
			l_ahinta[i] = new JLabel( String.format("%.2f", d_ahinta[i]) );
			l_ahinta[i].setFont(f);
			
			l_summa[i] = new JLabel( String.format("%.2f", (d_kpls[i]*d_ahinta[i]) ) );
			l_summa[i].setFont(f);
			
			l_muut[i] = new JLabel( "0.00" );
			l_muut[i].setFont(f);
			
		}
		
		l_muut[6] = new JLabel( "0.00" );
		
		l_muut[0].setFont(f2);
		l_muut[5].setFont(f2);
		l_muut[6].setFont(f2);
		
		
		
		for(int i=0; i<4; i++) {
			tf[i] = new JFormattedTextField(currencyFormat);
			tf[i].setEditable(false);
			tf[i].setFont(f3);
			tf[i].setForeground(new Color(0,0,0));
			tf[i].setBorder( new LineBorder(Color.black, 2) );
			tf[i].setHorizontalAlignment(JTextField.CENTER);
			tf[i].setValue(new Double(0.00));
		}
			
	}//...init()
	
	
	/**
	 * Empties double valued JLabels.
	 */
	public void resetLabels() {
		for(int i=0; i<6; i++) {
			l_kpls[i].setText(" ");
			l_ahinta[i].setText(" ");
			l_summa[i].setText(" ");
			l_muut[i].setText(" ");
		}
		l_muut[6].setText(" ");
		
		for(int i=0; i<4; i++) {
			tf[i].setText(" ");
		}
		
	}//...resetLabels()
	
	
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

}
