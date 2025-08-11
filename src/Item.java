public abstract class Item {

    String name;
    int price;

    public Item(String name, int price) {

        this.name = name;
        this.price = price;
    }

    public abstract String print();

    public static class Food extends Item {

        int power;

        public Food(String name, int price, int power) {

            super(name, price);
            this.power = power;
        }

        // Метод передает информацию о еде
        @Override
        public String print() {
            return  "Еда: " + name + "\n" +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " выносливости\n";
        }
    }

    public static class Potion extends Item {

        int power;
        String type;

        public Potion(String name, int price, String type) {

            super(name, price);
            this.type = type;
        }

        // Метод передает информацию о зелье
        @Override
        public String print() {

            String tempText = "";

            switch (type) {
                case "лечебное": {
                    tempText = "жизни";
                }
                case "выносливости": {
                    tempText = "выносливости";
                }
            }
            return  "Зелье: " + name + " " + type +
                    "Цена: " + price + "\n" +
                    "Восстанавливает: " + power + " " + tempText + "\n";
        }
    }

    public static class Weapon extends Item {

        int power;
        int damage;
        int level;
        public Weapon(String name, int price, int damage, int level) {

            super(name, price);
            this.damage = damage;
            this.level = level;
        }

        // Метод передает информацию о оружии
        @Override
        public String print() {

            return  "Оружие: \n" +
                    "Название: " + name + "\n" +
                    "Уровень: " + level + "\n" +
                    "Цена: " + price + "\n" +
                    "Урон: " + damage + "\n\n";
        }
    }

    public static class Armor extends Item {

        int defense;
        int level;

        public Armor(String name, int price, int defense, int level) {

            this.level = level;
            super(name, price);
            this.defense = defense;
        }

        // Метод передает информацию о брони
        @Override
        public String print() {

            return "Броня: \n" +
                    "Название: " + name + "\n" +
                    "Цена: " + price + "\n" +
                    "Защита: " + defense + "\n\n";
        }
    }
}
