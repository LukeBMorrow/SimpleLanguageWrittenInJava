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

    }

    public void processLine(String s){
        Scanner lineReader = new Scanner(s);
        String variableName = lineReader.next();//first token
        String firstRightVariable;
        String operator;
        String secondRightVariable;
        int firstRightConstant;
        int secondRightConstant;

        if(!variableName.equals("Q")) {
            if(!lineReader.next().equals("=")){throw new NumberFormatException("Incorrect placement of assignment operator");}
            firstRightVariable = fileReader.next();//first RHS item
            operator = fileReader.next();//the operator
            secondRightVariable = fileReader.next();//the second RHS item

            if(operator==null){
                try{
                    firstRightConstant = Integer.parseInt(firstRightVariable);

                    //the input is a single constant assignment
                    mainTable.insert(variableName, firstRightConstant);
                }catch(NumberFormatException e){
                    //search for the RHS item and duplicate its value in variableName's spot
                    mainTable.insert(variableName, mainTable.search(firstRightVariable));
                }
            }else{//there are 2 items


            }
        }else{//final line in program
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

    public int search(String key){

    }

    public void insert(String name, int value){

    }
}

