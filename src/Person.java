import java.util.ArrayList;

public abstract class Person {

    ArrayList<Item> inventory = new ArrayList<>();

    private String name;
    private double maxHealth, health;
    private int power, agility, gold, level ;
    private long levelUpThreshold; // порог для повышения уровня
    private long exp;
    private int maxStrength;
    private int Strength;
    private boolean isAlive;

    private Item.Weapon weapon;
    private Item.Armor armor;

    // Конструктор для создания персонажа
    public Person(String name) {

        this.name = name;
        maxStrength = 100;
        Strength = 100;
        isAlive = true;
        Item.Weapon weapon = null;
        Item.Armor armor = null;
    }

    public Person(String name, int maxHealth, int power, int agility, int gold, Item.Weapon weapon, Item.Armor armor) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.power = power;
        this.agility = agility;
        this.gold = gold;
        this.weapon = weapon;
        this.armor = armor;
        this.isAlive = true;
    }
    // Геттеры и сеттеры:
    public long getlevelUpThreshold() {

        return levelUpThreshold;
    }

    public Item.Weapon getWeapon() {

        return weapon;
    }

    public void setWeapon(Item.Weapon weapon) {
        this.weapon = weapon;
    }

    public Item.Armor getArmor() {

        return armor;
    }

    public void setArmor(Item.Armor armor) {
        this.armor = armor;
    }

    public int getGold() {
        return gold;
    }

    // Методы:

    // Метод для повышения уровня
    public void levelUp(long givenExp) {

        System.out.println("Вы получили " + givenExp + " опыта!");

        while (givenExp >= this.levelUpThreshold) {

            givenExp -= levelUpThreshold;
            level++;
            maxHealth += Math.round(maxHealth * 0.2);
            health = maxHealth;
            maxStrength += Math.round(maxStrength * 0.2);
            Strength = maxStrength;
            power += Math.round(power * 0.2);
            agility += Math.round(agility * 0.2);

            levelUpThreshold += Math.round(levelUpThreshold * 0.2);
            System.out.println("Уровень повышен!" + " Текущий уровень: " + level);
        }
        if (givenExp < this.levelUpThreshold) {
            this.exp += givenExp;
        }
        System.out.println("Текущий опыт: " + exp + " / " + levelUpThreshold);
        System.out.println();
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    // Метод открывает инвентарь и предлагает действия с предметами
    public void openInventory() {

        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    // печатает информацию о персонаже
    public void showStats() {

        if (isAlive) {
            System.out.println("Имя: " + name);
            System.out.println("Уровень: " + level);
            System.out.println("Здоровье: " + health + "/" + maxHealth);
            if (maxStrength != 0) {
                System.out.println("Выносливость: " + Strength + "/" + maxStrength);
            }
            System.out.println("Сила: " + power);
            System.out.println("Ловкость: " + agility);
            System.out.println("Золото: " + gold);
            System.out.println();
            showWeapon();
            showArmor();
        } else {
            System.out.println("Персонаж мертв!");
        }

    }

    // печатает информацию о одетом оружии
    public void showWeapon() {

        if (weapon != null) {

            System.out.print(weapon.print());
        } else {
            System.out.println("нет оружия!\n");
        }
    }

    // печатает информацию о одетой броне
    public void showArmor() {

        if (armor != null) {
            System.out.print(armor.print());
        } else {
            System.out.println("нет брони!\n");
        }

    }
    // Конструктор для удобного создания противника для процедурной генерации

    // Класс для игрока
    public static class Human extends Person {

        public Human(String name) {

            super(name);
            super.maxHealth = 100;
            super.health = 100;
            super.power = 10;
            super.agility = 10;
            super.gold = 0;
            super.level = 1;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
            super.setWeapon(new Item.Weapon("Кинжал", 10, 10, 1));
            super.setArmor(new Item.Armor("Кожаная кираса", 10, 10, 1));
            super.isAlive = true;

        }
    }

    // Класс для игрока
    public static class Elf extends Person {

        public Elf(String name) {

            super(name);
            super.maxHealth = 80;
            super.health = 80;
            super.power = 8;
            super.agility = 15;
            super.gold = 0;
            super.level = 1;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
            super.setWeapon(new Item.Weapon("Лук", 10, 10, 1));
            super.setArmor(new Item.Armor("Кожаная кираса", 10, 10, 1));
            super.isAlive = true;
        }
    }

    // Класс противника
    public static class Skeleton extends Person {

        public Skeleton(String name, int maxHealth, int power, int agility, int gold, int level, Item.Weapon weapon, Item.Armor armor) {

            super(name, maxHealth, power, agility, gold, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
            super.isAlive = true;
        }
    }

    // Класс противника
    public static class Zombie extends Person {

        public Zombie(String name, int maxHealth, int power, int agility, int gold, int level, Item.Weapon weapon, Item.Armor armor) {

            super(name, maxHealth, power, agility, gold, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
            super.isAlive = true;
        }
    }

    // Класс противника
    public static class Goblin extends Person {

        public Goblin(String name, int maxHealth, int power, int agility, int gold, int level, Item.Weapon weapon, Item.Armor armor) {

            super(name, maxHealth, power, agility, gold, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
        }
    }
}
