package beans;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.MessageDigest;

public class Password {
    public static final int nbLowerCaseChar = 3;
    public static final int nbUpperCaseChar = 3;
    public static final int nbDigits = 2;
    public static final int nbSpecialChars = 3;
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    public static String generateFirstPassword()
    {
        int nbLowerCaseChar = 3;
        int nbUpperCaseChar = 3;
        int nbDigits = 2;
        int nbSpecialChars = 3;
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(nbLowerCaseChar);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(nbUpperCaseChar);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(nbDigits);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return "problem inside specialchar of passay";
            }

            @Override
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(nbSpecialChars);

        String password = gen.generatePassword(15, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
        return password;
    }
}
