import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {

    @Test
    void testConstructorAndGetters() {
        Feedback fb = new Feedback(2, 1);
        assertEquals(2, fb.getBlack());
        assertEquals(1, fb.getWhite());
    }

    @Test
    void testEqualityAndHashCode() {
        Feedback fb1 = new Feedback(1, 2);
        Feedback fb2 = new Feedback(1, 2);
        Feedback fb3 = new Feedback(2, 1);

        assertEquals(fb1, fb2);
        assertEquals(fb1.hashCode(), fb2.hashCode());
        assertNotEquals(fb1, fb3);
    }

    @Test
    void testToStringFormat() {
        Feedback fb = new Feedback(3, 0);
        assertEquals("(3, 0)", fb.toString());
    }
}
