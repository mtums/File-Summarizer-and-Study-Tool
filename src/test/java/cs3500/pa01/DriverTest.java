package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/**
 * Represents a class for testing the Driver class
 */
class DriverTest {

  /**
   * Fake test provided by OOD overlords
   */
  @Test
  public void fakeTest() {
    assertEquals(5, 5);
  }

  /**
   * This test tests that the main method works correctly when given "name" as a
   * command line argument for how to sort the files in the given directory.
   * Does this by running Driver.main and checking that the contents of the output
   * match what the output is supposed to be
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  public void testMainByName() throws IOException {
    String[] args = {"src/test/resources/sampleFiles",
        "name",
        "src/test/resources/sampleFiles"};

    Driver.main(args);

    String summaryContents = Files.readString(
        Path.of("src/test/resources/sampleFiles/Summary.md"));

    String expectedSummaryContents = "\n"
        + "# Java Arrays\n"
        + "- An **array** is a collection of variables of the same type\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "  actually been created yet\n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- General form:  arrayName = new type[numberOfElements];\n"
        + "- numberOfElements must be a positive Integer.\n"
        + "- Gotcha: Array size is not   modifiable once instantiated.\n"
        + "\n"
        + "# Header 1\n"
        + "- Important info for header 1\n"
        + "\n"
        + "## Subheader 1\n"
        + "- Important info for subheader 1\n"
        + "- Important info for subheader 1 that spans two lines\n"
        + "\n"
        + "### Subheader 2\n"
        + "- bullet in subheader 2\n"
        + "\n"
        + "# Header 3\n"
        + "- Important info for header 3 that  spans multiple lines\n"
        + "- Important info\n"
        + "\n"
        + "# This file is to test if the program is sorting by time created\n"
        + "- If this file appears first in the list of files then I'm doing it right\n"
        + "\n"
        + "# Vectors\n"
        + "- Vectors act like resizable arrays\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- General Form: Vector<type> v = new Vector();\n"
        + "- type needs to be a valid reference type\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- v.add(object of type);\n";

    assertEquals(expectedSummaryContents, summaryContents);
    Files.deleteIfExists(Path.of("src/test/resources/sampleFiles/Summary.md"));
  }

  /**
   * This test tests that the main method works correctly when given "created" as a
   * command line argument for how to sort the files in the given directory.
   * Does this by running Driver.main and checking that the contents of the output
   * match what the output is supposed to be
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  public void testMainByCreated() throws IOException {

    BasicFileAttributeView attr1 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/arrays.md"),
            BasicFileAttributeView.class);

    BasicFileAttributeView attr2 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/vectors.md"),
            BasicFileAttributeView.class);

    BasicFileAttributeView attr3 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/example.md"),
            BasicFileAttributeView.class);

    BasicFileAttributeView attr4 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/example2.md"),
            BasicFileAttributeView.class);

    FileTime knownTime1 = FileTime.from(Instant.parse("2023-05-16T12:00:00Z"));
    FileTime knownTime2 = FileTime.from(Instant.parse("2023-05-16T12:15:00Z"));
    FileTime knownTime3 = FileTime.from(Instant.parse("2023-05-16T12:30:00Z"));
    FileTime knownTime4 = FileTime.from(Instant.parse("2023-05-16T12:45:00Z"));

    attr1.setTimes(knownTime1, knownTime1, knownTime1);
    attr2.setTimes(knownTime2, knownTime2, knownTime2);
    attr3.setTimes(knownTime3, knownTime3, knownTime3);
    attr4.setTimes(knownTime4, knownTime4, knownTime4);

    String[] args = {
        "src/test/resources/sampleFiles",
        "created",
        "src/test/resources/sampleFiles"};

    Driver.main(args);

    String summaryContents = Files.readString(
        Path.of("src/test/resources/sampleFiles/Summary.md"));

    String expectedSummaryContents = "\n"
        + "# Java Arrays\n"
        + "- An **array** is a collection of variables of the same type\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "  actually been created yet\n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- General form:  arrayName = new type[numberOfElements];\n"
        + "- numberOfElements must be a positive Integer.\n"
        + "- Gotcha: Array size is not   modifiable once instantiated.\n"
        + "\n"
        + "# Vectors\n"
        + "- Vectors act like resizable arrays\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- General Form: Vector<type> v = new Vector();\n"
        + "- type needs to be a valid reference type\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- v.add(object of type);\n"
        + "\n"
        + "# Header 1\n"
        + "- Important info for header 1\n"
        + "\n"
        + "## Subheader 1\n"
        + "- Important info for subheader 1\n"
        + "- Important info for subheader 1 that spans two lines\n"
        + "\n"
        + "### Subheader 2\n"
        + "- bullet in subheader 2\n"
        + "\n"
        + "# Header 3\n"
        + "- Important info for header 3 that  spans multiple lines\n"
        + "- Important info\n"
        + "\n"
        + "# This file is to test if the program is sorting by time created\n"
        + "- If this file appears first in the list of files then I'm doing it right\n";

    assertEquals(expectedSummaryContents, summaryContents);
    Files.deleteIfExists(Path.of("src/test/resources/sampleFiles/Summary.md"));
  }

  /**
   * This test tests that the main method works correctly when given "modified" as
   * a command line argument for how to sort the files in the given directory.
   * Does this by running Driver.main and checking that the contents of the output
   * match what the output is supposed to be
   *
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Test
  public void testMainByModified() throws IOException {
    BasicFileAttributeView attr1 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/vectors.md"),
            BasicFileAttributeView.class);

    BasicFileAttributeView attr2 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/example.md"),
            BasicFileAttributeView.class);

    BasicFileAttributeView attr3 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/example2.md"),
            BasicFileAttributeView.class);

    BasicFileAttributeView attr4 =
        Files.getFileAttributeView(
            Path.of("src/test/resources/sampleFiles/arrays.md"),
            BasicFileAttributeView.class);

    FileTime knownTime1 = FileTime.from(Instant.parse("2023-05-16T12:00:00Z"));
    FileTime knownTime2 = FileTime.from(Instant.parse("2023-05-16T12:15:00Z"));
    FileTime knownTime3 = FileTime.from(Instant.parse("2023-05-16T12:30:00Z"));
    FileTime knownTime4 = FileTime.from(Instant.parse("2023-05-16T12:45:00Z"));

    attr1.setTimes(knownTime1, knownTime1, knownTime1);
    attr2.setTimes(knownTime2, knownTime2, knownTime2);
    attr3.setTimes(knownTime3, knownTime3, knownTime3);
    attr4.setTimes(knownTime4, knownTime4, knownTime4);

    String[] args = {
        "src/test/resources/sampleFiles",
        "modified",
        "src/test/resources/sampleFiles"};

    Driver.main(args);

    String summaryContents = Files.readString(
        Path.of("src/test/resources/sampleFiles/Summary.md"));

    String expectedSummaryContents = "\n"
        + "# Vectors\n"
        + "- Vectors act like resizable arrays\n"
        + "\n"
        + "## Declaring a vector\n"
        + "- General Form: Vector<type> v = new Vector();\n"
        + "- type needs to be a valid reference type\n"
        + "\n"
        + "## Adding an element to a vector\n"
        + "- v.add(object of type);\n"
        + "\n"
        + "# Header 1\n"
        + "- Important info for header 1\n"
        + "\n"
        + "## Subheader 1\n"
        + "- Important info for subheader 1\n"
        + "- Important info for subheader 1 that spans two lines\n"
        + "\n"
        + "### Subheader 2\n"
        + "- bullet in subheader 2\n"
        + "\n"
        + "# Header 3\n"
        + "- Important info for header 3 that  spans multiple lines\n"
        + "- Important info\n"
        + "\n"
        + "# This file is to test if the program is sorting by time created\n"
        + "- If this file appears first in the list of files then I'm doing it right\n"
        + "\n"
        + "# Java Arrays\n"
        + "- An **array** is a collection of variables of the same type\n"
        + "\n"
        + "## Declaring an Array\n"
        + "- General Form: type[] arrayName;\n"
        + "- only creates a reference\n"
        + "  actually been created yet\n"
        + "\n"
        + "## Creating an Array (Instantiation)\n"
        + "- General form:  arrayName = new type[numberOfElements];\n"
        + "- numberOfElements must be a positive Integer.\n"
        + "- Gotcha: Array size is not   modifiable once instantiated.\n";

    assertEquals(expectedSummaryContents, summaryContents);
    Files.deleteIfExists(Path.of("src/test/resources/sampleFiles/Summary.md"));
  }

}