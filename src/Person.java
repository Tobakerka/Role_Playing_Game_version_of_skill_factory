public abstract class Person {

    String name;
    double maxHealth, health;
    int power, agility, gold, level ;
    long levelUpThreshold; // порог для повышения уровня
    long exp;

    // Конструктор для создания персонажа
    public Person(String name) {

        this.name = name;
    }

    // Метод для повышения уровня
    public void levelUp(long givenExp, long levelUpThreshold) {

        System.out.println("Вы получили " + givenExp + " опыта!");

        while (givenExp >= levelUpThreshold) {

            givenExp -= levelUpThreshold;
            level++;
            levelUpThreshold += Math.round(levelUpThreshold * 0.2);
            System.out.println("Уровень повышен!" + " Текущий уровень: " + level);
        }
        if (givenExp < levelUpThreshold) {
            this.exp += givenExp;
        }
        System.out.println("Текущий опыт: " + exp + " / " + levelUpThreshold);

    }

    // Конструктор для удобного создания противника для процедурной генерации
    public Person(String name, int maxHealth, int power, int agility, int gold) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.power = power;
        this.agility = agility;
        this.gold = gold;
    }

    // Класс для игрока
    public static class Human extends Person {

        public Human(String name) {

            super(name);
            super.maxHealth = 100;
            super.health = maxHealth;
            super.power = 10;
            super.agility = 10;
            super.gold = 0;
            super.level = 1;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
        }
    }

    // Класс для игрока
    public static class Elf extends Person {

        public Elf(String name) {

            super(name);
            super.maxHealth = 80;
            super.health = maxHealth;
            super.power = 8;
            super.agility = 15;
            super.gold = 0;
            super.level = 1;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
        }
    }

    // Класс противника
    public static class Skeleton extends Person {

        public Skeleton(String name, int maxHealth, int power, int agility, int gold, int level) {

            super(name, maxHealth, power, agility, gold);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
        }
    }

    // Класс противника
    public static class Zombie extends Person {

        public Zombie(String name, int maxHealth, int power, int agility, int gold, int level) {

            super(name, maxHealth, power, agility, gold);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
        }
    }
}
