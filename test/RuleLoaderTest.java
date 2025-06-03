import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleLoaderTest {

    @Test
    void testClassifyGuessPattern() {
        RuleLoader loader = new RuleLoader();

        Guess twoColor = new Guess(1, 1, 2, 2);
        Guess threeColor = new Guess(1, 2, 3, 2);
        Guess fourColor = new Guess(1, 2, 3, 4);

        assertEquals(RuleLoader.GuessPatternType.TWO_COLOR, loader.classify(twoColor));
        assertEquals(RuleLoader.GuessPatternType.THREE_COLOR, loader.classify(threeColor));
        assertEquals(RuleLoader.GuessPatternType.FOUR_COLOR, loader.classify(fourColor));
    }

    @Test
    void testGetRuleReturnsCanonicalRuleForKnownPattern() {
        RuleLoader loader = new RuleLoader();
        Guess guess = new Guess(4, 5, 4, 5);
        Feedback feedback = new Feedback(0, 0);

        DeductionRule rule = loader.getRule(guess, feedback);
        assertNotNull(rule);
        assertTrue(rule instanceof CanonicalMatrixRule);
    }


    @Test
    void testGetRuleThrowsOnMissingRule() {
        RuleLoader loader = new RuleLoader();
        Guess guess = new Guess(1, 2, 3, 4);
        Feedback feedback = new Feedback(4, 0); // not registered

        assertThrows(IllegalArgumentException.class, () -> {
            loader.getRule(guess, feedback);
        });
    }

    @Test
    void testGetRuleAppliesWithoutError() {
        RuleLoader loader = new RuleLoader();
        Guess guess = new Guess(4, 5, 4, 5);
        Feedback feedback = new Feedback(0, 0);
        DeductionState state = new DeductionState();

        DeductionRule rule = loader.getRule(guess, feedback);
        assertDoesNotThrow(() -> rule.apply(guess, feedback, state));
    }

}
