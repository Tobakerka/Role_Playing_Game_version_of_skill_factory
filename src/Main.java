public class Main {


    public static void main(String[] args) {

        Person.Human h = new Person.Human("Тест");
        Person.Skeleton s = new Person.Skeleton("Тест", 100, 2, 3, 10, 1);

        h.levelUp(100, h.getlevelUpThreshold());


    }

}
