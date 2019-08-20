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
import com.bicsoft.sy.dao.SeedCPLTDao;
import com.bicsoft.sy.entity.BlackList;
import com.bicsoft.sy.entity.BlackListD;
import com.bicsoft.sy.entity.ReCallRecord;
import com.bicsoft.sy.entity.SeedCPLT;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SeedCPLTModel;
import com.bicsoft.sy.service.BlackListService;
import com.bicsoft.sy.service.SeedCPLTService;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class SeedCPLTServiceImpl implements SeedCPLTService {
	private static final Logger log = LoggerFactory.getLogger(SeedCPLTServiceImpl.class);
	
	@Autowired
	private SeedCPLTDao seedCPLTDao;
	
	@Autowired
	private BlackListDao blackListDao;
	
	@Autowired
	private BlackListDDao blackListDDao;
	
	@Autowired
	private ReCallRecordDao reCallRecordDao;
	
	@Autowired
	private BlackListService blackListService;
	
	@Override
	public SeedCPLT getSeedCPLT(int id) {
		return this.seedCPLTDao.queryById(SeedCPLT.class, id);
	}

	@Override
	public void save(SeedCPLTModel seedCPLTModel) {
		try{
			SeedCPLT seedCPLT = null;
			if(seedCPLTModel.getId() == null){
				seedCPLT = (SeedCPLT) POVOConvertUtil.convert(seedCPLTModel, "com.bicsoft.sy.entity.SeedCPLT");
				seedCPLT.setCompanyCode( seedCPLTModel.getCompanyCode() == null ? "" : seedCPLTModel.getCompanyCode() );
				this.seedCPLTDao.save(seedCPLT);
				
				seedCPLTModel.setId( seedCPLT.getId() );
			}else{  //修改或处理
				seedCPLT = this.seedCPLTDao.queryById(SeedCPLT.class, seedCPLTModel.getId());
				
				seedCPLT.setProductionWeight( seedCPLTModel.getProductionWeight() );
				seedCPLT.setProcessResult( seedCPLTModel.getProcessResult() );
				seedCPLT.setProcessor( seedCPLTModel.getProcessor() );
				seedCPLT.setProcessorsMail( seedCPLTModel.getProcessorsMail() );
				seedCPLT.setProcessorsTel( seedCPLTModel.getProcessorsTel() );
				seedCPLT.setSettleDate( seedCPLTModel.getSettleDate() );
				seedCPLT.setCompanyCode( seedCPLTModel.getCompanyCode() );
				seedCPLT.setProcessMode( seedCPLTModel.getProcessMode() );
				seedCPLT.setSettleStatus("02");//已处理
				seedCPLT.setUpdateDate(seedCPLTModel.getUpdateDate());
				seedCPLT.setUpdateUserId(seedCPLTModel.getUpdateUserId());
				//是否加入黑名单操作
				if( seedCPLTModel.getAddBlackList() != null && "1".equals(seedCPLTModel.getAddBlackList()) ){
					seedCPLT.setBlackListTimeLimit( seedCPLTModel.getBlackListTimeLimit() );
					seedCPLT.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( seedCPLTModel.getSettleDate(), seedCPLTModel.getBlackListTimeLimit() )  );
				}else{
					seedCPLT.setBlackListTimeLimit( "" );
					seedCPLT.setBlackListEndDate( null  );
				}
				
				this.seedCPLTDao.save(seedCPLT);
				/*
				 * 加入黑名单时
				 */
				if( seedCPLTModel.getAddBlackList() != null && "1".equals(seedCPLTModel.getAddBlackList()) ){
					//加入黑名单 -- 操作
					//每次加入黑名单子表加一条记录
					BlackListD bld = new BlackListD();
					bld.setCompanyCode( seedCPLTModel.getCompanyCode() );
					bld.setProcessor( seedCPLTModel.getProcessor() );
					bld.setSettleDate( seedCPLTModel.getSettleDate() );
					bld.setBlackListReason( "" );
					bld.setBlackListTimeLimit( seedCPLTModel.getBlackListTimeLimit() );
					bld.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( seedCPLTModel.getSettleDate(), seedCPLTModel.getBlackListTimeLimit() )  );
					bld.setCreateDate( seedCPLTModel.getCreateDate() );
					bld.setCreateUserId( seedCPLTModel.getCreateUserId() );
					bld.setUpdateDate( seedCPLTModel.getUpdateDate() );
					bld.setUpdateUserId( seedCPLTModel.getUpdateUserId() );
					this.blackListDDao.save( bld );
					
					//加入黑名单主表，一个企业只加一条记录
					BlackList bl = this.blackListService.getBlackListByCompanyCod( seedCPLTModel.getCompanyCode() );
					if( bl != null && bl.getId() != null  ){  //存在记录时
						bl.setBlackListCount( bl.getBlackListCount() == null ? 1 : bl.getBlackListCount()+1 );
						bl.setUpdateDate( seedCPLTModel.getUpdateDate() );
						bl.setUpdateUserId( seedCPLTModel.getUpdateUserId() );
						bl.setSettleDate( seedCPLTModel.getSettleDate() );
						bl.setProcessor( seedCPLTModel.getProcessor() );
						bl.setBlackListReason( seedCPLTModel.getProcessResult() );
						bl.setBlackListTimeLimit( seedCPLTModel.getBlackListTimeLimit() );
						bl.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( seedCPLTModel.getSettleDate(), seedCPLTModel.getBlackListTimeLimit() )  );
					}else{
						bl = new BlackList();  //不存时新增
						bl.setBlackListCount(1);
						bl.setCreateDate( seedCPLTModel.getCreateDate() );
						bl.setCreateUserId( seedCPLTModel.getCreateUserId() );
						bl.setUpdateDate( seedCPLTModel.getUpdateDate() );
						bl.setUpdateUserId( seedCPLTModel.getUpdateUserId() );
						bl.setCompanyCode( seedCPLTModel.getCompanyCode() );
						bl.setSettleDate( seedCPLTModel.getSettleDate() );
						bl.setProcessor( seedCPLTModel.getProcessor() );
						bl.setBlackListReason( seedCPLTModel.getProcessResult() );
						bl.setBlackListEndDate( DateTimeUtil.computerBlackListEndDate( seedCPLTModel.getSettleDate(), seedCPLTModel.getBlackListTimeLimit() )  );
						bl.setBlackListTimeLimit( seedCPLTModel.getBlackListTimeLimit() );
					}
					this.blackListDao.save( bl );
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public void delete(int id) {
		this.seedCPLTDao.delete(SeedCPLT.class, id);
	}

	@Override
	public void logicDelete(Class<SeedCPLT> clazz, int id) {
		this.seedCPLTDao.logicDelete(SeedCPLT.class, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.seedCPLTDao.queryForPageModel(entityName, params, pageModel);
	}
}
