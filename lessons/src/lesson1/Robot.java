package lesson1;

public class Robot implements Actions {
    private final int maxlength;
    private final int maxheight;

    public Robot(int maxlength, int maxheight) {
        this.maxlength = maxlength;
        this.maxheight = maxheight;
    }

    public int getMaxlength() {
        return maxlength;
    }

    public int getMaxheight() {
        return maxheight;
    }

    @Override
    public boolean run(int trackLenth) {

        if (trackLenth <= this.maxlength) {
            System.out.println("Robot run");
            return true;
        } else {
            System.out.println("Robot not run. Cat is eliminated");
            return false;
        }
    }

    @Override
    public boolean jump(int wallHeight) {

        if (wallHeight <= this.maxheight) {
            System.out.println("Robot jump");
            return true;
        } else {
            System.out.println("Robot not jump. Cat is eliminated");
            return false;
        }
    }
}
