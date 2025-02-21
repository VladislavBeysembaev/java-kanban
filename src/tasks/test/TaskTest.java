package tasks.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import managers.Managers;
import tasks.Task;
import managers.TaskManager;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    TaskManager manager;
    Task task1 = new Task("Test addNewTask", "Test addNewTask description.",
            TaskStatus.NEW); //создаем задачу

    @BeforeEach
    void BeforeEach() {
        manager = Managers.getDefaultTaskManager();
    }

    @Test
    void equalsById() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        Task taskFirstModified = manager.updateTask(taskFirst);
        Task taskSecond = manager.getTaskById(taskFirstModified.getId());
        taskSecond.setDescription(taskSecond.getDescription() + " Дополнение 1");
        Task taskSecondModified = manager.updateTask(taskSecond);

        assertEquals(task1Modified.getId(), taskSecond.getId(), "Таски разные.");
        assertEquals(task1Modified, taskSecond, "Таски разные.");
    }


    @Test
    void getSnapShot() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        taskFirst.setDescription(taskFirst.getDescription() + " Дополнение 1");
        Task taskSecondModified = manager.updateTask(taskFirst);

        assertEquals(1, manager.getHistory().size(), "Неверное количество задач ");
        assertEquals(taskSecondModified, manager.getHistory().getFirst(), "Слепки разные ");
    }

    @Test
    void getId() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        Task taskFirstModified = manager.updateTask(taskFirst);
        Task taskSecond = manager.getTaskById(taskFirstModified.getId());
        taskSecond.setDescription(taskSecond.getDescription() + " Дополнение 1");
        Task taskSecondModified = manager.updateTask(taskSecond);

        assertEquals(task1Modified.getId(), taskSecond.getId(), "Таски разные.");
    }

    @Test
    void getName() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        Task taskFirstModified = manager.updateTask(taskFirst);

        assertEquals(taskFirstModified.getName(), "Test addNewTask", "Имя не совпадает");
    }

    @Test
    void getDescription() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        Task taskFirstModified = manager.updateTask(taskFirst);

        assertEquals(taskFirstModified.getDescription(), "Test addNewTask description.",
                "Описание не совпадает");
    }

    @Test
    void getStatus() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        Task taskFirstModified = manager.updateTask(taskFirst);

        assertEquals(taskFirstModified.getStatus(), TaskStatus.IN_PROGRESS, "Статусы не совпадают");
    }


    @Test
    void setDescription() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        Task taskFirstModified = manager.updateTask(taskFirst);

        assertEquals(taskFirstModified.getDescription(), "Test addNewTask description.",
                "Описание не совпадает");
    }

    @Test
    void setStatus() {
        Task task1Modified = manager.addTask(task1);
        Task taskFirst = manager.getTaskById(task1Modified.getId());
        taskFirst.setStatus(TaskStatus.IN_PROGRESS);
        Task taskFirstModified = manager.updateTask(taskFirst);

        assertEquals(taskFirstModified.getStatus(), TaskStatus.IN_PROGRESS, "Статусы не совпадают");
    }
}