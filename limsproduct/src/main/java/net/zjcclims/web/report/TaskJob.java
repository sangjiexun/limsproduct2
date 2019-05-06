package net.zjcclims.web.report;

import net.zjcclims.service.report.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("taskJob")
public class TaskJob {
	@Autowired
	private ReportService reportService;
	
	public void myJob(){
		//调用其他方法
	}
	
	public void myJobRank(){
		//调用其他方法
	}
	
	
	/************************************************
	 * 凌晨一点经行前一天的未上课确认的消息生成
	 * @author 孙虎
	 * 2017-12-26
	 *************************************************/
	public void createMessageForCourseConfirm(){
		System.out.println("凌晨一点经行前一天的未上课确认的消息生成");
		//调用张秦龙的方法
	}
	
}
