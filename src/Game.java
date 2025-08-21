// Основная логика игры

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Game {
    private boolean isSave = false; // Проверка есть ли файл сохранения
    private boolean isGameToPlay = false; // Проверка запущена ли игра. Нужен для правильного отображения меню при запуске игры

    // Геттеры и сеттеры
    public boolean getIsGameToPlay() {
        return isGameToPlay;
    }

    public void setIsGameToPlay(boolean isGameToPlay) {
        this.isGameToPlay = isGameToPlay;
    }

    public static Person spawnPerson(int level) {

        // Создается броня и оружие для будующего персонажа
        Item.Weapon weapon;
        Item.Armor armor;

        // Проверка есть ли оружие и броня у персонажа
        boolean isWeapon = false;
        boolean isArmor = false;

        Random random = new Random();
        isWeapon = random.nextBoolean();
        isArmor = random.nextBoolean();

        // Относительно уровня персонажа игрока проверяется какой уровень будет у противника
        Random randomLevel = new Random();
        int tempCheckLevel = randomLevel.nextInt(5);
        switch (tempCheckLevel) {
            case 0: {
                level += 1;
                break;
            }
            case 1: {
                level += 2;
                break;
            }
            case 2: {
                level -= 1;
                break;
            }
            case 3: {
                level -= 2;
                break;
            }
            case 4: {
                break;
            }
        }

        // Если уровень получился отрицательным, то он становится равным 1
        if (level < 1) {

            level = 1;
        }

        // Поля будующего персонажа
        int maxHealth = 100;
        int health = maxHealth;
        int maxStrength = 100;
        int strength = maxStrength;
        int defence = 2;
        int power = 20;
        int agility = 20;
        int gold = 0;

        // Присваиваются характеристики персонажа в зависимости от уровня
        for (int i = 0; i < level -1 ; i++) {

            maxHealth += Math.round(maxHealth * 0.05);
            maxStrength += Math.round(maxStrength * 0.05);
            power += Math.round(power * 0.05);
            agility += Math.round(agility * 0.05);
            defence += Math.round(defence * 0.05);
        }

        // Присваиваются максимально доступные жизнь и стамина.
        health = maxHealth;
        strength = maxStrength;

        // Если у персонажа будет оружие или броня, то запускается метод создания оружия или брони. Если их нет, то присваивается null
        if (isWeapon) {

            weapon = (Item.Weapon) spawnWeapon(level);
        } else {

            weapon = new Item.Weapon("Пусто", 0, 0, 0, "",0,0);
        }

        if (isArmor) {

            armor = (Item.Armor)spawnArmor(level);
        } else {

            armor = new Item.Armor("Пусто", 0, 0,0,0);
        }

        // Проверка будет ли у персонажа деньги
        boolean checkIsMoney = false;
        Random randomMoney = new Random();
        checkIsMoney = randomMoney.nextBoolean();

        // Количество денег
        if (checkIsMoney) {

            Random intRandomMoney = new Random();
            gold = level * intRandomMoney.nextInt(100);
        }

        // Случайным образом выбирается тип персонажа
        Random randomPerson = new Random();
        int tempCheckPerson = randomPerson.nextInt(4);
        switch (tempCheckPerson) {
            case 0: return new Person.Skeleton("Скелет", maxHealth, health, power, agility, maxStrength,strength, defence, gold, level, weapon, armor);
            case 1: return new Person.Goblin("Гоблин", maxHealth,  health, power, agility, maxStrength, strength, defence, gold, level, weapon, armor);
            case 2: return new Person.Zombie("Зомби", maxHealth, health, power, agility, maxStrength, strength, defence, gold, level, weapon, armor);
            case 3: return new Person.Vampire("Вампир", maxHealth, health, power, agility, maxStrength, strength, defence, gold, level, weapon, armor);
        }
        return null;
    }

    public void options(Person player) {

        while (true) {
            String sMag;
            String sInv;
            StringBuilder sDef = new StringBuilder();
            // Проверка включена ли сортировка магазина в опциях
            if (player.getIsChopSort()) {
                sMag = "Сортировка магазина включена";
            } else {
                sMag = "Сортировка магазина выключена";
            }

            // Проверка включена ли сортировка инвентаря в опциях
            if (player.getIsInventorySort()) {
                sInv = "Сортировка инвентаря включена";
            } else {
                sInv = "Сортировка инвентаря выключена";
            }


            if (player.getDifficulty() == 1) {
                sDef.append("Сложность игры: Легко");
            } else if (player.getDifficulty() == 2) {
                sDef.append("Сложность игры: Средне");
            } else if (player.getDifficulty() == 3) {
                sDef.append("Сложность игры: Сложно");
            }

            // Переменная для выставления сложности игры

            // Вывод меню опций
            Scanner scanner = new Scanner(System.in);
            System.out.println("Настройки\n\n");
            System.out.println(sInv + "\n" + sMag + "\n" + sDef.toString());
            System.out.println("1 - Сложность игры\n" + "2 - Сортировка инвентаря\n" + "3 - Сортировка магазина\n" + "0 - Назад");
            String otvet = scanner.nextLine();

            switch (otvet) {
                case "1" : {
                    Main.clearConsole();
                    player.setDifficulty();
                    break;
                }
                case "2" : {
                    Main.clearConsole();
                    player.setIsInventorySort();
                    break;
                }
                case "3" : {
                    Main.clearConsole();
                    player.setIsChopSort();
                    break;
                }
                case "0" : {
                    Main.clearConsole();
                    return;
                }
                default: {
                    Main.clearConsole();
                    System.err.println("Некорректный ввод!");
                }
            }
        }
    }

    public void mainMenu() {
        Person player = new Person("");
        Magazine magazine = new Magazine();
        String text = "";
        File file = new File("C:\\SaveToProgramOfTobakerka\\SaveOfGameRole.ser");

        if (file.exists()) {

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024]; // буфер для чтения
                int bytesRead;
                StringBuilder content = new StringBuilder();

                while ((bytesRead = fis.read(buffer)) != -1) {
                    // Можно обработать байты, например, преобразовать в строку
                    for (int i = 0; i < bytesRead; i++) {
                        content.append((char) buffer[i]);
                        isSave = true;
                    }
                }
                byte[] bytes = content.toString().getBytes();
            } catch (FileNotFoundException e) {
                Main.clearConsole();
                new CustomException("Ошибка в файле сохранения!");
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Main.clearConsole();
                    new CustomException("Ошибка при ожидании!");
                }
            } catch (IOException e) {
                Main.clearConsole();
                System.err.println(e.getMessage() + "Файл сохранения поврежден!");
            }
            String sTemp;
            int otvet = -1;

            while (player.getIsAlive()) {

                if (isSave && isGameToPlay) {
                    sTemp = "Главное меню\n\n1 - Начать новую игру\n2 - Продолжить игру\n3 - Загрузить игру\n4 - Настройки\n5 - Сохранить\n0 - Выйти из игры";
                    otvet = Main.checkInt(sTemp, 5);
                    switch (otvet) {
                        case 1: {
                            player = startNewGame();
                            Main.clearConsole();
                            magazine = new Magazine();
                            magazine.spawnMagazine(player.getLevel());
                            isGameToPlay = true;
                            startGame(player, magazine);
                            break;
                        }
                        case 2: {

                            Main.clearConsole();
                            continueGame(player, magazine);
                            break;
                        }
                        case 3: {
                            Main.clearConsole();
                            player = Main.loadGame();
                            isGameToPlay = true;
                            break;
                        }
                        case 4: {
                            Main.clearConsole();
                            options(player);
                            break;
                        }
                        case 5: {
                            try {
                                isSave = Main.saveGame(player);
                            } catch (CustomException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        }
                        case 0: {
                            Main.clearConsole();
                            quitGame(player);
                            break;
                        }
                        default: {

                        }
                    }
                } else if (!isSave && !isGameToPlay) {
                    sTemp = "Главное меню\n\n1 - Начать новую игру\n2 - Настройки\n0 - Выйти из игры";
                    otvet = Main.checkInt(sTemp, 2);
                    switch (otvet) {

                        case 1: {
                            player = startNewGame();
                            Magazine magazine1 = new Magazine();
                            magazine = magazine1;
                            magazine.spawnMagazine(player.getLevel());
                            isGameToPlay = true;
                            Main.clearConsole();
                            startGame(player, magazine);
                            break;
                        }
                        case 2: {
                            Main.clearConsole();
                            options(player);
                            break;
                        }
                        case 0: {
                            Main.clearConsole();
                            quitGame(player);
                            return;
                        }
                        default: {

                        }
                    }
                } else if (!isSave && isGameToPlay) {
                    sTemp = "Главное меню\n\n1 - Начать новую игру\n2 - Продолжить игру\n3 - Настройки\n4 - Сохранить\n0 - Выйти из игры";
                    otvet = Main.checkInt(sTemp, 4);
                    switch (otvet) {
                        case 1: {
                            player = startNewGame();
                            magazine = new Magazine();
                            magazine.spawnMagazine(player.getLevel());
                            isGameToPlay = true;
                            Main.clearConsole();
                            startGame(player, magazine);
                            break;
                        }
                        case 2: {
                            continueGame(player, magazine);
                            break;
                        }
                        case 3: {
                            options(player);
                            break;
                        }
                        case 4: {
                            try {
                                Main.saveGame(player);
                            } catch (CustomException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        }
                        case 0: {
                            Main.clearConsole();
                            isGameToPlay = false;
                            quitGame(player);
                            break;
                        }
                        default: {

                        }
                    }
                } else if (isSave && !isGameToPlay) {
                    sTemp = "Главное меню\n\n1 - Начать новую игру\n2 - Загрузить игру\n3 - Настройки\n0 - Выйти из игры";
                    otvet = Main.checkInt(sTemp, 3);
                    switch (otvet) {
                        case 1: {
                            Main.clearConsole();
                            player = startNewGame();
                            magazine = new Magazine();
                            magazine.spawnMagazine(player.getLevel());
                            isGameToPlay = true;
                            Main.clearConsole();
                            startGame(player, magazine);
                            break;
                        }
                        case 2: {
                            Main.clearConsole();
                            player = Main.loadGame();
                            isGameToPlay = true;
                            magazine.spawnMagazine(player.getLevel());
                            break;
                        }
                        case 3: {
                            Main.clearConsole();
                            options(player);
                            break;
                        }
                        case 0: {
                            Main.clearConsole();
                            quitGame(player);
                            break;
                        }
                        default: {
                            Main.clearConsole();
                            System.err.println("Некорректный ввод!");
                        }
                    }
                }
                System.out.println();
            }
        } else {

            try {
                File path = new File("C:\\SaveToProgramOfTobakerka");
                File save = new File("C:\\SaveToProgramOfTobakerka\\SaveOfGameRole.ser");
                path.mkdir();
                save.createNewFile();
                Main.clearConsole();
                System.out.println("Файл сохранения создан! сохрание находится по пути: " + save.getAbsolutePath());
            } catch (Exception e) {
                Main.clearConsole();
                System.out.println("Ошибка при создании файла сохранения!");
            }
        }
    }

    // Метод для создания новой игры
    public Person startNewGame() {

        Main.clearConsole();
        Person[] person = new Person[] {new Person.Human(""), new Person.Elf("")};
        System.out.println();
        for (Person player : person) {
            player.showStats();
            System.out.println();
        }

        while (true) {
            System.out.println("Выбор персонажа:\n");
            switch (Main.checkInt("Выберите персонажа:\n\n1 - " + person[0].getRace() + "\n2 - " + person[1].getRace(),2)) {

                case 1: {
                    Main.clearConsole();
                    System.out.println("Вы выбрали " + person[0].getRace());
                    person[0].setName(Main.checkNameOfNull("Введите имя:"));
                    return person[0];
                }
                case 2: {
                    Main.clearConsole();
                    System.out.println("Вы выбрали " + person[1].getRace());
                    person[1].setName(Main.checkNameOfNull("Введите имя:\n"));
                    return person[1];
                }
                case 0: {
                    Main.clearConsole();
                    System.err.println("Некорректный ввод!");
                }
                default: {
                }
            }
        }
    }

    //Запуск игры
    public void startGame(Person player, Magazine magazine) {

        Main.clearConsole();
        while (true) {

            // Отображение доступных действий
            System.out.println("Уровень: " + player.getLevel() + "\n");
            System.out.println("Здоровье: " + player.getHealth());
            System.out.println("Выносливость: " + player.getStrength() + "/" + player.getMaxStrength() + "\n");
            System.out.println("Золото: " + player.getGold() + "\n");

            switch (Main.checkInt("Действия: \n\n1 - Открыть инвентарь\n" +
                    "2 - идти в магазин\n" +
                    "3 - идти в темный лес\n" +
                    "4 - идти к кузнецу\n" +
                    "5 - отдыхать\n" +
                    "6 - статистика\n" +
                    "0 - Меню\n", 6)) {
                case 1: {
                    Main.clearConsole();
                    player.openInventary();
                    break;
                }
                case 2: {
                    Main.clearConsole();
                    player.move();
                    magazine.menuMagazine(player);
                    break;
                }
                case 3: {
                    Main.clearConsole();
                    Fight fight = new Fight(player);
                    Thread thread = new Thread(fight);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (!player.getIsAlive()) {
                        return;
                    }
                    break;
                }
                case 4: {
                    Main.clearConsole();
                    player.move();
                    goToTheBlacksmith(player);
                    break;
                }
                case 5: {
                    Main.clearConsole();
                    toTakeADreak(player, magazine);
                    break;
                }
                case 6: {
                    Main.clearConsole();
                    player.showStats();
                    break;
                }
                case 0: {

                    Main.clearConsole();
                    return;
                }
                default: {

                    Main.clearConsole();
                    System.out.println("Некорректный ввод!");
                }
            }
        }
    }

    // Метод для продолжения игры
    public void continueGame(Person player, Magazine magazine) {
            startGame(player, magazine);
    }

    // Метод для выхода из игры
    public void quitGame(Person player) {
        Main.clearConsole();
        System.out.println();
        switch (Main.checkInt("Вы хотите выйти из игры?\n1. Да\n2. Нет",2)) {
            case 1: {
                Main.clearConsole();
                switch (Main.checkInt("Хотите сохранить игру?\n1. Да\n2. Нет",2)) {

                    case 1: {
                        try {

                            Main.saveGame(player);
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
                Main.clearConsole();
                System.out.println("Вы вернулись в главное меню");
                break;
            }
        }
    }

    // Метод для отдыха и восстановления стамины
    private void toTakeADreak(Person player, Magazine magazine) {
        Main.clearConsole();
        System.out.println("Вы отдыхаете\n");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            new CustomException("Ошибка при ожидании!");
        }
        Main.clearConsole();
        System.out.println("Вы отдохнули\nТовары в магазине также обновились\n");
        player.setStrength(player.getMaxStrength());
        magazine.spawnMagazine(player.getLevel());
    }

    // Метод "Идти к кузнецу"
    private void goToTheBlacksmith(Person player) {
        // Защитывается передвижение персонажа и тратится стамина

        // Проверка на то, жив ли персонаж
        if (!player.getIsAlive()) {
            return;
        }

        // Очищаем консоль
        Main.clearConsole();

        while (true) {
            // Приветствие кузнеца
            System.out.println("Привет, " + player.getName() + " я кузнец!\n");

            // Создается массивы с оружием и броней
            ArrayList<Item.Weapon> weapons = new ArrayList<>();
            ArrayList<Item.Armor> armors = new ArrayList<>();

            // Пробегаемся по инвентарю персонажа и добавляем оружие и броню в массивы
            player.getInventory().forEach(item -> {
                if (item.getType().equals("Оружие")) {
                    weapons.add((Item.Weapon) item);
                } else if (item.getType().equals("Броня")) {
                    armors.add((Item.Armor) item);
                }
            });


            int count = 0;
            int count2 = 0;
            switch (Main.checkInt("Кузнец: Я могу прокачать или зачаровать твои вещи! Что ты хочешь сделать?\n1 - Прокачать вещь\n2 - Зачаровать вещь\n0 - Попрощаться", 2)) {

                case 1: {
                    Main.clearConsole();
                    while (true) {
                        System.out.println("Деньги: " + player.getGold());
                        StringBuilder sb = new StringBuilder();
                        sb.append("Твои вещи:\n");
                        sb.append("Оружие:\n");
                        for (Item.Weapon weapon : weapons) {
                            String equpWeapon = "";
                            if (weapon.equals(player.getWeapon())) {
                                equpWeapon = " - (надето)";
                            }
                            sb.append(count + " - " + weapon.getName() + " " + weapon.getLevelChange() + " ур." + equpWeapon + "\n");
                            count++;
                            if (count2 == 10) {
                                count2 = 0;
                            } else {
                                count2++;
                            }
                        }
                        sb.append("\n");
                        sb.append("Броня:\n");
                        for (Item.Armor armor : armors) {
                            String equpArmor = "";
                            if (armor.equals(player.getArmor())) {
                                equpArmor = " - (Надето)";
                            }
                            sb.append(count + " - " + armor.getName() + " " + armor.getLevelChange() + " ур." + equpArmor + "\n");
                            count++;
                            if (count2 == 10) {
                                count2 = 0;
                            } else {
                                count2++;
                            }
                        }
                        count = 0;

                        ArrayList<Item> items = new ArrayList<>();
                        items.addAll(weapons);
                        items.addAll(armors);

                        System.out.println(sb.toString());
                        System.out.println("Выберите вещь, которую хотите прокачать: или введите любую букву для выхода");
                        Scanner scanner = new Scanner(System.in);
                        if (scanner.hasNextInt()) {

                            int tempInt = scanner.nextInt();
                            Item itemNew = items.get(tempInt);
                            Item itemOld = items.get(tempInt);
                            if (tempInt >= 0 && tempInt < items.size()) {

                                if (items.get(tempInt).getType().equals("Оружие")) {
                                    Main.clearConsole();
                                    Item.Weapon weapon = (Item.Weapon) items.get(tempInt);
                                    switch (Main.checkInt("Вы хотите прокачать " + weapon.getName() + "?\n1 - Да\n2 - Нет", 2)) {
                                        case 1: {
                                            Main.clearConsole();
                                            weapon.getInfo();
                                            int tempIntLevel = Main.checkInt("Введите на сколько хотите прокачать: ", 19);
                                            if (tempIntLevel > 0 && tempIntLevel <= 20) {
                                                Item.Weapon weaponNew = (Item.Weapon) items.get(tempInt);
                                                Item.Weapon weaponOld = (Item.Weapon) items.get(tempInt);
                                                int countLevelUp = 0;
                                                if (weaponNew.getLevelChange() + tempIntLevel <= 20) {
                                                    int tempPrice = weaponNew.getPrice();
                                                    while (tempIntLevel > 0) {
                                                        tempIntLevel--;
                                                        countLevelUp++;
                                                        tempPrice += weaponNew.getPrice();
                                                    }
                                                    Main.clearConsole();
                                                    weapon.getInfo();
                                                    System.out.println("Деньги: " + player.getGold());
                                                    System.out.println("Цена прокачки: " + tempPrice);
                                                    switch (Main.checkInt("1 - Да\n2 - Нет", 2)) {
                                                        case 1: {
                                                            Main.clearConsole();
                                                            if (player.getGold() >= tempPrice) {
                                                                player.deliteGold(tempPrice);
                                                                player.removeItem(weaponOld);
                                                                if (player.getWeapon().equals(weaponNew)) {
                                                                    player.setWeapon(null);
                                                                    weaponNew.levelUp(countLevelUp);
                                                                    itemNew = weaponNew;
                                                                    player.setWeapon(weaponNew);
                                                                    player.addInventory(itemNew);
                                                                } else {
                                                                    player.removeItem(itemOld);
                                                                    player.addInventory(itemNew);
                                                                }
                                                                System.out.println("Успешно прокачено!");
                                                                System.out.println("Оружие: " + weaponNew.getName() + " " + weaponNew.getLevelChange() + " ур.");
                                                            } else {
                                                                Main.clearConsole();
                                                                System.err.println("Недостаточно денег!");
                                                                break;
                                                            }
                                                            break;
                                                        }
                                                        case 2: {
                                                            Main.clearConsole();
                                                            System.out.println("Возврат");
                                                            break;
                                                        }
                                                    }

                                                } else {
                                                    Main.clearConsole();
                                                    System.err.println("Невозможно прокачать на такую величину!\nМаксимальный уровень может быть 20");
                                                }
                                            }
                                            break;
                                        }
                                        case 2: {
                                            Main.clearConsole();
                                            System.out.println("Возврат");
                                            break;
                                        }
                                        default: {
                                            Main.clearConsole();
                                            System.err.println("Некорректный ввод!");
                                        }
                                    }
                                } else if (items.get(tempInt).getType().equals("Броня")) {
                                    Main.clearConsole();
                                    Item.Armor armor = (Item.Armor) items.get(tempInt);
                                    switch (Main.checkInt("Вы хотите прокачать " + armor.getName() + "?\n1 - Да\n2 - Нет", 2)) {
                                        case 1: {
                                            Main.clearConsole();
                                            armor.getInfo();

                                            int tempIntLevel = Main.checkInt("Введите на сколько хотите прокачать: ", 19);
                                            if (tempIntLevel > 0 && tempIntLevel <= 20) {
                                                int countLevelUp = 0;
                                                Item.Armor armorNew = (Item.Armor) items.get(tempInt);
                                                Item.Armor oldArmor = (Item.Armor) items.get(tempInt);
                                                if (armorNew.getLevelChange() + tempIntLevel <= 20) {
                                                    int tempPrice = armorNew.getPrice();
                                                    while (tempIntLevel > 0) {
                                                        tempIntLevel--;
                                                        countLevelUp++;
                                                        tempPrice += armorNew.getPrice();
                                                    }
                                                    Main.clearConsole();
                                                    armor.getInfo();
                                                    System.out.println("Деньги: " + player.getGold());
                                                    System.out.println("Цена прокачки: " + tempPrice);
                                                    switch (Main.checkInt("1 - Да\n2 - Нет", 2)) {
                                                        case 1: {
                                                            Main.clearConsole();
                                                            if (player.getGold() >= tempPrice) {
                                                                player.deliteGold(tempPrice);
                                                                if (player.getArmor().equals(armorNew)) {
                                                                    player.setArmor(null);
                                                                    armorNew.levelUp(countLevelUp);
                                                                    player.setArmor(armorNew);
                                                                    player.removeItem(oldArmor);
                                                                    player.addInventory(armorNew);
                                                                } else {
                                                                    itemNew = armorNew;
                                                                    player.removeItem(itemOld);
                                                                    player.addInventory(itemNew);
                                                                }
                                                                System.out.println("Успешно прокачено!");
                                                                System.out.println("Броня: " + armorNew.getName() + " " + armorNew.getLevelChange() + " ур.");
                                                            } else {
                                                                System.err.println("Недостаточно денег!");
                                                            }
                                                            break;
                                                        }
                                                        case 2: {
                                                            Main.clearConsole();
                                                            break;
                                                        }
                                                    }
                                                }
                                            } else {
                                                Main.clearConsole();
                                                System.err.println("Невозможно прокачать на такую величину!\nМаксимальный уровень может быть 20");
                                            }
                                            break;
                                        }
                                        case 2: {
                                            Main.clearConsole();
                                            System.out.println("Возврат");
                                            break;
                                        }
                                        default: {
                                            Main.clearConsole();
                                            System.err.println("Некорректный ввод!");
                                        }

                                    }

                                }
                            } else {
                                Main.clearConsole();
                                System.err.println("Некорректный ввод!");
                            }
                        } else {
                            return;
                        }
                    }
                }
                case 2: {
                    String equpWeapon = "";
                    Main.clearConsole();
                    count = 0;
                    StringBuilder sbChar = new StringBuilder();
                    sbChar.append("Твои вещи:\n");
                    sbChar.append("Оружие:\n");
                    for (Item.Weapon weapon : weapons) {
                        if (weapon.equals(player.getWeapon())) {
                            equpWeapon = " - (надето)";
                        }
                        sbChar.append(count + " - " + weapon.getName() + " " + weapon.getLevelChange() + " ур." + equpWeapon + "\n");
                        if (!weapon.getTypeEffect().equals("")) {
                            sbChar.append("Эффект: " + weapon.getTypeEffect() + "\n");
                        }
                        count++;
                        if (count2 == 10) {
                            count2 = 0;
                        } else {
                            count2++;
                        }
                    }
                    sbChar.append("\n");
                    System.out.println(sbChar.toString());
                    System.out.println("Выберите вещь, которую хотите зачаровать: или введите любую букву для выхода");
                    Scanner scanner = new Scanner(System.in);
                    if (scanner.hasNextInt()) {

                        int tempInt = scanner.nextInt();
                        if (tempInt >= 0 && tempInt < weapons.size()) {

                            Item itemOld = weapons.get(tempInt);
                            Item itemNew = weapons.get(tempInt);
                            Item.Weapon itemWeaponNew = weapons.get(tempInt);
                            Item.Weapon itemWeaponOld = weapons.get(tempInt);
                            if (tempInt >= 0 && tempInt < weapons.size()) {
                                if (itemWeaponOld.getTypeEffect().equals("")) {
                                    Main.clearConsole();

                                    System.out.println("Вы хотите зачаровать " + itemWeaponOld.getName() + "\n");
                                    System.out.println("Стоимость зачарования: " + (itemWeaponOld.getPrice() * 3));
                                    switch (Main.checkInt("1 - Да\n2 - Нет", 2)) {
                                        case 1: {
                                            Main.clearConsole();
                                            Random randomChar = new Random();
                                            int chanceChar = randomChar.nextInt(5);
                                            if (player.getGold() >= (itemWeaponOld.getPrice() * 3)) {
                                                int tempPowerEffect = 5 + randomChar.nextInt(100);
                                                switch (chanceChar) {
                                                    case 0: {

                                                        Main.clearConsole();
                                                        System.out.println("Зачарование прошло неудачно!");
                                                        System.out.println("Вы потеряли " + itemWeaponOld.print() + (itemWeaponOld.getPrice() * 3) + " золота!");
                                                        player.deliteGold(itemWeaponOld.getPrice() * 3);
                                                        player.removeItem(itemOld);
                                                        if (player.getWeapon().equals(itemWeaponOld)) {
                                                            player.setWeapon(null);
                                                        }
                                                        break;
                                                    }
                                                    case 1: {

                                                        Main.clearConsole();
                                                        System.out.println("Зачарование прошло успешно!");
                                                        if (tempPowerEffect <= 25) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 1.5);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else if (tempPowerEffect > 25 && tempPowerEffect <= 50) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 1.7);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else if (tempPowerEffect > 50 && tempPowerEffect <= 75) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 1.9);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 2.1);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        }
                                                        System.out.println("Вы получили огненные чары " + tempPowerEffect);
                                                        itemWeaponNew.setTypeEffect("огонь");
                                                        itemWeaponNew.setPrice((int) Math.round(itemWeaponNew.getPowerEffect() * 1.5));
                                                        player.deliteGold(itemWeaponOld.getPrice() * 3);
                                                        itemNew = itemWeaponNew;
                                                        if (player.getWeapon().equals(itemWeaponOld)) {
                                                            player.setWeapon(null);
                                                            player.setWeapon(itemWeaponNew);
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);
                                                        } else {
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);
                                                        }
                                                        break;
                                                    }
                                                    case 2: {

                                                        Main.clearConsole();
                                                        System.out.println("Зачарование прошло успешно!");
                                                        if (tempPowerEffect <= 25) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 1.5);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else if (tempPowerEffect > 25 && tempPowerEffect <= 50) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 1.7);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else if (tempPowerEffect > 50 && tempPowerEffect <= 75) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 1.9);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getDamage() * 2.1);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        }

                                                        System.out.println("Вы получили ледяные чары " + tempPowerEffect);
                                                        itemWeaponNew.setTypeEffect("лед");
                                                        itemWeaponNew.setPrice((int) Math.round(itemWeaponNew.getPowerEffect() * 1.5));
                                                        player.deliteGold(itemWeaponOld.getPrice() * 3);
                                                        itemNew = itemWeaponNew;
                                                        if (player.getWeapon().equals(itemWeaponOld)) {
                                                            player.setWeapon(null);
                                                            player.setWeapon(itemWeaponNew);
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);
                                                        } else {
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);
                                                        }
                                                        break;
                                                    }
                                                    case 3: {

                                                        Main.clearConsole();
                                                        System.out.println("Зачарование прошло успешно!");
                                                        if (tempPowerEffect <= 25) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getPowerEffect() * 1.5);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else if (tempPowerEffect > 25 && tempPowerEffect <= 50) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getPowerEffect() * 1.7);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else if (tempPowerEffect > 50 && tempPowerEffect <= 75) {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getPowerEffect() * 1.9);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        } else {
                                                            tempPowerEffect += (int) Math.round(itemWeaponOld.getPowerEffect() * 2.1);
                                                            itemWeaponNew.setPowerEffect(tempPowerEffect);
                                                        }
                                                        System.out.println("Вы получили водные чары " + tempPowerEffect);
                                                        itemWeaponNew.setTypeEffect("вода");
                                                        itemWeaponNew.setPrice((int) Math.round(itemWeaponNew.getPowerEffect() * 1.5));
                                                        player.deliteGold(itemWeaponOld.getPrice() * 3);
                                                        itemNew = itemWeaponNew;
                                                        if (player.getWeapon().equals(itemWeaponOld)) {
                                                            player.setWeapon(null);
                                                            player.setWeapon(itemWeaponNew);
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);

                                                        } else {
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);
                                                        }
                                                        break;
                                                    }
                                                    case 4: {

                                                        Main.clearConsole();
                                                        System.out.println("Зачарование прошло успешно!");
                                                        if (tempPowerEffect <= 25) {
                                                            itemWeaponNew.setPowerEffect((int) Math.round(itemWeaponOld.getPowerEffect() * 1.5));
                                                        } else if (tempPowerEffect > 25 && tempPowerEffect <= 50) {
                                                            itemWeaponNew.setPowerEffect((int) Math.round(itemWeaponOld.getPowerEffect() * 1.7));
                                                        } else if (tempPowerEffect > 50 && tempPowerEffect <= 75) {
                                                            itemWeaponNew.setPowerEffect((int) Math.round(itemWeaponOld.getPowerEffect() * 1.9));
                                                        } else {
                                                            itemWeaponNew.setPowerEffect((int) Math.round(itemWeaponOld.getPowerEffect() * 2.1));
                                                        }
                                                        System.out.println("Вы получили зачарование ветра " + tempPowerEffect);
                                                        itemWeaponNew.setTypeEffect("ветер");
                                                        itemWeaponNew.setPrice((int) Math.round(itemWeaponNew.getPowerEffect() * 1.5));
                                                        player.deliteGold(itemWeaponOld.getPrice() * 3);
                                                        itemNew = itemWeaponNew;
                                                        if (player.getWeapon().equals(itemWeaponOld)) {
                                                            player.setWeapon(null);
                                                            player.setWeapon(itemWeaponNew);
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);
                                                        } else {
                                                            player.removeItem(itemOld);
                                                            player.addInventory(itemNew);
                                                        }
                                                        break;
                                                    }
                                                }
                                            } else {
                                                Main.clearConsole();
                                                System.out.println("Недостаточно золота!");
                                            }

                                            break;
                                        }
                                        case 2: {
                                            Main.clearConsole();
                                            System.out.println("Возврат");
                                            break;
                                        }
                                        default: {
                                            Main.clearConsole();
                                            System.err.println("Некорректный ввод!");
                                        }
                                    }
                                } else {
                                    Main.clearConsole();
                                    System.err.println("Нельзя чаровать. Предмет уже зачарован!");
                                }
                            }
                        } else {
                            Main.clearConsole();
                            System.err.println("Некорректный ввод!");
                        }
                    } else {
                        Main.clearConsole();
                        System.out.println("Возврат");
                    }
                    break;
                }
                case 0: {
                    Main.clearConsole();
                    System.out.println("Пока!");
                    try {

                        sleep(1000);
                    } catch (InterruptedException e) {

                        new CustomException("Ошибка! " + e.getMessage());
                    }

                    return;
                }
            }
        }
    }

    /* Метод для дропа предметов из поверженного противника.
    В первый параметр передается игрок, которому будет присвоен дроп.
    Во второй параметр передается поверженный противник.
     */
    public static void lootOfPerson(Person player, Person isDeadPerson) {

        Main.clearConsole();
        ArrayList<Item> loot = new ArrayList<>();

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("Вы получили: \n----------------------------------------------------\n");

        // Проверка выпало ли оружие. Если да, то добавляется в список дропа
        if (random.nextBoolean() && isDeadPerson.getWeapon() != null && !isDeadPerson.getWeapon().getName().equals("Пусто")) {
            loot.add(isDeadPerson.getWeapon());
            sb.append(isDeadPerson.getWeapon().print() + "\n");
        }

        // Проверка выпало ли броня. Если да, то добавляется в список дропа
        if (random.nextBoolean() && isDeadPerson.getArmor() != null && !isDeadPerson.getArmor().getName().equals("Пусто")) {
            loot.add(isDeadPerson.getArmor());
            sb.append(isDeadPerson.getArmor().print() + "\n");
        }

        Random randomFoodOfPotion = new Random();
        int checkCount = randomFoodOfPotion.nextInt(6);
        for (int i = 0; i < checkCount; i++) {
            if (random.nextInt() >= 90) {
                boolean isFood = random.nextBoolean();
                if (isFood) {
                    Item food = Game.spawnFood(player.getLevel());
                    sb.append(food.getName() + " цена: " + food.price + " золота\n\n" );
                    loot.add(food);
                } else {
                    Item potion = Game.spawnPotion(player.getLevel());
                    sb.append(potion.getName() + " цена: " + potion.price + " золота\n\n" );
                    loot.add(potion);
                }
            }
        }

        if (random.nextBoolean() && isDeadPerson.getGold() > 0) {
            player.addGold(isDeadPerson.getGold());
            sb.append(isDeadPerson.getGold() + " золота");
        }
        sb.append("\n----------------------------------------------------\n");

        if (!sb.toString().equals("Вы получили: \n----------------------------------------------------\n\n----------------------------------------------------\n")) {
            System.out.println(sb.toString());
        } else {
            System.out.println("Вы ничего не получили\n");
        }

        for (Item item : loot) {

            System.out.println("Добавить предмет + " + item.name + " в инвентарь? (y/n)");
            Scanner scanner = new Scanner(System.in);
            boolean check = true;
            while (check) {

                switch (scanner.next()) {
                    case "y": {
                        player.addInventory(item);
                        check = false;
                        scanner = null;
                        break;
                    }
                    case "n": {
                        check = false;
                        scanner = null;
                        break;
                    }
                    default: {
                        System.out.println("Неверный ввод");
                    }
                }

            }
        }
    }

    /* Метод для генерации предметов в магазин или если потребуется куда либо еще.
    Данный метод принимает два параметра:
    1. Количество предметов, которые нужно сгенерировать
    2. Уровень предметов
    Сам метод возвращает список сгенерированных предметов
     */
    public static ArrayList generateItem(int countItem, int level) {

        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < countItem; i++) {

            // Случайным образом выбирается тип предмета
            Random random = new Random();
            int tempRand = random.nextInt(4);
            switch (tempRand) {
                case 0: {
                    items.add(Game.spawnWeapon(level));
                    break;
                }
                case 1: {
                    items.add(Game.spawnArmor(level));
                    break;
                }
                case 2: {
                    items.add(Game.spawnPotion(level));
                    break;
                }
                case 3: {
                    items.add(Game.spawnFood(level));
                    break;
                }
            }
        }

        // Возвращаем сгенерированный список
        return items;
    }

    // Метод для генерации оружия
    public static Item.Weapon spawnWeapon(int level) {

        // Присваиваются начальные значения
        int levelWeapon = level;
        String name = "";
        int damage = 10;
        int price = 10;
        String typeEffect = "";
        int powerEffect = 3;
        int levelCharacter = 1;

        // Случайным образом выбирается название оружия
        Random random = new Random();
        switch (random.nextInt(3))  {
            case 0: {
                name = "Кинжал";
                break;
            }
            case 1: {
                name = "Посох";
                break;
            }
            case 2: {
                name = "Лук";
                break;
            }
        }

        // Случайным образом выбирается тип эффекта
        Random randTypeEffect = new Random();
        switch (randTypeEffect.nextInt(5)) {
            case 0: {
                typeEffect = "Огонь";
                break;
            }
            case 1: {
                typeEffect = "Ветер";
                break;
            }
            case 2: {
                typeEffect = "Вода";
                break;
            }
            case 3: {
                typeEffect = "Лед";
                break;
            }
            case 4: {
                typeEffect = "";
            }
        }

        // Производится расчет параметров оружия
        for (int i = 0; i < level; i++) {
            damage += Math.round(damage * 0.05);
            price += Math.round(price * 0.05);
            if (!typeEffect.equals("")) {

                if (random.nextBoolean()) {
                    powerEffect += Math.round(powerEffect * 0.05);
                }
            }
        }

        return new Item.Weapon(name, price, damage, level, typeEffect, powerEffect, levelCharacter);

    }

    // Метод для генерации брони
    public static Item.Armor spawnArmor(int level) {

        // Присваиваются начальные значения
        int levelArmor = level;
        String name = "";
        int price = 10;
        int defence = 2;
        int levelCharacter = 1;

        // Определение мощьности и цены брони
        for (int i = 0; i < level; i++) {
            price += Math.round(price * 0.05);
            defence += Math.round(defence * 0.05);
        }

        // Случайным образом выбирается название брони
        Random random = new Random();
        // Случайным образом выбирается тип брони и в зависимости от этого выбирается название и сила доп эффекта
        switch (random.nextInt(3)) {
            case 0: {
                name = "Кожаная броня";
                price += Math.round(price * 0.05);
                break;
            }
            case 1: {
                name = "Железная броня";
                price += Math.round(price * 0.08);
                break;
            }
            case 2: {
                name = "Стальной броня";
                price += Math.round(price * 0.1);
                break;
            }
        }
        return new Item.Armor(name, price, defence, level, levelCharacter);
    }

    // Метод для генерации зелий
    private static Item.Potion spawnPotion(int level) {


        // Создаются начальные параметры зелья
        String name = "";
        int price = 10;
        String typeEffect = "";
        int power = 10;

        // Случайным образом выбирается тип зелья. Может быть лечебное или выносливость
        Random random = new Random();
        boolean isPotionEffect = random.nextBoolean();

        // Проверка типа зелья
        if (isPotionEffect) {

            typeEffect = "лечебное";
            name = "Зелье лечения";
        } else {

            typeEffect = "выносливости";
            name = "Зелье выносливости";
        }

        //Если уровень зелья больше 1, то происходит увеличение цены и силы зелья
        if (level > 1) {
            for (int i = 0; i < level; i++) {
                price += Math.round(price * 0.05);
                power += Math.round(power * 0.05);
            }
        }

        return new Item.Potion(name, "Зелье", price, typeEffect, power, level);
    }

    // Метод для генерации еды
    public static Item.Food spawnFood(int level) {

        // Создаются начальные параметры еды
        String name = "";
        int price = 10;
        int power = 10;

        // Случайным образом выбирается название и тип еды + от типа еды зависит сколько сил будет прибавлять
        Random random = new Random();
        int rendFood = random.nextInt(7);

        switch (rendFood) {
            case 0: {
                name = "Яблоко";
                power += 1;
                break;
            }
            case 1: {
                name = "Хлеб";
                power += 2;
                break;
            }
            case 2: {
                name = "Мясо";
                power += 3;
                break;
            }
            case 3: {
                name = "Сыр";
                power += 4;
                break;
            }
            case 4: {
                name = "Молоко";
                power += 5;
                break;
            }
            case 5: {
                name = "Картошка";
                power += 6;
                break;
            }
            case 6: {
                name = "Суп";
                power += 7;
                break;
            }
        }

        // Если уровень еды больше 1, то происходит увеличение цены и силы еды
        if (level > 1) {
            for (int i = 0; i < level; i++) {
                price += Math.round(price * 0.05);
                power += Math.round(power * 0.05);
            }
        }
        return new Item.Food(name, price, power, level);
    }
}
