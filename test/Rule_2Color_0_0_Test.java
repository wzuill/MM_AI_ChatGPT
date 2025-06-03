import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Rule_2Color_0_0_Test {

    @Test
    void testRuleApplicationEliminatesBothColors() {
        // Given a guess of (4, 5, 4, 5) with feedback (0 black, 0 white)
        Guess guess = new Guess(4, 5, 4, 5);
        Feedback feedback = new Feedback(0, 0);
        DeductionState state = new DeductionState();

        // When the rule is applied
        DeductionRule rule = new Rule_2Color_0_0();
        rule.apply(guess, feedback, state);

        // Then both colors should be marked as not in the code and all positions eliminated
        assertEquals(false, state.isColorInCode(4));
        assertEquals(false, state.isColorInCode(5));

        for (int pos = 0; pos < 4; pos++) {
            assertTrue(state.isPositionEliminated(4, pos), "Color 4 not eliminated at pos " + pos);
            assertTrue(state.isPositionEliminated(5, pos), "Color 5 not eliminated at pos " + pos);
        }
    }
}
