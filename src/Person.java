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
    private int defense; // Защита
    private boolean isAlive;
    private String race;
    private boolean isChopSort;
    private boolean isInventorySort;
    private int difficulty; // сложность игры. Для каждого персонажа свой уровень сложности.
    private double maxWeight; // Вес, который можно взять с собой
    private double weight; // Текущий вес

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
        defense = 20;
        isAlive = true;
        Item.Weapon weapon = null;
        Item.Armor armor = null;
        this.isChopSort = isChopSort;
        this.isInventorySort = isInventorySort;
        this.difficulty = 1;

        for (Item item : inventory) {
            inventory.add(null);
        }
    }

    // Конструктор для удобного создания противника для процедурной генерации

    public Person(String name, double maxHealth, double health, int power, int agility, int maxStrength, int strength, int defense, int gold, int level, Item.Weapon weapon, Item.Armor armor) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = health;
        this.power = power;
        this.agility = agility;
        this.gold = gold;
        this.weapon = weapon;
        this.armor = armor;
        this.isAlive = true;
        this.countInventory = 20;
        this.maxStrength = maxStrength;
        this.strength = strength;
        this.defense = defense;
        inventory.add(weapon);
        inventory.add(armor);
        isChopSort = false;
        isInventorySort = false;
        difficulty = 1;
    }

    // Геттеры и сеттеры:
    public long getlevelUpThreshold() {

        return levelUpThreshold;
    }

    public double getMaxWeight (){
        return maxWeight;
    }

    public double getWeight (){
        return weight;
    }

    public boolean getIsChopSort() {
        return isChopSort;
    }

    public boolean getIsInventorySort() {
        return isInventorySort;
    }

    public int getDifficulty() {
        return difficulty;
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

    public int getDefense() {
        return defense;
    }

    public Item.Weapon getWeapon() {

        if (weapon.equals(null)) {
            return new Item.Weapon("Пусто", 0, 0, 0, "", 0, 0, 0);
        } else {
            return weapon;
        }
    }

    public void setWeapon(Item.Weapon weapon) {
        this.weapon = weapon;
    }

    public void isShopSort(boolean isChopSort) {
        this.isChopSort = isChopSort;
    }

    public void isInventorySort(boolean isInventorySort) {
        this.isInventorySort = isInventorySort;
    }

    public void setDifficulty() {
        if (difficulty == 1 || difficulty == 2) {
            this.difficulty ++;
        }else {
            difficulty = 1;
        }
    }

    public void setIsInventorySort() {
        if (this.isInventorySort) {
            this.isInventorySort = false;
        } else {
            this.isInventorySort = true;
        }
    }

    public void setIsChopSort() {
        if (this.isChopSort) {
            this.isChopSort = false;
        } else {
            this.isChopSort = true;
        }
    }

    public Item.Armor getArmor() {

        if (armor.equals(null)) {
            return new Item.Armor("Пусто", 0, 0, 0, 0, 0);
        }
        return armor;
    }

    public void setArmor(Item.Armor armor) {
        this.armor = armor;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {

        if (gold > 0) {

            this.gold = gold;
        } else {

            System.out.println("Нельзя установить значение меньше 0");
        }
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

    public int getAgility() {
        return agility;
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

        int tempPower;
        if (power > maxStrength) {
            this.strength = maxStrength;
        } else {
            tempPower = this.strength + power;
            if (tempPower > maxStrength) {
                this.strength = maxStrength;
            } else {
                this.strength = tempPower;
            }
        }
    }
    private void setHealth(int power, double maxHealth) {

        double temp = this.health + power;
        if (temp > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = temp;
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

    public void checkIsAlive() {
        if (health <= 0) {
            isAlive = false;
            System.out.println("Персонаж " + name + " умер");
        }
    }

    public String getInfoEnergy() {
        return "Энергия: " + strength + "/" + maxStrength;
    }

    public int checkVulnerability(Item.Weapon weapon) {

        if (weapon.getTypeEffect().equals("Огонь") && vulnerabilityOfFire) {
            System.out.println("Уезвимость: К огню ");
            return weapon.getPowerEffect() * 2;
        } else if (weapon.getTypeEffect().equals("Вода") && vulnerabilityOfWater) {
            System.out.println("Уезвимость: К воде ");
            return weapon.getPowerEffect() * 2;
        } else if (weapon.getTypeEffect().equals("Лед") && vulnerabilityOfIce) {
            System.out.println("Уезвимость: Ко льду ");
            return weapon.getPowerEffect() * 2;
        } else if (weapon.getTypeEffect().equals("Ветер") && vulnerabilityOfWind) {
            System.out.println("Уезвимость: К ветру ");
            return weapon.getPowerEffect() * 2;
        } else if (weapon.getTypeEffect().equals("Огонь") && resistanceOfFire) {
            System.out.println("Резист: К огню ");
            return -weapon.getPowerEffect();
        } else if (weapon.getTypeEffect().equals("Вода") && resistanceOfWater) {
            System.out.println("Резист: К воде ");
            return -weapon.getPowerEffect();
        } else if (weapon.getTypeEffect().equals("Лед") && resistanceOfIce) {
            System.out.println("Резист: Ко льду ");
            return -weapon.getPowerEffect();
        } else if (weapon.getTypeEffect().equals("Ветер") && resistanceOfWind) {
            System.out.println("Резист: К ветру ");
            return -weapon.getPowerEffect();
        } else {
            return 0;
        }
    }

    public void giveAttack(int damage) {

        if (health >= damage) {
            health -= damage;
        } else {
            health = 0;
        }
        if (health <= 0) {
            isAlive = false;
        }
    }
    public String toInfo() {

        String temp = "";
        if (weapon != null) {
            temp = "\tОружие: " + weapon.getName() + "\n";
        }
        if (armor != null) {
            temp += "\tБроня: " + armor.getName() + "\n";
        }

        if (vulnerabilityOfFire) {
            temp += "Уезвимость: Огонь\n";
        }
        if (vulnerabilityOfIce) {
            temp += "Уезвимость: Лед\n";
        }
        if (vulnerabilityOfWind) {
            temp += "Уезвимость: Ветер\n";
        }
        if (vulnerabilityOfWater) {
            temp += "Уезвимость: Вода\n";
        }
        if (resistanceOfFire) {
            temp += "Резист: Огонь\n";
        }
        if (resistanceOfIce) {
            temp += "Резист: Лед\n";
        }
        if (resistanceOfWind) {
            temp += "Резист: Ветер\n";
        }
        if (resistanceOfWater) {
            temp += "Резист: Вода\n";
        }

        return "Имя: " + name + "\n" +
                "Уровень: " + level + "\n" +
                "Здоровье: " + health + "/" + maxHealth + "\n" +
                "Сила: " + strength + "\n" +
                "Сила атаки: " + power + "\n" + temp;

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

        exp += givenExp;
        while (exp >= this.levelUpThreshold) {

            exp -= levelUpThreshold;
            level++;
            maxHealth += Math.round(maxHealth * 0.1);
            health = maxHealth;
            maxStrength += Math.round(maxStrength * 0.1);
            strength = maxStrength;
            power += Math.round(power * 0.1);
            agility += Math.round(agility * 0.1);
            maxWeight += Math.round(maxWeight * 0.1);

            levelUpThreshold += Math.round(levelUpThreshold * 0.1);
            System.out.println("Уровень повышен!" + " Текущий уровень: " + level);
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
                            Main.clearConsole();
                            inventory.get(tempInt).getInfo();
                            System.out.println("1 - снять, 2 - удалить, 3 - информация, 0 - назад");
                            Scanner scannerWeapon = new Scanner(System.in);
                            String tempWeapon = scannerWeapon.nextLine();
                            switch (tempWeapon) {
                                case "1": {
                                    weapon = new Item.Weapon("Пусто", 0, 0, 0, "", 0, 0, 0);
                                    Main.clearConsole();
                                    System.out.println("Оружие снято");
                                    break;
                                }
                                case "2": {
                                    weapon = new Item.Weapon("Пусто", 0, 0, 0, "", 0, 0, 0);
                                    removeItem(inventory.get(tempInt));
                                    Main.clearConsole();
                                    System.out.println("Оружие удалено");
                                    break;
                                }
                                case "3": {
                                    Main.clearConsole();
                                    Item.Weapon weapon1 = (Item.Weapon) inventory.get(tempInt);
                                    weapon1.getInfo();
                                    return;
                                }
                                case "0": {
                                    Main.clearConsole();
                                    System.out.println("Возврат");
                                    return;
                                }
                                default: {
                                    Main.clearConsole();
                                    System.out.println("Неверный ввод");
                                }
                            }
                        } else {
                            Main.clearConsole();
                            inventory.get(tempInt).getInfo();
                            System.out.println("1 - надеть, 2 - удалить, 3 - информация, 0 - назад");
                            Scanner scannerWeapon1 = new Scanner(System.in);
                            String tempWeaponToEqup = scannerWeapon1.nextLine();
                            switch (tempWeaponToEqup) {
                                case "1": {
                                    if (inventory.get(tempInt).getLevel() <= level) {
                                        weapon = (Item.Weapon) inventory.get(tempInt);
                                        Main.clearConsole();
                                        System.out.println("Оружие надето");
                                    } else {
                                        Main.clearConsole();
                                        System.out.println("Уровня персонажа не достаточно!");
                                    }
                                    break;
                                }
                                case "2": {
                                    removeItem(inventory.get(tempInt));
                                    Main.clearConsole();
                                    System.out.println("Оружие удалено");
                                    break;
                                }
                                case "3": {
                                    Main.clearConsole();
                                    Item.Weapon weapon1 = (Item.Weapon) inventory.get(tempInt);
                                    weapon1.getInfo();
                                    break;
                                }
                            }
                        }
                    } else if (inventory.get(tempInt).getType().equals("Броня")) {
                        Main.clearConsole();
                        inventory.get(tempInt).getInfo();
                        if (inventory.get(tempInt).equals(armor)) {
                            System.out.println("1 - снять, 2 - удалить, 3 - информация, 0 - назад");
                            Scanner scannerArmor = new Scanner(System.in);
                            String tempArmor = scannerArmor.nextLine();
                            switch (tempArmor) {
                                case "1": {
                                    armor = new Item.Armor("Пусто", 0, 0, 0, 0, 0);
                                    Main.clearConsole();
                                    System.out.println("Броня снята");
                                    break;
                                }
                                case "2": {
                                    armor = new Item.Armor("Пусто", 0, 0, 0, 0, 0);
                                    removeItem(inventory.get(tempInt));
                                    Main.clearConsole();
                                    System.out.println("Броня удалена");
                                    break;
                                }
                                case "3": {
                                    Main.clearConsole();
                                    inventory.get(tempInt).getInfo();
                                    Item.Armor armor1 = (Item.Armor) inventory.get(tempInt);
                                    armor1.getInfo();
                                    break;
                                }
                                case "0": {
                                    Main.clearConsole();
                                    System.out.println("Возврат");
                                    return;
                                }
                                default: {
                                    Main.clearConsole();
                                    System.out.println("Возврат");
                                    return;
                                }
                            }
                        } else {
                            Main.clearConsole();
                            inventory.get(tempInt).getInfo();
                            System.out.println("1 - надеть, 2 - удалить, 3 - информация, 0 - назад");
                            Scanner scannerArmorToEqup = new Scanner(System.in);
                            String tempArmorToEqup = scannerArmorToEqup.nextLine();
                            switch (tempArmorToEqup) {
                                case "1": {
                                    if (inventory.get(tempInt).getLevel() <= level) {
                                        armor = (Item.Armor) inventory.get(tempInt);
                                        Main.clearConsole();
                                        System.out.println("Броня надета");
                                    } else {
                                        Main.clearConsole();
                                        System.out.println("Уровня персонажа не достаточно!");
                                    }
                                    break;
                                }
                                case "2": {
                                    armor = new Item.Armor("Пусто", 0, 0, 0, 0, 0);
                                    removeItem(inventory.get(tempInt));
                                    Main.clearConsole();
                                    System.out.println("Броня удалена");
                                    break;
                                }
                                case "3": {
                                    Main.clearConsole();
                                    inventory.get(tempInt).getInfo();
                                    Item.Armor armor1 = (Item.Armor) inventory.get(tempInt);
                                    armor1.getInfo();
                                }
                                case "0": {
                                    Main.clearConsole();
                                    System.out.println("Возврат");
                                    return;
                                }
                                default: {
                                    Main.clearConsole();
                                    System.out.println("Возврат");
                                    return;
                                }
                            }
                        }
                    } else if (inventory.get(tempInt).getType().equals("Зелье")) {
                        Main.clearConsole();
                        inventory.get(tempInt).getInfo();
                        System.out.println("1 - выпить, 2 - удалить, 3 - информация, 0 - назад");
                        Scanner scannerPotion = new Scanner(System.in);
                        String tempPotion = scannerPotion.nextLine();
                        switch (tempPotion) {
                            case "1": {
                                Item.Potion potion = (Item.Potion) inventory.get(tempInt);
                                if (potion.level <= level) {
                                    Main.clearConsole();
                                    if (potion.typeEffect.equals("лечебное")) {
                                        setHealth(potion.getPower(), maxHealth);

                                        System.out.println("Вы выпили зелье здоровья. Здоровье: + " + potion.getPower());
                                    } else if (potion.typeEffect.equals("выносливости")) {
                                        setStrength(potion.getPower(), maxStrength);
                                        System.out.println("Вы выпили зелье выносливости. Выносливость + " + potion.getPower());
                                        inventory.remove(potion);
                                    }
                                } else {
                                    Main.clearConsole();
                                    System.out.println("Уровня персонажа не достаточно!");
                                }

                                break;
                            }
                            case "2": {

                                inventory.remove(inventory.get(tempInt));
                                Main.clearConsole();
                                System.out.println("Зелье удалено");
                                break;
                            }
                            case "3": {
                                Main.clearConsole();
                                Item.Potion potion = (Item.Potion) inventory.get(tempInt);
                                potion.getInfo();
                                break;
                            }
                            case "0": {
                                Main.clearConsole();
                                System.out.println("Возврат");
                                return;
                            }
                            default: {

                            }
                        }
                    } else {
                        Main.clearConsole();
                        inventory.get(tempInt).getInfo();
                        System.out.println("1 - съесть, 2 - удалить, 3 - информация, 0 - назад");
                        Scanner scannerFood = new Scanner(System.in);
                        String tempFood = scannerFood.nextLine();
                        if (tempFood.equals("1")) {
                            Item.Food food = (Item.Food) inventory.get(tempInt);
                            setStrength(food.getPower(), maxStrength);
                            Main.clearConsole();
                            System.out.println("Вы съели " + food.getName() + ". Сила: + " + food.getPower());
                            removeItem(inventory.get(tempInt));
                        } else if (tempFood.equals("2")) {
                            Main.clearConsole();
                            inventory.remove(inventory.get(tempInt));
                            System.out.println("Предмет удален");
                        } else if (tempFood.equals("3")) {
                            Main.clearConsole();
                            Item.Food food = (Item.Food) inventory.get(tempInt);
                            food.getInfo();
                        }
                    }
                } else {
                    Main.clearConsole();
                    System.out.println("Неверный ввод");
                    return;
                }
            } else {
                Main.clearConsole();
                System.out.println("Возврат");
                check = false;
            }
        }
    }
    // печатает информацию о персонаже

    public void showStats() {

        Main.clearConsole();
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
            System.out.println("Опыт: " + exp + " / " + levelUpThreshold);
            System.out.println("Вес: " + weight + " / " + maxWeight);
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

        Main.clearConsole();
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

    public void sortInventory() {
        for (int i = 0; i < inventory.size(); i++) {

            ArrayList temp = new ArrayList();
            ArrayList<Item> weapons = new ArrayList();
            ArrayList<Item> armors = new ArrayList();
            ArrayList<Item> potions = new ArrayList();
            ArrayList<Item> foods = new ArrayList();

            temp.clear();
            weapons.clear();
            armors.clear();
            potions.clear();
            foods.clear();

            for (int j = 0; j < inventory.size(); j++) {
                if (inventory.get(j).getType().equals("Оружие")) {
                    weapons.add(inventory.get(j));
                } else if (inventory.get(j).getType().equals("Броня")) {
                    armors.add(inventory.get(j));
                } else if (inventory.get(j).getType().equals("Зелье")) {
                    potions.add(inventory.get(j));
                } else if (inventory.get(j).getType().equals("Еда")) {
                    foods.add(inventory.get(j));
                }
            }

            for (Item item : weapons) {
                temp.add(item);
            }
            for (Item item : armors) {
                temp.add(item);
            }
            for (Item item : potions) {
                temp.add(item);
            }
            for (Item item : foods) {
                temp.add(item);
            }

            inventory.clear();
            inventory.addAll(temp);
        }
    }
    // Метод для вывода содержимого инвентаря

    public void lookInventory() {
        int count = 0;
        int schet = 0;

        // Проверка включена ли сортировка. Если да, то предмены сортируются
        if (isInventorySort) {

            sortInventory();
        }

        System.out.println("Инвентарь:" + "\n" +
                "Ваше золото: " + gold + "\n" +
                "Предметы:" + inventory.size() + " из " + countInventory + "\n");

        if (inventory.size() == 0) {

            System.out.println("Инвентарь пуст");
        } else {

            for (Item item : inventory) {

                String equip = "";
                String tempName = item.getName();
                if (item.getType().equals("Оружие")) {
                    Item.Weapon weapon = (Item.Weapon) item;
                    tempName += " " + weapon.getLevel() + " Ур. " + weapon.getLevelChange() + " точ. ";
                    if (!weapon.getTypeEffect().equals("")) {
                        tempName +=  weapon.getTypeEffect() + " " + weapon.getPowerEffect() + "ед. ";
                    }

                    if (weapon.equals(getWeapon())) {
                        equip += " - (Надет)";
                    }
                }
                if (item.getType().equals("Броня")) {
                    Item.Armor armor = (Item.Armor) item;
                    tempName += " " + armor.getLevel() + " Ур. " + armor.getLevelChange() + " точ. ";
                    if (armor.equals(getArmor())) {
                        equip += " - (Надета)";
                    }
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
                    tempName = tempName.substring(0, 20) + "... " + item.getPrice() + " з." + equip;
                } else if (sb.length() <= 25) {
                    sb.append(" " + item.getPrice() + " з." + equip);
                    while (sb.length() - 30 < 13) {
                        sb.append(" ");
                    }
                    tempName = sb.toString();
                }
                System.out.println(tempName + "\t");

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

    public void move() {
        if (difficulty <= this.strength && this.isAlive) {
            this.strength -= difficulty;
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

    public int getPower() {
        return power;
    }

    public String getHealth() {
        return health + "/" + maxHealth;
    }

    public String getMaxHealth() {
        return maxHealth + "";
    }

    public void setHealth(int i) {
        this.health = i;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void leve() {
        if (this.strength > 20 && this.isAlive) {
            this.strength -= 20;
        } else {
            System.out.println("Вы умерли от усталости!");
            this.strength = 0;
            this.isAlive = false;
        }
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void addWeight(double weight) {
        this.weight += weight;
    }

    public void setCountInventory(int i) {
        countInventory = i;
    }

    public void addCountInventory() {
        countInventory++;
    }

    // Внутренние статические классы:
    // Класс для игрока
    public static class Human extends Person implements Serializable{

        public Human(String name) {

            super(name);
            super.maxHealth = 100;
            super.health = 100;
            super.power = 20;
            super.agility = 10;
            super.maxStrength = 100;
            super.strength = 100;
            super.defense = 2;
            super.gold = 0;
            super.level = 1;
            super.exp = 0L;
            super.levelUpThreshold = 200L;
            super.setWeapon(new Item.Weapon("Кинжал", 10, 10, 1, "", 0, 1, 0.4));
            super.setArmor(new Item.Armor("Кожаная кираса", 10, 10, 1, 1, 0.5));
            super.isAlive = true;
            super.countInventory = 20;
            super.race = "Человек";
            inventory.add(getWeapon());
            inventory.add(getArmor());
            super.maxWeight = 20;
            super.weight = 0;



        }
    }

    // Класс для игрока
    public static class Elf extends Person implements Serializable{

        public Elf(String name) {

            super(name);
            super.maxHealth = 80;
            super.health = 80;
            super.power = 20;
            super.agility = 15;
            super.maxStrength = 100;
            super.strength = 100;
            super.defense = 2;
            super.gold = 0;
            super.level = 1;
            super.exp = 0L;
            super.levelUpThreshold = 200L;
            super.setWeapon(new Item.Weapon("Лук", 10, 10, 1, "", 0, 1, 2));
            super.setArmor(new Item.Armor("Кожаная кираса", 10, 10, 1, 1, 0.5));
            super.isAlive = true;
            super.countInventory = 20;
            super.race = "Эльф";
            inventory.add(getWeapon());
            inventory.add(getArmor());
            super.maxWeight = 20;
            super.weight = 0;
        }
    }

    // Класс противника
    public static class Skeleton extends Person {

        public Skeleton(String name, int maxHealth,int health, int power, int agility, int maxStrength, int strength, int defense, int gold, int level, Item.Weapon weapon, Item.Armor armor) {

            super(name, maxHealth, health, power, agility, maxStrength, strength, defense, gold, level, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 200L;
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

        public Zombie(String name, double maxHealth,  double health, int power, int agility, int maxStrength, int strength, int defense, int gold, int level, Item.Weapon weapon, Item.Armor armor) {

            super(name, maxHealth, health, power, agility, maxStrength, strength, defense, gold, level, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 200L;
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

        public Goblin(String name, double maxHealth, double health, int power, int agility, int maxStrength, int strength, int defense, int gold, int level, Item.Weapon weapon, Item.Armor armor) {

            super(name, maxHealth, health, power, agility, maxStrength, strength, defense, gold, level, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 200L;
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

        public Vampire(String name, double maxHealth, double health, int power, int agility, int maxStrength, int strength, int defense, int gold, int level, Item.Weapon weapon, Item.Armor armor) {
            super(name, maxHealth, health, power, agility, maxStrength, strength, defense, gold, level, weapon, armor);
            super.level = level;
            super.exp = 0L;
            super.levelUpThreshold = 200L;
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
