package mortis;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "tasks list initialized";
    }
    public TaskList(ArrayList<Task> initial) {
        this.tasks = (initial == null) ? new ArrayList<>() : initial;
        assert tasks != null : "tasks list initialized";
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public Task get(int idx) {
        return tasks.get(idx);
    }

    public Task add(Task t) {
        tasks.add(t);
        assert tasks.contains(t) : "added task must be in list";
        return t;
    }

    public Task delete(int idx) {
        Task removed = tasks.remove(idx);
        assert !tasks.contains(removed) : "task removed should no longer be in list";
        return removed;
    }

    public Task mark(int idx) {
        Task t = tasks.get(idx);
        t.markAsDone();
        assert idx >= 1 && idx <= size() : "mark/unmark index valid";
        assert t != null : "task retrieved should not be null";
        return t;
    }

    public Task unmark(int idx) {
        Task t = tasks.get(idx);
        t.unmark();
        assert idx >= 1 && idx <= size() : "mark/unmark index valid";
        assert t != null : "task retrieved should not be null";
        return t;
    }

    public ArrayList<Task> asList() {
        return tasks;
    }

    /**
     * Finds tasks whose description contains the given keyword.
     *
     * @param keyword The keyword to search for in the task description.
     * @return An ArrayList of tasks whose descriptions contain the keyword.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(task);
            }
        }
        return matches;
    }

    public Task edit(int index, EditSpec spec) throws MortisException {
        if (index < 0 || index >= tasks.size()) {
            throw new MortisException("No such task at index " + (index + 1));
        }

        Task t = tasks.get(index);

        if (spec.getNewDescription() != null) {
            t.setDescription(spec.getNewDescription());
        }
        if (t instanceof Deadline && spec.getNewBy() != null) {
            ((Deadline) t).setBy(spec.getNewBy());
        }
        if (t instanceof Event) {
            Event e = (Event) t;
            if (spec.getNewFrom() != null) e.setFrom(spec.getNewFrom());
            if (spec.getNewTo() != null) e.setTo(spec.getNewTo());
        }

        return t;
    }

}

