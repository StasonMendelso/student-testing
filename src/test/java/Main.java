
import com.stason.testing.controller.utils.EncryptionPassword;

public class Main {
    public static void main(String[] args) {
        String salt = EncryptionPassword.generateSalt();
        System.out.println(salt);
        String hashedPassword = EncryptionPassword.hash("12345678123456",salt.toString());
        System.out.println(hashedPassword);
        if(hashedPassword.equals(EncryptionPassword.hash("12345678",salt.toString()))){
            System.out.println("CORRECT");
        }
    }
}
