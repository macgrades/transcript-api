package Courses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeFinder {
    private final Pattern coursePattern = Pattern.compile("[[A-Z]{3,} ]+[0-9][A-Z0-9]{2}[0-9][A|B]*|[[A-Z]{2,} ]+[A-Z]*[0-9]{3,}[A|B]*");

    private Matcher matcher;
    private final String line;
    public CodeFinder(String line) {
        this.line = line;
    }

    public boolean containsCourse() {
        matcher = coursePattern.matcher(line);
        return matcher.find();
    }

    public String getCourse() {
        return line.substring(matcher.start(), matcher.end()).trim();
    }
}
