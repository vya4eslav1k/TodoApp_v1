package javadaddy.course.service;

import javadaddy.course.model.Task;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(int id);
    void addTask(Task task);
    void sortTasks(Comparator<Task> comparator);
    void filterTasks(Predicate<Task> predicate);
    void updateTask(Task task);
    void deleteTask(int taskId);
}
