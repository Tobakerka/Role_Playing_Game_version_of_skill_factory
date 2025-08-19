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
    }

    public void printMagazine(Person player, boolean isShopSort) {

        int count = 0;
        int schet = 0;

        System.out.println("Магазин\n" +
                "Ваше золото:" + player.getGold() + "\n" +
                "Предметы:" + itemsOfSales.size() + " из " + countToItems + "\n");

        if (isShopSort) {
            sortMagazine();
        }
        if (itemsOfSales.size() == 0) {
            System.out.println("Магазин пуст");
        } else {

            for (Item item : itemsOfSales) {
                StringBuilder space = new StringBuilder();
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
                    tempName = tempName.substring(0, 33) + " /.../ " + space.toString() + item.getPrice() + " золота.";
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
                tempName = sb.toString();
                if ( sb.length() > 25) {
                    tempName = tempName.substring(0, 10) + "... " + item.getPrice() + " золота.";
                } else if (sb.length() < 25) {
                    sb.append(" " + item.getPrice() + " золота.");
                    while (sb.length() - 25 < 15) {
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

    public void addItemToMagazine(){
    }

    public void menuMagazine(Person player, boolean isInventorySort, boolean isShopSort) {

        while (true) {
            System.out.printf("Магазин: \n\n1 - Купить | 2 - Продать | 3 - Выкупить | 0 - Выйти\n");
            Scanner scanner = new Scanner(System.in);
            String tempString = scanner.nextLine();
            switch (tempString) {
                case "1" : {
                    Main.clearConsole();
                    openMagazine(player, isShopSort);
                    break;
                }
                case "2" : {
                    Main.clearConsole();
                    sellItem(player, isInventorySort);
                    break;
                }
                case "3" : {
                    Main.clearConsole();
                    openSellPerson(player, isInventorySort);
                    break;
                }
                case "0" : {
                    Main.clearConsole();
                    System.out.println("Выход");
                    return;
                }
                default : {
                    Main.clearConsole();
                    System.out.println("Неверный ввод");
                }
            }
            scanner = null;
        }
    }

    private void openSellPerson(Person player, boolean isInventorySort) {
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
    private void sellItem(Person player, boolean isInventorySort) {

        while (true) {
            player.lookInventory(isInventorySort);

            System.out.println("Выберите предмет \nлюбая буква или символ для выхода");
            Scanner scanner = new Scanner(System.in);
            int tempInt = 0;
            if (scanner.hasNextInt()) {

                tempInt = scanner.nextInt();
                if (tempInt < player.inventory.size() && tempInt >= 0) {

                    player.inventory.get(tempInt).getInfo();
                    if (player.inventory.get(tempInt).getType().equals("Оружие")) {

                        if (player.inventory.get(tempInt).equals(player.getWeapon())) {
                            Main.clearConsole();
                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerWeapon = new Scanner(System.in);
                            String tempWeapon = scannerWeapon.nextLine();
                            switch (tempWeapon) {
                                case "1": {
                                    Main.clearConsole();
                                    player.addGold(player.inventory.get(tempInt).getPrice());
                                    player.setWeapon(new Item.Weapon("Пусто", 0, 0, 0, "",0, 0));
                                    itemsOfsellPerson.add(player.inventory.get(tempInt));
                                    player.removeItem(player.inventory.get(tempInt));
                                    System.out.println("Оружие продано");
                                    break;
                                }
                                case "2": {
                                    Main.clearConsole();
                                    player.inventory.get(tempInt).getInfo();
                                    break;
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
                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerWeapon1 = new Scanner(System.in);
                            String tempWeaponToEqup = scannerWeapon1.nextLine();
                            switch (tempWeaponToEqup) {
                                case "1": {
                                    Main.clearConsole();
                                    player.addGold(player.inventory.get(tempInt).getPrice());
                                    itemsOfsellPerson.add(player.inventory.get(tempInt));
                                    player.removeItem(player.inventory.get(tempInt));
                                    System.out.println("Оружие продано");
                                    break;
                                }
                                case "2": {
                                    Main.clearConsole();
                                    player.inventory.get(tempInt).getInfo();
                                    break;
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
                        }
                    } else if (player.inventory.get(tempInt).getType().equals("Броня")) {

                        if (player.inventory.get(tempInt).equals(player.getArmor())) {
                            Main.clearConsole();
                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerArmor = new Scanner(System.in);
                            String tempArmor = scannerArmor.nextLine();
                            switch (tempArmor) {
                                case "1": {
                                    player.setArmor(new Item.Armor("Пусто", 0, 0, 0, 0));
                                    player.addGold(player.inventory.get(tempInt).getPrice());
                                    itemsOfsellPerson.add(player.inventory.get(tempInt));
                                    player.removeItem(player.inventory.get(tempInt));
                                    System.out.println("Броня продана");
                                    break;
                                }
                                case "2": {
                                    player.inventory.get(tempInt).getInfo();
                                    break;
                                }
                                case "0": {
                                    System.out.println("Выход");
                                    return;
                                }
                                default: {
                                    System.out.println("Неверный ввод");
                                }
                            }
                        } else {
                            Main.clearConsole();
                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerArmorToEqup = new Scanner(System.in);
                            String tempArmorToEqup = scannerArmorToEqup.nextLine();
                            switch (tempArmorToEqup) {
                                case "1": {
                                    Main.clearConsole();
                                    itemsOfsellPerson.add(player.inventory.get(tempInt));
                                    player.removeItem(player.inventory.get(tempInt));
                                    player.addGold(player.inventory.get(tempInt).getPrice());
                                    System.out.println("Броня продана");
                                    break;
                                }
                                case "2": {
                                    Main.clearConsole();
                                    player.inventory.get(tempInt).getInfo();
                                    break;
                                }
                                case "0": {
                                    Main.clearConsole();
                                    System.out.println("Выход");
                                    return;
                                }
                                default: {
                                    Main.clearConsole();
                                    System.out.println("Неверный ввод");

                                }
                            }
                        }
                    } else if (player.inventory.get(tempInt).getType().equals("Зелье")) {

                        System.out.println("1 - продать, 2 - информация, 0 - назад");
                        Scanner scannerPotion = new Scanner(System.in);
                        String tempPotion = scannerPotion.nextLine();
                        switch (tempPotion) {
                            case "1": {
                                itemsOfsellPerson.add(player.inventory.get(tempInt));
                                player.removeItem(player.inventory.get(tempInt));
                                player.addGold(player.inventory.get(tempInt).getPrice());
                                System.out.println("Зелье продано");
                                break;
                            }
                            case "2": {
                                player.inventory.get(tempInt).getInfo();
                                break;
                            }
                            case "0": {
                                System.out.println("Выход");
                                return;
                            }
                            default: {
                                System.out.println("Неверный ввод");
                            }
                        }
                    } else {
                        System.out.println("1 - продать, 2 - информация, 0 - назад");
                        Scanner scannerFood = new Scanner(System.in);
                        String tempFood = scannerFood.nextLine();
                        switch (tempFood) {
                            case "1": {
                                itemsOfsellPerson.add(player.inventory.get(tempInt));
                                player.removeItem(player.inventory.get(tempInt));
                                player.addGold(player.inventory.get(tempInt).getPrice());
                                System.out.println("Еда продана");
                                break;
                            }
                            case "2": {
                                player.inventory.get(tempInt).getInfo();
                                break;
                            }
                            case "0": {
                                System.out.println("Выход");
                            }
                            default: {
                                System.out.println("Неверный ввод");
                            }
                        }
                    }
                } else {
                    System.out.println("Неверный ввод");
                    Main.clearConsole();
                    return;
                }
            } else {
                System.out.println("Выход");
                return;
            }
        }
    }

    // Открытие магазина для покупки
    public void openMagazine(Person player, boolean isShopSort) {

        while (true) {
            printMagazine(player, isShopSort);
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

