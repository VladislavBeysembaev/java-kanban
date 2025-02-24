import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import tasks.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryTaskManager memoryManager = new InMemoryTaskManager();
    Task task1 = new Task("Task1", "Description Task1.", TaskStatus.NEW);
    Epic epic1 = new Epic("Epic1", "Description Epic1.", TaskStatus.NEW);

    int taskId = 0;
    int epicId = 0;
    int subtaskId = 0;

    @Test
    void createTask() {
        memoryManager.addTask(task1);
        taskId = task1.getId();

        assertTrue(task1 instanceof Task);
    }

    @Test
    void createEpic() {
        memoryManager.addEpic(epic1);
        epicId = epic1.getId();

        assertTrue(epic1 instanceof Epic);
    }

    @Test
    void createSubtask() {
        Subtask subtask1 = new Subtask("Subtask1", "Description Subtask1.",
                TaskStatus.NEW, epic1.getId());
        memoryManager.addEpic(epic1);
        subtaskId = subtask1.getId();
        assertTrue(subtask1 instanceof Subtask);
    }

    @Test
    void getTaskById() {
        memoryManager.addTask(task1);
        taskId = task1.getId();
        Task task = memoryManager.getTaskById(taskId);
        taskId = task1.getId();
        assertEquals(task, task1);
    }

    @Test
    void getEpicById() {
        memoryManager.addEpic(epic1);
        epicId = epic1.getId();
        Epic epic = memoryManager.getEpicById(epicId);
        assertEquals(epic, epic1);
    }

    @Test
    void getSubtaskById() {
        memoryManager.addEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask1", "Description Subtask1.",
                TaskStatus.NEW, epic1.getId());
        memoryManager.addSubtask(subtask1);
        subtaskId = subtask1.getId();
        Subtask subtask = memoryManager.getSubtaskById(subtaskId);
        assertEquals(subtask, subtask1);

    }
}