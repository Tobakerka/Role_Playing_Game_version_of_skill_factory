import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

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


    public static boolean saveGame(Person person) throws CustomException {
        Path pathToSave = Paths.get("src/Save.ser");

        try (FileOutputStream fos = new FileOutputStream(pathToSave.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Если файл не существует, он будет создан автоматически
            oos.writeObject(person);
            Main.clearConsole();
            System.out.println("Игра сохранена");
            return true;
        } catch (IOException e) {
            throw new CustomException("Ошибка! Файл не найден или произошла ошибка при записи: " + e.getMessage());
        }
    }

    public static Person loadGame() {

        Person loadedPerson = null;
        try (FileInputStream fis = new FileInputStream("src/Save.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            loadedPerson = (Person) ois.readObject(); // Читаем объект из файла
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
                    System.err.println("Некорректный ввод!");
                }
            } else {
                System.err.println("Некорректный ввод!");
            }
        }
    }
}
