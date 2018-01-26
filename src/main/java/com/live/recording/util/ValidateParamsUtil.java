package com.live.recording.util;

import org.apache.commons.lang.StringUtils;

import com.live.recording.domain.vo.input.LiveFileListDataReq;
import com.live.recording.domain.vo.input.LiveRecordListReq;
import com.live.recording.domain.vo.input.SysReq;
import com.live.recording.domain.vo.input.V_MultiplePeriodsRecordsReq;

public class ValidateParamsUtil {

	public static String validateGet(LiveRecordListReq req) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		// if (StringUtils.isEmpty(req.getChannelId())) {
		// sb.append(",channelId=" + req.getChannelId());
		// }

		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}

	// 验证合并flv文件参数
	public static String validateRecords(LiveRecordListReq req) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		if (StringUtils.isEmpty(req.getChannelId())) {
			sb.append(",channelId=" + req.getChannelId());
		}
		if (StringUtils.isEmpty(req.getRecordStartTime())) {
			sb.append(",recordStartTime=" + req.getRecordStartTime());
		}
		if (StringUtils.isEmpty(req.getRecordEndTime())) {
			sb.append(",recordEndTime=" + req.getRecordEndTime());
		}
		
		if (StringUtils.isEmpty(req.getSourceCode())) {
			sb.append(",sourceCode=" + req.getSourceCode());
		}

		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}

	// 进度
	public static String validateProgresst(String  keyId) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		if (StringUtils.isEmpty(keyId)) {
			sb.append("keyId=" + keyId);
		}

		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}
	
	// 删除cut
	public static String validateDeleteCut(LiveFileListDataReq req) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		if (StringUtils.isEmpty(req.getCutId())) {
			sb.append("cutId=" + req.getCutId());
		}

		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}

	// 删除record
	public static String validateRecordDelete(LiveRecordListReq req) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		if (StringUtils.isEmpty(req.getRecordId())) {
			sb.append(",recordId=" + req.getRecordId());
		}
		
		

		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}

	
	public static String validateCreatMultipleFlv(V_MultiplePeriodsRecordsReq req) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		if (StringUtils.isEmpty(req.getChannelId())) {
			sb.append(",channelId=" + req.getChannelId());
		}
		if (StringUtils.isEmpty(req.getCreater())) {
			sb.append(",creater=" + req.getCreater());
		}
		if (StringUtils.isEmpty(req.getRecordName())) {
			sb.append(",recordName=" + req.getRecordName());
		}
		if (StringUtils.isEmpty(req.getSourceCode())) {
			sb.append(",sourceCode=" + req.getSourceCode());
		}
		
		
		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}
	
	// 验证2次合并文件参数
	public static String validateCutRecords(LiveRecordListReq req) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		if (StringUtils.isEmpty(req.getRecordId())) {
			sb.append(",recordId=" + req.getRecordId());
		}
		if (StringUtils.isEmpty(req.getCutStartTime())) {
			sb.append(",cutStartTime=" + req.getCutStartTime());
		}
		if (StringUtils.isEmpty(req.getCutEndTime())) {
			sb.append(",cutEndTime=" + req.getCutEndTime());
		}
		if (StringUtils.isEmpty(req.getSourceCode())) {
			sb.append(",sourceCode=" + req.getSourceCode());
		}

		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}

	public static String validateRecords(SysReq req) {
		// 校验
		StringBuffer sb = new StringBuffer();
		sb.append("参数为空");
		if (StringUtils.isEmpty(req.getStream())) {
			sb.append(",stream=" + req.getStream());
		}
		if (StringUtils.isEmpty(req.getFile())) {
			sb.append(",file=" + req.getFile());
		}

		if (sb.toString().length() > 4) {
			return sb.toString();
		}
		return null;
	}

	public static void main(String[] args) {
		boolean empty = StringUtils.isEmpty("x");
		System.out.println(empty);
	}
}
