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

        Participant[] participants = {
                new Participant(new Human(500, 3)),
                new Participant(new Cat(1000, 7)),
                new Participant(new Robot(200, 0))
        };

        for (ObstacleCourse obstacleCourse : obstacleCourses) {
            obstacleCourse.info();

            for (Participant participant : participants) {
                participant.run(obstacleCourse);
                participant.jump(obstacleCourse);
            }
        }

    }
}
