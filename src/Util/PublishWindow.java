package Util;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;

public class PublishWindow extends SelectorComposer<Component>{
	// window属性
	private String title;
	private String height;
	private String width;
	private String position;
	private String border;
	private boolean closable;
	private boolean sizable;
	private boolean maximizable;
	private String openMethod; // 可选内容：doPopup;doModal;doOverlapped

	public enum openWindow {
		doPopup, doModal, doOverlapped
	};

	// 队列属性
	private String EventName;
	private Component target;

	// 信息的内容
	private String sendMessage;
	private String sendUrl;
	private Map<String, Object> sendMap;

	// 默认构造函数
	public PublishWindow() {
		title = "系统提示";
		height = "150px";
		width = "200px";
		position = "right,bottom";
		border = "normal";
		closable = true;
		sizable = true;
		maximizable = false;
		openMethod = "doPopup";
		EventName = "Default";
	}

	/**
	 * @Methodname:推送方法（主要用于系统提示）
	 * 
	 * @input: queueName:队列的名称(该名称需与接收名称相同,主页弹出框接收名称为：“Uid”+userid)；
	 *         message:提示信息；url：跳转的URL；map:URL中的参数；
	 */
	public void publish(String queueName, String message, String url, Map<String, Object> map) {
		setSendMessage(message);
		setSendUrl(url);
		setSendMap(map);
		EventQueue<Event> que = EventQueues.lookup(queueName,
				EventQueues.APPLICATION, true);
		que.publish(new Event(EventName, target, this));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public boolean getClosable() {
		return closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
	}

	public boolean getSizable() {
		return sizable;
	}

	public void setSizable(boolean sizable) {
		this.sizable = sizable;
	}

	public boolean getMaximizable() {
		return maximizable;
	}

	public void setMaximizable(boolean maximizable) {
		this.maximizable = maximizable;
	}

	public String getOpenMethod() {
		return openMethod;
	}

	public void setOpenMethod(openWindow openWindow) {
		switch (openWindow) {
		case doPopup:
			this.openMethod = "doPopup";
			break;
		case doModal:
			this.openMethod = "doModal";
			break;
		case doOverlapped:
			this.openMethod = "doOverlapped";
			break;
		default:
			this.openMethod = "doPopup";
			break;
		}
	}

	public String getEventName() {
		return EventName;
	}

	public void setEventName(String eventName) {
		EventName = eventName;
	}

	public Component getTarget() {
		return target;
	}

	public void setTarget(Component target) {
		this.target = target;
	}

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public Map<String, Object> getSendMap() {
		return sendMap;
	}

	public void setSendMap(Map<String, Object> sendMap) {
		this.sendMap = sendMap;
	}

}
