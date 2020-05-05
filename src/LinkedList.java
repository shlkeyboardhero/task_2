import javax.swing.*;

public class LinkedList {
    private Link head;
    private Link tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }


    public void insertFirst(long dd) {
        Link newObj = new Link(dd);
        if (isEmpty())
            tail = newObj;
        else
            newObj.next = head;
        head = newObj;
    }

    public void insertLast(long dd) {
        Link newObj = new Link(dd);
        if (isEmpty())
            head = newObj;
        else
            tail.next = newObj;
        tail = newObj;
    }


    public Link deleteFirst() {
        Link returnHead = head;
        if (!isEmpty())
            head = head.next;
        return returnHead;
    }


    public Link deleteLast() {
        Link returnTail = tail;
        if (!isEmpty()) {
            Link rememberLink = head;
            Link rememberLinkPrevious = null;
            while (rememberLink != tail) {
                rememberLinkPrevious = rememberLink;
                rememberLink = rememberLink.next;
            }
            tail = rememberLinkPrevious;
            if (rememberLinkPrevious != null)
                rememberLinkPrevious.next = null;
            else {
                head = null;
            }
        }
        return returnTail;
    }

    public Link insertAfter(long key, long dd) {
        Link present = head;
        while (present.dData != key) {
            present = present.next;
            if (present == null)
                return null;
        }
        Link pasteLink = new Link(dd);
        pasteLink.next = present.next;
        present.next = pasteLink;
        if (present == tail)
            tail = pasteLink;
        return pasteLink;
    }

    public Link deleteKey(long key) {
        Link present = head;
        Link previousPresent = null;
        while (present.dData != key) {
            previousPresent = present;
            present = present.next;
            if (present == null)
                return null;
        }
        if (present == head)
            head = present.next;
        else
            previousPresent.next = present.next;
        return present;
    }


    public void displayForward() {
        System.out.print("List (first -->last): ");
        Link current = head;
        while (current != null) {
            current.displayLink();
            current = current.next;
        }
        System.out.println("");
    }

    public void removeEvenNumber() {
        Link present = head;
        Link previousPresent = null;
        while (present != null) {
            if ((present.dData % 2) == 0) {
                if (present == head)
                    head = head.next;
                else
                    previousPresent.next = present.next;
                if (present == tail)
                    tail = previousPresent;
            }
            else
                previousPresent = present;
            present = present.next;
        }
    }

    public void printLinkedList(JTextArea textArea) {
        String outputString = new String();
        Link current = head;
        while (current != null) {
            outputString = outputString + current.dData + ",";
            current = current.next;
        }
        textArea.setText(outputString);
    }
}
