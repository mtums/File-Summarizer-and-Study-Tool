package cs3500.pa01;

import java.util.Comparator;

/**
 * Represents a class for a comparator for sorting files by name
 */
public class SortByName implements Comparator<File> {

  /**
   * Compares one file to another file based on their name and returns
   * a negative number if the first file comes before the second, a positive
   * number if the second file comes before the first, and 0 if given the same
   * file to compare
   *
   * @param f1 the first file to be compared.
   * @param f2 the second file to be compared.
   * @return - number if f1 comes before f2, + number if f2 comes before f1, 0 if
   * files are the same
   */
  @Override
  public int compare(File f1, File f2) {
    return f1.fileName.compareTo(f2.fileName);
  }
}
