package Controller.SalaryPaper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.zkoss.zk.ui.Executions;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;

public class SendHtmlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 请求的get方式
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EmSalaryDataModel m = (EmSalaryDataModel) request.getSession()
				.getAttribute("m");
		List<EmSalaryBaseAddItemModel> list = m.getItemList();
		JSONArray json = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject(); // 创建jsonobject对象
			obj.put("name", list.get(i).getCsii_item_name()); // 将对象转成josn格式
			obj.put("amount", list.get(i).getAmount());
			json.add(i, obj); // 将json格式的对象加入到jsonarray中
		}
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(json.toString()); // 将jsonarray通过输出流传递到前端
		out.flush();
		out.close();
		request.getSession().removeAttribute("m"); // 清除session里面的参数

	}

	/**
	 * 请求的post方式
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
