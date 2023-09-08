package cs3500.pa01;

import static cs3500.pa01.OverwriteFile.overwriteFile;
import static cs3500.pa01.Question.getDifficulty;
import static cs3500.pa01.Question.setEasy;
import static cs3500.pa01.Question.setHard;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a study session "in session." Handles all user input having to do with an active
 * study session
 */
public class HandleStudySession {
  ArrayList<Question> questionBank;
  QuestionBankFile source;

  Readable input;
  Appendable output;
  private int totalQuestionsSeen;
  private int easyToHard;
  private int hardToEasy;

  /**
   * Main constructor for this class. Instantiates the user input and the terminal output into
   * Readable and appendable
   */
  HandleStudySession() {
    this.input = new InputStreamReader(System.in);
    this.output = new OutputStreamWriter(System.out);
  }

  /**
   * Constructor for use in testing
   * @param readable sample input that can be passed in
   * @param appendable output recorded to an Appendable StringBuilder
   */
  HandleStudySession(Readable readable, Appendable appendable) {
    this.input = readable;
    this.output = appendable;
  }

  /**
   * Shows each question to user and handles user input for each question
   * @param filePath path of the file from where questions are being generated
   * @throws IOException if the path to the file does not exist
   */
  public void showQuestions(String filePath) throws IOException {
    Scanner scanner = new Scanner(input);
    for (Question question : questionBank) {
      System.out.println(question.questionText);
      System.out.println("Question listed as: " + getDifficulty(question));
      int input = scanner.nextInt();
      totalQuestionsSeen++;
      if (input == 1) {
        setEasy(question);
        System.out.println("Question has been set to easy\n");
        hardToEasy++;
      } else if (input == 2) {
        setHard(question);
        System.out.println("Question has been set to hard\n");
        easyToHard++;
      } else if (input == 3) {
        System.out.println("The correct answer is: " + question.answerText + "\n");
      } else if (input == 4) {
        break;
      } else {
        System.out.println("Not a valid input. Your session will be restarted");
        showQuestions(filePath);
      }
    }
    System.out.println("Thanks for studying! Here are your stats: ");
    System.out.println("Total number of questions seen: " + calculateTotalNum());
    System.out.println("Number of questions you changed from easy to hard: " + easyToHard());
    System.out.println("Number of questions you changed from hard to easy: " + hardToEasy());
    System.out.println("Updated number of hard questions in the total set: " + calculateNumHard());
    System.out.println("Updated number of easy questions in the total set: " + calculateNumEasy());
    scanner.close();
    overwriteFile(filePath, source.questions);
  }

  /**
   * Calculates the total number of questions seen by the user
   * @return the total number of questions seen by the user
   */
  private int calculateTotalNum() {
    return totalQuestionsSeen;
  }

  /**
   * Calculates the total number of questions changed from easy to hard by the user
   * @return the total number of questions changed from easy to hard by the user
   */
  private int easyToHard() {
    return easyToHard;
  }

  /**
   * Calculates the total number of questions changed from hard to easy by the user
   * @return the total number of questions changed from hard to easy by the user
   */
  private int hardToEasy() {
    return hardToEasy;
  }

  /**
   * Calculates the total number of hard questions in the question bank after the session is complete
   * @return the total number of hard questions in the question bank after the session is complete
   */
  public int calculateNumHard() {
    ArrayList<Question> questionsList = source.questions;
    int numHard = 0;
    for (Question question : questionsList) {
      if (question.isHard) {
        numHard++;
      }
    }
    return numHard;
  }

  /**
   * Calculates the total number of easy questions in the question bank after the session is complete
   * @return the total number of easy questions in the question bank after the session is complete
   */
  public int calculateNumEasy() {
    ArrayList<Question> questionsList = source.questions;
    int numEasy = 0;
    for (Question question : questionsList) {
      if (!question.isHard) {
        numEasy++;
      }
    }
    return numEasy;
  }
}
