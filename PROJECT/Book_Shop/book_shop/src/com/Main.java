package com;

import shop.*;
import database.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        PostgresSQLJDBC.connectToDatabase();



 /*       Scanner in = new Scanner(System.in);
        String inputStr, inputOp, name, username,pages;
        int pageNr;
        while(true)
        {
            System.out.println("Choose your user type (guest, librarian, q) ");
            inputStr = in.nextLine();
            switch (inputStr.toLowerCase(Locale.ROOT)) {
                case "librarian":
                    System.out.println("What's your name? ");
                    username = in.nextLine();
                    Librarian librarian = new Librarian(username);

                    System.out.println("Choose operation (add, delete, q) ");
                    inputOp = in.nextLine();

                    if(inputOp.equals("add") || inputOp.equals("delete")) {
                        System.out.println("Give the name of the book: ");
                        name = in.nextLine();
                        System.out.println("Give the nr of pages");
                        pages = in.nextLine();
                        pageNr = Integer.parseInt(pages);
                        //in.nextLine(); //pt enter dupa int

                        Book book = new Book(name, pageNr);

                        if (inputOp.equals("add")) {
                            librarian.addBook(book);
                        } else if (inputOp.equals("delete")) {
                            librarian.deleteBook(book);
                        }
                    }
                    break;
                case "guest":
                    System.out.println("What's your name? ");
                    username = in.nextLine();
                    Guest guest = new Guest(username);

                    System.out.println("Choose operation (view, take, q) ");
                    inputOp = in.nextLine();

                    if(inputOp.equals("view")) {
                        guest.viewBooks();
                    }
                    else if(inputOp.equals("take")) {
                        System.out.println("Give the name of the book: ");
                        name = in.nextLine();
                        guest.takeOwnership(name);
                    }
                    break;
                case "q": System.exit(0);
            }
        }*/
    }
    public void run() {
        System.out.println("This code is running in a thread");
    }
}
