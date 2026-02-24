import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Magazine {

    int countToItems = 10; // Количество предметов в магазине
    int countMinItems = 20; // Минимальное количество предметов в магазине

    ArrayList<Item> itemsOfSales = new ArrayList<>(countToItems); // Предметы, которые будут продаваться
    ArrayList<Item> itemsOfsellPerson = new ArrayList<>();

    public void spawnMagazine(int level){

        Random randomIntSizeItem = new Random();
        countToItems = countMinItems + randomIntSizeItem.nextInt(countToItems);

        itemsOfSales.clear();
        itemsOfSales = new ArrayList<>(Game.generateItem(countToItems, level));
        itemsOfsellPerson.clear();
    }

    public void printMagazine(Person player) {

        int count = 0;
        int schet = 0;

        System.out.println("Магазин\n" +
                "Ваше золото:" + player.getGold() + "\n" +
                "Предметы:" + itemsOfSales.size() + " из " + countToItems + "\n");

        if (player.getIsInventorySort()) {
            sortMagazine();
        }
        if (itemsOfSales.size() == 0) {
            System.out.println("Магазин пуст");
        } else {

            for (Item item : itemsOfSales) {
                String tempName = item.getName();

                if (Integer.valueOf(count) < 10) {
                    System.out.print("  ");
                } else if (Integer.valueOf(count) < 100) {
                    System.out.print(" ");
                } else {
                    System.out.print("");
                }

                StringBuilder sb = new StringBuilder();
                sb.append(count + ": " + tempName);
                if (item.getType().equals("Оружие")) {
                    Item.Weapon weapon = (Item.Weapon) item;
                    sb.append(" " + weapon.getDamage() + " урона" + " " + weapon.level + " ур." + " " + weapon.getLevelChange() + " точ. ");
                    if (!weapon.getTypeEffect().equals("")) {
                        sb.append(" " + weapon.getTypeEffect() + " " + weapon.getPowerEffect() + " урона ");
                    }
                } else if (item.getType().equals("Броня")) {
                    Item.Armor armor = (Item.Armor) item;
                    sb.append(" " + armor.getDefense() + " брони" + " " + armor.level + " ур." + " " + armor.getLevelChange() + " точ. ");
                } else if (item.getType().equals("Зелье")) {
                    Item.Potion potion = (Item.Potion) item;
                    sb.append(" " + potion.getPower() + " урона" + " " + potion.level + " ур. ");
                } else if (item.getType().equals("Еда")){
                    Item.Food food = (Item.Food) item;
                    sb.append(" " + food.getPower() + " силы зелья" + " " + food.level + " ур. ");
                }
                tempName = sb.toString();
                if ( sb.length() > 42) {
                    tempName = tempName.substring(0, 33) + " /.../ " + item.getPrice() + " золота.";
                } else if (sb.length() <= 42) {
                    sb.append(item.getPrice() + " золота.");
                    tempName = sb.toString();
                }
                System.out.println(tempName + "\t");

                count++;

            }
            count = 0;
            System.out.println();
        }
        System.out.println();
    }

    public void printMagazineOfSellPerson() {

        int count = 0;
        int schet = 0;

        System.out.println("Магазин проданных предметов:" + "\n");

        if (itemsOfsellPerson.size() == 0) {

            System.out.println("Магазин пуст");
        } else {

            for (Item item : itemsOfsellPerson) {

                String tempName = item.getName();

                if (Integer.valueOf(count) < 10) {

                    System.out.print("  ");
                } else if (Integer.valueOf(count) < 100) {

                    System.out.print(" ");
                } else {
                    System.out.print("");
                }

                StringBuilder sb = new StringBuilder();
                sb.append(count + ": " + tempName);
                if (item.getType().equals("Оружие")) {
                    Item.Weapon weapon = (Item.Weapon) item;
                    sb.append(" " + weapon.getDamage() + " урона" + " " + weapon.level + " ур." + " " + weapon.getLevelChange() + " точ. ");
                    if (!weapon.getTypeEffect().equals("")) {
                        sb.append(" " + weapon.getTypeEffect() + " " + weapon.getPowerEffect() + " урона ");
                    }
                } else if (item.getType().equals("Броня")) {
                    Item.Armor armor = (Item.Armor) item;
                    sb.append(" " + armor.getDefense() + " брони" + " " + armor.level + " ур." + " " + armor.getLevelChange() + " точ. ");
                } else if (item.getType().equals("Зелье")) {
                    Item.Potion potion = (Item.Potion) item;
                    sb.append(" " + potion.getPower() + " урона" + " " + potion.level + " ур. ");
                } else if (item.getType().equals("Еда")){
                    Item.Food food = (Item.Food) item;
                    sb.append(" " + food.getPower() + " силы зелья" + " " + food.level + " ур. ");
                }
                tempName = sb.toString();
                if ( sb.length() > 42) {
                    tempName = tempName.substring(0, 33) + "... " + item.getPrice() + " золота.";
                } else if (sb.length() <= 42) {
                    sb.append(item.getPrice() + " золота.");
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

    public void addItemToMagazine(){
    }

    public void menuMagazine(Person player) {

        while (true) {
            int priceCount = 1000; // Цена покупки дополнительной ячейки
            int i = 0;
            for (i = 0; i < player.getCountInventory(); i++) {
                i++;
            }
            priceCount = priceCount * i;
            i = 0;
            System.out.printf("Магазин: \n\n1 - Купить | 2 - Продать | 3 - Выкупить | 4 - Купить дополнительную ячейку | 0 - Выйти\n\n");
            Scanner scanner = new Scanner(System.in);
            String tempString = scanner.nextLine();


            switch (tempString) {
                case "1" : {
                    Main.clearConsole();
                    openMagazine(player);
                    break;
                }
                case "2" : {
                    Main.clearConsole();
                    sellItem(player);
                    break;
                }
                case "3" : {
                    Main.clearConsole();
                    openSellPerson(player);
                    break;
                }
                case "0" : {
                    Main.clearConsole();
                    System.out.println("Выход");
                    return;
                }
                case "4" : {

                    switch (Main.checkInt("Цена покупки дополнительной ячейки: " + priceCount + " золота. \n1 - Купить | любая клавиша - Выйти", 2)) {
                        case 1 : {
                            if (player.getGold() >= priceCount) {
                                Main.clearConsole();
                                player.addCountInventory();
                                player.deliteGold(priceCount);
                                System.out.println("Вы купили дополнительную ячейку");
                            } else {
                                if (player.getGold() < priceCount) {
                                    Main.clearConsole();
                                    System.out.println("Недостаточно золота");
                                }
                            }

                        }
                    }
                    break;
                }
                default : {
                    Main.clearConsole();
                    System.out.println("Неверный ввод");
                }
            }
            scanner = null;
        }
    }

    private void openSellPerson(Person player) {
        while (true) {
            printMagazineOfSellPerson();
            System.out.println("Выберите предмет \nлюбая буква или символ для выхода");
            Scanner scanner = new Scanner(System.in);

            int tempInt = 0;
            if (scanner.hasNextInt()) {

                tempInt = scanner.nextInt();
                if (tempInt < itemsOfsellPerson.size() && tempInt >= 0) {

                    itemsOfsellPerson.get(tempInt).getInfo();
                    System.out.println("1 - Купить | 2 - Выйти");
                    Scanner scannerVar = new Scanner(System.in);
                    String tempString = scannerVar.nextLine();
                    switch (tempString) {
                        case "1" -> {
                            if (player.getGold() >= itemsOfsellPerson.get(tempInt).getPrice()) {
                                if (player.inventory.size() < player.getCountInventory()) {
                                    player.deliteGold(itemsOfsellPerson.get(tempInt).getPrice());
                                    player.addInventory(itemsOfsellPerson.get(tempInt));
                                    itemsOfsellPerson.remove(tempInt);
                                } else {
                                    System.out.println("Нет места в инвентаре");
                                    Main.clearConsole();
                                }
                            } else {
                                System.out.println("Недостаточно золота");
                            }
                            break;
                        }
                        case "2" -> {
                            Main.clearConsole();
                            return;
                        }
                    }

                } else {
                    System.out.println("Неверный ввод");
                    Main.clearConsole();
                }
            } else {
                System.out.println("Выход");
                Main.clearConsole();
                return;
            }
        }
    }

    public void sortMagazine(){

        ArrayList<Item> tempItems = new ArrayList<>(itemsOfSales);
        ArrayList<Item.Weapon> weapons = new ArrayList<>();
        ArrayList<Item.Armor> armors = new ArrayList<>();
        ArrayList<Item.Potion> potions = new ArrayList<>();
        ArrayList<Item.Food> foods = new ArrayList<>();

        tempItems.clear();
        weapons.clear();
        armors.clear();
        potions.clear();
        foods.clear();
        for (Item item : itemsOfSales) {

            if (item.getType().equals("Оружие")) {
                weapons.add((Item.Weapon) item);
            } else if (item.getType().equals("Броня")) {
                armors.add((Item.Armor) item);
            } else if (item.getType().equals("Зелье")) {
                potions.add((Item.Potion) item);
            } else if (item.getType().equals("Еда")){
                foods.add((Item.Food) item);
            }
        }

        for (Item i : weapons) {
            tempItems.add(i);
        }
        for (Item i : armors) {
            tempItems.add(i);
        }
        for (Item i : potions) {
            tempItems.add(i);
        }
        for (Item i : foods) {
            tempItems.add(i);
        }
        itemsOfSales = tempItems;
    }

    // Продажа предметов
    private void sellItem(Person player) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            player.lookInventory();
            System.out.println("Выберите предмет\nЛюбая буква или символ для выхода");

            if (!scanner.hasNextInt()) {
                Main.clearConsole();
                System.out.println("Выход");
                return;
            }

            int tempInt = scanner.nextInt();
            scanner.nextLine(); // очищаем буфер послеnextInt()

            if (tempInt < 0 || tempInt >= player.inventory.size()) {
                Main.clearConsole();
                System.out.println("Неверный ввод");
                return;
            }

            Item selectedItem = player.inventory.get(tempInt);
            selectedItem.getInfo();

            String choice = showSellMenu(scanner);
            switch (choice) {
                case "1": // Продать
                    sellSelectedItem(player, selectedItem);
                    break;
                case "2": // Информация
                    selectedItem.getInfo();
                    break;
                case "0": // Выйти
                    Main.clearConsole();
                    return;
                default:
                    Main.clearConsole();
                    System.out.println("Неверный ввод");
            }
        }
    }

    private String showSellMenu(Scanner scanner) {
        System.out.println("1 - продать, 2 - информация, 0 - назад");
        return scanner.nextLine();
    }

    private void sellSelectedItem(Person player, Item item) {
        itemsOfsellPerson.add(item);
        player.removeItem(item);
        player.addGold(item.getPrice());
        System.out.println(item.getType() + " продано");
    }

    // Открытие магазина для покупки
    public void openMagazine(Person player) {

        while (true) {
            printMagazine(player);
            System.out.println("Выберите предмет \nлюбая буква или символ для выхода");
            Scanner scannerPred = new Scanner(System.in);

            int tempInt = 0;
            if (scannerPred.hasNextInt()) {

                tempInt = scannerPred.nextInt();
                if (tempInt < itemsOfSales.size() && tempInt >= 0) {

                    itemsOfSales.get(tempInt).getInfo();
                    System.out.println("1 - Купить | 2 - Выйти");
                    Scanner scannerVar = new Scanner(System.in);
                    String tempString = scannerVar.nextLine();
                    switch (tempString) {
                        case "1" : {
                            if (player.getGold() >= itemsOfSales.get(tempInt).getPrice()) {
                                if (player.inventory.size() < player.getCountInventory()) {
                                    player.deliteGold(itemsOfSales.get(tempInt).getPrice());
                                    player.addInventory(itemsOfSales.get(tempInt));
                                    itemsOfSales.remove(tempInt);
                                } else {
                                    System.out.println("Нет места в инвентаре");
                                    Main.clearConsole();
                                }
                            } else {
                                System.out.println("Недостаточно золота");
                            }
                            break;
                        }
                        case "2" : {
                            Main.clearConsole();
                            return;
                        }
                    }

                } else {
                    System.out.println("Неверный ввод");
                    Main.clearConsole();
                }
            } else {
                System.out.println("Выход");
                Main.clearConsole();
                return;
            }
            scannerPred = null;
        }
    }
}

