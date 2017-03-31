package Util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;

public class ObjectClassUtil {
	private Class c;
	private Field[] fields;
	private Method[] methods;

	/*
	 * ObjectClassUtil ocu = new ObjectClassUtil();
		String classname = "EmBenefitModel";
		ocu.getClassDal("Model", "EmBenefitModel");
	 */
	public void getClassField(String packageName, String className) {

		try {

			// Class c = Class.forName(getClassList("Model").get(0)); // 要包名+类名
			Class c = Class.forName(packageName + "." + className);
			System.out.println(c.getName());
			Field[] fields = c.getDeclaredFields();// 拿到数据成员
			Method[] methods = c.getMethods();// 拿到函数成员

			for (Field f : fields) {
				System.out.println("该类的内部变量有:" + f.getName());
			}
			for (Method m : methods) {
				if (!m.getName().substring(0, 3).equals("set")) {
					System.out.println("em." + m.getName() + "(),");
				}

			}
			System.out.println("");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getClassDal(String packageName, String className) {
		Class c;
		try {
			c = Class.forName(packageName + "." + className);
			System.out.println("public List<"+className+"> getList("+className+" model){");
			System.out.println("List<"+className+"> list = new ListModelList<>();");
			System.out.println("dbconn db = new dbconn();");
			System.out.println("StringBuilder sql = new StringBuilder();");
			System.out.println("sql.add(\"select \");");
			Field[] fields = c.getDeclaredFields();// 拿到数据成员
			for (Field f : fields) {
				System.out.println("sql.add(\""+f.getName()+",");
				String objName = "model.get"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1);
				String str = "if("+objName+"!=null){" +f.getName()+"="+objName+"}";
				System.out.println(str);
			}
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getClassList(String packageName) {

		List<String> classNames = getClassName(packageName);
		return classNames;
	}

	public static List<String> getClassName(String packageName) {
		String filePath = ClassLoader.getSystemResource("").getPath()
				+ packageName.replace(".", "\\");
		List<String> fileNames = getClassName(filePath, null);
		return fileNames;
	}

	private static List<String> getClassName(String filePath,
			List<String> className) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				myClassName.addAll(getClassName(childFile.getPath(),
						myClassName));
			} else {
				String childFilePath = childFile.getPath();
				childFilePath = childFilePath.substring(
						childFilePath.indexOf("\\classes") + 9,
						childFilePath.lastIndexOf("."));
				childFilePath = childFilePath.replace("\\", ".");
				myClassName.add(childFilePath);
			}
		}

		return myClassName;
	}
}
