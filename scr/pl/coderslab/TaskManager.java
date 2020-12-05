package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static String fileName = "tasks.csv";
    static String[] listOfTask = {"add", "remove", "list", "exit"};
    static String[][] tasks;


    public static void main(String[] args) {

        tasks = getData(fileName);
        optionList(listOfTask);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String number = scanner.nextLine();

            switch (number) {
                case "exit" -> {
                    saveArr(fileName, tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye. See you again.");
                    System.exit(0);
                }
                case "add" -> newTask();
                case "remove" -> {
                    deleteTask(tasks, getNumber());
                    System.out.println("Task deleted");
                }
                case "list" -> showAll(tasks);
                default -> System.out.println("Please select a correct option.");
            }

            optionList(listOfTask);
        }
    }
    public static void optionList(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: ");
        System.out.print(ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static String[][] getData(String fileName) {


        Path pathFile = Paths.get(fileName);
        if (!Files.exists(pathFile)) {
            System.out.println("File not found");
        }
        String[][] arr =null;
        try {
            List<String> strings = Files.readAllLines(pathFile);
            arr= new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    arr[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }



    public static void showAll(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int getNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select number to remove.");

        String toDelete = scanner.nextLine();
        while (!moreThan0(toDelete)) {
            System.out.println("Give number greater or equal 0");
            scanner.nextLine();
        }
        return Integer.parseInt(toDelete);
    }

    private static void deleteTask(String[][] arr, int index) {
        try {
            if (index < arr.length) {
                tasks = ArrayUtils.remove(arr, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }
    }

    public static boolean moreThan0(String number) {
        if (NumberUtils.isParsable(number)) {
            return Integer.parseInt(number) >= 0;
        }
        return false;
    }


    private static void newTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String important = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = important;
    }



    public static void saveArr(String fileName, String[][] tab) {
        Path filePath = Paths.get(fileName);

        String[] strArr = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            strArr[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(filePath, Arrays.asList(strArr));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
