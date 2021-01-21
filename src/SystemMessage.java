import java.awt.Component;

import javax.swing.JOptionPane;

/**
* <h1>Notifications & Messages</h1>
* A simpleton class responsible of showing error and other messages in the software.
* <p>
* @author  Toni "tontsakaze" Torvela
* @version 1.0
* @since   2020-10-18
*/
public final class SystemMessage extends JOptionPane {
	private static final SystemMessage INSTANCE = new SystemMessage();
	
	private boolean errorMsgON = true;
	private boolean plainMsgON = true;
	
	/**
	 * Constructor
	 */
	private SystemMessage() { super(); }
	
	
	/**
	 * Gets instance of this class
	 * @return SystemMessage instance
	 */
	public static SystemMessage getInstance() {
		return INSTANCE;
	}
	
	
	/**
	 * Sets state of all messages
	 * @param on - boolean
	 */
	public void setMessagesState(boolean on) {
		errorMsgON = on;
		plainMsgON = on;
	}
	
	
	/**
	 * Sets state for error messages
	 * @param on - boolean
	 */
	public void setErrorMessagesState(boolean on) {
		errorMsgON = on;
	}
	
	
	/**
	 * Set state for other messages
	 * @param on - boolean
	 */
	public void setPlainMessagesState(boolean on) {
		plainMsgON = on;
	}
	
	
	/**
	 * Creates a new error message that is shown to the user
	 * @param cmp - Component in which the message is shown
	 * @param message - Error message
	 * @param title - Title of message
	 */
	public void newErrorMessage(Component cmp, String message, String title) {
		if(errorMsgON) {
			showMessageDialog(	cmp,
								message, 
								title, 
								JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * Creates a new plain message that is shown to the user
	 * @param cmp - Component in which the message is shown
	 * @param message - Message
	 * @param title - Title of message
	 */
	public void newPlainMessage(Component cmp, String message, String title) {
		if(plainMsgON) {
			showMessageDialog(	cmp,
								message, 
								title, 
								JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	
}
