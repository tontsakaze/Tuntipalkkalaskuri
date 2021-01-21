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
		tooltips.put("TAB.VEROPALKKAUS", 		"Vero- ja palkkatietojen t�ytt�minen");
		tooltips.put("TAB.TYOAJAT", 			"Ty�aikojen t�ytt�minen");
		tooltips.put("TAB.LASKE", 				"Laskelman tekeminen");
		
		
		
		tooltips.put("TYOAJAT.BUTTONCLEAR", 	"Tyhjent�� yll�olevat kent�t");
		
		tooltips.put("TYOAJAT.BEGINTIME", 		"Ty�ajan alkamisaika (00:00-23:59)");
		tooltips.put("TYOAJAT.ENDTIME", 		"Ty�ajan p��ttymisaika (00:01-24:00)");
		
		tooltips.put("TYOAJAT.ERROR_NOTNUMBER", "Sy�tetty arvo sis�lt�� virheellisi� merkkej�");
		tooltips.put("TYOAJAT.ERROR_BEGIN", 	"Sy�tetty arvo tulee olla v�lilt� 0:00-23:59");
		tooltips.put("TYOAJAT.ERROR_END", 		"Sy�tetty arvo tulee olla v�lilt� 0:01-24:00");
		tooltips.put("TYOAJAT.ERROR_ENDLOWERTHANBEGIN", "P��ttymisajan tulee olla my�h�isempi kuin alkamisajan");
		tooltips.put("TYOAJAT.ERROR_BEGINGREATERTHANEND", "Alkamisajan tulee olla aikaisempi kuin p��ttymisajan");
		tooltips.put("TYOAJAT.ERROR_NEG", 		"Sy�tetty arvo ei saa olla negatiivinen");
		tooltips.put("TYOAJAT.ERROR_MINUTES", 	"Minuuttien arvo tulee olla v�lilt� 0-59");
		
		
		
		tooltips.put("VEROP.ANSAITTU", 			"Bruttona ansaittu palkka ennen " +
						 						"verov�hennyksi� ja muita lakis��teisi� maksuja.");
		
		tooltips.put("VEROP.TULORAJA", 			"Raja (bruttopalkasta), josta v�hennet��n Perusvero. "+ 
					 	 						"Rajan ylitt�v�st� summasta perit��n Lis�vero.");
		
		tooltips.put("VEROP.PERUSVERO",			"Ansaitusta bruttopalkasta v�hennett�v� vero-osuus " + 
						 						"(ennen tulorajan ylityst�).");
		
		tooltips.put("VEROP.LISAVERO", 			"Tulorajan ylitt�v�st� bruttopalkasta v�hennett�v� vero-osuus.");
		
		tooltips.put("VEROP.ELAKEMAKSU",		"Ansaitusta bruttopalkasta v�hennett�v� maksu (pakollinen). " + 
						   						"Vaihtelee ty�ntekij�n i�n mukaan.");
		
		tooltips.put("VEROP.TYOTTOMYYSMAKSU", 	"Ansaitusta bruttopalkasta v�hennett�v� maksu (pakollinen).");
		
		tooltips.put("VEROP.AYMAKSUE", 			"Ansaitusta bruttopalkasta maksettava eurom��r�inen " + 
						 						"ammattiliiton j�senmaksu (vapaaehtoinen).");
		
		tooltips.put("VEROP.TESSET", 			"Lataa valitun ty�ehtosopimuksen tiedot t�ytett�viin kenttiin.");
		tooltips.put("VEROP.TESSIT", 			"Ladattavat ty�ehtosopimukset.");
		
		tooltips.put("VEROP.AYMAKSUP", 			"Ansaitusta bruttopalkasta maksettava prosentuaalinen " + 
						 						"ammattiliiton j�senmaksu (vapaaehtoinen).");
		
		tooltips.put("VEROP.TUNTIPALKKA", 		"Eurom��r�inen korvaus tehdyst� ty�tunnista.");
		
		tooltips.put("VEROP.ILTALISAB", 		"Aika, josta alkaen iltalis�� maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.ILTALISAE", 		"Aika, johon asti iltalis�� maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.ILTALISA", 			"Eurom��r�inen korvaus iltalis�ajalla tehdyst� ty�tunnista.");
		
		tooltips.put("VEROP.YOLISAB", 			"Aika, josta alkaen y�lis�� maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.YOLISAE", 			"Aika, johon asti y�lis�� maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.YOLISA", 			"Eurom��r�inen korvaus y�lis�ajalla tehdyst� ty�tunnista.");
		
		tooltips.put("VEROP.LALISAB", 			"Aika, josta alkaen lis�� maksetaan lauantaisin. (00:00-23:59)");
		tooltips.put("VEROP.LALISAE", 			"Aika, johon asti lis�� maksetaan lauantaisin. (00:00-23:59)");
		tooltips.put("VEROP.LALISA", 			"Eurom��r�inen korvaus em. lis�ajalla tehdyst� ty�tunnista lauantaina.");
		
		tooltips.put("VEROP.SULISAB", 			"Aika, josta alkaen lis�� maksetaan sunnuntaisin. (00:00-23:59)");
		tooltips.put("VEROP.SULISAE", 			"Aika, johon asti lis�� maksetaan sunnuntaisin. (00:00-23:59)");
		tooltips.put("VEROP.SULISA", 			"Eurom��r�inen korvaus em. lis�ajalla tehdyst� ty�tunnista sunnuntaina.");
		
		tooltips.put("VEROP.OMALISAPAIVAT", 	"Valitse ne p�iv�t, joilta omaa lis�� maksetaan.");
		tooltips.put("VEROP.OMALISAB", 			"Aika, josta alkaen lis�� maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.OMALISAE", 			"Aika, johon asti lis�� maksetaan. (00:00-23:59)");
		tooltips.put("VEROP.OMALISA", 			"Eurom��r�inen korvaus em. lis�ajalla tehdyst� ty�tunnista.");
		tooltips.put("VEROP.OMALISAPROSENTTI", 	"M��r� = Prosenttim��r�inen korvaus bruttopalkasta.");
		
		tooltips.put("VEROP.BUTTONCLEAR", 		"Nollaa kaikki t�ytett�v�t kent�t.");
		
		tooltips.put("VEROP.BUTTONLOAD", 		"Lataa tiedostosta viimeksi tallennetut " +
						   						"tiedot t�ytett�viin kenttiin.");
		
		tooltips.put("VEROP.BUTTONSAVE", 		"Tallentaa tiedostoon t�ytettyjen kenttien tiedot.");
		tooltips.put("VEROP.BUTTONDEFAULT", 	"Lataa tiedostosta oletusasetukset.");
		
		tooltips.put("VEROP.ERROR_NEGVALUE", 	"Sy�tetty arvo ei saa olla negatiivinen");
		tooltips.put("VEROP.ERROR_OVER100PERCENT", "Sy�tetty arvo voi olla korkeintaan 100.00%");
		
		tooltips.put("VEROP.ERROR_NOTNUMBER", 	"Sy�tetty arvo sis�lt�� virheellisi� merkkej�");
		tooltips.put("VEROP.ERROR_BEGIN", 		"Sy�tetty arvo tulee olla v�lilt� 0:00-23:59");
		tooltips.put("VEROP.ERROR_END", 		"Sy�tetty arvo tulee olla v�lilt� 0:01-24:00");
		tooltips.put("VEROP.ERROR_ENDLOWERTHANBEGIN", "P��ttymisajan tulee olla my�h�isempi kuin alkamisajan");
		tooltips.put("VEROP.ERROR_BEGINGREATERTHANEND", "Alkamisajan tulee olla aikaisempi kuin p��ttymisajan");
		tooltips.put("VEROP.ERROR_MINUTES", 	"Minuuttien arvo tulee olla v�lilt� 0-59");
		
		tooltips.put("VEROP.NOTIFY_DEFAULT", 	"Oletusarvot ladattu!");
		tooltips.put("VEROP.NOTIFY_SAVE", 		"Arvot tallennettiin!");
		tooltips.put("VEROP.NOTIFY_LOAD", 		"Tallennetut arvot ladattu!");
		tooltips.put("VEROP.NOTIFY_TESSIT", 	"Ty�ehtosopimuksen arvot ladattu!");
		
		
		
		tooltips.put("LASKE.MAKSETAANFIELD", 	"Nettopalkka eli summa, joka maksetaan kaikkien v�hennysten j�lkeen");
		tooltips.put("LASKE.PERUSTUNNITFIELD", 	"Summa, joka tulee pelk�st��n tuntipalkasta");
		tooltips.put("LASKE.LISATFIELD", 		"Summa, joka tulee pelk�st��n lisist�");
		tooltips.put("LASKE.MAKSUTFIELD", 		"Yhteissumma kaikista bruttopalkasta maksettavista v�hennyksist�");
		
		tooltips.put("LASKE.BUTTONCALC", 		"Tekee palkkalaskelman annettujen tietojen perusteella.");
		tooltips.put("LASKE.BUTTONSAVE", 		"Tallentaa laskelman tiedostoon.");
		
		
		tooltips.put("HELP.CHECKBOXERROR", 		"N�yt� virheilmoitus, kun sy�t�t virheellisen arvon.");
		tooltips.put("HELP.CHECKBOXMESSAGE", 	"N�yt� muut ilmoitukset, kuten vahvistusilmoitukset.");
		
	}
}
