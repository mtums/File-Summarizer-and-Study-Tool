package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.attribute.FileTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents a class for testing the File class
 */
class FileTest {
  /**
   * Tests the various cases of what a line that gets summarized might have
   * by running the summarize method on some sample files and ensuring the output
   * matches the desired output
   */
  @Test
  public void testSummarize() {
    File f1 = new File("file1.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5), "# Header 1\n"
        + "[[Important info under header 1]]\n"
        + "## Subheader for header 1\n"
        + "[[Important info under subheader]]");

    File file2 = new File("file2.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "[[message]]\n");

    File file3 = new File("file3.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "[[open brackets message\n");

    File file4 = new File("file4.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "message within brackets w/ only closing brackets]]");

    File file5 = new File("file5.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "string that is within brackets but doesn't have brackets");

    File file6 = new File("file6.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "# First Header for file6.md\n"
    + "- line with no brackets, unimportant phrase\n"
    + "- [[line with one important phrase]]\n"
    + "- line with one [[important phrase embedded]] within the line\n"
    + "- line with [[open brackets\n"
    + "line with closing brackets for prev line]]\n");



    assertEquals("\n# Header 1\n"
    + "- Important info under header 1\n"
    + "\n## Subheader for header 1\n"
    + "- Important info under subheader\n", f1.summarize().contents);

    assertEquals("- message\n", file2.summarize().contents);

    assertEquals("- open brackets message ", file3.summarize().contents);

    assertEquals("message within brackets w/ only closing brackets\n",
        file4.summarize().contents);

    assertEquals("\n# First Header for file6.md\n"
        + "- line with one important phrase\n"
        + "- important phrase embedded\n"
        + "- open brackets line with closing brackets for prev line\n", file6.summarize().contents);
  }

  @Test
  public void testExtractQuestions() {
    File questionsFile1 = new File("test1.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5), "# Header 1\n"
        + "[[Important info under header 1]]\n"
        + "[[Question1:::Answer1]]\n"
        + "## Subheader for header 1\n"
        + "[[Important info under subheader]]\n"
        + "[[Question2:::Answer2]]");

    File questionsFile2 = new File("test2.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "[[Question:::Partial answer\n"
        + "rest of answer]]");

    assertEquals("Question1", questionsFile1.extractQuestions().questions.get(0).questionText);
    assertEquals("Answer1", questionsFile1.extractQuestions().questions.get(0).answerText);
    assertEquals("Question2", questionsFile1.extractQuestions().questions.get(1).questionText);
    assertEquals("Answer2", questionsFile1.extractQuestions().questions.get(1).answerText);

    assertEquals("Question", questionsFile2.extractQuestions().questions.get(0).questionText);
    assertEquals("Partial answer rest of answer", questionsFile2.extractQuestions().questions.get(0).answerText);
  }
}