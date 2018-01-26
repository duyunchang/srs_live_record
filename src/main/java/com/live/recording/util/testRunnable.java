package com.live.recording.util;


import org.apache.log4j.Logger;



public class testRunnable  implements Runnable {
	private Logger logger = Logger.getLogger(testRunnable.class);
	
	private  static int count =0; 
	
	public testRunnable() {
		count++;
		System.out.println(count);
	}

	

	@Override
	public void run() {
		boolean executeCmd = false;
		try {                   //17276560
			int c=count;        //17285889
			logger.info(">>"+c);//17285819   17286051 
			                    //41277086  41472369
			Thread.currentThread().sleep(1000*100);
			logger.info(">>>"+c);

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Thread.currentThread().interrupt();
		}

	}

}
