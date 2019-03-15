import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PageEntry{
	String pageid;
	PageIndex index;
	public int totalWords=0; 
	public PageEntry(String pageName){
		pageid = pageName;
		index = new PageIndex();

		BufferedReader br = null;
		try {
			String temp="";
			String[] wordarr;
			br = new BufferedReader(new FileReader(pageid));
			int wordPos =0;
			int wordCount =0;
			while((temp = br.readLine()) != null){
				temp = temp.toLowerCase();
				temp = temp.replaceAll("[\\t\\n\\r]+"," ");
				wordarr = temp.split("[\"\'\\]\\[\\{\\}()<>.,;:?#!=\\- ]+");
				for(String word : wordarr){
					if(!word.equals("")){
						wordPos += 1;
					}
					if(!(word.equals("")||word.equals("a")|| word.equals("an")||word.equals("the") || word.equals("they")|| word.equals("these")|| word.equals("this")|| word.equals("for")||word.equals("is")|| word.equals("are") || word.equals("was")|| word.equals("of") || word.equals("or") || word.equals("and")|| word.equals("does")|| word.equals("will")|| word.equals("whose"))){
						wordCount += 1;
						if(word.equals("stacks")){
							word = "stack";
						}
						if(word.equals("structures")){
							word = "structure";
						} 
						if(word.equals("applications")){
							word  = "application";
						}
						Position p = new Position(this, wordPos);
						this.index.addPositionForWord(word, p);
						WordEntry currword = this.index.findWordEntry(word);
						Position pos = new Position(this, wordCount);
						avlNode posToBeAdded = new avlNode(pos);
						currword.posTree.treeInsert(posToBeAdded);
						currword.helperlist.add(wordCount);
					}					
				}	
			}
			totalWords = wordCount;
			Node<WordEntry> temp1  = this.index.getWordEntries().getHead();
			while(temp1!=null){
				temp1.getdata().setWordFreq(totalWords);
				temp1 = temp1.getnext();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		} 
		finally{
			try{
				if (br != null)br.close();
			} 
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	public float getRelevanceOfPage(String str[ ], boolean doTheseWordsRepresentAPhrase, InvertedPageIndex invertedindex ){
		float relevance = 0;
		if(doTheseWordsRepresentAPhrase){
			int m=0;
			boolean flag = true;
			PageIndex p = this.getPageIndex();
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
					m+=1;
				}
				firstwordtemp = firstwordtemp.getnext();
			}
			try{
				int nw  = invertedindex.getPagesWhichContainPhrase(str).getList().size();
				relevance = (float)m/(this.totalWords-(str.length-1)*m);
				float t = (float)Math.log((float)(invertedindex.numpages+0.0001)/nw); 
				relevance = relevance*t;
			}
			catch(Exception e){}
		}
		else{
			try{
				for(String word : str){
					if(word.equals("stacks")){
						word = "stack";
					}
					if(word.equals("structures")){
						word = "structure";
					} 
					if(word.equals("applications")){
						word  = "application";
					}
					if(this.index.wordExist(word)){
						int nw  = invertedindex.getPagesWhichContainWord(word).getList().size();
						relevance += this.index.findWordEntry(word).getTermFrequency()*(Math.log((float)(invertedindex.numpages+0.0001)/nw)); 
					}
				}
			}
			catch(Exception e){}
		}
		return relevance;
	}
	public PageIndex getPageIndex(){
		return this.index;
	}
	public String getPageName(){
		return this.pageid;
	}
	public boolean equals(PageEntry p){
		return (this.getPageName().equals(p.getPageName()));
	}
}
