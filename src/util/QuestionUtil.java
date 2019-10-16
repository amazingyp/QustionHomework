package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Random;

import bean.Num;

public class QuestionUtil {
	/**
	  * ����much���������ʽ��������������ļ���
	 *
	 */
	public static void Create(int number,int much) {
		NumberUtil cn = new NumberUtil(number);
		Random random = new Random();
		
		FileUtil.cleanExerciseFile();
		FileUtil.cleanAnswerFile();
		for(int now=0;now<much;now++) {
			int sigNumber = random.nextInt(3)+1;//��ʽ���м�������
			String result = new String();
			if(sigNumber==1) {//һ������������
				result = cn.NewNumber()+createSign()+cn.NewNumber();
				
				//System.out.println(result);
				Queue<Object> mid = ReadString(result);
				Num answer = CountQueue(mid);
				//System.out.println("�𰸣�"+answer);
				
				if(answer.getSon()<0 || answer.getMother()<0) {//���ָ�����������Ŀ
					now--;
					continue;
				}
				FileUtil.printToFile(now+1+"."+result, 0);
				FileUtil.printToFile(now+1+"."+answer, 1);

			}
			
			else if(sigNumber==2) {//���������
				int bracketIndex = random.nextInt(3);//����λ��
				if(bracketIndex==0) {//û����
					result = cn.NewNumber()+createSign()+cn.NewNumber()+createSign()+cn.NewNumber();
				}
				else if(bracketIndex==1) {//12����
					result = "("+cn.NewNumber()+createSign()+cn.NewNumber()+")"+createSign()+cn.NewNumber();
				}
				else {
					result = cn.NewNumber()+createSign()+"("+cn.NewNumber()+createSign()+cn.NewNumber()+")";
				}
				//System.out.println(result);
				Queue<Object> mid = ReadString(result);
				Num answer = CountQueue(mid);
				//System.out.println("�𰸣�"+answer);
				
				if(answer.getSon()<0 || answer.getMother()<0) {//���ָ�����������Ŀ
					now--;
					continue;
				}
				FileUtil.printToFile(now+1+"."+result, 0);
				FileUtil.printToFile(now+1+"."+answer, 1);
			}
			else {//���������������û�п���ȫ����������岻��
				int bracket = random.nextInt(2);//�Ƿ���������
				if(bracket==0) {
					result = cn.NewNumber()+createSign()+cn.NewNumber()+createSign()+cn.NewNumber()+createSign()+cn.NewNumber();
				}
				else {
					result = cn.NewNumber()+createSign()+"("+cn.NewNumber()+createSign()+cn.NewNumber()+")"+createSign()+cn.NewNumber();
				
				}
				//System.out.println(result);
				Queue<Object> mid = ReadString(result);
				Num answer = CountQueue(mid);
				//System.out.println("�𰸣�"+answer);
				
				if(answer.getSon()<0 || answer.getMother()<0) {//���ָ�����������Ŀ
					now--;
					continue;
				}
				FileUtil.printToFile(now+1+"."+result, 0);
				FileUtil.printToFile(now+1+"."+answer, 1);
			}
			
		}
		
	}
	
	/**
	  * ������Ŀ·���ʹ�·�������ɼ������Ϊ�ļ�
	 *
	 */
	public static void printGrade(String exercisePath , String answerPath) {
		List exerciseList = FileUtil.readFile(exercisePath);
		List answerList = FileUtil.readFile(answerPath);
		List <Integer>right = new ArrayList();
		List <Integer>wrong = new ArrayList();
		
		for(int i=0;i<exerciseList.size();i++) {
			String exercise = (String)exerciseList.get(i);
			String answer = (String)answerList.get(i);
			
			//�����ȥ��
			int firstIndex = exercise.indexOf('.');
			exercise = exercise.substring(firstIndex+1);
			answer = answer.substring(firstIndex+1);
			
			//�õ���ȷ�𰸺�ѧ����
			Num trueAnswer = CountQueue(ReadString(exercise));
			Num sAnswer = NumberUtil.stringToNum(answer);
			
			if(NumberUtil.isEqual(trueAnswer,sAnswer)) {
				right.add(i);
			}
			else wrong.add(i);
		}
		
		String rightString = "Correct:"+right.size()+"(";
		String wrongString = "Wrong:"+wrong.size()+"(";
		
		for (int r : right) {
			rightString = rightString+r+",";
		}
		
		for (int w : wrong) {
			wrongString = wrongString+w+",";
		}
		
		rightString = rightString.substring(0, rightString.length()-1)+")\n";
		wrongString = wrongString.substring(0, wrongString.length()-1)+")";
		
		File file = new File("./questionbank/Grade.txt");
		if(file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FileOutputStream o = null;
		try {
			o=new FileOutputStream(file);
			o.write(rightString.getBytes());
			o.write(wrongString.getBytes());
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	  * ���ַ�����ʽ����Ŀת��ΪQueue<Object>��ʽ����׺���ʽ
	 *
	 */
	public static Queue<Object> ReadString(String question) {
		Queue<Object> mid = new LinkedList<Object>();

		int son = -1;
		String temp = "";
		for(int x = 0; x<question.length();x++) {
			char c = question.charAt(x);
			if(c=='(') {//������ǰ��Ҫôû�У�Ҫô����һ�������������ֱ�ӷŽ�����
				mid.add(c);
			}
			else if(c==')' || c=='+' || c=='-' || c=='��' || c=='��') {
				if(son!=-1) {//����з��ӣ���˵�����ڵ�temp�Ƿ�ĸ
					mid.add(new Num(son,Integer.valueOf(temp)));
					temp = "";
					son = -1;
				}
				else if(!temp.equals("")) {//�����������ַ�����temp�Ƿ��ӣ���ĸΪ1
					mid.add(new Num(Integer.valueOf(temp),1));
					temp = "";
				}
				mid.add(c);
			}
			else {//�������ֻ��߷�ĸ��
				if(c!='/') {//���Ƿ����ţ��ŵ�temp������
					temp = temp+c;
				}
				else {//���������ţ����еĶ��������������
					son = Integer.valueOf(temp);
					temp = "";
				}
			}
		}
		
		//�����һ���������
		if(son!=-1) {//����з��ӣ���˵�����ڵ�temp�Ƿ�ĸ
			mid.add(new Num(son,Integer.valueOf(temp)));
			temp = "";
		}
		else if(!temp.equals("")) {//�����������ַ�����temp�Ƿ��ӣ���ĸΪ1
			mid.add(new Num(Integer.valueOf(temp),1));
			temp = "";
		}
		
		return mid;
	}
	
	/**
	  * ��Queue<Object>����Ϊ�����
	  * Ӧ������׺���ʽ
	 */
	public static Num CountQueue(Queue mid) {
		Queue<Object> after = MidToAfter(mid);
		Stack<Object> tempStack = new Stack<Object>();
		
		while(after.peek()!=null) {
			if(after.peek() instanceof Num) {//������ֱ����ջ
				tempStack.push(after.poll());
			}
			else {
				char op = (char)after.poll();
				Num n,first,second;
				switch (op) {
				case '+':
					n = NumberUtil.add((Num)tempStack.pop(),(Num)tempStack.pop());
					tempStack.push(n);
					break;
				case '-':
					first = (Num)tempStack.pop();
					second = (Num)tempStack.pop();
					n = NumberUtil.sub(second,first);
					tempStack.push(n);
					break;
				case '��':
					n = NumberUtil.mul((Num)tempStack.pop(),(Num)tempStack.pop());
					tempStack.push(n);
					break;
				case '��':
					first = (Num)tempStack.pop();
					second = (Num)tempStack.pop();
					n = NumberUtil.div(second,first);
					tempStack.push(n);
					break;
				}
			}
			
		}
		Num answer = (Num)tempStack.pop();
		return NumberUtil.normal(answer);
	}
	
	/**
	  * ����׺���ʽת��Ϊ��׺���ʽ
	 *
	 */
	public static Queue<Object> MidToAfter(Queue mid) {
		Queue<Object> after = new LinkedList<Object>();
		Stack<Object> tempStack = new Stack<Object>();
		
		while(mid.peek()!=null) {
			if(mid.peek() instanceof Num) {//������ֱ�������
				after.add(mid.poll());
			}
			else {//���ŵ��ж�
				char c = (char) mid.poll();
				if(c=='(') {//������ֱ����ջ
					tempStack.add(c);
				}
				else if(c==')') {//�����Ű�ջ��Ķ���ȫŪ�������ֱ������������
					while (true) {
                        if (tempStack.empty()) {
                            System.out.println("ȱ��������! ");
                            return null;
                        } else if ((char)tempStack.peek()=='(') {
                        	tempStack.pop();
                            break;
                        } else {
                        	after.add(tempStack.pop());
                        }
					}
				}
				
                //�������������
				else if (!tempStack.empty()) {
                    char peek = (char)tempStack.peek();
                    //��ǰ��������ȼ�����ջ����������ȼ�������ջ��Ϊ������ʱ����ǰ�����ֱ����ջ
                    if(((c=='��' ||c=='��')&&((peek=='+') || (peek=='-'))) || peek=='(') {
                    	tempStack.push(c);
                    }
                    //���򣬽�ջ���������ȡ����������У�Ȼ���Լ���ջ
                    else {
                        after.add(tempStack.pop());
                        tempStack.push(c);
                    }                        
                } 
                else {
                	tempStack.push(c);
                }
			}
		}
		
		while(!tempStack.empty()) {
			after.add(tempStack.pop());
		}
		return after;
	}
	
	
	/**
	  * ����һ����������
	 *
	 */
	private static String createSign() {
		Random random = new Random();
		String result = new String();
		switch (random.nextInt(4)) {
		case 0:
			result = "+";
			break;
		case 1:
			result = "-";
			break;
		case 2:
			result = "��";
			break;
		case 3:
			result = "��";
			break;
		default:
			break;
		}
		return result;
	}
}
