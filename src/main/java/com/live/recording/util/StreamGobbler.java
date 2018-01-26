package com.live.recording.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.live.recording.config.CacheMap;

public class StreamGobbler implements Callable<String> {

	private InputStream inputStrean;
	private String path;

	private Pattern pattern;// Pattern.CASE_INSENSITIVE
	private Pattern patternEnd;// Pattern.CASE_INSENSITIVE
	private long allsize;

	private CacheMap  cahcheMap;
	private String cacheKey;
	
	private String secondKey;
	public StreamGobbler() {
		this.pattern = Pattern.compile("time=(\\d{2}):(\\d{2}):(\\d{2})");
		this.patternEnd =Pattern.compile("^video:\\d+kB audio:\\d+kB subtitle:\\d+kB other streams:\\d+kB global headers:\\d+kB muxing overhead");

	}

	public StreamGobbler(InputStream is, String path, long allsize,CacheMap cahcheMap,String cacheKey) {
		this.inputStrean = is;
		this.path = path;
		this.allsize = allsize;
		this.cahcheMap=cahcheMap;
		this.cacheKey=cacheKey;
		this.pattern = Pattern.compile("time=(\\d{2}):(\\d{2}):(\\d{2})");
		this.patternEnd =Pattern.compile("^video:\\d+kB audio:\\d+kB subtitle:\\d+kB other streams:\\d+kB global headers:\\d+kB muxing overhead");

	}

	public String call() throws Exception {
		BufferedReader br = null;
		Scanner sc = null;
		//FileWriter fw = null;
		try {
			if(cahcheMap!=null){
				//实际上 是数据库中这条数据的id
				String[] split = path.split(File.separator);				
				secondKey=split[split.length-1].replace(cacheKey, "");
				System.out.println("secondKey="+secondKey+";日志文件路径="+path);
			}
			//最后一个参数是缓冲区 5*1024*1024(5M) 4096

			StringBuffer resultStringBuffer = new StringBuffer();
			String line = "";

			br = new BufferedReader(new InputStreamReader(inputStrean, "UTF-8"), 1024);
			// 写文件

			//fw = new FileWriter(path, true);

			while ((line = br.readLine()) != null) {

				line += "\n";
				//resultStringBuffer.append(line);

				// 获取切片实时时间
				 DecodeFFmpegTime(line);
				

				if (Thread.interrupted()) {
					return "";
				}

				//fw.write(line);
				//fw.flush();

			}

			return resultStringBuffer.toString();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			return ioe.getMessage();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (sc != null) {
					sc.close();
				}
				if (inputStrean != null) {
					inputStrean.close();
				}
//				if (fw != null) {
//					fw.close();
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	// 获取 切片进度//frame=53573 fps=2980 q=-1.0 size= 276933kB time=00:35:43.64
	// bitrate=1058.3kbits/s speed= 119x
	private String DecodeFFmpegTime(String ffmstr) {
		//System.out.println("allsize="+allsize);
		///System.out.println(ffmstr);
		float  valueEnd=0;
		Integer[] values = { 0, 0, 0 };
		Integer value = null;
		// Pattern pattern=Pattern.compile(regx);//Pattern.CASE_INSENSITIVE
		Matcher matcher = pattern.matcher(ffmstr);
		if (matcher.find()) {
			values[0] = matcher.group(1) == null ? 0 : Integer.parseInt(matcher.group(1));
			values[1] = matcher.group(2) == null ? 0 : Integer.parseInt(matcher.group(2));
			values[2] = matcher.group(3) == null ? 0 : Integer.parseInt(matcher.group(3));
			
			value = values[0] * 60 * 60 + values[1] * 60 + values[2];
			//System.out.println("value="+value+";values[0]=" + values[0] + ";values[1]=" + values[1] + ";values[2]="+values[2]);
			valueEnd=(value/(float)allsize);
			//保留2位
			valueEnd=(float)(Math.round(valueEnd*100))/100;
			if(cahcheMap!=null){
				//System.out.println("cacheKey="+cacheKey+";valueEnd="+valueEnd);				
				//实际上 是数据库中这条数据的id
				valueEnd=valueEnd>0.96?(float)0.98:valueEnd;
				cahcheMap.setCahche(cacheKey, secondKey,valueEnd+"");
							
			}

		}else{//结束
			DecodeFFmpegEnd(ffmstr);
		}
		return valueEnd+"";
	}
	
	private void DecodeFFmpegEnd(String ffmstr) {

		Matcher matcher = patternEnd.matcher(ffmstr);
		if (matcher.find()) {
			
			if(cahcheMap!=null){
				System.out.println("cacheKey="+cacheKey+";valueEnd="+1);
				//实际上已经拆条成功
				cahcheMap.setCahche(cacheKey, secondKey,0.99+"");
				//cahcheMap.setCahche(cacheKey, secondKey,1+"");
								
				//cahcheMap.setCahche(cacheKey, 1.0+"");
			}

		}
		
	}
	


}