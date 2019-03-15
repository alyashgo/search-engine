public class avlNode{
	public Position data;
	public avlNode left;
	public avlNode right;
	public avlNode parent;

	public avlNode(Position position){
		this.data = position;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
}