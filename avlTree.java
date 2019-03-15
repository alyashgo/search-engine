public class avlTree{
	public avlNode root;
	public avlTree(){
		root = null;
	}
	public int getHeight(avlNode node){
		if(node == null){
			return 0;
		}
		int count =0;
		avlNode temp = node;
		count = 1 + Math.max(this.getHeight(temp.left), this.getHeight(temp.right));
		return count;
	}
	public int getBalance(avlNode node){
		if(node ==null){
			return 0;
		}
		return (getHeight(node.left) - getHeight(node.right));
	}
	public Boolean doesExist(int p){
		avlNode temp = this.root;
		while(temp!=null){
			if(temp.data.getWordIndex()==p){
				return true;
			}
			else if(temp.data.getWordIndex()<p){
				temp = temp.right;
			}
			else if(temp.data.getWordIndex()>p){
				temp = temp.left;
			}
		}
		return false;
	}
	public void rotateRight(avlNode z, boolean flag){
		avlNode y = z.left;
		avlNode t3 = y.right;
		avlNode temp1 = z.parent;
		if(z.parent == null){
			this.root = y;
		}
		if(z.parent!=null){
			if(flag){
				temp1.right = y;
			}
			else{
				temp1.left = y;
			}
		}
		y.parent = temp1;
		y.right = z;
		z.left = t3;
		z.parent = y;
		if(t3!=null){
			t3.parent = z;
		}	
	}

	public void rotateLeft(avlNode z, boolean flag){
		avlNode y = z.right;
		avlNode t2 = y.left;
		avlNode temp1 = z.parent;
		if(z.parent==null){
			this.root = y;
		}
		if(z.parent!=null){
			if(flag){
				temp1.right = y;
			}
			else{
				temp1.left = y;
			}
		}
		y.parent = temp1;
		y.left = z;
		z.parent = y;
		z.right = t2;
		if(t2!=null){
			t2.parent = z;
		}	
	}
	public void treeInsert(avlNode node){
		avlNode temp = this.root;
		avlNode tempParent =null;
		boolean flag = false;
		if(temp==null){
			node.parent = tempParent;
			this.root = node;
			return;
		}
		while(temp!=null){
			if(node.data.getWordIndex()<=temp.data.getWordIndex()){
				tempParent = temp;
				temp = temp.left;
				flag = false;
			}
			else if(node.data.getWordIndex()>temp.data.getWordIndex()){
				tempParent = temp;
				temp = temp.right;
				flag = true;
			}
		}
		node.parent = tempParent;
		temp = node;
		if(flag){
			tempParent.right = node;
		}
		else{
			tempParent.left = node;
		}
		while(temp.parent!=null){
			if(this.getBalance(temp)>1 || this.getBalance(temp)<-1){
				if(this.getBalance(temp)>1 && node.data.getWordIndex()<=temp.left.data.getWordIndex()){
					this.rotateRight(temp, flag);//left left case
					return;
				}
				if(this.getBalance(temp)<-1 && node.data.getWordIndex()>temp.right.data.getWordIndex()){
					this.rotateLeft(temp, flag);//right right case
					return;
				}
				if(this.getBalance(temp)>1 && node.data.getWordIndex()>temp.left.data.getWordIndex()){
					this.rotateLeft(temp.left, false);//left right case
					this.rotateRight(temp, flag);
					return;
				}
				if(this.getBalance(temp)<-1 && node.data.getWordIndex()<=temp.right.data.getWordIndex()){
					this.rotateRight(temp.right, true);//right left case
					this.rotateLeft(temp, flag);
					return;
				}
			}
			else{
				temp = temp.parent;
			}
		}
		return;
	}
	public void print(avlNode node){
		if(node==null){
			return;
		}
		else{
			this.print(node.left);
			System.out.println(node.data.getWordIndex());
			this.print(node.right);
		}
	}
}