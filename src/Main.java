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

        //clearFile(iFile);

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
            //refreshFile(iFile,inputLinkedList);
            theList.printLinkedList(outputLinkedList);
        }
    }

    public class deleteLinkedList implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //clearFile(iFile);
            clearText();
            while (!theList.isEmpty()) {
                theList.deleteFirst();
            }
            theList.printLinkedList(outputLinkedList);
        }
    }

    public class removeEvenNumber implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            theList.removeEvenNumber();
            theList.printLinkedList(outputLinkedList);
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