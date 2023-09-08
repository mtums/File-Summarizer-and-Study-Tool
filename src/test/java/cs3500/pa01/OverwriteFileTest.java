package cs3500.pa01;

import static cs3500.pa01.OverwriteFile.overwriteFile;
import static cs3500.pa01.Question.setEasy;
import static cs3500.pa01.Question.setHard;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class OverwriteFileTest {

  /**
   * Tests that a given file is overwritten after the difficulties of its questions are changed
   * @throws IOException
   */
  @Test
  void testOverwriteFile() throws IOException {
    ArrayList<Question> expectedQuestions = new ArrayList<>();
    Question q1 = new Question("What color is the sky?", "Blue", true);
    Question q2 = new Question("What color is grass?", "Green", true);
    Question q3 = new Question("What color is wood?", "Brown", false);
    expectedQuestions.add(q1);
    expectedQuestions.add(q2);
    expectedQuestions.add(q3);
    Path testPath = Path.of("src/test/resources/sampleFiles/testSetMutable.sr");
    assertTrue(q1.isHard);
    assertTrue(q2.isHard);
    assertFalse(q3.isHard);

    setEasy(q1);
    setEasy(q2);
    setHard(q3);
    overwriteFile(testPath.toString(), expectedQuestions);
    String testFileContentAfter = Files.readString(testPath);

    assertEquals("1. [[What color is the sky?:::Blue]]\n"
        + "Difficulty: Easy\n"
        + "\n"
        + "2. [[What color is grass?:::Green]]\n"
        + "Difficulty: Easy\n"
        + "\n"
        + "3. [[What color is wood?:::Brown]]\n"
        + "Difficulty: Hard\n\n", testFileContentAfter);
    setHard(q1);
    setHard(q2);
    setEasy(q3);

  }
}