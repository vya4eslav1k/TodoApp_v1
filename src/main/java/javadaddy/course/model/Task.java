package javadaddy.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class Task {
    private String title;
    private String description;
    private LocalDate dueDate;
}
