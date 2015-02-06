
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
    
   /*   
        public int cardinality();

	public Boolean member(int x);

	public BST add(int x);

	public BST remove(int x);
    
	public int cardinality();
    
	public BST successor();
    
	public BST minimum();*/
    
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
    }
    
    class Leaf implements BST{
        int key;
        
        
        Leaf(int key){
            this.key = key;
        }
        
        public Boolean isEmptyHuh(){
            return true;
        }
    }
}
