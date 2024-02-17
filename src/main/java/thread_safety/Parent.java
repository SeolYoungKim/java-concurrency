package thread_safety;

public class Parent {
    private int parentField;

    public Parent() {
        System.out.println("called Parent constructor");
        parentField = 9999;
        printField();
    }

    public void printField() {
        System.out.println("field = " + parentField);
    }
}
