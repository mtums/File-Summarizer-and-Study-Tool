package cs3500.pa01;

import static cs3500.pa01.Question.getDifficulty;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the class that overwrites files
 */
public class OverwriteFile {

  /**
   * Overwrites a file based on any change to a question's difficulty
   * @param filePath path of the file to be overwritten
   * @param questions the questions that may or may not be changed
   * @throws IOException if the path to the file does not exist
   */
  public static void overwriteFile(String filePath, ArrayList<Question> questions) throws IOException {
    FileWriter fileWriter = new FileWriter(filePath, false);
    int questionNum = 1;
    for (Question question : questions) {
      fileWriter.write(questionNum
          + ". "
          + "[[" + question.questionText
          + ":::" + question.answerText + "]]"
          + "\n" + "Difficulty: "
          + getDifficulty(question)
          + "\n\n");
      questionNum++;
    }
    fileWriter.close();
  }
}
