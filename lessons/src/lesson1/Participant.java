package lesson1;

public class Participant {
    private boolean canParticipate;
    private Actions participant;

    public Participant(Actions participant) {
        this.canParticipate = true;
        this.participant = participant;
    }

    public boolean isCanParticipate() {
        return canParticipate;
    }

    public Actions getParticipant() {
        return participant;
    }

    public void run(ObstacleCourse obstacleCourse) {
        int trackLenth = obstacleCourse.getLength();
        if (trackLenth == 0 || !this.canParticipate) {
            return;
        }

        Actions participant = this.getParticipant();
        this.canParticipate = participant.run(trackLenth);

    }

    public void jump(ObstacleCourse obstacleCourse) {
        int wallHeight = obstacleCourse.getHeight();
        if (wallHeight == 0 || !this.canParticipate) {
            return;
        }

        Actions participant = this.getParticipant();
        this.canParticipate = participant.jump(wallHeight);
    }
}
