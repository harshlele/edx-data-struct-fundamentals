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
                int left = n.left != null ? n.left.height : -1;
                int right = n.right != null ? n.right.height : -1;
                return 1 + Math.max(left,right);
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

        void delete(int val){
            if(root == null) return;
            Node curr = root;
            while(curr != null){
                if(curr.val == val){
                    Node toRebal = removeNode(curr);
                    rebalance(toRebal);
                    break;
                }
                else if(curr.val > val){
                    if(curr.left == null) return;
                    else{
                        curr = curr.left;
                    }
                }
                else{
                    if(curr.right == null) return;
                    else{
                        curr = curr.right;
                    }
                }
            }
            
        }
        
        boolean search(int val){
            if(root == null) return false;
            
            boolean found = false;
            Node curr = root;
            while(curr != null){
                if(curr.val == val){
                    found = true;
                    break;
                }
                else if(curr.val > val){
                    if(curr.left == null) break;
                    else{
                        curr = curr.left;
                    }
                }
                else{
                    if(curr.right == null) break;
                    else{
                        curr = curr.right;
                    }
                }
            }

            return found;
        }

        void rebalance(Node n){
            while(n != null){
                
                n.height = calcHeight(n);

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

                if(cRight != null) {
                    right.left = cRight;
                    cRight.parent = right;
                }
                else right.left = null;
                
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
            if(cLeft != null) {
                n.right = cLeft;
                cLeft.parent = n;
            }
            
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

                if(cLeft != null) {
                    left.right = cLeft;
                    cLeft.parent = left;
                }
                else left.right = null;
                
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
            if(cRight != null) {
                n.left = cRight;
                cRight.parent = n;
            }
            
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

        Node removeNode(Node n){
            if(n.left == null && n.right == null){
                Node p = n.parent;
                if(p.left == n) p.left = null;
                else p.right = null;
                return p;
            }
            else if(n.left != null){
                
                Node left = n.left;
                while(left.right != null){
                    left = left.right;
                }
                
                if(left == n.left){
                    Node p = n.parent;
                    Node r = n.right;
                    if(p.left == n){
                        p.left = left;
                    }
                    else{
                        p.right = left;
                    }
                    left.parent = p;

                    if(r != null) {
                        left.right = r;
                        r.parent = left;
                    }
                    return p;
                }
                else{
                    n.val = left.val;
                    return removeNode(left);
                }

            }
            else{

                Node right = n.right;
                while(right.left != null){
                    right = right.left;
                }
                
                if(right == n.right){
                    Node p = n.parent;
                    Node l = n.left;
                    if(p.left == n){
                        p.left = right;
                    }
                    else{
                        p.right = right;
                    }
                    right.parent = p;
                    if(l != null) {
                        right.left = l;
                        l.parent = right;
                    }
                    return p;
                }
                else{
                    n.val = right.val;
                    return removeNode(right);
                }

            }
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
        s.insert(43);
        s.insert(44);
        s.insert(8);
        s.insert(4);
        s.delete(8);
        System.out.println(s);

    }
}