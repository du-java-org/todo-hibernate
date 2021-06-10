package by.du;

import by.du.exception.NotFoundException;
import by.du.model.Meeting;
import by.du.model.Status;
import by.du.model.Task;
import by.du.repository.Dao;
import by.du.service.MeetingService;
import by.du.service.TaskService;
import by.du.util.DaoFactory;
import by.du.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class App {
    public static void main(String[] args) {
        final Session session = HibernateUtil.createSession();
        final MeetingService meetingService = getMeetingService(session);
        final TaskService taskService = getTaskService(session);
        meeting(meetingService);
        task(taskService);
    }

    private static MeetingService getMeetingService(Session session) {
        final Dao<Meeting> meetingDao = DaoFactory.buildRepository(Meeting.class, session);
        return new MeetingService(meetingDao);
    }

    private static TaskService getTaskService(Session session) {
        final Dao<Task> dao = DaoFactory.buildRepository(Task.class, session);
        return new TaskService(dao);
    }

    private static void meeting(MeetingService meetingService) {
        final Meeting meeting1 = Meeting.builder()
                .place("place1")
                .time(LocalDateTime.now())
                .desc("desc1")
                .build();

        final Meeting meeting = meetingService.create(meeting1);
        final List<Meeting> all = meetingService.findAll();
        meeting.setPlace("place1.1");
        meetingService.update(meeting);
        meetingService.findAllBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        meetingService.delete(meeting);
        try {
            meetingService.findById(1);
        } catch (NotFoundException ex) {
            log.error("{}", ex.getMessage());
        }
    }

    private static void task(TaskService taskService) {
        final Task task1 = Task.builder()
                .date(LocalDate.now())
                .desc("desc1")
                .status(Status.NEW)
                .build();

        final Task task = taskService.create(task1);
        final List<Task> all = taskService.findAll();
        task.setStatus(Status.READY);
        taskService.update(task);
        taskService.findAllBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
//        taskService.delete(task);
        try {
            taskService.findById(1);
        } catch (NotFoundException ex) {
            log.error("{}", ex.getMessage());
        }
    }
}
