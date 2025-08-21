public class Todo extends Task {
    // tasks without any date/time attached to it
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
