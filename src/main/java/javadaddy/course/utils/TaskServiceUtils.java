package javadaddy.course.utils;

import javadaddy.course.enums.TaskStatus;
import javadaddy.course.model.Task;
import javadaddy.course.service.TaskService;

import java.time.LocalDate;

public class TaskServiceUtils {
    public static void init(TaskService taskService) {
        taskService.addTask(new Task("Task 1", "Description 1", LocalDate.now().plusDays(1), TaskStatus.TODO));
        taskService.addTask(new Task("Task 2", "Description 2", LocalDate.now().plusDays(2), TaskStatus.IN_PROGRESS));
        taskService.addTask(new Task("Task 3", "Description 3", LocalDate.now().plusDays(3), TaskStatus.TODO));
        taskService.addTask(new Task("Task 4", "Description 4", LocalDate.now().plusDays(4), TaskStatus.DONE));
        taskService.addTask(new Task("Task 5", "Description 5", LocalDate.now().plusDays(5), TaskStatus.TODO));
        taskService.addTask(new Task("Task 6", "Description 6", LocalDate.now().plusDays(6), TaskStatus.IN_PROGRESS));
        taskService.addTask(new Task("Task 7", "Description 7", LocalDate.now().plusDays(7), TaskStatus.DONE));
    }
}
