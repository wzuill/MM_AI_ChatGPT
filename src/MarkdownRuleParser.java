import java.util.*;
import java.util.regex.*;
import java.nio.file.*;
import java.io.IOException;

public class MarkdownRuleParser {

    public static CanonicalMatrixRule fromMarkdownFile(Path path, String feedbackLabel) {
        try {
            String content = Files.readString(path);
            return fromMarkdownSection(content, feedbackLabel);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read markdown file: " + path, e);
        }
    }

    public static CanonicalMatrixRule fromMarkdownFile(String filename, String feedbackLabel) {
        return fromMarkdownFile(Path.of(filename), feedbackLabel);
    }

    public static CanonicalMatrixRule fromMarkdownSection(String markdown, String feedbackLabel) {
        List<CanonicalMatrixRule.DeductionEntry> entries = new ArrayList<>();

        String[] lines = markdown.split("\\R");
        boolean inSection = false;

        for (String line : lines) {
            if (line.trim().equalsIgnoreCase("## Feedback " + feedbackLabel)) {
                inSection = true;
                continue;
            }
            if (inSection && line.startsWith("## ")) {
                break; // End of section
            }
            if (inSection && line.matches("\\|\\s*[A-Z]\\s*\\|.*")) {
                String[] cells = line.split("\\|");
                if (cells.length < 7) continue;

                char letter = cells[1].trim().charAt(0);
                List<Integer> eliminate = new ArrayList<>();
                for (int i = 2; i <= 5; i++) {
                    if (cells[i].trim().equalsIgnoreCase("X")) {
                        eliminate.add(i - 2);
                    }
                }
                boolean inCode = !cells[6].toLowerCase().contains("no");

                entries.add(new CanonicalMatrixRule.DeductionEntry(letter, inCode, eliminate));
            }
        }

        if (entries.isEmpty()) throw new IllegalArgumentException("No deduction entries found for: " + feedbackLabel);
        return new CanonicalMatrixRule(entries);
    }

    // Integration for RuleLoader usage
    public static void registerRule(Map<RuleLoader.RuleKey, DeductionRule> rules,
                                    RuleLoader.GuessPatternType type, Feedback feedback,
                                    String filename, String feedbackLabel) {
        RuleLoader.RuleKey key = new RuleLoader.RuleKey(type, feedback);
        CanonicalMatrixRule rule = fromMarkdownFile(filename, feedbackLabel);
        rules.put(key, rule);
    }

    // Example RuleLoader usage
    public static void registerRules(Map<RuleLoader.RuleKey, DeductionRule> rules) {
        registerRule(rules, RuleLoader.GuessPatternType.TWO_COLOR, new Feedback(0, 0), "Locked_2_color_matrices.md", "(0,0)");
        // Add more rules here...
    }
}
