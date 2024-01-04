import javax.net.ssl.HostnameVerifier;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


import java.util.ArrayList;
import java.util.HashMap;

public class Main  {
	
	public static String searchString;
	public static double w1 = 1;
	public static double w2 = 0.8;
	public static double w3 = 0.6;
	public static double w4 = 0.4;

	public Main(String searchKeyword)

	{
		Main.searchString = searchKeyword;
	}

	public static headerlist main() throws Exception {
		System.out.println("Start Searching!");
		// TODO Auto-generated method stub
	    ArrayList<Keyword> keywords = new ArrayList<Keyword>();
	    keywords.add(new Keyword("Australia", w1)); 
        keywords.add(new Keyword("Travel", w1)); 
        keywords.add(new Keyword("Tourism", w1)); 
        keywords.add(new Keyword("Trip", w1)); 
        keywords.add(new Keyword("Attraction", w1)); 
        keywords.add(new Keyword("Airplane", w2)); 
        keywords.add(new Keyword("Ticket", w2)); 
        keywords.add(new Keyword("Accommodation", w2)); 
        keywords.add(new Keyword("Hotel", w2)); 
        keywords.add(new Keyword("Packages", w2)); 
        keywords.add(new Keyword("Kangaroo", w2)); 
        keywords.add(new Keyword("Koala", w2)); 
        keywords.add(new Keyword("Beach", w2)); 
        keywords.add(new Keyword("National Park", w2)); 
        keywords.add(new Keyword("Natural", w2)); 
        keywords.add(new Keyword("Sight", w2)); 
        keywords.add(new Keyword("Sydney", w3)); 
        keywords.add(new Keyword("Melbourne", w3)); 
        keywords.add(new Keyword("Brisbane", w3)); 
        keywords.add(new Keyword("Gold Coast", w3)); 
        keywords.add(new Keyword("Perth", w3)); 
        keywords.add(new Keyword("Cairns", w3)); 
        keywords.add(new Keyword("Ayers Rock", w3)); 
        keywords.add(new Keyword("Opera House", w3)); 
        keywords.add(new Keyword("Surfing", w3)); 
        keywords.add(new Keyword("Harbour Bridge", w3));
        keywords.add(new Keyword("Working holiday", w4)); 
        keywords.add(new Keyword("Self-guided", w4));
        keywords.add(new Keyword("backpacking", w4));
        keywords.add(new Keyword("Tour", w4));
        keywords.add(new Keyword("Group", w4));
        keywords.add(new Keyword("Surfing", w4));
        keywords.add(new Keyword("Zoo", w4));
        keywords.add(new Keyword("Skydiving", w4));


	   searchString = searchString.replace(" ", "+");
	   lcs recommend = new lcs(keywords, searchString);
	   String recommend_key = recommend.rank();
 	   System.out.println("recommended key : "+recommend_key);
	   
	   headerlist ans=new headerlist();
	   GoogleQuery g =new GoogleQuery(searchString+"+Australia+"+recommend_key);
	   
	   HashMap<String, String> results = g.query();
	   
		try {
			for (  String key : results.keySet()) {
//				System.out.println(key);
//			    System.out.println( results.get(key).substring(7) );
				try {
			    WebPage rootPage = new WebPage(results.get(key).substring(7),key);	
			    WebTree tree=new WebTree(rootPage);
				
			    
			    //while還有子頁面,addchild
			    Query child = new Query(results.get(key).substring(7));
//			    GoogleQuery child = new GoogleQuery(results.get(key).substring(7));
			    HashMap<String, String> childmap = child.query();
//			    System.out.println(childmap.size());
			    
			    for(String childkey:childmap.keySet()) {
			    	tree.root.addChild(new WebNode(new WebPage(childmap.get(childkey),childkey)));
			    }
			    
				//計算並輸出
				tree.setPostOrderScore(keywords);
				tree.eularPrintTree();
				System.out.println(tree.root.nodeScore);
				ans.add(new header(key, results.get(key).substring(7), tree.root.nodeScore));
				}
				catch(Exception e) {
					continue;
				}
				

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//排序
		System.out.println(ans.size());
		ans.sort();
		ans.output();
		ans.addfirst(new header("Do you want to search: "+recommend_key+" ?", "http://www.google.com/search?q="+recommend_key+"&oe=utf8&num=30", 9999));
//		ans.output();
		System.out.println("Done!");
		return ans;
	}
	
	static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() 
        {
            public boolean verify(String hostname,SSLSession session) 
            {
                return true;
            }
        });
    }
}