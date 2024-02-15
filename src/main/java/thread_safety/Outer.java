package thread_safety;

public class Outer {
    private int outerField;

    public class Inner {
        private int innerField;

        public void accessOuterField() {
            Outer.this.outerField = 1;
            this.innerField = 2;
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Inner inner = outer.new Inner();
        inner.accessOuterField();
    }
}
