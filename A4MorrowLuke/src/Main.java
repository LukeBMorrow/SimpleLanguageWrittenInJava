/*
    COMP 2140 SECTION A01
    INSTRUCTOR   Cameron(A01)
    ASSIGNMENT   4
    @author      Luke Morrow, 7787696
    @version     2018-11-16

    REMARKS:
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
    }


}

class Controller{

    public Controller(){

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

