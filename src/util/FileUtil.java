package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * �ļ�������
 *
 */

public class FileUtil {	
	/**
	 * ��һ��������д���ļ���
	 *@param s ����
	 *@param type 0Ϊ��Ŀ 1Ϊ��
	 */
	public static void printToFile(String s,int type) {
		String path=null;
		if(type == 0) {
			path = "./questionbank/Exercises.txt";
		}
		else {
			path = "./questionbank/Answers.txt";
		}
		
		s = s+"\n";
		File file = new File(path);
		FileOutputStream o = null;
		try {
			o=new FileOutputStream(file,true);
			o.write(s.getBytes());
			//System.out.println("д��"+s);
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * ������Ŀ��exercise�ļ�
	 */
	public static void cleanExerciseFile() {
		File file = new File("./questionbank/Exercises.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
				//System.out.println("�½��ļ���");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				file.delete();
				file.createNewFile();
				//System.out.println("�½��ļ���");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ������Ŀ��exercise�ļ�
	 */
	public static void cleanAnswerFile() {
		File file = new File("./questionbank/Answers.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
				//System.out.println("�½��ļ���");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				file.delete();
				file.createNewFile();
				//System.out.println("�½��ļ���");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ���ļ��е����ݰ��л��
	 */
	public static List readFile(String path) {
		File file = new File(path);
		List list = new ArrayList();
		String str = null;
		
		try {
			InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
			BufferedReader bf = new BufferedReader(inputReader);
			while ((str = bf.readLine()) != null) {
				list.add(str);
			}
			bf.close();
			inputReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
