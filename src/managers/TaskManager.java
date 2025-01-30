package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {


    Task addTask(Task task);

    Epic addEpic(Epic epic);


    Subtask addSubtask(Subtask subtask);

    ArrayList<Task> getTaskList();

    ArrayList<Epic> getEpicList();

    ArrayList<Subtask> getSubtaskList();

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    Task getTaskById(int idNumber);

    Epic getEpicById(int idNumber);

    Subtask getSubtaskById(int idNumber);

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    void deleteTaskById(int idNumber);

    void deleteEpicById(int idNumber);

    void deleteSubtaskById(int idNumber);

    ArrayList<Subtask> getSubtasksList(int idNumber);

    List<Task> getHistory();
}
