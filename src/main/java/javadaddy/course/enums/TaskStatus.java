package javadaddy.course.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    TODO(1),
    IN_PROGRESS(2),
    DONE(3);

    TaskStatus(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    private final int sortPriority;
}
