import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
* <h1>Tabbed Panel</h1>
* Creates panel of tabs that can be added to JFrame.
* Includes 4 tabs.
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-07
*/
public class TabPanel extends JPanel implements ChangeListener {
	private JTabbedPane 		tabbedPane;
	private ImageIcon 			helpIcon = new ImageIcon("icons\\helpIcon.gif");
	
	private JComponent 			helpTab = new HelpTab();
	private VeroPalkkausTab 	veropalkkausTab = new VeroPalkkausTab();
	private TyoajatTab 			tyoajatTab = new TyoajatTab();
	private LaskeTab 			laskeTab = new LaskeTab(veropalkkausTab, tyoajatTab);
	
	private Tooltips 			tooltips = Tooltips.getInstance();


	/**
	 * Constructor
	 */
	public TabPanel() {
		super(new GridLayout(1, 1));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);
        
        
        // Help -tab
        helpTab.setPreferredSize( new Dimension(90, 90) );
        tabbedPane.addTab("", helpIcon, helpTab, tooltips.get("TAB.HELP"));
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        
        
        
        // VERO JA PALKKAUS -tab
        veropalkkausTab.setPreferredSize( new Dimension(360, 90) );
        tabbedPane.addTab(	"VERO JA PALKKAUS", 
        					null, 
        					veropalkkausTab,
        					tooltips.get("TAB.VEROPALKKAUS"));
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        // TYÖAJAT -tab
        tyoajatTab.setPreferredSize( new Dimension(240, 90) );
        tabbedPane.addTab(	"TYÖAJAT", 
        					null, 
    						tyoajatTab,
    						tooltips.get("TAB.TYOAJAT"));
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        // LASKE -tab
        laskeTab.setPreferredSize( new Dimension(500, 500) );
        tabbedPane.addTab(	"LASKE", 
        					null, 
    						laskeTab,
    						tooltips.get("TAB.LASKE"));
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        
        
        //Add the tabbed pane to this panel.
        add(tabbedPane);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        //Starting screen is VERO JA PALKKAUS -tab
        tabbedPane.setSelectedIndex(1);
        
	}//...public TabPanel()
	
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        //JLabel filler = new JLabel(text);
        JLabel filler = new JLabel("ASDASD");
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
        
    }//...JComponent makeTextPanel(String)


	// EVERYTIME TAB IS CHANGED, GET VALUES 
	// FROM OTHER TABS AND EDIT Laske -TAB
	public void stateChanged(ChangeEvent ce) {
		laskeTab.resetLabels();
	}
	
	
}//...class TabPanel

