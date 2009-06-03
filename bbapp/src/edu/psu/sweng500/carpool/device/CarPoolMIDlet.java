/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package device;

import edu.psu.sweng500.carpool.device.services.generated.SecurityService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.SimpleMessage;
import java.rmi.RemoteException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author EBorisow
 */
public class CarPoolMIDlet extends MIDlet implements CommandListener, ItemCommandListener {

    private boolean midletPaused = false;
    private String logMessage;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">                      
    private Form frm_Login;
    private TextField txtUserName;
    private StringItem strTitle;
    private Spacer spacer;
    private TextField txtPassword;
    private Spacer spacer1;
    private Form frm_Main_Menu;
    private StringItem strTitle1;
    private TextField textField;
    private TextField textField1;
    private Command cmdOK;
    private Command cmdShowRecords;
    private Command screenCommand;
    private Command cmdShowLog;
    private Command cmdBack;
    private Command itemCommand;
    private Command screenCommand1;
    private Command cmdOK1;
    private Command cmdOKMainMenu;
    private Command cmdBackMainMenu;
    //</editor-fold>                    

    /**
     * The LoginScreen constructor.
     */
    public CarPoolMIDlet() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">                       
    //</editor-fold>                     

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">                                           
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {                                         
        // write pre-initialize user code here
                                           
        // write post-initialize user code here
    }                            
    //</editor-fold>                          

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">                                        
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {                                      
        // write pre-action user code here
        switchDisplayable(null, getFrm_Login());                                        
        // write post-action user code here
    }                             
    //</editor-fold>                           

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">                                         
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {                                       
        // write pre-action user code here
                                         
        // write post-action user code here
    }                              
    //</editor-fold>                            

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">                                              
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {                                            
        // write pre-switch user code here
        Display display = getDisplay();                                               
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }                                             
        // write post-switch user code here
    }                                   
    //</editor-fold>                                 

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdOK ">                                   
    /**
     * Returns an initiliazed instance of cmdOK component.
     * @return the initialized component instance
     */
    public Command getCmdOK() {
        if (cmdOK == null) {                                 
            // write pre-init user code here
            cmdOK = new Command("Ok", Command.OK, 0);                                   
            // write post-init user code here
        }                         
        return cmdOK;
    }
    //</editor-fold>                       



    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">                                                 
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {                                               
        // write pre-action user code here
        if (displayable == frm_Login) {                                           
            if (command == cmdOK) {                                         
                // write pre-action user code here
                SecurityService_Stub securityService = new SecurityService_Stub();
                String userName = txtUserName.getString();
                String password = txtPassword.getString();
                try
                {
                    SimpleMessage message = securityService.login(userName, password);
                    logMessage = ("The result is: " + message.getIsSuccess());
                    if (!message.getIsSuccess().booleanValue())
                    {
                        return;
                    }

                    // store the current user name
                    storeUserName(userName);
                }
                catch (RemoteException rme)
                {
                    rme.printStackTrace();
                    logMessage = "A remote exception occurred: " + rme.getMessage();

                    return;
                }

                switchDisplayable(null, getFrm_Main_Menu());                                           
                // write post-action user code here
            } else if (command == cmdShowLog) {                                          
                // write pre-action user code here
                                           
                Alert alert = new Alert("Log", logMessage, null, AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                alert.addCommand(cmdBack);
                Display display = getDisplay();
                display.setCurrent(alert);
            // write post-action user code here
            }                                           
        } else if (displayable == frm_Main_Menu) {
            if (command == cmdBackMainMenu) {                                         
                // write pre-action user code here
                                           
                // write post-action user code here
            } else if (command == cmdOKMainMenu) {                                          
                // write pre-action user code here
                                           
                // write post-action user code here
            }                                                  
        }                                                
        if (command == cmdShowLog)
        {
        }
        // write post-action user code here
    }                                
    //</editor-fold>                              





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frm_Login ">                                   
    /**
     * Returns an initiliazed instance of frm_Login component.
     * @return the initialized component instance
     */
    public Form getFrm_Login() {
        if (frm_Login == null) {                                 
            // write pre-init user code here
            frm_Login = new Form("Login Form", new Item[] { getStrTitle(), getSpacer(), getTxtUserName(), getSpacer1(), getTxtPassword() });                                    
            frm_Login.addCommand(getCmdOK());
            frm_Login.addCommand(getCmdShowLog());
            frm_Login.setCommandListener(this);                                  
            this.getDisplay().setCurrentItem(txtUserName);
            // write post-init user code here
        }                         
        return frm_Login;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: strTitle ">                                   
    /**
     * Returns an initiliazed instance of strTitle component.
     * @return the initialized component instance
     */
    public StringItem getStrTitle() {
        if (strTitle == null) {                                 
            // write pre-init user code here
            strTitle = new StringItem("Please login", "", Item.PLAIN);                                   
            // write post-init user code here
        }                         
        return strTitle;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtUserName ">                                   
    /**
     * Returns an initiliazed instance of txtUserName component.
     * @return the initialized component instance
     */
    public TextField getTxtUserName() {
        if (txtUserName == null) {                                 
            // write pre-init user code here
            txtUserName = new TextField("User Name:", "", 32, TextField.ANY);                                    
            txtUserName.addCommand(getCmdShowRecords());
            txtUserName.setItemCommandListener(this);                                  
            // write post-init user code here
        }                         
        return txtUserName;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtPassword ">                                   
    /**
     * Returns an initiliazed instance of txtPassword component.
     * @return the initialized component instance
     */
    public TextField getTxtPassword() {
        if (txtPassword == null) {                                 
            // write pre-init user code here
            txtPassword = new TextField("Password:", "", 32, TextField.ANY | TextField.PASSWORD);                                   
            // write post-init user code here
        }                         
        return txtPassword;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer ">                                   
    /**
     * Returns an initiliazed instance of spacer component.
     * @return the initialized component instance
     */
    public Spacer getSpacer() {
        if (spacer == null) {                                 
            // write pre-init user code here
            spacer = new Spacer(16, 30);                                    
            spacer.setPreferredSize(-1, 30);                                  
            // write post-init user code here
        }                         
        return spacer;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer1 ">                                   
    /**
     * Returns an initiliazed instance of spacer1 component.
     * @return the initialized component instance
     */
    public Spacer getSpacer1() {
        if (spacer1 == null) {                                 
            // write pre-init user code here
            spacer1 = new Spacer(16, 30);                                    
            spacer1.setPreferredSize(-1, 30);                                  
            // write post-init user code here
        }                         
        return spacer1;
    }
    //</editor-fold>                       



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frm_Main_Menu ">                                   
    /**
     * Returns an initiliazed instance of frm_Main_Menu component.
     * @return the initialized component instance
     */
    public Form getFrm_Main_Menu() {
        if (frm_Main_Menu == null) {                                 
            // write pre-init user code here
            frm_Main_Menu = new Form("Main Menu", new Item[] { getStrTitle1(), getTextField(), getTextField1() });                                    
            frm_Main_Menu.addCommand(getCmdOKMainMenu());
            frm_Main_Menu.addCommand(getCmdBackMainMenu());
            frm_Main_Menu.setCommandListener(this);                                  

            // write post-init user code here
        }                         
        return frm_Main_Menu;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: strTitle1 ">                                   
    /**
     * Returns an initiliazed instance of strTitle1 component.
     * @return the initialized component instance
     */
    public StringItem getStrTitle1() {
        if (strTitle1 == null) {                                 
            // write pre-init user code here
            strTitle1 = new StringItem("Please select an item from the following:", getUserName());                                   
            // write post-init user code here
        }                         
        return strTitle1;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdShowRecords ">                                   
    /**
     * Returns an initiliazed instance of cmdShowRecords component.
     * @return the initialized component instance
     */
    public Command getCmdShowRecords() {
        if (cmdShowRecords == null) {                                 
            // write pre-init user code here
            cmdShowRecords = new Command("Show Records", Command.SCREEN, 0);                                   
            // write post-init user code here
        }                         
        return cmdShowRecords;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Items ">                                                         
    /**
     * Called by a system to indicated that a command has been invoked on a particular item.
     * @param command the Command that was invoked
     * @param displayable the Item where the command was invoked
     */
    public void commandAction(Command command, Item item) {                                                       
        // write pre-action user code here
        if (item == txtUserName) {                                               
            if (command == cmdShowRecords) {                                             
                // write pre-action user code here
                                               
                // write post-action user code here
            }                                                          
        }                                                        
        // write post-action user code here
    }                                   
    //</editor-fold>                                 

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdShowLog ">                                   
    /**
     * Returns an initiliazed instance of cmdShowLog component.
     * @return the initialized component instance
     */
    public Command getCmdShowLog() {
        if (cmdShowLog == null) {                                 
            // write pre-init user code here
            cmdShowLog = new Command("Show Log", Command.SCREEN, 0);                                   
            // write post-init user code here
        }                         
        return cmdShowLog;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: screenCommand ">                                   
    /**
     * Returns an initiliazed instance of screenCommand component.
     * @return the initialized component instance
     */
    public Command getScreenCommand() {
        if (screenCommand == null) {                                 
            // write pre-init user code here
            screenCommand = new Command("Screen", Command.SCREEN, 0);                                   
            // write post-init user code here
        }                         
        return screenCommand;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand ">                                   
    /**
     * Returns an initiliazed instance of itemCommand component.
     * @return the initialized component instance
     */
    public Command getItemCommand() {
        if (itemCommand == null) {                                 
            // write pre-init user code here
            itemCommand = new Command("Item", Command.ITEM, 0);                                   
            // write post-init user code here
        }                         
        return itemCommand;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdBack ">                                   
    /**
     * Returns an initiliazed instance of cmdBack component.
     * @return the initialized component instance
     */
    public Command getCmdBack() {
        if (cmdBack == null) {                                 
            // write pre-init user code here
            cmdBack = new Command("Back", Command.BACK, 0);                                   
            // write post-init user code here
        }                         
        return cmdBack;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: screenCommand1 ">                                   
    /**
     * Returns an initiliazed instance of screenCommand1 component.
     * @return the initialized component instance
     */
    public Command getScreenCommand1() {
        if (screenCommand1 == null) {                                 
            // write pre-init user code here
            screenCommand1 = new Command("Screen", Command.SCREEN, 0);                                   
            // write post-init user code here
        }                         
        return screenCommand1;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField ">                                   
    /**
     * Returns an initiliazed instance of textField component.
     * @return the initialized component instance
     */
    public TextField getTextField() {
        if (textField == null) {                                 
            // write pre-init user code here
            textField = new TextField("txtViewCarpools", "View Planned Trips", 32, TextField.ANY);                                   
            // write post-init user code here
        }                         
        return textField;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField1 ">                                   
    /**
     * Returns an initiliazed instance of textField1 component.
     * @return the initialized component instance
     */
    public TextField getTextField1() {
        if (textField1 == null) {                                 
            // write pre-init user code here
            textField1 = new TextField("txtCreateCarpool", "Create a Carpool", 32, TextField.ANY);                                   
            // write post-init user code here
        }                         
        return textField1;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdOK1 ">                                   
    /**
     * Returns an initiliazed instance of cmdOK1 component.
     * @return the initialized component instance
     */
    public Command getCmdOK1() {
        if (cmdOK1 == null) {                                 
            // write pre-init user code here
            cmdOK1 = new Command("Ok", Command.OK, 0);                                   
            // write post-init user code here
        }                         
        return cmdOK1;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdOKMainMenu ">                                   
    /**
     * Returns an initiliazed instance of cmdOKMainMenu component.
     * @return the initialized component instance
     */
    public Command getCmdOKMainMenu() {
        if (cmdOKMainMenu == null) {                                 
            // write pre-init user code here
            cmdOKMainMenu = new Command("Ok", Command.OK, 0);                                   
            // write post-init user code here
        }                         
        return cmdOKMainMenu;
    }
    //</editor-fold>                       

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdBackMainMenu ">                                   
    /**
     * Returns an initiliazed instance of cmdBackMainMenu component.
     * @return the initialized component instance
     */
    public Command getCmdBackMainMenu() {
        if (cmdBackMainMenu == null) {                                 
            // write pre-init user code here
            cmdBackMainMenu = new Command("Back", Command.BACK, 0);                                   
            // write post-init user code here
        }                         
        return cmdBackMainMenu;
    }
    //</editor-fold>                       









    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    private void storeUserName(String userName)
    {
        RecordStore rs= null;
        try {
            rs = getRecordStore(true);
            if (rs != null)
            {
                RecordStore.deleteRecordStore("sweng500_carpool");
            }

            rs = getRecordStore(true);

            int recordId = rs.addRecord(userName.getBytes(), 0, userName.getBytes().length );
            System.out.println("The recordId is: " + recordId);
        } catch( RecordStoreException e ){
            e.printStackTrace();
        }
        finally
        {
            try
            {
                rs.closeRecordStore();
            }
            catch(RecordStoreException rse)
            {}
        }
    }

    private String getUserName()
    {
        RecordStore rs = null;
        try {
            rs = getRecordStore(false);
            byte[] data = rs.getRecord(1);
            return new String(data);
        } catch( RecordStoreException e ){
            e.printStackTrace();
        }
        finally
        {
            try
            {
                rs.closeRecordStore();
            }
            catch(RecordStoreException rse)
            {}
        }
        return "";
    }

    private RecordStore getRecordStore(boolean createIfNecessary)
    {
        RecordStore rs = null;

        try
        {
            rs = RecordStore.openRecordStore( "sweng500_carpool", createIfNecessary );
        }
        catch( RecordStoreException e )
        {
            e.printStackTrace();
        }
        return rs;
    }
}
