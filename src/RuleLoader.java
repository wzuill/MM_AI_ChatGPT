
import java.util.*;

public class RuleLoader {

    public enum GuessPatternType {
        TWO_COLOR, THREE_COLOR, FOUR_COLOR
    }

    private final Map<RuleKey, DeductionRule> rules;

    public RuleLoader() {
        rules = new HashMap<>();
        registerRules();
    }
    public GuessPatternType classify(Guess guess) {
        Set<Integer> distinct = new HashSet<>();
        for (int value : guess.values()) {
            distinct.add(value);
        }

        int size = distinct.size();
        if (size == 2) return GuessPatternType.TWO_COLOR;
        else if (size == 3) return GuessPatternType.THREE_COLOR;
        else return GuessPatternType.FOUR_COLOR;
    }


    public DeductionRule getRule(Guess guess, Feedback feedback) {
        GuessPatternType type = classify(guess);
        RuleKey key = new RuleKey(type, feedback);
        DeductionRule rule = rules.get(key);
        if (rule == null) throw new IllegalArgumentException("No rule for: " + key);
        return rule;
    }

    private void registerRules() {
        rules.put(new RuleKey(GuessPatternType.TWO_COLOR, new Feedback(0, 0)), CanonicalMatrixRule.for2Color_0_0());

        // Add other rules here later...
    }

    private static class RuleKey {
        private final GuessPatternType type;
        private final Feedback feedback;

        RuleKey(GuessPatternType type, Feedback feedback) {
            this.type = type;
            this.feedback = feedback;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof RuleKey)) return false;
            RuleKey rk = (RuleKey) o;
            return type == rk.type && feedback.equals(rk.feedback);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, feedback);
        }

        @Override
        public String toString() {
            return type + " + " + feedback;
        }
    }
}

