package co.posinvent.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class HashGeneratorTest {

    @Test
    void printAdminHash() {
        var hash = new BCryptPasswordEncoder(12).encode("Admin123!");
        System.out.println("\n>>> BCrypt hash for Admin123!: " + hash + "\n");
    }
}
