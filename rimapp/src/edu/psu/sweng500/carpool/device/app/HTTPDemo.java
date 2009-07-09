package edu.psu.sweng500.carpool.device.app;

/**
 * A simple example using the HTTP connection
 * Copyright ¬ 1998-2007 Research In Motion Ltd.
 */

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.i18n.*;
import net.rim.device.api.system.*;
import javax.microedition.io.*;
import java.io.*;

public class HTTPDemo extends UiApplication
{
    //constants ----------------------------------------------------------------
    private static String SAMPLE_HTTPS_PAGE = "https://www.blackberry.com/go/mobile/samplehttps.shtml";
    private static final String[] HTTP_PROTOCOL = {"http://", "http:\\", "https://", "https:\\"};
    private static final char HTML_TAG_OPEN = '<';
    private static final char HTML_TAG_CLOSE = '>';
    private static String HEADER_CONTENTTYPE = "content-type";
    private static String CONTENTTYPE_TEXTHTML = "text/html";

    private static final int STATE_0 = 0;
    private static final int STATE_1 = 1;
    private static final int STATE_2 = 2;
    private static final int STATE_3 = 3;
    private static final int STATE_4 = 4;
    private static final int STATE_5 = 5;

    private static final char CR = 0x000D;
    private static final char LF = 0x000A;

    //members ------------------------------------------------------------------
    private HTTPDemoScreen _mainScreen;
    
    private RichTextField _content;
    private boolean _useWapStack;
    
    
    //cache the clear content menu item for reuse
    private MenuItem _clearContent = new MenuItem("Clear content", 105, 10) {
        public void run()
        {
            _content.setText("Content Default");
        }
    };

    StatusThread _statusThread = new StatusThread();
    ConnectionThread _connectionThread = new ConnectionThread();

    
    public static void main(String[] args)
    {
        HTTPDemo theApp = new HTTPDemo();
        theApp.enterEventDispatcher();
    }

    //innerclasses -------------------------------------------------------------
    /**
     * The ConnectionThread class manages the HTTP connection
     * Fetch operations are not queued, however, if a fetch call
     * is made and, while active, another request made,
     * the 2nd request will stall until the previous request completes.
     */
    private class ConnectionThread extends Thread
    {
        private static final int TIMEOUT = 500; //ms

        private String _theUrl;

        private volatile boolean _start = false;
        private volatile boolean _stop = false;

        //retrieve the URL
        public synchronized String getUrl()
        {
            return _theUrl;
        }

        //fetch a page
        // synchronized so that i don't miss requests
        public void fetch(String url)
        {
            if ( _start )
            {
                Dialog.alert("Request in progress");
            }
            else
            {
                synchronized(this)
                {
                    if ( _start )
                    {
                        Dialog.alert("Request in progress");
                    }
                    else
                    {
                        _start = true;
                        _theUrl = url;
                    }
                }
            }
        }

        //shutdown the thread
        public void stop()
        {
            _stop = true;
        }

        public void run()
        {
            for(;;)
            {
                //Thread control
                while( !_start && !_stop)
                {
                    //sleep for a bit so we don't spin
                    try {
                        sleep(TIMEOUT);
                    } catch (InterruptedException e) {
                        System.err.println(e.toString());
                    }
                }
                //exit condition
                if ( _stop )
                {
                    return;
                }
                //This entire block is synchronized, this ensures I won't miss fetch requests made while i process a page
                synchronized(this)
                {
                    //open the connection and extract the data
                    StreamConnection s = null;
                    try {
                        s = (StreamConnection)Connector.open(getUrl());
                        HttpConnection httpConn = (HttpConnection)s;

                        int status = httpConn.getResponseCode();
                        if (status == HttpConnection.HTTP_OK)
                        {
                            //is this html?
                            String ctype = httpConn.getHeaderField(HEADER_CONTENTTYPE);
                            boolean htmlContent = (ctype != null && ctype.equals(CONTENTTYPE_TEXTHTML));

                            InputStream input = s.openInputStream();

                            byte[] data = new byte[256];
                            int len = 0;
                            int size = 0;
                            StringBuffer raw = new StringBuffer();
                            while ( -1 != (len = input.read(data)) )
                            {
                                raw.append(new String(data, 0, len));
                                size += len;
                            }
                            
                            raw.insert(0, "bytes received]\n");
                            raw.insert(0, size);
                            raw.insert(0, '[');
                            String content = raw.toString();
                            if ( htmlContent )
                            {
                                content = prepareData(raw.toString());
                            }
                            //The long operation is the parsing above,
                            // after the parsing is complete, shutdown the status thread
                            // before setting the text (since both threads modify the content
                            // pane, we want to make sure we don't have the status thread
                            // overwriting our data)
                            stopStatusThread();
                            updateContent(content);
                            input.close();
                        } else {
                            stopStatusThread();
                            updateContent("response code = " + status);
                        }
                        s.close();

                    } catch (IOException e) {
                        System.err.println(e.toString());
                        stopStatusThread();
                        updateContent(e.toString());
                    }
                    //we're done one connection so reset the start state
                    _start = false;
                }
            }
        }

        private void stopStatusThread()
        {
            _statusThread.pause();
            try {
                synchronized(_statusThread)
                {
                    //Check the paused condition, incase the notify fires prior to our wait, in which case we may never see that nofity
                    while ( !_statusThread.isPaused() );
                    {
                        _statusThread.wait();
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
    }

    /**
     * The StatusThread class manages display of the status message while lengthy HTTP/HTML operations are taking place
     */
    private class StatusThread extends Thread
    {
        private static final int INTERVAL = 6;
        private static final int TIMEOUT = 500; //ms
        private static final int THREAD_TIMEOUT = 500;

        private volatile boolean _stop = false;
        private volatile boolean _running = false;
        private volatile boolean _isPaused = false;

        //resume the thread
        public void go()
        {
            _running = true;
            _isPaused = false;
        }

        //pause the thread
        public void pause()
        {
            _running = false;
        }

        //query the paused status
        public boolean isPaused()
        {
            return _isPaused;
        }

        //shutdown the thread
        public void stop()
        {
            _stop = true;
        }

        public void run()
        {
            int i = 0;
            //setup the status messages
            String[] statusMsg = new String[6];
            StringBuffer status = new StringBuffer("Status message");
            statusMsg[0] = status.toString();
            //preincrement improves efficiency
            for ( int j = 1; j < 6; ++j)
            {
                statusMsg[j] = status.append("Status message append").toString();
            }

            for (;;)
            {
                while (!_stop && !_running)
                {
                    //sleep a bit so we don't spin
                    try {
                        sleep(THREAD_TIMEOUT);
                    } catch ( InterruptedException e) {
                        System.err.println(e.toString());
                    }

                }
                if ( _stop )
                {
                    return;
                }
                i = 0;
                status.delete(0, status.length()); //clear the status buffer
                for ( ;; )
                {
                    //we're not synchronizing on the boolean flag! value is declared volatile therefore
                    if ( _stop )
                    {
                        return;
                    }
                    if ( !_running )
                    {
                        _isPaused = true;
                        synchronized(this)
                        {
                            this.notify();
                        }
                        break;
                    }

                    updateContent(statusMsg[++i%6]);

                    try {
                        this.sleep(TIMEOUT); //wait for a bit
                    } catch (InterruptedException e) {
                        System.err.println(e.toString());
                    }
                }
            }
        }
    }
    
    
        

    //constructors -------------------------------------------------------------
    public HTTPDemo()
    {
        
    }

    //methods ------------------------------------------------------------------
    

    private void fetchPage(String url)
    {
        //First, normalize the url
        String lcase = url.toLowerCase();
        boolean validHeader = false;
        int i = 0;

        // Winding down and comparing to 0 saves instructions.
        for (i = HTTP_PROTOCOL.length - 1; i >= 0; --i)
        {
            if ( -1 != lcase.indexOf(HTTP_PROTOCOL[i]) )
            {
                validHeader = true;
                break;
            }
        }
        if ( !validHeader )
        {
            url = HTTP_PROTOCOL[0] + url; //prepend the protocol specifier
        }

        /*
         * It is illegal to open a connection on the event thread, due to the system architecture, therefore,
         * spawn a new thread for connection operations
         */
        _connectionThread.fetch(url);

        //create a thread for showing status of the operation
        _statusThread.go();

    }

    private void updateContent(final String text)
    {
        //This will create significant garbage, but avoids threading issues
        // (compared with creating a static Runnable and setting the text)
        UiApplication.getUiApplication().invokeLater(new Runnable() {
            public void run()
            {
                _content.setText(text);
            }
        });
    }

    private String prepareData(String text)
    {
        //do some simple operations on the html data

        //a simple state machine for removing tags, comments, whitespace and inserting newlines
        // for the <p> tag.

        final int text_length = text.length();
        StringBuffer data = new StringBuffer(text_length);
        int state = STATE_0;
        int count = 0;
        int writeIndex = -1;
        char c = (char)0;
        //cache the text length and preincrement the counter for
        for ( int i = 0; i < text_length; ++i)
        {
            c = text.charAt(i);
            switch ( state )
            {
                case STATE_0:
                    if ( c == HTML_TAG_OPEN )
                    {
                        ++count;
                        state = STATE_1;
                    }
                    else if ( c == ' ' )
                    {
                        data.insert(++writeIndex, c);
                        state = STATE_5;
                    }
                    else if ( !specialChar(c) )
                    {
                        data.insert(++writeIndex, c);
                    }
                    break;
                case STATE_1:
                    if ( c == '!' && text.charAt(i + 1) == '-' && text.charAt(i + 2) == '-' )
                    {
                        System.out.println("Entering Comment state");
                        i += 2;
                        state = STATE_3;
                    }
                    else if ( Character.toLowerCase(c) == 'p' )
                    {
                        state = STATE_4;
                    }
                    else if ( c == HTML_TAG_CLOSE )
                    {
                        --count;
                        state = STATE_0;
                    }
                    else
                    {
                        state = STATE_2;
                    }
                    break;
                case STATE_2:
                    if ( c == HTML_TAG_OPEN )
                    {
                        ++count;
                    }
                    else if ( c == HTML_TAG_CLOSE )
                    {
                        if( --count == 0 )
                        {
                            state = STATE_0;
                        }
                    }
                    break;
                case STATE_3:
                    if ( c == '-' && text.charAt(i+1) == '-' && text.charAt(i + 2) == HTML_TAG_CLOSE )
                    {
                        --count;
                        i += 2;
                        state = STATE_0;
                        System.out.println("Exiting comment state");
                    }
                    break;
                case STATE_4:
                    if ( c == HTML_TAG_CLOSE )
                    {
                        --count;
                        data.insert(++writeIndex, '\n');
                        state = STATE_0;
                    }
                    else
                    {
                        state = STATE_1;
                    }
                    break;
                case STATE_5:
                    if ( c == HTML_TAG_OPEN )
                    {
                        ++count;
                        state = STATE_1;
                    }
                    else if ( c != ' ' )
                    {
                        state = STATE_0;
                        if ( !specialChar(c) )
                        {
                            data.insert(++writeIndex, c);
                        }
                    }
                    break;
            }
        }
        return data.toString().substring(0, writeIndex + 1);
    }

    private boolean specialChar(char c)
    {
        return c == LF || c == CR;
    }

    protected void onExit()
    {
        _statusThread.stop();
        _connectionThread.stop();
    }
}
