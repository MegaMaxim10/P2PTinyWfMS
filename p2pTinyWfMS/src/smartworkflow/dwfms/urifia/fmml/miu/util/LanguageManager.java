package smartworkflow.dwfms.urifia.fmml.miu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;

/**
 * @author Ndadji Maxime
 * 
 * Voici la classe chargée de manipuler les fichiers de langue. Pour tout ce qui aura 
 * trait à la langue, il faudra s'adresser à cet objet. 
 * 
 */

public class LanguageManager {
    private HashMap<String, Properties> langFiles;
    private ConfigurationManager configurationManager;
    private int frenchLoaded = 0, englishLoaded = 0;
    
    public LanguageManager(ConfigurationManager configurationManager) throws ApplException{
        this.configurationManager = configurationManager;
        this.setLangFiles(this.configurationManager.getLangTags());
    }
    
    public final void setLangFiles(ArrayList<String> langTags) throws ApplException{
    	langFiles = new HashMap<String, Properties>();
    	Properties langFile;
    	InputStream fichierProperties;
        File file;
        String englishFile = APPLConstants.LANG_FOLDER + APPLConstants.LANG_FILE_PREFIX + APPLConstants.ENGLISH_TAG + APPLConstants.LANG_FILE_EXTENSION;
        String frenchFile = APPLConstants.LANG_FOLDER + APPLConstants.LANG_FILE_PREFIX + APPLConstants.FRENCH_TAG + APPLConstants.LANG_FILE_EXTENSION;
    	try{
            langFile = new Properties();
            fichierProperties = (getClass().getResource(APPLConstants.RESOURCES_FOLDER
                    + englishFile)).openStream();
            langFile.load(fichierProperties);
            langFiles.put(APPLConstants.ENGLISH_TAG, langFile);
            
            langFile = new Properties();
            fichierProperties = (getClass().getResource(APPLConstants.RESOURCES_FOLDER
                    + frenchFile)).openStream();
            langFile.load(fichierProperties);
            langFiles.put(APPLConstants.FRENCH_TAG, langFile);
        }catch(Exception e){
            
        }
        
        for(String langTag : langTags){
            langFile = new Properties();
            fichierProperties = null;
            try{
                file = new File(APPLConstants.LANG_FOLDER
                            + APPLConstants.LANG_FILE_PREFIX + langTag + APPLConstants.
                            LANG_FILE_EXTENSION);
                fichierProperties = file.toURI().toURL().openStream();
            }catch(Exception ex){
                if(langTag.equals(APPLConstants.ENGLISH_TAG)){
                    try{
                        file = new File(APPLConstants.RESOURCES_FOLDER
                                + englishFile);
                        fichierProperties = file.toURI().toURL().openStream();
                    }catch(Exception e){
                        if(frenchLoaded == -1)
                            throw new ApplException( "Le fichier de langue "+APPLConstants.
                                LANG_FILE_PREFIX + langTag + APPLConstants.LANG_FILE_EXTENSION + " " +
                                                "est introuvable.");
                        englishLoaded = -1;
                    }
                }
                if(langTag.equals(APPLConstants.FRENCH_TAG)){
                    try{
                        file = new File(APPLConstants.RESOURCES_FOLDER
                                + frenchFile);
                        fichierProperties = file.toURI().toURL().openStream();
                    }catch(Exception e){
                        if(englishLoaded == -1)
                            throw new ApplException( "Le fichier de langue "+APPLConstants.
                            LANG_FILE_PREFIX + langTag + APPLConstants.LANG_FILE_EXTENSION + " " +
                                            "est introuvable.");
                        frenchLoaded = -1;
                    }
                }
            }

            if(fichierProperties != null) {
                try {
                    langFile.load(fichierProperties);
                    langFiles.put(langTag, langFile);
                }catch (FileNotFoundException e) {
                    if(langTag.equals(APPLConstants.ENGLISH_TAG) && frenchLoaded == -1){
                        throw new ApplException( "Le fichier de langue "+APPLConstants.
                            LANG_FILE_PREFIX + langTag + APPLConstants.LANG_FILE_EXTENSION + " " +
                                            "est introuvable.");
                    }
                    if(langTag.equals(APPLConstants.FRENCH_TAG) && englishLoaded == -1){
                            throw new ApplException( "Le fichier de langue "+APPLConstants.
                            LANG_FILE_PREFIX + langTag + APPLConstants.LANG_FILE_EXTENSION + " " +
                                            "est introuvable.");
                    }
                }catch (IOException e) {
                    if(langTag.equals(APPLConstants.ENGLISH_TAG) && frenchLoaded == -1){
                        throw new ApplException( "Le fichier de langue "+APPLConstants.
                            LANG_FILE_PREFIX + langTag + APPLConstants.LANG_FILE_EXTENSION + " " +
                                            "ne peut pas Ãªtre chargÃ©.");
                    }
                    if(langTag.equals(APPLConstants.FRENCH_TAG) && englishLoaded == -1){
                            throw new ApplException( "Le fichier de langue "+APPLConstants.
                            LANG_FILE_PREFIX + langTag + APPLConstants.LANG_FILE_EXTENSION + " " +
                                            "ne peut pas Ãªtre chargÃ©.");
                    }
                }
            }
    	}
    }
    
    public String getValue(String key, String langTag){
        String tag = langTag;
        if(langFiles.get(tag) == null)
            tag = (englishLoaded == 0) ? APPLConstants.ENGLISH_TAG : ((frenchLoaded == 0) ? APPLConstants.FRENCH_TAG : null);
        if(tag == null){
            if(langFiles.keySet().isEmpty())
                return "----";
        }
        return langFiles.get(tag).getProperty(key, "----");
    }
}
