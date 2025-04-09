package yilee.beasm.utils;

import java.security.*;

public class JwtRsaGeneratorUtils {

    public static KeyPair keyPair = generatorKeyPair();
    public static PrivateKey secretKey = keyPair.getPrivate();
    public static PublicKey pubKey = keyPair.getPublic();

    private static KeyPair generatorKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
