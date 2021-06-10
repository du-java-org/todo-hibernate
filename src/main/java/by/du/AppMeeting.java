package by.du;

import by.du.model.Meeting;
import by.du.repository.Dao;
import by.du.repository.MeetingDao3;
import by.du.service.MeetingService;
import by.du.util.HibernateConfig;
import org.hibernate.Session;

import java.time.LocalDateTime;

public class AppMeeting {
    public static void main(String[] args) {
        final Session session = HibernateConfig.createSession();
        final MeetingService meetingService = getMeetingService(session);

        final Meeting meeting1 = Meeting.builder()
                .place("place1")
                .time(LocalDateTime.now())
                .desc("desc1")
                .build();

        final Meeting meeting = meetingService.create(meeting1);
//        final Meeting byId = meetingService.findById(1);
//        final List<Meeting> all = meetingService.findAll();
//        meeting.setPlace("place1.1");
//        meetingService.update(meeting);
//        meetingService.delete(meeting);

        meetingService.findAllBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

    }

    private static MeetingService getMeetingService(Session session) {
        final Dao<Meeting> meetingDao = new MeetingDao3(session);
        return new MeetingService(meetingDao);
    }

//    private static TaskService getTaskService() {
//        final Dao<Task> dao = new TaskDao2();
//        return new TaskService(dao);
//    }
}
