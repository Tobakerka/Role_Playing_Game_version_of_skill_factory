public class Item {

    String name;
    int price;
    int level;

    public Item(String name, int price, int level) {

        this.name = name;
        this.price = price;
        String type;
        this.level = level;
    }

    public void getInfo() {
        System.out.println(name + " " + price + " " + level);
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

    public static class Food extends Item {

        int power;
        String type = "Еда";

        public Food(String name, int price, int power, int level) {

            super(name, price, level);
            this.power = power;
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
                    "Восстанавливает: " + power + " выносливости\n");
        }


        public String getType() {
            return type;
        }

        public int getPower() {
            return power;
        }
    }

    public static class Potion extends Item {

        int power;
        String typeEffect;
        String type = "Зелье";

        public Potion(String name, int price, String type, int power, int level) {

            super(name, price, level);
            this.typeEffect = type;
            this.power = power;
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
            return  type + ": " + name + " " + typeEffect +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " " + tempText + "\n";
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
            System.out.println(type + ": " + name + " " + typeEffect +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " " + "\n");
        }
    }

    public static class Weapon extends Item {

        private int damage;
        private String type = "Оружие";
        private String typeEffect = "";
        private int powerEffect;
        private int levelChange; // Уровень заточки

        public Weapon(String name, int price, int damage, int level, String typeEffect, int powerEffect, int levelChange) {

            super(name, price, level);
            this.damage = damage;
            super.level = level;
            this.typeEffect = typeEffect;
            this.powerEffect = powerEffect;
            this.levelChange = levelChange;
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
                    "Название: " + name + "\n" +
                    "Уровень: " + level + "\n" +
                    "Цена: " + price + "\n" +
                    "Урон: " + damage + "\n" +
                    "Уровень заточки: " + levelChange + "\n";
            if (!typeEffect.equals("")) {
                tempText += "Эффект: " + typeEffect + "\n" + "Сила эффекта: " + powerEffect + "\n";

            } else {
                tempText += "\n";
            }
            return  tempText;

        }

        public void getInfo() {

            String tempText = type + ": \n" +
                    "Название: " + name + "\n" +
                    "Уровень: " + level + "\n" +
                    "Цена: " + price + "\n" +
                    "Урон: " + damage + "\n" +
                    "Уровень заточки: " + levelChange + "\n";
            if (!typeEffect.equals("")) {
                tempText += "Эффект: " + typeEffect + "\n" + "Сила эффекта: " + powerEffect + "\n";

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

        public void levelUp() {

            levelChange++;
            price += price * 10 / 100;
            damage += price * 10 / 100;

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

    public static class Armor extends Item {

        int defense;
        String type = "Броня";
        int levelChange;

        public Armor(String name, int price, int defense, int level, int levelChange) {

            super(name, price, level);
            this.defense = defense;
            this.levelChange = levelChange;

        }

        public int getLevelChange() {
            return levelChange;
        }

        // Метод передает информацию о брони

        public String print() {

            return type + ": \n" +
                    "Название: " + name + "\n" +
                    "Уровень: " + level + "\n" +
                    "Цена: " + price + "\n" +
                    "Защита: " + defense + "\n\n" +
                    "Уровень заточки: " + levelChange + "\n";
        }

        public void levelUp() {
            levelChange++;
            price += price * 10 / 100;
            defense += price * 10 / 100;
        }
        public void getInfo() {
            System.out.println(type + ": \n" +
                    "Название: " + name + "\n" +
                    "Уровень: " + level + "\n" +
                    "Цена: " + price + "\n" +
                    "Защита: " + defense + "\n\n" +
                    "Уровень заточки: " + levelChange + "\n");
        }


        public String getName() {
            return super.name;
        }


        public String getType() {
            return type;
        }
    }
}
