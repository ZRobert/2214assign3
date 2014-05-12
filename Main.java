import red_black_trees.*;


public class Main {

	  //you can use the following initialization to debug your implementation
    public static void main(String[] args) {		 
		   
		  		RedBlackTree<String> rbTree = new RedBlackTree<String>();  
				
				
				rbTree.add("d"); //d
				rbTree.add("f"); //e
				rbTree.add("e"); //f for left rotation check case 2
				rbTree.add("h");
				rbTree.add("a");
				rbTree.add("s");
				rbTree.add("z");
				rbTree.add("t");
				rbTree.add("b");
				rbTree.add("w");
				rbTree.add("h");
				rbTree.add("u");
				rbTree.add("d");
				rbTree.add("c");
				rbTree.add("e");
				rbTree.add("z");
				rbTree.add("y");
				rbTree.add("x");
				rbTree.add("w");
				rbTree.add("v");
				rbTree.add("u");
				rbTree.add("t");
				rbTree.add("r"); 
				rbTree.add("c");
				rbTree.add("e");
				rbTree.add("z");


				
				rbTree.positionTraversal();
				System.out.println();
				rbTree.colorTraversal();
    			System.out.println();			
				rbTree.levelOrderTraversal(); 
				System.out.println();
				rbTree.depthTraversal();
				System.out.println();
				rbTree.reverseLevelOrderTraversal();  
	 }
   
}