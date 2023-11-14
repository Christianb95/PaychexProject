class SecurityManager {
    constructor(password) {
        this.password = password;
    }

    verifyPassword(inputPassword) {
        return this.password === inputPassword;
    }

    encrypt(data) {
        const shift = 3;
        let encryptedData = '';

        for (let i = 0; i < data.length; i++) {
            let c = data.charAt(i);

            if (c.match(/[a-z]/i)) {
                const isUpperCase = c === c.toUpperCase();
                const offset = isUpperCase ? 'A'.charCodeAt(0) : 'a'.charCodeAt(0);
                encryptedData += String.fromCharCode((c.charCodeAt(0) - offset + shift) % 26 + offset);
            } else {
                encryptedData += c;
            }
        }

        return encryptedData;
    }

    decrypt(encryptedData) {
        const shift = 3;
        let decryptedData = '';

        for (let i = 0; i < encryptedData.length; i++) {
            let c = encryptedData.charAt(i);

            if (c.match(/[a-z]/i)) {
                const isUpperCase = c === c.toUpperCase();
                const offset = isUpperCase ? 'A'.charCodeAt(0) : 'a'.charCodeAt(0);
                decryptedData += String.fromCharCode((c.charCodeAt(0) - offset - shift + 26) % 26 + offset);
            } else {
                decryptedData += c;
            }
        }

        return decryptedData;
    }
}

const password = "myPassword123";
const securityManager = new SecurityManager(password);

const inputPassword = "password123";
const isValidPassword = securityManager.verifyPassword(inputPassword);

if (isValidPassword) {
    console.log("Password is valid!");
    const data = "Sensitive data";
    const encryptedData = securityManager.encrypt(data);
    console.log("Encrypted data: " + encryptedData);

    const decryptedData = securityManager.decrypt(encryptedData);
    console.log("Decrypted data: " + decryptedData);
} else {
    console.log("Invalid password!");
}
