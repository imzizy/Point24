package Server;

public class Game {

	public static String CreateNum(){
		String Number="#";
		
		for (int i=0;i<=3;i++){
			Number+=String.valueOf((int)(Math.random()*9)+1)+"#";
			
			
		}
		return Number;
		
	}
	
}
