import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleLoaderIntegrationTest {

    @Test
    void testRuleLoaderAppliesCorrectRuleToDeductionState() {
        // Given
        RuleLoader loader = new RuleLoader();
        Guess guess = new Guess(4, 5, 4, 5);
        Feedback feedback = new Feedback(0, 0);
        DeductionState state = new DeductionState();

        // When
        DeductionRule rule = loader.getRule(guess, feedback);
        rule.apply(guess, feedback, state);

        // Then
        assertEquals(false, state.isColorInCode(4));
        assertEquals(false, state.isColorInCode(5));

        for (int pos = 0; pos < 4; pos++) {
            assertTrue(state.isPositionEliminated(4, pos), "Color 4 should be eliminated at pos " + pos);
            assertTrue(state.isPositionEliminated(5, pos), "Color 5 should be eliminated at pos " + pos);
        }
    }
}

