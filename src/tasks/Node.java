package tasks;

public class Node {

    private Task task;
    private Node next;
    private Node prev;

    public Node(Node prev, Task task, Node next) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

    // собирать все задачи в обычный ArrayList
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

}