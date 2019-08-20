package com.bicsoft.sy.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.core.dao.ReCallRecordDao;
import com.bicsoft.sy.dao.BlackListDDao;
import com.bicsoft.sy.dao.BlackListDao;
import com.bicsoft.sy.dao.CompanyCPLTDao;
import com.bicsoft.sy.entity.BlackList;
import com.bicsoft.sy.entity.BlackListD;
import com.bicsoft.sy.entity.CompanyCPLT;
import com.bicsoft.sy.entity.ReCallRecord;
import com.bicsoft.sy.model.CompanyCPLTModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.BlackListService;
import com.bicsoft.sy.service.CompanyCPLTService;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class CompanyCPLTServiceImpl implements CompanyCPLTService {
	private static final Logger log = LoggerFactory.getLogger(BlackListServiceImpl.class);
	
	@Autowired
	private CompanyCPLTDao companyCPLTDao;

	@Autowired
	private BlackListDao blackListDao;
	
	@Autowired
	private BlackListDDao blackListDDao;
	
	@Autowired
	private ReCallRecordDao reCallRecordDao;
	
	@Autowired
	private BlackListService blackListService;
	
	@Override
	public void save(CompanyCPLTModel companyCPLTModel) {
		try{
			CompanyCPLT companyCPLT = null;
			if(companyCPLTModel.getId() == null){   //新增时
				companyCPLT = (CompanyCPLT) POVOConvertUtil.convert(companyCPLTModel, "com.bicsoft.sy.entity.CompanyCPLT");
				companyCPLT.setSettleStatus("01");   //默认未处理;
				this.companyCPLTDao.save(companyCPLT);
				
				companyCPLTModel.setId( companyCPLT.getId() );
				
				if( companyCPLTModel.getAddBlackList() != null && "1".equals(companyCPLTModel.getAddBlackList()) ){
					//加入黑名单
					BlackList bl = new BlackList();
					bl.setCreateDate( companyCPLTModel.getCreateDate() );
					bl.setCreateUserId( companyCPLTModel.getCreateUserId() );
					bl.setUpdateDate( companyCPLTModel.getUpdateDate() );
					bl.setUpdateUserId( companyCPLTModel.getUpdateUserId() );
				}
				//召回
				if( companyCPLTModel.getRecall() != null && "1".equals( companyCPLTModel.getRecall() ) ){
					ReCallRecord rcr = new ReCallRecord();
					rcr.setCreateDate( companyCPLTModel.getCreateDate() );
					rcr.setCreateUserId( companyCPLTModel.getCreateUserId() );
					rcr.setUpdateDate( companyCPLTModel.getUpdateDate() );
					rcr.setUpdateUserId( companyCPLTModel.getUpdateUserId() );
				}
			}else{   //修改
				companyCPLT = this.companyCPLTDao.queryById(CompanyCPLT.class, companyCPLTModel.getId());
				
				companyCPLT.setProcessResult( companyCPLTModel.getProcessResult() );
				companyCPLT.setProcessor( companyCPLTModel.getProcessor() );
				companyCPLT.setProcessorsMail( companyCPLTModel.getProcessorsMail() );
				companyCPLT.setProcessorsTel( companyCPLTModel.getProcessorsTel() );
				companyCPLT.setSettleDate( companyCPLTModel.getSettleDate() );
				companyCPLT.setCompanyCode( companyCPLTModel.getCompanyCode() );
				companyCPLT.setSettlePepole( companyCPLTModel.getSettlePepole() );
				companyCPLT.setProcessMode( companyCPLTModel.getProcessMode() );
				companyCPLT.setSettleStatus("02");
				companyCPLT.setProductCode( companyCPLTModel.getProductCode() );
				companyCPLT.setUpdateDate(companyCPLTModel.getUpdateDate());
				companyCPLT.setUpdateUserId(companyCPLTModel.getUpdateUserId());
				//是否加入黑名单操作
				if( companyCPLTModel.getAddBlackList() != null && "1".equals(companyCPLTModel.getAddBlackList()) ){
					companyCPLT.setBlackListTimeLimit( companyCPLTModel.getBlackListTimeLimit() );
					companyCPLT.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( companyCPLTModel.getSettleDate(), companyCPLTModel.getBlackListTimeLimit() )  );
				}else{
					companyCPLT.setBlackListTimeLimit( "" );
					companyCPLT.setBlackListEndDate( null );
				}
				
				this.companyCPLTDao.save(companyCPLT);
				/*
				 * 加入黑名单时
				 */
				if( companyCPLTModel.getAddBlackList() != null && "1".equals(companyCPLTModel.getAddBlackList()) ){
					//加入黑名单 -- 操作
					//每次加入黑名单子表加一条记录
					BlackListD bld = new BlackListD();
					bld.setCompanyCode( companyCPLTModel.getCompanyCode() );
					bld.setProcessor( companyCPLTModel.getProcessor() );
					bld.setSettleDate( companyCPLTModel.getSettleDate() );
					bld.setBlackListReason( companyCPLTModel.getRecallReason() == null ? "" : companyCPLTModel.getRecallReason());
					bld.setBlackListTimeLimit( companyCPLTModel.getBlackListTimeLimit() );
					bld.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( companyCPLTModel.getSettleDate(), companyCPLTModel.getBlackListTimeLimit() )  );
					bld.setCreateDate( companyCPLTModel.getCreateDate() );
					bld.setCreateUserId( companyCPLTModel.getCreateUserId() );
					bld.setUpdateDate( companyCPLTModel.getUpdateDate() );
					bld.setUpdateUserId( companyCPLTModel.getUpdateUserId() );
					this.blackListDDao.save( bld );
					
					//加入黑名单主表，一个企业只加一条记录
					BlackList bl = this.blackListService.getBlackListByCompanyCod( companyCPLTModel.getCompanyCode() );
					if( bl != null && bl.getId() != null  ){  //存在记录时
						bl.setBlackListCount( bl.getBlackListCount() == null ? 1 : bl.getBlackListCount()+1 );
						bl.setUpdateDate( companyCPLTModel.getUpdateDate() );
						bl.setUpdateUserId( companyCPLTModel.getUpdateUserId() );
						bl.setSettleDate( companyCPLTModel.getSettleDate() );
						bl.setProcessor( companyCPLTModel.getProcessor() );
						bl.setBlackListReason( companyCPLTModel.getRecallReason() == null ? "" : companyCPLTModel.getRecallReason() );
						bl.setBlackListTimeLimit( companyCPLTModel.getBlackListTimeLimit() );
						bl.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( companyCPLTModel.getSettleDate(), companyCPLTModel.getBlackListTimeLimit() )  );
						
					}else{
						bl = new BlackList();  //不存时新增
						bl.setBlackListCount(1);
						bl.setCreateDate( companyCPLTModel.getCreateDate() );
						bl.setCreateUserId( companyCPLTModel.getCreateUserId() );
						bl.setUpdateDate( companyCPLTModel.getUpdateDate() );
						bl.setUpdateUserId( companyCPLTModel.getUpdateUserId() );
						bl.setCompanyCode( companyCPLTModel.getCompanyCode() );
						bl.setSettleDate( companyCPLTModel.getSettleDate() );
						bl.setProcessor( companyCPLTModel.getProcessor() );
						bl.setBlackListReason( companyCPLTModel.getRecallReason() == null ? "" : companyCPLTModel.getRecallReason());
						bl.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( companyCPLTModel.getSettleDate(), companyCPLTModel.getBlackListTimeLimit() )  );
						bl.setBlackListTimeLimit( companyCPLTModel.getBlackListTimeLimit() );
					}
					this.blackListDao.save( bl );
				}
				//召回
				if( companyCPLTModel.getAddBlackList() != null && "1".equals(companyCPLTModel.getAddBlackList()) &&
						companyCPLTModel.getRecall() != null && "1".equals( companyCPLTModel.getRecall() ) ){
					ReCallRecord rcr = new ReCallRecord();
					rcr.setCompanyCode( companyCPLTModel.getCompanyCode() );
					rcr.setProcessor( companyCPLTModel.getProcessor() );
					rcr.setSettleDate( companyCPLTModel.getSettleDate() );
					rcr.setBatchNo( companyCPLTModel.getBatchNo() );
					rcr.setRecallReason( companyCPLTModel.getRecallReason() == null ? "" : companyCPLTModel.getRecallReason() );
					rcr.setProductCode( companyCPLTModel.getProductCode() );
					rcr.setCreateDate( companyCPLTModel.getCreateDate() );
					rcr.setCreateUserId( companyCPLTModel.getCreateUserId() );
					rcr.setUpdateDate( companyCPLTModel.getUpdateDate() );
					rcr.setUpdateUserId( companyCPLTModel.getUpdateUserId() );
					
					this.reCallRecordDao.save( rcr );
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	
	@Override
	public void delete(int id) {
		this.companyCPLTDao.delete(CompanyCPLT.class, id);
		
	}

	@Override
	public void logicDelete(Class<CompanyCPLT> clazz, int id) {
		this.companyCPLTDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		
		return this.companyCPLTDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public CompanyCPLT getCompanyCPLT(int id) {
		return this.companyCPLTDao.queryById(CompanyCPLT.class, id);
	}

}
