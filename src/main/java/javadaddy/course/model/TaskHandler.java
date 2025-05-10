package javadaddy.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Setter
@Getter
@AllArgsConstructor
public class TaskHandler {
    int nextTaskId = 0;
    List<Task> tasks;
    Predicate<Task> filter;
    Comparator<Task> sorter;
}
