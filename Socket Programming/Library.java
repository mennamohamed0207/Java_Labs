package Requirement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
* Class Library: contains an array of books as data member and the following functions:
a. Constructor: reads books’ data from file and fills the array of books
*
b. Borrow: takes the client’s name and the isbn, and borrows this book to this client
if possible. The function updates the book data members appropriately.
*
c. ReturnBack: takes the client’s name and the isbn, and returns this book to the
library if it is borrowed by the passed client name. The function updates the book
data members appropriately.
*
d. Print: prints the isbn of all books and the name of the borrower of each
*/
public class Library {
    Book [] books;
    public Library(String filename)
    {
        try {
            Scanner scanner=new Scanner (new File(filename));
            int numBooks=scanner.nextInt();
            scanner.nextLine();  //As next int does NOT go to the new line
            books = new Book[numBooks];
            for (int i = 0; i < numBooks; i++) {
               String isbn = scanner.nextLine();
                String title = scanner.nextLine();
                String author = scanner.nextLine();
                books[i] = new Book(isbn, title, author);
            }
            scanner.close();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    int Borrow(String ClientName,String isbn)
    {
            //return -2 if the book is borrowed by the same Client
            //return -1 if the book is not found in the library
            //return 0 if the book is  borrowed
            //return 1 if the book will be borrowed  ----->success

        System.out.print(ClientName+": ");

        boolean checkifBorrow=true;
        boolean checkifExist=false;
        Book FoundBook=null;
        for (int i=0;i<books.length;i++)
        {
            if (books[i].isbn.equalsIgnoreCase(isbn))
            {
                FoundBook=books[i];
                checkifExist=true;
                if (!books[i].borrowed) {
                    checkifBorrow=false;
                    books[i].borrowed = true;
                    books[i].borrower = ClientName;
                    System.out.println(ClientName + " has borrowed " + books[i].title);
                    //Sucess not borrowed and in the lib
                    this.print();
                    return 1;
                }
            }
        }
        System.out.print(ClientName + " Can't borrow ");
        if (checkifExist&&checkifBorrow)
        {
            if (FoundBook.borrower.equalsIgnoreCase(ClientName)) {
                System.out.println(" As the book is borrowed to the same client  ");
                this.print();
                return -2;
            }else {
                System.out.println(" As the book is borrowed to  "+FoundBook.borrower);
                this.print();
                return 0;
            }

        }else if (!checkifExist)
            System.out.println( " As the book does not exist ");
        this.print();
            return -1;

    }
    int returnBack(String ClientName,String isbn)
            //return -2 if the book is not borrowed
            //return -1 if the book is not found in the book of the library
            //return 1 if the client is the borrower of the book         ----->Sucess
            //return 0 if the book is borrowed but not to that client
    {
        System.out.print(ClientName+": ");
        Book B=null;
        boolean checkifBorrowed=false;
        boolean checkifExist=false;
        for (int i=0;i<books.length;i++)
        {
            if (books[i].isbn.equalsIgnoreCase(isbn))
            {
                B=books[i];
                checkifExist=true;
                if (books[i].borrowed)
                {
                    checkifBorrowed=true;
                if (books[i].borrower.equalsIgnoreCase(ClientName))
                {
                    books[i].borrowed = false;
                    books[i].borrower = null;
                    System.out.println(ClientName + " has returned " + books[i].title);
                    this.print();
                    return 1;
                }else {
                    System.out.println(books[i].title+" is not borrowed by "+ClientName + " it is borrowed by "+books[i].borrower);
                    this.print();
                    return 0;
                }
            }
            }
        }
        if (!checkifBorrowed&&checkifExist)//Faliure it is not borrowed but exist
        {
            System.out.println(B.title+" is not borrowed from the library");
            this.print();
            return -2;
        } else if (!checkifExist) //the book does not exist

            System.out.println("This book does not belong to the libray");
        this.print();
            return -1;




    }
    public void print() {
        for (Book book : books) {
            System.out.print(book.isbn + " ");
            if (book.borrowed) {
                System.out.println("Borrowed by " + book.borrower);
            } else {
                System.out.println("Available and No one borrowed it ");
            }
        }
    }
    public static void main(String[] args) {

        Library lib=new Library("books.txt");
        lib.print();
    }

}
