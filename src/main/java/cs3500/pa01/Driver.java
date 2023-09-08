package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 3) {
      String filePathString = args[0];
      Path filePath = Path.of(filePathString);
      ArrayList<File> initFileList = new ArrayList<>();
      FileTreeWalker ftw = new FileTreeWalker(initFileList, new ArrayList<>());
      Files.walkFileTree(filePath, ftw);
      ArrayList<File> fileList = ftw.getSumFileList();

      if (args[1].equalsIgnoreCase("name")) {
        SortByName byName = new SortByName();
        Collections.sort(fileList, byName);
        new FileCreator("Summary.md").makeSummary(
            fileList,
            args[2]);
      } else if (args[1].equalsIgnoreCase("created")) {
        SortByCreated byCreated = new SortByCreated();
        Collections.sort(fileList, byCreated);
        new FileCreator("Summary.md").makeSummary(
            fileList,
            args[2]);
      } else if (args[1].equalsIgnoreCase("modified")) {
        SortByModified byModified = new SortByModified();
        Collections.sort(fileList, byModified);
        new FileCreator("Summary.md").makeSummary(
            fileList,
            args[2]);
      }
      ArrayList<QuestionBankFile> initQuestionList = new ArrayList<>();
      FileTreeWalker ftw2 = new FileTreeWalker(new ArrayList<>(), initQuestionList);
      Files.walkFileTree(filePath, ftw2);
      ArrayList<QuestionBankFile> questionFileList = ftw2.getQuestionFileList();
      new FileCreator("Summary.sr").makeQuestionSet(questionFileList, args[2]);
    }
    else if (args.length == 0) {
      RunStudySession sessionStart = new RunStudySession();
      sessionStart.startSession();
    }
  }

}