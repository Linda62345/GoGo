	import java.io.BufferedReader;
	import java.io.File;
	import java.io.IOException;
	
	import java.io.InputStream;
	
	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.URL;
	
	import java.net.URLConnection;
	import java.nio.charset.StandardCharsets;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Collections;
	import java.util.Comparator;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Map.Entry;
	import java.util.PriorityQueue;
	import java.util.Scanner;
	import java.util.Set;
	
	import org.jsoup.Jsoup;
	
	import org.jsoup.nodes.Document;
	
	import org.jsoup.nodes.Element;
	
	import org.jsoup.select.Elements;
	
	
	
	public class GoogleQuery 
	
	{
	
	 public String searchKeyword;
	
	 public String url;
	
	 public String content;
	 
	 //public int searchNum;
	 
	 public PriorityQueue<WebNode> heap;
	 
	 public String results;
	 
	 public KeywordList list;
	 
	 public HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
	
	 public GoogleQuery(String searchKeyword)
	
	 {
	
	  this.searchKeyword = searchKeyword;
	  //this.searchNum=searchNum;
	  setKeyword();
	  
	  list = new KeywordList();
	  
	  scoreMap = new HashMap<String, Integer>();
	
	  this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
	
	 }
	
	 public void setKeyword() {
	  
	 }
	
	 private String fetchContent() throws IOException
	
	 {
	  String retVal = "";
	
	  URL u = new URL(url);
	
	  //URLConnection conn = u.openConnection();
	  
	  
	  HttpURLConnection conn = (HttpURLConnection) u.openConnection();
	
	  conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
	
	  InputStream in = conn.getInputStream();
	
	  InputStreamReader inReader = new InputStreamReader(in,"utf-8");
	
	  BufferedReader bufReader = new BufferedReader(inReader);
	  String line = null;
	
	  while((line=bufReader.readLine())!=null)
	  {
	   retVal += line;
	
	  }
	  return retVal;
	 }
	 public HashMap<String, String> query() throws IOException
	
	 {
	
	  if(content==null)
	
	  {
	
	   content= fetchContent();
	
	  }
	 
	
	  HashMap<String, String> retVal = new HashMap<String, String>(); //改快趕回來
	  
	  Document doc = Jsoup.parse(content);
	//  System.out.println(doc.text());
	  Elements lis = doc.select("div");//把標題記起來
	//   System.out.println(lis);
	  lis = lis.select(".kCrYT");
	//   System.out.println(lis.size());
	  
	  
	  for(Element li : lis)
	  {
	   try 
	
	   {
	   // String citeUrl = li.select("a").get(0).attr("href");
	    //String title = li.select("a").get(0).select(".vvjwJb").text();
		   String citeUrl = li.select("a").attr("href");
			System.out.println("origin: " + citeUrl);
			if (citeUrl.startsWith("/url?q=")) {
				citeUrl = citeUrl.replace("/url?q=", "");
			}
			String[] splittedString = citeUrl.split("&sa=");
			if (splittedString.length > 1) {
				citeUrl = splittedString[0];
			}
			// url decoding from UTF-8
			citeUrl = java.net.URLDecoder.decode(citeUrl, StandardCharsets.UTF_8);
	
			// parse down title
			String title = li.select("a").select(".vvjwJb").text();
			if (title.equals("")) {
				continue;
			}
			
	    System.out.println(title + ","+citeUrl);
	    retVal.put(title, citeUrl);     //趕快改回來
	    //也許可以改放score跟citeUrl
	    
	    
	   // String newUrl = citeUrl.substring(7);
	    
	    WebPage rootPage = new WebPage(citeUrl, title);
	    ArrayList<Keyword> keywords = new ArrayList<Keyword>();
	    KeywordList lst = new KeywordList();
	    
	    
	    keywords.add(new Keyword("FinTech",4));
	    keywords.add(new Keyword("queen",5));
	    keywords.add(new Keyword("kingdom",5));
	    keywords.add(new Keyword("throne",5));
	    //還沒使用file
	    
	    rootPage.setScore(keywords);
	    
	    int score = (int) rootPage.score;
	    
	    //list.add(new Keyword(title,score));
	    
	    
	
	    System.out.println(title+","+rootPage.score);
	    
	    scoreMap.put(title, score); //有差別的好像是這行
	  
	   //list.sort(); //要如何排沒有東西的值
	    
	 
	   } catch (IndexOutOfBoundsException e) {
	
	//    e.printStackTrace();
	
	   }
	   
	  }
	
	  return retVal;
	
	 }
	 
	 @SuppressWarnings("unchecked")
	public HashMap<String, Integer> scoremap(){
		 HashMap<String, Integer> scoreSet = new HashMap<String, Integer>();
		 Map phone = scoreMap;
		 Set set = phone.keySet();
	     Object[] arr = set.toArray();
	     Arrays.sort(arr);
	     List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(phone.entrySet());
	     list.sort(new Comparator<Map.Entry<String, Integer>>() {
	         @Override
	         public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	             return o2.getValue().compareTo(o1.getValue());
	         }
	     });
	     //collections.sort()
	     Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	         @Override
	         public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	             return o2.getValue().compareTo(o1.getValue());
	         }
	     });
	     //for
	     for (int i = 0; i < list.size(); i++) {
	    	 scoreSet.put(list.get(i).getKey(), list.get(i).getValue()); //這樣就有放進一個hashMap還有排好
	         System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
	     }
	     System.out.println();
	     /*for-each
	     for (Map.Entry<String, Integer> mapping : list) {
	         System.out.println(mapping.getKey() + ": " + mapping.getValue());
	     }
	     */
		 return scoreSet;
	 }
	 
	 public void sortlst() {
		list.add(new Keyword("a",4));
		list.add(new Keyword("c",2));
		list.add(new Keyword("b",6)); //sort根本就沒有排出來 所以是sort沒有用
		
		list.sort(); 
		list.output(); //他都走到這裏了 代表應該要有排
	 }
	 
	 public void hashmap() {
		 
	 }
	
	 
	
	}