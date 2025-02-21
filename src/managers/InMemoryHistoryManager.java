package managers;

import tasks.Node;
import tasks.Task;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> nodeMap = new HashMap<>();

    private Node head;                      //Указатель на первый элемент списка
    private Node tail;                      //Указатель на последний элемент списка

    // добавляем задачу в историю просмотров с помощью HashMap
    @Override
    public void addTask(Task task) {
        if (task != null && !task.getName().isEmpty()) {
            if (nodeMap.get(task.getId()) != null) {
                removeNode(nodeMap.get(task.getId()));
            }
            linkLast(task);
        }
    }

    // получаем историю просмотров
    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();
        Node node = head;
        while (Objects.nonNull(node)) {
            result.add(node.getTask());
            node = node.next;
        }
        return result;
    }

    // метод удаления Ноды по Id таска из списка
    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
    }

    // метод удаления Ноды
    private void removeNode(Node node) {
        if (node == null) {
            return;

        }
        if (node.prev != null && node.next != null) {
            Node previous = node.prev;
            Node next = node.next;
            previous.next = next;
            next.prev = previous;
        } else if (node.prev != null) {
            node.prev.next = null;
            tail = node.prev;
        } else if (node.next != null) {
            node.next.prev = null;
            head = node.next;
        } else {
            head = null;
            tail = null;
        }
        nodeMap.remove(node.task.getId());
    }


    private void linkLast(Task task) {
        if (Objects.isNull(task)) {
            return;
        }
        Node newNode = new Node(null, task, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        nodeMap.put(task.getId(), newNode);

    }


}
