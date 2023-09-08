package cs3500.pa01;

import static cs3500.pa01.Question.getDifficulty;
import static cs3500.pa01.Question.setEasy;
import static cs3500.pa01.Question.setHard;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionTest {
  Question q1;
  Question q2;

  @BeforeEach
  void setup() {
    q1 = new Question("Question 1", "Answer 1", true);
    q2 = new Question("Question 2", "Answer 2", false);
  }

  @Test
  void getAnswer() {
    assertEquals("Answer 1", q1.getAnswer());
    assertEquals("Answer 2", q2.getAnswer());
  }

  @Test
  void testGetDifficulty() {
    assertEquals("Hard", getDifficulty(q1));
    assertEquals("Easy", getDifficulty(q2));
  }

  @Test
  void testSetHard() {
    Question easyQ = new Question("Q", "A", false);
    setHard(easyQ);
    assertTrue(easyQ.isHard);
  }

  @Test
  void testSetEasy() {
    Question hardQ = new Question("Q", "A", true);
    setEasy(hardQ);
    assertFalse(hardQ.isHard);
  }
}