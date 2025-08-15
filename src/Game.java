// Основная логика игры

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.StrictMath.random;
import static java.lang.Thread.sleep;

public class Game {

    boolean isSave = false;
    boolean isGameToPlay = false;
    int difficulty = 1; // Сложность игры 1 - Легко, 2 - Средне, 3 - Сложно
    boolean isShopSort = false;
    boolean isInventorySort = false;

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

        int maxHealth = 100;
        int health = 100;
        int maxStrength = 100;
        int strength = 100;
        int power = 10;
        int agility = 10;
        int gold = 0;

        health = maxHealth;
        strength = maxStrength;
        // Присваиваются характеристики персонажа в зависимости от уровня
        for (int i = 0; i < level -1 ; i++) {


            maxHealth += Math.round(maxHealth * 0.05);
            maxStrength += Math.round(maxStrength * 0.05);
            power += Math.round(power * 0.05);
            agility += Math.round(agility * 0.05);


        }

        // Если у персонажа будет оружие или броня, то запускается метод создания оружия или брони. Если их нет, то присваивается null
        if (isWeapon) {

            weapon = (Item.Weapon) spawnWeapon(level);
        } else {

            weapon = null;
        }

        if (isArmor) {

            armor = (Item.Armor)spawnArmor(level);
        } else {

            armor = null;
        }

        boolean checkIsMoney = false;
        Random randomMoney = new Random();
        checkIsMoney = randomMoney.nextBoolean();

        if (checkIsMoney) {

            Random intRandomMoney = new Random();
            gold = level * intRandomMoney.nextInt(100);
        }

        // Случайным образом выбирается тип персонажа
        Random randomPerson = new Random();
        int tempCheckPerson = randomPerson.nextInt(4);
        switch (tempCheckPerson) {
            case 0: return new Person.Skeleton("Скелет", maxHealth, power, agility, gold, level, weapon, armor );
            case 1: return new Person.Goblin("Гоблин", maxHealth, power, agility, gold, level, weapon, armor);
            case 2: return new Person.Zombie("Зомби", maxHealth, power, agility, gold, level,weapon, armor);
            case 3: return new Person.Vampire("Вампир", maxHealth, power, agility, gold, level, weapon, armor);
        }
        return null;
    }

    public void options() {

        while (true) {
            String sMag = "";
            String sInv = "";

            if (isShopSort) {
                sMag = "Сортировка магазина включена";
            } else {
                sMag = "Сортировка магазина выключена";
            }

            if (isInventorySort) {
                sInv = "Сортировка инвентаря включена";
            } else {
                sInv = "Сортировка инвентаря выключена";
            }

            String sDef = "";

            switch (difficulty) {
                case 1: {
                    sDef = "Сложность игры: Легко";
                    break;
                }
                case 2: {
                    sDef = "Сложность игры: Средне";
                    break;
                }
                case 3: {
                    sDef = "Сложность игры: Сложно";
                    break;
                }
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Настройки\n\n");
            System.out.println(sInv + "\n" + sMag + "\n" + sDef);
            System.out.println("1 - Сложность игры\n" + "2 - Сортировка инвентаря\n" + "3 - Сортировка магазина\n" + "0 - Назад");
            String otvet = scanner.nextLine();

            switch (otvet) {
                case "1" : {
                    if (difficulty == 1 || difficulty == 2) {
                        difficulty ++;
                        Main.clearConsole();
                    } else {
                        difficulty = 1;
                        Main.clearConsole();
                    }
                    break;
                }
                case "2" : {
                    if (isInventorySort) {
                        isInventorySort = false;
                        Main.clearConsole();
                    } else {
                        isInventorySort = true;
                        Main.clearConsole();
                    }
                    break;
                }
                case "3" : {
                    if (isShopSort) {
                        isShopSort = false;
                        Main.clearConsole();
                    } else {
                        isShopSort = true;
                        Main.clearConsole();
                    }
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

    public void mainMenu() throws CustomException {
        Person player = new Person("");
        Magazine magazine = new Magazine();
        String text = "";
        File file = new File("src/Save.ser");
        while (true) {
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
                    throw new CustomException("Ошибка в файле сохранения!");
                } catch (IOException e) {
                    Main.clearConsole();
                    System.err.println(e.getMessage() + "Файл сохранения поврежден!");
                }
                String sTemp;
                int otvet = -1;

                if (isSave && isGameToPlay) {
                    sTemp = "Главное меню\n\n1 - Начать новую игру\n2 - Продолжить игру\n3 - Загрузить игру\n4 - Настройки\n5 - Сохранить\n0 - Выйти из игры";
                    otvet = Main.checkInt(sTemp, 5);
                    switch (otvet) {
                        case 1: {
                            player = startNewGame();
                            magazine = new Magazine();
                            magazine.spawnMagazine(player.getLevel());
                            Main.clearConsole();
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
                            options();
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
                            options();
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
                            Main.clearConsole();
                            continueGame(player, magazine);
                            break;
                        }
                        case 3: {
                            Main.clearConsole();
                            options();
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
                            options();
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
            } else {
                try {
                    File save = new File("src/Save.ser");
                    save.createNewFile();
                    System.out.println("Файл сохранения создан!");
                } catch (Exception e) {
                    Main.clearConsole();
                    System.out.println("Ошибка при создании файла сохранения!");
                }
            }
        }
    }

    public Person startNewGame() {

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

                    break;
                }
                default: {
                }
            }
        }
    }

    public void quitGame(Person player) {

        System.out.println();
        switch (Main.checkInt("Вы хотите выйти из игры?\n1. Да\n2. Нет",2)) {

            case 1: {

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
                System.out.println("Вы вернулись в главное меню");
                break;
            }
        }
    }

    public void startGame(Person player, Magazine magazine) {

        while (true) {

            System.out.println("Выносливость: " + player.getStrength() + "/" + player.getMaxStrength() + "\n");

            switch (Main.checkInt("Действия: \n\n1 - Открыть инвентарь\n" +
                    "2 - идти в магазин\n" +
                    "3 - идти в темный лес\n" +
                    "4 - идти к кузнецу\n" +
                    "5 - отдыхать\n" +
                    "0 - Меню\n", 5)) {
                case 1: {
                    player.openInventary();
                    break;
                }
                case 2: {
                    magazine.openMagazine(player);
                    break;
                }
                case 3: {

                    Fight fight = new Fight();
                    fight.start();
                    break;
                }
                case 4: {
                    try {
                        goToTheBlacksmith(player);
                    } catch (CustomException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                }
                case 5: {
                    Main.clearConsole();
                    toTakeADreak(player, magazine);
                    break;
                }
                case 0: {
                    return;
                }
                default: {
                    System.out.println("Некорректный ввод!");
                }
            }
        }
    }

    private void toTakeADreak(Person player, Magazine magazine) {
        player.setStrength(player.getMaxStrength());
        magazine.spawnMagazine(player.getLevel());
    }

    public void continueGame(Person player, Magazine magazine) {
        startGame(player, magazine);

    }

    private void goToTheBlacksmith(Person player) throws CustomException {
        if (player.getStrength() >= 10) {
            player.move(1);
            if (!player.getIsAlive()) {
                System.out.println("Вы умерли!");
                return;
            }
            Main.clearConsole();
            System.out.println("Привет, " + player.getName() + " я кузнец!\n");
            ArrayList<Item.Weapon> weapons = new ArrayList<>();
            ArrayList<Item.Armor> armors = new ArrayList<>();

            player.getInventory().forEach(item -> {
                if (item.getType().equals("Оружие")) {
                    weapons.add((Item.Weapon) item);
                } else if (item.getType().equals("Броня")) {
                    armors.add((Item.Armor) item);
                }
            });
            while (true) {

                int count = 0;
                int count2 = 0;
                switch (Main.checkInt("Кузнец: Я могу прокачать или зачаровать твои вещи! Что ты хочешь сделать?\n1 - Прокачать вещь\n2 - Зачаровать вещь\n0 - Попрощаться", 2)) {

                    case 1: {
                        while (true) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Твои вещи:\n");
                            sb.append("Оружие:\n");
                            for (Item.Weapon weapon : weapons) {
                                sb.append(count + " - " + weapon.getName() + " " + weapon.getLevelChange() + " ур.\n");
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
                                sb.append(count + " - " + armor.getName() + " " + armor.getLevelChange() + " ур.\n");
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
                                        Item.Weapon weapon = (Item.Weapon) items.get(tempInt);
                                        switch (Main.checkInt("Вы хотите прокачать " + weapon.getName() + "?\n1 - Да\n2 - Нет", 2)) {
                                            case 1: {
                                                int tempIntLevel = Main.checkInt("Введите на сколько хотите прокачать: ", 19);
                                                if (tempIntLevel > 0 && tempIntLevel <= 20) {
                                                    Item.Weapon weaponNew = (Item.Weapon) items.get(tempInt);
                                                    Item.Weapon weaponOld = (Item.Weapon) items.get(tempInt);
                                                    if (weaponNew.getLevelChange() + tempIntLevel <= 20) {
                                                        int tempPrice = weaponNew.getPrice();
                                                        while (tempIntLevel > 0) {
                                                            tempIntLevel--;
                                                            tempPrice += weaponNew.getPrice();
                                                            weaponNew.levelUp();
                                                        }
                                                        System.out.println("Цена прокачки: " + tempPrice);
                                                        switch (Main.checkInt("1 - Да\n2 - Нет", 2)) {
                                                            case 1: {
                                                                if (player.getGold() >= tempPrice) {
                                                                    player.deliteGold(tempPrice);
                                                                    player.removeItem(weaponOld);
                                                                    if (player.getWeapon().equals(weaponNew)) {
                                                                        player.setWeapon(null);
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
                                                                    System.err.println("Недостаточно денег!");
                                                                }
                                                            }
                                                            case 2: {
                                                                System.out.println("Возврат");
                                                            }
                                                        }

                                                    } else {
                                                        System.err.println("Невозможно прокачать на такую величину!\nМаксимальный уровень может быть 20");
                                                    }
                                                }
                                                break;
                                            }
                                            case 2: {
                                                System.out.println("Возврат");
                                                break;
                                            }
                                            default: {
                                                System.err.println("Некорректный ввод!");
                                            }
                                        }
                                    } else if (items.get(tempInt).getType().equals("Броня")) {
                                        Item.Armor armor = (Item.Armor) items.get(tempInt);
                                        switch (Main.checkInt("Вы хотите прокачать " + armor.getName() + "?\n1 - Да\n2 - Нет", 2)) {
                                            case 1: {
                                                int tempIntLevel = Main.checkInt("Введите на сколько хотите прокачать: ", 19);
                                                if (tempIntLevel > 0 && tempIntLevel <= 20) {
                                                    Item.Armor armorNew = (Item.Armor) items.get(tempInt);
                                                    Item.Armor oldArmor = (Item.Armor) items.get(tempInt);
                                                    if (armorNew.getLevelChange() + tempIntLevel <= 20) {
                                                        int tempPrice = armorNew.getPrice();
                                                        while (tempIntLevel > 0) {
                                                            tempIntLevel--;
                                                            armorNew.levelUp();
                                                            tempPrice += armorNew.getPrice();
                                                        }
                                                        System.out.println("Цена прокачки: " + tempPrice);
                                                        switch (Main.checkInt("1 - Да\n2 - Нет", 2)) {
                                                            case 1: {
                                                                if (player.getGold() >= tempPrice) {
                                                                    player.deliteGold(tempPrice);
                                                                    if (player.getArmor().equals(armorNew)) {
                                                                        player.setArmor(null);
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
                                                                break;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    System.err.println("Невозможно прокачать на такую величину!\nМаксимальный уровень может быть 20");
                                                }
                                                break;
                                            }
                                            case 2: {
                                                System.out.println("Возврат");
                                                break;
                                            }
                                            default: {
                                                System.err.println("Некорректный ввод!");
                                            }

                                        }

                                    }
                                } else {
                                    System.err.println("Некорректный ввод!");
                                }
                            } else {
                                return;
                            }
                        }
                    }
                    case 2: {
                        count = 0;
                        StringBuilder sbChar = new StringBuilder();
                        sbChar.append("Твои вещи:\n");
                        sbChar.append("Оружие:\n");
                        for (Item.Weapon weapon : weapons) {
                            sbChar.append(count + " - " + weapon.getName() + " " + weapon.getLevelChange() + " ур.\n");
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

                                        System.out.println("Вы хотите зачаровать " + itemWeaponOld.getName() + "\n");
                                        System.out.println("Стоимость зачарования: " + (itemWeaponOld.getPrice()) * 3);
                                        switch (Main.checkInt("1 - Да\n2 - Нет", 2)) {
                                            case 1: {
                                                Random randomChar = new Random();
                                                int chanceChar = randomChar.nextInt(5);
                                                if (player.getGold() >= (itemWeaponOld.getPrice()) * 3) {
                                                    int tempPowerEffect = 5 + randomChar.nextInt(100);
                                                    switch (chanceChar) {
                                                        case 0: {
                                                            System.out.println("Зачарование прошло неудачно!");
                                                            System.out.println("Вы потеряли " + itemWeaponOld + (itemWeaponOld.getPrice() * 3) + " золота!");
                                                            player.deliteGold(itemWeaponOld.getPrice() * 3);
                                                            player.removeItem(itemOld);
                                                            if (player.getWeapon().equals(itemWeaponOld)) {
                                                                player.setWeapon(null);
                                                            }
                                                            break;
                                                        }
                                                        case 1: {

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
                                                    System.out.println("Недостаточно золота!");
                                                }

                                                break;
                                            }
                                            case 2: {
                                                System.out.println("Возврат");
                                                break;
                                            }
                                            default: {
                                                System.err.println("Некорректный ввод!");
                                            }
                                        }
                                    } else {
                                        System.err.println("Нельзя чаровать. Предмет уже зачарован!");
                                    }
                                }
                            } else {
                                System.err.println("Некорректный ввод!");
                            }
                        } else {
                            System.out.println("Возврат");
                        }
                        break;
                    }
                    case 0: {
                        System.out.println("Пока!");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new CustomException("Ошибка при ожидании!");
                        }
                        return;
                    }
                }

            }
        } else {
            Main.clearConsole();
            System.err.println("Недостаточно силы!");

        }

    }

    public static Item.Weapon spawnWeapon(int level) {

        int levelWeapon = level;
        String name = "";
        int damage = 10;
        int price = 10;
        String typeEffect = "";
        int powerEffect = 0;
        int levelCharacter = 1;

        Random random = new Random();
        switch (random.nextInt(3))  {
            case 0: {
                name = "Кинжал";
            }
            case 1: {
                name = "Посох";
            }
            case 2: {
                name = "Лук";
            }
        }

        Random randTypeEffect = new Random();
        switch (randTypeEffect.nextInt(5)) {
            case 0: {
                typeEffect = "огонь";

            }
            case 1: {
                typeEffect = "воздух";
            }
            case 2: {
                typeEffect = "вода";
            }
            case 3: {
                typeEffect = "лед";
            }
            case 4: {
                typeEffect = "";
            }

        }

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

    public static Item.Armor spawnArmor(int level) {

        int levelArmor = level;
        String name = "";
        int price = 10;
        int defence = 10;
        int levelCharacter = 1;

        Random random = new Random();

        for (int i = 0; i < level; i++) {
            price += Math.round(price * 0.05);
            defence += Math.round(defence * 0.05);
        }

        // Случайным образом выбирается тип брони и в зависимости от этого выбирается название и сила доп эффекта
        switch (random.nextInt(3)) {
            case 0: {
                name = "Кожаная броня";
                price += Math.round(price * 0.05);
            }
            case 1: {
                name = "Железная броня";
                price += Math.round(price * 0.08);
            }
            case 2: {
                name = "Стальной броня";
                price += Math.round(price * 0.1);
            }
        }
        return new Item.Armor(name, price, defence, level, levelCharacter);
    }

    // метод для дропа предметов из поверженного противника. В первый параметр передается игрок, которому будет присвоен дроп. Во второй параметр передается поверженный противник.
    public static void lootOfPerson(Person player, Person isDeadPerson) {

        ArrayList<Item> loot = new ArrayList<>();

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("Вы получили: \n----------------------------------------------------\n");

        // Проверка выпало ли оружие. Если да, то добавляется в список дропа
        if (random.nextBoolean() && isDeadPerson.getWeapon() != null) {
            loot.add(isDeadPerson.getWeapon());
            sb.append(isDeadPerson.getWeapon().print());
        }

        // Проверка выпало ли броня. Если да, то добавляется в список дропа
        if (random.nextBoolean() && isDeadPerson.getArmor() != null) {
            loot.add(isDeadPerson.getArmor());
            sb.append(isDeadPerson.getArmor().print());
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

    public static ArrayList generateItem(int countItem, int level) {

        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < countItem; i++) {

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
        return items;
    }

    private static Item.Potion spawnPotion(int level) {

        Random random = new Random();
        boolean isPotionEffect = random.nextBoolean();
        String name = "";
        int price = 10;
        String typeEffect = "";
        int power = 10;

        if (isPotionEffect) {

//          typeEffect = "лечебное";
            name = "Зелье лечения";
        } else {

            typeEffect = "выносливости";
            name = "Зелье выносливости";
        }

        if (level > 1) {
            for (int i = 0; i < level; i++) {
                price += Math.round(price * 0.05);
                power += Math.round(power * 0.05);
            }
        } else {

        }
        return new Item.Potion(name, price, "Зелье", power, level);
    }

    public static Item.Food spawnFood(int level) {

        String name = "";
        int price = 10;
        int power = 10;
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

        if (level > 1) {
            for (int i = 0; i < level; i++) {
                price += Math.round(price * 0.05);
                power += Math.round(power * 0.05);
            }
        } else {

        }
        return new Item.Food(name, price, power, level);
    }
}
