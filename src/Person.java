import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Person implements Serializable{

    private int countInventory;
    ArrayList<Item> inventory = new ArrayList<>(countInventory);

    private String name;
    private double maxHealth, health;
    private int power, agility, gold, level ;
    private long levelUpThreshold; // порог для повышения уровня
    private long exp;
    private int maxStrength;
    private int strength;
    private boolean isAlive;
    private String race;

    // Возможные уязвимости
    private boolean vulnerabilityOfFire;
    private boolean vulnerabilityOfIce;
    private boolean vulnerabilityOfWind;
    private boolean vulnerabilityOfWater;

    // Возможные резисты
    private boolean resistanceOfFire;
    private boolean resistanceOfIce;
    private boolean resistanceOfWind;
    private boolean resistanceOfWater;

    // Экипировка:
    private Item.Weapon weapon;
    private Item.Armor armor;

    // Конструктор для создания персонажа
    public Person(String name) {

        this.name = name;
        maxStrength = 100;
        strength = 100;
        isAlive = true;
        Item.Weapon weapon = null;
        Item.Armor armor = null;

        for (Item item : inventory) {
            inventory.add(null);
        }
    }

    // Конструктор для удобного создания противника для процедурной генерации

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
        this.countInventory = 20;
        this.strength = 100;
    }

    // Геттеры и сеттеры:
    public long getlevelUpThreshold() {

        return levelUpThreshold;
    }

    public void setStrength(int strength) {
        if (strength <= maxStrength) {
            this.strength = strength;
        } else {
            this.strength = maxStrength;
        }
    }

    public int getStrength() {
        return strength;
    }

    public int getMaxStrength() {
        return maxStrength;
    }

    public void setMaxStrength(int maxStrength) {
        if (maxStrength > this.maxStrength) {
            this.maxStrength = maxStrength;
        } else {
            System.out.println("Нельзя установить значение меньше максимального");
        }
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean getVulnerabilityOfFire() {
        return vulnerabilityOfFire;
    }

    public boolean getVulnerabilityOfIce() {
        return vulnerabilityOfIce;
    }

    public boolean getVulnerabilityOfWind() {
        return vulnerabilityOfWind;
    }

    public boolean getVulnerabilityOfWater() {
        return vulnerabilityOfWater;
    }

    public boolean getResistanceOfFire() {
        return resistanceOfFire;
    }

    public boolean getResistanceOfIce() {
        return resistanceOfIce;
    }

    public boolean getResistanceOfWind() {
        return resistanceOfWind;
    }

    public boolean getResistanceOfWater() {
        return resistanceOfWater;
    }

    public int getLevel() {
        return level;
    }

    public String getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public int getCountInventory() {
        return countInventory;
    }

    public void setStrength(int power, int maxStrength) {

        int temp =  + maxStrength;
        if (temp > maxStrength) {
            this.strength = maxStrength;
        } else {
            this.strength += temp;
        }
    }
    private void setHealth(int power, double maxHealth) {

        double temp = this.health + health;
        if (temp > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health += temp;
        }
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
    // Методы:
    // Метод списания золота за покупку предмета

    public void deliteGold(int price) {
        gold -= price;
    }
    // Метод для проверки резистов и уязвимостей

    public int checkVulnerabilityResistanceOfDamage(String element, int powerElement, int damage) {

        switch (element) {

            case "огонь": {
                if (vulnerabilityOfFire) {
                    System.out.println("Уезвимость: Урон с огнем повышен на " + powerElement);
                    return damage + powerElement;
                } else {
                    if (resistanceOfFire) {
                        int temp = (int) Math.rint(damage / 0.5);
                        if (temp > 0) {
                            System.out.println("Резист: Урон с огнем уменьшен на половину " + temp);
                            return temp;
                        } else {
                            System.out.println("Резист: Урон равен нулю");
                            return 0;
                        }
                    } else {
                        System.out.println("Урон равен " + damage);
                        return damage;
                    }
                }
            }
            case "вода": {
                if (vulnerabilityOfWater) {
                    System.out.println("Уезвимость: Урон с огнем повышен на " + powerElement);
                    return damage + powerElement;
                } else {
                    if (resistanceOfWater) {
                        int temp = (int) Math.rint(damage / 0.5);
                        if (temp > 0) {
                            System.out.println("Резист: Урон с водой уменьшен на половину " + temp);
                            return temp;
                        } else {
                            System.out.println("Резист: Урон равен нулю");
                            return 0;
                        }
                    } else {
                        return damage;
                    }
                }
            }
            case "лед": {
                if (vulnerabilityOfIce) {

                    System.out.println("Уезвимость: Урон со льдом повышен на " + powerElement);
                    return damage + powerElement;
                } else {
                    if (resistanceOfIce) {

                        int temp = (int) Math.rint(damage / 0.5);
                        if (temp > 0) {
                            System.out.println("Резист: Урон со льдом уменьшен на половину " + temp);
                            return temp;
                        } else {
                            System.out.println("Резист: Урон равен нулю");
                            return 0;
                        }
                    } else {
                        return damage;
                    }
                }
            }
            case "ветер" : {
                if (vulnerabilityOfWind) {
                    System.out.println("Уезвимость: Урон с ветром повышен на " + powerElement);
                    return damage + powerElement;
                } else {
                    if (resistanceOfWind) {
                        int temp = (int) Math.rint(damage / 0.5);
                        if (temp > 0) {
                            System.out.println("Резист: Урон с ветром уменьшен на половину " + temp);
                            return temp;
                        } else {
                            System.out.println("Резист: Урон равен нулю");
                            return 0;
                        }
                    } else {
                        return damage;
                    }
                }
            }
            default: {
                return 0;
            }
        }
    }
    // Метод для повышения уровня

    public void levelUp(long givenExp) {

        System.out.println("Вы получили " + givenExp + " опыта!");

        while (givenExp >= this.levelUpThreshold) {

            givenExp -= levelUpThreshold;
            level++;
            maxHealth += Math.round(maxHealth * 0.05);
            health = maxHealth;
            maxStrength += Math.round(maxStrength * 0.05);
            strength = maxStrength;
            power += Math.round(power * 0.05);
            agility += Math.round(agility * 0.05);

            levelUpThreshold += Math.round(levelUpThreshold * 0.05);
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

    public void openInventary() {

        boolean check = true;
        while (check) {
        lookInventory();

        System.out.println("Выберите предмет \nлюбая буква или символ для выхода");
        Scanner scanner = new Scanner(System.in);

        int tempInt = 0;
        if (scanner.hasNextInt()) {
            tempInt = scanner.nextInt();

            if (tempInt < inventory.size() && tempInt >= 0) {

                if (inventory.get(tempInt).getType().equals("Оружие")) {

                    if (inventory.get(tempInt).equals(weapon)) {

                        System.out.println("1 - снять, 2 - удалить, 3 - информация, 0 - назад");
                        Scanner scannerWeapon = new Scanner(System.in);
                        String tempWeapon = scannerWeapon.nextLine();
                        switch (tempWeapon) {
                            case "1": {
                                weapon = null;
                                System.out.println("Оружие снято");
                                break;
                            }
                            case "2": {
                                weapon = null;
                                removeItem(inventory.get(tempInt));
                                System.out.println("Оружие удалено");
                                break;
                            }
                            case "3": {
                                inventory.get(tempInt).getInfo();
                                return;
                            }
                            case "0": {
                                System.out.println("Возврат");
                                return;
                            }
                            default: {
                                System.out.println("Неверный ввод");
                            }
                        }
                    } else {

                        System.out.println("1 - надеть, 2 - удалить, 3 - информация, 0 - назад");
                        Scanner scannerWeapon1 = new Scanner(System.in);
                        String tempWeaponToEqup = scannerWeapon1.nextLine();
                        switch (tempWeaponToEqup) {
                            case "1": {
                                if (inventory.get(tempInt).getLevel() <= level) {
                                    weapon = (Item.Weapon) inventory.get(tempInt);
                                    System.out.println("Оружие надето");
                                } else {
                                    System.out.println("Уровня персонажа не достаточно!");
                                }
                                break;
                            }
                            case "2": {
                                removeItem(inventory.get(tempInt));
                                System.out.println("Оружие удалено");
                                break;
                            }
                            case "3": {
                                Item.Weapon weapon1 = (Item.Weapon) inventory.get(tempInt);
                                weapon1.getInfo();
                                break;
                            }
                        }
                    }
                } else if (inventory.get(tempInt).getType().equals("Броня")) {

                    if (inventory.get(tempInt).equals(armor)) {
                        System.out.println("1 - снять, 2 - удалить, 3 - информация, 0 - назад");
                        Scanner scannerArmor = new Scanner(System.in);
                        String tempArmor = scannerArmor.nextLine();
                        switch (tempArmor) {
                            case "1": {
                                armor = null;
                                System.out.println("Броня снята");
                                break;
                            }
                            case "2": {
                                armor = null;
                                removeItem(inventory.get(tempInt));
                                System.out.println("Броня удалена");
                                break;
                            }
                            case "3": {
                                inventory.get(tempInt).getInfo();
                                break;
                            }
                            case "0": {
                                System.out.println("Возврат");
                                return;
                            }
                            default: {
                                System.out.println("Возврат");
                                return;
                            }
                        }
                    } else {
                        System.out.println("1 - надеть, 2 - удалить, 3 - информация, 0 - назад");
                        Scanner scannerArmorToEqup = new Scanner(System.in);
                        String tempArmorToEqup = scannerArmorToEqup.nextLine();
                        switch (tempArmorToEqup) {
                            case "1": {
                                if (inventory.get(tempInt).getLevel() <= level) {
                                    armor = (Item.Armor) inventory.get(tempInt);
                                    System.out.println("Броня надета");
                                } else {
                                    System.out.println("Уровня персонажа не достаточно!");
                                }
                                break;
                            }
                            case "2": {
                                armor = null;
                                removeItem(inventory.get(tempInt));
                                System.out.println("Броня удалена");
                                break;
                            }
                            case "3": {
                                inventory.get(tempInt).getInfo();
                            }
                            case "0": {
                                System.out.println("Возврат");
                                return;
                            }
                            default: {
                                System.out.println("Возврат");
                                    return;
                                }
                            }
                        }
                    } else if (inventory.get(tempInt).getType().equals("Зелье")) {

                        System.out.println("1 - выпить, 2 - удалить, 0 - назад");
                        Scanner scannerPotion = new Scanner(System.in);
                        String tempPotion = scannerPotion.nextLine();
                        switch (tempPotion) {
                            case "1": {
                                Item.Potion potion = (Item.Potion) inventory.get(tempInt);
                                if (potion.level <= level) {
                                    if (potion.typeEffect.equals("лечебное")) {
                                        setHealth(potion.getPower(), maxHealth);

                                        System.out.println("Вы выпили зелье здоровья. Здоровье: + " + potion.getPower());
                                    } else if (potion.typeEffect.equals("выносливости")){
                                        setStrength(potion.getPower(), maxStrength);
                                        System.out.println("Вы выпили зелье " + potion.typeEffect + potion.getPower());
                                        inventory.remove(potion);
                                    }
                                } else {
                                    System.out.println("Уровня персонажа не достаточно!");
                                }

                                break;
                            }
                            case "2": {

                                inventory.remove(inventory.get(tempInt));
                                System.out.println("Зелье удалено");
                                break;
                            }
                        }
                    } else {
                        System.out.println("1 - съесть, 2 - удалить, 3 - информация, 0 - назад");
                        Scanner scannerFood = new Scanner(System.in);
                        String tempFood = scannerFood.nextLine();
                        if (tempFood.equals("1")) {
                            Item.Food food = (Item.Food) inventory.get(tempInt);
                            setStrength(food.getPower(), maxStrength);
                            System.out.println("Вы съели " + food.getName() + ". Сила: + " + food.getPower());
                            removeItem(inventory.get(tempInt));
                        } else if (tempFood.equals("2")) {
                                inventory.remove(inventory.get(tempInt));
                                System.out.println("Предмет удален");
                        } else if (tempFood.equals("3")) {
                            inventory.get(tempInt).getInfo();
                        }
                    }
                } else {
                    System.out.println("Неверный ввод");
                    return;
                }
            } else {
                System.out.println("Возврат");
                check = false;
            }
        }
    }
    // печатает информацию о персонаже

    public void showStats() {

        if (isAlive) {
            if (!name.equals("")) {

                System.out.println("Имя: " + name);
            }

            System.out.println("Род: " + race);;
            System.out.println("Уровень: " + level);
            System.out.println("Здоровье: " + health + "/" + maxHealth);
            if (maxStrength != 0) {
                System.out.println("Выносливость: " + strength + "/" + maxStrength);
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
    // Метод для добавления предмета в инвентарь

    public void addInventory(Item item) {

        if (inventory.size() < countInventory && item != null) {
            inventory.add(item);
            System.out.println("Предмет добавлен в инвентарь\n");
        } else if (item == null){
            System.out.println("Невозможно добавить предмет\n");
        } else {
            System.out.println("Инвентарь заполнен\n");
        }
    }
    // Метод для удаления предмета из инвентаря

    public void removeItem(Item item){

        inventory.remove(item);
    }
    // Метод для вывода содержимого инвентаря

    public void lookInventory() {
        int count = 0;
        int schet = 0;

        System.out.println("Инвентарь:" + "\n" +
                "Ваше золото: " + gold + "\n" +
                "Предметы:" + inventory.size() + " из " + countInventory + "\n");

        if (inventory.size() == 0) {

            System.out.println("Инвентарь пуст");
        } else {

            for (Item item : inventory) {

                String tempName = item.getName();
                if (item.getType().equals("Оружие")) {
                    Item.Weapon weapon = (Item.Weapon) item;
                    tempName += " " + weapon.getLevel() + " Ур. " + weapon.getLevelChange() + " точ. ";
                    if (!weapon.getTypeEffect().equals("")) {
                        tempName +=  weapon.getTypeEffect() + " " + weapon.getPowerEffect() + "ед. ";
                    }
                }
                if (item.getType().equals("Броня")) {
                    Item.Armor armor = (Item.Armor) item;
                    tempName += " " + armor.getLevel() + " Ур. " + armor.getLevelChange() + " точ. ";
                }

                if (item.getType().equals("Зелье")) {
                    Item.Potion potion = (Item.Potion) item;
                    tempName += " " + potion.getLevel() + " Ур. ";
                }

                if (item.getType().equals("Еда")) {
                    Item.Food food = (Item.Food) item;
                    tempName += " " + food.getLevel() + " Ур. ";
                }

                if (Integer.valueOf(count) < 10) {

                    System.out.print("  ");
                } else if (Integer.valueOf(count) < 100) {

                    System.out.print(" ");
                } else {
                    System.out.print("");
                }

                StringBuilder sb = new StringBuilder();
                sb.append(count + ": " + tempName);
                tempName = sb.toString();
                if ( sb.length() > 25) {
                    tempName = tempName.substring(0, 20) + "... " + item.getPrice() + " з.";
                } else if (sb.length() <= 25) {
                    sb.append(" " + item.getPrice() + " з.");
                    while (sb.length() - 30 < 13) {
                        sb.append(" ");
                    }
                    tempName = sb.toString();
                }
                System.out.print(tempName + "\t");

                schet++;
                count++;

                if (schet == 5) {

                    schet = 0;
                    System.out.println();
                }

            }
            System.out.println();
        }
        System.out.println();
    }

    public void move(int i) {
        if (i <= this.strength && this.isAlive) {
            this.strength -= i;
            if (this.strength <= 0) {
                this.isAlive = false;
                Main.clearConsole();
                System.out.println("Вы погибли от усталости!");
                System.out.println("Игра окончена");
            } else {

            }
        } else {
            System.out.println("Нет живых персонажей!");
        }
    }

    // Внутренние статические классы:
    // Класс для игрока
    public static class Human extends Person implements Serializable{

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
            super.setWeapon(new Item.Weapon("Кинжал", 10, 10, 1, "", 0, 1));
            super.setArmor(new Item.Armor("Кожаная кираса", 10, 10, 1, 1));
            super.isAlive = true;
            super.countInventory = 20;
            super.race = "Человек";

        }
    }

    // Класс для игрока
    public static class Elf extends Person implements Serializable {

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
            super.setWeapon(new Item.Weapon("Лук", 10, 10, 1, "", 0, 1));
            super.setArmor(new Item.Armor("Кожаная кираса", 10, 10, 1, 1));
            super.isAlive = true;
            super.countInventory = 20;
            super.race = "Эльф";
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
            super.race = "Скелет";
            // Уезвим к стихии ветра а резист к воде
            super.vulnerabilityOfFire = false;
            super.vulnerabilityOfIce = false;
            super.vulnerabilityOfWind = true;
            super.vulnerabilityOfWater = false;
            super.resistanceOfFire = false;
            super.resistanceOfIce = false;
            super.resistanceOfWind = false;
            super.resistanceOfWater = true;

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
            super.race = "Зомби";
            // Уезвим к стихии воды а резист к ветру
            super.vulnerabilityOfFire = false;
            super.vulnerabilityOfIce = false;
            super.vulnerabilityOfWind = false;
            super.vulnerabilityOfWater = true;
            super.resistanceOfFire = false;
            super.resistanceOfIce = false;
            super.resistanceOfWind = true;
            super.resistanceOfWater = false;
        }
    }

    // Класс противника
    public static class Goblin extends Person {

        public Goblin(String name, int maxHealth, int power, int agility, int gold, int level, Item.Weapon weapon, Item.Armor armor) {

            super(name, maxHealth, power, agility, gold, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
            super.isAlive = true;
            super.race = "Гоблин";
            // Уезвим к стихии льда а резист к огню
            super.vulnerabilityOfFire = false;
            super.vulnerabilityOfIce = true;
            super.vulnerabilityOfWind = false;
            super.vulnerabilityOfWater = false;
            super.resistanceOfFire = true;
            super.resistanceOfIce = false;
            super.resistanceOfWind = false;
            super.resistanceOfWater = false;
        }
    }

    // Класс противника
    public static class Vampire extends Person {

        public Vampire(String name) {
            super(name);
        }

        public Vampire(String name, int maxHealth, int power, int agility, int gold,int level, Item.Weapon weapon, Item.Armor armor) {
            super(name, maxHealth, power, agility, gold, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 100L;
            super.isAlive = true;
            super.race = "Вампир";
            // Уезвим к стихии огня а резист к льду
            super.vulnerabilityOfFire = true;
            super.vulnerabilityOfIce = false;
            super.vulnerabilityOfWind = true;
            super.vulnerabilityOfWater = false;
            super.resistanceOfFire = false;
            super.resistanceOfIce = true;
            super.resistanceOfWind = false;
            super.resistanceOfWater = true;
        }
    }
}
