package cs3500.pa01;

import static cs3500.pa01.Question.getDifficulty;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a FileCreator, which implements FileWriter and can be
 * used to create a new file.
 */
public class FileCreator extends FileWriter {
  /**
   * Instantiates an instance of the FileCreator class
   * @param fileName the name of the file to be created
   * @throws IOException if the name of the file being created already exists
   */
  public FileCreator(String fileName) throws IOException {
    super(fileName);
  }

  /**
   * Creates a single java File based on a list of files and places the resulting file
   * into a directory based on a supplied path.
   *
   * @param files the list of files to be condensed into one file
   * @param sumFilePath the path of the directory where the file will be placed
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  public static void makeSummary(ArrayList<File> files, String sumFilePath) throws IOException {
    FileWriter summaryFileCreator = new FileWriter(new java.io.File(sumFilePath, "Summary.md"));
    for (File file : files) {
      summaryFileCreator.write(file.contents);
    }
    summaryFileCreator.close();
  }

  /**
   * Generates a .sr file of questions given a list of QuestionBankFiles and a path to put the .sr
   * file in
   * @param files Files to generate question set from
   * @param qBankPath location for resulting file
   * @throws IOException if file path does not exist
   */
  public void makeQuestionSet(ArrayList<QuestionBankFile> files, String qBankPath) throws IOException {
    FileWriter questionSetCreator = new FileWriter(new java.io.File(qBankPath, "Summary.sr"));
    int questionNum = 1;
    for (QuestionBankFile questionFile : files) {
      for (Question question : questionFile.questions) {
        questionSetCreator.write(questionNum
            + ". "
            + "[[" + question.questionText
            + ":::" + question.answerText + "]]"
            + "\n" + "Difficulty: "
            + getDifficulty(question)
            + "\n\n");
        questionNum++;
      }
    }
    questionSetCreator.close();
  }
}
