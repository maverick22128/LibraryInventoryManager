import java.util.LinkedList;
import java.util.NoSuchElementException;
// --== CS400 File Header Information ==--
// Name: Maverick Hedlund
// Email: mjhedlund@wisc.edu
// Team: CA
// TA: Yeping Wang
// Lecturer: Gary Dahl
// Notes to Grader:

/**
 * Class representing a library inventory
 * 
 * @author Maverick Hedlund
 *
 */
public class Library {

  private HashTableMap libBarcode;
  // private HashTableMap libAuthor;

  /**
   * Constructs a library from a given capacity
   * 
   * @param capacity The max capacity of the library
   */
  public Library(int capacity) {
    libBarcode = new HashTableMap(capacity);
    /// libAuthor = new HashTableMap(capacity);
  }

  /**
   * Constructs a library from a given capacity, also adds an array of books to the library
   * 
   * @param capacity the capacity of the library
   * @param books    an array of books to be added to the library
   */
  public Library(int capacity, Book[] books) {
    libBarcode = new HashTableMap(capacity);
    // libAuthor = new HashTableMap(capacity);

    for (int i = 0; i < books.length; i++) {
      libBarcode.put(books[i].getBarcode(), books[i]);

      // LinkedList<Book> bookList = (LinkedList<Book>) libAuthor.get(books[i].getAuthor());
      // bookList.add(books[i]);
      // libAuthor.put(books[i].getAuthor(), bookList);
    }
  }

  /**
   * Allows customers to add or return books
   * 
   * @param book the book object to be returned to the library
   */
  public void returnBook(Book book) {
    libBarcode.put(book.getBarcode(), book);

    /*
     * try { LinkedList<Book> bookList = (LinkedList<Book>) libAuthor.get(book.getAuthor());
     * bookList.add(book); libAuthor.put(book.getAuthor(), bookList); } catch(NoSuchElementException
     * e) { libAuthor.put(book.getAuthor(), book); }
     */
  }

  /**
   * Search the library by a given barcode
   * 
   * @param barcode the ISBN of the book.
   * @return The book with the corresponding barcode
   */
  public Book searchISBN(long barcode) {
    if (!libBarcode.containsKey(barcode)) {
      return null;
    } else {
      Book result = (Book) libBarcode.get(barcode);
      return result;
    }
  }

  /*
   * public LinkedList<Book> searchAuthor(String author){ if (!libAuthor.containsKey(author)) {
   * return null; } else { //LinkedList<Book> result = (LinkedList<Book>) libAuthor.get(author);
   * return result; } }
   */

  /**
   * Returns the size of the library
   * 
   * @return the size
   */
  public int size() {
    return libBarcode.size();
  }

  /**
   * Gets the ISBN of a book in the library
   * 
   * @param book the book being requested
   * @return the barcode
   */
  public long getBarcode(Book book) {
    return book.getBarcode();
  }

  /**
   * Checks a book out by removing it from the library given its barcode.
   * 
   * @param barcode the books barcode that will be checked out
   * @return the book that has been checked out.
   */
  public Book checkoutBook(long barcode) {
    if (!libBarcode.containsKey(barcode)) {
      return null;
    } else {
      Book result = (Book) libBarcode.remove(barcode);
      return result;
    }
  }

}
