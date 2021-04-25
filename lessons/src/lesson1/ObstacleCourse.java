package lesson1;

public class ObstacleCourse {
    private final String name;
    private final int length;
    private final int height;

    public ObstacleCourse(int length, int height, String name) {
        this.length = length;
        this.height = height;
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

      public void info() {
        if (this.length == 0) {
            System.out.printf("---%s %s---%n", this.name, String.valueOf(this.height));
        } else {
            System.out.printf("---%s %s---%n", this.name, String.valueOf(this.length));
        }

    }
}
