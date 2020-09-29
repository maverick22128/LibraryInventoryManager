import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

// --== CS400 File Header Information ==--
// Name: Neil Walsh
// Email: ntwalsh@wisc.edu
// Team: CA
// TA: Yeping Wang
// Role: Data Wrangler
// Lecturer: Florian Heimerl
// Notes to Grader:

/**
 * Makes a Book object using data from a random real book
 * 
 * @author Neil Walsh
 *
 */
public class Book {

  // these fields are accessible with getter methods, and will all be set to a random real book and
  // its real data
  private String title;
  private String author;
  private boolean isCheckedIn; // All new books will be initiated as true (checked in)
  private double averageRating; // Average rating on the GoodReads website
  private int numRatings; // Number of ratings on the website
  private int numPages; // Number of pages in the book
  private long barcode; // isbn 13 standard barcode
  private static Long[] usedBarcodes = new Long[10000000]; // Stores all barcodes created in an
                                                           // oversized array in order to check
                                                           // against creating duplicate barcodes
  private static int usedBarcodesSize = 0; // Number of barcodes in the array

  private File dataset; // Comma seperated values list of books and their respective data fields
  private Random rand = new Random(); // used to find a random book
  private Scanner dataScanner; // Scans the dataset file for information.


  /**
   * Constructor method to initialize instance of Book.java to a random real book found in our
   * dataset. isCheckedIn will initialized as true.
   * 
   */
  public Book() {
    try {
      dataset = new File(".//books.csv");
    } catch (NullPointerException ex) {
      System.out.println("Error: Dataset file could not be found by the File() constructor");
      // If you're getting this error try the steps listed below and make sure the file is in the
      // same package as this class. This should basically never be thrown but if it is you know the
      // problem isn't with the scanner

    }
    try {
      dataScanner = new Scanner(dataset);
    } catch (FileNotFoundException e) {
      System.out.println("Error: Dataset file cannot be found by the scanner");
      // if you're getting this error make sure that the dataset file
      // is in the same folder as this program. Make sure the title matches the string passed to
      // FIle() and make sure that if you move books.csv for whatever reason you update the path
      // string passed to FIle().

    }

    for (int i = 0; i < 1 + rand.nextInt(11127); i++) { // 11128 is the number of entries in our
                                                        // data sheet. This skips to a random
                                                        // line and then reads the next line in as
                                                        // this books data. The first line is
                                                        // skipped because it is just the column
      //System.out.println(i + " - thsi is the value of i before nextline runs"); // headings
      dataScanner.nextLine();
    }

    String dataLine = dataScanner.nextLine();
    String[] bookData = dataLine.split(","); // Makes an array of all the comma seperated data
                                             // points for the book
    try {
      title = bookData[1];
      author = bookData[2];
      averageRating = Double.parseDouble(bookData[3]);
      barcode = Long.parseLong(bookData[5]);
      numPages = Integer.parseInt(bookData[7]);
      numRatings = Integer.parseInt(bookData[8]);
      isCheckedIn = true;
    } catch (NullPointerException e) {
      System.out.println("Error: one or more data fields for this book are null");
    }

    dataScanner.close();
  }

  /**
   * Constructor method to initialize instance of Book.java when values for all fields are passed to
   * the constructor
   * 
   * @param title
   * @param author
   */
  public Book(String title, String author, int numPages, long barcode, double averageRating,
      int numRatings, boolean isCheckedIn) {
    this.title = title;
    this.author = author;
    this.barcode = barcode;
    this.numPages = numPages;
    this.isCheckedIn = isCheckedIn;
    this.averageRating = averageRating;
    this.numRatings = numRatings;

    usedBarcodes[usedBarcodesSize] = barcode; // This makes sure when we make a random barcode for a
                                              // new book it won't have the same barcode as this
                                              // book
    usedBarcodesSize++;
  }

  /**
   * 
   * Constructor method to initialize instance of Book.java in a more realistic situation where the
   * title,author,page count, average rating and number of ratings are known, but a random barcode
   * still needs to be generated.
   * 
   * @param title         title of book
   * @param author        author of book
   * @param numPages      number of pages in the book
   * @param averageRating average rating on the GoodReads website
   * @param numRatings    number of ratings on the GoodReads website
   */
  public Book(String title, String author, int numPages, double averageRating, int numRatings) {
    this(title, author, numPages, randomBarcode(), averageRating, numRatings, true);
  }

  /**
   * Constructor method to initialize instance of Book.java using a title and author of an existing
   * book. The constructor will search for the book with that title and author and complete the
   * other fields. If no book by that name can be found, the other fields will fill generically
   * after displaying an error message.
   * 
   * @param title  of book
   * @param author of book
   */
  public Book(String title, String author) {

    // this for loop searches the dataset for a match to the title and author of a book. The search
    // function is set to ignore case but beyond that it is unforgiving. Once a match is found it
    // creates a new book with that information
    for (int i = 0; i < 1 + rand.nextInt(11127); i++) { // 11128 is the number of entries in our
                                                        // dataset

      String temp = dataScanner.nextLine();
      String[] bookData = temp.split(",");
      if (bookData[1].equalsIgnoreCase(title) && bookData[2].equalsIgnoreCase(author)) {
        try {
          this.title = bookData[1];
          this.author = bookData[2];
          this.averageRating = Double.parseDouble(bookData[3]);
          this.barcode = Long.parseLong(bookData[5]);
          this.numPages = Integer.parseInt(bookData[7]);
          this.numRatings = Integer.parseInt(bookData[8]);
          this.isCheckedIn = true;
        } catch (NullPointerException e) {
          System.out.println("Error: one or more data fields for this book are null");
        }
        return;
      }
    } // end for loop

    // Fills in with generic information
    System.out.println("We're sorry, we don't have any more information about that book");
    this.title = title;
    this.author = author;
    this.averageRating = 0.0;
    this.numRatings = 0;
    this.isCheckedIn = true;
    this.numPages = 0;
    this.barcode = randomBarcode();

    usedBarcodes[usedBarcodesSize] = barcode;
    usedBarcodesSize++;

  }

  /**
   * Generates a random 14 digit barcode for a situation where a new book is being added that does
   * not have a match in the dataset. The barcode is 14 digits to prevent against accidental
   * retroactive conflicts with barcodes from the dataset.
   * 
   * @return random 14 digit barcode
   */
  private static long randomBarcode() {
    long randomBarcode = (long) (Math.random() * 99999999999999L);

    if (randomBarcode < 10000000000000L)
      randomBarcode = randomBarcode * 10;


    for (int i = 0; i < usedBarcodesSize; i++) {
      if (randomBarcode == usedBarcodes[i]) {
        randomBarcode = randomBarcode(); // if the barcode is already found in the array it runs
                                         // this method again to get a new number
      }
    }
    return randomBarcode;
  }

  /**
   * Returns the important data about this book in a string
   * 
   * @return string representation of data from this book object
   */
  @Override
  public String toString() {
    return "Title: " + title + "\nAuthor: " + author + "\nRating: " + averageRating + " over "
        + numRatings + " ratings" + "\nPages: " + numPages + "\nIs the book checked in: "
        + isCheckedIn + "\nBarcode: " + barcode + "\n";
  }



  /**
   * Getter method to return title
   * 
   * @return the title
   */
  public String getTitle() {
    return title;
  }



  /**
   * Getter method to return author
   * 
   * @return the author
   */
  public String getAuthor() {
    return author;
  }



  /**
   * Getter method to return isCheckedIn
   * 
   * @return the isCheckedIn
   */
  public boolean isCheckedIn() {
    return isCheckedIn;
  }



  /**
   * Getter method to return averageRating
   * 
   * @return the averageRating
   */
  public double getAverageRating() {
    return averageRating;
  }



  /**
   * Getter method to return numRatings
   * 
   * @return the numRatings
   */
  public int getNumRatings() {
    return numRatings;
  }



  /**
   * Getter method to return numPages
   * 
   * @return the numPages
   */
  public int getNumPages() {
    return numPages;
  }

  /**
   * Getter method to return barcode
   * 
   * @return the barcode
   */
  public long getBarcode() {
    return barcode;
  }



  // Test method, delete once confirmed that it works
  public static void main(String[] args) {
    

  }

}
