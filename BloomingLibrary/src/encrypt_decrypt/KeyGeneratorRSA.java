/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypt_decrypt;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author 2dam
 */
public class KeyGeneratorRSA {
    /**
     * Genera el fichero con la clave privada
     */
    public void generatePrivateKey() {
        
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024); // Inicializamos el tamaño a 1024 bits
            KeyPair keypair = generator.generateKeyPair();
            PublicKey publicKey = keypair.getPublic(); // Clave Pública
            PrivateKey privateKey = keypair.getPrivate(); // Clave Privada

            // Pública
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            FileOutputStream fileOutputStream = new FileOutputStream("Generate_Public.key");
            fileOutputStream.write(x509EncodedKeySpec.getEncoded());
            fileOutputStream.close();

            // Privada
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            fileOutputStream = new FileOutputStream("Generate_Private.key");
            fileOutputStream.write(pKCS8EncodedKeySpec.getEncoded());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
