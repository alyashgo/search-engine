public class SearchResult implements Comparable<SearchResult>{
	PageEntry page;
	Float relev;
	public SearchResult(PageEntry p, Float r){
		page = p;
		relev = r;
	}
	public PageEntry getPageEntry(){
		return this.page;
	}
	public Float getRelevance(){
		return this.relev;
	}
	public int compareTo(SearchResult otherobject){
		return (Float.compare(this.relev, otherobject.getRelevance()));
	}
}