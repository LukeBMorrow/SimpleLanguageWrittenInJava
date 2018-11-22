/*
    COMP 2140 SECTION A01
    INSTRUCTOR   Cameron(A01)
    ASSIGNMENT   4
    @author      Luke Morrow, 7787696
    @version     2018-11-16

    REMARKS:
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class A4MorrowLuke {

    public static void main(String[] args) {
        Controller head = new Controller();
        head.processFile();
        System.out.println("Program ended normally.");
    }


}

class Controller {
    private Scanner fileReader;
    private Table mainTable;
    private String errors = "";

    public Controller() {
        Scanner inputReader = new Scanner(System.in);
        System.out.println("COMP 2140 Assignment 4: Executing Lenert Programs\n" +
                "-------------------------------------------------\n");
        System.out.println("Please enter the input file name (.txt files only):\n ");
        File fileName = new File(inputReader.next());
        System.out.println("\n\n");
        try {
            fileReader = new Scanner(fileName);

        } catch (FileNotFoundException e) {
            System.out.println("Failed to open file: " + e);
        }
        mainTable = new Table();
    }

    public void processFile() {
        if(fileReader!=null) {
            int numPrograms = fileReader.nextInt();
            String line = fileReader.nextLine();
            for (int i = 0; i < numPrograms; i++) {//process file loop
                System.out.println("\nLenert Program " + (i + 1) +
                        "\n-----------------");
                do {
                    processLine(line);
                    line = fileReader.nextLine();
                } while (!line.equals("Q"));//process program loop
                System.out.println("Error Messages: \n" + errors);
                System.out.println("Final values of the variables: \n");
                mainTable.printTable();
                mainTable = new Table();
                errors = "";
            }
            System.out.println("\n---------------------");// the last line before end of main program
        }
    }

    private void processLine(String s) {
        String[] variables = trimAndSplit(s);

        if(variables.length>2) {//there is, in fact, something on the line
            ValueNamePair temp;
            boolean errorFound = false;
            String variableName = variables[0];//first token
            String firstString = variables[2];
            int result = 0;
            int firstConstant = Integer.MAX_VALUE;
            int secondConstant = 0;

            try {
                firstConstant = Integer.parseInt(firstString);//turn string into number
            } catch (NumberFormatException e1) {    //firstRightVariable is a key
                temp = mainTable.search(firstString);//turn key into number
                if (temp == null) {
                    errors += "Invalid input line: variable \""+firstString+"\" is used before its declaration in \""+s+"\"\n";
                    errorFound = true;
                } else {
                    firstConstant = temp.value;
                }
            }
            if (variables.length > 4) {//there are two right side strings and an operator
                String operator = variables[3];
                String secondString = variables[4];//second token
                try {
                    secondConstant = Integer.parseInt(secondString);//turn string into number
                } catch (NumberFormatException e1) {    //secondRightVariable is a key
                    temp = mainTable.search(secondString);//turn key into number
                    if (temp == null) {//turn key into number
                        errors += "Invalid input line: variable \""+secondString+"\" is used before its declaration in \""+s+"\"\n";
                        errorFound = true;
                    } else {
                        secondConstant = temp.value;
                    }
                }
                if (!errorFound) {
                    result = operatorProcessing(firstConstant, secondConstant, operator);
                }//do nothing if an error has been found
            } else {//if there is one item
                result = firstConstant;
            }
            if (!errorFound && result != Integer.MAX_VALUE) {//if no errors occured
                ValueNamePair searchResult = mainTable.search(variableName);
                if(searchResult==null) {
                    mainTable.insert(variableName, result);
                }else{
                    searchResult.value=result;
                }
            }
        }
    }

    private int operatorProcessing(int a, int b, String operator) {
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
                result = a / b;
                break;
        }
        if (result == Integer.MAX_VALUE) {
            errors += "invalid mathematical operator '" + operator + "'\n";
        }
        return result;
    }

    private String[] trimAndSplit(String s){
        String[] result;
        String temp = s.trim();
        result=temp.split("\\s+");//an alternative for the space delimiter
        for(int i = 0; i<result.length;i++){
            result[i] = result[i].trim();
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

    public String toString() {
        return (data.name + ": " + data.value + "\n");
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
        while (curr != null && curr.getData().name.compareTo(key) != 0) {
            if (curr.getData().name.compareTo(key) < 0) {
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
            while (curr != null && curr.getData().name.compareTo(name) != 0) {
                prev = curr;
                if (curr.getData().name.compareTo(name) < 0) {
                    curr = curr.getLeft();
                } else {
                    curr = curr.getRight();
                }
            }
            if (curr == null) {
                if (prev.getData().name.compareTo(name) < 0) {
                    prev.setLeft(new Node(new ValueNamePair(value, name)));
                } else {
                    prev.setRight(new Node(new ValueNamePair(value, name)));
                }
            }
        }
    }

    public void printTable() {
        printTable(root);
    }

    private void printTable(Node curr) {
        if (curr != null) {
            printTable(curr.getLeft());
            System.out.print(curr);
            printTable(curr.getRight());
        }
    }
}

class ValueNamePair {
    public int value;
    public String name;

    public ValueNamePair(int value, String name) {
        this.value = value;
        this.name = name;
    }
}