/**
 * @Classname FileOperate
 * @ClassInfo 文件操作类
 * @author 李文洁
 * @Date 2013-12-12
 */
package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

import com.jxcell.CellException;
import com.jxcell.View;

public class FileOperate {

	// 文件上传(filename:保存文件的路径文件夹及文件名。如：DocumentsInfo/UploadFile/20131101170854101.xls)
	public static boolean upload(Media media, String filename) {
		boolean bl = false;
		// 服务器绝对路径及文件名
		String name = getAbsolutePath() + filename;
		InputStream is = media.getStreamData();
		if (is != null) {
			File file = new File(name);
			try {
				org.zkoss.io.Files.copy(file, is);
				bl = true;
			} catch (IOException e) {

			} finally {
				org.zkoss.io.Files.close(is);
			}
		}
		return bl;
	}

	// 下载（url为相对路径。如：DocumentsInfo/UploadFile/20131101170854101.xls）
	public static void download(String FileUrl) {
		File file = new File(FileUrl);
		String filename = "";
		try {
			// 文件名称转码解决中文乱码问题。
			filename = new String(file.getName().getBytes("gb2312"),
					"ISO8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			String ss=getAbsolutePath();
			Filedownload.save(
					new FileInputStream((getAbsolutePath() + FileUrl).replace(
							"%20", " ")), "application/octet-stream", filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 获取文件大小
	public static String getFileSize(Media media) {
		String fileSize = "";
		try {
			DecimalFormat df = new DecimalFormat("#.00");
			int fileS = media.getStreamData().available();
			if (fileS < 1024) {
				fileSize = df.format((double) fileS) + "B";
			} else if (fileS < 1048576) {
				fileSize = df.format((double) fileS / 1024) + "K";
			} else if (fileS < 1073741824) {
				fileSize = df.format((double) fileS / 1048576) + "M";
			} else {
				fileSize = df.format((double) fileS / 1073741824) + "G";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileSize;
	}

	// 项目绝对路径
	public static String getAbsolutePath() {
		return Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("/").replace("\\", "/");
	}

	// 查找是否存在该文件(folder:文件目录，如：DocumentsInfo/UploadFile/
	// ；FileName：文件名(带后缀如：SalaryChange.xls))
	public static boolean existFile(String folder, String filename) {
		try {
			File f = new File(getAbsolutePath() + folder);
			String[] fileList = f.list();
			if(fileList!=null){
			  if (fileList.length > 0) {
				 for (String str : fileList) {
					File file = new File(str);
					if (file.getName().equals(filename)) {
						return true;
					}
				 }
			  }
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	// 删除文件（filepath为相对路径。如：DocumentsInfo/UploadFile/20131101170854101.xls）
	public static boolean deleteFile(String filepath) {
		boolean bool = false;
		try {
			File file = new File(getAbsolutePath() + filepath);
			bool = file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	// Excel文件加密（仅对xls文件加密，filepath为相对路径。如：DocumentsInfo/UploadFile/20131101170854101.xls）
	// 需注意Excel文件是否完整
	public static boolean encryptionExcel(String filepath, String password) {
		String absolutePath = getAbsolutePath();
		try {
			View vExl = new View();
			vExl.read(absolutePath + filepath);
			vExl.write(absolutePath + filepath, password);
			return true;
		} catch (CellException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 拼接上传文件的名称(格式为：yyyyMMddHHmmss_操作人ID)
	public static String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + "_" + UserInfo.getUserid();
		return name;
	}

}
