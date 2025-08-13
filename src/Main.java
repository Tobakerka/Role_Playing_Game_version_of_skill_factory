public class Main {


    public static void main(String[] args) {

        Person.Human h = new Person.Human("Тест");
        Person s = Game.spawnPerson(3);

        Game.spawnPerson(1);

        h.showStats();
        h.levelUp(300);

        h.showStats();
        s.showStats();





    }

}
