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
    
        int calcHeight(Node n){
            if(n == null) return -1;
            else{
                return 1 + Math.max(n.left.height, n.right.height);
            }
        }
    
        int calcBalance(Node n){
            /*
            if(n.left == null && n.right == null) return 0;
            if(n.left == null) return -1;
            else if(n.right == null) return 1;
            */
            return n.left.height - n.right.height;
        }

        void insert(int val){
            Node curr = root;
            
            while(curr != null){
                if (curr.val == val){
                    curr = null;
                }
                if(curr.val > val){
                    if(curr.left != null){
                        curr = curr.left;
                    }
                    else{
                        curr.left = new Node(val);
                        curr.left.parent = curr;
                        curr = curr.left;
                        curr.height = calcHeight(curr);
                        break;
                    }
                }
                else{
                    if(curr.right != null){
                        curr = curr.right;
                    }
                    else{
                        curr.right = new Node(val);
                        curr.right.parent = curr;
                        curr = curr.right;
                        curr.height = calcHeight(curr);
                        break;
                    }
                }
            }

            rebalance(curr);
        }

        void rebalance(Node n){
            while(n != null){
                int bf = calcBalance(n);
                if (Math.abs(bf) <= 1){
                    n = n.parent;
                }
                else{
                    if(bf < 0){
                        rotateRightChild(n);
                        n = n.parent;
                    }
                    else{
                        rotateLeftChild(n);
                        n = n.parent;
                    }
                }
            }

        }

        void rotateRightChild(Node n){
            Node right = n.right;
            Node p = n.parent;
            int bf = calcBalance(right);

            if(bf > 0){
                Node left = right.left;
                
                Node cRight = left.right;
                
                left.right = right;
                left.parent = right;

                if(cRight != null) right.left = cRight;
                
                n.right = left;
                left.parent = n;

                //update heights
                n.right.height += 1;
                n.right.right.height -= 1;

            }

            Node newN = n.right;

            n.right = null;
            newN.parent = null;

            Node cLeft = newN.left;
            newN.left = n;
            n.parent = newN;
            if(cLeft != null) n.right = cLeft;
            
            if(p != null){
                if(p.right == n){
                    p.right = newN;
                }
                else{
                    p.left = newN;
                }
                newN.parent = p;
            }

            newN.height += 1;
            newN.left.height -= 1;


        }

        void rotateLeftChild(Node n){
            Node left = n.left;
            Node p = n.parent;
            int bf = calcBalance(left);
            
            if(bf < 0){
                Node right = left.right;
                
                Node cLeft = right.left;
                
                right.left = left;
                left.parent = right;

                if(cLeft != null) left.right = cLeft;
                
                n.left = right;
                right.parent = n;

                //update heights
                n.left.height += 1;
                n.left.left.height -= 1;
                
            }


            Node newN = n.left;

            n.left = null;
            newN.parent = null;

            Node cRight = newN.right;
            newN.right = n;
            n.parent = newN;
            if(cRight != null) n.left = cRight;
            
            if(p != null){
                if(p.right == n){
                    p.right = newN;
                }
                else{
                    p.left = newN;
                }
                newN.parent = p;
            }

            newN.height += 1;
            newN.right.height -= 1;

        }

    }

        


    public static void main(String[] args){
        System.out.println("test");
        
        AVLTree s = new AVLTree(10);


    }
}