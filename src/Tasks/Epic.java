package Tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {


    ArrayList<Integer> subtaskId = new ArrayList<>();

    Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    Epic(String name, String description, TaskStatus status, int id, ArrayList<Integer> subtaskId) {
        super(name, description, status, id);
        this.subtaskId = subtaskId;
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskId, epic.subtaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskId);
    }

    public void addIdOfSubtasks(Subtask subtask) {
        subtaskId.add(subtask.getId());
    }


    @Override
    public String toString() {
        return "Tasks.Epic{" +
                "subtaskId=" + subtaskId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
