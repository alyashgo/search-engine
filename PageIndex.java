public class PageIndex{
	LinkedList<WordEntry> wordSet;
	public PageIndex(){
		wordSet = new LinkedList<WordEntry>();
	}
	public boolean wordExist(String word){
		Node<WordEntry> temp = this.wordSet.getHead();
		while(temp!=null){
			if(temp.getdata().getWord().equals(word)){
				return true;
			}
			else{
				temp = temp.getnext();
			}
		}
		return false;	
	}
	public WordEntry findWordEntry(String word){
		Node<WordEntry> temp = this.wordSet.getHead();
		if(this.wordExist(word)){
			while(temp!=null){
				if(temp.getdata().getWord().equals(word)){
					return temp.getdata();
				}
				else{
					temp = temp.getnext();
				}
			}
		}
		return temp.getdata();
	}
	public void addPositionForWord(String str, Position p){
		if(this.wordExist(str)){
			WordEntry temp = this.findWordEntry(str);
			temp.getPositionList().add(p);
		}
		else{
			WordEntry newEntry = new WordEntry(str);
			newEntry.getPositionList().add(p);
			this.wordSet.add(newEntry);
		}
		
	}
	public LinkedList<WordEntry> getWordEntries(){
		return this.wordSet;
	}

}