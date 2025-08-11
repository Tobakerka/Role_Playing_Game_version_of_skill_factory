public class Main {


    public static void main(String[] args) {

        Person.Human h = new Person.Human("Тест");
        Person s = Game.spawnPerson(3);

        Game.spawnPerson(1);


        s.showStats();

        Game.lootOfPerson(h, s);
        h.showStats();

    }

}
