package lesson1;

public class Participant {
    boolean canParticipate;
    private int maxlength;
    private int maxheight;

    public Participant(Human participant) {
        this.maxheight = participant.getMaxheight();
        this.maxlength = participant.getMaxlength();
        this.canParticipate = true;
    }

    public Participant(Cat participant) {
        this.maxheight = participant.getMaxheight();
        this.maxlength = participant.getMaxlength();
        this.canParticipate = true;
    }

    public Participant(Robot participant) {
        this.maxheight = participant.getMaxheight();
        this.maxlength = participant.getMaxlength();
        this.canParticipate = true;
    }

    public boolean isCanParticipate() {
        return canParticipate;
    }

    public void setCanParticipate(boolean canParticipate) {
        this.canParticipate = canParticipate;
    }

    public Participant(boolean canParticipate, int maxlength, int maxheight) {
        this.canParticipate = canParticipate;
        this.maxlength = maxlength;
        this.maxheight = maxheight;
    }

    public void run(ObstacleCourse obstacleCourse) {
        int trackLenth = obstacleCourse.getLength();
        if (trackLenth == 0) {
            return;
        }

//        if (distanceLenth <= this.maxlength) {
//            System.out.println("Robot run");
//        } else {
//            System.out.println("Robot not run. Robot is eliminated");
//            this.setCanParticipate(false);
//        }
    }

    public void jump(ObstacleCourse obstacleCourse) {
        int wallHeight = obstacleCourse.getHeight();
        if (wallHeight == 0) {
            return;
        }

//        if (distanceHeight <= this.maxheight) {
//            System.out.println("Robot jump");
//        } else {
//            System.out.println("Robot not jump. Robot is eliminated");
//            this.setCanParticipate(false);
//        }
    }
}
