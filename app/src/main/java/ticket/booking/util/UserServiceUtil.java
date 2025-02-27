package ticket.booking.util;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class UserServiceUtil {
    public static String hashPassword(String plainPassword) {
        System.out.println("Hashing Password");
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
        
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        BCrypt.Result result =  BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
        return result.verified;
    }


}
