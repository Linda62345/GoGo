import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		try {
			GoogleQuery g = new GoogleQuery("NCCU");
 		    System.out.println(g.query());
 		    //g.sortlst(); //用hashmap中的sort看看
		   //排序並沒有發生作用
 		    
 		   g.scoremap();
 		    /*List<Integer> calcu = .sort(s);
 		    
 		    for(Integer number:calcu) {
 		    	System.out.printf("%s = %d\n", s.keySet(),number);
 		    }*/
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
