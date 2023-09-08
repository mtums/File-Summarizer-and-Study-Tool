package cs3500.pa01;

import static cs3500.pa01.QuestionBankFile.convertToQuestionFile;
import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a class that starts a study session
 */
public class RunStudySession {
  QuestionBankFile source;
  ArrayList<Question> questionBank;
  Readable input;
  Appendable output;

  RunStudySession() {
    this.input = new InputStreamReader(System.in);
    this.output = new OutputStreamWriter(System.out);
  }

  RunStudySession(Readable readable, Appendable appendable) {
    this.input = readable;
    this.output = appendable;
  }

  /**
   * Starts a study session and asks the user to provide a path to a file of questions as well as
   * how many questions the user would like to study
   * @throws IOException if the file provided does not exist
   */
  public void startSession() throws IOException {
    Scanner scanner = new Scanner(input);

    System.out.println(
        "Welcome! Please enter the path (relative or absolute) \n" +
            "to the file that has the questions you would like to study: ");

    String nameOfFile = scanner.nextLine();
    Path pathOfSource = Paths.get(nameOfFile);

    try {
      Path absolutePathOfSource = pathOfSource.toAbsolutePath();
      source = convertToQuestionFile(absolutePathOfSource);
    } catch (NoSuchFileException e) {
      System.out.println("The file does not exist. Please provide a valid file path.");
      scanner.close();
      return;
    }

    System.out.println("How many questions would you like to go over? ");
    String numQuestionsWantedString = scanner.nextLine();
    int numQuestionsWanted = parseInt(numQuestionsWantedString);

    if (numQuestionsWanted > source.questions.size()) {
      System.out.println(
          "DISCLAIMER: " +
              "The number you entered is greater than " +
              "the total number of questions in the file you provided.\n" +
              "All questions in the file will be shown\n");
    } else if (numQuestionsWanted < 0) {
      scanner.close();
      throw new IllegalArgumentException("Number of questions cannot be negative");
    }
    questionBank = source.getQuestions(numQuestionsWanted);
    HandleStudySession session = new HandleStudySession();
    session.source = this.source;
    session.questionBank = this.questionBank;
    session.showQuestions(pathOfSource.toAbsolutePath().toString());
    scanner.close();
  }
}
