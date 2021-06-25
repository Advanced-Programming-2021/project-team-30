package view;

import controller.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static boolean readFromConsole;
    public static Scanner scanner;
    public static void setInput(){
        if (readFromConsole){
            scanner = new Scanner(System.in);
        } else {
            try {
                String string = new String(Files.readAllBytes(Paths.get("src/main/resources/testcases/test1.txt")));
                scanner = new Scanner(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void outputToUser(String output) {
        System.out.println(output);
    }
    public static String getInput(){
        if (!readFromConsole){
            if (scanner.hasNextLine()){
                return scanner.nextLine();
            } else {
                return "";
            }
        }
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        readFromConsole = true;
        setInput();
        Controller controller = new Controller();
        controller.run();
    }

}
