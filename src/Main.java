public class Main {


    public static void main(String[] args) {

        Person.Human h = new Person.Human("Тест");
        Person s = Game.spawnPerson(3);

        Game.spawnPerson(1);

        h.showStats();
        h.levelUp(40000);
        h.showStats();

        h.inventory.addAll(Game.generateItem(20, 10));
        h.openInventary();



    }

}
