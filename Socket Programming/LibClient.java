package Requirement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LibClient {
    public static final int BorrowPort = 6666;
    public static final int ReturnPort = 6667;
    public static void main(String[] args) throws IOException {
        System.out.println("Enter the name of Client ");
        Scanner scanner = new Scanner(System.in);
        String clientName =scanner.nextLine();

        Socket borrowSocket = new Socket("localhost", 6666);
        PrintWriter borrowWriter = new PrintWriter(borrowSocket.getOutputStream(), true);
        BufferedReader borrowReader = new BufferedReader(new InputStreamReader(borrowSocket.getInputStream()));

        Socket returnSocket = new Socket("localhost", 6667);
        PrintWriter returnWriter=new PrintWriter(returnSocket.getOutputStream(), true);
        BufferedReader returnReader = new BufferedReader(new InputStreamReader(returnSocket.getInputStream()));

        borrowWriter.println(clientName);
        returnWriter.println(clientName);
        while (true)
        {
            System.out.print("Do you want to borrow or return a book? (b/r): ");
            String action = scanner.nextLine();
            if (action.equals(".")) {
                // Exit loop if user enters "."
                borrowSocket.close();
                returnSocket.close();
                break;
            }
            System.out.print("Enter the ISBN of the book: ");
            String isbn = scanner.nextLine();

            // Send ISBN to appropriate socket
            if (action.equals("b")) {
//                sendToSocket(borrowSocket, isbn);
                borrowWriter.println(isbn);
                //read from the server the message
                String BorrowMessage = borrowReader.readLine();
                System.out.println("The received message is  " + BorrowMessage + "\n");
            } else if (action.equals("r")) {
                returnWriter.println(isbn);
                String returnMessage = returnReader.readLine();
                System.out.println("The received message is  " + returnMessage + "\n");
            } else {
                System.out.println("Invalid input.");
                continue;
            }
        }
        System.out.println("the Client is exiting");
        scanner.close();

        borrowWriter.close();
        returnWriter.close();

        borrowSocket.close();
        returnSocket.close();

        borrowReader.close();
        returnReader.close();
    }
}

