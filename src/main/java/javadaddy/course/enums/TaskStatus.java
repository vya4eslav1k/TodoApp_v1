package javadaddy.course.enums;

public enum TaskStatus {
    TODO(1),
    IN_PROGRESS(2),
    DONE(3);

    TaskStatus(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    private final int sortPriority;
}
