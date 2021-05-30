import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BST{

    private static class Node{
        int val;
        Node left,right,parent;

        public Node(int val, Node left, Node right){
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public Node(int val){
            this.val = val;
        }
    }

    private static Node insert(Node root,int val){
        if(root == null){
            return new Node(val,null,null);
        }
        if(root.val == val) return root;
        else{
            
            if(root.val > val){
                if(root.left == null){
                    Node l = new Node(val,null,null);
                    l.parent = root;
                    root.left = l;
                    return l;
                }
                else return insert(root.left,val);
            }
            else{
                if(root.right == null){
                    Node l = new Node(val,null,null);
                    l.parent = root;
                    root.right = l;
                    return l;
                }
                else return insert(root.right,val);
            }
        }
    }

    private static Node find(Node root,int val){
        if(root.val == val) return root;

        if(root.val < val){
            if(root.right == null) return root;
            else return find(root.right,val);
        }
        else{
            if(root.left == null) return root;
            else return find(root.left,val);
        }
    }

    private static void printInOrder(Node root){
        if(root == null) return;

        if(root.left != null) printInOrder(root.left);
        System.out.print(root.val + " ");
        if(root.right != null) printInOrder(root.right);
    } 


    private static Node getNeighbour(Node n,int side){
        if(side == 0){
            Node curr = n.left;
            while(curr.right != null){
                curr = curr.right;
            }
            return curr;
        }
        else{
            Node curr = n.right;
            while(curr.left != null){
                curr = curr.left;
            }
            return curr;
        }
    }

    
    private static void delete(Node root, int val){
        Node n = find(root,val);
        Node parent = n.parent;
        if(n.val != val || parent == null) return;

        if(n.right != null && n.left != null){
            int side = new Random().nextInt(2);
            Node neighbour = getNeighbour(n, side);
            if(side == 0){
                delete(n.left,neighbour.val);
            }
            else delete(n.right,neighbour.val);
            n.val = neighbour.val;
            
        }
        else if(n.left != null){
            if(parent.left == n){
                parent.left = n.left;
            }
            else{
                parent.right = n.left;
            }
            n.left.parent = parent;
        }
        else if(n.right != null){
            if(parent.left == n){
                parent.left = n.right;
            }
            else{
                parent.right = n.right;
            }
            n.right.parent = parent;
        }
        else{
            if(parent.left == n){
                parent.left = null;
            }
            else{
                parent.right = null;
            }
        }
    }
    
    public static void main(String[] args){
        Node tree = new Node(4);
        insert(tree, 2);
        insert(tree, 6);
        insert(tree, 1);
        insert(tree, 3);
        insert(tree, 5);
        insert(tree, 7);
        insert(tree, 8);
        printInOrder(tree);
        System.out.println("");
        
        delete(tree,3);
        delete(tree,8);
        delete(tree,6);
        printInOrder(tree);


    }
}