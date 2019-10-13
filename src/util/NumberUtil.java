package util;

import java.util.Random;

import bean.Num;


/**
 * ���ֹ�����
 *
 */
public class NumberUtil {
	private int number;
	private Random random = new Random();
	public NumberUtil(int number) {
		this.number = number;
	}
	
	/**
	 * ��������ַ�����ʽ��������
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
			//��ĸ�ͷ�����ȣ����������
			return Integer.toString(son);
		}
		else if(son>mother) {
			//���ӱȷ�ĸ�󣬽�������
			return Integer.toString(mother)+"/"+Integer.toString(son);
		}
		else {
			return Integer.toString(son)+"/"+Integer.toString(mother);
		}
	}
	
	/**
	 * ����ӷ�
	 *
	 */
	public static Num add (Num a,Num b) {
		int newMother = a.getMother()*b.getMother();
		int newSon = a.getSon()*b.getMother()+b.getSon()*a.getMother();
		
		Num n = new Num(newSon,newMother);
		
		return n;
	}
	
	/**
	 * �������
	 *
	 */
	public static Num sub (Num a,Num b) {
		int newMother = a.getMother()*b.getMother();
		int newSon = a.getSon()*b.getMother()-b.getSon()*a.getMother();
		
		Num n = new Num(newSon,newMother);
		
		return n;
	}
	
	/**
	 * ����˷�
	 *
	 */
	public static Num mul (Num a,Num b) {
		Num n = new Num(a.getSon()*b.getSon(),a.getMother()*b.getMother());
		return n;
	}
	
	/**
	 * �������
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
	 * ͨ��
	 *
	 */
	public static Num normal (Num a) {
		int big = gcd(a.getSon(),a.getMother());
		Num n = new Num(a.getSon()/big,a.getMother()/big);
		return n;
	}
	
	/**
	 * �ж������Ƿ����
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
	
	//�����Լ��
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
