package cs3500.pa01;

import static cs3500.pa01.QuestionBankFile.convertToQuestionFile;
import static cs3500.pa01.QuestionBankFile.getDifficultyFromText;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionBankFileTest {
  private QuestionBankFile questionBankFile;
  private ArrayList<Question> questions;

  @BeforeEach
  void setup() {
    questions = new ArrayList<>();
    questions.add(new Question("Question 1 (Easy)", "Answer 1", false));
    questions.add(new Question("Question 2 (Hard)", "Answer 2", true));
    questions.add(new Question("Question 3 (Hard)", "Answer 3", true));
    questions.add(new Question("Question 4 (Hard)", "Answer 4", true));
    questions.add(new Question("Question 5 (Easy)", "Answer 5", false));
    questionBankFile = new QuestionBankFile("TestFile", questions);
  }

  /**
   * tests that upon calling this method, a list of n questions is randomly generated
   */
  //shuffleQuestions is a private method within the QuestionBankFile class.
  //The assertNotEquals lines are testing the shuffling of the questions indirectly
  @Test
  void testGetQuestions() {
    int n1 = 2;
    ArrayList<Question> result1 = questionBankFile.getQuestions(n1);
    assertEquals(n1, result1.size());
    assertEquals(true, result1.get(0).isHard);
    assertEquals(true, result1.get(1).isHard);

    int n2 = 3;
    ArrayList<Question> result2 = questionBankFile.getQuestions(n2);
    assertEquals(n2, result2.size());
    assertEquals(true, result2.get(0).isHard);
    assertEquals(true, result2.get(1).isHard);
    assertEquals(true, result2.get(2).isHard);


    int n3 = 5;
    ArrayList<Question> result3 = questionBankFile.getQuestions(n3);
    assertEquals(n3, result3.size());
    assertEquals(true, result3.get(0).isHard);
    assertEquals(true, result3.get(1).isHard);
    assertEquals(false, result3.get(3).isHard);
    assertEquals(false, result3.get(4).isHard);

    ArrayList<Question> result4 = questionBankFile.getQuestions(5);
    assertEquals(5, result4.size());
    assertNotEquals(questions.get(0), result4.get(0));

    ArrayList<Question> result5 = questionBankFile.getQuestions(6);
    assertEquals(5, result5.size());
  }

  /**
   * asserts that a real file can be accurately converted to a QuestionBankFile
   * @throws IOException if the file does not exist
   */
  @Test
  void testConvertToQuestionFile() throws IOException {
    ArrayList<Question> expectedQuestions = new ArrayList<>();
    expectedQuestions.add(new Question("What color is the sky?", "Blue", true));
    expectedQuestions.add(new Question("What color is grass?", "Green", true));
    expectedQuestions.add(new Question("What color is wood?", "Brown", false));
    QuestionBankFile expectedResult = new QuestionBankFile("testSet.sr", expectedQuestions);

    QuestionBankFile result = convertToQuestionFile(Path.of("src/test/resources/sampleFiles/testSet.sr"));
    assertEquals(expectedResult.fileName, result.fileName);
    assertEquals(expectedQuestions.get(0).questionText, result.questions.get(0).questionText);
    assertEquals(expectedQuestions.get(1).answerText, result.questions.get(1).answerText);
    assertEquals(expectedQuestions.get(2).questionText, result.questions.get(2).questionText);
  }

  /**
   * Asserts that the difficulty of a question can be extracted from a file
   */
  @Test
  void testGetDifficultyFromText() {
    assertTrue(getDifficultyFromText("Difficulty: Hard"));
    assertFalse(getDifficultyFromText("Difficulty: Easy"));
    assertTrue(getDifficultyFromText("Does not contain correct string"));
  }
}