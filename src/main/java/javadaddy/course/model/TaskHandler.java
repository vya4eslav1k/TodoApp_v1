package javadaddy.course.model;

import lombok.*;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Setter
@Getter
@RequiredArgsConstructor
public class TaskHandler {
    @NonNull
    List<Task> tasks;
    Predicate<Task> filter;
    Comparator<Task> sorter;
    int nextTaskId = 0;
}
