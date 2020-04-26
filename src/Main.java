import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;


public class Main {
    private JTextArea outputLinkedList;
    private JTextArea inputLinkedList;
    File iFile = new File("input.txt");
    File oFile = new File("output.txt");

    static LinkedList theList = new LinkedList();

    private JFrame frame;

    public static void main(String[] args) {
        Main guiMain = new Main();

        guiMain.buildGUI();

    }

    public void buildGUI() {
        frame = new JFrame("Example LinkedList for remove even number");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainpanel = new JPanel();
        Font bigFont = new Font("sansserif", Font.BOLD, 20);
        outputLinkedList = new JTextArea(6,20);
        outputLinkedList.setLineWrap(true);
        outputLinkedList.setWrapStyleWord(true);
        outputLinkedList.setFont(bigFont);
        outputLinkedList.setEditable(false);

        JScrollPane oScroller = new JScrollPane(outputLinkedList);
        oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        inputLinkedList = new JTextArea(6,20);
        inputLinkedList.setLineWrap(true);
        inputLinkedList.setWrapStyleWord(true);
        inputLinkedList.setFont(bigFont);

        JScrollPane iScroller = new JScrollPane(inputLinkedList);
        iScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        iScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Box buttonBoxGUI = new Box(BoxLayout.LINE_AXIS);

        JButton addList = new JButton("Add item LinkedList");
        addList.addActionListener(new addLinkedList());
        buttonBoxGUI.add(addList);
        buttonBoxGUI.add(Box.createHorizontalStrut(10));

        JButton deleteList = new JButton("Delete LinkedList");
        deleteList.addActionListener(new deleteLinkedList());
        buttonBoxGUI.add(deleteList);
        buttonBoxGUI.add(Box.createHorizontalStrut(10));



        Box buttonBoxGUITwo = new Box(BoxLayout.LINE_AXIS);
        JButton iFileList = new JButton("Input File List");
        iFileList.addActionListener(new inputFileLinkedList());
        buttonBoxGUITwo.add(iFileList);
        buttonBoxGUITwo.add(Box.createHorizontalStrut(10));

        JButton removeEvenList = new JButton("Remove even Number");
        removeEvenList.addActionListener(new removeEvenNumber());
        buttonBoxGUITwo.add(removeEvenList);
        buttonBoxGUITwo.add(Box.createHorizontalStrut(10));

        JButton helpmenu = new JButton("Help");
        helpmenu.addActionListener(new HelpMenuListener());

        JLabel oLabel = new JLabel("Output");
        JLabel iLabel = new JLabel("Input");

        mainpanel.add(oLabel);
        mainpanel.add(oScroller);
        mainpanel.add(iLabel);
        mainpanel.add(iScroller);
        mainpanel.add(buttonBoxGUI);
        mainpanel.add(buttonBoxGUITwo);
        mainpanel.add(helpmenu);


        frame.getContentPane().add(BorderLayout.CENTER, mainpanel);
        frame.setSize(400,500);
        frame.setVisible(true);
        frame.setResizable(false);

        clearFile(oFile);
        clearFile(iFile);

    }

    public class HelpMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,"Ввод данных происходит через зяпятую " +
                    "Ввод осуществляется с помощью файла input.txt");
        }
    }

    public class addLinkedList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            parsingStr();
            inputLinkedList.setText("");
            refreshFile(oFile,outputLinkedList);
            refreshFile(iFile,inputLinkedList);
            theList.printLinkedList(outputLinkedList);
        }
    }

    public class deleteLinkedList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clearFile(iFile);
            clearFile(oFile);
            clearText();
            while (!theList.isEmpty()) {
                theList.deleteFirst();
            }
            theList.printLinkedList(outputLinkedList);
            refreshFile(oFile, outputLinkedList);
        }
    }

    public class removeEvenNumber implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            theList.removeEvenNumber();
            theList.printLinkedList(outputLinkedList);
            refreshFile(oFile, outputLinkedList);
        }
    }

    public class inputFileLinkedList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            inputLinkedList.setText("");
            addFileTextArea();
        }
    }

    public void clearText() {
        inputLinkedList.setText("");
        outputLinkedList.setText("");
        inputLinkedList.requestFocus();
    }

    private void clearFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("");
            out.close();
        } catch (IOException ex) {
            System.out.println("Error in file cleaning" + ex.getMessage());
        }
    }

    public void addFileTextArea() {
        try {
            FileReader iFileReader = new FileReader(iFile);
            BufferedReader iReader = new BufferedReader(iFileReader);

            String line = null;

            while ((line = iReader.readLine()) != null) {
                inputLinkedList.append(line);
            }

            iReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void parsingStr() {
        String str = inputLinkedList.getText();
        Scanner scanner = new Scanner(str);
        scanner.useDelimiter(",");
        String outStr = "";
        try {
            while (scanner.hasNext()) {
                outStr = outStr + Long.toString(scanner.nextLong()) + ",";
            }
            scanner.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Incorrect line input");
            outStr = "";
        }
        buildLinkedList(outStr);

    }

    public void buildLinkedList(String str) {
        if (str == "") {
            return;
        }
        else {
            Scanner scanner = new Scanner(str);
            scanner.useDelimiter(",");
            try {
                while (scanner.hasNext()) {
                    theList.insertLast(scanner.nextLong());
                }

                scanner.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Ошибка");
            }
        }
        theList.printLinkedList(outputLinkedList);
    }

    public void refreshFile(File file, JTextArea textArea) {
        try {
            FileWriter iFileWriter = new FileWriter(file);
            BufferedWriter iwriter = new BufferedWriter(iFileWriter);

            iwriter.write(textArea.getText());
            iwriter.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Link {
    public long dData;
    public Link next;
    public Link previous;

    public Link(long d)
    {   dData = d;  }

    public void displayLink()
    {
        System.out.print(dData + ",");
    }
}

class LinkedList {
    private Link first;
    private Link last;      

    public LinkedList() {
        first = null;
        last = null;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public void insertFirst(long dd) {
        Link newLink = new Link(dd);
        if (isEmpty())
            last = newLink;
        else
            first.previous = newLink;
        newLink.next = first;
        first = newLink;
    }

    public void insertLast(long dd){
        Link newLink = new Link(dd);
        if (isEmpty()) {
            first = newLink;
        } else
        {
            last.next = newLink;
            newLink.previous = last;
        }
        last = newLink;
    }

    public Link deleteFirst() {
        Link temp = first;
        if (first.next == null)
            last = null;
        else
            first.next.previous = null;
        first = first.next;
        return temp;
    }

    public Link deleteLast() {
        Link temp = last;
        if (first.next == null)
            first = null;
        else
            last.previous.next = null;
        last = last.previous;
        return temp;
    }

    public boolean insertAfter(long key, long dd){
        Link current = first;
        while (current.dData != key)
        {
            current = current.next;
            if (current == null)
                return false;
        }
        Link newLink = new Link(dd);

        if (current == last) {
            newLink.next = null;
            last = newLink;
        } else {
            newLink.next = current.next;
            current.next.previous = newLink;
        }
        newLink.previous = current;
        current.next = newLink;
        return true;
    }

    public Link deleteKey(long key) {
        Link current = first;
        while (current.dData != key)
        {
            current = current.next;
            if (current == null)
                return null;
        }
        if (current == first)
            first = current.next;
        else
            current.previous.next = current.next;

        if (current == last)
            last = current.previous;
        else
            current.next.previous = current.previous;
        return current;
    }

    public void displayForward() {
        System.out.print("List (first -->last): ");
        Link current = first;
        while (current != null)
        {
            current.displayLink();
            current = current.next;
        }
        System.out.println("");
    }

    public void displayBackward() {
        System.out.print("List (last --> first): ");
        Link current = last;
        while(current != null)
        {
            current.displayLink();
            current = current.previous;
        }
        System.out.println("");
    }

    public void removeEvenNumber() {
        Link current = first;
        while (current != null)
        {
            if((current.dData % 2) == 0)
            {
                if (current == first) {
                    first = current.next;
                }
                else {
                    current.previous.next = current.next;
                }
                if(current == last)
                {
                    last = current.previous;
                }
                else {
                    current.next.previous = current.previous;
                }
            }
            current = current.next;
        }
    }

    public void printLinkedList(JTextArea textArea) {
        String outputString = new String();
        Link current = first;
        while (current != null) {
            outputString = outputString + current.dData + ",";
            current = current.next;
        }
        textArea.setText(outputString);
    }

}