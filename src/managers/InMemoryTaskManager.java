package managers;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistoryManager();

    private int generatorId = 0;

    protected void setIdCounter(int generatorId) {
        this.generatorId = generatorId;
    }

    protected int getIdCounter() {
        return generatorId;
    }

    @Override
    public Task addTask(Task task) {

        task.setId(++generatorId);
        tasks.put(task.getId(), task);
        return task;
    }


    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(++generatorId);
        epics.put(epic.getId(), epic);
        changeEpicStatus(epic.getId());
        return epic;
    }


    @Override
    public Subtask addSubtask(Subtask subtask) {
        final int id = ++generatorId;
        subtask.setId(id);
        subTasks.put(subtask.getId(), subtask);
        return subtask;
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
        for (Epic epic : epics.values()) { // удаление эпиков из истории
            historyManager.remove(epic.getId());
        }
        for (Subtask subtask : subTasks.values()) { // удаление подзадач из истории
            historyManager.remove(subtask.getId());
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Subtask subtask : subTasks.values()) {  // цикл для удаления подзадач из истории
            int i = subtask.getId();
            if (historyManager.getHistory().contains(i)) {
                historyManager.remove(i);
            }
        }
        for (Epic epic : epics.values()) {
            deleteSubtasks(); // Удаление всех подзадач из эпиков
            changeEpicStatus(epic.getId()); // Обновление прогресса эпиков
        }
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int idNumber) {
        Task task = tasks.get(idNumber);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public Epic getEpicById(int idNumber) {
        Epic epic = epics.get(idNumber);
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int idNumber) {
        Subtask subtask = subTasks.get(idNumber);
        historyManager.addTask(subtask);
        return subtask;
    }

    @Override
    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        Epic currentEpic = epics.get(epic.getId());
        currentEpic.setName(epic.getName());
        currentEpic.setDescription(epic.getDescription());
        return epic;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        final int id = subtask.getId();
        final int epicId = subtask.getEpicId();
        final Subtask savedSubtask = subTasks.get(id);
        if (savedSubtask == null) {
            return null;
        }
        final Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        subTasks.put(id, subtask);
        changeEpicStatus(epicId);
        return subtask;
    }

    @Override
    public void deleteTaskById(int id) {
        historyManager.remove(id);
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
    public ArrayList<Subtask> getSubtasksList(int idNumber) {
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

    private void changeEpicStatus(int epicId) {
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

    protected Map<Integer, Task> getTasks() {
        return tasks;
    }

    protected Map<Integer, Epic> getEpics() {
        return epics;
    }

    protected Map<Integer, Subtask> getSubtasks() {
        return subTasks;
    }


    @Override

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}





