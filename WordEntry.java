public class WordEntry{
	String word;
	public float wordFreq;
	LinkedList<Position> positionList;
	public LinkedList<Integer> helperlist;
	public avlTree posTree;
	public WordEntry(String wordId){
		word = wordId;
		wordFreq =0;
		positionList = new LinkedList<Position>();
		posTree = new avlTree();
		helperlist = new LinkedList<Integer>();
	}
	public LinkedList<Position> getPositionList(){
		return this.positionList;
	}
	public String getWord(){
		return this.word;
	}
	public void addPosition(Position position){
		positionList.add(position);
	}
	public void addPositions(LinkedList<Position> positions){
		Node<Position> temp = positions.getHead();
		while(temp!=null){
			this.positionList.add(temp.getdata());
			temp = temp.getnext();
		}
	}
	public LinkedList<Position> getAllPositionsForThisWord(){
		return this.positionList;
	}
	public boolean equals( WordEntry wordEntry){
		if(wordEntry.getWord().equals(this.getWord())){
			return true;
		}
		return false;
	}
	public float getTermFrequency(){
		return wordFreq;
	}
	public void setWordFreq(int numwords){
		this.wordFreq = (float)(this.positionList.size())/numwords;
	}
	
}