/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.CryptographyException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Ndadji Maxime
 */
public class APPLConstants {
    public static final String  
            LANG_FOLDER = "Languages/",
            LANG_FILE_PREFIX = "language-",
            LANG_FILE_EXTENSION = ".properties",
            CONFIG_FOLDER = "Configurations/",
            ENGINE_FOLDER = "Engine/",
            CONFIG_FILE_NAME = "configuration.xml",
            ENGINE_FILE_NAME = "reconciliation.hs",
            ENGLISH_TAG = "en_GB",
            FRENCH_TAG = "fr_FR",
            RESOURCES_FOLDER = "/smartworkflow/dwfms/urifia/fmml/miu/resources/",
            WORKSPACE_ID = "p2pTinyCE-Workspace",
            LOCAL_ID = "Local",
            DISTRIBUTED_ID = "Distributed",
            TEMPLATES_ID = "Templates",
            GRAMMARS_ID = "Grammars",
            WORK_ID = "Work",
            SEPARATOR = "-~@~-";
    
    public static final int PORT = 6102;
    
    /**
     * Forme courte de l'algorithme de cryptage par défaut
     */
    public static final String DEFAULT_ENCRYPTION_ALGO_SHORT = "AES";

    /**
     * Forme longue de l'algorithme de cryptage par défaut
     */
    public static final String DEFAULT_ENCRYPTION_ALGO_LONG = "AES/CBC/PKCS5Padding";

    /**
     * Clé privée de cryptage (<i>ne utiliser qu'en cas de besoin réel</i>)
     */
    public static final String DEFAULT_ENCRYPTION_SECRET_KEY = "#1_%$M-(>=*/@]{+";

    /**
     * Vecteur d'initialisation de cryptage (<i>ne utiliser qu'en cas de besoin réel</i>) 
     */
    public static final String DEFAULT_ENCRYPTION_INIT_VECTOR = "<~r2&-I|^.q[}9s:";
    
    
    /**
     * Cette méthode permet de crypter des messages suivant un algorithme donnée. Les messages cryptés
     * peuvent par la suite être décryptés. Il est alors important de savoir que la clé et le vecteur
     * d'initialisation (chaines de taille 16) doivent être identiques lors du cryptage et du décryptage.
     * L'algorithme utilisé est fourni en argument à cette méthode. Nous attendons une courte et une longue
     * forme de l'algorithme.
     * 
     * @param message
     * Le message à crypter
     * @param secretKey
     * La clé privée de cryptage (16 caractères)
     * @param initVector
     * Le vecteur d'initialisation de cryptage (16 caractères)
     * @param encryptionAlgoShort
     * La forme courte de l'algorithme de cryptage (ex: "AES")
     * @param encryptionAlgoLong
     * La forme longue de l'algorithme de cryptage (ex: "AES/CBC/PKCS5Padding")
     * @return
     * Le message crypté.
     * @throws CryptographyException
     */
    public static String encryptMessage(String message, String secretKey, String initVector, 
                String encryptionAlgoShort, String encryptionAlgoLong) throws CryptographyException{
        try {
            // Premier cryptage avec les paramètres passés en argument.
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), encryptionAlgoShort);
            IvParameterSpec ivParamSpec = new IvParameterSpec(initVector.getBytes());

            Cipher cipher = Cipher.getInstance(encryptionAlgoLong);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParamSpec);

            byte[] messageBytes = message.getBytes("UTF8");
            byte[] encryptedMessageBytes;

            encryptedMessageBytes = cipher.doFinal(messageBytes);
            BASE64Encoder base64Encoder = new BASE64Encoder();

            String firstResult = base64Encoder.encode(encryptedMessageBytes);

            // Second cryptage avec les paramètres de cryptage par défaut.
            secretKeySpec = new SecretKeySpec(DEFAULT_ENCRYPTION_SECRET_KEY.getBytes(), DEFAULT_ENCRYPTION_ALGO_SHORT);
            ivParamSpec = new IvParameterSpec(DEFAULT_ENCRYPTION_INIT_VECTOR.getBytes());

            cipher = Cipher.getInstance(DEFAULT_ENCRYPTION_ALGO_LONG);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParamSpec);

            messageBytes = firstResult.getBytes("UTF8");

            encryptedMessageBytes = cipher.doFinal(messageBytes);

            return base64Encoder.encode(encryptedMessageBytes);

        } catch (IllegalBlockSizeException e) {
            throw new CryptographyException(e);
        } catch (BadPaddingException e) {
            throw new CryptographyException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographyException(e);
        } catch (NoSuchPaddingException e) {
            throw new CryptographyException(e);
        } catch (InvalidKeyException e) {
            throw new CryptographyException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new CryptographyException(e);
        } catch (UnsupportedEncodingException e) {
            throw new CryptographyException(e);
        }
    }
    
    public static String encryptMessage(String message) throws CryptographyException{
        return encryptMessage(message, DEFAULT_ENCRYPTION_SECRET_KEY, DEFAULT_ENCRYPTION_INIT_VECTOR, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }
    
    public static String encryptMessage(String message, String secretKey, String initVector) 
            throws CryptographyException{
        return encryptMessage(message, secretKey, initVector, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }

    /**
     * Cette méthode permet de décrypter des messages suivant un algorithme donnée.
     * 
     * @param encryptedMessage
     * Le message crypté à décrypter
     * @param secretKey
     * La clé privée utilisée lors du cryptage (16 caractères)
     * @param initVector
     * Le vecteur d'initialisation utilisé lors du cryptage (16 caractères)
     * @param encryptionAlgoShort
     * La forme courte de l'algorithme de cryptage utilisé (ex: "AES")
     * @param encryptionAlgoLong
     * La forme longue de l'algorithme de cryptage utilisé (ex: "AES/CBC/PKCS5Padding")
     * @return
     * Le message décrypté.
     * @throws CryptographyException
     */
    public static String decryptMessage(String encryptedMessage, String secretKey, String initVector, 
                String encryptionAlgoShort, String encryptionAlgoLong) throws CryptographyException{
        try {
            // Premier décryptage avec les paramètres de cryptage par défaut.
            SecretKeySpec secretKeySpec = new SecretKeySpec(DEFAULT_ENCRYPTION_SECRET_KEY.getBytes(), DEFAULT_ENCRYPTION_ALGO_SHORT);
            IvParameterSpec ivParamSpec = new IvParameterSpec(DEFAULT_ENCRYPTION_INIT_VECTOR.getBytes());

            Cipher cipher = Cipher.getInstance(DEFAULT_ENCRYPTION_ALGO_LONG);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParamSpec);

            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] encryptedMessageBytes = base64Decoder.decodeBuffer(encryptedMessage);
            byte[] messageBytes = cipher.doFinal(encryptedMessageBytes);

            String firstResult = new String(messageBytes, "UTF8");

            // Second décryptage avec les paramètres passés en argument.
            secretKeySpec = new SecretKeySpec(secretKey.getBytes(), encryptionAlgoShort);
            ivParamSpec = new IvParameterSpec(initVector.getBytes());

            cipher = Cipher.getInstance(encryptionAlgoLong);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParamSpec);

            base64Decoder = new BASE64Decoder();
            encryptedMessageBytes = base64Decoder.decodeBuffer(firstResult);
            messageBytes = cipher.doFinal(encryptedMessageBytes);

            return new String(messageBytes, "UTF8");
        } catch (IllegalBlockSizeException e) {
            throw new CryptographyException(e);
        } catch (BadPaddingException e) {
            throw new CryptographyException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographyException(e);
        } catch (NoSuchPaddingException e) {
            throw new CryptographyException(e);
        } catch (InvalidKeyException e) {
            throw new CryptographyException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new CryptographyException(e);
        } catch (UnsupportedEncodingException e) {
            throw new CryptographyException(e);
        } catch (IOException e) {
            throw new CryptographyException(e);
        }
    }
    
    public static String decryptMessage(String encryptedMessage) throws CryptographyException{
        return decryptMessage(encryptedMessage, DEFAULT_ENCRYPTION_SECRET_KEY, DEFAULT_ENCRYPTION_INIT_VECTOR, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }
    
    public static String decryptMessage(String encryptedMessage, String secretKey, String initVector) 
            throws CryptographyException{
        return decryptMessage(encryptedMessage, secretKey, initVector, 
                DEFAULT_ENCRYPTION_ALGO_SHORT, DEFAULT_ENCRYPTION_ALGO_LONG);
    }
    
    public static String getFileNameFromString(String name){
        String fileName = "";
        if(name != null){
            for(int i = 0; i < name.length(); i++){
                String cat = name.charAt(i)+"";
                if(cat.matches("[a-zA-Z0-9_ -]"))
                    fileName += cat;
            }
        }
        return fileName;
    }
    
    public static ArrayList<String> getFilesNames(File rep) {
        ArrayList<String> files = new ArrayList<String>();
        for(File file : rep.listFiles()){
            if(file.isFile())
                files.add(file.getName());
        }
        return files;
    }
    
    public static ArrayList<String> getFilesNamesMatchingPattern(File rep, String pattern) {
        ArrayList<String> files = new ArrayList<String>();
        for(File file : rep.listFiles()){
            if(file.isFile() && file.getName().matches(pattern))
                files.add(file.getName());
        }
        return files;
    }
    
    public static ArrayList<String> getDirectoriesNames(File rep) {
        ArrayList<String> files = new ArrayList<String>();
        for(File file : rep.listFiles()){
            if(file.isDirectory())
                files.add(file.getName());
        }
        return files;
    }
    
    public static ArrayList<String> getAllFilesNames(File rep) {
        ArrayList<String> files = new ArrayList<String>();
        for(File file : rep.listFiles())
            files.add(file.getName());
        return files;
    }
}
