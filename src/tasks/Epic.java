package tasks;


import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {


    private ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public Epic(String name, String description, TaskStatus status, ArrayList<Integer> subtaskId) {
        super(name, description, status);
        this.subtaskId = subtaskId;
    }

    public void addSubtaskId(int subtaskId) {
        this.subtaskId.add(subtaskId);
    }

    // Метод для удаления идентификатора сабтаска
    public void removeSubtaskId(int subtaskId) {
        this.subtaskId.remove(Integer.valueOf(subtaskId));
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }

    public void removeSubtask(Integer subtask) {
        subtaskId.remove(subtask);
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
        return String.format("Epic{id=%d, name='%s', status=%s, description='%s', subtaskIds=%s}",
                getId(),          // %d
                getName(),        // %s
                getStatus(),      // %s
                getDescription(), // %s
                getSubtaskId());   // %s
    }


}
