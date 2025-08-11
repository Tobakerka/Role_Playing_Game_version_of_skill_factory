// Основная логика игры

import java.util.ArrayList;
import java.util.Random;

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

            weapon = spawnWeapon(level);
        } else {

            weapon = null;
        }

        if (isArmor) {

            armor = spawnArmor(level);
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

                maxHealth += Math.round(maxHealth * 0.2);
                maxStrength += Math.round(maxStrength * 0.2);
                power += Math.round(power * 0.2);
                agility += Math.round(agility * 0.2);
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
        int price = 2;

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
            damage += damage * 0.2;
            price += price * 0.2;
        }

        return new Item.Weapon(name, damage, price, level);
    }

    public static Item.Armor spawnArmor(int level) {

        int levelArmor = level;
        String name = "";
        int price = 2;
        int defence = 10;

        Random random = new Random();

        for (int i = 0; i < level; i++) {
            price += price * 0.2;
            defence += defence * 0.2;
        }

        // Случайным образом выбирается тип брони и в зависимости от этого выбирается название и сила доп эффекта
        switch (random.nextInt(3)) {
            case 0: {
                name = "Кожаная броня";
                price += Math.round(price * 0.2);
            }
            case 1: {
                name = "Железная броня";
                price += Math.round(price * 0.4);
            }
            case 2: {
                name = "Стальной броня";
                price += Math.round(price * 0.6);
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

    }

    public static void generateItem() {


    }
}
