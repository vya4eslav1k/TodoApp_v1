package javadaddy.course.service;

import javadaddy.course.model.Task;
import javadaddy.course.model.TaskHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TaskServiceImpl implements TaskService {
    TaskHandler taskHandler;

    @Override
    public List<Task> getAllTasks() {
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
        List<Task> tasks = taskHandler.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.set(i, task);
                break;
            }
        }
    }

    @Override
    public void deleteTask(Task task) {
        List<Task> tasks = taskHandler.getTasks();
        for (int i = 0; i < taskHandler.getTasks().size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                tasks.remove(i);
                break;
            }
        }
    }
}
