package cs3500.pa01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a file for questions
 */
public class QuestionBankFile {
  String fileName;
  ArrayList<Question> questions;

  /**
   * Constructor for a QuestionBankFile
   * @param fileName name of the file
   * @param questions questions in the file
   */
  QuestionBankFile(String fileName, ArrayList<Question> questions) {

    this.fileName = fileName;
    this.questions = questions;
  }

  /**
   * Gathers questions from this file and first sorts them into easy and hard, then gives all
   * questions to the study session
   * @param n number of questions to be generated
   * @return an array list of questions, where the size of the list is n. If n < the number of
   * hard questions in the set, then the set will only contain hard questions
   */
  public ArrayList<Question> getQuestions(int n) {
    ArrayList<Question> questionBank = new ArrayList<Question>();
    ArrayList<Question> hardQuestions = new ArrayList<Question>();
    ArrayList<Question> easyQuestions = new ArrayList<Question>();

    for (Question question : questions) {
      if (question.isHard) {
        hardQuestions.add(question);
      } else {
        easyQuestions.add(question);
      }
    }

    if (n > questions.size()) {
      n = questions.size();
    }

    shuffleQuestions(hardQuestions);
    shuffleQuestions(easyQuestions);

    int hardCount = Math.min(n, hardQuestions.size());

    for (int i = 0; i < hardCount; i++) {
      questionBank.add(hardQuestions.get(i));
    }

    if (hardCount < n && easyQuestions.size() > 0) {
      int remaining = n - hardCount;
      int easyCount = Math.min(remaining, easyQuestions.size());
      for (int i = 0; i < easyCount; i++) {
        questionBank.add(easyQuestions.get(i));
      }
    }
    return questionBank;
  }

  /**
   * Randomizes a list of questions
   * @param listOfQuestions questions to be randomized
   */
  private void shuffleQuestions(ArrayList<Question> listOfQuestions) {
    Random random = new Random();
    for (int i = listOfQuestions.size() - 1; i > 0; i--) {
      int randInt = random.nextInt(i + 1);
      Question temp = listOfQuestions.get(i);
      listOfQuestions.set(i, listOfQuestions.get(randInt));
      listOfQuestions.set(randInt, temp);
    }
  }

  /**
   * Converts a real file into a QuestionBankFile
   * @param filePath path of file to be converted
   * @return QuestionBankFile with the contents of the real file
   * @throws IOException if the file doesn't exist
   */
  public static QuestionBankFile convertToQuestionFile(Path filePath) throws IOException {
    Scanner scanner = new Scanner(filePath);
    String fileName = filePath.getFileName().toString();
    ArrayList<Question> newQuestions = new ArrayList<>();

    while (scanner.hasNext()) {
      String line = scanner.nextLine();

      if (line.contains("[[") && line.contains(":::") && line.contains("]]")) {
        int startIdx = line.indexOf("[[") + 2;
        int endIdx = line.indexOf("]]");
        int midIdx = line.indexOf(":::");
        boolean difficulty = getDifficultyFromText(scanner.nextLine());
        String questionText = line.substring(startIdx, midIdx);
        String answerText = line.substring(midIdx + 3, endIdx);
        Question newQ = new Question(questionText, answerText, difficulty);
        newQuestions.add(newQ);
      }
    }
    return new QuestionBankFile(fileName, newQuestions);
  }

  /**
   * Gets the difficulty from a real file's content
   * @param text supplied text to check for difficulty
   * @return true if the difficulty is hard, false if easy
   */
  public static boolean getDifficultyFromText(String text) {
    if (text.contains("Difficulty: Hard")) {
      return true;
    } else if (text.contains("Difficulty: Easy")) {
      return false;
    }
    return true;
  }
}
