package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.attribute.FileTime;
import org.junit.jupiter.api.Test;

/**
 * Represents a class for testing the three sorting classes
 */
class SortTest {

  /**
   * Tests that the compare method for comparing files by name correctly identifies
   * which file comes first
   */
  @Test
  void compareName() {
    File f1 = new File("AFile", FileTime.fromMillis(1), FileTime.fromMillis(5), "content");
    File f2 = new File("TheFile", FileTime.fromMillis(1), FileTime.fromMillis(5), "content");
    SortByName test = new SortByName();

    assertEquals(-19, test.compare(f1, f2));
    assertEquals(19, test.compare(f2, f1));
    assertEquals(0, test.compare(f1, f1));
    assertEquals(0, test.compare(f2, f2));

  }

  /**
   * Tests that the compare method for comparing files by date created correctly
   * identifies which file comes first
   */
  @Test
  void compareCreated() {
    File f1 = new File("AFile", FileTime.fromMillis(1), FileTime.fromMillis(5), "content");
    File f2 = new File("TheFile", FileTime.fromMillis(3), FileTime.fromMillis(5), "content");
    SortByCreated test = new SortByCreated();

    assertEquals(-1, test.compare(f1, f2));
    assertEquals(1, test.compare(f2, f1));
    assertEquals(0, test.compare(f1, f1));
    assertEquals(0, test.compare(f2, f2));
  }

  /**
   * Tests that the compare method for comparing files by date last modified
   * correctly identifies which file comes first
   */
  @Test
  void compareModified() {
    File f1 = new File("AFile", FileTime.fromMillis(1), FileTime.fromMillis(3), "content");
    File f2 = new File("TheFile", FileTime.fromMillis(3), FileTime.fromMillis(5), "content");
    SortByModified test = new SortByModified();

    assertEquals(-1, test.compare(f1, f2));
    assertEquals(1, test.compare(f2, f1));
    assertEquals(0, test.compare(f1, f1));
    assertEquals(0, test.compare(f2, f2));
  }
}