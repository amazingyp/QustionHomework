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
	  * 生成much个随机运算式，并将其输出到文件中
	 *
	 */
	public static void Create(int number,int much) {
		NumberUtil cn = new NumberUtil(number);
		Random random = new Random();
		
		FileUtil.cleanExerciseFile();
		FileUtil.cleanAnswerFile();
		for(int now=0;now<much;now++) {
			int sigNumber = random.nextInt(3)+1;//算式中有几个符号
			String result = new String();
			if(sigNumber==1) {//一个运算符的情况
				result = cn.NewNumber()+createSign()+cn.NewNumber();
				
				//System.out.println(result);
				Queue<Object> mid = ReadString(result);
				Num answer = CountQueue(mid);
				//System.out.println("答案："+answer);
				
				if(answer.getSon()<0 || answer.getMother()<0) {//出现负数，抛弃题目
					now--;
					continue;
				}
				FileUtil.printToFile(now+1+"."+result, 0);
				FileUtil.printToFile(now+1+"."+answer, 1);

			}
			
			else if(sigNumber==2) {//两个运算符
				int bracketIndex = random.nextInt(3);//括号位置
				if(bracketIndex==0) {//没括号
					result = cn.NewNumber()+createSign()+cn.NewNumber()+createSign()+cn.NewNumber();
				}
				else if(bracketIndex==1) {//12括号
					result = "("+cn.NewNumber()+createSign()+cn.NewNumber()+")"+createSign()+cn.NewNumber();
				}
				else {
					result = cn.NewNumber()+createSign()+"("+cn.NewNumber()+createSign()+cn.NewNumber()+")";
				}
				//System.out.println(result);
				Queue<Object> mid = ReadString(result);
				Num answer = CountQueue(mid);
				//System.out.println("答案："+answer);
				
				if(answer.getSon()<0 || answer.getMother()<0) {//出现负数，抛弃题目
					now--;
					continue;
				}
				FileUtil.printToFile(now+1+"."+result, 0);
				FileUtil.printToFile(now+1+"."+answer, 1);
			}
			else {//三个运算符，括号没有考虑全部情况，意义不大
				int bracket = random.nextInt(2);//是否生成括号
				if(bracket==0) {
					result = cn.NewNumber()+createSign()+cn.NewNumber()+createSign()+cn.NewNumber()+createSign()+cn.NewNumber();
				}
				else {
					result = cn.NewNumber()+createSign()+"("+cn.NewNumber()+createSign()+cn.NewNumber()+")"+createSign()+cn.NewNumber();
				
				}
				//System.out.println(result);
				Queue<Object> mid = ReadString(result);
				Num answer = CountQueue(mid);
				//System.out.println("答案："+answer);
				
				if(answer.getSon()<0 || answer.getMother()<0) {//出现负数，抛弃题目
					now--;
					continue;
				}
				FileUtil.printToFile(now+1+"."+result, 0);
				FileUtil.printToFile(now+1+"."+answer, 1);
			}
			
		}
		
	}
	
	/**
	  * 给出题目路径和答案路径，将成绩输出成为文件
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
			
			//把题号去掉
			int firstIndex = exercise.indexOf('.');
			exercise = exercise.substring(firstIndex+1);
			answer = answer.substring(firstIndex+1);
			
			//得到正确答案和学生答案
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
	  * 将字符串形式的题目转化为Queue<Object>形式的中缀表达式
	 *
	 */
	public static Queue<Object> ReadString(String question) {
		Queue<Object> mid = new LinkedList<Object>();

		int son = -1;
		String temp = "";
		for(int x = 0; x<question.length();x++) {
			char c = question.charAt(x);
			if(c=='(') {//左括号前面要么没有，要么就有一个运算符，所以直接放进队列
				mid.add(c);
			}
			else if(c==')' || c=='+' || c=='-' || c=='×' || c=='÷') {
				if(son!=-1) {//如果有分子，则说明现在的temp是分母
					mid.add(new Num(son,Integer.valueOf(temp)));
					temp = "";
					son = -1;
				}
				else if(!temp.equals("")) {//若队列内有字符，则temp是分子，分母为1
					mid.add(new Num(Integer.valueOf(temp),1));
					temp = "";
				}
				mid.add(c);
			}
			else {//读到数字或者分母号
				if(c!='/') {//不是分数号，放到temp队列里
					temp = temp+c;
				}
				else {//遇到分数号，队列的东西提出来做分子
					son = Integer.valueOf(temp);
					temp = "";
				}
			}
		}
		
		//把最后一个数搞出来
		if(son!=-1) {//如果有分子，则说明现在的temp是分母
			mid.add(new Num(son,Integer.valueOf(temp)));
			temp = "";
		}
		else if(!temp.equals("")) {//若队列内有字符，则temp是分子，分母为1
			mid.add(new Num(Integer.valueOf(temp),1));
			temp = "";
		}
		
		return mid;
	}
	
	/**
	  * 将Queue<Object>计算为最后结果
	  * 应输入中缀表达式
	 */
	public static Num CountQueue(Queue mid) {
		Queue<Object> after = MidToAfter(mid);
		Stack<Object> tempStack = new Stack<Object>();
		
		while(after.peek()!=null) {
			if(after.peek() instanceof Num) {//操作数直接入栈
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
				case '×':
					n = NumberUtil.mul((Num)tempStack.pop(),(Num)tempStack.pop());
					tempStack.push(n);
					break;
				case '÷':
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
	  * 将中缀表达式转换为后缀表达式
	 *
	 */
	public static Queue<Object> MidToAfter(Queue mid) {
		Queue<Object> after = new LinkedList<Object>();
		Stack<Object> tempStack = new Stack<Object>();
		
		while(mid.peek()!=null) {
			if(mid.peek() instanceof Num) {//操作数直接入队列
				after.add(mid.poll());
			}
			else {//符号的判定
				char c = (char) mid.poll();
				if(c=='(') {//左括号直接入栈
					tempStack.add(c);
				}
				else if(c==')') {//右括号把栈里的东西全弄到队列里，直到遇到左括号
					while (true) {
                        if (tempStack.empty()) {
                            System.out.println("缺少左括号! ");
                            return null;
                        } else if ((char)tempStack.peek()=='(') {
                        	tempStack.pop();
                            break;
                        } else {
                        	after.add(tempStack.pop());
                        }
					}
				}
				
                //非括号类运算符
				else if (!tempStack.empty()) {
                    char peek = (char)tempStack.peek();
                    //当前运算符优先级大于栈顶运算符优先级，或者栈顶为左括号时，当前运算符直接入栈
                    if(((c=='×' ||c=='÷')&&((peek=='+') || (peek=='-'))) || peek=='(') {
                    	tempStack.push(c);
                    }
                    //否则，将栈顶的运算符取出并存入队列，然后将自己入栈
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
	  * 生成一个随机运算符
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
			result = "×";
			break;
		case 3:
			result = "÷";
			break;
		default:
			break;
		}
		return result;
	}
}
