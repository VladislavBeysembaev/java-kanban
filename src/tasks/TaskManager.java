package tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager implements ITaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private int generatorId = 0;

    protected int id() {
        return ++generatorId;
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
        changeEpicStatus(epic.id);
    }

    @Override
    public Integer addSubtask(Subtask subtask) {
        final int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        final int id = ++generatorId;
        subtask.setId(id);
        subTasks.put(id, subtask);
        epic.addIdOfSubtasks(subtask);
        changeEpicStatus(epicId);
        return id;


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
        for (Subtask sub : subTasks.values()) {
            Subtask subtask = subTasks.get(sub.getId());
            if (subtask != null) {
                Epic epic = epics.get(subtask.getEpicId());
                if (epic != null) {
                    epic.getSubtaskId().clear();
                    changeEpicStatus(epic.getId());
                }
            }
        }
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int idNumber) {
        return tasks.get(idNumber);
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
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
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
        if (savedSubtask == null) {
            return;
        }
        final Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subTasks.put(id, subtask);
        changeEpicStatus(epicId);
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        final Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskId()) {
            subTasks.remove(subtaskId);
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subTasks.remove(id);
        if (subtask == null) {
            return;
        }
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.getSubtaskId().remove(Integer.valueOf(id));
        }
        subTasks.remove(id);
        changeEpicStatus(epicId);
    }

    @Override
    public ArrayList<Subtask> subtasksList(int idNumber) {
        Epic epic = epics.get(idNumber);
        if (epic == null) {
            return null;
        }
        ArrayList<Subtask> listSubtasks = new ArrayList<>();
        for (int subtaskIdNumber : epic.getSubtaskId()) {
            listSubtasks.add(subTasks.get(subtaskIdNumber));
        }
        return listSubtasks;
    }

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
            if (status == subtask.getStatus() && status != TaskStatus.IN_PROGRESS) {
                continue;
            }
            epic.setStatus(TaskStatus.IN_PROGRESS);
            return;

        }
        epic.setStatus(status);

    }


}





