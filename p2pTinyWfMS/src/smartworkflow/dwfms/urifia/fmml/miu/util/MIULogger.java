/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.joda.time.DateTime;

/**
 *
 * @author Ndadji Maxime
 */
public class MIULogger {
    public static final String  
            INFO = "INFO",
            WARNING = "WARNING",
            EXCEPTION = "EXCEPTION",
            ERROR = "ERROR",
            DEMO_INFO = ">>>DEMO CREATION<<<";
    
    private File logFile;

    public MIULogger() {
        logFile = new File("ressources/logs/journal.miu.log");
    }
    
    /*
     * Méthode pas performante du tout
     */
    public void log(final String type, final String entry){
        Thread t = new Thread(){

            @Override
            public void run() {
                writeOnLog(type, entry);
            }
            
        };
        t.start();
    }
    
    public boolean isDemoEnded(){
        try {
            synchronized(this){
                if(!logFile.getParentFile().exists())
                    logFile.getParentFile().mkdirs();
                if(!logFile.exists()){
                    logFile.createNewFile();
                    log(INFO, "******************************************************");
                    log(INFO, "New log file created");
                    log(INFO, "******************************************************");
                    return false;
                }
                FileReader fileReader = new FileReader(logFile);   
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                try{
	                String line = new String();
	                while((line = bufferedReader.readLine()) != null){
	                    if(line.contains(DEMO_INFO))
	                        return true;
	                }
                } catch (Exception ex) {

                } finally{
                	bufferedReader.close();
                }
            }
        } catch (Exception ex) {

        }
        return false;
    }

    private void writeOnLog(String type, String entry) {
        synchronized(this){
            try {
                if(!logFile.getParentFile().exists())
                    logFile.getParentFile().mkdirs();
                if(!logFile.exists()){
                    logFile.createNewFile();
                    log(INFO, "******************************************************");
                    log(INFO, "New log file created");
                    log(INFO, "******************************************************");
                }
                String logLine = DateTime.now().toString() + " --- " + type.toUpperCase() + " >>> " + entry;
                BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));
                out.write(logLine + "\n");
                out.close();
            } catch (Exception ex) {

            }
        }
    }
}
