package filemanagers;

import exception.ManagerSaveException;
import managers.InMemoryTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.*;


public class FileBackedTaskManager extends InMemoryTaskManager {
    public static void main(String[] args) {
        File file = new File("test.csv");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        Task task1 = new Task("Сделать домашнее задание", "Дочитать теорию", TaskStatus.NEW);
        Task task2 = new Task("Сделать домашние дела", "Подготовить вещи к переезду", TaskStatus.NEW);

        fileBackedTaskManager.addTask(task1);
        fileBackedTaskManager.addTask(task2);


        Epic epic1 = new Epic("Доделать работу", "Сдать в назначенное время", TaskStatus.NEW);
        Epic epic2 = new Epic("Cходить на тренировку", "Сделать упражнения на ноги", TaskStatus.NEW);

        fileBackedTaskManager.addEpic(epic1);
        fileBackedTaskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask("Сходить в аптку", "Купить Нимесил", TaskStatus.NEW,
                epic1.getId());
        Subtask subtask2 = new Subtask("Отвезти кота к ветеринару", "Проверить сердце и сделать справку",
                TaskStatus.NEW, epic2.getId());

        fileBackedTaskManager.addSubtask(subtask1);
        fileBackedTaskManager.addSubtask(subtask2);

        fileBackedTaskManager.save();
    }

    private final File file;

    private FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(CsvConverter.CSV_HEADER);
            writer.newLine();

            for (Task task : getTasks().values()) {
                writer.write(CsvConverter.toCsv(task));
                writer.newLine();
            }

            for (Epic epic : getEpics().values()) {
                writer.write(CsvConverter.toCsv(epic));
                writer.newLine();
            }

            for (Subtask subtask : getSubtasks().values()) {
                writer.write(CsvConverter.toCsv(subtask));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи в файл: " + e.getMessage());
        }
    }


    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Пропускаем заголовок
            String line;

            // Считываем все задачи в мапы
            while ((line = reader.readLine()) != null) {
                TaskType type = CsvConverter.getType(line);
                int id = Integer.parseInt(line.split(",")[0]); // Получаем идентификатор задачи

                switch (type) {
                    case TASK:
                        Task task = CsvConverter.lineToTask(line);
                        manager.getTasks().put(task.getId(), task);
                        break;

                    case EPIC:
                        Epic epic = CsvConverter.lineToEpic(line);
                        manager.getEpics().put(epic.getId(), epic);
                        break;

                    case SUBTASK:
                        Subtask subtask = CsvConverter.lineToSubtask(line);
                        manager.getSubtasks().put(subtask.getId(), subtask);
                        break;

                    default:
                        throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
                }

                // Обновляем счетчик идентификаторов напрямую
                if (id > manager.getIdCounter()) {
                    manager.setIdCounter(id + 1);
                }
            }

            // Добавляем идентификаторы сабтасков в список эпиков
            for (Subtask subtask : manager.getSubtasks().values()) {
                int epicId = subtask.getEpicId();
                if (manager.getEpics().containsKey(epicId)) {
                    manager.getEpics().get(epicId).addSubtaskId(subtask.getId());
                } else {
                    manager.getSubtasks().remove(subtask.getId()); // Удаляем сабтаск из менеджера
                    System.err.println("Эпик с ID " + epicId + " не найден. Сабтаск с ID " +
                            subtask.getId() + " удален из менеджера.");
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

    @Override
    public Task updateTask(Task task) {
        super.updateTask(task);
        save();
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
        return subtask;
    }

}

