package genericutils;

import java.util.Random;

public class JavaUtils {
	
	public int randomNum(int origin,int bound) {
		Random ran=new Random();
		return ran.nextInt(origin,bound);
	}
	

}
