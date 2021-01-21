import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

/**
* <h1>File Handler</h1>
* A class that handles everything associated with files
* in the program likes TESSIT-files and saved DAT-files
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-07
*/
public final class FileHandler {
	private static final FileHandler 		INSTANCE = new FileHandler();
	private static HashMap<String, File> 	fileMap = new HashMap<String, File>();
	private File[] 							listOfFiles;
	private static String 					dataSep = "!";
	private static SystemMessage			sysMsg = SystemMessage.getInstance();
	
	// CONSTRUCTOR
	/**
	 * Constructor
	 * 
	 * @param directory - Directory where files are located
	 */
	private FileHandler() {
		listOfFiles = new File("tes").listFiles();
	}
	
	public static FileHandler getInstance() {
		return INSTANCE;
	}
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	FUNCTIONS FOR HANDLING TES |
// - - - - - - - - - - - - - - -
	/**
	 * Fills given JComboBox with TESSIT-files found from directory
	 * 
	 * @param tessit - JComboBox that is filled
	 */
	public void fillTESBox(JComboBox<String> tessit) {
		for(int i=0; i < listOfFiles.length; i++) {
			if ( isAcceptableFile( listOfFiles[i]) ){
				tessit.addItem( getEditedFilename(listOfFiles[i]) );
				fileMap.put( getEditedFilename(listOfFiles[i]), listOfFiles[i] );
	}	}	}
	
	
	/**
	 * Checks if file's extension is acceptable for the program
	 * 
	 * @param f - File that is checked
	 * @return True if file is TESSIT-file
	 */
    private boolean isAcceptableFile(File f) {
    	if( f.getName().length() < 7 ) {
    		return false;
    	}
    	
    	String extension = f.getName().substring( f.getName().length()-7, f.getName().length() );
    	if ( !extension.equals(".tessit") ) {
    		return false;
    	}
    	
    	return true;
    }
    
    
    /**
     * Extracts title from the TESSIT-file
     * 
     * @param f - File TESSIT-file including a title
     * @return String - Title
     */
    private String getEditedFilename(File f) {
    	String name = "$Corrupted$";
    	String s = "$Corrupted$";
    	
    	try {
    		BufferedReader br = new BufferedReader( new FileReader(f) );
    		s = br.readLine();
    		br.close();
    	
    	}catch (IOException e) {
    		e.printStackTrace();
    	}
    	

    	if ( s.substring(0,6).equals("title"+dataSep) ) {
    		name = s.substring(6,s.length());
    		
    	}
    	
    	return name;
    	
    }//...fillTESBox(JComboBox<String>)
    
    
    /**
     * Fills given FormattedTextField array with file's data
     * using key to get the correct data
     * 
     * @param tf_lisat - JFormattedTextField array that is filled
     * @param key - String that is used to load data from hashmap
     */
    public static void loadTessitData(JFormattedTextField[] tf_lisat, String key) {
    	if ( fileMap.containsKey(key) ) {
    		File f = fileMap.get(key);
    		String[] keys = {"palkk"+dataSep, "iltib"+dataSep, "iltie"+dataSep, 
    						 "illis"+dataSep, "yotib"+dataSep, "yotie"+dataSep, 
    						 "yolis"+dataSep, "latib"+dataSep, "latie"+dataSep, 
    						 "lalis"+dataSep, "sutib"+dataSep, "sutie"+dataSep, 
    						 "sulis"+dataSep};
    		
    		try {
        		BufferedReader br = new BufferedReader( new FileReader(f) );
        		String str = br.readLine();
        		
        		for(int i=0; i<keys.length; i++) {
        			str = br.readLine();
        			if ( keys[i].equals( str.substring(0,6) ) ) {
        				int z = Integer.parseInt(str.substring(6,8));
        				int x = Integer.parseInt(str.substring(8,10));
        				int y = Integer.parseInt(str.substring(10,12));
        				
        				if ( i%3 == 0 ) {
        					str = Integer.toString(z) + Integer.toString(x) + "." + Integer.toString(y);
        					tf_lisat[i].setValue( Double.parseDouble(str) );
        				
        				} else {
        					tf_lisat[i].setValue( str.substring(8,10)+":"+str.substring(10,12) );
        				}
        				
        			} else {
        				sysMsg.newErrorMessage(	null,
        										"Työehtosopimuksen lukeminen ei onnistunut", 
												"VIRHE: Verotiedot");
                		break; 
        			}
        			
        		}
        		
        		br.close();
        	
        	}catch (Exception e) {
        		sysMsg.newErrorMessage(	null,
        								"Työehtosopimuksen lukeminen ei onnistunut", 
    									"VIRHE: Verotiedot");
        		e.printStackTrace();
        	}
    		
    		
    	}
    }//...loadTessitData(JFormattedTextField[], String)
    
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// 	FUNCTIONS FOR SETTINGS   |
// - - - - - - - - - - - - - -
    /**
     * Saves data from filled TextFields and CheckBoxes to DAT-file
     * 
     * @param tf_verotiedot - JFormattedTextField array
     * @param tf_lisat - JFormattedTextField array
     * @param cb_omalisa - JCheckBox array
     * @param tf_omalisa - JFormattedTextField array
     */
    public static void saveSettings( JFormattedTextField[] tf_verotiedot, 
			 				  JFormattedTextField[] tf_lisat,
	 				  		  JCheckBox[] cb_omalisa,
	 				  		  JFormattedTextField[] tf_omalisa) {
    	
    	// CREATE A STRING FROM VALUES THAT IS SAVED TO THE FILE
    	String str = "";
    	
    	try {
	    	// VEROTIEDOT
	    	for(int i=0; i<8; i++)
	    		str = str + tf_verotiedot[i].getText() + dataSep;
	    	
	    	// LISÄT
	    	for(int i=0; i<13; i++) 
    			str = str + tf_lisat[i].getText() + dataSep;
	    	
	    	// OMALISÄ PÄIVÄT
	    	for(int i=0; i<7; i++) 
	    		str = str + Boolean.toString( cb_omalisa[i].isSelected() ) + dataSep;
	    	
	    	// OMALISÄ MUUT
	    	for(int i=0; i<3; i++) 
	    	str = str + tf_omalisa[i].getText() + dataSep;
	    	str = str + Boolean.toString( cb_omalisa[7].isSelected() );
	    	
	    	
	    	str = str.replace(',', '.');
	    	
    	} catch (Exception e) {
    		sysMsg.newErrorMessage(	null,
    								"Tietojen tallentaminen ei onnistunut" , 
									"VIRHE: Verotiedot");
    	}
    	
    	
    	// SAVE STRING TO A FILE
    	BufferedWriter bw;
    	
		try {
			bw = new BufferedWriter( new FileWriter("cfg\\saved.dat"));
			bw.write(str);
			bw.close();
			
		} catch (IOException e) {
			sysMsg.newErrorMessage(	null,
									"Tietojen tallentaminen ei onnistunut" , 
									"VIRHE: Verotiedot");
		}

    }//...saveSettings( JFormattedTextField[], JFormattedTextField[], JCheckBox[], JFormattedTextField[])
    
    
    /**
     * Loads data from DAT-file to TextFields and CheckBocxes
     * 
     * @param tf_verotiedot - JFormattedTextField array
     * @param tf_lisat - JFormattedTextField array
     * @param cb_omalisa - JCheckBox array
     * @param tf_omalisa - JFormattedTextField array
     * @param defaultFile - True if data is loaded from default.DAT file
     */
    public static void loadSettings( JFormattedTextField[] tf_verotiedot, 
    								 JFormattedTextField[] tf_lisat,
    								 JCheckBox[] cb_omalisa,
    								 JFormattedTextField[] tf_omalisa,
    								 boolean defaultFile) {
    	
    	// READ FILE TO A STRING
    	BufferedReader br;
    	String str = "cfg\\saved.dat";
    	
    	if (defaultFile)
    		str = "cfg\\default.dat";
    	
		try {
			br = new BufferedReader( new FileReader(str));
			str = br.readLine();
			br.close();
			
		
			// LOAD VALUES FROM STRING
			String[] values = str.split(dataSep);
			int idx = 0;
			
			
			// VEROTIEDOT
			for(int i=0; i<8; i++) {
				tf_verotiedot[i].setValue( Double.parseDouble( values[idx] ) );
				idx++;
			}
			
			// LISÄT
			for(int i=0; i<13; i++) {
	    		if ( i%3==0 )
	    			tf_lisat[i].setValue( Double.parseDouble( values[idx] ) );
	    		
	    		else 
	    			tf_lisat[i].setValue( values[idx] );
	    		
	    		
	    		idx++;
	    	}
			
			// OMALISÄ PÄIVÄT
			for(int i=0; i<7; i++) {
				cb_omalisa[i].setSelected( Boolean.parseBoolean( values[idx] ) );
				idx++;
			}
			
			// OMALISÄ MUUT
			tf_omalisa[0].setValue( values[idx] );
			idx++;
			
			tf_omalisa[0].setValue( values[idx] );
			idx++;
			
			tf_omalisa[2].setValue( Double.parseDouble( values[idx] ) );
			cb_omalisa[7].setSelected( Boolean.parseBoolean( values[idx+1] ) );
		
		
		} catch (Exception e) {
			sysMsg.newErrorMessage(	null,
									"Tietojen lataaminen ei onnistunut", 
									"VIRHE: Verotiedot");
			e.printStackTrace();
		}

    }//...loadSettings( JFormattedTextField[], JFormattedTextField[],JCheckBox[], JFormattedTextField[], boolean)
 
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// 	FUNCTIONS FOR CALCULATIONS   |
// - - - - - - - - - - - - - - - -
    /**
     * Saves payslip / calculation for wage to a file.
     * File is named dd-MM-yyyy_HHmm.txt and is saved
     * to a folder named calculations.
     * @param kpl - String array of KPLs
     * @param ahinta - String Array of A-Hintas
     * @param summa - String Array of Summas
     */
    public void saveCalculations(String[] kpl, String[] ahinta, String[] summa) {
    	final int strLength = 30;
    	String filename="palkkalaskelma_";
    	String temp = "";
    	String[] defs={	"Perustunnit",
    					"Iltalisä",
    					"Yölisä",
    					"Lauantailisä",
    					"Sunnuntailisä",
    					"Oma lisä",
    					"YHTEENSÄ",
    					" ",
    					"Ay-Maksu",
    					"Työttömyysvakuutusmaksu",
    					"Työeläkemaksu",
    					"Ennakkopidätys",
    					"YHTEENSÄ",
    					" ",
    					"MAKSETAAN" };
    	
    	// GET TIMESTAMP FOR FILENAME
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy_HHmm");
    	filename += dateformat.format(cal.getTime());
    	
    	
    	// SAVE STRING TO A FILE
    	BufferedWriter bw;
    	
		try {
			bw = new BufferedWriter( new FileWriter("calculations\\"+filename+".txt"));
			bw.write("");
			
			//TITLES
			temp = makeStringThisLong("SELITE",strLength,' ');
			temp+= makeStringThisLong("KPL",strLength/2,' ');
			temp+= makeStringThisLong("A-HINTA",strLength/2,' ');
			temp+= makeStringThisLong("SUMMA (€)",strLength,' ');
			bw.append(temp+"\n");
			
			temp = makeStringThisLong("",strLength*3,'-');
			bw.append(temp+"\n");
			
			for(int i=0; i< defs.length; i++) {
				temp = makeStringThisLong(defs[i],strLength,' ');
				temp+= makeStringThisLong(kpl[i],strLength/2,' ');
				temp+= makeStringThisLong(ahinta[i],strLength/2,' ');
				temp+= makeStringThisLong(summa[i],strLength,' ');
				bw.append(temp+"\n");
			}
			
			bw.close();
			sysMsg.newPlainMessage(	null,
									"Laskelma tallennettu tiedostoon\n\""+filename+".txt\"", 
									"Laske");
			
		} catch (IOException e) {
			sysMsg.newErrorMessage(	null,
									"Tietojen tallentaminen ei onnistunut" , 
									"VIRHE: Laske");
		}
		
    }//...saveCalculations()
    
    
    /**
     * Appends a given string to a given length with given character.
     * @param oldString - String that is appended
     * @param length - Integer how long the string will be
     * @param padding - Character that is used to append
     * @return String - A new appended string
     */
    private String makeStringThisLong(String oldString, int length, Character padding) {
    	String newString = "";
    	
    	if(oldString.length()>length) {
    		newString = oldString.substring(0, length);
    	
    	}else {
    		newString = new String(oldString);
    		for( int i=newString.length(); i<=length; i++ )
    			newString += padding;
    	}
    	
    	
    	return newString;
    	
    }//...String makeStringThisLong(String, int)
    
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//  	FUNCTIONS FOR HELPTAB  |
// - - - - - - - - - - - - - - -
    /**
     * Loads pre-written files that are used for the Help-tab
     * @return Array of String array that contains all loaded lines from file
     */
    public String[][] getHelpTexts(){
    	String[][] 		strs = new String[3][];
    	String[]		filenames= {"cfg\\helpTab01.txt",
    								"cfg\\helpTab02.txt",
    								"cfg\\helpTab03.txt"};
    	BufferedReader 	br;
    	final int		MAXSIZE=35;
    	int				linecount;
    	
    	// READ FILES TO A STRING ARRAY
		try {
			for(int i=0; i<3; i++) {
				linecount=0;
				strs[i] = new String[MAXSIZE];
				br = new BufferedReader(
				           new InputStreamReader(new FileInputStream(filenames[i]), "UTF-8"));
				for(int j=0; j<MAXSIZE; j++) {
					strs[i][j] = br.readLine();
					linecount++;
				}
				br.close();
				
				for(int j=linecount; j<MAXSIZE; j++)
					strs[i][j] = " ";
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	return strs;
    }//...String[] getHelpTexts()
    
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
// 	GENERAL FUNCTIONS  |
// - - - - - - - - - - -
    /**
     * Returns file corresponding to key
     * 
     * @param key - String that is used to get file from hashmap
     * @return File if key is found, otherwise null
     */
    public File getFile(String key) {
    	if( fileMap.containsKey(key) )
    		return fileMap.get(key);
    	
    	return null;
    }
// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    

}//...class FileHandler
