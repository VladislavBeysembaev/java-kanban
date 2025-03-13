package filemanagers;

import exception.ManagerSaveException;
import managers.InMemoryTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.*;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    private FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(CsvConverter.CSV_HEADER);
            writer.newLine();

            for (Task task : getTasks().values()) {
                writer.write(CsvConverter.taskToCsv(task));
                writer.newLine();
            }

            for (Epic epic : getEpics().values()) {
                writer.write(CsvConverter.epicToCsv(epic));
                writer.newLine();
            }

            for (Subtask subtask : getSubtasks().values()) {
                writer.write(CsvConverter.subtaskToCsv(subtask));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void saveToFile() throws ManagerSaveException {
        save();
    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Пропускаем заголовок
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[1];
                String name = parts[2];
                TaskStatus status = TaskStatus.valueOf(parts[3]);
                String description = parts[4];
                int id = Integer.parseInt(parts[0]);

                switch (type) {
                    case "TASK":
                        Task task = new Task(name, description, status);
                        task.setId(id); // Устанавливаем идентификатор из файла
                        manager.getTasks().put(id, task); // Добавляем задачу в мапу
                        break;

                    case "EPIC":
                        Epic epic = new Epic(name, description, status);
                        epic.setId(id); // Устанавливаем идентификатор из файла
                        manager.getEpics().put(id, epic); // Добавляем эпик в мапу
                        break;

                    case "SUBTASK":
                        int epicId = Integer.parseInt(parts[5]); // Получаем идентификатор эпика
                        Subtask subtask = new Subtask(name, description, status, epicId);
                        subtask.setId(id); // Устанавливаем идентификатор из файла
                        manager.getSubtasks().put(id, subtask); // Добавляем сабтаск в мапу

                        // Добавляем идентификатор сабтаска в список эпика
                        if (manager.getEpics().containsKey(epicId)) {
                            manager.getEpics().get(epicId).addSubtaskId(id);
                        }
                        break;

                    default:
                        throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
                }

                // Обновляем счетчик идентификаторов
                if (id > manager.getIdCounter()) {
                    FileBackedTaskManager.setIdCounter(id + 1);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла: " + e.getMessage());
        }
        return manager;
    }

    @Override
    public Task addTask(Task task) {
        super.addTask(task);
        save();
        return task;
    }


    @Override
    public Epic addEpic(Epic epic) {
        super.addEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
        return subtask;
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

}

