package Util;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;

public class RedirectUtil {
	
	/*
	 * 作用：公司业务中心跳转方法
	 * 参数：url 公司业务中心跳转的页面路径
	 * 参数类型：String
	 * Author:陈耀家
	 * */
	public void refreshCoUrl(String url)
	{
		Map map = new HashMap<>();
		map.put("url",url);// 跳转的页面连接
		BindUtils.postGlobalCommand(null, null, "refreshCoUrl", map);
	}
	
	/*
	 * 作用：员工业务中心跳转方法
	 * 参数：url 员工业务中心跳转的页面路径
	 * 参数类型：String
	 * Author:陈耀家
	 * */
	public void refreshEmUrl(String url)
	{
		Map map = new HashMap<>();
		map.put("url",url);// 跳转的页面连接
		BindUtils.postGlobalCommand(null, null, "refreshEmUrl", map);
	}
	
	/*
	 * 作用：员工入职第二页跳转方法
	 * 参数：url 员工入职第二页跳转的页面路径
	 * 参数类型：String
	 * Author:陈耀家
	 * */
	public void refreshEntrySecondUrl(String url)
	{
		Map map = new HashMap<>();
		map.put("url",url);// 跳转的页面连接
		BindUtils.postGlobalCommand(null, null, "refreshEntrySecondUrl", map);
	}
	
	/*
	 * 作用：员工入职第三页跳转方法
	 * 参数：url 员工入职第三页跳转的页面路径
	 * 参数类型：String
	 * Author:陈耀家
	 * */
	public void refreshEntryThirdUrl(String url)
	{
		Map map = new HashMap<>();
		map.put("url",url);// 跳转的页面连接
		BindUtils.postGlobalCommand(null, null, "refreshEntryThirdUrl", map);
	}
	
	/*
	 * 作用：首页打开新标签
	 * 参数：url新标签的页面连接
	 * 参数类型：String
	 * Author:陈耀家
	 * */
	public void indexAddTab(String url,String tabtext)
	{
		Map map = new HashMap<>();
		map.put("url",url);// 跳转的页面连接
		map.put("tabtext", tabtext);//标签文字
		EventQueue<Event> que = EventQueues.lookup(
				"tab" + UserInfo.getUserid(), EventQueues.SESSION,
				true);
		que.publish(new Event("Default", null, map));
	}
}
