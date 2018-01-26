package com.live.recording.constants;

public class Constants {

	/**
	 * 是否重新创建flv任务
	 */
//	public static final int CREATFLV_YES = 1;// 需要
//	public static final int CREATFLV_NO = 2;// 不需要

	/**
	 * 录制类型
	 */
	public static final String CREATFLV_locking_task = "locking";// 及时录制
	
	/**
	 * 录制类型
	 */
	public static final int CREATFLV_Record = 1;// 及时录制
	public static final int CREATFLV_Scheduled = 2;// 定时录制
	
	/**
	 * 是否已删除
	 */
	public static final int ISDELETE_YES = 1;// 被删除
	public static final int ISDELETE_NO = 2;// 未删除
	public static final int ISDELETE_SERVER_YES = 3;// 实际服务器上已经删除
	public static final int ISDELETE_SERVER_NO = 4;// 实际服务器上删除失败
	/**
	 * MESSAGE返回结果
	 */
	public static final String MSG_SUCCESS = "success";// 成功
	public static final String MSG_FAIL = "fail";// 失败

	/**
	 * CODE返回结果
	 */
	public static final int CODE_SUCCESS = 0;// 成功
	public static final int CODE_FAIL = 1;// 失败

	/**
	 * 录制状态
	 */
//	public static final int TRANS_STATUS_1 = 1;// 1未录制
//	public static final int TRANS_STATUS_2 = 2;// 2录制中
//	public static final int TRANS_STATUS_3 = 3;// 3录制完成
//	public static final int TRANS_STATUS_4 = 4;// 4录制失败
//	public static final int TRANS_STATUS_5 = 5;// 5没有视频
	public static final int TRANS_STATUS_1 = 1;// 1未录制
	public static final int TRANS_STATUS_2 = 2;// 2录制中
	public static final int TRANS_STATUS_3 = 6;// 3录制完成
	public static final int TRANS_STATUS_4 = 4;// 4录制失败
	public static final int TRANS_STATUS_5 = 5;// 5没有视频
	/**
	 * 录制转码状态
	 */
	public static final int SERVER_YES = 1;// 可用
	public static final int SERVER_NO = 2;// 不可用

	/**
	 * 是否停止任务
	 */
	public static final int ISSTOP_YES = 1;// 需要
	public static final int ISSTOP_NO = 2;// 不需要
	
	public static final int reportstatus_1 = 1;// 未上报
	public static final int reportstatus_2 = 2;// 上报成功
	public static final int reportstatus_3 = 3;// 上报失败
	
	
	/**
	 *上报任务状态
	 */
	public static final int STATUS_YES = 1;// 可用
	public static final int STATUS_NO = 2;// 不可用

}
