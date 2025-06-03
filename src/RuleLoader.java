import java.util.*;

public class RuleLoader {

    public enum GuessPatternType {
        TWO_COLOR, THREE_COLOR, FOUR_COLOR
    }

    public static class RuleKey {
        private final GuessPatternType patternType;
        private final Feedback feedback;

        public RuleKey(GuessPatternType patternType, Feedback feedback) {
            this.patternType = patternType;
            this.feedback = feedback;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof RuleKey)) return false;
            RuleKey other = (RuleKey) obj;
            return patternType == other.patternType && Objects.equals(feedback, other.feedback);
        }

        @Override
        public int hashCode() {
            return Objects.hash(patternType, feedback);
        }
    }

    private final Map<RuleKey, DeductionRule> rules = new HashMap<>();

    public RuleLoader() {
        registerRules();
    }

    private void registerRules() {
        MarkdownRuleParser.registerRulesInto(rules);
    }

    public GuessPatternType classify(Guess guess) {
        Set<Integer> distinct = new HashSet<>();
        for (int value : guess.values()) {
            distinct.add(value);
        }

        if (distinct.size() == 2)
            return GuessPatternType.TWO_COLOR;
        else if (distinct.size() == 3)
            return GuessPatternType.THREE_COLOR;
        else
            return GuessPatternType.FOUR_COLOR;
    }


    public DeductionRule getRule(Guess guess, Feedback feedback) {
        GuessPatternType type = classify(guess);
        RuleKey key = new RuleKey(type, feedback);
        DeductionRule rule = rules.get(key);
        if (rule == null) {
            throw new IllegalArgumentException("No rule found for pattern type: " + type + ", feedback: " + feedback);
        }
        return rule;
    }
}
