package service;

import javadaddy.course.enums.TaskStatus;
import javadaddy.course.model.Task;
import javadaddy.course.model.TaskHandler;
import javadaddy.course.service.TaskService;
import javadaddy.course.service.TaskServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class TaskServiceTest {
    TaskHandler taskHandler;
    TaskService taskService;

    @BeforeEach
    void setUp() {
        taskHandler = new TaskHandler(new ArrayList<>());
        taskService = new TaskServiceImpl(taskHandler);
    }

    @Test
    void addTask_addOneTask() {
        Task newTask = new Task("Task 1", "Description 1", LocalDate.now().plusDays(1), TaskStatus.TODO);
        taskService.addTask(newTask);
        Assertions.assertTrue(taskHandler.getTasks().contains(newTask));
    }

    @Test
    void addTask_addFewTasks() {
        Task task1 = new Task("Task 1", "Description 1", LocalDate.now().plusDays(1), TaskStatus.TODO);
        Task task2 = new Task("Task 2", "Description 2", LocalDate.now().plusDays(2), TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Task 3", "Description 3", LocalDate.now().plusDays(3), TaskStatus.TODO);
        taskService.addTask(task1);
        taskService.addTask(task2);
        taskService.addTask(task3);
        Assertions.assertTrue(taskHandler.getTasks().containsAll(List.of(task1, task2, task3)));
    }

    @Test
    void sortTasks_differentSorters() {
        Comparator<Task> comparator = (t1,t2) -> t1.getDueDate().compareTo(t2.getDueDate());
        taskService.sortTasks(comparator);
        Assertions.assertEquals(taskHandler.getSorter(), comparator);

        Comparator<Task> comparator2 = (t1,t2) -> Integer.compare(t1.getStatus().getSortPriority(), t2.getStatus().getSortPriority());
        taskService.sortTasks(comparator2);
        Assertions.assertEquals(taskHandler.getSorter(), comparator2);
    }

    @Test
    void sortTasks_nullSorter() {
        taskService.sortTasks(null);
        Assertions.assertNull(taskHandler.getSorter());
    }

    @Test
    void filterTasks_differentFilters() {
        Predicate<Task> predicate = t -> t.getDueDate().isBefore(LocalDate.now().plusDays(1));
        taskService.filterTasks(predicate);
        Assertions.assertEquals(taskHandler.getFilter(), predicate);

        Predicate<Task> predicate2 = t -> t.getDueDate().isBefore(LocalDate.now().minusDays(1));
        taskService.filterTasks(predicate2);
        Assertions.assertEquals(taskHandler.getFilter(), predicate2);
    }

    @Test
    void updateTask_updateOneTask() {
        taskService.addTask(new Task("Task 1", "Description 1", LocalDate.now().plusDays(1), TaskStatus.TODO));
        Task task = taskService.getTasks().getFirst();
        task.setTitle("Task 2");
        task.setDescription("Description 2");
        taskService.updateTask(task);
        Assertions.assertEquals(taskService.getTaskById(task.getId()), task);
    }

    @Test
    void deleteTask_deleteOneTask() {
        taskService.addTask(new Task("Task 1", "Description 1", LocalDate.now().plusDays(1), TaskStatus.TODO));
        Task task = taskService.getTasks().getFirst();
        taskService.deleteTask(task.getId());
        Assertions.assertThrows(IllegalArgumentException.class, () -> taskService.getTaskById(task.getId()));
    }
}
