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

class Controller{
    Scanner fileReader;
    Table mainTable;

    public Controller(){
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Please enter the name of the Lenert program text file:\n ");
        String fileName = inputReader.nextLine();
        fileReader = new Scanner(fileName);
        mainTable = new Table();
    }

    public void processFile() throws NumberFormatException
    {

        int numPrograms = fileReader.nextInt();

        String variableName = fileReader.next();
        if(fileReader.next()!="="){//next item should be =
            throw new NumberFormatException("missing or misplaced assignment operator '='");
        }
        try{
            String firstRightVariable = fileReader.next();
            int firstRightConstant = Integer.parseInt(firstRightVariable);//try and do this

            String operator = fileReader.next();// should be +, -, *, or /

            String secondRightVariable = fileReader.next();
            int secondRightConstant = Integer.parseInt(secondRightVariable);//try and do this again
        }catch(NumberFormatException e)
    }

    public void processLine(String s){
        Scanner lineReader = new Scanner(s);
        String variableName = lineReader.next();
        String operator;
        String secondRightVariable;
        String firstRightVariable;
        int firstRightConstant;
        int secondRightConstant;
        if(!variableName.equals("Q")) {
            if (!fileReader.next().equals("=")) {//next item should be =
                throw new NumberFormatException("missing or misplaced assignment operator '='");
            }
            try {
                firstRightVariable = fileReader.next();
                firstRightConstant = Integer.parseInt(firstRightVariable);

                if (fileReader.hasNext()) {
                    //item 1 is a CONSTANT
                    operator = fileReader.next();// should be +, -, *, or /


                    try {//try to parse item 2
                        secondRightVariable = fileReader.next();
                        secondRightConstant = Integer.parseInt(secondRightVariable);
                        //second item is a CONSTANT

                    } catch (NumberFormatException e) {
                        //use BST
                        //item 2 is a VARIABLE
                    }
                }
            } catch (NumberFormatException e) {
                if (fileReader.hasNext()) {
                    //use BST
                    //item 1 is a VARIABLE
                    operator = fileReader.next();// should be +, -, *, or /
                    try {
                        secondRightVariable = fileReader.next();
                        secondRightConstant = Integer.parseInt(secondRightVariable);

                        //item 2 is a CONSTANT
                    } catch (NumberFormatException e) {
                        //use BST
                        //item 2 is a VARIABLE
                    }
                }
            }
        }else{



            //END OF PROGRAM PROCESSING
            mainTable = new Table();
        }
    }
}

class Node{
    private Node left;
    private Node right;
    private int value;
    private String name;

    public Node(String name,int value){
        this.value = value;
        this.name = name;
        left = null;
        right = null;
    }

    /*a block of getters and setters*/
    public int getValue() { return value; }
    public String getName() { return name; }
    public Node getLeft() { return left; }
    public Node getRight() { return right; }
    public void setLeft(Node left) { this.left = left; }
    public void setRight(Node right) { this.right = right; }
}

class Table{

    Node root;

    public Table(){
        root = null;
    }

    public Node search(String key){

    }

    public void insert(String name, int value){

    }
}

