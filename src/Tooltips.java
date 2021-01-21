import java.util.HashMap;

/**
* <h1>Tooltips for software's components</h1>
* A simpleton class that has all the tooltips for software's components.
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-14
*/
public final class Tooltips {
	private static final Tooltips INSTANCE = new Tooltips();
	private HashMap<String, String> tooltips;
	
	/**
	 * Gets instance of this class
	 * @return Tooltips -instance
	 */
	public static Tooltips getInstance() {
		return INSTANCE;
	}
	
	
	/**
	 * Gets tooltip for given key
	 * @param key - String key
	 * @return String - tooltip
	 */
	public String get(String key) {
		if(tooltips.containsKey(key))
			return tooltips.get(key);
		
		else
			return "ERROR: TOOLTIP NOT FOUND!";
	}
	
	
	/**
	 * Constructor
	 */
	private Tooltips() {
		tooltips = new HashMap<String, String>();
		
		tooltips.put("TAB.HELP", 				"Tietoa, apua ja asetuksia");
		tooltips.put("TAB.VEROPALKKAUS", 		"Vero- ja palkkatietojen täyttäminen");
		tooltips.put("TAB.TYOAJAT", 			"Työaikojen täyttäminen");
		tooltips.put("TAB.LASKE", 				"Laskelman tekeminen");
		
		
		
		tooltips.put("TYOAJAT.BUTTONCLEAR", 	"Tyhjentää ylläolevat kentät");
		
		tooltips.put("TYOAJAT.BEGINTIME", 		"Työajan alkamisaika (00:00-23:59)");
		tooltips.put("TYOAJAT.ENDTIME", 		"Työajan päättymisaika (00:01-24:00)");
		
		tooltips.put("TYOAJAT.ERROR_NOTNUMBER", "Syötetty arvo sisältää virheellisiä merkkejä");
		tooltips.put("TYOAJAT.ERROR_BEGIN", 	"Syötetty arvo tulee olla väliltä 0:00-23:59");
		tooltips.put("TYOAJAT.ERROR_END", 		"Syötetty arvo tulee olla väliltä 0:01-24:00");
		tooltips.put("TYOAJAT.ERROR_ENDLOWERTHANBEGIN", "Päättymisajan tulee olla myöhäisempi kuin alkamisajan");
		tooltips.put("TYOAJAT.ERROR_BEGINGREATERTHANEND", "Alkamisajan tulee olla aikaisempi kuin päättymisajan");
		tooltips.put("TYOAJAT.ERROR_NEG", 		"Syötetty arvo ei saa olla negatiivinen");
		tooltips.put("TYOAJAT.ERROR_MINUTES", 	"Minuuttien arvo tulee olla väliltä 0-59");
		
		
		
		tooltips.put("VEROP.ANSAITTU", 			"Bruttona ansaittu palkka ennen " +
						 						"verovähennyksiä ja muita lakisääteisiä maksuja.");
		
		tooltips.put("VEROP.TULORAJA", 			"Raja (bruttopalkasta), josta vähennetään Perusvero. "+ 
					 	 						"Rajan ylittävästä summasta peritään Lisävero.");
		
		tooltips.put("VEROP.PERUSVERO",			"Ansaitusta bruttopalkasta vähennettävä vero-osuus " + 
						 						"(ennen tulorajan ylitystä).");
		
		tooltips.put("VEROP.LISAVERO", 			"Tulorajan ylittävästä bruttopalkasta vähennettävä vero-osuus.");
		
		tooltips.put("VEROP.ELAKEMAKSU",		"Ansaitusta bruttopalkasta vähennettävä maksu (pakollinen). " + 
						   						"Vaihtelee työntekijän iän mukaan.");
		
		tooltips.put("VEROP.TYOTTOMYYSMAKSU", 	"Ansaitusta bruttopalkasta vähennettävä maksu (pakollinen).");
		
		tooltips.put("VEROP.AYMAKSUE", 			"Ansaitusta bruttopalkasta maksettava euromääräinen " + 
						 						"ammattiliiton jäsenmaksu (vapaaehtoinen).");
		
		tooltips.put("VEROP.TESSET", 			"Lataa valitun työehtosopimuksen tiedot täytettäviin kenttiin.");
		tooltips.put("VEROP.TESSIT", 			"Ladattavat työehtosopimukset.");
		
		tooltips.put("VEROP.AYMAKSUP", 			"Ansaitusta bruttopalkasta maksettava prosentuaalinen " + 
						 						"ammattiliiton jäsenmaksu (vapaaehtoinen).");
		
		tooltips.put("VEROP.TUNTIPALKKA", 		"Euromääräinen korvaus tehdystä työtunnista.");
		
		tooltips.put("VEROP.ILTALISAB", 		"Aika, josta alkaen iltalisää maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.ILTALISAE", 		"Aika, johon asti iltalisää maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.ILTALISA", 			"Euromääräinen korvaus iltalisäajalla tehdystä työtunnista.");
		
		tooltips.put("VEROP.YOLISAB", 			"Aika, josta alkaen yölisää maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.YOLISAE", 			"Aika, johon asti yölisää maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.YOLISA", 			"Euromääräinen korvaus yölisäajalla tehdystä työtunnista.");
		
		tooltips.put("VEROP.LALISAB", 			"Aika, josta alkaen lisää maksetaan lauantaisin. (00:00-23:59)");
		tooltips.put("VEROP.LALISAE", 			"Aika, johon asti lisää maksetaan lauantaisin. (00:00-23:59)");
		tooltips.put("VEROP.LALISA", 			"Euromääräinen korvaus em. lisäajalla tehdystä työtunnista lauantaina.");
		
		tooltips.put("VEROP.SULISAB", 			"Aika, josta alkaen lisää maksetaan sunnuntaisin. (00:00-23:59)");
		tooltips.put("VEROP.SULISAE", 			"Aika, johon asti lisää maksetaan sunnuntaisin. (00:00-23:59)");
		tooltips.put("VEROP.SULISA", 			"Euromääräinen korvaus em. lisäajalla tehdystä työtunnista sunnuntaina.");
		
		tooltips.put("VEROP.OMALISAPAIVAT", 	"Valitse ne päivät, joilta omaa lisää maksetaan.");
		tooltips.put("VEROP.OMALISAB", 			"Aika, josta alkaen lisää maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.OMALISAE", 			"Aika, johon asti lisää maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.OMALISA", 			"Euromääräinen korvaus em. lisäajalla tehdystä työtunnista.");
		tooltips.put("VEROP.OMALISAPROSENTTI", 	"Määrä = Prosenttimääräinen korvaus bruttopalkasta.");
		
		tooltips.put("VEROP.BUTTONCLEAR", 		"Nollaa kaikki täytettävät kentät.");
		
		tooltips.put("VEROP.BUTTONLOAD", 		"Lataa tiedostosta viimeksi tallennetut " +
						   						"tiedot täytettäviin kenttiin.");
		
		tooltips.put("VEROP.BUTTONSAVE", 		"Tallentaa tiedostoon täytettyjen kenttien tiedot.");
		tooltips.put("VEROP.BUTTONDEFAULT", 	"Lataa tiedostosta oletusasetukset.");
		
		tooltips.put("VEROP.ERROR_NEGVALUE", 	"Syötetty arvo ei saa olla negatiivinen");
		tooltips.put("VEROP.ERROR_OVER100PERCENT", "Syötetty arvo voi olla korkeintaan 100.00%");
		
		tooltips.put("VEROP.ERROR_NOTNUMBER", 	"Syötetty arvo sisältää virheellisiä merkkejä");
		tooltips.put("VEROP.ERROR_BEGIN", 		"Syötetty arvo tulee olla väliltä 0:00-23:59");
		tooltips.put("VEROP.ERROR_END", 		"Syötetty arvo tulee olla väliltä 0:01-24:00");
		tooltips.put("VEROP.ERROR_ENDLOWERTHANBEGIN", "Päättymisajan tulee olla myöhäisempi kuin alkamisajan");
		tooltips.put("VEROP.ERROR_BEGINGREATERTHANEND", "Alkamisajan tulee olla aikaisempi kuin päättymisajan");
		tooltips.put("VEROP.ERROR_MINUTES", 	"Minuuttien arvo tulee olla väliltä 0-59");
		
		tooltips.put("VEROP.NOTIFY_DEFAULT", 	"Oletusarvot ladattu!");
		tooltips.put("VEROP.NOTIFY_SAVE", 		"Arvot tallennettiin!");
		tooltips.put("VEROP.NOTIFY_LOAD", 		"Tallennetut arvot ladattu!");
		tooltips.put("VEROP.NOTIFY_TESSIT", 	"Työehtosopimuksen arvot ladattu!");
		
		
		
		tooltips.put("LASKE.MAKSETAANFIELD", 	"Nettopalkka eli summa, joka maksetaan kaikkien vähennysten jälkeen");
		tooltips.put("LASKE.PERUSTUNNITFIELD", 	"Summa, joka tulee pelkästään tuntipalkasta");
		tooltips.put("LASKE.LISATFIELD", 		"Summa, joka tulee pelkästään lisistä");
		tooltips.put("LASKE.MAKSUTFIELD", 		"Yhteissumma kaikista bruttopalkasta maksettavista vähennyksistä");
		
		tooltips.put("LASKE.BUTTONCALC", 		"Tekee palkkalaskelman annettujen tietojen perusteella.");
		tooltips.put("LASKE.BUTTONSAVE", 		"Tallentaa laskelman tiedostoon.");
		
		
		tooltips.put("HELP.CHECKBOXERROR", 		"Näytä virheilmoitus, kun syötät virheellisen arvon.");
		tooltips.put("HELP.CHECKBOXMESSAGE", 	"Näytä muut ilmoitukset, kuten vahvistusilmoitukset.");
		
	}
}
