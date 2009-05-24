/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.psu.sweng500.carpool.device;

import edu.psu.sweng500.carpool.device.services.generated.SecurityService_Stub;
import java.rmi.RemoteException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author EBorisow
 */
public class CarPoolMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command cmdOK;
    private Command cmdShowRecords;
    private Form frm_Login;
    private StringItem strTitle;
    private TextField txtUserName;
    private TextField txtPassword;
    private Spacer spacer;
    private Spacer spacer1;
    private Form frm_Main_Menu;
    private StringItem strTitle1;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The LoginScreen constructor.
     */
    public CarPoolMIDlet() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getFrm_Login());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdOK ">//GEN-BEGIN:|25-getter|0|25-preInit
    /**
     * Returns an initiliazed instance of cmdOK component.
     * @return the initialized component instance
     */
    public Command getCmdOK() {
        if (cmdOK == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
            cmdOK = new Command("Ok", Command.OK, 0);//GEN-LINE:|25-getter|1|25-postInit
            // write post-init user code here
        }//GEN-BEGIN:|25-getter|2|
        return cmdOK;
    }
    //</editor-fold>//GEN-END:|25-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == frm_Login) {//GEN-BEGIN:|7-commandAction|1|26-preAction
            if (command == cmdOK) {//GEN-END:|7-commandAction|1|26-preAction
                // write pre-action user code here
                SecurityService_Stub securityService = new SecurityService_Stub();
                String userName = txtUserName.getString();
                String password = txtPassword.getString();
                try
                {
                    Boolean isLoggedIn = securityService.login(userName, password);
                    if (!isLoggedIn.booleanValue())
                    {
                        return;
                    }

                    // store the current user name
                    storeUserName(userName);
                }
                catch (RemoteException rme)
                {
                    rme.printStackTrace();
                }
                switchDisplayable(null, getFrm_Main_Menu());//GEN-LINE:|7-commandAction|2|26-postAction
                // write post-action user code here
            } else if (command == cmdShowRecords) {//GEN-LINE:|7-commandAction|3|39-preAction
                // write pre-action user code here
//GEN-LINE:|7-commandAction|4|39-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|7-postCommandAction
        }//GEN-END:|7-commandAction|5|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|6|
    //</editor-fold>//GEN-END:|7-commandAction|6|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frm_Login ">//GEN-BEGIN:|14-getter|0|14-preInit
    /**
     * Returns an initiliazed instance of frm_Login component.
     * @return the initialized component instance
     */
    public Form getFrm_Login() {
        if (frm_Login == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            frm_Login = new Form("Login Form", new Item[] { getStrTitle(), getSpacer(), getTxtUserName(), getSpacer1(), getTxtPassword() });//GEN-BEGIN:|14-getter|1|14-postInit
            frm_Login.addCommand(getCmdOK());
            frm_Login.addCommand(getCmdShowRecords());
            frm_Login.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
        }//GEN-BEGIN:|14-getter|2|
        return frm_Login;
    }
    //</editor-fold>//GEN-END:|14-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: strTitle ">//GEN-BEGIN:|20-getter|0|20-preInit
    /**
     * Returns an initiliazed instance of strTitle component.
     * @return the initialized component instance
     */
    public StringItem getStrTitle() {
        if (strTitle == null) {//GEN-END:|20-getter|0|20-preInit
            // write pre-init user code here
            strTitle = new StringItem("", "Please login", Item.PLAIN);//GEN-LINE:|20-getter|1|20-postInit
            // write post-init user code here
        }//GEN-BEGIN:|20-getter|2|
        return strTitle;
    }
    //</editor-fold>//GEN-END:|20-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtUserName ">//GEN-BEGIN:|21-getter|0|21-preInit
    /**
     * Returns an initiliazed instance of txtUserName component.
     * @return the initialized component instance
     */
    public TextField getTxtUserName() {
        if (txtUserName == null) {//GEN-END:|21-getter|0|21-preInit
            // write pre-init user code here
            txtUserName = new TextField("User Name:", "", 32, TextField.ANY);//GEN-LINE:|21-getter|1|21-postInit
            // write post-init user code here
        }//GEN-BEGIN:|21-getter|2|
        return txtUserName;
    }
    //</editor-fold>//GEN-END:|21-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtPassword ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of txtPassword component.
     * @return the initialized component instance
     */
    public TextField getTxtPassword() {
        if (txtPassword == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            txtPassword = new TextField("Password:", "", 32, TextField.ANY | TextField.PASSWORD);//GEN-LINE:|22-getter|1|22-postInit
            // write post-init user code here
        }//GEN-BEGIN:|22-getter|2|
        return txtPassword;
    }
    //</editor-fold>//GEN-END:|22-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer ">//GEN-BEGIN:|23-getter|0|23-preInit
    /**
     * Returns an initiliazed instance of spacer component.
     * @return the initialized component instance
     */
    public Spacer getSpacer() {
        if (spacer == null) {//GEN-END:|23-getter|0|23-preInit
            // write pre-init user code here
            spacer = new Spacer(16, 30);//GEN-BEGIN:|23-getter|1|23-postInit
            spacer.setPreferredSize(-1, 30);//GEN-END:|23-getter|1|23-postInit
            // write post-init user code here
        }//GEN-BEGIN:|23-getter|2|
        return spacer;
    }
    //</editor-fold>//GEN-END:|23-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer1 ">//GEN-BEGIN:|24-getter|0|24-preInit
    /**
     * Returns an initiliazed instance of spacer1 component.
     * @return the initialized component instance
     */
    public Spacer getSpacer1() {
        if (spacer1 == null) {//GEN-END:|24-getter|0|24-preInit
            // write pre-init user code here
            spacer1 = new Spacer(16, 30);//GEN-BEGIN:|24-getter|1|24-postInit
            spacer1.setPreferredSize(-1, 30);//GEN-END:|24-getter|1|24-postInit
            // write post-init user code here
        }//GEN-BEGIN:|24-getter|2|
        return spacer1;
    }
    //</editor-fold>//GEN-END:|24-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frm_Main_Menu ">//GEN-BEGIN:|30-getter|0|30-preInit
    /**
     * Returns an initiliazed instance of frm_Main_Menu component.
     * @return the initialized component instance
     */
    public Form getFrm_Main_Menu() {
        if (frm_Main_Menu == null) {//GEN-END:|30-getter|0|30-preInit
            // write pre-init user code here
            frm_Main_Menu = new Form("Main Menu", new Item[] { getStrTitle1() });//GEN-LINE:|30-getter|1|30-postInit
            // write post-init user code here
        }//GEN-BEGIN:|30-getter|2|
        return frm_Main_Menu;
    }
    //</editor-fold>//GEN-END:|30-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: strTitle1 ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of strTitle1 component.
     * @return the initialized component instance
     */
    public StringItem getStrTitle1() {
        if (strTitle1 == null) {//GEN-END:|31-getter|0|31-preInit
            // write pre-init user code here
            strTitle1 = new StringItem("Please select an item from the following:", getUserName());//GEN-LINE:|31-getter|1|31-postInit
            // write post-init user code here
        }//GEN-BEGIN:|31-getter|2|
        return strTitle1;
    }
    //</editor-fold>//GEN-END:|31-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cmdShowRecords ">//GEN-BEGIN:|38-getter|0|38-preInit
    /**
     * Returns an initiliazed instance of cmdShowRecords component.
     * @return the initialized component instance
     */
    public Command getCmdShowRecords() {
        if (cmdShowRecords == null) {//GEN-END:|38-getter|0|38-preInit
            // write pre-init user code here
            cmdShowRecords = new Command("Show Records", Command.SCREEN, 0);//GEN-LINE:|38-getter|1|38-postInit
            // write post-init user code here
        }//GEN-BEGIN:|38-getter|2|
        return cmdShowRecords;
    }
    //</editor-fold>//GEN-END:|38-getter|2|









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
            RecordStore.deleteRecordStore("sweng500_carpool");

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
