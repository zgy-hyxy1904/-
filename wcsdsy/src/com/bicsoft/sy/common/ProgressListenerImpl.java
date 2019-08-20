package com.bicsoft.sy.common;

import java.math.BigDecimal;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.ProgressListener;
import com.bicsoft.sy.model.UploadStatusModel;

/**
 * @上传进度扩展，用于多文件上传及上传进度查询
 * @Author    Gaohua
 * @Version   2015/08/16
 */
public class ProgressListenerImpl implements ProgressListener {

	private UploadStatusModel status;
	private HttpSession session;
	
	public ProgressListenerImpl(UploadStatusModel status,HttpSession session) {
		super();
		this.status = status;
		this.session = session;
	}

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		double result = 0.00;
		double MBRead = bytesRead / 1021 / 1024;
		double MBLength = contentLength / 1024 /1024;
		if(MBLength > 0) result = MBRead / MBLength * 100;
		
		System.out.println("MBRead:" + MBRead);
		System.out.println("MBLength:" + MBLength);
		System.out.println("items:" + items);
		BigDecimal b = new BigDecimal(result);
		status.setPercent(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		status.setItems(items);
		session.setAttribute("status",status);
	}

}
