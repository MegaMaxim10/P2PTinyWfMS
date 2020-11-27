package smartworkflow.dwfms.urifia.fmml.miu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;
import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.CryptographyException;
import smartworkflow.dwfms.urifia.fmml.miu.util.p2pworkflow.PeerToPeerWorkflow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * @author Ndadji Maxime
 * 
 * Voici l'objet de configuration. Celui-ci sera chargÈ de manipuler les fichiers de
 * configuration. Lorsqu'on voudra donc accÈder ‡ une configuration, c'est ‡ cet objet
 * qu'il faudra s'adresser.
 * 
 */

public class ConfigurationManager {
    private LanguageManager languageManager;
    private URL filePath;
    private String configurationFile = APPLConstants.CONFIG_FOLDER + APPLConstants.CONFIG_FILE_NAME;
    private Document document = null;
    private DOMParser parser = null;
    private Theme theme;
    private HashMap<String, String> user;
    
    public ConfigurationManager() throws ApplException{
        updateConfigurationFiles();
        loadConfigurationFile();
        languageManager = new LanguageManager(this);
        try{
            theme = new Theme(this);
        }catch(Exception e){
            theme = new Theme();
        }
    }
    
    public final void loadConfigurationFile() throws ApplException{
        try{
            parser = new DOMParser();
            parser.parse(filePath.toExternalForm().replace("%20", " "));
            document = parser.getDocument();
        }catch(Exception ex){
            filePath = getClass().getResource(APPLConstants.RESOURCES_FOLDER
        		+ configurationFile);
            File file = new File(configurationFile);
            try {
                if(!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                byte[] bytes = new byte[1024];
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file) );
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath.getFile().replace("%20", " ")));
                int i = 0;
                while ((i = in.read(bytes)) != -1){
                    out.write(bytes,0,i);
                }
                out.flush();
                out.close();
                in.close();

                filePath = (new File(configurationFile)).toURI().toURL();
                loadConfigurationFile();
            }catch (Exception e) {
                throw new ApplException("Erreur lors du chargement du fichier " +
            		"de configuration. ");
            }
        }
        
        return;
    }
    
    
    /*######################################################################*/
    /*#                         LANGUAGES MANAGING                         #*/
    /*######################################################################*/
    
    public final ArrayList<String> getLangTags(){
        NodeList nodeList = document.getElementsByTagName("lang-tag");
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < nodeList.getLength(); i++)
            list.add(nodeList.item(i).getTextContent());
        
        return list;
    }
    
    public final String getLangName(String langTag){
    	 NodeList nodeList = document.getElementsByTagName("lang-tag");
    	 for(int i = 0; i < nodeList.getLength(); i++){
            if(nodeList.item(i).getTextContent().equalsIgnoreCase(langTag))
                return nodeList.item(i).getParentNode().getChildNodes().item(1).getTextContent();
    	 }
    	 return null;
    }
    
    public final ArrayList<String> getLangNames(){
        NodeList nodeList = document.getElementsByTagName("lang-name");
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < nodeList.getLength(); i++)
                list.add(nodeList.item(i).getTextContent());

        return list;
    }
	
    public final String getDefaultLangTag(){
        NodeList nodeList = document.getElementsByTagName("lang-default");
        return nodeList.item(0).getTextContent().split("~")[1];
    }

    public final String getDefaultLangName(){
        NodeList nodeList = document.getElementsByTagName("lang-default");
        return nodeList.item(0).getTextContent().split("~")[0];
    }

    public final String getLangTag(String langName){
         NodeList nodeList = document.getElementsByTagName("lang-name");
         for(int i = 0; i < nodeList.getLength(); i++){
            if(nodeList.item(i).getTextContent().equalsIgnoreCase(langName))
                return nodeList.item(i).getParentNode().getChildNodes().item(3).getTextContent();
         }
         return null;
    }

    /*
     * defaultLang form is : lang-name~lang-tag
     */
    public final void changeDefaultLang(String defaultLang) throws ApplException{
        if((getLangTag(defaultLang.split("~")[0]) != null) && (getLangName(defaultLang.split("~")[1]) != null)){
            NodeList nodeList = document.getElementsByTagName("lang-default");
            nodeList.item(0).setTextContent(defaultLang);
            
            save();
        }
        else
            throw new ApplException("");
    }

    /*######################################################################*/
    /*#                     END OF LANGUAGES MANAGING                      #*/
    /*######################################################################*/



    /*######################################################################*/
    /*#                    APPLICATION INFOS MANAGING                      #*/
    /*######################################################################*/

    
    public final HashMap<String, String> getSoftwareInfos() throws ApplException{
        Document doc = null;
        DOMParser pars = null;
        try{
            pars = new DOMParser();
            pars.parse(getClass().getResource(APPLConstants.RESOURCES_FOLDER + configurationFile).toExternalForm().replace("%20", " "));
            doc = pars.getDocument();
        }catch(Exception ex){
            throw new ApplException("Erreur lors du chargement du fichier " +
            		"de configuration. ");
        }
        NodeList nodeList = doc.getElementsByTagName("software").item(0).getChildNodes();
        HashMap<String, String> sinfos = new HashMap<String, String>();
        for(int i = 1; i < nodeList.getLength(); i += 2)
            sinfos.put(nodeList.item(i).getNodeName().split("-")[1], nodeList.item(i).getTextContent());
        return sinfos;
    }
    
    public final boolean isAskThemActivated(){
        return document.getElementsByTagName("theme-loadingask").item(0).getTextContent().equalsIgnoreCase("Activated");
    }
    
    public final boolean isAskWorkspaceActivated(){
        return document.getElementsByTagName("workspace-loadingask").item(0).getTextContent().equalsIgnoreCase("Activated");
    }
    
    public final boolean isWorkspaceValid(){
        String dir = document.getElementsByTagName("workspace-directory").item(0).getTextContent();
        try{
            File f = new File(dir);
            if(f.exists() && f.isDirectory() && f.canRead() && f.canWrite() && f.canExecute()){
                f = new File(dir + File.separator + APPLConstants.LOCAL_ID);
                if(!f.exists())
                    f.mkdir();
                f = new File(dir + File.separator + APPLConstants.DISTRIBUTED_ID);
                if(!f.exists())
                    f.mkdir();
                f = new File(dir + File.separator + APPLConstants.TEMPLATES_ID);
                if(!f.exists())
                    f.mkdir();
                f = new File(dir + File.separator + APPLConstants.GRAMMARS_ID);
                if(!f.exists())
                    f.mkdir();
                f = new File(dir + File.separator + APPLConstants.WORK_ID);
                if(!f.exists())
                    f.mkdir();
                return true;
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }
    
    public final void setAskThemState(boolean state) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("theme-loadingask");
        nodeList.item(0).setTextContent(state ? "Activated" : "Deactivated");
        
        save();
    }
    
    public final void setAskWorkspaceState(boolean state) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("workspace-loadingask");
        nodeList.item(0).setTextContent(state ? "Activated" : "Deactivated");
        
        save();
    }
    
    public final String getWorkspace(){
        return document.getElementsByTagName("workspace-directory").item(0).getTextContent();
    }
    
    public final void setWorkspace(String workspace) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("workspace-directory");
        nodeList.item(0).setTextContent(workspace);
        
        save();
    }
    
    /*######################################################################*/
    /*#                END OF APPLICATION INFOS MANAGING                   #*/
    /*######################################################################*/
    
    
    /*######################################################################*/
    /*#                    SERVER INFOS MANAGING                           #*/
    /*######################################################################*/
    
    public final void setServerInfos(String host) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("sv-host");
        nodeList.item(0).setTextContent(host);
        nodeList = document.getElementsByTagName("sv-url");
        nodeList.item(0).setTextContent("rmi://" + host + ":" + APPLConstants.PORT);
        
        save();
    }
    
    public void setUserInfos(HashMap<String, String> userInfos) throws ApplException{
        try {
            NodeList nodeList = document.getElementsByTagName("user-login");
            nodeList.item(0).setTextContent(APPLConstants.encryptMessage(userInfos.get("login")));
            nodeList = document.getElementsByTagName("user-password");
            nodeList.item(0).setTextContent(APPLConstants.encryptMessage(userInfos.get("password")));
            
            save();
        } catch (CryptographyException ex) {
            throw new ApplException("");
        }
    }
    
    public HashMap<String, String> getUserInfos() throws ApplException {
        try {
            HashMap<String, String> userInfos = new HashMap<String, String>();
            NodeList nodeList = document.getElementsByTagName("user").item(0).getChildNodes();
            for(int i = 3; i < nodeList.getLength(); i += 2)
                userInfos.put(nodeList.item(i).getNodeName().split("-")[1], APPLConstants.decryptMessage(nodeList.item(i).getTextContent()));
            return userInfos;
        } catch (CryptographyException ex) {
            throw new ApplException("");
        }
    }
    
    public final boolean isSoundActivated(){
        return document.getElementsByTagName("sound-activated").item(0).getTextContent().equalsIgnoreCase("Activated");
    }
    
    public final void setSoundState(boolean state) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("sound-activated");
        nodeList.item(0).setTextContent(state ? "Activated" : "Deactivated");
        
        save();
    }

    public HashMap<String, String> getUser() {
        return user;
    }

    public void setUser(HashMap<String, String> user) {
        this.user = user;
    }
    
    public final boolean isUserRememberActivated(){
        return document.getElementsByTagName("user-remember").item(0).getTextContent().equalsIgnoreCase("Activated");
    }
    
    public final void setUserRememberState(boolean state) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("user-remember");
        nodeList.item(0).setTextContent(state ? "Activated" : "Deactivated");
        
        save();
    }
    
    /*######################################################################*/
    /*#                END OF SERVER INFOS MANAGING                        #*/
    /*######################################################################*/
    

    /*######################################################################*/
    /*#                      WINDOW INFOS MANAGING                         #*/
    /*######################################################################*/
    
    public final HashMap<String, String> getWindowInfos(){
        NodeList nodeList = document.getElementsByTagName("window").item(0).getChildNodes();
        HashMap<String, String> winfos = new HashMap<String, String>();
        for(int i = 1; i < nodeList.getLength(); i += 2)
            winfos.put(nodeList.item(i).getNodeName().split("-")[1], nodeList.item(i).getTextContent());
        return winfos;
    }
    
    public final void setWindowInfos(HashMap<String, String> winfos) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("window-width");
        nodeList.item(0).setTextContent(winfos.get("width"));
        nodeList = document.getElementsByTagName("window-height");
        nodeList.item(0).setTextContent(winfos.get("height"));
        
        save();
    }
    
    public final String getDefaultTheme(){
        return document.getElementsByTagName("default-theme").item(0).getTextContent();
    }
    
    public final void setDefaultTheme(String theme) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("default-theme");
        nodeList.item(0).setTextContent(theme);
        this.theme = new Theme(this);
        
        save();
    }
    
    public final String getSelectionRangeIndex(){
        return document.getElementsByTagName("pagin_selection_range").item(0).getTextContent();
    }
    
    public final void setSelectionRangeIndex(String index) throws ApplException{
        NodeList nodeList = document.getElementsByTagName("pagin_selection_range");
        nodeList.item(0).setTextContent(index);
        
        save();
    }
    
    public final String getHaskellInterpreter(){
        return document.getElementsByTagName("interpreter").item(0).getTextContent();
    }
    
    public int getTokenNum() {
        return 20;
    }
    
    public LanguageManager getLangManager() {
        return languageManager;
    }

    public String getLangValue(String key){
        return this.languageManager.getValue(key, this.getDefaultLangTag());
    }

    public Theme getTheme() {
        return theme;
    }

    private void save() throws ApplException {
        try{
            XMLSerializer ser = new XMLSerializer(
                new FileOutputStream(configurationFile), new OutputFormat("xml", "UTF-8", true));
            ser.serialize(document);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "L'application a rencontr√© un probl√®me et va s'arr√™ter."
                        + "\nVeuillez contacter un d√©veloppeur pour r√©gler ce probl√®me. ", "Arr√™t brusque"
                        + " de l'application", 
                        JOptionPane.ERROR_MESSAGE); 
                System.exit(0); 
            throw new ApplException("");
        }
    }

    private void updateConfigurationFiles() {
        File file = new File(configurationFile);
        try {
            if(file.exists())
                filePath = file.toURI().toURL();
            else{
                filePath = getClass().getResource(APPLConstants.RESOURCES_FOLDER
        		+ configurationFile.toLowerCase());
                try {
                    if(!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    byte[] bytes = new byte[1024];
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file) );
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath.getFile().replace("%20", " ")));
                    int i = 0;
                    while ((i = in.read(bytes)) != -1){
                        out.write(bytes,0,i);
                    }
                    out.flush();
                    out.close();
                    in.close();
                    
                    filePath = (new File(configurationFile)).toURI().toURL();
                } catch (Exception ex) {
                	
                }    
            }
        } catch (MalformedURLException ex) {
            
        }
        String englishFile = APPLConstants.LANG_FOLDER + APPLConstants.LANG_FILE_PREFIX + APPLConstants.ENGLISH_TAG + APPLConstants.LANG_FILE_EXTENSION;
        String frenchFile = APPLConstants.LANG_FOLDER + APPLConstants.LANG_FILE_PREFIX + APPLConstants.FRENCH_TAG + APPLConstants.LANG_FILE_EXTENSION;
        file = new File(englishFile);
        if(!file.exists()){
            try {
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                byte[] bytes = new byte[1024];
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file) );
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(getClass().getResource(
                        APPLConstants.RESOURCES_FOLDER + englishFile).getFile().replace("%20", " ")));
                int i = 0;
                while ((i = in.read(bytes)) != -1){
                    out.write(bytes,0,i);
                }
                out.flush();
                out.close();
                in.close();

                file = new File(frenchFile);
                out = new BufferedOutputStream(new FileOutputStream(file) );
                in = new BufferedInputStream(new FileInputStream(getClass().getResource(
                        APPLConstants.RESOURCES_FOLDER + frenchFile).getFile().replace("%20", " ")));
                i = 0;
                while ((i = in.read(bytes)) != -1){
                    out.write(bytes,0,i);
                }
                out.flush();
                out.close();
                in.close();
            } catch (Exception ex) {

            }
        }
        String engine = APPLConstants.ENGINE_FOLDER + APPLConstants.ENGINE_FILE_NAME;
        file = new File(engine);
        URL fileP;
        try {
            if(!file.exists()){
                fileP = getClass().getResource(APPLConstants.RESOURCES_FOLDER
        		+ engine);
                try {
                    if(!file.getParentFile().exists())
                        file.getParentFile().mkdirs();
                    byte[] bytes = new byte[1024];
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file) );
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileP.getFile().replace("%20", " ")));
                    int i = 0;
                    while ((i = in.read(bytes)) != -1){
                        out.write(bytes,0,i);
                    }
                    out.flush();
                    out.close();
                    in.close();
                } catch (Exception ex) {
                    
                }
            }
        } catch (Exception ex) {
            
        }
    }

    public ArrayList<String> getTemplatesNames() {
        String path = getWorkspace() + File.separator + APPLConstants.TEMPLATES_ID;
        File dir = new File(path);
        ArrayList<String> elts = APPLConstants.getFilesNamesMatchingPattern(dir, "templ_([a-zA-Z0-9_ -]{1,})\\.json");
        ArrayList<String> names = new ArrayList<String>();
        for(String name : elts)
            names.add(name.substring(6, name.length() - 5));
        return names;
    }
    
    private static char getElt(){
       int nb = Math.round((float)Math.random()*40);
       switch(nb){
           case 1 : {return 'e';} case 2 : {return 'y';} case 3 : {return ')';} case 4 : {return 'r';}
           case 5 : {return '}';} case 6 : {return '8';} case 7 : {return 'o';} case 8 : {return 'j';}
           case 9 : {return '.';} case 10 : {return 'w';} case 11 : {return '@';} case 12 : {return 't';}
           case 13 : {return 'x';} case 14 : {return 'l';}case 15 : {return '9';} case 16 : {return '_';}
           case 17 : {return '-';} case 18 : {return '(';} case 19 : {return '%';} case 20 : {return '#';}
           case 21 : {return 'K';} case 22 : {return '"';} case 23 : {return ':';} case 24 : {return '+';}
           case 25 : {return ',';} case 26 : {return '~';} case 27 : {return '^';} case 28 : {return '&';}
           case 29 : {return '>';} case 30 : {return '<';} case 31 : {return '*';} case 32 : {return 't';}
           case 33 : {return 'È';} case 34 : {return '$';} case 35 : {return '{';} case 36 : {return 'z';}
           case 37 : {return '!';} case 38 : {return '[';} case 39 : {return '‚';} case 40 : {return ']';}
           default : {return '?';}
       }
   }
    
    public static String generate(int length){
        String key = "";
        while(key.getBytes().length < length){
            key += getElt();
            if(key.getBytes().length > length)
                key = ""+getElt();
        }
        return key;
    }
    
	public PeerToPeerWorkflow getPeerToPeerWorkflowFromTemplate(String suffix) {
    	PeerToPeerWorkflow ed = null;
        String fileName = "templ_" + suffix + ".json";
        File file = new File(getWorkspace() + File.separator + APPLConstants.TEMPLATES_ID + File.separator + fileName);
        if(!file.exists() || !file.isFile() || !file.canRead())
            return null;
        String line = new String();
        String json = "";
        try{  
            FileReader fileReader = new FileReader(file);   
            BufferedReader bufferedReader = new BufferedReader(fileReader);  
            while((line = bufferedReader.readLine()) != null)  
                json += line+"\n";
            bufferedReader.close();
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            ed = gson.fromJson(json, PeerToPeerWorkflow.class);
        }catch(Exception ex){
            return null;
        }
        return ed;
    }
    
	public PeerToPeerWorkflow getPeerToPeerWorkflow(String suffix) {
    	PeerToPeerWorkflow ed = null;
        String fileName = "tdw_" + suffix + ".tdw";
        File file = new File(getWorkspace() + File.separator + APPLConstants.DISTRIBUTED_ID + File.separator + fileName);
        if(!file.exists() || !file.isFile() || !file.canRead())
            return null;
        String line = new String();
        String value = "";
        try{  
            FileReader fileReader = new FileReader(file);   
            BufferedReader bufferedReader = new BufferedReader(fileReader);  
            while((line = bufferedReader.readLine()) != null)  
                value += line+"~~~";
            bufferedReader.close();
            value = value.substring(0, value.length() - 3);
            String[] vals = value.split("~~~");
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            String json = null;
            if(vals.length > 4){
                String key = APPLConstants.decryptMessage(vals[2]);
                String initVect = APPLConstants.decryptMessage(vals[3]);
                String mess = vals[4];
                for(int j = 5; j < vals.length; j++)
                    mess += vals[j];
                json = APPLConstants.decryptMessage(mess, key, initVect);
            }else{
                throw new Exception(getLangValue("incorrect_file_input"));
            }
            ed = gson.fromJson(json, PeerToPeerWorkflow.class);
        }catch(Throwable ex){
            return null;
        }
        return ed;
    }

    public void savePeerToPeerWorkflowAsTemplate(PeerToPeerWorkflow edition, String nameSuffix) {
        if(nameSuffix == null || nameSuffix.trim().isEmpty())
            nameSuffix = "default_";
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final String json = gson.toJson(edition);
        String path = getWorkspace() + File.separator + APPLConstants.TEMPLATES_ID + File.separator + "templ_" + nameSuffix;
        File f = new File(path + ".json");
        int j = 1;
        while(f.exists()){
            f = new File(path + j + ".json");
            j++;
        }
        try {
            byte[] bytes = new byte[1024];
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
            BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(json.getBytes()));
            int i = 0;
            while ((i = in.read(bytes)) != -1){
                out.write(bytes,0,i);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception ex) {
            
        }
    }
}
