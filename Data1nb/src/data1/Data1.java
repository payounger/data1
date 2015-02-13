
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
        
        public BST inter(BST bst);
        
        public BST diff(BST bst);
        
        public Boolean equal(BST bst);
        
        public Boolean subset(BST bst);
    
    }      
    
    class Branch implements BST{
        
        public String toString(){
            return 
                    "Branch(" + this.left + ","
                        + this.key + "," + this.right + ")" ;
        }
    
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
        //This method first goes until it hits the leftmost leaf, then checks
        //to the right from there, hits a leaf and then adds the key to the 
        //target set, it maintains purity since add is a pure method. This 
        //process continues, hitting the bottom of this.left and this.right 
        //adding this.key to bst, until all of this has been added onto bst,
        //producing an entirely new BST, thereby maintaining purity
        public BST union(BST bst){
            return bst.union(this.left).union(this.right).add(this.key);
        }
        
        //checks to see if this.key is a member of bst, if yes it purely returns
        //a new branch, calling itself recursively on both children and putting
        //in the key. If this.key is not a member of bst, it calls remove on
        //the key, and then recursively calls itself. When inter hits a leaf it
        //returns this 
        public BST inter(BST bst){
            if(bst.member(this.key)){
                return new Branch(this.left.inter(bst), 
                                    this.key, this.right.inter(bst));
            } else return this.remove(this.key).inter(bst);
        }
        
        //pretty much an inverse of union, removes things that are in common,
        //creates a new branch from any items that are not found to be common,
        //maintains purity by returning a fresh new BST
        public BST diff(BST bst){
            if(bst.member(this.key)){
                return this.remove(this.key).diff(bst);
            }else 
                return new Branch(this.left.diff(bst),
                                    this.key, this.right.diff(bst));
        }
        
        //if two sets are equal then their difference should be nothing, so this
        //method tries to find that quickly by just calling the diff method and
        //checking to see if it is empty, it calls it in both directions to 
        //avoid false positives or negatives based on supersets and subsets
        public Boolean equal(BST bst){
            return (bst.diff(this).isEmptyHuh()
                        &&
                        this.diff(bst).isEmptyHuh());
        }
        
        //if all elements of this are members of the target, bst, then this is
        //a subset.
        public Boolean subset(BST bst){
            return bst.member(this.key)
                    && subset(this.left)
                    && subset(this.right);
        }
        
    }
    
    class Leaf implements BST{
        
        //a leaf is like the equivalent of an empty set for this data structure
        Leaf(){
        }
        
        public String toString(){
            return "Leaf";
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
        
        public BST inter(BST bst){
            return this;
        }
        
        public BST diff(BST bst){
            return this;
        }
        
        //if this leaf based method of equal is called then if the bst is also
        //a leaf then they are equal.
        public Boolean equal(BST bst){
            return bst.isEmptyHuh();
        }
        
        //the empty set is a subset of all sets, so always true
        public Boolean subset(BST bst){
            return true;
        }
        
    }
}
