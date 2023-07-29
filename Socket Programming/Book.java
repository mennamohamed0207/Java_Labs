package Requirement;


/*contains the following data members:
 isbn, title, author, a boolean called
borrowed indicating if the book is borrowed or not, and a string called borrower
containing the client’s name that currently borrowing the book.*/
public class Book  {
    String isbn;
    String title;
    String author;
    boolean borrowed;
    String borrower;

    public Book()
    {

    }
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowed = false;
        this.borrower = null;
    }


}
