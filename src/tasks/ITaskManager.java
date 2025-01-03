package tasks;

import java.util.ArrayList;

public interface ITaskManager {


    void addTask(Task task);

    void addEpic(Epic epic);


    Integer addSubtask(Subtask subtask);

    ArrayList<Task> getTaskList();

    ArrayList<Epic> getEpicList();

    ArrayList<Subtask> getSubtaskList();

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    Task getTaskById(int idNumber);

    Epic getEpicById(int idNumber);

    Subtask getSubtaskById(int idNumber);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTaskById(int idNumber);

    void deleteEpicById(int idNumber);

    void deleteSubtaskById(int idNumber);

    ArrayList<Subtask> subtasksList(int idNumber);


}
