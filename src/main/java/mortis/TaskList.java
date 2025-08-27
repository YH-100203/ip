package mortis;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    public TaskList(ArrayList<Task> initial) {
        this.tasks = (initial == null) ? new ArrayList<>() : initial;
    }

    public int size() { return tasks.size(); }
    public boolean isEmpty() { return tasks.isEmpty(); }
    public Task get(int idx) { return tasks.get(idx); }

    public Task add(Task t) {
        tasks.add(t);
        return t;
    }

    public Task delete(int idx) {
        return tasks.remove(idx);
    }

    public Task mark(int idx) {
        Task t = tasks.get(idx);
        t.markAsDone();
        return t;
    }

    public Task unmark(int idx) {
        Task t = tasks.get(idx);
        t.unmark();
        return t;
    }

    public ArrayList<Task> asList() { return tasks; }
}

