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
 * 文件工具类
 *
 */

public class FileUtil {	
	/**
	 * 将一行问题或答案写入文件中
	 *@param s 内容
	 *@param type 0为题目 1为答案
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
			//System.out.println("写了"+s);
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 清理项目下exercise文件
	 */
	public static void cleanExerciseFile() {
		File file = new File("./questionbank/Exercises.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
				//System.out.println("新建文件了");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				file.delete();
				file.createNewFile();
				//System.out.println("新建文件了");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 清理项目下exercise文件
	 */
	public static void cleanAnswerFile() {
		File file = new File("./questionbank/Answers.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
				//System.out.println("新建文件了");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				file.delete();
				file.createNewFile();
				//System.out.println("新建文件了");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 将文件中的内容按行获得
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
