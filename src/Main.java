import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    boolean isSave = false;
    boolean isGameToPlay = false;

    public static void main(String[] args) {

        Magazine magazine = new Magazine();
        magazine.spawnMagazine(1);

        Game game = new Game();
        try {
        game.mainMenu();
        } catch (CustomException e) {
            new CustomException("Ошибка! " + e.getMessage());
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void continueGame() {

    }

    public static String checkNameOfNull(String text) {

        while (true) {
            System.out.println(text);
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            if (s.equals("")) {
                System.out.println("Имя не может быть пустым!");
            } else {
                return s;
            }
        }
    }

    public static Person startNewGame() {

        Person[] person = new Person[] {new Person.Human(""), new Person.Elf("")};
        System.out.println("Выбор персонажа:");
        System.out.println();
        for (Person player : person) {
            player.showStats();
            System.out.println();
        }

        while (true) {

            switch (Main.checkInt("Выберите персонажа:\n\n1 - " + person[0].getRace() + "\n2 - " + person[1].getRace() + "\n" + "0 - Выход",2)) {

                case 1: {
                    System.out.println("Вы выбрали " + person[0].getRace());
                    person[0].setName(Main.checkNameOfNull("Введите имя:"));
                    return person[0];
                }
                case 2: {
                    System.out.println("Вы выбрали " + person[1].getRace());
                    person[1].setName(Main.checkNameOfNull("Введите имя:\n"));
                    return person[1];
                }
                case 0: {
                    quitGame();
                    break;
                }
                default: {
                }
            }
        }
    }

    public static void quitGame() {

        System.out.println();
        switch (Main.checkInt("Вы хотите выйти из игры?\n1. Да\n2. Нет",2)) {

            case 1: {

                switch (Main.checkInt("Хотите сохранить игру?\n1. Да\n2. Нет",2)) {

                    case 1: {
                        try {
                        saveGame();
                        } catch (CustomException e) {
                            new CustomException("Ошибка! " + e.getMessage());
                        }
                        System.exit(0);
                        break;
                    }
                    case 2: {
                        System.exit(0);
                        break;
                    }

                }
                break;
            }
            case 2: {
                System.out.println("Вы вернулись в главное меню");
                break;
            }
        }
    }

    public static void saveGame() throws CustomException{

        Path pathToSave = Paths.get("src/Save.txt");
        if (pathToSave.toFile().exists()) {

            try {
                InputStream inputStream = new FileInputStream(pathToSave.toFile());
            } catch (FileNotFoundException e) {
                throw new CustomException("Файл не найден!");
            }
        } else {

        }
    }

    public static void loadGame() {

    }



    public static int checkInt(String text, int count) {

        while (true) {
            System.out.println(text);
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextInt()) {
                int temp = sc.nextInt();
                if (temp >= 0 && temp <= count) {
                    return temp;
                } else {
                    System.err.println("Некорректный ввод!");
                }
            } else {
                System.err.println("Некорректный ввод!");
            }
        }
    }
}
