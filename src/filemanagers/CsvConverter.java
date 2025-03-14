package filemanagers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;

public class CsvConverter {
    // Заголовок CSV-файла
    public static final String CSV_HEADER = "epicId,type,name,status,description,epic";

    public static TaskType getType(String line) {
        String[] parts = line.split(",");
        return TaskType.valueOf(parts[1]);
    }

    public static Task lineToTask(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        String description = parts[4];
        Task task = new Task(name, description, status);
        task.setId(id);
        return task;
    }

    public static Epic lineToEpic(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        String description = parts[4];
        Epic epic = new Epic(name, description, status);
        epic.setId(id);
        return epic;
    }

    public static Subtask lineToSubtask(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        String description = parts[4];
        int epicId = Integer.parseInt(parts[5]);
        Subtask subtask = new Subtask(name, description, status, epicId);
        subtask.setId(id);
        return subtask;
    }

    // Преобразование Task в строку CSV
    public static String toCsv(Task task) {
        return String.format("%d,%s,%s,%s,%s,-", task.getId(), TaskType.TASK, task.getName(), task.getStatus(), task.getDescription());
    }

    // Преобразование Epic в строку CSV
    public static String toCsv(Epic epic) {
        return String.format("%d,%s,%s,%s,%s", epic.getId(), TaskType.EPIC, epic.getName(), epic.getStatus(), epic.getDescription());
    }

    // Преобразование Subtask в строку CSV
    public static String toCsv(Subtask subtask) {
        return String.format("%d,%s,%s,%s,%s,%d", subtask.getId(), TaskType.SUBTASK, subtask.getName(), subtask.getStatus(), subtask.getDescription(), subtask.getEpicId());
    }

    // Преобразование строки CSV в Task
    public static Task csvToTask(String csv) {
        String[] parts = csv.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        String description = parts[4];
        Task task = new Task(name, description, status);
        task.setId(id);
        return task;
    }

    // Преобразование строки CSV в Epic
    public static Epic csvToEpic(String csv) {
        String[] parts = csv.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        String description = parts[4];
        ArrayList<Integer> subtaskIds = new ArrayList<>();
        if (parts.length > 5 && !parts[5].equals("-")) {
            String[] subtaskIdsArray = parts[5].split(",");
            for (String subtaskId : subtaskIdsArray) {
                subtaskIds.add(Integer.parseInt(subtaskId));
            }
        }
        Epic epic = new Epic(name, description, status, subtaskIds);
        epic.setId(id);
        return epic;
    }

    // Преобразование строки CSV в Subtask
    public static Subtask csvToSubtask(String csv) {
        String[] parts = csv.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        String description = parts[4];
        int epicId = Integer.parseInt(parts[5]);

        Subtask subtask = new Subtask(name, description, status, epicId);
        subtask.setId(id);
        return subtask;
    }
}