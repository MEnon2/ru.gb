package lesson1;

public class Cat implements Actions {

    private final int maxlength;
    private final int maxheight;

    public Cat(int maxlength, int maxheight) {
        this.maxlength = maxlength;
        this.maxheight = maxheight;
    }

    @Override
    public boolean run(int trackLenth) {
        if (trackLenth <= this.maxlength) {
            System.out.println("Cat run");
            return true;
        } else {
            System.out.println("Cat not run. Cat is eliminated");
            return false;
        }
    }

    @Override
    public boolean jump(int wallHeight) {
        if (wallHeight <= this.maxheight) {
            System.out.println("Cat jump");
            return true;
        } else {
            System.out.println("Cat not jump. Cat is eliminated");
            return false;
        }
    }
}
