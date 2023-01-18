/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypt_decrypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author nerea
 */
public class EncryptDecrypt {

    /**
     * Encriptar de forma asimetrica (RSA, modo ECB y padding PKCS1Padding)con
     * clave publica.
     *
     * @param text String con texto a enciptar.
     * @return Array de bytes con el texto cifrado.
     */
    public static byte[] encryptTextPublicKey(String text) {
        byte[] encodedText = null;
        try {
            //Recuperar Clave pública
            byte fileKey[] = fileReader("Generate_Public.key");
            System.out.println("Tamaño -> " + fileKey.length + " bytes");

            //Generamos una instancia de KeyFactory para el algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            //Ciframos el mensaje con el algoritmo RSA modo ECB y padding PKCS1Padding
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedText;

    }

    /**
     * Desencriptar de forma asimetrica (RSA, modo ECB y padding PKCS1Padding)
     * con clave privada.
     *
     * @param text String con texto a enciptar.
     * @return El mensaje descifrado
     */
    private static byte[] decryptTextPrivateKey(byte[] text) {
        byte[] decodedText = null;
        try {
            // Recuperar Clave privada
            byte fileKey[] = fileReader("Generate_Private.key");
            System.out.println("Tamaño -> " + fileKey.length + " bytes");

            //Generamos una instancia de KeyFactory para el algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);

            //Desciframos el mensaje con el algoritmo RSA modo ECB y padding PKCS1Padding
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decodedText = cipher.doFinal(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedText;
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
