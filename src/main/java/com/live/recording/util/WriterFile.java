package com.live.recording.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.io.Files;
import com.live.recording.domain.entity.Livefilelist;
import com.live.recording.domain.entity.Liverecordlist;
import com.live.recording.domain.vo.input.V_Livecutlist;

@Component
public class WriterFile {
	private Logger logger = Logger.getLogger(WriterFile.class);

	/**
	 * MultipleMountFlvpath flv文件挂载 concat_path concat 目录
	 */
	public static String createCmdFlv(List<Livefilelist> findByChannelIAll, String concat_path, String multipleMountFlvpath) throws Exception {
		try {
			// 创建路径
			if (!createFile(concat_path)) {
				return null;
			}
			;
			// 写concat文件
			FileWriter fw = null;
			try {
				fw = new FileWriter(concat_path);
				fw.write("ffconcat version 1.0\n");
				for (Livefilelist data : findByChannelIAll) {
					if(data.getDuration()==null||(int)Double.parseDouble(data.getDuration())==0){
						continue;	
					}
					
					fw.write("file " + multipleMountFlvpath + File.separator + data.getPath() + "\n");
					fw.write("duration " + data.getDuration() + "\n");
					
				}
			} catch (IOException e) {
				concat_path = null;
				e.printStackTrace();
			} finally {
				try {
					if (fw != null) {
						fw.close();
					}
					;
				} catch (IOException e) {
					concat_path = null;
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			concat_path = null;
			e.printStackTrace();
		}
		return concat_path;
	}

	public static String createCmdCutFlv(Liverecordlist findByChannelIAll, String concat_path) throws Exception {
		try {
			// 创建路径
			if (!createFile(concat_path)) {
				return null;
			}
			;
			// 写concat文件
			FileWriter fw = null;
			try {
				fw = new FileWriter(concat_path);
				fw.write("ffconcat version 1.0\n");

				fw.write("file " + findByChannelIAll.getRecordPath() + "\n");
				fw.write("duration " + findByChannelIAll.getRecordDuration() + "\n");

				
			} catch (IOException e) {
				concat_path = null;
				e.printStackTrace();
			} finally {
				try {
					if (fw != null) {
						fw.close();
					}
					;
				} catch (IOException e) {
					concat_path = null;
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			concat_path = null;
			e.printStackTrace();
		}
		return concat_path;
	}

	public static  String createCmdMultipleCutFlv(List<V_Livecutlist> findByChannelIAll, String concat_path) throws Exception {
		try {
			// 创建路径
			if (!createFile(concat_path)) {
				return null;
			}
			;

			// 写concat文件
			FileWriter fw = null;
			try {
				fw = new FileWriter(concat_path);
				fw.write("ffconcat version 1.0\n");

				for (V_Livecutlist data : findByChannelIAll) {
					fw.write("file " + data.getCutInfo() + "\n");
					fw.write("duration " + data.getCutDuration() + "\n");
					
				}

			} catch (IOException e) {
				concat_path = null;
				e.printStackTrace();
			} finally {
				try {
					if (fw != null) {
						fw.close();
					}
					;
				} catch (IOException e) {
					concat_path = null;
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			concat_path = null;
			e.printStackTrace();
		}
		return concat_path;
	}

	public static  boolean createFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			System.out.println("创建单个文件" + destFileName + "目标文件已存在！");
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
			return false;
		}
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				System.out.println("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
			return false;
		}
	}

	public static  boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "目标目录已经存在");
			return true;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	public boolean existsFile(String destFileName) {
		File file = new File(destFileName);
		boolean flag = false;
		int count = 0;
		while (true) {
			count++;
			if (file.exists()) {
				flag = true;
				break;
			}
			// 等待5分钟
			if (count >= 600) {
				break;
			}
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("检查日志文件是否存在次数="+count+";path="+destFileName);
		}
		return flag;
	}

	// 读取文件内容
	public String readfile(String path) throws Exception {

		File file = new File(path);
		String readFileToString = FileUtils.readFileToString(file, "UTF-8");

		return prasefile(readFileToString);

	}
	
	// 读取文件内容全部内容
	public String readfileALl(String path) throws Exception {

		File file = new File(path);
		String readFileToString = FileUtils.readFileToString(file, "UTF-8");

		return readFileToString;

	}

	// 解析文件内容
	public String prasefile(String content) throws Exception {
		String value = null;
		Pattern p = Pattern.compile("duration=(\\d+\\.\\d+)\\|size=");
		Matcher m = p.matcher(content);
		while (m.find()) {
			value = m.group(1);
			break;
		}

//		if("null".equals(value)){
//			value=null;
//		}
		
		return value;
	}

	public static long sizeOf(File file) throws IOException {
		  return Files.asByteSource(file).size();
     }
	public static void main(String[] args) {
		System.out.println((int)Double.parseDouble("0.0"));
		
//		WriterFile w=new WriterFile();
//		try {
//			String prasefile = w.prasefile("");
//			System.out.println(prasefile==null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		File file = new File("");
//		try {
//			
//			long sizeOf = sizeOf(file);
//			
//			System.out.println(sizeOf);
//			long length = file.length();
//			System.out.println(length);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Integer a=null;
//		String s=a==null?null:a+"";//String.valueOf(a);//"" "null"
//		System.out.println(s);
//		System.out.println("null".equals(s));
//		System.out.println(null==s);
		//String path = "D:" + File.separator + "xxx" + DateHelper.getDateDirStr() + File.separator + "tysxlive_testRec15048344705941.txt";
		//WriterFile e = new WriterFile();
		//System.out.println(e.existsFile("C:\\Users\\dyc\\Desktop\\liveyun_recording_service.jar"));
	}
}
