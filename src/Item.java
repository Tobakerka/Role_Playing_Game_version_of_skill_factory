import java.io.Serializable;

public class Item implements Serializable {

    String name;
    int price;
    int level;
    double weight;

    public Item(String name, int price, int level) {

        this.name = name;
        this.price = price;
        this.level = level;
    }

    public void getInfo() {
        System.out.println(name + " " + price + " " + level);
    }

    public double getWeight() {
        return weight;
    }

    public String getType() {
        return getType();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price += price;
    }

    public static class Food extends Item implements Serializable{

        int power;
        String type = "Еда";

        public Food(String name, int price, int power, int level, double weight) {

            super(name, price, level);
            this.power = power;
            super.weight = weight;
        }

        // Метод передает информацию о еде

        public String print() {
            return  type + ": " + name + "\n" +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " выносливости\n";
        }


        public String getName() {
            return super.name;
        }

        public void getInfo() {
            System.out.println(type + ": " + name + "\n" +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " выносливости\n" +
                    "Вес: " + weight + "\n");
        }


        public String getType() {
            return type;
        }

        public int getPower() {
            return power;
        }
    }

    public static class Potion extends Item implements Serializable{

        int power;
        String typeEffect;
        String type = "Зелье";

        public Potion(String name, String type, int price, String typeEffect, int power, int level, double weight) {

            super(name, price, level);
            this.type = type;
            this.typeEffect = typeEffect;
            this.power = power;
            super.weight = weight;
        }

        // Метод передает информацию о зелье

        public String print() {

            String tempText = "";

            switch (typeEffect) {
                case "лечебное": {
                    tempText = "жизни";
                }
                case "выносливости": {
                    tempText = "выносливости";
                }
            }
            return  type + ": " + typeEffect + " " +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " " + tempText + "\n" +
                    "Вес: " + weight + "\n";
        }


        public String getName() {
            return super.name;
        }


        public String getType() {
            return type;
        }

        public int getPower() {
            return power;
        }

        public void getInfo() {
            System.out.println(type + ": " + typeEffect + " " +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " " + "\n" +
                    "Вес: " + weight + "\n");
        }
    }

    public static class Weapon extends Item implements Serializable{

        private int damage;
        private String type = "Оружие";
        private String typeEffect = "";
        private int powerEffect;
        private int levelChange; // Уровень заточки

        public Weapon(String name, int price, int damage, int level, String typeEffect, int powerEffect, int levelChange, double weight) {

            super(name, price, level);
            this.damage = damage;
            super.level = level;
            this.typeEffect = typeEffect;
            this.powerEffect = powerEffect;
            this.levelChange = levelChange;
            super.weight = weight;
        }

        public int getLevelChange() {
            return levelChange;
        }

        public String getTypeEffect() {
            return typeEffect;
        }

        // Метод передает информацию о оружии

        public String print() {

            String tempText = type + ": \n" +
                    "\tНазвание: " + name + "\n" +
                    "\tУровень: " + level + "\n" +
                    "\tЦена: " + price + "\n" +
                    "\tУрон: " + damage + "\n" +
                    "\tВес: " + weight + "\n" +
                    "\tУровень заточки: " + levelChange + "\n";
            if (!typeEffect.equals("")) {
                tempText += "\tЭффект: " + typeEffect + "\n" + "\tСила эффекта: " + powerEffect + "\n";

            } else {
                tempText += "\n";
            }
            return  tempText;

        }

        public void getInfo() {

            System.out.println("Информация о " + type + ": \n");
            String tempText = type + ": \n" +
                    "\tНазвание: " + name + "\n" +
                    "\tУровень: " + level + "\n" +
                    "\tЦена: " + price + "\n" +
                    "\tУрон: " + damage + "\n" +
                    "\tВес: " + weight + "\n" +
                    "\tУровень заточки: " + levelChange + "\n";
            if (!typeEffect.equals("")) {
                tempText += "\tЭффект: " + typeEffect + "\n" + "\tСила эффекта: " + powerEffect + "\n";

            } else {
                tempText += "\n";
            }
            System.out.println(tempText);
        }


        public String getName() {
            return super.name;
        }


        public String getType() {
            return type;
        }

        public void levelUp(int countLevel) {

            for (int i = 0; i < countLevel; i++) {
                levelChange++;
                price += price * 10 / 100;
                damage += price * 10 / 100;
            }

        }

        public int getPowerEffect() {
            return powerEffect;
        }

        public void setPowerEffect(int powerEffect) {
            this.powerEffect = powerEffect;
        }

        public void setTypeEffect(String typeEffect) {
            this.typeEffect = typeEffect;
        }

        public int getDamage() {
            return damage;
        }
    }

    public static class Armor extends Item implements Serializable{

        int defense;
        String type = "Броня";
        int levelChange;

        public Armor(String name, int price, int defense, int level, int levelChange, double weight) {

            super(name, price, level);
            this.defense = defense;
            this.levelChange = levelChange;
            super.weight = weight;

        }

        public int getLevelChange() {
            return levelChange;
        }

        public int getDefense() {
            return defense;
        }

        // Метод передает информацию о брони

        public String print() {

            return type + ": \n" +
                    "\tНазвание: " + name + "\n" +
                    "\tУровень: " + level + "\n" +
                    "\tЦена: " + price + "\n" +
                    "\tЗащита: " + defense + "\n" +
                    "\tВес: " + weight + "\n" +
                    "\tУровень заточки: " + levelChange + "\n";
        }

        public void levelUp(int countLevelUp) {
            for (int i = 0; i < countLevelUp; i++) {
                levelChange++;
                price += price * 10 / 100;
                defense += price * 10 / 100;
            }
        }
        public void getInfo() {
            System.out.println(type + ": \n" +
                    "\tНазвание: " + name + "\n" +
                    "\tУровень: " + level + "\n" +
                    "\tЦена: " + price + "\n" +
                    "\tЗащита: " + defense + "\n\n" +
                    "\tВес: " + weight + "\n" +
                    "\tУровень заточки: " + levelChange + "\n");
        }


        public String getName() {
            return super.name;
        }


        public String getType() {
            return type;
        }
    }
}
