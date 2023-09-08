package cs3500.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import java.nio.file.FileVisitResult;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import javax.naming.spi.DirStateFactory;
import org.junit.jupiter.api.Test;

/**
 * Represents a class for testing the FileTreeWalker class
 */
class FileTreeWalkerTest {
  File f1 = new File("file1.md",
                FileTime.fromMillis(1),
        FileTime.fromMillis(5), "# Header 1\n"
            + "[[Important info under header 1]]\n"
            + "## Subheader for header 1\n"
            + "[[Important info under subheader]]");

  File file2 = new File("file2.md",
                   FileTime.fromMillis(1),
        FileTime.fromMillis(5),
            "[[message]]\n");

  File file3 = new File("file3.md",
                   FileTime.fromMillis(1),
        FileTime.fromMillis(5),
            "[[open brackets message\n");

  ArrayList<File> fileArrayList = new ArrayList<File>(Arrays.asList(f1, file2, file3));

  /**
   * Tests the getList method by instantiating a FileTreeWalker with a sample list,
   * and ensuring that the FileTreeWalker's list matches the inputted list
   */
  @Test
  void getList() {
    ArrayList<File> list1 = new ArrayList<File>();
    File file4;
    File file5;
    File file6;

    file4 = new File(
        "file1",
        FileTime.fromMillis(1),
        FileTime.fromMillis(3),
        "content");
    file5 = new File(
        "file2",
        FileTime.fromMillis(1),
        FileTime.fromMillis(3),
        "content");
    file6 = new File(
        "file3",
        FileTime.fromMillis(1),
        FileTime.fromMillis(3),
        "content");

    list1.add(file4);
    list1.add(file5);
    list1.add(file6);
    FileTreeWalker test = new FileTreeWalker(list1, new ArrayList<>());

    assertEquals(list1, test.getSumFileList());
  }

  /**
   * Ensures that the preVisitDirectory method continues onto the next file when it
   * needs to
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  void preVisitDirectory() throws IOException {
    FileTreeWalker ftwTest = new FileTreeWalker(fileArrayList, new ArrayList<>());

    BasicFileAttributes attributes = mock(BasicFileAttributes.class);
    FileVisitResult result = ftwTest.preVisitDirectory(Path.of("/Examples"), attributes);
    assertEquals(CONTINUE, result);
  }

  /**
   * Ensures that the visitFile method continues onto the next file when it
   * needs to
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  void testVisitFileContinue() throws IOException {
    FileTreeWalker ftwTest = new FileTreeWalker(fileArrayList, new ArrayList<>());

    BasicFileAttributes attributes = mock(BasicFileAttributes.class);
    FileVisitResult result = ftwTest.visitFile(Path.of("/Examples"), attributes);
    assertEquals(CONTINUE, result);
  }

  /**
   * Tests that visitFile is filtering only .md files and adding them to a list
   * by checking the number of lines in the list after calling visitFile
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  void testVisitFileContent() throws IOException {
    ArrayList<File> mtLoF = new ArrayList<>();
    FileTreeWalker ftwTest = new FileTreeWalker(mtLoF, new ArrayList<>());

    //there is nothing in the list of files
    assertEquals(0, ftwTest.getSumFileList().size());

    BasicFileAttributes attributes = mock(BasicFileAttributes.class);

    ftwTest.visitFile(Path.of(
        "src/test/resources/sampleFiles/arrays.md"),
        attributes);
    //22 is the number of lines from the arrays.md file, which is what is added into the list
    //of files created by the visitFile method, representing that the arrays.md file has been
    //added to the list of files for use in the summary
    assertEquals(22, ftwTest.getSumFileList().size());

    ftwTest.visitFile(Path.of(
        "src/test/resources/sampleFiles/notMD.txt"),
        attributes);
    //the number of lines in the file remains the same because notMD.txt is not a .md file, so
    //it was not added to the list
    assertEquals(22, ftwTest.getSumFileList().size());

    ftwTest.visitFile(Path.of(
        "src/test/resources/sampleFiles/example.md"),
        attributes);
    //the number of lines in the list of files is now 35, because vectors.md has been added to
    //the list
    assertEquals(35, ftwTest.getSumFileList().size());


  }

  /**
   * Ensures that the visitFileFailed method continues onto the next file when it
   * needs to
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  void visitFileFailed() throws IOException {
    FileTreeWalker ftwTest = new FileTreeWalker(fileArrayList, new ArrayList<>());

    IOException exc = mock(IOException.class);
    FileVisitResult result = ftwTest.visitFileFailed(Path.of("/Examples"), exc);
    assertEquals(CONTINUE, result);
  }

  /**
   * Ensures that the postVisitDirectory method continues onto the next file when it
   * needs to
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  void postVisitDirectory() throws IOException {
    FileTreeWalker ftwTest = new FileTreeWalker(fileArrayList, new ArrayList<>());

    IOException exc = mock(IOException.class);
    FileVisitResult result = ftwTest.postVisitDirectory(Path.of("/Examples"), exc);
    assertEquals(CONTINUE, result);
  }
}