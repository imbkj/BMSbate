/**
 * @Classname CheckStringService
 * @ClassInfo 判断字符串接口（实现类impl.CheckStringImpl）
 * @author 李文洁
 * @Date 2013-9-13
 */
package service;

public interface CheckStringService {
   //判断字符串是否包含中文
   boolean isChinese(String str);
   //判断字符串是否为纯数字
   boolean isNum(String str);
   //判断字符串是否为纯字母
   boolean isLetter(String str);
}
