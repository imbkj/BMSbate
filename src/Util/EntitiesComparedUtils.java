package Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import Model.SystLogModel;

/**
 * 用途比较一个model操作前操作后属性值有哪些已改变，并把它拼接成字符串
 * 
 * @author suhongyuan 2016-05-16
 */
public class EntitiesComparedUtils {

	public static String OldToNewReflect(Object model, Object model1)
			throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		Field[] field1 = model1.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		String s = "";
		for (int j = 0; j < field.length; j++) { // 遍历所有属性
			for (int i = 0; i < field1.length; i++) {
				String name = field[j].getName(); // 获取属性的名字
				String name1 = field1[i].getName(); // 获取属性的名字
				// System.out.println("attribute name:"+name);
				name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
				name1 = name1.substring(0, 1).toUpperCase()
						+ name1.substring(1); // 将属性的首字符大写，方便构造get，set方法
				Method m = model.getClass().getMethod("get" + name);
				Object value = m.invoke(model); // 调用getter方法获取属性值
				Method m1 = model1.getClass().getMethod("get" + name1);
				Object value1 = m1.invoke(model1); // 调用getter方法获取属性值
				if (field[j].getName() == field1[i].getName()) {
					// if(value!=null&&value1!=null){
					if (!String.valueOf(value).equals(String.valueOf(value1))) {
						// s+=field[j].getName()+"的值:"+value.toString()+"已被修改为:"+value1.toString();
						s += field[j].getName() + "的值:" + String.valueOf(value)
								+ "已被修改为:" + String.valueOf(value1);
					}
				}
			}
			// }
		}
		return s;
	}

	public static String NewReflect(Object model) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		String s = "";
		for (int j = 0; j < field.length; j++) { // 遍历所有属性
			String name = field[j].getName(); // 获取属性的名字
			name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
			Method m = model.getClass().getMethod("get" + name);
			Object value = m.invoke(model); // 调用getter方法获取属性值
			if (value != null) {
				s += "插入 " + field[j].getName() + "的值为:" + value.toString();
			}
		}
		return s;
	}

	// 比较同一个MODEL修改前后的值的变化
	public static <X> List<SystLogModel> compareModel(Object o1, Object o2, Class<X> clazz)
			throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {
		Field[] field = clazz.getDeclaredFields();
		X obj = clazz.newInstance();
		List<SystLogModel> list = new ArrayList<>();
		SystLogModel m=null;
		for (Field f : field) {
			Method method = obj.getClass().getMethod(getMethod(f.getName()));
			Object v1 = method.invoke(o1);
			Object v2 = method.invoke(o2);
			if (v1 != null && v2 != null) {
				if (!v1.equals(v2)) {
					m=new SystLogModel();
					m.setTableName(clazz.getName());
					m.setColumnName(f.getName());
					m.setOleValue(v1.toString());
					m.setNewValue(v2.toString());
					m.setContent(f.getName()+"的值为:"+v1.toString()+" 已被修改为: "+v2.toString());
				}
			}else if (v1==null && v2!=null) {
				m=new SystLogModel();
				m.setTableName(clazz.getName());
				m.setColumnName(f.getName());
				m.setOleValue(null);
				m.setNewValue(v2.toString());
				m.setContent("插入"+f.getName()+"的值为:"+v2.toString());
			}else if (v2==null && v1!=null) {
				m=new SystLogModel();
				m.setTableName(clazz.getName());
				m.setColumnName(f.getName());
				m.setOleValue(v1.toString());
				m.setNewValue(null);
				m.setContent(f.getName()+"的值为:"+v1.toString()+" 被清空");
			}
			if (m!=null) {
				list.add(m);
			}
			m=null;
		}

		return list;
	}

	public static String getMethod(String name) {
		return "get"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	public static String setMethod(String name) {
		return "set"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}
}
