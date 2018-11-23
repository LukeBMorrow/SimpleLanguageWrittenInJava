/*
    COMP 2140 SECTION A01
    INSTRUCTOR   Cameron(A01)
    ASSIGNMENT   4
    @author      Luke Morrow, 7787696
    @version     2018-11-16

    REMARKS: This program reads a file that contains variable
    declarations and operations. Then takes the lines read
    and translates them into integers and variables(these are stored
    in a binary search tree). Finally it executes the commands given in the
    file and prints the final results of the variables
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/* runs the program upon execution */
public class A4MorrowLuke {
    public static void main(String[] args) {
        Controller head = new Controller();
        head.processFile();
        System.out.println("Program ended normally.");
    }
}

/* gets the file and processes its data */
class Controller {
    private Scanner fileReader;
    private Table mainTable;
    private String errors = "";//preps a string to hold a list of errors

    /* opens the file and declares the main table */
    public Controller() {
        Scanner inputReader = new Scanner(System.in);
        System.out.println("COMP 2140 Assignment 4: Executing Lenert Programs\n" +
                "-------------------------------------------------\n");
        System.out.println("Please enter the input file name (.txt files only):\n ");
        File fileName = new File(inputReader.next());
        try {
            fileReader = new Scanner(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to open file: " + e);
        }
        mainTable = new Table();
    }

    /* does (almost) all of the printing, uses a for loop to read each program.
       * Nested in the for loop is a while loop to read each line. Ends by printing errors
       * and the final values of the table*/
    public void processFile() {
        if (fileReader != null) {
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
                errors = "";//wipes error records
            }
            System.out.println("\n---------------------");// the last line before end of main program
        }
    }

    /* takes a line and splits it into individual string items then chooses a processing path
     * based on the number of strings made by the split. Ends by adding/changing the item in the table */
    private void processLine(String s) {
        final int MIN_NUM_STRINGS=3;
        final int VARIABLE_NAME_POSITION=0;
        final int FIRST_STRING_POSITION=2;
        final int MIN_STRINGS_FOR_OPERATION=5;
        final int OPERATOR_POSITION=3;
        final int SECOND_STRING_POSITION=4;
        String[] variables = trimAndSplit(s);

        if (variables.length >= MIN_NUM_STRINGS) {//there is, in fact, something on the line
            ValueNamePair temp;
            boolean errorFound = false;
            String variableName = variables[VARIABLE_NAME_POSITION];//first token
            String firstString = variables[FIRST_STRING_POSITION];
            int result = 0;
            int firstConstant = Integer.MAX_VALUE;
            int secondConstant = 0;

            try {
                firstConstant = Integer.parseInt(firstString);//turn string into number
            } catch (NumberFormatException e1) {    //firstRightVariable is a key
                temp = mainTable.search(firstString);//turn key into number
                if (temp == null) {
                    errors += "Invalid input line: variable \"" + firstString + "\" is used before its declaration in \"" + s + "\"\n";
                    errorFound = true;
                } else {
                    firstConstant = temp.value;
                }
            }
            if (variables.length >= MIN_STRINGS_FOR_OPERATION) {//there are two right side strings and an operator
                String operator = variables[OPERATOR_POSITION];
                String secondString = variables[SECOND_STRING_POSITION];//second token
                try {
                    secondConstant = Integer.parseInt(secondString);//turn string into number
                } catch (NumberFormatException e1) {    //secondRightVariable is a key
                    temp = mainTable.search(secondString);//turn key into number
                    if (temp == null) {//turn key into number
                        errors += "Invalid input line: variable \"" + secondString + "\" is used before its declaration in \"" + s + "\"\n";
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
            if (!errorFound && result != Integer.MAX_VALUE) {//if no errors occurred
                ValueNamePair searchResult = mainTable.search(variableName);
                if (searchResult == null) {
                    mainTable.insert(variableName, result);
                } else {
                    searchResult.value = result;
                }
            }
        }
    }

    /* takes the operator and values and preforms the appropriate operation on them. */
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

    /* takes a string and splits it and trims each item in the String array */
    private String[] trimAndSplit(String s) {
        String[] result;
        String temp = s.trim();
        result = temp.split("\\s+");//a space delimiter intended for many\large white spaces
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].trim();
        }
        return result;
    }
}

/* the object that the tree is composed of */
class Node {
    private Node left;
    private Node right;
    private ValueNamePair data;

    public Node(ValueNamePair data) {
        this.data = data;
        left = null;
        right = null;
    }

    @Override
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

/* the BST class */
class Table {

    Node root;

    public Table() {
        root = null;
    }

    /* searches for an item and returns it if found. If not found, returns null */
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

    /* adds an item to the tree. If the item already exists, don't add it,*/
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

    /* the driver method of the print table */
    public void printTable() {
        printTable(root);
    }

    /* prints every item in the table recursively using inorder traversal */
    private void printTable(Node curr) {
        if (curr != null) {
            printTable(curr.getLeft());
            System.out.print(curr);
            printTable(curr.getRight());
        }
    }
}

/* a class made to tie two variables together */
class ValueNamePair {
    public int value;
    public String name;

    public ValueNamePair(int value, String name) {
        this.value = value;
        this.name = name;
    }
}