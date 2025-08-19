import java.util.Random;

public class Fight extends Thread {

    Person player;
    Person monster;
    int tempAgility;
    int difficulty;
    double difPower;
    long expTemp;
    boolean isInventorySort;

    public Fight(Person player, int difficulty, boolean isInventorySort) {
        this.difficulty = difficulty;
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

                switch (Main.checkInt("Выберите действие:\n1. Атаковать\n2. Попробовать сбежать (- 20 Энергии)\n3. Открыть инвентарь", 3)) {

                    case 1: {

                        isFight = false;
                        while (player.getIsAlive() && monster.getIsAlive()) {

                            player.move(1);
                            if (player.getIsAlive()) {

                                System.out.println("Игрок: " + player.getName() + " HP: " + player.getHealth() + " Энергия: " + player.getInfoEnergy() + " " + "Противник: " + monster.getName() + " HP: " + monster.getHealth());
                                tempAgility = player.getAgility() - monster.getAgility();

                                Random random = new Random();
                                int agilityP = random.nextInt(player.getAgility() + tempAgility);
                                int agilityM = random.nextInt(monster.getAgility());

                                if (agilityP >= agilityM) {
                                    isPlayerAttack = true;
                                } else {
                                    isPlayerAttack = false;
                                }
                                if (isPlayerAttack) {

                                    System.out.println("Игрок " + player.getName() + " атакует!");
                                    int dopDamage = monster.checkVulnerability(player.getWeapon());
                                    int tempKrit;
                                    if (player.getAgility() * 3 > random.nextInt(100)) {
                                        if (random.nextInt(100) > 80) {
                                            System.out.println("Критический удар!\n");
                                            tempKrit = (int) Math.round((player.getPower() + dopDamage + player.getWeapon().getDamage()) * 1.5);
                                            tempKrit = tempKrit - monster.getArmor().getDefense();
                                            if (tempKrit < 0) {
                                                tempKrit = 1;
                                                expTemp += Math.abs(tempKrit);
                                            } else {
                                                tempKrit = (int) Math.round(tempKrit * difPower);
                                                expTemp += Math.abs(tempKrit);
                                            }
                                            System.out.println("Игрок " + player.getName() + " наносит " + monster.getName() + " " + tempKrit + " урона!\n");
                                            monster.giveAttack(tempKrit);
                                            System.out.println();
                                        } else {
                                            tempKrit = player.getPower() + dopDamage + player.getWeapon().getDamage();
                                            tempKrit = tempKrit - monster.getArmor().getDefense();
                                            if (tempKrit < 0) {
                                                tempKrit = 1;
                                                expTemp += Math.abs(tempKrit);

                                            } else {
                                                tempKrit = (int) Math.round(tempKrit * difPower);
                                                expTemp += Math.abs(tempKrit);
                                            }
                                            System.out.println("Игрок " + player.getName() + " наносит " + monster.getName() + " " + tempKrit + " урона!");
                                            monster.giveAttack(tempKrit);
                                        }

                                    } else {
                                        System.out.println("Игрок " + player.getName() + " промахивается");
                                    }
                                    isPlayerAttack = false;
                                } else {
                                    System.out.println("Противник " + monster.getName() + " атакует!");
                                    int tempKrit;
                                    if (monster.getAgility() * 3 > random.nextInt(100)) {
                                        if (random.nextInt(100) > 80) {
                                            System.out.println("Критический удар!");
                                            tempKrit = (int) Math.round((monster.getPower() + monster.getWeapon().getDamage()) * 1.5);
                                            tempKrit = tempKrit - player.getArmor().getDefense();
                                            if (tempKrit < 0) {
                                                tempKrit = 1;
                                                expTemp += Math.abs(tempKrit);
                                            } else {
                                                tempKrit = (int) Math.round(tempKrit / difPower);
                                                expTemp += Math.abs(tempKrit);
                                            }
                                            System.out.println("Противник " + monster.getName() + " наносит " + player.getName() + " " + tempKrit + " урона!");
                                            player.giveAttack(tempKrit);
                                        } else {
                                            tempKrit = monster.getPower() + monster.getWeapon().getDamage();
                                            tempKrit = tempKrit - player.getArmor().getDefense();
                                            if (tempKrit < 0) {
                                                tempKrit = 1;
                                                expTemp += Math.abs(tempKrit);
                                            } else {
                                                tempKrit = (int) Math.round(tempKrit / difPower);
                                                expTemp += Math.abs(tempKrit);
                                            }
                                            player.giveAttack(tempKrit);
                                            System.out.println("Противник " + monster.getName() + " наносит " + player.getName() + " " + tempKrit + " урона!");
                                        }
                                    } else {
                                        System.out.println("Противник промахивается");
                                        expTemp += monster.getPower() + monster.getWeapon().getDamage();
                                    }
                                    isPlayerAttack = true;
                                }
                            } else {
                                System.out.println("Ваш персонаж погиб.");
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
                        player.move(-20);
                        Random random = new Random();
                        boolean isEscape = random.nextBoolean();
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
                        player.openInventary(isInventorySort);
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
