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
        String normalizedLabel = feedbackLabel.replaceAll("\\s+", "");
        Map<String, Integer> columnIndices = new HashMap<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.matches("^#{2,3}\\s+Feedback\\s*\\(\\s*\\d\\s*,\\s*\\d\\s*\\)$")) {
                String headerDigits = trimmed.replaceAll("[^0-9,]", "");
                if (headerDigits.equals(normalizedLabel.replaceAll("[^0-9,]", ""))) {
                    inSection = true;
                    continue;
                }
            }
            if (inSection && trimmed.startsWith("## ")) {
                break; // End of section
            }
            if (inSection && columnIndices.isEmpty() && line.toLowerCase().contains("color") && line.toLowerCase().contains("pos")) {
                String[] headers = line.split("\\|");
                for (int i = 0; i < headers.length; i++) {
                    String col = headers[i].trim().toLowerCase();
                    if (col.equals("color")) columnIndices.put("color", i);
                    else if (col.equals("in code?") || col.equals("in code")) columnIndices.put("incode", i);
                    else if (col.startsWith("pos ")) {
                        int posIndex = Integer.parseInt(col.substring(4).trim());
                        columnIndices.put("pos" + posIndex, i);
                    }
                }
                continue;
            }
            if (inSection && line.matches("\\|\\s*[A-Z]\\s*\\|.*")) {
                String[] cells = line.split("\\|");
                if (Collections.max(columnIndices.values()) >= cells.length) continue;

                char letter = cells[columnIndices.get("color")].trim().charAt(0);
                boolean inCode = true;
                if (columnIndices.containsKey("incode")) {
                    String status = cells[columnIndices.get("incode")].trim().toLowerCase();
                    inCode = !(status.equals("no") || status.equals("x"));
                }

                List<Integer> eliminate = new ArrayList<>();
                for (int pos = 0; pos < 4; pos++) {
                    Integer colIdx = columnIndices.get("pos" + pos);
                    if (colIdx != null && colIdx < cells.length && cells[colIdx].trim().equalsIgnoreCase("X")) {
                        eliminate.add(pos);
                    }
                }

                entries.add(new CanonicalMatrixRule.DeductionEntry(letter, inCode, eliminate));
            }
        }

        if (entries.isEmpty()) throw new IllegalArgumentException("No deduction entries found for: " + feedbackLabel);

        return new CanonicalMatrixRule(entries) {
            @Override
            public void apply(Guess guess, Feedback feedback, DeductionState state) {
                Map<Character, Integer> canonicalToActual = new HashMap<>();
                Set<Integer> seen = new HashSet<>();
                char[] canonical = {'A', 'B', 'C', 'D'};
                int index = 0;
                for (int value : guess.values()) {
                    if (!seen.contains(value)) {
                        if (index >= canonical.length) break;
                        canonicalToActual.put(canonical[index++], value);
                        seen.add(value);
                    }
                }

                for (DeductionEntry entry : entries) {
                    Integer actual = canonicalToActual.get(entry.letter);
                    if (actual == null) continue;
                    state.setColorInCode(actual, entry.inCode);
                    for (int pos : entry.eliminatePositions) {
                        state.eliminatePosition(actual, pos);
                    }
                }
            }
        };
    }

    // Integration for RuleLoader usage
    public static void registerRule(Map<RuleLoader.RuleKey, DeductionRule> rules,
                                    RuleLoader.GuessPatternType type, Feedback feedback,
                                    String filename, String feedbackLabel) {
        RuleLoader.RuleKey key = new RuleLoader.RuleKey(type, feedback);
        CanonicalMatrixRule rule = fromMarkdownFile(filename, feedbackLabel);
        rules.put(key, rule);
    }

    // Delegated usage in RuleLoader
    public static void registerRulesInto(Map<RuleLoader.RuleKey, DeductionRule> rules) {
        registerRule(rules, RuleLoader.GuessPatternType.TWO_COLOR, new Feedback(0, 0), "Locked_2_color_matrices.md", "(0,0)");
        // Add more rules here...
    }
}
