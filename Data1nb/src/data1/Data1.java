
package data1;

public class Data1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //TESTS GO HERE
        
        //does this main method have to go below my other classes? Also i am not
        //really sure how to implement an effective test structure for this,
        //I know i should build a to-string for each class, but im still not 
        //really sure how im going to test these methods effectively.
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
    
        public Boolean member(int x);
        //returns true if x is a member of BST 

	public BST add(int x);

	public BST remove(int x);
        
        public BST union(BST bst);
        
        /*
        public BST intersection(BST bst);
        
        public BST difference(BST bst);
        
        public Boolean equal(BST bst);
        
	public Boolean subset(BST bst);
            */
    
    }      
    
    class Branch implements BST{
    
        BST left;
        int key;
        BST right;
        
        //a branch contains 3 things, the key, which is the data it contains,
        //in this case an int, and then  a left and right child, both BST's 
        //the left child must hold a smaller number, the right a larger number.
        Branch(BST left, int key, BST right){
            this.left = left;
            this.right = right;
            this.key = key;
        }
        
        //a branch must contain something in its key so this always returns true
        public Boolean isEmptyHuh(){
            return false;
        }
        
        
        //im a bit confused about the implementation of this method, should it 
        //exist within the branch class? I currently have it just making a new
        //leaf
        public BST empty(){
            return new Leaf();
        }
        
        //this returns the number of members within the BST, it does this by
        //beggining with a value of one to count the current element, and then
        //calls itself on each of the children, which cascade down until they
        //hit a leaf which returns 0
        public int cardinality(){
            return 1 + left.cardinality() + right.cardinality();
        }
        
        
        //this is the good part about BST's is that their member method can be
        //pretty quick since the structure of BST's serves to simplify numerical
        //searches by the left child less than right child greater than tool.
        //First it checks to see if the current branch contains the target, x,
        //if not, it checks inequality to see if it should search left or right,
        //and then proceeds down. If it hits a leaf it returns zero.
        public Boolean member(int x){
            if(this.key == x){
                return true;
            } else if(this.key > x){
                return left.member(x);
            } else return right.member(x);
        }
        
      /*
        first the add function checks to see if the key already exists within
        the target, if so, it just returns a fresh copy of the current BST to
        preserve purity, but im not sure if this is necessary, or if just 
        returning an unmodified version of the initial tree is pure. Afterwards
        it checks inequalities to determine if the new value should be added to 
        the left or right, left if the value is less than the key, right if its
        greater, and the function calls itself, until it hits a leaf, then 
        injects itself in the proper locale to preserve the BST structure, the
        function creates a new branch in every case, so purity is maintained
        */
        public BST add(int x){
            if(this.key == x){
                return new Branch(this.left, this.key, this.right);
            } else if(this.key > x){
                return new Branch(this.left.add(x), this.key, this.right);
            } else /*if(this.key < x)*/{
                return new Branch(this.left, this.key, this.right.add(x));
                
            }
        }
        
        public BST remove(int x){
            if(this.key == x){
                return this.left.union(this.right);
            }else if(this.key > x){
                return new Branch(this.left.remove(x), this.key, this.right);
            }else /*if(this.key < x)*/{
                return new Branch(this.left, this.key, this.right.remove(x));
            }
        }
        
        
        //Nick Burka helped me with my union method since i hadn't been able
        //to figure it out, I called him and he came over and explained his 
        //implementation to me super thoroughly, so now I understand what goes
        //on much better, please give him a participation/helper point!
        public BST union(BST bst){
            return bst.union(this.left).union(this.right).add(this.key);
        }
    }
    
    class Leaf implements BST{
        
        //a leaf is like the equivalent of an empty set for this data structure
        Leaf(){
        }
        
        //a leaf has no key, so it always returns true for isEmptyHuh
        public Boolean isEmptyHuh(){
            return true;
        }
        
        //This method generates a new leaf, a fresh empty set
        public BST empty(){
            return new Leaf();
        }
        
        //this generally will be called through the cardinality function of
        //the branch class i assume, but since leaf is an empty set it has no
        //members and returns 0
        public int cardinality(){
            return 0;
        }
        
        //since there are no ints within a leaf it cannot find a member here,
        //so member will always return false on a leaf
        public Boolean member(int x){
            return false;
        }
        
        public BST add(int x){
            return new Branch(this, x, this);
        }
        
        public BST remove(int x){
            return this;
        }
        
        public BST union(BST bst){
            return bst;
        }
    }
}
