package javadaddy.course.model;

import javadaddy.course.enums.TaskStatus;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {
    int id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private LocalDate dueDate;
    @NonNull
    private TaskStatus status;
}
