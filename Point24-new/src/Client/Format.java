package Client;
public class Format {

	public static int[] NumberFormat(String str){
		int num_Point=0;
		int Number[]=new int[4];
		if (str.toCharArray()[0]=='#'){
			for(int i=1;i<=str.toCharArray().length-1;i++){
				if (str.toCharArray()[i]!='#'){
					Number[num_Point]=str.toCharArray()[i]-48;
					num_Point++;
					
				}
			}
			
		}
//		Number=new int[]{3,1,3,5};
		
		return Number;
		
	}
	public static boolean isBug(String str,int[] question){
		int sum=0;
		int sumb=0;
		for(int i=0;i<=str.length()-1;i++){
			if(((str.toCharArray()[i]-48)<10) && ((str.toCharArray()[i]-48)>0)){
				for (int a=0;a<=3;a++){
					if (str.toCharArray()[i]-48==question[a]){
						sum++;
						question[a]=0;
						sumb++;
						break;
					}
					
				}
				if 	(sumb!=1 ) return false;else sumb=0;
			}
			
		}
		//System.out.print(sum);
		if (sum==4) return true; else return false;
		
		
	}
}
