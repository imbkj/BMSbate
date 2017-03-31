package Util;

import org.zkoss.zul.Messagebox;

/**
 *
 * @功能 :对话框的封装 
 * @创建人: cyj
 * @创建日期: 2013-10-08
 *
 */
public class MessageUtil{
 /**
  * 显示提示信息 
  *
  * @param value
  */
 public static void showInfo(String value){
	 Messagebox.show(value, "提示", Messagebox.OK, Messagebox.INFORMATION);
 	}

 /**
  * 显示询问信息
  *
  * @param value
  * @return
  */
 public static int showQuestion(String value){
	 return Messagebox.show(value, "询问", Messagebox.YES | Messagebox.NO,
			Messagebox.QUESTION);
 	}

 /**
  * 显示警告
  *
  * @param value
  */
 public static void showWarning(String value) {
	 Messagebox.show(value, "警告", Messagebox.OK, Messagebox.EXCLAMATION);
 	}

 /**
  * 显示错误 
  *
  * @param value
  */
 public static void showError(String value) {
	 Messagebox.show(value, "错误", Messagebox.OK, Messagebox.ERROR);
 	}
}