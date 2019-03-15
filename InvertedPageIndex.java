import java.util.*;
public class InvertedPageIndex{
	MyHashTable hashTable;
	public int numpages =0;
	public InvertedPageIndex(){
		hashTable = new MyHashTable();
	}
	public MyHashTable getHashTable(){
		return this.hashTable;
	}
	public void addPage(PageEntry p){
		PageIndex temp = p.getPageIndex();
		LinkedList<WordEntry> newtemp = temp.getWordEntries();
		Node<WordEntry> tempnode = newtemp.getHead();
		while(tempnode!=null){
			WordEntry w = new WordEntry(tempnode.getdata().getWord());
			w.addPositions(tempnode.getdata().getPositionList());
			hashTable.addPositionsForWord(w);
			tempnode = tempnode.getnext();
		}
		numpages+=1;
	}
	public Myset<PageEntry> getPagesWhichContainWord(String str) throws noWordFoundError{
		Myset<PageEntry> pageSet = new Myset<PageEntry>();
		int index = this.hashTable.gethashIndex(str);
		try{
			WordEntry wordentry = this.hashTable.getWordEntry(str);
			Node<Position> temp = wordentry.getPositionList().getHead();
			while(temp!=null){
				pageSet.Insert(temp.getdata().getPageEntry());
				temp = temp.getnext();
			}
		}
		catch(wordnotfoundException e){
			throw new noWordFoundError("No webpage contains word "+str);
		}
		return pageSet;
	}
	public Myset<PageEntry> getPagesWhichContainPhrase(String str[]) throws noPageContainsPhrase{
		Myset<PageEntry> pages = new Myset<PageEntry>();
		Myset<PageEntry> ans = new Myset<PageEntry>();
		try{
			for(int i=0; i<str.length;i++){
				String word = str[i].toLowerCase();
				if(word.equals("stacks")){
					word = "stack";
				}
				if(word.equals("structures")){
					word = "structure";
				} 
				if(word.equals("applications")){
					word  = "application";
				}
				if(i==0){
					pages = pages.Union(this.getPagesWhichContainWord(word));
				}
				else{
					pages = pages.Intersection(this.getPagesWhichContainWord(word));
				}
			}
			if(pages.IsEmpty()){
				throw new noPageContainsPhrase("phrase not contained in any Webpage");	
			}
			Node<PageEntry> temp = pages.getList().getHead();
			while(temp!=null){
				boolean flag = true;
				PageIndex p = temp.getdata().getPageIndex();
				String word = str[0].toLowerCase();
				if(word.equals("stacks")){
					word = "stack";
				}
				if(word.equals("structures")){
					word = "structure";
				} 
				if(word.equals("applications")){
					word  = "application";
				}
				Node<Integer> firstwordtemp = p.findWordEntry(word).helperlist.getHead();
				while(firstwordtemp!=null){
					int pos = firstwordtemp.getdata();
					for(int j =1; j<str.length; j++){
						String tempword = str[j].toLowerCase();
						if(tempword.equals("stacks")){
							tempword = "stack";
						}
						if(tempword.equals("structures")){
							tempword = "structure";
						} 
						if(tempword.equals("applications")){
							tempword  = "application";
						}
						if(p.findWordEntry(tempword).posTree.doesExist(pos+1)){
							flag = true;
						}
						else{
							flag =false;
							break;
						}
						pos = pos+1;		
					}
					if(flag){
					ans.Insert(temp.getdata());
					}
					firstwordtemp = firstwordtemp.getnext();
				}
				temp = temp.getnext();
			}
			if(ans.IsEmpty()){
				throw new noPageContainsPhrase("phrase not contained in any Webpage"); 
			}
		}
		catch(noWordFoundError e){
			throw new noPageContainsPhrase("phrase not contained in any Webpage");
		}
		return ans;

	}
}
class noWordFoundError extends Exception{
	public noWordFoundError(String message){
		super(message);
	}
}
class noPageContainsPhrase extends Exception{
	public noPageContainsPhrase(String message){
		super(message);
	}
}