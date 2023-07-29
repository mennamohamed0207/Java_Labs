package Requirement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LibServer {
    public static final int BorrowPort = 6666;
    public static final int ReturnPort = 6667;
    public static int clientNumber = 0; // to keep track of the number of clients connecting to the server.

    public static Library Lib;
    public static void main(String[] args) {

         Lib=new Library("books.txt");
        new Thread() {
            public void run()
            {
                try {
                    ServerSocket serverSocket = new ServerSocket(BorrowPort);
                    while (true) {
                        new ServiceProvider(serverSocket.accept(), clientNumber++).start();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
        new Thread() {
            public void run()
            {
                try {
                    ServerSocket serverSocket = new ServerSocket(ReturnPort);
                    while (true) {
                        new ServiceProvider(serverSocket.accept(), clientNumber++).start();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }
    private static  class ServiceProvider extends Thread
    {
        Socket socket;
        public  int clientNumber ; // to keep track of the number of clients connecting to the server.

        ServiceProvider(Socket s,int clientNo)
        {
            this.clientNumber=clientNo;
            this.socket=s;
            System.out.println("Connection with Client # "  +clientNumber+ " at socket " + socket);

        }
        @Override
        public void run()
        {
            try {
                PrintWriter Writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader Reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String clientName=null;
                if ((clientName=Reader.readLine())==null) {
                }
                while (true)
                {
                    String isbn=null;


                    if ((isbn=Reader.readLine())==null)
                    {

                        break;
                    }

                    if (this.socket.getLocalPort()==BorrowPort)
                    {
                        int result=Lib.Borrow(clientName,isbn);
                        if (result==1)//Sucess
                        {
                            Writer.println("Success The book has been borrowed");
                        }else if (result==0)//the book is  borrowed by another client
                        {
                            Writer.println("Failure As the book is borrowed by another borrower");
                        }else if (result==-2)//the book is  borrowed by the same client
                        {
                            Writer.println("Failure As the book is borrowed by the same client ");
                        }else if (result==-1)//the book is not found in the library
                        {
                            Writer.println("Failure As the book is not found ");

                        }
                    }else if (this.socket.getLocalPort()==ReturnPort)
                    {
                        int result =Lib.returnBack(clientName,isbn);
                        if (result==1)//Success
                        {
                            Writer.println("Success The book has been returned ");

                        }else if (result==0) //Failure  if the book is borrowed but not to that client
                        {
                            Writer.println("Failure this book is not borrowed by this client ");
                        }else if (result==-1) //if the book is not found in the books of the library

                        {
                            Writer.println("Failure this book does not belong to the library");

                        }else if (result==-2) // if the book is not borrowed
                        {
                            Writer.println("Failure this book is not borrowed form library");

                        }


                    }

                }
                Reader.close();
                Writer.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }

    }
}
