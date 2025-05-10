package javadaddy.course.model;

import javadaddy.course.enums.TaskStatuses;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@RequiredArgsConstructor
public class Task {
    int id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private LocalDate dueDate;
    @NonNull
    private TaskStatuses status;
}
