package lesson1;

import java.util.ArrayList;

public class LessonsApp {
    public static void main(String[] args) {

        ObstacleCourse[] obstacleCourses = {
                new Track(300),
                new Wall(5),
                new Track(200),
                new Wall(3)
        };

        Actions[] arrayParticipants = {
                new Human(500, 3),
                new Cat(1000, 7),
                new Robot(200, 0)
        };

        Participant[] participants = new Participant[arrayParticipants.length];

        for (int i = 0; i < participants.length; i++) {
            participants[i] = new Participant(arrayParticipants[i]);
        }

        for (ObstacleCourse obstacleCourse : obstacleCourses) {
            obstacleCourse.info();

            for (Participant participant : participants) {
                if (participant.isCanParticipate()) {
                    participant.run(obstacleCourse);
                    participant.jump(obstacleCourse);
                }
            }
        }

    }
}
