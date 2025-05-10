package javadaddy.course.cli;

import javadaddy.course.enums.Command;
import javadaddy.course.enums.TaskStatus;
import javadaddy.course.model.Task;
import javadaddy.course.model.TaskHandler;
import javadaddy.course.service.TaskService;
import javadaddy.course.service.TaskServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp {
    public static void main(String[] args) {

        TaskHandler taskHandler = new TaskHandler(new ArrayList<>());
        TaskService taskService = new TaskServiceImpl(taskHandler);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""
                                \n
                                Type \'list\' to see all tasks
                                Type \'add\' to add a new task
                                Type \'update\' to to update task
                                Type \'delete\' to delete task
                                Type \'filter\' to filter tasks
                                Type \'exit\' to sort tasks
                                """);
            try {
                String command = scanner.nextLine();
                switch (Command.valueOf(command.toUpperCase())) {
                    case LIST -> {
                        System.out.println("Tasks:");
                        for (Task task : taskService.getAllTasks()) {
                            System.out.println(task);
                        }
                    }
                    case ADD -> {
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
                    case UPDATE -> {
                        Task task = new Task();
                        System.out.println("Type id of the task to update:");
                        task.setId(Integer.parseInt(scanner.nextLine()));
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
                        taskService.addTask(task);
                        System.out.println("\"" + taskTitle + "\" task updated!");
                    }
                    case UPDATE_STATUS -> {
                        Task task;
                        System.out.println("Type id of the task to update:");
                        int taskId = Integer.parseInt(scanner.nextLine());
                        task = taskService.getTaskById(taskId);
                        System.out.println("Type new task status:");
                        task.setStatus(TaskStatus.valueOf(scanner.nextLine()));
                        System.out.println("\"" + task.getTitle() + "\" task updated!");
                    }
                    case DELETE -> {
                        System.out.println("Type id of the task to delete:");
                        int taskId = Integer.parseInt(scanner.nextLine());
                        taskService.deleteTask(taskId);
                        System.out.println("Task deleted!");
                    }
                    case FILTER -> {
                        //TODO
                    }
                    case SORT -> {
                        //TODO
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
}
