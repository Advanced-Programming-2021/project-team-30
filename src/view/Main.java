package view;

import controller.Controller;

import java.util.Scanner;

public class Main {
    private static Controller controller = new Controller();

    public static void main(String[] args) {
        getInput();
    }
    public static void getInput(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String command = scanner.nextLine();
            if (controller.run(command))
                break;
        }
    }

}
