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

    public void printMagazine(Person player) {

        int count = 0;
        int schet = 0;

        System.out.println("Магазин\n" +
                "Ваше золото:" + player.getGold() + "\n" +
                "Предметы:" + itemsOfSales.size() + " из " + countToItems + "\n");

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

    public void printMagazineOfSellPerson(){

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

    public void menuMagazine(Person player) {

        while (true) {
            System.out.printf("Магазин: \n\n1 - Купить | 2 - Продать | 3 - Выкупить | 0 - Выйти\n");
            Scanner scanner = new Scanner(System.in);
            String tempString = scanner.nextLine();
            switch (tempString) {
                case "1" : {
                    openMagazine(player);
                    break;
                }
                case "2" : {
                    sellItem(player);
                    break;
                }
                case "3" : {
                    openSellPerson(player);
                    break;
                }
                case "0" : {
                    System.out.println("Выход");
                    return;
                }
                default : {
                    System.out.println("Неверный ввод");
                    Main.clearConsole();
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

    private void sellItem(Person player) {

        while (true) {
            player.lookInventory();

            System.out.println("Выберите предмет \nлюбая буква или символ для выхода");
            Scanner scanner = new Scanner(System.in);
            int tempInt = 0;
            if (scanner.hasNextInt()) {

                tempInt = scanner.nextInt();
                if (tempInt < player.inventory.size() && tempInt >= 0) {

                    if (player.inventory.get(tempInt).getType().equals("Оружие")) {

                        if (player.inventory.get(tempInt).equals(player.getWeapon())) {

                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerWeapon = new Scanner(System.in);
                            String tempWeapon = scannerWeapon.nextLine();
                            switch (tempWeapon) {
                                case "1": {
                                    player.addGold(player.inventory.get(tempInt).getPrice());
                                    player.setWeapon(null);
                                    itemsOfsellPerson.add(player.inventory.get(tempInt));
                                    player.removeItem(player.inventory.get(tempInt));
                                    System.out.println("Оружие продано");
                                    break;
                                }
                                case "2": {
                                    player.inventory.get(tempInt).getInfo();
                                    break;
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

                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerWeapon1 = new Scanner(System.in);
                            String tempWeaponToEqup = scannerWeapon1.nextLine();
                            switch (tempWeaponToEqup) {
                                case "1": {
                                    player.addGold(player.inventory.get(tempInt).getPrice());
                                    itemsOfsellPerson.add(player.inventory.get(tempInt));
                                    player.removeItem(player.inventory.get(tempInt));
                                    System.out.println("Оружие продано");
                                    break;
                                }
                                case "2": {
                                    player.inventory.get(tempInt).getInfo();
                                    break;
                                }
                                case "0": {
                                    System.out.println("Возврат");
                                    return;
                                }
                                default: {
                                    System.out.println("Неверный ввод");
                                }
                            }
                        }
                    } else if (player.inventory.get(tempInt).getType().equals("Броня")) {

                        if (player.inventory.get(tempInt).equals(player.getArmor())) {
                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerArmor = new Scanner(System.in);
                            String tempArmor = scannerArmor.nextLine();
                            switch (tempArmor) {
                                case "1": {
                                    player.setArmor(null);
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
                            System.out.println("1 - продать, 2 - информация, 0 - назад");
                            Scanner scannerArmorToEqup = new Scanner(System.in);
                            String tempArmorToEqup = scannerArmorToEqup.nextLine();
                            switch (tempArmorToEqup) {
                                case "1": {
                                    itemsOfsellPerson.add(player.inventory.get(tempInt));
                                    player.removeItem(player.inventory.get(tempInt));
                                    player.addGold(player.inventory.get(tempInt).getPrice());
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

