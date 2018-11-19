/*
    COMP 2140 SECTION A01
    INSTRUCTOR   Cameron(A01)
    ASSIGNMENT   4
    @author      Luke Morrow, 7787696
    @version     2018-11-16

    REMARKS:
 */

import java.util.Scanner;

public class A4MorrowLuke {

    public static void main(String[] args) {
        // write your code here
    }


}

class Controller {
    Scanner fileReader;
    Table mainTable;

    public Controller() {
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Please enter the name of the Lenert program text file:\n ");
        String fileName = inputReader.nextLine();
        fileReader = new Scanner(fileName);
        mainTable = new Table();
    }

    public void processFile() throws NumberFormatException {

        int numPrograms = fileReader.nextInt();

    }

    public void processLine(String s) {
        Scanner lineReader = new Scanner(s);
        String variableName = lineReader.next();//first token
        String firstString;
        String operator;
        String secondString;
        int result;
        int firstConstant = Integer.MIN_VALUE;
        int secondConstant = Integer.MIN_VALUE;


        if (!variableName.equals("Q")) {
            if (!lineReader.next().equals("=")) {
                throw new NumberFormatException("Incorrect placement of assignment operator");
            }
            firstString = fileReader.next();//first RHS item
            operator = fileReader.next();//the operator
            secondString = fileReader.next();//the second RHS item

            try {
                firstConstant = Integer.parseInt(firstString);//turn string into number
            } catch (NumberFormatException e1) {    //firstRightVariable is a key
                firstConstant = mainTable.search(firstString).value;//turn key into number
            }
            if (operator != null) {//2 item line
                try {
                    secondConstant = Integer.parseInt(secondString);//turn string into number
                } catch (NumberFormatException e1) {    //secondRightVariable is a key
                    secondConstant = mainTable.search(secondString).value;//turn key into number
                }
                result = operatorProcessing(firstConstant, secondConstant, operator);
            } else {
                result = firstConstant;
            }
            mainTable.insert(variableName, result);
        } else {//final line in program
            //END OF PROGRAM PROCESSING

            mainTable.printTable();
            mainTable = new Table();
        }
    }

    public int operatorProcessing(int a, int b, String operator) {
        int result = Integer.MAX_VALUE;//will return MIN_VALUE if operation failed
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;//SHOULD I BE A DOUBLE?
                break;
        }
        return result;
    }
}

class Node {
    private Node left;
    private Node right;
    private ValueNamePair data;

    public Node(ValueNamePair data) {
        this.data = data;
        left = null;
        right = null;
    }

    /*a block of getters and setters*/
    public ValueNamePair getData() {
        return data;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}

class Table {

    Node root;

    public Table() {
        root = null;
    }

    public ValueNamePair search(String key) {
        Node curr = root;
        while (curr != null && curr.getData().name.compareTo(key)!=0) {
            if (curr.getData().name.compareTo(key)<0) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        if (curr == null) {
            //failed to find the item, add to error
            return null;
        } else {
            return curr.getData();
        }
    }

    public void insert(String name, int value) {
        Node curr = root;
        Node prev = null;
        if (root == null) {
            root = new Node(new ValueNamePair(value, name));
        } else {
            while (curr != null && curr.getData().name.compareTo(name)!=0) {
                if (curr.getData().name.compareTo(name)<0) {
                    curr = curr.getLeft();
                } else {
                    curr = curr.getRight();
                }
            }
            if (curr == null) {
                if (prev.getData().name.compareTo(name)>0) {
                    prev.setLeft(new Node(new ValueNamePair(value, name)));
                } else {
                    prev.setRight(new Node(new ValueNamePair(value, name)));
                }
            }
        }
    }

    public void printTable() {

    }
}

class ValueNamePair{
    public int value;
    public String name;
    public ValueNamePair(int value, String name){
        this.value=value;
        this.name=name;
    }
}