package thread_safety;

public class Child extends Parent {
    private int childField = 1998;

    @Override
    public void printField() {
        System.out.println("called Child printField");
        System.out.println("field = " + childField);
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.printField();
    }
}
