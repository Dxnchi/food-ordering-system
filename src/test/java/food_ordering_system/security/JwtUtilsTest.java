package food_ordering_system.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private final String testEmail = "test@user.com";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        // Inject fake properties for testing using Spring's ReflectionTestUtils
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "4A614E645267556B58703273357638792F423F4528482B4D6251655468576D5A");
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 86400000); // 24 hours
    }

    @Test
    void testGenerateAndExtractToken() {
        String token = jwtUtils.generateToken(testEmail);

        assertNotNull(token);
        assertTrue(jwtUtils.validateToken(token));

        String extractedEmail = jwtUtils.extractEmail(token);
        assertEquals(testEmail, extractedEmail);
    }

    @Test
    void testTamperedTokenIsInvalid() {
        String token = jwtUtils.generateToken(testEmail);
        String tamperedToken = token + "fake"; // Mess up the signature

        assertFalse(jwtUtils.validateToken(tamperedToken));
    }
}