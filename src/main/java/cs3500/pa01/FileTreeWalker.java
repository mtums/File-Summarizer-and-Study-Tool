package cs3500.pa01;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Represents a FileTreeWalker
 */
public class FileTreeWalker implements FileVisitor<Path> {
  private ArrayList<File> sumFileList;
  private ArrayList<QuestionBankFile> quesionFileList;

  /**
   * Instantiates an instance of a FileTreeWalker
   * @param list the list of files to be visited
   */

  FileTreeWalker(ArrayList<File> sumFileList, ArrayList<QuestionBankFile> quesionFileList) {
    this.sumFileList = sumFileList;
    this.quesionFileList = quesionFileList;
  }

  /**
   * gets the list being used for a FileTreeWalker
   * @return the list being used by the FileTreeWalker
   */

  public ArrayList getSumFileList() {
    return sumFileList;
  }

  public ArrayList getQuestionFileList() {
    return quesionFileList;
  }

  /**
   * Determines what occurs once the FileVisitor accesses a file. If the file is
   * a .md file,it calls the summarize method on it and adds it to the
   * FileTreeWalker's list.
   *
   * @param file  a reference to the file
   * @param attrs the file's basic attributes
   * @return CONTINUE to the next file
   * @throws IOException if the file exists but is a directory rather than a
   * regular file does not exist but cannot be created, or cannot be opened
   * for any other reason.
   */
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    if (file.getFileName().toString().endsWith(".md")) {
      Scanner scanner = new Scanner(file.toFile());

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        File newSumFile = new File(file.getFileName().toString(),
            attrs.creationTime(),
            attrs.lastModifiedTime(),
            line.toString());

        File newQFile = new File(file.getFileName().toString(),
            attrs.creationTime(),
            attrs.lastModifiedTime(),
            line.toString());

        sumFileList.add(newSumFile.summarize());
        quesionFileList.add(newQFile.extractQuestions());
      }
    }

    return CONTINUE;
  }



  /**
   * The result of the FileVisitor on the file being visited
   * @param file a reference to the file
   * @param exc  the I/O exception that prevented the file from being visited
   * @return CONTINUE onto the next file
   * @throws IOException if the file exists but is a directory rather than a
   *    * regular file does not exist but cannot be created, or cannot be opened
   *    * for any other reason.
   */

  /**
   * Called if the file being visited cannot be visited for whatever reason
   *
   * @param file a reference to the file
   * @param exc the I/O exception that prevented the file from being visited
   *
   * @return CONTINUE onto the next file.
   * @throws IOException if the file exists but is a directory rather than a
   * regular file does not exist but cannot be created, or cannot be opened
   * for any other reason.
   */
  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    return CONTINUE;
  }

  /**
   * Called before visiting the file
   *
   * @param dir   a reference to the directory
   * @param attrs the directory's basic attributes
   * @return CONTINUE onto the next file
   * @throws IOException if the file exists but is a directory rather than a
   * regular file does not exist but cannot be created, or cannot be opened
   * for any other reason.
   */
  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    return CONTINUE;
  }

  /**
   * Called after visiting the file
   *
   * @param dir   a reference to the directory
   * @param exc the directory's basic attributes
   * @return CONTINUE onto the next file
   * @throws IOException if the file exists but is a directory rather than a
   * regular file does not exist but cannot be created, or cannot be opened
   * for any other reason.
   */
  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
    return CONTINUE;
  }
}

