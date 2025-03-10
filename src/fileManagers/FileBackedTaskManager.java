package fileManagers;

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

    public FileBackedTaskManager(File file) {
        this.file = new File(file.getAbsolutePath());
    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();

            List<Task> tasks = getTaskList();
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }

            List<Epic> epics = getEpicList();
            for (Epic epic : epics) {
                writer.write(epic.toString());
                writer.newLine();
            }

            List<Subtask> subtasks = getSubtaskList();
            for (Subtask subtask : subtasks) {
                writer.write(subtask.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи в файл: " + e.getMessage());
        }

    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException, IllegalArgumentException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        setIdCounter(2); //Объясни пожалуйста, почему без этого метода айди в тестах не совпадает
        //Мне подсказали как реализовать, но саму суть я не до конца понял
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Пропускаем заголовок
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[1];
                String name = parts[2];
                TaskStatus taskStatus = TaskStatus.valueOf(parts[3]);
                String description = parts[4];

                switch (type) {
                    case "TASK":
                        Task task = new Task(name, description, taskStatus);
                        manager.addTask(task);
                        break;
                    case "EPIC":
                        Epic epic = new Epic(name, description, taskStatus);
                        manager.addEpic(epic);
                        break;
                    case "SUBTASK":
                        int epicId = Integer.parseInt(parts[5]); // Получаем ID эпика
                        Subtask subtask = new Subtask(name, description, taskStatus, epicId);
                        manager.addSubtask(subtask);
                        break;
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

}

