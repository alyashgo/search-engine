public class Position{
	PageEntry page;
	int wordIndex;
	public Position(PageEntry p, int wordPosition){
		page  = p;
		wordIndex = wordPosition;
	}
	public PageEntry getPageEntry(){
		return this.page;
	}
	public int getWordIndex(){
		return this.wordIndex;
	}
}