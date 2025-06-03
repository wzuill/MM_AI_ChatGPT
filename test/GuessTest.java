import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GuessTest {

    @Test
    void testConstructorAndValues() {
        Guess guess = new Guess(1, 2, 3, 4);
        int[] expected = {1, 2, 3, 4};
        assertArrayEquals(expected, guess.values());
    }

    @Test
    void testGetSinglePosition() {
        Guess guess = new Guess(4, 3, 2, 1);
        assertEquals(4, guess.get(0));
        assertEquals(3, guess.get(1));
        assertEquals(2, guess.get(2));
        assertEquals(1, guess.get(3));
    }

    @Test
    void testLengthIsFour() {
        Guess guess = new Guess(0, 0, 0, 0);
        assertEquals(4, guess.length());
    }

    @Test
    void testEqualityAndHashCode() {
        Guess g1 = new Guess(1, 2, 3, 4);
        Guess g2 = new Guess(1, 2, 3, 4);
        Guess g3 = new Guess(4, 3, 2, 1);

        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
        assertNotEquals(g1, g3);
    }

    @Test
    void testToStringFormat() {
        Guess guess = new Guess(1, 0, 2, 3);
        assertEquals("[1, 0, 2, 3]", guess.toString());
    }
}
