package com.live.recording.util;

import java.lang.reflect.Field;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.live.recording.config.CacheMap;


public class ExecRuntime {
	private static Logger logger = Logger.getLogger(ExecRuntime.class);

	private static int OSNAME = 0; // 1:WINDOWS 2:LINUX

	static {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") >= 0) {
			OSNAME = 1;
		} else if (osName.indexOf("Linux") >= 0) {
			OSNAME = 2;
		}
	}

	public boolean executeCmd(String cmd, long timeoutInSECONDS, String path, long allsize,CacheMap  cahcheMap,String cacheKey) throws Exception {
		if (OSNAME == 1) {
			return executeWinCmd(cmd, timeoutInSECONDS, path, allsize,cahcheMap,cacheKey);
		} else {
			return executeLinuxCmd(cmd, timeoutInSECONDS, path, allsize,cahcheMap,cacheKey);
		}

	}

	//// timeoutInSec 表示秒 Linux
	public boolean executeLinuxCmd(String cmd, long timeoutInSECONDS, String path, long allsize,CacheMap  cahcheMap,String cacheKey) throws Exception {
		logger.info("timeout=" + timeoutInSECONDS + "(s);execute=" + cmd);
		long t1 = System.currentTimeMillis();
		boolean ret = false;
		Runtime runtime = Runtime.getRuntime();
		final Process process = runtime.exec(new String[] { "/bin/bash", "-c", cmd });
		long pid = -1;
		Field field = null;
		try {
			// 关闭输入流
			process.getOutputStream().close();

			// 获取pid
			Class<?> clazz = Class.forName("java.lang.UNIXProcess");
			field = clazz.getDeclaredField("pid");
			field.setAccessible(true);
			pid = (Integer) field.get(process);
			logger.info("pid=" + pid);

			if(cahcheMap!=null){
				// 读取输出流
				StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), path, allsize, cahcheMap,cacheKey);
				StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), path, allsize, cahcheMap,cacheKey);
	
				final FutureTask<String> errTask = new FutureTask<String>(errorGobbler);
				Thread errThread = new Thread(errTask);
				errThread.start();
	
				final FutureTask<String> outputTask = new FutureTask<String>(outputGobbler);
				Thread outputThread = new Thread(outputTask);
				outputThread.start();
			}
//			Callable processCall = new Callable() {
//
//				@Override
//				public Integer call() throws Exception {
//					process.waitFor();
//
//					return process.exitValue();
//				}
//
//			};
//
//			ExecutorService service = Executors.newSingleThreadExecutor();
//			Future processFuture = service.submit(processCall);
//			service.shutdown();
//
//			// 设置超时时间
//			Integer exitValue = (Integer) processFuture.get(timeoutInSECONDS, TimeUnit.SECONDS);
//
//			if (exitValue != null && exitValue == 0) {
//				ret = true;
//			}
			
			//设置超时时间
			if (process.waitFor(timeoutInSECONDS, TimeUnit.SECONDS)) {
				ret = true;
			}

		} catch (Exception e) {
			logger.error("Execute command error!");
			e.printStackTrace();
		} finally {
			if (!ret) {
				//强制摧毁进程
				Process destroyForcibly = process.destroyForcibly();
				// 手动杀死进程
				if(process.isAlive()){
				
					String kill_cmd = "kill -9 " + pid;
					Process killPrcess = runtime.exec(new String[] { "/bin/bash", "-c", kill_cmd });
					killPrcess.waitFor();
	
					Integer exitValue2 = killPrcess.exitValue();
					if (exitValue2 != null && exitValue2 == 0) {
						logger.info("kill pid=" + pid + " 成功");
					} else {
						logger.info("kill pid" + pid + " 失败");
					}
				}
			}
			
			long t2 = System.currentTimeMillis();
			System.out.println("runtime execute " + " cost:" + (t2 - t1) + " ms");
		}

		return ret;
	}

	//// timeoutInSec 表示秒
	public boolean executeWinCmd(String cmd, long timeoutInSECONDS, String path, long allsize,CacheMap  cahcheMap,String cacheKey) throws Exception {
		logger.info("timeout=" + timeoutInSECONDS + "(s);execute=" + cmd);
		long t1 = System.currentTimeMillis();
		boolean ret = false;
		final Process process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/C", cmd });
		try {

			// 关闭输入流
			process.getOutputStream().close();
			if(cahcheMap!=null){
				// 读取输出流
				StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), path, allsize,cahcheMap,cacheKey);
				StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), path,allsize, cahcheMap,cacheKey);
	
				final FutureTask<String> errTask = new FutureTask<String>(errorGobbler);
				Thread errThread = new Thread(errTask);
				errThread.start();
	
				final FutureTask<String> outputTask = new FutureTask<String>(outputGobbler);
				Thread outputThread = new Thread(outputTask);
				outputThread.start();
			}
//			Callable processCall = new Callable() {
//
//				@Override
//				public Integer call() throws Exception {
//					process.waitFor();
//					return process.exitValue();
//				}
//
//			};
//
//			ExecutorService service = Executors.newSingleThreadExecutor();
//			Future processFuture = service.submit(processCall);
//			service.shutdown();
//
//			// 设置超时时间
//			int exitValue = (Integer) processFuture.get(timeoutInSECONDS, TimeUnit.SECONDS);
			
			
			if (process.waitFor(timeoutInSECONDS, TimeUnit.SECONDS)) {
				ret = true;
			}

		} catch (Exception e) {
			logger.error("Execute command error!");
			e.printStackTrace();
		} finally {
			process.destroyForcibly();
			long t2 = System.currentTimeMillis();
			System.out.println("runtime execute " + " cost:" + (t2 - t1) + " ms");
		}

		return ret;
	}

}
