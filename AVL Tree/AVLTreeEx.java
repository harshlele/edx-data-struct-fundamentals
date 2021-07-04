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
                return 1 + Math.max(calcHeight(n.left),calcHeight(n.right));
            }
        }
    
        int calcBalance(Node n){
            /*
            if(n.left == null && n.right == null) return 0;
            if(n.left == null) return -1;
            else if(n.right == null) return 1;
            */
            int left = n.left != null ? n.left.height : -1;
            int right = n.right != null ? n.right.height : -1;
            return left - right;
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
                        curr.height = 1;    // either 0 or 1 node to the right
                        curr = curr.left;
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
                        curr.height = 1;        // either 0 or 1 node to the left
                        curr = curr.right;
                        break;
                    }
                }
            }

            rebalance(curr);
        }

        void rebalance(Node n){
            while(n != null){
                
                int left = n.left != null ? n.left.height : -1;
                int right = n.right != null ? n.right.height : -1;
                n.height = 1 + Math.max(left,right);

                int bf = calcBalance(n);
                if (Math.abs(bf) > 1){
                    if(bf < 0){
                        rotateRightChild(n);
                    }
                    else{
                        rotateLeftChild(n);
                    }
                }
                n = n.parent;
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

            newN.left.height -= 2;

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

            newN.right.height -= 2;

        }

    }

        


    public static void main(String[] args){
        System.out.println("test");
        
        AVLTree s = new AVLTree(40);
        s.insert(20);
        s.insert(50);
        s.insert(10);
        s.insert(25);
        s.insert(52);
        s.insert(54);
        s.insert(8);
        s.insert(4);
        System.out.println(s);

    }
}