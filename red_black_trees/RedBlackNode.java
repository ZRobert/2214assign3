package red_black_trees;

public class RedBlackNode<T> {
    
    final public static String BLACK = "black";
    final public static String RED = "red";
	 final public static String LEFT = "left";
	 final public static String RIGHT = "right";
	 
    private T content;
    private RedBlackNode<T> parent;
    private RedBlackNode<T> left;
    private RedBlackNode<T> right;
    private String color; 
	 private String position;
    private int depth;

    public RedBlackNode() {	
	 
	 		left = null;
			right = null;
			color = BLACK;
			position = null;
			depth = 0;		         
    }

    public RedBlackNode(T element) {
        content = element;
		  left = null;
		  right = null;
		  color = BLACK;
		  depth = 0;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
	 
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public RedBlackNode<T> getLeft() {
        return left;
    }

    public void setLeft(RedBlackNode<T> left) {
        this.left = left;
    }

    public RedBlackNode<T> getParent() {
        return parent;
    }

    public void setParent(RedBlackNode<T> parent) {
        this.parent = parent;
    }

    public RedBlackNode<T> getRight() {
        return right;
    }

    public void setRight(RedBlackNode<T> right) {
        this.right = right;
    }   
    
}
