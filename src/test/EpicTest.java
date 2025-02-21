package test;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    TaskManager manager;

    @Test
    void getSubtaskId() {
        manager = Managers.getDefaultTaskManager();
        Epic epic1 = new Epic("Test addNewEpic1", "Test addNewEpic1 description.", TaskStatus.NEW);
        Epic epic1M = manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Test addNewSubtask1", "Test addNewSubtask1 description.",
                TaskStatus.NEW, epic1.getId());
        subtask1 = manager.addSubtask(subtask1);

        Subtask subtask2 = new Subtask("Test addNewSubtask2", "Test addNewSubtask2 description.",
                TaskStatus.NEW, epic1.getId());

        subtask2 = manager.addSubtask(subtask2);
        Integer[] list = new Integer[]{2, 3};
        List<Integer> listSubtask = epic1.getSubtaskId();
        Integer[] integerArray = new Integer[listSubtask.size()];
        integerArray = listSubtask.toArray(integerArray);


        assertArrayEquals(integerArray, list, "Номер субтаска не совпадает");

    }

    @Test
    void isEmptySubtasks() {
        manager = Managers.getDefaultTaskManager();
        Epic epic1 = new Epic("Test addNewEpic1", "Test addNewEpic1 description.", TaskStatus.NEW);
        Epic epic1M = manager.addEpic(epic1);
        List<Subtask> subtasks = manager.getSubtasksList(epic1.getId());

        assertEquals(subtasks.size(), 0, "Список субтасков не пустой");
    }
}