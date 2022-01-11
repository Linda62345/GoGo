
public class Keyword {
	public String name;
    public int count;
    

    

	public Keyword(String name, int count) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.count = count;
	}


	@Override
    public String toString(){
    	return "["+name+","+count+"]";
    }
}
