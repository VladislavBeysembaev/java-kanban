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

    private Node head;
    private Node tail;


    @Override
    public void addTask(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            removeNode(nodeMap.get(task.getId()));
        }
        linkLast(task);
        nodeMap.put(task.getId(), tail);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();
        Node node = head;
        while (Objects.nonNull(node)) {
            result.add(node.getTask());
            node = node.getNext();
        }
        return result;
    }


    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
    }

    // метод удаления Ноды
    private void removeNode(Node node) {
        if (node == null) {
            return;

        }
        if (node.getPrev() != null && node.getNext() != null) {
            Node previous = node.getPrev();
            Node next = node.getNext();
            previous.setNext(next);
            next.setPrev(previous);
        } else if (node.getPrev() != null) {
            node.getPrev().setNext(null);
            tail = node.getPrev();
        } else if (node.getNext() != null) {
            node.getNext().setPrev(null);
            head = node.getNext();
        } else {
            head = null;
            tail = null;
        }
        nodeMap.remove(node.getTask().getId());
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
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        nodeMap.put(task.getId(), newNode);

    }
}

