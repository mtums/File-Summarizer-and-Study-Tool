package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents a class for testing the FileCreator class
 */
class FileCreatorTest {

  /**
   * Tests before each test that Summary.md
   * does not already exist in the output file.
   */
  @BeforeEach
  public void setup() {
    assertEquals(false, Files.exists(Path.of(
        "/Users/mikatums/Desktop/Northeastern/OOD/pa01-mtums/Examples/Summary.md")));
  }

  /**
   * Ensures that after makeSummary is called on a sample list of files,
   * that a Summary.md file exists in the supplied output directory
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  void testMakeSummary1() throws IOException {

    File f1;
    File file2;
    File file3;

    ArrayList<File> fileArrayList;

    f1 = new File("file1.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5), "# Header 1\n"
        + "[[Important info under header 1]]\n"
        + "## Subheader for header 1\n"
        + "[[Important info under subheader]]");

    file2 = new File("file2.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "[[message]]\n");

    file3 = new File("file3.md",
        FileTime.fromMillis(1),
        FileTime.fromMillis(5),
        "[[open brackets message\n");

    fileArrayList = new ArrayList<>();
    fileArrayList.add(f1);
    fileArrayList.add(file2);
    fileArrayList.add(file3);

    FileCreator testSummary = new FileCreator("Summary.md");
    testSummary.makeSummary(
        fileArrayList,
        "src/test/resources/sampleFiles");

    assertEquals(true,
        Files.exists(
            Path.of(
                "src/test/resources/sampleFiles/Summary.md")));

    Files.deleteIfExists(Path.of("src/test/resources/sampleFiles/Summary.md"));

  }

  /**
   * Ensures a file of questions is created in the given path upon calling makeQuestionSet
   * @throws IOException if the path given does not exist
   */
  @Test
  void testMakeQuestionSet() throws IOException {
    ArrayList<Question> expectedQuestions1 = new ArrayList<>();
    Question q1 = new Question("What color is the sky?", "Blue", true);
    Question q2 = new Question("What color is grass?", "Green", true);
    Question q3 = new Question("What color is wood?", "Brown", false);
    expectedQuestions1.add(q1);
    expectedQuestions1.add(q2);
    expectedQuestions1.add(q3);
    QuestionBankFile qbf1 = new QuestionBankFile("qbf1", expectedQuestions1);

    ArrayList<Question> expectedQuestions2 = new ArrayList<>();
    Question q4 = new Question("What color is the night?", "Black", true);
    Question q5 = new Question("What color is the sun?", "Yellow", true);
    Question q6 = new Question("What color is water?", "Clear", false);
    expectedQuestions2.add(q4);
    expectedQuestions2.add(q5);
    expectedQuestions2.add(q6);
    QuestionBankFile qbf2 = new QuestionBankFile("qbf2", expectedQuestions2);

    ArrayList<Question> expectedQuestions3 = new ArrayList<>();
    Question q7 = new Question("What color is the the coca cola logo?", "Red", true);
    Question q8 = new Question("What color is a sunset?", "Orange", true);
    Question q9 = new Question("What color is the white house?", "White", false);
    expectedQuestions3.add(q7);
    expectedQuestions3.add(q8);
    expectedQuestions3.add(q9);
    QuestionBankFile qbf3 = new QuestionBankFile("qbf3", expectedQuestions3);

    ArrayList<QuestionBankFile> listOfQFiles = new ArrayList<>();
    listOfQFiles.add(qbf1);
    listOfQFiles.add(qbf2);
    listOfQFiles.add(qbf3);

    FileCreator testQuestionSet = new FileCreator("Summary.sr");
    testQuestionSet.makeQuestionSet(listOfQFiles, "src/test/resources/sampleFiles");

    assertTrue(Files.exists(Path.of("src/test/resources/sampleFiles/Summary.sr")));
    Files.deleteIfExists(Path.of("src/test/resources/sampleFiles/Summary.sr"));
  }

}