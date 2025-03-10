import fileManagers.FileBackedTaskManager;
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

    // Создание временного файла
    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("task_manager_test", ".txt");
        manager = new FileBackedTaskManager(tempFile);
    }

    // Тест загрузки и сохранения нескольких задач
    @Test
    void testSaveAndLoad() {
        Epic epic = new Epic("Test Epic", "Epic description", TaskStatus.NEW);
        manager.addEpic(epic);
        Subtask subtask = new Subtask("Test Subtask", "Subtask description",
                TaskStatus.NEW, epic.getId());
        manager.addSubtask(subtask);
        Task task = new Task("Test Task", "Description of test task", TaskStatus.DONE);
        manager.addTask(task);
        manager.save(); // Сохраняем текущее состояние в файл

        // Создаем новый менеджер и загружаем из файла
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверяем, что загруженная задача соответствует сохраненной
        assertEquals(1, loadedManager.getTaskList().size());
        assertEquals(task, loadedManager.getTaskList().getFirst());
    }

    // Тест на сохранение и загрузку пустого файла
    @Test
    void testSaveAndLoadEmptyFile() {
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);
        assertTrue(loadedManager.getTaskList().isEmpty());
    }
}