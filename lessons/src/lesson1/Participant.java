package lesson1;

public class Participant {
    private boolean canParticipate;
    private Object participant;

    public Participant(Object participant) {
        this.canParticipate = true;
        this.participant = participant;
    }

    public void run(ObstacleCourse obstacleCourse) {
        int trackLenth = obstacleCourse.getLength();
        if (trackLenth == 0 || !this.canParticipate) {
            return;
        }

        if (this.participant instanceof Human) {
            this.canParticipate = ((Human) this.participant).run(trackLenth);
        } else if (this.participant instanceof Cat) {
            this.canParticipate = ((Cat) this.participant).run(trackLenth);
        } else if (this.participant instanceof Robot) {
            this.canParticipate = ((Robot) this.participant).run(trackLenth);
        }

    }

    public void jump(ObstacleCourse obstacleCourse) {
        int wallHeight = obstacleCourse.getHeight();
        if (wallHeight == 0 || !this.canParticipate) {
            return;
        }

        if (this.participant instanceof Human) {
            this.canParticipate = ((Human) this.participant).jump(wallHeight);
        } else if (this.participant instanceof Cat) {
            this.canParticipate = ((Cat) this.participant).jump(wallHeight);
        } else if (this.participant instanceof Robot) {
            this.canParticipate = ((Robot) this.participant).jump(wallHeight);
        }
    }
}
