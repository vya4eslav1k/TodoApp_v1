package javadaddy.course.service;

import javadaddy.course.model.Task;
import javadaddy.course.model.TaskHandler;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    TaskHandler taskHandler;

    @Override
    public List<Task> getTasks() {
        Predicate<Task> filter = taskHandler.getFilter();
        Comparator<Task> comparator = taskHandler.getSorter();
        Stream<Task> stream = taskHandler.getTasks().stream();
        if (filter != null) {
            stream = stream.filter(filter);
        }
        if (comparator != null) {
            stream = stream.sorted(comparator);
        }
        return new ArrayList<>(stream.toList());
    }

    public Task getTaskById(int id) {
        Task task = getTask(id);
        return new Task(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus());
    }

    @Override
    public void addTask(Task task) {
        List<Task> tasks = taskHandler.getTasks();
        task.setId(taskHandler.getNextTaskId());
        taskHandler.setNextTaskId(taskHandler.getNextTaskId() + 1);
        tasks.add(task);
    }

    @Override
    public void sortTasks(Comparator<Task> comparator) {
        taskHandler.setSorter(comparator);
    }

    @Override
    public void filterTasks(Predicate<Task> predicate) {
        taskHandler.setFilter(predicate);
    }

    @Override
    public void updateTask(Task task) {
        Task oldTask = getTask(task.getId());
        oldTask.setTitle(task.getTitle());
        oldTask.setDescription(task.getDescription());
        oldTask.setDueDate(task.getDueDate());
        oldTask.setStatus(task.getStatus());
    }

    @Override
    public void deleteTask(int taskId) {
        List<Task> tasks = taskHandler.getTasks();
        Task task = getTask(taskId);
        tasks.remove(task);
    }

    private Task getTask(int taskId) {
        for (Task task : taskHandler.getTasks()) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        throw new IllegalArgumentException("Task with id " + taskId + " does not exist");
    }
}
