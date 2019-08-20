package com.bicsoft.sy.common.interceptor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bicsoft.sy.entity.LandLog;
import com.bicsoft.sy.model.GeneLandRegModel;
import com.bicsoft.sy.model.SpecLandRegModel;
import com.bicsoft.sy.service.LandLogService;

@Aspect
@Component
public class LandLogInterceptor  {
	private static Log log = LogFactory.getLog(LandLogInterceptor.class.getName());
	
	@Autowired
	private LandLogService landLogService;
	
	private Integer landId = 0;
	
	
	@Before("execution(public void com.bicsoft.sy.service.impl.GeneLandRegServiceImpl.save*(..))"
			+ "||execution(public void com.bicsoft.sy.service.impl.SpecLandRegServiceImpl.save*(..))"
			+ "||execution(public * com.bicsoft.sy.service.impl.SpecLandRegServiceImpl.updateStatus*(..))")
	public void beforeMethod(JoinPoint point) {
		String className = point.getSignature().getDeclaringType().getSimpleName();
		String methodName = point.getSignature().getName();
		if(isGene(className)){
			if(isSaveOrUpdateMethod(methodName)){
				GeneLandRegModel geneLandRegModel = (GeneLandRegModel)(point.getArgs()[0]);
				this.landId = geneLandRegModel.getId();
			}
		}else if(isSpec(className)){
			if (isSaveOrUpdateMethod(methodName)) {
				if(methodName.startsWith("save")){
					SpecLandRegModel specLandRegModel = (SpecLandRegModel)(point.getArgs()[0]);
					this.landId = specLandRegModel.getId();
				}else{
					//如果是updateStatus方法,就随便设置一个不为0的ID即可,但好像没什么用
					this.landId = 100;
				}
			}
		}
	}
	
	@AfterReturning(pointcut="execution(public void com.bicsoft.sy.service.impl.GeneLandRegServiceImpl.save*(..))"
			+ "||execution(public void com.bicsoft.sy.service.impl.SpecLandRegServiceImpl.save*(..))"
			+ "||execution(public * com.bicsoft.sy.service.impl.SpecLandRegServiceImpl.updateStatus*(..))",returning="returnValue")
	public void afterReturning(JoinPoint point, Object returnValue) {
		String className = point.getSignature().getDeclaringType().getSimpleName();
		String methodName = point.getSignature().getName();
		LandLog landLog = new LandLog();
		if(isGene(className)){
			//普通土地
			if(isSaveOrUpdateMethod(methodName)){
				GeneLandRegModel geneLandRegModel = (GeneLandRegModel)(point.getArgs()[0]);
				landLog.setLandLogType("GENE");
				landLog.setBatchNo(geneLandRegModel.getApplyBatchNo());
				landLog.setDeleteFlag("N");
				if( "01".equals( geneLandRegModel.getStatus() ) ){
					if(landId == null || landId.equals(0)){
						//新增
						landLog.setActionInfo("普通土地备案信息登记");
						landLog.setRemark("普通土地备案信息保存成功");
						landLog.setCreateUserId(geneLandRegModel.getCreateUserId());
						landLog.setCreateDate(geneLandRegModel.getCreateDate());
					}else{
						//修改
						landLog.setActionInfo("普通土地备案信息修改");
						landLog.setRemark("普通土地备案信息修改成功");
						landLog.setCreateUserId(geneLandRegModel.getUpdateUserId());
						landLog.setCreateDate(geneLandRegModel.getUpdateDate());
					}
				}else if( "02".equals( geneLandRegModel.getStatus() ) ){
					landLog.setActionInfo("普通土地备案信息提交审核");
					landLog.setRemark("土地确权系统自动审核通过");
					landLog.setCreateUserId(geneLandRegModel.getUpdateUserId());
					landLog.setCreateDate(geneLandRegModel.getUpdateDate());
				}else if( "03".equals( geneLandRegModel.getStatus() ) ){
					landLog.setActionInfo("普通土地备案信息提交审核");
					landLog.setRemark("土地确权系统审核未通过");
					landLog.setCreateUserId(geneLandRegModel.getUpdateUserId());
					landLog.setCreateDate(geneLandRegModel.getUpdateDate());
				}
			}else if(isDeleteMethod(methodName)){
				//如果被删除,查询列表中也查不出来了,因此处理没有意义
			}
		}else if(isSpec(className)){
			//特殊土地
			SpecLandRegModel specLandRegModel = new SpecLandRegModel();
			if(isSaveOrUpdateMethod(methodName)){
				if(methodName.startsWith("updateStatus")){
					//这里不需要循环取,因为提交审核和审核在页面控制都只允许单条记录
					specLandRegModel = ((List<SpecLandRegModel>) returnValue).get(0);
				}else if(methodName.startsWith("save")){
					specLandRegModel = (SpecLandRegModel)(point.getArgs()[0]);
				}
				landLog.setLandLogType("SPEC");
				landLog.setBatchNo(specLandRegModel.getApplyBatchNo());
				landLog.setDeleteFlag("N");
				if( "01".equals( specLandRegModel.getStatus() ) ){
					if(landId == null || landId.equals(0)){
						//新增
						landLog.setActionInfo("特殊土地备案信息登记");
						landLog.setRemark("特殊土地备案信息保存成功");
						landLog.setCreateUserId(specLandRegModel.getCreateUserId());
						landLog.setCreateDate(specLandRegModel.getCreateDate());
					}else{
						//修改
						landLog.setActionInfo("特殊土地备案信息修改");
						landLog.setRemark("特殊土地备案信息修改成功");
						landLog.setCreateUserId(specLandRegModel.getUpdateUserId());
						landLog.setCreateDate(specLandRegModel.getUpdateDate());
					}
				}else if( "02".equals( specLandRegModel.getStatus() ) ){
					landLog.setActionInfo("特殊土地备案信息申请审核");
					landLog.setRemark("特殊土地备案信息已提交审核申请,待审核");
					landLog.setCreateUserId(specLandRegModel.getUpdateUserId());
					landLog.setCreateDate(specLandRegModel.getUpdateDate());
				}else if( "03".equals( specLandRegModel.getStatus() ) ){
					landLog.setActionInfo("特殊土地备案信息审核");
					landLog.setRemark("经审核本次特殊土地备案信息合规,审核通过");
					landLog.setCreateUserId(specLandRegModel.getUpdateUserId());
					landLog.setCreateDate(specLandRegModel.getUpdateDate());
				}else if( "04".equals( specLandRegModel.getStatus() ) ){
					landLog.setActionInfo("特殊土地备案信息审核驳回");
					landLog.setRemark("驳回原因:"+specLandRegModel.getReason());
					landLog.setCreateUserId(specLandRegModel.getUpdateUserId());
					landLog.setCreateDate(specLandRegModel.getUpdateDate());
				}
			}else if(isDeleteMethod(methodName)){
				//如果被删除,查询列表中也查不出来了,因此处理没有意义
			}
		}
		landLog.setUpdateUserId(landLog.getCreateUserId());
		landLog.setUpdateDate(landLog.getCreateDate());
		landLogService.save(landLog);
		this.landId = 0;
	}
	
	
	private boolean isGene(String className){
		return className.startsWith("Gene");
	}
	
	private boolean isSpec(String className){
		return className.startsWith("Spec");
	}
	
	private boolean isSaveOrUpdateMethod(String methodName){
		return methodName.startsWith("save") || methodName.startsWith("update");
	}
	
	private boolean isDeleteMethod(String methodName){
		return methodName.startsWith("delete")||methodName.startsWith("logicDelete");
	}

}
