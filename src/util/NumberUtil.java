package util;

import java.util.Random;

import bean.Num;


/**
 * 数字工具类
 *
 */
public class NumberUtil {
	private int number;
	private Random random = new Random();
	public NumberUtil(int number) {
		this.number = number;
	}
	
	/**
	 * 随机生成字符串形式的新数字
	 *
	 */
	public String NewNumber() {
		int son = random.nextInt(number);
		int mother = random.nextInt(number-1)+1;
		
		if(son==0) {
			return "0";
		}
		else if(mother==1) {
			return Integer.toString(son);
		}
		else if(mother==son) {
			//分母和分子相等，就输出分子
			return Integer.toString(son);
		}
		else if(son>mother) {
			//分子比分母大，交换次序
			return Integer.toString(mother)+"/"+Integer.toString(son);
		}
		else {
			return Integer.toString(son)+"/"+Integer.toString(mother);
		}
	}
	
	/**
	 * 计算加法
	 *
	 */
	public static Num add (Num a,Num b) {
		int newMother = a.getMother()*b.getMother();
		int newSon = a.getSon()*b.getMother()+b.getSon()*a.getMother();
		
		Num n = new Num(newSon,newMother);
		
		return n;
	}
	
	/**
	 * 计算减法
	 *
	 */
	public static Num sub (Num a,Num b) {
		int newMother = a.getMother()*b.getMother();
		int newSon = a.getSon()*b.getMother()-b.getSon()*a.getMother();
		
		Num n = new Num(newSon,newMother);
		
		return n;
	}
	
	/**
	 * 计算乘法
	 *
	 */
	public static Num mul (Num a,Num b) {
		Num n = new Num(a.getSon()*b.getSon(),a.getMother()*b.getMother());
		return n;
	}
	
	/**
	 * 计算除法
	 *
	 */
	public static Num div (Num a,Num b) {
		if(a.getSon()==0 || b.getSon()==0) {
			return new Num(0,1);
		}
		Num n = new Num(a.getSon()*b.getMother(),a.getMother()*b.getSon());
		return n;
	}
	
	/**
	 * 通分
	 *
	 */
	public static Num normal (Num a) {
		int big = gcd(a.getSon(),a.getMother());
		Num n = new Num(a.getSon()/big,a.getMother()/big);
		return n;
	}
	
	/**
	 * 判断两数是否相等
	 *
	 */
	public static boolean isEqual(Num m,Num n) {
		if(m.getSon()==n.getSon() && m.getMother()==n.getMother()) {
			return true;
		}
		else return false;
	}
	
	public static Num stringToNum(String s) {
		if(s.indexOf('/')==-1) {
			return new Num(Integer.valueOf(s),1);
		}
		else {
			String son = s.substring(0,s.indexOf('/'));
			String mother = s.substring(s.indexOf('/')+1);
			return new Num(Integer.valueOf(son),Integer.valueOf(mother));
		}
	}
	
	//求最大公约数
	private static int gcd(int m,int n) {
		int s,r;
		s = m * n;
        while (n != 0)
        {
            r = m % n;
            m = n;
            n = r;
        }
        return m;
	}
}
