import filemanagers.FileBackedTaskManager;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileBackedTaskManagerTest {

    private FileBackedTaskManager manager;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("task_manager_test", ".txt");
        manager = FileBackedTaskManager.loadFromFile(tempFile);
    }

    // Тест на добавление и сохранение тасков
    @Test
    void fileBackedTaskManager_Save_And_Load() {
        Epic epic = new Epic("Test Epic", "Epic description", TaskStatus.NEW);
        manager.addEpic(epic);
        Subtask subtask = new Subtask("Test Subtask", "Subtask description", TaskStatus.NEW, epic.getId());
        manager.addSubtask(subtask);
        Task task = new Task("Test Task", "Description of test task", TaskStatus.DONE);
        manager.addTask(task);
        manager.saveToFile();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertEquals(1, loadedManager.getTaskList().size());
        assertEquals(task, loadedManager.getTaskList().getFirst());
    }

    // Тест на сохранение и  загрузку пустого файла
    @Test
    void fileBackedTaskManager_SaveAndLoadEmptyFile() {
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertTrue(loadedManager.getTaskList().isEmpty());
    }

    // Удаляем задачу и загружаем из файла
    @Test
    void fileBackedTaskManager_DeleteTaskAndLoadFromFile() throws IOException {
        Task task1 = new Task("Test Task 1", "Description 1", TaskStatus.DONE);
        Task task2 = new Task("Test Task 2", "Description 2", TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.saveToFile();

        manager.deleteTaskById(task1.getId());
        manager.saveToFile();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertEquals(1, loadedManager.getTaskList().size());
        assertEquals(task2, loadedManager.getTaskList().getFirst());
    }

    // Добавляем задачу после загрузки из фала
    @Test
    void fileBackedTaskManager_AddTasksAfterLoadingFromFile() throws IOException {
        Task task1 = new Task("Test Task 1", "Description 1", TaskStatus.DONE);
        manager.addTask(task1);
        manager.saveToFile();

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        Task task2 = new Task("Test Task 2", "Description 2", TaskStatus.NEW);
        Task task3 = new Task("Test Task 3", "Description 3", TaskStatus.IN_PROGRESS);
        loadedManager.addTask(task2);
        loadedManager.addTask(task3);

        assertEquals(3, loadedManager.getTaskList().size());
        assertEquals(task1, loadedManager.getTaskList().get(0));
        assertEquals(task2, loadedManager.getTaskList().get(1));
        assertEquals(task3, loadedManager.getTaskList().get(2));
    }
}