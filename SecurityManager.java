public class SecurityManager {
    private String password;

    public SecurityManager(String password) {
        this.password = password;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public String encrypt(String data) {

        int shift = 3;
        StringBuilder encryptedData = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    encryptedData.append((char) ('A' + (c - 'A' + shift) % 26));
                } else {
                    encryptedData.append((char) ('a' + (c - 'a' + shift) % 26));
                }
            } else {
                encryptedData.append(c);
            }
        }

        return encryptedData.toString();
    }

    public String decrypt(String encryptedData) {

        int shift = 3;
        StringBuilder decryptedData = new StringBuilder();

        for (int i = 0; i < encryptedData.length(); i++) {
            char c = encryptedData.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    decryptedData.append((char) ('A' + (c - 'A' - shift + 26) % 26));
                } else {
                    decryptedData.append((char) ('a' + (c - 'a' - shift + 26) % 26));
                }
            } else {
                decryptedData.append(c);
            }
        }

        return decryptedData.toString();
    }

    public static void main(String[] args) {
        String password = "myPassword123";
        SecurityManager securityManager = new SecurityManager(password);

        String inputPassword = "password123";
        boolean isValidPassword = securityManager.verifyPassword(inputPassword);

        if (isValidPassword) {
            System.out.println("Password is valid!");
            String data = "Sensitive data";
            String encryptedData = securityManager.encrypt(data);
            System.out.println("Encrypted data: " + encryptedData);

            String decryptedData = securityManager.decrypt(encryptedData);
            System.out.println("Decrypted data: " + decryptedData);
        } else {
            System.out.println("Invalid password!");
        }
    }
}

