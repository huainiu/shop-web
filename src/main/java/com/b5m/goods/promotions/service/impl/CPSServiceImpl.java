package com.b5m.goods.promotions.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import com.b5m.goods.promotions.dto.CPSInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.ICPSService;
import com.b5m.goods.promotions.service.mapper.CPSInfoMapper;

@Service
public class CPSServiceImpl implements ICPSService {
	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;
	
	private final String SQL_findBySuppliserId = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
				.append(",").append(DtoSqlColumns.COL_CPS_INFO).append(",").append(DtoSqlColumns.COL_CPS_CHANNEL)
				.append(" from t_cms_suppliser as s right join t_cps_info as i on s.id=i.suppliserId and (i.state='正常' or i.state='隐藏')")
				.append(" left join t_cps_channel as c on i.channelId=c.id")
				.append(" where s.id=? ").toString();
	
	private final String SQL_findBySuppliserName = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
			.append(",").append(DtoSqlColumns.COL_CPS_INFO).append(",").append(DtoSqlColumns.COL_CPS_CHANNEL)
			.append(" from t_cms_suppliser as s right join t_cps_info as i on s.id=i.suppliserId and (i.state='正常' or i.state='隐藏')")
			.append(" left join t_cps_channel as c on i.channelId=c.id")
			.append(" where s.name=? ").toString();
	
	private final String SQL_findAllCPSInfo = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
			.append(",").append(DtoSqlColumns.COL_CPS_INFO).append(",").append(DtoSqlColumns.COL_CPS_CHANNEL)
			.append(" from t_cms_suppliser as s right join t_cps_info as i on s.id=i.suppliserId and (i.state='正常' or i.state='隐藏')")
			.append(" left join t_cps_channel as c on i.channelId=c.id").toString();

	@Override
	public List<CPSInfoDto> findBySuppliser(SuppliserDto suppliser) {
		if(suppliser.getId() != null){
			final int suppliserId = suppliser.getId();
			return template.query(new PreparedStatementCreator(){

				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement pstat = conn.prepareStatement(SQL_findBySuppliserId);
					pstat.setInt(1, suppliserId);
					return pstat;
				}
				
			}, new CPSInfoMapper());
		}else if(suppliser.getName() != null){
			final String name = suppliser.getName();
			return template.query(new PreparedStatementCreator(){

				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement pstat = conn.prepareStatement(SQL_findBySuppliserName);
					pstat.setString(1, name);
					return pstat;
				}
				
			}, new CPSInfoMapper());
		}
		return new ArrayList<CPSInfoDto>();
	}

	private Map<String, CPSInfoDto> convertToMap(List<CPSInfoDto> cpsInfos){
		Map<String, CPSInfoDto> mapCps = new HashMap<String, CPSInfoDto>(cpsInfos.size());
		for(CPSInfoDto cpsInfo : cpsInfos){
			mapCps.put(cpsInfo.getSuppliser().getName(), cpsInfo);
		}
		return mapCps;
	}
	
	@Override
	public Map<String, CPSInfoDto> findAllCPSInfo() {		
		return convertToMap(
				template.query(SQL_findAllCPSInfo, new CPSInfoMapper())
				);
	}

}
