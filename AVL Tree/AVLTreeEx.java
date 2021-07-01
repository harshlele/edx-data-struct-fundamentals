class AVLTreeEx{

    static class AVLTree{

        Node root;

        public AVLTree(int val){
            this.root = new Node(val);
        }

        class Node{
            int val, height;
            Node left,right,parent;
    
            public Node(int val){
                this.val = val;
                this.height = 0;
                this.left = this.right = this.parent = null;
            }
    
        }
    
        static int calcHeight(Node n){
            if(n == null || (n.left == null && n.right == null)) return 0;
            else{
                return 1 + Math.max(calcHeight(n.left), calcHeight(n.right));
            }
        }
    
        static int calcBalance(Node n){
            if(n.left == null && n.right == null) return 0;
            if(n.left == null) return -1;
            else if(n.right == null) return 1;
            else return calcHeight(n.left) - calcHeight(n.right);
        }

        
    }

    


    public static void main(String[] args){
        System.out.println("test");
        
        AVLTree s = new AVLTree(10);


    }
}