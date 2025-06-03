public interface DeductionRule {
    void apply(Guess guess, Feedback feedback, DeductionState state);
}
