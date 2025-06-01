package javadaddy.course.cli;

import javadaddy.course.enums.Command;
import javadaddy.course.enums.SortDirection;
import javadaddy.course.enums.SortOption;
import javadaddy.course.enums.TaskStatus;
import javadaddy.course.model.Task;
import javadaddy.course.model.TaskHandler;
import javadaddy.course.service.TaskService;
import javadaddy.course.service.TaskServiceImpl;
import javadaddy.course.utils.TaskServiceUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class TodoApp {

    private static final TaskHandler taskHandler = new TaskHandler(new ArrayList<>());
    private static final TaskService taskService = new TaskServiceImpl(taskHandler);
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static boolean isRunning = true;

    public static void main(String[] args) {
        //for testing
        //TaskServiceUtils.init(taskService);

        while (isRunning) {
            System.out.println("""
                                \n
                                Type \'list\' to see all tasks
                                Type \'add\' to add a new task
                                Type \'update\' to to update task
                                Type \'update status\' to to update task
                                Type \'delete\' to delete task
                                Type \'filter\' to filter tasks
                                Type \'exit\' to sort tasks
                                """);
            try {
                String command = scanner.nextLine();
                switch (Command.getCommandByAlias(command.toUpperCase())) {
                    case LIST -> {
                        commandList();
                    }
                    case ADD -> {
                        commandAdd();
                    }
                    case UPDATE -> {
                        commandUpdate();
                    }
                    case UPDATE_STATUS -> {
                        commandUpdateStatus();
                    }
                    case DELETE -> {
                        commandDelete();
                    }
                    case FILTER -> {
                       commandFilter();
                    }
                    case SORT -> {
                        commandSort();
                    }
                    case EXIT -> {
                        commandExit();
                    }
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Unhandled exception: " + e.getMessage());
            }
        }
    }

    private static void commandList() {
        System.out.println("Tasks:");
        for (Task task : taskService.getTasks()) {
            System.out.println(task);
        }
    }

    private static void commandAdd() {
        Task task = new Task();
        task.setStatus(TaskStatus.TODO);
        System.out.println("Type task title:");
        String taskTitle = scanner.nextLine();
        task.setTitle(taskTitle);
        System.out.println("Type task description:");
        String taskDescription = scanner.nextLine();
        task.setDescription(taskDescription);
        System.out.println("Type task due date in format yyyy-MM-dd:");
        String dueDateString = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateString, formatter);
        task.setDueDate(dueDate);
        taskService.addTask(task);
        System.out.println("\"" + taskTitle + "\" task added!");
    }

    private static void commandUpdate() {
        Task task = new Task();
        System.out.println("Type id of the task to update:");
        task.setId(Integer.parseInt(scanner.nextLine()));
        Task oldTask = taskService.getTaskById(task.getId());
        task.setStatus(oldTask.getStatus());
        System.out.println("Type new task title:");
        String taskTitle = scanner.nextLine();
        task.setTitle(taskTitle);
        System.out.println("Type new task description:");
        String taskDescription = scanner.nextLine();
        task.setDescription(taskDescription);
        System.out.println("Type new task due date in format yyyy-MM-dd:");
        String dueDateString = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateString, formatter);
        task.setDueDate(dueDate);
        taskService.updateTask(task);
        System.out.println("\"" + taskTitle + "\" task updated!");
    }

    private static void commandUpdateStatus() {
        Task task;
        System.out.println("Type id of the task to update:");
        int taskId = Integer.parseInt(scanner.nextLine());
        task = taskService.getTaskById(taskId);
        System.out.println("Type new task status:");
        task.setStatus(TaskStatus.valueOf(scanner.nextLine()));
        System.out.println("\"" + task.getTitle() + "\" task updated!");
    }

    private static void commandDelete() {
        System.out.println("Type id of the task to delete:");
        int taskId = Integer.parseInt(scanner.nextLine());
        taskService.deleteTask(taskId);
        System.out.println("Task deleted!");
    }

    private static void commandFilter() {
        System.out.println("""
                                \n
                                Type \'disable\' to disable filter
                                Type \'todo\' to filter tasks in "todo" status
                                Type \'in progress\' to filter tasks in "in progress" status
                                Type \'done\' to filter tasks in "done" status
                                """);
        String commandString = scanner.nextLine();
        if (commandString.equalsIgnoreCase("DISABLE")) {
            taskService.filterTasks(null);
            System.out.println("Filter is disabled!");
            return;
        }
        TaskStatus taskStatus = TaskStatus.valueOf(commandString.toUpperCase());
        taskService.filterTasks(t -> t.getStatus() == taskStatus);
        System.out.println("Tasks filtered!");
    }

    private static void commandSort() {
        System.out.println("""
                                \n
                                Type \'disable\' to disable sorter
                                Type \'due date\' to sort tasks according to "due date"
                                Type \'status\' to filter tasks according to "status"
                                """);
        String commandString = scanner.nextLine();
        if (commandString.equalsIgnoreCase("DISABLE")) {
            taskService.sortTasks(null);
            System.out.println("Sorter is disabled!");
            return;
        }
        SortOption sortOption = SortOption.valueOf(commandString.toUpperCase());
        System.out.println("""
                                \n
                                Select sort direction
                                Type \'asc\' to sort ascending
                                Type \'desc\' to sort descending
                                """);
        String sortDirectionString = scanner.nextLine();
        SortDirection sortDirection = SortDirection.valueOf(sortDirectionString.toUpperCase());
        int sortDirectionValue = sortDirection == SortDirection.ASC ? 1 : -1;
        Comparator<Task> comparator = null;
        switch (sortOption) {
            case DUE_DATE -> {
                comparator = (t1, t2) ->
                        t1.getDueDate().compareTo(t2.getDueDate()) * sortDirectionValue;
            }
            case STATUS -> {
                comparator = (t1, t2) ->
                        Integer.compare(t1.getStatus().getSortPriority(), t2.getStatus().getSortPriority()) * sortDirectionValue;
            }
        }
        taskService.sortTasks(comparator);
        System.out.println("Tasks sorted!");
    }

    private static void commandExit() {
        System.out.println("Goodbye!");
        isRunning = false;
    }
}
