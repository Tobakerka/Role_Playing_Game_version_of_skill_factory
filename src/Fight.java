import java.util.Random;

public class Fight extends Thread {

    Person player;
    Person monster;
    int tempAgilityPlayer, tempAgilityMonster;
    int difficulty;
    double difPower;
    long expTemp;
    boolean isInventorySort;

    public Fight(Person player) {
        this.difficulty = difficulty; // Уровень сложности 1 - легкий, 2 - средний, 3 - сложный
        this.player = player;
        monster = Game.spawnPerson(player.getLevel());
        if (difficulty == 1) {
            difPower = 2;
        } else if (difficulty == 2) {
            difPower = 1.5;
        } else {
            difPower = 1;
        }
        this.isInventorySort = isInventorySort;
    }


    public void run() {

        Object focus = new Object();
        boolean isFight = true;
        while (isFight) {
            synchronized (focus) {
                boolean isPlayerAttack = true;
                expTemp = 0;
                Main.clearConsole();
                System.out.println("Игрок: " + player.toInfo() + "\n\n" + "Против:\n\n" + "Монстр: " + monster.toInfo());

                int count = 1;
                tempAgilityPlayer = player.getAgility() - monster.getAgility();
                if (tempAgilityPlayer < 0) {
                    tempAgilityPlayer = 0;
                }
                tempAgilityMonster = monster.getAgility() - player.getAgility();
                if (tempAgilityMonster < 0) {
                    tempAgilityMonster = 0;
                }

                Random random = new Random();
                int agilityP = random.nextInt(player.getAgility() + tempAgilityPlayer);
                int agilityM = random.nextInt(monster.getAgility() + tempAgilityMonster);

                // Проверка кто атакует первым
                if (agilityP >= agilityM) {
                    isPlayerAttack = true;
                } else {
                    isPlayerAttack = false;
                }
                switch (Main.checkInt("Выберите действие:\n1. Атаковать\n2. Попробовать сбежать (- 20 Энергии)\n3. Открыть инвентарь", 3)) {

                    case 1: {

                        isFight = false;
                        while (player.getIsAlive() && monster.getIsAlive()) {

                            player.move();
                            System.out.println("Ход № " + count++);
                            if (player.getIsAlive()) {

                                Random randomFalisEndKrit = new Random();
                                int randomKrit = randomFalisEndKrit.nextInt(100);
                                System.out.println("Статистика: " + player.getName() + " HP: " + player.getHealth() + " Энергия: " + player.getInfoEnergy() + " | " + monster.getName() + " HP: " + monster.getHealth());

                                if (isPlayerAttack) {

                                    System.out.println("Игрок " + player.getName() + " атакует!");
                                    int dopDamage = monster.checkVulnerability(player.getWeapon());
                                    int tempKrit;
                                    // Чуть изменил логику, поскольку из-за автоматического повышения показателей ловкости необходима была другая логика.

                                    if (randomKrit > 80) {
                                        System.out.println("Критический удар!\n");
                                        tempKrit = (int) Math.round((player.getPower() + dopDamage + player.getWeapon().getDamage()) * 1.5);
                                        // Проверка на защиту
                                        tempKrit = tempKrit - (monster.getDefense() + monster.getArmor().getDefense());
                                        // Если урон отрицательный, то атака наносит 1 урон подразумевается, что защита намного выше
                                        if (tempKrit <= 0) {
                                            tempKrit = 1;
                                            expTemp += Math.abs(tempKrit);
                                        } else {
                                            tempKrit = (int) Math.round(tempKrit * difPower);
                                            expTemp += Math.abs(tempKrit);
                                        }
                                        System.out.println("Игрок " + player.getName() + " наносит " + monster.getName() + " " + tempKrit + " урона!\n");
                                        monster.giveAttack(tempKrit);
                                    } else if (randomKrit > 20 && randomKrit <= 80) {
                                        tempKrit = player.getPower() + dopDamage + player.getWeapon().getDamage();
                                        tempKrit = tempKrit - (monster.getDefense() + monster.getArmor().getDefense());
                                        if (tempKrit <= 0) {
                                            tempKrit = 1;
                                            expTemp += Math.abs(tempKrit);

                                        } else {
                                            tempKrit = (int) Math.round(tempKrit * difPower);
                                            expTemp += Math.abs(tempKrit);
                                        }
                                        System.out.println("Игрок " + player.getName() + " наносит " + monster.getName() + " " + tempKrit + " урона!");
                                        monster.giveAttack(tempKrit);
                                    } else {
                                        System.out.println("Игрок промахивается");
                                        expTemp += Math.abs((player.getPower() + dopDamage + player.getWeapon().getDamage()) - monster.getDefense());
                                    }

                                    isPlayerAttack = false;
                                } else {
                                    System.out.println("Противник " + monster.getName() + " атакует!");
                                    int tempKrit;

                                    if (randomKrit > 80) {
                                        System.out.println("Критический удар!");
                                        tempKrit = (int) Math.round((monster.getPower() + monster.getWeapon().getDamage()) * 1.5);
                                        tempKrit = tempKrit - (player.getDefense() + player.getArmor().getDefense());
                                        if (tempKrit <= 0) {
                                            tempKrit = 1;
                                            expTemp += Math.abs(tempKrit);
                                        } else {
                                            tempKrit = (int) Math.round(tempKrit / difPower);
                                            expTemp += Math.abs(tempKrit);
                                        }
                                        System.out.println("Противник " + monster.getName() + " наносит " + player.getName() + " " + tempKrit + " урона!");
                                        player.giveAttack(tempKrit);
                                    } else if (randomKrit > 20 && randomKrit <= 80){
                                        tempKrit = monster.getPower() + monster.getWeapon().getDamage();
                                        tempKrit = tempKrit - (player.getDefense() + player.getArmor().getDefense());
                                        if (tempKrit <= 0) {
                                            tempKrit = 1;
                                            expTemp += Math.abs(tempKrit);
                                        } else {
                                            tempKrit = (int) Math.round(tempKrit / difPower);
                                            expTemp += Math.abs(tempKrit);
                                        }
                                        player.giveAttack(tempKrit);
                                        System.out.println("Противник " + monster.getName() + " наносит " + player.getName() + " " + tempKrit + " урона!");
                                    } else {
                                        System.out.println("Противник промахивается");
                                        expTemp += monster.getPower() + monster.getWeapon().getDamage();
                                    }

                                    isPlayerAttack = true;
                                }
                            } else {
                                System.out.println("Ваш персонаж погиб.");
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                new CustomException("Ошибка при ожидании");
                            }
                            System.out.println();
                        }
                        break;
                    }
                    case 2: {
                        isFight = false;
                        Random randCheckLuck = new Random();
                        if (randCheckLuck.nextInt(100) == 100) {
                            System.out.println("В попытке сбежать вы попали в засаду и были убиты!");
                            player.setHealth(0);
                            break;
                        }
                        player.move();
                        Random randomLuck = new Random();
                        boolean isEscape = randomLuck.nextBoolean();
                        if (isEscape) {
                            break;
                        } else {
                            System.out.println("В попытке сбежать монстр смог нанести вам урон!");
                            player.giveAttack(monster.getPower() + monster.getWeapon().getDamage());
                            System.out.println("Вы получили урон: " + (monster.getPower() + monster.getWeapon().getDamage()));
                        }
                        break;
                    }
                    case 3: {
                        Main.clearConsole();
                        player.openInventary();
                        break;
                    }
                    default: {

                    }
                }

                player.checkIsAlive();
                monster.checkIsAlive();
                if (player.getIsAlive() && !monster.getIsAlive()) {
                    player.levelUp(expTemp);
                    Game.lootOfPerson(player, monster);
                }
            }
        }
    }
}
