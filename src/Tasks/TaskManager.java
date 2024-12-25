package Tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager implements ITaskManager {


    private final HashMap<Integer, Task> tasks = new HashMap<>();

    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();


    private final HashMap<Integer, Epic> epics = new HashMap<>();


    private int generatorId = 0;

    protected int id(Task task) {
        task.setId(++generatorId);
        return generatorId;
    }

    @Override
    public void addTask(Task task) {
        task.setId(++generatorId);
        tasks.put(task.getId(), task);

    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(++generatorId);
        epics.put(epic.getId(), epic);


    }

    @Override
    public void addSubtask(Subtask subtask) {
        int epicIdOfSubtask = subtask.getEpicId();
        Epic epic = epics.get(epicIdOfSubtask);
        if (epic != null) {
            subTasks.put(id(subtask), subtask);
            epic.addIdOfSubtasks(subtask);
            changeEpicStatus(epicIdOfSubtask);


        }
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteTasks() {
        tasks.clear();

    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int idNumber) {
        Task task = tasks.get(idNumber);
        if (task == null) {
            return null;
        }
        return task;
    }

    @Override
    public Epic getEpicById(int idNumber) {
        Epic epic = epics.get(idNumber);
        if (epic == null) {
            return null;
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int idNumber) {
        Subtask subtask = subTasks.get(idNumber);
        if (subtask == null) {
            return null;
        }
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        int idUpdatedTask = task.getId();
        if (tasks.containsKey(idUpdatedTask)) {
            tasks.put(idUpdatedTask, task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.getId());
        currentEpic.setName(epic.getName());
        currentEpic.setDescription(epic.getDescription());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
      final int id = subtask.getId();
      final int epicId = subtask.getEpicId();
      final Subtask savedSubtask = subTasks.get(id);
      if (savedSubtask == null){
          return;
      }
      if(subtask.getEpicId() != savedSubtask.getEpicId()){
          return;
      }
      final Epic epic = epics.get(epicId);
      if (epic==null){
          return;
      }
      subTasks.put(id, subtask);
      changeEpicStatus(epicId);
    }

    @Override
    public void deleteTaskById(int idNumber) {
        Task task = tasks.get(idNumber);
        if (task == null) {
            return;
        }
        tasks.remove(idNumber);
    }

    @Override
    public void deleteEpicById(int idNumber) {
        Epic epic = epics.get(idNumber);
        if (epic == null) {
            return;
        }
        for (int sub : epic.getSubtaskId()) {
            Subtask subtask = subTasks.get(sub);
            subTasks.remove(sub);
        }
        epics.remove(idNumber);
    }

    @Override
    public void deleteSubtaskById(int idNumber) {
        Subtask sub = subTasks.get(idNumber);
        if (sub == null) {
            return;
        }
        int epicId = sub.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.getSubtaskId().remove(Integer.valueOf(idNumber));
        }
        subTasks.remove(idNumber);
    }

    @Override
    public ArrayList<Subtask> subtasksList(int idNumber) {
        Epic epic = epics.get(idNumber);
        if (epic == null) {
            return null;
        }
        ArrayList<Subtask> listSubtasks = new ArrayList<>();
        for (int subtaskIdNumber : epic.getSubtaskId()) {
            Subtask subtask = subTasks.get(subtaskIdNumber);
            listSubtasks.add(subtask);
        }
        return listSubtasks;
    }

    @Override
    public void changeEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subs = epic.getSubtaskId();
        if (subs.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        TaskStatus status = null;
        for (int id : subs) {
            Subtask subtask = subTasks.get(id);
            if (status == null) {
                status = subtask.getStatus();
                continue;
            }
            if (status == subtask.getStatus() && status!= TaskStatus.IN_PROGRESS){
                continue;
            }
            epic.setStatus(TaskStatus.IN_PROGRESS);
            return;

        }
        epic.setStatus(status);

    }

    @Override
    public Task createTask(Task task) {
        return new Task(task.getName(), task.getDescription(), task.getStatus());
    }

    @Override
    public Epic createEpic(Epic epic) {
        return new Epic(epic.getName(), epic.getDescription(), epic.getStatus());
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        return new Subtask(subtask.getName(), subtask.getDescription(), subtask.getStatus(), subtask.getEpicId());
    }

}





