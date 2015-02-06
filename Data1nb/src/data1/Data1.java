
package data1;

public class Data1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //TESTS GO HERE
    }
 
    public interface BST {
    
        public Boolean isEmptyHuh();
        //determines if t is empty
        
        
        //So since essentially this just gives me a leaf, should i code it only
        //in the leaf class? Should this method even be in the BST class, or 
        //just in the leaf class? 
        public BST empty();
        //Returns a fresh empty set
        
        public int cardinality();
        //returns the number of elements in bst
    
   /*   
        
        
//         (cardinality t) → integer
//         t : finite-set
//         Returns the number of elements in t.


	public Boolean member(int x);
        //

	public BST add(int x);
        //

	public BST remove(int x);
        //
    
	public BST union(bst1 bst2);
        //
    
	public BST successor();
        //
    
	public BST minimum();*/
        //
    
    }      
    
    class Branch implements BST{
    
        BST left;
        int key;
        BST right;
        
        Branch(BST left, int key, BST right){
            this.left = left;
            this.right = right;
            this.key = key;
        }
        
        public Boolean isEmptyHuh(){
            return false;
        }
        
        public BST empty(){
            return new Leaf();
        }
        
        public int cardinality(){
            return 1 + left.cardinality() + right.cardinality();
        }
    }
    
    class Leaf implements BST{
        int key;
        
        
        
        Leaf(){
        }
        
        public Boolean isEmptyHuh(){
            return true;
        }
        
        public BST empty(){
            return new Leaf();
        }
        
        public int cardinality(){
            return 0;
        }
    }
}
