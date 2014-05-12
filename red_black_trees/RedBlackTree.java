/*********************************************************
* RedBlackTree.java
* Author: Robert Payne
* Date: 8/5/2012
* Class: ITCS 2214-021

* Purpose: RedBlackTree is an implementation algorithm
* for a binary search tree that solves the problem of
* an unbalanced tree by assigning each node with a 
* color and using a set of rules to arrange the tree in
* a way to keep the search operating at Big O(log n)
* The Four Properties of a Red/Black tree
* 1. Every node is either Red or Black
* 2. The root and leaves(null containers) are black
* 3. Every red node's parent is black
* 4. Every simple path from a node to a descendant leaf 
*    have the same black height
/*********************************************************/
package red_black_trees;


public class RedBlackTree<T extends Comparable<T>> implements BinarySearchTree<T> {

/*
* 	Fields
---------------------------------------------------------*/
    
	private RedBlackNode<T> root;
	private int count;
    
/*
*	Constructor
==========================================================
	Initializes the Red Black Tree by setting the tree to
	a count of 0.
---------------------------------------------------------*/

	public RedBlackTree() {	
		
		count = 0;
    }
/*
*	add(element)	 
===========================================================
	Prepares an element to be added to the RB Tree, if
	there are no elements in the tree, it sets it as the
	root of the tree. If there are elements inside the tree,
	it sends that new node containing the element to
	addRecursive. Sets the root to black after rebalance
	and addRecursive are finished as the last step to
	insertion.
---------------------------------------------------------*/ 	 
	@Override
	public void add(T element) {
		
		int depth = 0;
		RedBlackNode<T> temp = new RedBlackNode<T>(element);

		temp.setColor("red");
		
		if(count == 0) {
			RedBlackNode<T> left = new RedBlackNode<T>();
			RedBlackNode<T> right = new RedBlackNode<T>();
			left.setPosition("left");
			right.setPosition("right");
			root = temp;
			root.setLeft(left);
			root.setRight(right);
			root.getRight().setParent(root);
			root.getLeft().setParent(root);
			count++;			 	
			return;		
		}
		
		if(root.getContent().compareTo(element) > 0)
			addRecursive(root.getLeft(), temp, depth);
			
		else
			addRecursive(root.getRight(), temp, depth);	

			root.setColor("black");
	}
/*
*	addRecursive(node,temp,depth)
===========================================================
	addRecursive increments the depth of the temp node and
	looks to see if the current node is null. If it is, then
	it places the element in that position and creates two
	new leaves that are colored black. The node that is 
	added is colored red. If the container is not empty, a
	comparison is made to check to see if the node needs
	to be sent left or right down the tree to find it's
	proper place. The method then calls itself with the
	correct path and repeats the process until the node
	has found it's correct position. Then it calls the
	rebalance method to fix the properties of the RB Tree.
---------------------------------------------------------*/
	private void addRecursive(RedBlackNode<T> node,RedBlackNode<T> temp,int depth ) {
	
		depth++;
		temp.setParent(node);
		node.setDepth(depth);
		if(node.getContent() == null) {
	
			RedBlackNode<T> left = new RedBlackNode<T>();
			RedBlackNode<T> right = new RedBlackNode<T>();

			left.setPosition("left");
			right.setPosition("right");
			count++;
			node.setContent(temp.getContent());
			node.setLeft(left);
			node.setColor("red");
			node.setRight(right);
			left.setParent(node);
			right.setParent(node);
			node.setDepth(depth);
		
		if(node.getParent() != null)
			if(node.getParent().getParent() != null)
				if(node.getParent().getColor() == "red")
					rebalance(node);
									
		return;
	}
	
	else if(node.getContent().compareTo(temp.getContent()) > 0) {
		
		temp.setPosition("left");
		addRecursive(node.getLeft(), temp, depth);
	}
	
	else {
		
		temp.setPosition("right");
		addRecursive(node.getRight(), temp, depth);
	}
}
/*
*	rebalance(node)
===========================================================
	rebalance takes the newly added node then it checks for
	six cases that will correct the tree so that the tree
	does not violate the rules for a RB tree.
	1. Red aunt: recolors the parent, the aunt and the
	grandparent nodes pushing the red up towards the root.
	Will call rebalance again with the grandparent and
	continue to push upwards until there are no more
	violations.
	2. Black aunt/Bent line from grand parent:
	In case 2, we perform a rotation to straigthen the
	path from the inserted node to it's grand parent. 
	After this, we proceed into case 3 with node now
	set to it's former parent.
	3. Black aunt/Straight line from grand parent:
	In this case, we rotate the parent node around the
	grand parent and recolor the parent and the grand
	parent. After this is performed, the black heights
	will be the same, so this case will end rebalance.
	
	The last 3 cases are the same as those above, but 
	swapping left and right around. After rebalance is
	finished, the program resumes at add where the root
	is colored black.
---------------------------------------------------------*/
	private void rebalance(RedBlackNode<T> node) { 


		RedBlackNode<T> dummyRoot = new RedBlackNode<T>();
		root.setParent(dummyRoot);
				
		if(node.getParent() == dummyRoot || node.getParent() == root)
			return;
		
		RedBlackNode<T> grandParent = new RedBlackNode<T>();
		RedBlackNode<T> parent = new RedBlackNode<T>();
		RedBlackNode<T> aunt = new RedBlackNode<T>();
			
		parent = node.getParent();
		
		grandParent = getGrandParent(node);
		
		//Setting aunt node 
		if(parent.getPosition() == "right")
			aunt = grandParent.getLeft();
		else
			aunt = grandParent.getRight();
			
		//Case 1, left red aunt
		if(parent.getPosition() == "right") {	
			
			if(aunt.getColor() == "red"){
				
				parent.setColor("black");
				aunt.setColor("black");
				
				if(grandParent != null) {
					
					grandParent.setColor("red");
					node = grandParent;
					
					if(node != root && node.getColor() == "red")
						
						rebalance(node); 
				}	
			} 
			//Case 2 and 3, left black aunt
				else {
					if(aunt.getColor() == "black") {
						//check for case 2
						if(node.getPosition() == "left") {
							
							rightRotation(node.getLeft());
							node = parent;							
							parent = node.getParent();
						}
						
						if(grandParent != null) {
							
							grandParent.setColor("red");
							leftRotation(node);
							parent.setColor("black");
							return;
						}
					}
				}
			}
			//Case 1, right red aunt 		
			else {
			 		
					if(aunt.getColor() == "red"){
							
						parent.setColor("black");
						aunt.setColor("black");				
							
						if(grandParent != null) {
								
							grandParent.setColor("red");
							node = grandParent;
								
								if(node != root && node.getColor() == "red")
									
									rebalance(node);
						}
					}
					//Case 2 and 3, right black aunt
					else if(aunt.getColor() == "black") {
						//case 2 check	
						if(node.getPosition() == "right") {
								
								leftRotation(node.getRight());
								node = parent;
								parent = node.getParent();
						}		
						//case 3	
						if(grandParent != null) {
								
							grandParent.setColor("red");
							rightRotation(node);
							parent.setColor("black");
							return;
						}
					}
				}
					
		
	}
/*
*	rightRotation(node)
===========================================================
	rightRotation takes typically takes the node that was
	just added and uses that to launch the rotation from.
	During the right rotation, the depths change and are
	accounted for by calling minusDepth and addDepth on the
	affected subtrees. Right rotation moves the parent into
	the position that the grandparent was, shifting parent's
	sub tree up and pushing grand parent's subtree down. The
	grand parent picks up parent's right node and places it
	as it's new left node. The positions of the nodes is also
	adjusted to reflect the changes of the rotation.
-----------------------------------------------------------*/
	private void rightRotation(RedBlackNode<T> node) {
	 
		RedBlackNode<T> formerGrandParent = new RedBlackNode<T>();
		RedBlackNode<T> parent = new RedBlackNode<T>();
		
		formerGrandParent = getGrandParent(node);
		parent = node.getParent();
	 
		if(formerGrandParent == null)
			
			return;
		
		if(formerGrandParent == root.getParent())
			
			return;
			
		if(formerGrandParent == root) {
			
			root = formerGrandParent.getLeft();
			root.setPosition("ROOT");
			root.setParent(null);
		}
		
		else if(formerGrandParent.getPosition() == "right") {
			
			parent.setParent(formerGrandParent.getParent());
			parent.getParent().setRight(parent);
			parent.setPosition("right");
		}
		
		else if(formerGrandParent.getPosition() == "left") {
			
			parent.setParent(formerGrandParent.getParent());
			parent.getParent().setLeft(parent);
			parent.setPosition("left");
		}
		//update the pointers to affected nodes
		formerGrandParent.setParent(parent);
		formerGrandParent.setLeft(parent.getRight());
		formerGrandParent.getLeft().setPosition("left");
		parent.setRight(formerGrandParent);
		formerGrandParent.getLeft().setParent(formerGrandParent);
		//correctly set the positions after rotate
		node.setPosition("left");
		formerGrandParent.setPosition("right");
		formerGrandParent.getLeft().setPosition("left");
		//Right rotation depth changes
		parent.setDepth(parent.getDepth()- 1);
		minusDepth(node);
		addDepth(formerGrandParent);								
	}    
/*
*	leftRotation(node)
===========================================================
	leftRotation takes typically takes the node that was
	just added and uses that to launch the rotation from.
	During the left rotation, the depths change and are
	accounted for by calling minusDepth and addDepth on the
	affected subtrees. Left rotation moves the parent into
	the position that the grandparent was, shifting parent's
	sub tree up and pushing grand parent's subtree down. The
	grand parent picks up parent's left node and places it
	as it's new right node. The positions of the nodes is also
	adjusted to reflect the changes of the rotation.
-----------------------------------------------------------*/  
	private void leftRotation(RedBlackNode<T> node) {
	 
		RedBlackNode<T> formerGrandParent = new RedBlackNode<T>();
		RedBlackNode<T> parent = new RedBlackNode<T>();
		
		parent = node.getParent();
		formerGrandParent = getGrandParent(node);
		
		if(formerGrandParent == null || formerGrandParent == root.getParent())
			
			return;
			
		if(formerGrandParent == root) {
		
			root = formerGrandParent.getRight();
			root.setPosition("ROOT");
			root.setParent(null);
		}
		
		else if(formerGrandParent.getPosition() == "right") {
			
			parent.setParent(formerGrandParent.getParent());
			parent.getParent().setRight(parent);
			parent.setPosition("right");
		}
		
		else if(formerGrandParent.getPosition() == "left") {

			parent.setParent(formerGrandParent.getParent());
			parent.getParent().setLeft(parent);
			parent.setPosition("left");
		}
		//update the pointers of the affected nodes
		formerGrandParent.setParent(parent);
		formerGrandParent.setRight(parent.getLeft());
		formerGrandParent.getRight().setPosition("right");
		parent.setLeft(formerGrandParent);
		formerGrandParent.getRight().setParent(formerGrandParent);		
		//correctly set the positions after rotate
		node.setPosition("right");
		formerGrandParent.setPosition("left");
		formerGrandParent.getRight().setPosition("right");
		//Left rotation depth changes
		parent.setDepth(parent.getDepth()- 1);
		minusDepth(node);
		addDepth(formerGrandParent);
	
	}       
/*
*	levelOrderTraversal()
===========================================================
	levelOrderTraversal creates an array to store all of the
	elements in the RBTree and then outputs them in 
	sequence. The array is filled by calling a recursive 
	function, levelOrder()to traverse the tree and place 
	the nodes in the array in the same fashion as a binary 
	tree array implementation. Then it uses a loop to output
	all of the elements in level order.
---------------------------------------------------------*/
	@Override
	public void levelOrderTraversal() {
    		
		int arrayCapacity = 20 * (int)(count*(Math.log(count)/Math.log(2)) + 4);
			
		RedBlackNode tempArray[] =new RedBlackNode [arrayCapacity];
			
		tempArray[0] = root;
		levelOrder(root.getLeft(), tempArray, 1);
		levelOrder(root.getRight(), tempArray, 2);

		System.out.println();
		for(int i = 0; i < arrayCapacity; i++)	{
				
				if(tempArray[i] != null)
					
					System.out.print(" " + tempArray[i].getContent() + " ");
				
				if(i == 0 || i== 2 || i == 6 || i == 14 || i == 31 || i == 63)
					
					System.out.println();
		}    
	}
/*
*	reverseLeverOrderTraversal()
===========================================================
	This method works just like the levelOrderTraversal()
	function except it iterates in reverse during the 
	printing loop.
---------------------------------------------------------*/
	@Override
	public void reverseLevelOrderTraversal() {
	 
		int arrayCapacity = 20 * (int)(count*(Math.log(count)/Math.log(2)) + 4);
			
		RedBlackNode tempArray[] =new RedBlackNode [arrayCapacity];
			
		tempArray[0] = root;
		levelOrder(root.getLeft(), tempArray, 1);
		levelOrder(root.getRight(), tempArray, 2);

		System.out.println();
		
		for(int i = arrayCapacity-1; i >= 0; i--) {
		
			if(i == 0 || i== 2 || i == 6 || i == 14 || i == 31 || i == 63)
					
				System.out.println();
			
			if(tempArray[i] != null)
				
				System.out.print(" " + tempArray[i].getContent() + " ");

		}    
    }    
    
/*
*	colorTraversal()
===========================================================
	Works just like the previous two methods, except it
	outputs the color of the node next to it.
---------------------------------------------------------*/
	@Override
	public void colorTraversal() {
	 		
		int arrayCapacity = 20 * (int)(count*(Math.log(count)/Math.log(2)) + 4);
			
		RedBlackNode tempArray[] =new RedBlackNode [arrayCapacity];
			
		tempArray[0] = root;
		levelOrder(root.getLeft(), tempArray, 1);
		levelOrder(root.getRight(), tempArray, 2);

		System.out.println();
		
		for(int i = 0; i < arrayCapacity; i++) {
				
			if(tempArray[i] != null)
					
				System.out.print(" " + tempArray[i].getContent() + " " + tempArray[i].getColor() + " ");
				
			if(i == 0 || i== 2 || i == 6 || i == 14 || i == 31 || i == 63)
			
				System.out.println();
		}
	}
/*
*	positionTraversal()
===========================================================
	Works the same as the other traversals with the except
	it prints out the position of the node out also.
---------------------------------------------------------*/
	 @Override
	 public void positionTraversal() {
	 	 		
		int arrayCapacity = 20 * (int)(count*(Math.log(count)/Math.log(2)) + 4);
			
		RedBlackNode tempArray[] =new RedBlackNode [arrayCapacity];
			
		tempArray[0] = root;
		levelOrder(root.getLeft(), tempArray, 1);
		levelOrder(root.getRight(), tempArray, 2);

		System.out.println();
		
		for(int i = 0; i < arrayCapacity; i++) {
			
			if(tempArray[i] != null)
			
				System.out.print(" " + tempArray[i].getContent() + " " + tempArray[i].getPosition() + " ");
				
			if(i == 0 || i== 2 || i == 6 || i == 14 || i == 31 || i == 63)
			
				System.out.println();
	 	}
	}	 
/*
*	depthTraversal()
===========================================================
	Same as the other traversal outputs, except prints the
	depth of the node.
---------------------------------------------------------*/
	@Override
	 public void depthTraversal() {
	 
		int arrayCapacity = 20 * (int)(count*(Math.log(count)/Math.log(2)) + 4);
			
		RedBlackNode tempArray[] =new RedBlackNode [arrayCapacity];
			
		tempArray[0] = root;
		levelOrder(root.getLeft(), tempArray, 1);
		levelOrder(root.getRight(), tempArray, 2);

		System.out.println();
			
		for(int i = 0; i < arrayCapacity; i++) {
				
			if(tempArray[i] != null)
			
				System.out.print(" " + tempArray[i].getContent() + " " + tempArray[i].getDepth() + " ");
			
			if(i == 0 || i== 2 || i == 6 || i == 14 || i == 31 || i == 63)
	
				System.out.println();
		}
	}
/*
*	levelOrder(node, tempArray, index)
===========================================================
	This method adds the elements from the tree into an
	array in level order. Calls itself recursively until
	all of the elements from the tree are in the array.
---------------------------------------------------------*/	 	
	private void levelOrder(RedBlackNode<T> node, RedBlackNode<T> tempArray[], int index) {
	
			if(node.getContent() == null)
				return;
			
			if(node.getContent() != null)
				tempArray[index] = node;
			
			levelOrder(node.getLeft(), tempArray, ((index * 2) + 1));
			levelOrder(node.getRight(), tempArray, ((index + 1) * 2));
	}
	 
/*
*	getGrandParent(node)
===========================================================
	Returns the grand parent of the node input. Returns
	null if there is no grand parent.
---------------------------------------------------------*/	
	private RedBlackNode<T> getGrandParent(RedBlackNode<T> node) {

		if(count >= 3 && node.getParent() != null)
			if(node.getParent().getParent()!= null)
				return node.getParent().getParent();
		
			return null;
	}
/*
*	addDepth(node)
===========================================================
	Adds 1 to all the depths of a subtree.
---------------------------------------------------------*/	
	private void addDepth(RedBlackNode<T> node) {
		
		node.setDepth(node.getDepth() + 1);
		
		if(node.getLeft() != null)
			addDepth(node.getLeft());
		
		if(node.getRight() != null)	
			addDepth(node.getRight());
	}
/*
* minusDepth(node)
===========================================================
	This method takes the root of a subtree and decreases
	all depths by 1.
---------------------------------------------------------*/
	private void minusDepth(RedBlackNode<T> node) {
		
		node.setDepth(node.getDepth() - 1);
		
		if(node.getLeft() != null)
			minusDepth(node.getLeft());
		
		if(node.getRight() != null)
			minusDepth(node.getRight());
	}		
}//end of RedBlackTree.java