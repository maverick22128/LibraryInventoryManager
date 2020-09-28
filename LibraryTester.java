
public class LibraryTester {
  public static void main(String args[]) {
    System.out.println("Test1(): " + test1()); 
    
  }
  /**
   * Checks whether method performs correctly
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean test1() {
    Library one = new Library(100);
    for(int i =0; i<1000; i++) {
      Book book = new Book();
      one.returnBook(book);
    }
    
    Book test = new Book();
    long bar = test.getBarcode();
    one.returnBook(test); 
    Book results = one.searchISBN(bar);
    System.out.println(results);
    one.checkoutBook(bar);
    if (one.searchISBN(bar) != null) {
      return false;
    }
    return true;
  }
}
