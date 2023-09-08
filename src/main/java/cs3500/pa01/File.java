package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a File
 */
public class File {

  String fileName;
  FileTime dateCreated;
  FileTime dateModified;
  String contents;

  /**
   * Instantiates a File
   *
   * @param fileName the name of the file
   * @param dateCreated the date the file was created
   * @param dateModified the date the file was last modified
   * @param contents the contents of the file
   */
  public File(String fileName, FileTime dateCreated, FileTime dateModified, String contents) {
    this.fileName = fileName;
    this.dateCreated = dateCreated;
    this.dateModified = dateModified;
    this.contents = contents;
  }

  public File(FileTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Reads through a File and summarizes its contents and returns a new file with
   * the summarized content.
   *
   * @return a new File with the summarized content instead of the original content
   */
  public File summarize() {
    StringBuilder summarizedText = new StringBuilder();
    Scanner scanner = new Scanner(contents);
    boolean insideBrackets = false;

    while (scanner.hasNext()) {
      String line = scanner.nextLine();

      if (line.contains("#") && !line.contains("##")) {
        summarizedText.append("\n" + line + "\n");
      }

      if (line.contains("##")) {
        summarizedText.append("\n" + line + "\n");
      }

      if (line.contains("[[") && line.contains("]]") && !line.contains(":::")) {
        int startIdx = line.indexOf("[[") + 2;
        int endIdx = line.indexOf("]]");
        summarizedText.append("- " + line.substring(startIdx, endIdx) + "\n");
        insideBrackets = false;
      }

      else if (line.contains("[[") && !line.contains(":::")) {
        int startIdx = line.indexOf("[[") + 2;
        summarizedText.append("- " + line.substring(startIdx) + " ");
        insideBrackets = true;
      }

      else if (line.contains("]]") && !line.contains(":::")) {
        int endIdx = line.indexOf("]]");
        summarizedText.append(line.substring(0, endIdx) + "\n");
        insideBrackets = false;
      }

    }
    scanner.close();
    return new File(this.fileName, this.dateCreated, this.dateModified, summarizedText.toString());
  }

  /**
   * Extracts the questions from a .md file and makes a QuestionBankFile with those questions.
   * Sets all questions to hard
   * @return A new QuestionBankFile with the questions from the source .md file
   */
  public QuestionBankFile extractQuestions() {
    ArrayList<Question> listOfQuestions = new ArrayList<>();
    Scanner scanner = new Scanner(contents);

    while (scanner.hasNext()) {
      String line = scanner.nextLine();

      if (line.contains("[[") && line.contains(":::") && line.contains("]]")) {
        int startIdx = line.indexOf("[[") + 2;
        int endIdx = line.indexOf("]]");
        int midIdx = line.indexOf(":::");
        String questionText = line.substring(startIdx, midIdx);
        String answerText = line.substring(midIdx + 3, endIdx);
        Question newQ = new Question(questionText, answerText, true);
        listOfQuestions.add(newQ);
      }
      else if (line.contains("[[") && line.contains(":::") && !line.contains("]]")) {
        int startIdx = line.indexOf("[[") + 2;
        int midIdx = line.indexOf(":::");
        String questionText = line.substring(startIdx, midIdx);
        String answerText = line.substring(midIdx + 3);
        StringBuilder endOfQuestionBuilder = new StringBuilder(scanner.nextLine());
        while (!endOfQuestionBuilder.toString().contains("]]")) {
          endOfQuestionBuilder.append(" ").append(scanner.nextLine());
        }
        int endIdx = endOfQuestionBuilder.indexOf("]]");
        String endOfQuestion = endOfQuestionBuilder.substring(0, endIdx);
        Question newQ = new Question(questionText, answerText + " " + endOfQuestion, true);
        listOfQuestions.add(newQ);
      }
    }
    return new QuestionBankFile(this.fileName, listOfQuestions);
  }
}