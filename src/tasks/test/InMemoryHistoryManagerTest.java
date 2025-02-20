package tasks.test;
import managers.HistoryManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import tasks.TaskStatus;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    public HistoryManager historyManager = Managers.getDefaultHistoryManager();
    InMemoryTaskManager memoryManager = new InMemoryTaskManager();
    Task task1 = new Task("Task1", "Description Task1", TaskStatus.NEW);
    Task task2 = new Task("Task1", "Description Task1", TaskStatus.NEW);
    Epic epic1 = new Epic("Epic1", "Description Epic1", TaskStatus.NEW);
    List<Object> list = new ArrayList<>();

    @Test
    void addTaskTest() {
        assertEquals(list, historyManager.getHistory());
        assertNotNull(historyManager.getHistory());
        memoryManager.addEpic(epic1);
        historyManager.addTask(task1);
        list.add(task1);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void getHistoryTest() {
        list.clear();
        assertEquals(list, historyManager.getHistory());
        assertNotNull(historyManager.getHistory());
        memoryManager.addTask(task2);
        memoryManager.addTask(task1);
        memoryManager.addEpic(epic1);
        historyManager.addTask(task2);
        historyManager.addTask(task1);
        historyManager.addTask(epic1);

        for (Task task : historyManager.getHistory()) {
            list.add(task);
        }
        assertEquals(3, list.size());
        list.clear();
        list.add(task2);
        list.add(task1);
        list.add(epic1);
        assertEquals(list, historyManager.getHistory());

    }

    @Test
    void removeTest() {
        list.clear();
        assertEquals(list, historyManager.getHistory());
        assertNotNull(historyManager.getHistory());
        memoryManager.addTask(task2);
        memoryManager.addTask(task1);
        memoryManager.addEpic(epic1);
        historyManager.addTask(task2);
        historyManager.addTask(task1);
        historyManager.addTask(epic1);

        for (Task task : historyManager.getHistory()) {
            list.add(task);
        }
        assertEquals(3, list.size());
        historyManager.remove(task1.getId());
        list.clear();
        for (Task task : historyManager.getHistory()) {
            list.add(task);
        }
        assertEquals(2, list.size());

        list.clear();
        list.add(task2);
        list.add(epic1);
        assertEquals(list, historyManager.getHistory());
        // заполнена
    }
}