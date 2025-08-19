import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        while (restartGame()) {
            Game game = new Game();

            Main.clearConsole();
            try {
                game.mainMenu();
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean restartGame() {

        boolean isRestart = true;
        while (isRestart) {
            System.out.println("Запустить игру?");
            switch (Main.checkInt("1 - Да\n0 - Нет", 1)) {
                case 1: {

                    return true;
                }
                case 0: {
                    return false;
                }
                default: {
                }
            }
        }
        return false;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String checkNameOfNull(String text) {

        while (true) {
            System.out.println(text);
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            if (s.equals("")) {
                Main.clearConsole();
                System.out.println("Имя не может быть пустым!");
            } else {
                return s;
            }
        }
    }


    public static boolean saveGame(Person person) throws CustomException {
        Main.clearConsole();
        Path pathToSave = Paths.get("C:\\SaveToProgramOfTobakerka\\SaveOfGameRole.ser");

        try (FileOutputStream fosSave = new FileOutputStream(pathToSave.toFile());
             ObjectOutputStream oosIsSave = new ObjectOutputStream(fosSave)){


            // Если файл не существует, он будет создан автоматически
            oosIsSave.writeObject(person);

            Main.clearConsole();
            System.out.println("Игра сохранена");
            return true;
        } catch (FileNotFoundException e) {
            throw new CustomException ("Файл не найден");
        } catch (IOException e) {
            throw new CustomException("Ошибка ввода-вывода");
        }
    }

    public static Person loadGame() {

        Person loadedPerson = null;
        try (FileInputStream fis = new FileInputStream("C:\\SaveToProgramOfTobakerka\\SaveOfGameRole.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            loadedPerson = (Person) ois.readObject(); // Читаем объект из файла
            Main.clearConsole();
            System.out.println("Восстановленный объект: " + loadedPerson);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedPerson;
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
                    Main.clearConsole();
                    System.err.println("Некорректный ввод!");
                }
            } else {
                Main.clearConsole();
                System.err.println("Некорректный ввод!");
            }
        }
    }
}
