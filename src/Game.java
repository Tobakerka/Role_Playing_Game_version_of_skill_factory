// Основная логика игры

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.StrictMath.random;

public class Game {

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
            case 0: level = level += 1;
            case 1: level = level += 2;
            case 2: level = level -= 1;
            case 3: level = level -= 2;
            case 4: level = level;
        }

        // Если уровень получился отрицательным, то он становится равным 1
        if (level < 1) {

            level = 1;
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

        int maxHealth = 100;
        int health = 100;
        int maxStrength = 100;
        int strength = 100;
        int power = 10;
        int agility = 10;
        int gold = 0;

        // Присваиваются характеристики персонажа в зависимости от уровня
        for (int i = 0; i < level; i++) {

            if (level > 1) {

                maxHealth += Math.round(maxHealth * 0.05);
                maxStrength += Math.round(maxStrength * 0.05);
                power += Math.round(power * 0.05);
                agility += Math.round(agility * 0.05);
            } else {
                maxHealth = 100;
                maxHealth = 100;
                power = 10;
                agility = 10;
            }

        }
        health = maxHealth;
        strength = maxStrength;

        boolean checkIsMoney = false;
        Random randomMoney = new Random();
        checkIsMoney = randomMoney.nextBoolean();

        if (checkIsMoney) {

            Random intRandomMoney = new Random();
            gold = level * intRandomMoney.nextInt(100);
        }

        // Случайным образом выбирается тип персонажа
        Random randomPerson = new Random();
        int tempCheckPerson = randomPerson.nextInt(3);
        switch (tempCheckPerson) {
            case 0:
                return new Person.Skeleton("Скелет", maxHealth, power, agility, gold, level, weapon, armor );
            case 1:
                return new Person.Goblin("Гоблин", maxHealth, power, agility, gold, level, weapon, armor);
            case 2:
                return new Person.Zombie("Зомби", maxHealth, power, agility, gold, level,weapon, armor);
        }
        return null;
    }

    public static Item.Weapon spawnWeapon(int level) {

        int levelWeapon = level;
        String name = "";
        int damage = 10;
        int price = 10;

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

        for (int i = 0; i < level; i++) {
            damage += Math.round(damage * 0.05);
            price += Math.round(price * 0.05);
        }

        return new Item.Weapon(name, damage, price, level);

    }

    public static Item.Armor spawnArmor(int level) {

        int levelArmor = level;
        String name = "";
        int price = 10;
        int defence = 10;

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
        return new Item.Armor(name, price, defence, level);
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
