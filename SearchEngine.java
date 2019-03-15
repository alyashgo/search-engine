import java.util.*;
public class SearchEngine{
	InvertedPageIndex invertedIndex;
	LinkedList<String> pages;
	int numPages=0;
	
	public SearchEngine(){
		invertedIndex = new InvertedPageIndex();
		pages = new LinkedList<String>();
	}
	public void performAction(String actionMessage){
		String[] arr = actionMessage.split(" ");
		if(arr[0].equals("addPage")){
			PageEntry page = new PageEntry(arr[1]);
			this.invertedIndex.addPage(page);
			this.pages.add(arr[1]);
			numPages+=1;
		}
		if(arr[0].equals("queryFindPagesWhichContainWord")){
			String word = arr[1].toLowerCase();
			if(word.equals("stacks")){
							word = "stack";
						}
			if(word.equals("structures")){
				word = "structure";
			} 
			if(word.equals("applications")){
				word  = "application";
			}
			try{
				Node<PageEntry> temp = this.invertedIndex.getPagesWhichContainWord(word).getList().getHead();
				Myset<SearchResult> ans = new Myset<SearchResult>();
				String[] array = new String[1];
				array[0] = word;
				while(temp!=null){
					SearchResult s = new SearchResult(temp.getdata(),temp.getdata().getRelevanceOfPage(array,false,this.invertedIndex));
					ans.Insert(s);
					temp = temp.getnext();
				}
				MySort sort = new MySort();
				ArrayList<SearchResult> resutls = sort.sortThisList(ans);
				for(int i=0; i<resutls.size();i++){
					if(i==resutls.size()-1){
						System.out.println(resutls.get(i).getPageEntry().getPageName());
					}
					else{
						System.out.print(resutls.get(i).getPageEntry().getPageName()+", ");
					}
				}
			}
			catch(noWordFoundError e){
				System.out.println("No webpage contains word "+arr[1]);
			}
		}
		if(arr[0].equals("queryFindPositionsOfWordInAPage")){
			String word = arr[1].toLowerCase();
			String pagename = arr[2];
			LinkedList<WordEntry>[] wordarr = this.invertedIndex.getHashTable().getArray();
			int index = this.invertedIndex.getHashTable().gethashIndex(word);
			Node<WordEntry> temp = wordarr[index].getHead();
			if(word.equals("stacks")){
							word = "stack";
						}
			if(word.equals("structures")){
				word = "structure";
			} 
			if(word.equals("applications")){
				word  = "application";
			}
			if(this.pages.contains(arr[2])){
				if(temp!=null){
				while(temp!=null){
					if(temp.getdata().getWord().equals(word)){
						break;
					}
					else{
						temp = temp.getnext();
					}
				}
				if(temp!=null){
					System.out.print("Position at which "+arr[1]+" occurs in page "+arr[2]+" are: ");
					Node<Position> tempos = temp.getdata().getPositionList().getHead();
					while(tempos!=null){
						if(tempos.getdata().getPageEntry().getPageName().equals(pagename)){
							System.out.print(tempos.getdata().getWordIndex()+" ");
						}
						tempos = tempos.getnext();					
					}
					System.out.println("");
				}
				else{
					System.out.println("Webpage "+pagename+" does not contain word "+word);
				}
			}
				else{
					System.out.println("Webpage "+pagename+" does not contain word "+word);
				}
			}
			else{
				System.out.println("Webpage "+pagename+" not found in the database");
			}	
		}
		if(arr[0].equals("queryFindPagesWhichContainAllWords")){
			try{
				Myset<PageEntry> ans = new Myset<PageEntry>();
				for(int i=1; i<arr.length;i++){
					String word = arr[i].toLowerCase();
					if(word.equals("stacks")){
						word = "stack";
					}
					if(word.equals("structures")){
						word = "structure";
					} 
					if(word.equals("applications")){
						word  = "application";
					}
					if(i==1){
						ans = ans.Union(invertedIndex.getPagesWhichContainWord(word));
					}
					else{
						ans = ans.Intersection(invertedIndex.getPagesWhichContainWord(word));
					}
				}
				if(ans.IsEmpty()){
				System.out.println("No webpage contains any of these words");
				return;
				}
				Myset<SearchResult> newans = new Myset<SearchResult>();
				Node<PageEntry> temp = ans.getList().getHead();
				while(temp!=null){
					SearchResult s = new SearchResult(temp.getdata(),temp.getdata().getRelevanceOfPage(arr,false,this.invertedIndex));
					newans.Insert(s);
					temp = temp.getnext();
				}
				MySort sort = new MySort();
				ArrayList<SearchResult> resutls = sort.sortThisList(newans);
				for(int i=0; i<resutls.size();i++){
					if(i==resutls.size()-1){
						System.out.println(resutls.get(i).getPageEntry().getPageName());
					}
					else{
						System.out.print(resutls.get(i).getPageEntry().getPageName()+", ");
					}
				}
			}
			catch(noWordFoundError e){
				System.out.println(e.getMessage());

			}
		}
		if(arr[0].equals("queryFindPagesWhichContainAnyOfTheseWords")){
			Myset<PageEntry> ans = new Myset<PageEntry>();
			for(int i=1; i<arr.length;i++){
				String word = arr[i].toLowerCase();
				if(word.equals("stacks")){
					word = "stack";
				}
				if(word.equals("structures")){
					word = "structure";
				} 
				if(word.equals("applications")){
					word  = "application";
				}
				try{
					ans = ans.Union(invertedIndex.getPagesWhichContainWord(word));
				}
				catch(Exception e){}
			}
			if(ans.IsEmpty()){
				System.out.println("No webpage contains any of these words");
			}
			else{
				Myset<SearchResult> newans = new Myset<SearchResult>();
				Node<PageEntry> temp = ans.getList().getHead();
				while(temp!=null){
					SearchResult s = new SearchResult(temp.getdata(),temp.getdata().getRelevanceOfPage(arr,false,this.invertedIndex));
					newans.Insert(s);
					temp = temp.getnext();
				}
				MySort sort = new MySort();
				ArrayList<SearchResult> resutls = sort.sortThisList(newans);
				for(int i=0; i<resutls.size();i++){
					if(i==resutls.size()-1){
						System.out.println(resutls.get(i).getPageEntry().getPageName());
					}
					else{
						System.out.print(resutls.get(i).getPageEntry().getPageName()+", ");
					}
				}
			}
		}
		if(arr[0].equals("queryFindPagesWhichContainPhrase")){
			int n = arr.length-1;
			String[] phraseArr = new String[n];
			for(int i=1;i<arr.length;i++){
				phraseArr[i-1] = arr[i];
			}
			try{
				Node<PageEntry> temp = this.invertedIndex.getPagesWhichContainPhrase(phraseArr).getList().getHead();
				Myset<SearchResult> ans = new Myset<SearchResult>();
				while(temp!=null){
					SearchResult s = new SearchResult(temp.getdata(),temp.getdata().getRelevanceOfPage(phraseArr,true,this.invertedIndex));
					ans.Insert(s);
					temp = temp.getnext();
				}
				MySort sort = new MySort();
				ArrayList<SearchResult> resutls = sort.sortThisList(ans);
				for(int i=0; i<resutls.size();i++){
					if(i==resutls.size()-1){
						System.out.println(resutls.get(i).getPageEntry().getPageName());
					}
					else{
						System.out.print(resutls.get(i).getPageEntry().getPageName()+", ");
					}
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
}