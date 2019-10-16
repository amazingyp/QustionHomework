

import java.util.Scanner;

import util.QuestionUtil;

public class Main {

	public static void main(String[] args) {
		while(true) {
			System.out.println("��������-n 10 -r 10 ��-e E:/Exercises.txt -a E:/Answers.txt ��ָ��");
			Scanner in = new Scanner(System.in);
			String pwd = in.nextLine();
			
			pwd = pwd.replaceAll(" ","");
			String c1 = pwd.substring(1,2);
			if(c1.equals("n")||c1.equals("r")) {
				int op1;
				int op2;
				String c2;
				int index=0;
				if(c1.equals("n")) {
					index = pwd.indexOf('r');
				}
				else {
					index = pwd.indexOf('n');
				}
				if(index==-1) {
					System.out.println("��������-n 10 -r 10 ��-e E:/Exercises.txt -a E:/Answers.txt ��ָ��");
					continue;
					}
				
				op1 = Integer.parseInt(pwd.substring(2, index-1));
				
				c2 = pwd.substring(index,index+1);
				
				if(c2.equals(c1) || (!c2.equals("r") && !c2.equals("n"))) {
					System.out.println("��������-n 10 -r 10 ��-e E:/Exercises.txt -a E:/Answers.txt ��ָ��");
					continue;
				}
				else {
					op2 = Integer.parseInt(pwd.substring(index+1));
					
					
					if(c1.equals("r")) {
						util.QuestionUtil.Create(op1, op2);
						System.out.println("�ɹ���");
					}
					else {
						util.QuestionUtil.Create(op2, op1);
						System.out.println("�ɹ���");
					}	
				}
			}
			else if(c1.equals("e")){
				
				String op1,op2;
				int index = pwd.indexOf('a');
				if(index==-1) {
					System.out.println("��������-n 10 -r 10 ��-e E:/Exercises.txt -a E:/Answers.txt ��ָ��");
					continue;
					}
				op1 = pwd.substring(2,index-1);
				op2 = pwd.substring(index+1);
				
				
				QuestionUtil.printGrade(op1, op2);
				System.out.println("�ɹ���");

			}
			else {
				System.out.println("��������-n 10 -r 10 ��-e E:/Exercises.txt -a E:/Answers.txt ��ָ��");
				continue;
			}
		}

	}

}
