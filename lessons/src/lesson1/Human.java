package lesson1;

public class Human implements Actions {

    private final int maxlength;
    private final int maxheight;

    public Human(int maxlength, int maxheight) {
        this.maxlength = maxlength;
        this.maxheight = maxheight;
    }

     @Override
    public boolean run(int trackLenth) {
        if (trackLenth <= this.maxlength) {
            System.out.println("Human run");
            return true;
        } else {
            System.out.println("Human not run. Human is eliminated");
            return false;
        }
    }

    @Override
    public boolean jump(int wallHeight) {
        if (wallHeight <= this.maxheight) {
            System.out.println("Human jump");
            return true;
        } else {
            System.out.println("Human not jump. Human is eliminated");
            return false;
        }
    }
}
