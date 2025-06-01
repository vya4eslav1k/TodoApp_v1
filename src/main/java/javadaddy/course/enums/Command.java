package javadaddy.course.enums;

public enum Command {
    LIST("LIST"),
    ADD("ADD"),
    UPDATE("UPDATE"),
    UPDATE_STATUS("UPDATE STATUS"),
    DELETE("DELETE"),
    FILTER("FILTER"),
    SORT("SORT"),
    EXIT("EXIT");


    Command(String alias) {
        this.alias = alias;
    }

    public static Command getCommandByAlias(String alias) {
        for (Command command : Command.values()) {
            if (command.alias.equals(alias)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Unknown command: " + alias);
    }

    private final String alias;
}
