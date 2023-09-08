package cs3500.pa01;

/**
 * Represents a question
 */
public class Question {
  String questionText;
  String answerText;
  boolean isHard;

  /**
   * Represents an instance of the Question class
   *
   * @param questionText the text for the question
   * @param answerText the text for the answer
   * @param isHard the difficulty of the question. True is easy False is hard
   */
  Question(String questionText, String answerText, boolean isHard) {
    this.questionText = questionText;
    this.answerText = answerText;
    this.isHard = isHard;
  }

  /**
   * @return the answer of a question
   */
  public String getAnswer() {
    return answerText;
  }

  /**
   * method for getting the difficulty of a question
   * @param q the question to get the difficulty from
   * @return the difficulty of a given question as a string
   */
  public static String getDifficulty(Question q) {
    if (q.isHard) {
      return "Hard";
    } else {
      return "Easy";
    }
  }

  /**
   * Sets a question to hard
   * @param q question to be set to hard
   */
  public static void setHard(Question q) {
    q.isHard = true;
  }

  /**
   * Sets a question to easy
   * @param q question to be set to easy
   */
  public static void setEasy(Question q) {
    q.isHard = false;
  }
}
