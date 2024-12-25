package Tasks;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Task task1 = new Task("Task1", "Task1 Description", TaskStatus.NEW);
        manager.addTask(task1);
        Task task2 = new Task("Task2", "Task2 Description", TaskStatus.IN_PROGRESS);
        manager.addTask(task2);
        Epic epic1 = new Epic("Epic1", "Epic1 Description", TaskStatus.DONE);
        manager.addEpic(epic1);
        Epic epic2 = new Epic("Epic2", "Epic2 Description", TaskStatus.NEW);
        manager.addEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask1", "Subtask1 Description",
                TaskStatus.DONE, 3);
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask2", "Subtask2 Description",
                TaskStatus.DONE, 3);
        manager.addSubtask(subtask2);


        //проверяю создание таски, эпика и сабтаски
        ArrayList<Task> task4 = manager.getTaskList();
        System.out.println("Получение task4 " + task4);
        ArrayList<Epic> epic4 = manager.getEpicList();
        System.out.println("Получение epic4 " + epic4);
        ArrayList<Subtask> subtask4 = manager.getSubtaskList();
        System.out.println("Получение subtask4 " + subtask4);

        //проверяю метод get по Id, для task, epic и subtask
        Task task5 = manager.getTaskById(2);
        System.out.println("Получение по id task5 " + task5);
        Epic epic5 = manager.getEpicById(3);
        System.out.println("Получение по id epic5 " + epic5);
        Subtask subtask5 = manager.getSubtaskById(5);
        System.out.println("Получение по id subtask5 " + subtask5);

        //Удаление по id, для task, epic и subtask
        manager.deleteTaskById(1);
        ArrayList<Task> task6 = manager.getTaskList();
        System.out.println("Удаление по id task6" + task6);
        manager.deleteEpicById(3);
        ArrayList<Epic> epic6 = manager.getEpicList();
        System.out.println("Удаление по id epic6" + epic6);
        ArrayList<Subtask> subtask6 = manager.getSubtaskList();
        System.out.println("Удаление по id subtask6" + subtask6);

        //Обновление таски, сабтаски и эпика
        Task task7 = new Task("Task1", "Описание task7", TaskStatus.DONE, 2);
        manager.updateTask(task7);
        System.out.println("Обновление task7" + manager.getTaskList());

        ArrayList<Integer> epicList = new ArrayList<>();
        Epic epic7 = new Epic("Epic500", "Epic500 description", TaskStatus.DONE, 4, epicList);
        manager.updateEpic(epic7);
        System.out.println("Обновление epic7 " + manager.getEpicList());

        Subtask subtask7 = new Subtask("Subtask300", "Subtask300 description",
                TaskStatus.IN_PROGRESS, 6, 4);
        manager.updateSubtask(subtask7);
        System.out.println("Обновление subtask7" + manager.getSubtaskList());


        //Подзадачи со статусом NEW И DONE
        Subtask sub1 = manager.createSubtask(new Subtask("Сабтаска 1 ", "Описание сабтаски1",
                TaskStatus.NEW, 4));
        manager.addSubtask(sub1);
        Subtask sub2 = manager.createSubtask(new Subtask("Сабтаска 2 ", "Описание сабтаски2",
                TaskStatus.DONE, 4));
        manager.addSubtask(sub2);


        System.out.println(manager.getEpicById(4).getStatus());


//Подзадачи со статусом IN_PROGRESS
        Subtask sub3 = manager.createSubtask(new Subtask("Сабтаска3", "Описание сабтаски3",
                TaskStatus.IN_PROGRESS, 4));
        manager.addSubtask(sub3);
        Subtask sub4 = manager.createSubtask(new Subtask("Сабтаска4", "Описание сабтаски 4",
                TaskStatus.IN_PROGRESS, 4));
        manager.addSubtask(sub4);

        System.out.println(manager.getEpicById(4).getStatus());


//Удаление всех тасков, сабстасков и эпиков
        manager.deleteSubtasks();
        System.out.println("Удалил все сабтаски, должно быть пусто в списке" + manager.getSubtaskList());
        manager.deleteEpics();
        System.out.println("Удалил все эпики, должно быть пусто в списке" + manager.getEpicList());
        manager.deleteTasks();
        System.out.println("Удалил все таски, должно быть пусто в списке" + manager.getTaskList() );

    }


}