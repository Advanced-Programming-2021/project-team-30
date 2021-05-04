package view;

import controller.Controller;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void outputToUser(String output) {
        System.out.println(output);
    }
    public static String getInput(){
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run();
    }

}
