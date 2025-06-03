import java.util.*;
import java.util.regex.*;
import java.nio.file.*;
import java.io.IOException;

public class MarkdownRuleParser {

    public static CanonicalMatrixRule fromMarkdownFile(Path path, String feedbackLabel) throws IOException {
        String content = Files.readString(path);
        return fromMarkdownSection(content, feedbackLabel);
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
}

