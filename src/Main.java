import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    boolean isSave = false;
    Person[] person = new Person[2];
    public static void main(String[] args) {

        Main main = new Main();
        main.mainMenu();
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void mainMenu() {
        String text = "";
        File file = new File("src/Save.dat");
        if (file.exists()) {
            while (true) {

                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024]; // буфер для чтения
                    int bytesRead;
                    StringBuilder content = new StringBuilder();

                    while ((bytesRead = fis.read(buffer)) != -1) {
                        // Можно обработать байты, например, преобразовать в строку
                        for (int i = 0; i < bytesRead; i++) {
                            content.append((char) buffer[i]);
                        }
                    }
                    text = content.toString();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    System.err.println("Ошибка при чтении файла: " + e.getMessage());
                }
                String sTemp;
                int otvet = -1;

                if (text.length() > 0) {
                    isSave = true;
                } else {
                    isSave = false;
                }
                if (isSave) {
                    sTemp = "Главное меню\n\n1 - Начать новую игру\n2 - Загрузить игру\n0 - Выйти из игры";
                    otvet = Main.checkInt(sTemp, 2);
                } else {
                    sTemp = "Главное меню\n\n1 - Начать новую игру\n0 - Выйти из игры";
                    otvet = Main.checkInt(sTemp, 1);
                }
                System.out.println();

                if (isSave) {
                    switch (otvet) {
                        case 1:
                            startNewGame();
                            break;
                        case 2:
                            loadGame();
                            break;
                        case 0: {
                            quitGame();
                            break;
                        }
                        default: {
                        }
                    }
                } else {
                    switch (otvet) {
                        case 1:
                            startNewGame();
                            break;
                        case 0:
                            quitGame();
                            break;
                    }
                }
            }
        } else {
            try {
                File save = new File("src/Save.dat");
                save.createNewFile();
            } catch (Exception e) {
                System.out.println("Ошибка при создании файла сохранения!");
            }
        }
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

    public Person startNewGame() {

        System.out.println("Выбор персонажа:");
        System.out.println();
        for (Person player : person) {
            player.showStats();
            System.out.println();
        }

        while (true) {

            switch (Main.checkInt("Выберите персонажа:\n\n1 - " + person[0].getRace() + "\n2 - " + person[1].getRace() + "\n",2)) {

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

    public void quitGame() {

        System.out.println("");
        System.out.println();
        switch (Main.checkInt("Вы хотите выйти из игры?1. Да\n2. Нет",2)) {
            case 1: {

                switch (Main.checkInt("Хотите сохранить игру?\n1. Да\n2. Нет",2)) {
                    case 1: {
                        saveGame();
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

    void saveGame() {

        Path pathToSave = Paths.get("Save.txt");
        if (pathToSave.toFile().exists()) {

        } else {

        }
    }

    void loadGame() {

    }



    private static int checkInt(String text, int count) {

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
