import java.util.*;
public class MySort{
	public MySort(){ 
	}
	public ArrayList<SearchResult> sortThisList(Myset<SearchResult> listOfSortableEntries){
		ArrayList<SearchResult>ans = new ArrayList<SearchResult>();
		LinkedList<SearchResult> pagelist = listOfSortableEntries.getList();
		Node<SearchResult> temp = pagelist.getHead();
		while(temp!=null){
			ans.add(temp.getdata());
			temp = temp.getnext();
		}
		for(int i=0; i<ans.size(); i++){
			for(int j=0; j<ans.size()-i-1;j++){
				SearchResult s1 = (SearchResult)ans.get(j);
				SearchResult s2 = (SearchResult)ans.get(j+1);
				if(s1.compareTo(s2)<0){
					Collections.swap(ans,j,j+1);
				}
			}
		}
	return ans;
	}	
}