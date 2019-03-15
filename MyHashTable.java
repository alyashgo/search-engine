import java.util.*;
public class MyHashTable{
	LinkedList<WordEntry>[] arr;
	public MyHashTable(){
		arr = new LinkedList[26];
		int i;
		for(i=0;i<26;i++){
			arr[i] = new LinkedList<WordEntry>();
		}
	}
	private int getHashIndex(String str){
		int index = (((int)str.charAt(0)-'a')%26);
		if(index<0){
			index = -1*index;
		}
		return index;
	}
	public int gethashIndex(String str){
		int index = (((int)str.charAt(0)-'a')%26);
		if(index<0){
			index = -1*index;
		}
		return index;
	}
	public LinkedList<WordEntry>[] getArray(){
		return this.arr;
	} 
	public void addPositionsForWord(WordEntry w){
		String wordId = w.getWord();
		int hashIndex = this.gethashIndex(wordId);
		Node<WordEntry> temp = this.arr[hashIndex].getHead();
		if(temp!=null){
			while(temp!=null){
				if(temp.getdata().getWord().equals(wordId)){
					LinkedList<Position> newlist = w.getPositionList();
					temp.getdata().addPositions(newlist);
					return;
				}
				else{
					temp = temp.getnext();
				}
			}
			if(temp==null){
				this.arr[hashIndex].add(w);
				return;
			}
		}
		else{
			this.arr[hashIndex].add(w);
		}
		return;
	}
	public WordEntry getWordEntry(String str) throws wordnotfoundException{
		int index = this.gethashIndex(str);
		Node<WordEntry> temp  = this.arr[index].getHead();
		WordEntry result = null;
		while(temp!=null){
			if(temp.getdata().getWord().equals(str)){
				result = temp.getdata();
				break;
			}
			temp = temp.getnext();
		}
		if(result ==null){
			throw new wordnotfoundException("no such word");
		}
		return result;
	}
}
class wordnotfoundException extends Exception{
	public wordnotfoundException(String message){
		super(message);
	}
}