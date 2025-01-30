package tasks.test;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.*;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    TaskManager manager;

    @Test
    void getEpicId() {
        manager = Managers.getDefaultTaskManager();

        Epic epic1 = new Epic("Test addNewEpic1", "Test addNewEpic1 description", TaskStatus.NEW);
        Epic epic1M = manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Test addNewSubtask1", "Test addNewSubtask1 description",
                TaskStatus.NEW, epic1.getId());
        subtask1 = manager.addSubtask(subtask1);

        int epicId = epic1M.getId();
        assertEquals(subtask1.getEpicId(), epicId, "Id разные");
    }
}