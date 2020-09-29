import java.util.NoSuchElementException;

//--== CS400 File Header Information ==--
//Name: Ethan Theiste
//Email: etheiste@wisc.edu
//Team: CA
//Role: Test Engineer 1
//TA: Yeping Wang
//Lecturer: Gary Dahl
//Notes to Grader:

/**
 * Contains test methods for Book and Library classes
 * 
 * @author Ethan
 *
 */
public class LibraryTester {
  
  public static void main (String[] args) {
    runTests();
  }
  
  /**
   * Runs all tests and prints the results
   */
  public static void runTests() {
    System.out.println("testBook(): " + testBook());
    System.out.println("testNewBook(): " + testNewBook());
    System.out.println("testLibraryConstructors(): " + testLibraryConstructors());
    System.out.println("testLibraryCheckoutReturn(): " + testLibraryCheckoutReturn());
  }
  
  /**
   * Tests the Book class for correct construction and method behavior
   */
  public static boolean testBook() {
    // create a new book and fill in information
    Book book1 = new Book();
    book1.setTitle("Book One");
    book1.setAuthor("Ethan");
    book1.setBarcode(1234567890);
    book1.setNumPages(100);
    book1.setRating(5.0);
    
    // check that information is stored and returned correctly
    if (!book1.toString().equals("Book One by Ethan\nrating: 5.0\nbarcode: 1234567890\npage count: 100")) {
      System.out.println(book1);
      return false;
    }
    if (!book1.getTitle().equals("Book One"))
      return false;
    if (!book1.getAuthor().equals("Ethan"))
      return false;
    if (book1.getBarcode() != 1234567890)
      return false;
    if (book1.getNumPages() != 100)
      return false;
    if (book1.getRating() != 5.0)
      return false;
    
    // tests passed
    return true;
  }
  
  /**
   * Tests the newBook method from the Book class
   */
  public static boolean testNewBook() {
    // create a book from the books.csv data file
    Book book2 = Book.newBook("books.csv");
    
    // check that a book was properly created using toString()
    if (!book2.toString().contains(" by "))
      return false;
    
    // test passed
    return true;
  }
  
  /**
   * Tests both constructors of the Library class
   */
  public static boolean testLibraryConstructors() {
    // create an empty library with capacity 10
    Library lib1 = new Library(10);
    
    // verify Library was created correctly
    if (lib1.size() != 0)
      return false;
    if (lib1.searchISBN(1234567890) != null)
      return false;
    
    // create a library with some books already stored in it
    Book[] books = new Book[10];
    for (int i = 0; i < 10; i ++)
      books[i] = Book.newBook("books.csv");
    Library lib2 = new Library(20, books);
    
    // verify Library was created correctly 
    // size should be 10
    if (lib2.size() != 10) 
      return false;
    // find book 0 from the books array
    if (!lib2.searchISBN(books[0].getBarcode()).equals(books[0]))
      return false;
    // find book 9 from the books array
    if (!lib2.searchISBN(books[9].getBarcode()).equals(books[9]))
      return false;
    
    // tests passed
    return true;
  }
  
  /**
   * Tests the checkout and return methods of the Library class
   */
  public static boolean testLibraryCheckoutReturn() {
    // create a library with some books already stored in it
    Book[] books = new Book[10];
    for (int i = 0; i < 10; i ++)
      books[i] = Book.newBook("books.csv");
    Library lib = new Library(20, books);
    
    // check out some books and verify correct method behavior
    // size should be 10
    if (lib.size() != 10)
      return false;
    // check out book 0 from the books array
    if (!lib.checkoutBook(books[0].getBarcode()).equals(books[0]))
      return false;
    // size should now be 9
    if (lib.size() != 9)
      return false;
    // attempt to check out a book that doesn't exist
    if (lib.checkoutBook(0) != null)
      return false;
    // size should not have changed
    if (lib.size() != 9)
      return false;
    
    // return some books and verify correct method behavior
    // return book 0 from the books array
    lib.returnBook(books[0]);
    // size should now be 10
    if (lib.size() != 10) 
      return false;
    // add a new book to the library 
    Book anotherBook = Book.newBook("books.csv");
    lib.returnBook(anotherBook);
    // size should now be 11
    if (lib.size() != 11) 
      return false;
    
    // tests passed
    return true;
  }
  
}
