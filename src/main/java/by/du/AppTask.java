package by.du;

import by.du.model.Status;
import by.du.model.Task;
import by.du.repository.Dao;
import by.du.repository.TaskDao5;
import by.du.service.TaskService;
import by.du.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AppTask {
    public static void main(String[] args) {
        final Session session = HibernateUtil.createSession();
        final TaskService taskService = getTaskService(session);

        final Task task1 = Task.builder()
                .date(LocalDate.now())
                .desc("desc1")
                .status(Status.NEW)
                .build();

        final Task task = taskService.create(task1);
        final Task byId = taskService.findById(1);
        final List<Task> all = taskService.findAll();
        task.setStatus(Status.DONE);
        taskService.update(task);
        taskService.findAllBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        taskService.delete(task);
    }

    private static TaskService getTaskService(Session session) {
        final Dao<Task> dao = new TaskDao5(session);
        return new TaskService(dao);
    }
}
