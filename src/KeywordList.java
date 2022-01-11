import java.util.ArrayList;

public class KeywordList {
	private ArrayList<Keyword> lst;//偷偷改成public
	
	public KeywordList(){
		this.lst = new ArrayList<Keyword>();
    }
	
	public void add(Keyword keyword){
		lst.add(keyword);
//		System.out.println("Done");
    }
	
	//quick sort
	public void sort(){
		if(lst.size() == 0)
		{
			System.out.println("InvalidOperation");
		}
		else 
		{
			quickSort(0, lst.size()-1);
//			System.out.println("Done");
		}

	}
	
	
	private void quickSort(int leftbound, int rightbound){
		//1. implement quickSort algorithm
		if(leftbound<rightbound)
		{
		    int pivot = lst.get(rightbound).count;
		    int j=leftbound;
			for(j=leftbound; j<=rightbound - 1;j++) {
				
				if(lst.get(j).count>pivot)
				{
					swap(pivot,j);
				}
				if(leftbound>=rightbound) {
					return;
				}
			}
			quickSort(leftbound,j-1);//再去從原先pivot右邊找出新的排序
			quickSort(j+1,rightbound);//原先pivot左邊找出新的排序

		}
	}

	
	
	private void swap(int aIndex, int bIndex){
		Keyword temp = lst.get(aIndex);
		lst.set(aIndex, lst.get(bIndex));
		lst.set(bIndex, temp);
	}
	
	public void output(){
		//TODO: write output and remove all element logic here...
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<lst.size();i++){
			Keyword k = lst.get(i);
			if(i>0)sb.append(" ");
			sb.append(k.toString());
		}
		
		System.out.println(sb.toString());	
	}
}
