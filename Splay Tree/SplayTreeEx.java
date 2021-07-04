class SplayTreeEx {

    static class SplayTree{

        class Node {
            Node left, right, parent;
            int value;
    
            public Node(int value,Node parent,Node left,Node right){
                this.value = value;
                this.parent = parent;
                this.left = left;
                this.right = right;
            }
        }
    
        Node root;
    
        void splay(Node n){
            if(n == null || n == root) return;
            //zig
            if(n.parent == root){
                if(root.left == n){
                    Node right = n.right;
                    n.right = root;
                    n.right.left = right;
                    
                    n.right.parent = n;
                    if(n.right.left != null) n.right.left.parent = n.right; 
                    root = n;
                }
                else{
                    Node left = n.left;
                    n.left = root;
                    n.left.right = left;
    
                    n.left.parent = n;
                    if(n.left.right != null) n.left.right.parent = n.left;
    
                    root = n;
                }
                root.parent = null;
            }
            else{
                Node parent = n.parent;
                Node grandparent = n.parent.parent;
                //zig-zig
                if(grandparent.left == parent && parent.left == n){
                    Node p = grandparent.parent;
                    
                    Node right = parent.right;
                    parent.right = grandparent;
                    parent.right.left = right;
    
                    parent.right.parent = parent;
                    if(parent.right.left != null) parent.right.left.parent = parent.right;
    
                    right = n.right;
                    n.right = parent;
                    n.right.left = right;
    
                    n.right.parent = n;
                    if(n.right.left != null) n.right.left.parent = n.right;
    
                    if(p == null){
                        root = n;
                        root.parent = null;
                    }
                    else {
                        if(p.right == grandparent){
                            p.right = n;
                        }
                        else p.left = n;
                        n.parent = p;
                    }
    
                    splay(n);
                }
                else if(grandparent.right == parent && parent.right == n){
                    Node p = grandparent.parent;
                    
                    Node left = parent.left;
                    parent.left = grandparent;
                    parent.left.right = left;
    
                    parent.left.parent = parent;
                    if(parent.left.right != null) parent.left.right.parent = parent.left;
    
                    left = n.left;
                    n.left = parent;
                    n.left.right = left;
    
                    n.left.parent = n;
                    if(n.left.right != null) n.left.right.parent = n.left;
    
                    if(p == null){
                        root = n;
                        root.parent = null;
                    }
                    else {
                        if(p.right == grandparent){
                            p.right = n;
                        }
                        else p.left = n;
                        n.parent = p;
                    }
    
                }
                //zig-zag
                else if(grandparent.left == parent && parent.right == n){
                    Node p = grandparent.parent;
    
                    Node left = n.left;
                    n.left = parent;
                    n.left.right = left;
    
                    n.left.parent = n;
                    if(n.left.right != null) n.left.right.parent = n.left;
                    
                    Node right = n.right;
                    n.right = grandparent;
                    n.right.left = right;
    
                    n.right.parent = n;
                    if(n.right.left != null) n.right.left.parent = n.right;
    
                    if(p == null){
                        root = n;
                        root.parent = null;
                    }
                    else {
                        if(p.right == grandparent){
                            p.right = n;
                        }
                        else p.left = n;
                        n.parent = p;
                    }
    
                }
                else{
                    Node p = grandparent.parent;
    
                    Node right = n.right;
                    n.right = parent;
                    n.right.left = right;
    
                    n.right.parent = n;
                    if(n.right.left != null) n.right.left.parent = n.right;
                    
                    Node left = n.left;
                    n.left = grandparent;
                    n.left.right = left;
    
                    n.left.parent = n;
                    if(n.left.right != null) n.left.right.parent = n.left;
    
                    if(p == null){
                        root = n;
                        root.parent = null;
                    }
                    else {
                        if(p.right == grandparent){
                            p.right = n;
                        }
                        else p.left = n;
                        n.parent = p;
                    }
                }
                splay(n);
            }    
        }
    
        void add(int key){
            if(root == null){
                root = new Node(key,null,null,null);
                return;
            }
    
            Node curr = root;
            while(curr != null){
                if(curr.value == key){
                    splay(curr);
                    break;
                }
                else if(curr.value > key){
                    if(curr.left != null){
                        curr = curr.left;
                    }
                    else{
                        curr.left = new Node(key,curr,null,null);
                        splay(curr.left);
                        break;
                    }
                }
                else{
                    if(curr.right != null){
                        curr = curr.right;
                    }
                    else{
                        curr.right = new Node(key,curr,null,null);
                        splay(curr.right);
                        break;
                    }
                }
            }
            
        }

        boolean find(int val){
            if(root == null) return false;
            if(root.value == val) return true;

            boolean found = false;

            Node curr = root;

            while(curr != null){
                if(curr.value == val){
                    splay(curr);
                    found = true;
                    break;
                }
                else if(curr.value > val){
                    if(curr.left != null){
                        curr = curr.left;
                    }
                    else{
                        splay(curr);
                        found = false;
                        break;
                    }
                }
                else{
                    if(curr.right != null){
                        curr = curr.right;
                    }
                    else{
                        splay(curr);
                        found = false;
                        break;
                    }
                }
            }

            return found;

        }

        void delete(int key){
            if(root == null) return;
            Node curr = root;
            while(curr.value != key){
                if(curr.value > key){
                    if(curr.left != null){
                        curr = curr.left;
                    }
                    else break;
                }
                else{
                    if(curr.right != null){
                        curr = curr.right;
                    }
                    else break;
                }
            }
            if(curr.value != key){
                return;
            }
            Node parent = curr.parent;
            if(curr.left == null && curr.right == null){
                if(parent != null){
                    if(parent.left == curr) parent.left = null;
                    else parent.right = null;
                    splay(parent);
                    return;
                }
                else{
                    root = null;
                }
            }
            else if(curr.left != null){
                Node pred = inOrderPredecessor(curr);
                if(pred == curr.left){
                    if(curr.parent != null){
                        if(curr.parent.left == curr){
                            curr.parent.left = curr.left;
                        }
                        else{
                            curr.parent.right = curr.left;
                        }
                        curr.left.parent = curr.parent;    
                    }
                    else{
                        root = pred;
                        root.right = curr.right;
                        root.parent = null;
                        if(root.right != null) root.right.parent = root;
                    }
                }
                else{
                    curr.value = pred.value;
                    if(pred.left != null){
                        pred.parent.right = pred.left;
                        pred.left.parent = pred.parent;
                    }
                    else pred.parent.right = null;
                }
            }
            else{
                Node succ = inOrderSuccessor(curr);
                if(succ == curr.right){
                    if(curr.parent != null){
                        if(curr.parent.left == curr){
                            curr.parent.left = curr.right;
                        }
                        else{
                            curr.parent.right = curr.right;
                        }
                        curr.right.parent = curr.parent;    
                    }
                    else{
                        root = succ;
                        root.left = curr.left;
                        root.parent = null;
                        if(root.left != null) root.left.parent = root;                        
                    }
                }
                else{
                    curr.value = succ.value;
                    if(succ.right != null){
                        succ.parent.left = succ.right;
                        succ.right.parent = succ.parent;
                    }
                    else succ.parent.left = null;    
                }
            }

        }

        Node[] split(int key){
            if(root == null) return new Node[]{};
            if(root.left == null && root.right == null){
                return new Node[]{root,null};
            }

            boolean found = find(key);
            if(!found && root.value > key){
                Node right = root;
                Node left = root.left;
                right.left = null;
                left.parent = null;
                
                //set root to null so no operations run on the root...?
                root = null; 

                return new Node[]{left,right};
            }
            else{
                Node left = root;
                Node right = root.right;
                left.right = null;
                right.parent = null;

                //set root to null so no operations run on the root...?
                root = null; 
                
                return new Node[]{left,right};
            }
        }

        void join(Node left, Node right){
            root = left;
            Node curr = root;
            while(curr.right != null){
                curr = curr.right;
            }
            splay(curr);

            root.right = right;
            root.right.parent = root;
        }

        Node inOrderSuccessor(Node n){
            Node right = n.right;
            while(right.left != null){
                right = right.left;
            }
            return right;
        }

        Node inOrderPredecessor(Node n){
            Node left = n.left;
            while(left.right != null){
                left = left.right;
            }
            return left;
        }

        void inOrder(Node n){
            if(n == null) return;
            if(n.left != null) inOrder(n.left);
            System.out.print(n.value + " ");
            if(n.right != null) inOrder(n.right);

        }

        void splitPrint(int key){
            Node[] arr = split(key);
            join(arr[0], arr[1]);
        }
    
    }

    public static void main(String[] args){
        SplayTree s = new SplayTree();
        s.add(0);
        s.add(20);
        s.add(10);
        s.add(60);
        s.add(70);
        s.add(50);
        s.add(40);
        s.add(30);
        s.add(80);
        s.add(90);
        s.splitPrint(30);
        s.inOrder(s.root);    
   }
}