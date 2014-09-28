package com.b5m.goods.promotions.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.b5m.goods.promotions.dto.CPSInfoDto;
import com.b5m.goods.promotions.dto.CouponInfoDto;
import com.b5m.goods.promotions.dto.PromInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.dto.SuppliserPromotionsDto;
import com.b5m.goods.promotions.service.ICPSService;
import com.b5m.goods.promotions.service.ICouponService;
import com.b5m.goods.promotions.service.IPromotionService;
import com.b5m.goods.promotions.service.ISuppliserService;
import com.b5m.goods.promotions.service.mapper.SuppliserAndGroupMapper;
import com.b5m.goods.promotions.service.mapper.SuppliserMapper;

public class SuppliserServiceImpl implements ISuppliserService {

	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;
	
	@Resource
	private ICouponService couponService;
	
	@Resource
	private ICPSService cpsService;
	
	@Resource
	private IPromotionService promotionService;
	
	private final String SQL_findAllSuppliser = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
				.append(",").append(DtoSqlColumns.COL_CMS_SUPPLISER_GROUP)
				.append(" from t_cms_suppliser as s left join t_cms_suppliser_group as g on s.groupId = g.id").toString();
	
	private final String SQL_findByCountry = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
				.append(",").append(DtoSqlColumns.COL_CMS_SUPPLISER_GROUP)
				.append(" from t_cms_suppliser as s left join t_cms_suppliser_group as g on s.groupId = g.id")
				.append(" where country = ? ").toString();
	
	@Override
	public SuppliserPromotionsDto findBySource(String source) {
		SuppliserPromotionsDto spd = new SuppliserPromotionsDto();
		SuppliserDto queryDto = new SuppliserDto();
		queryDto.setName(source);
		List<CPSInfoDto> cpsInfos = cpsService.findBySuppliser(queryDto);
		if(cpsInfos.size() > 0){
			spd.setCpsInfo(cpsInfos.get(0));
			spd.setSuppliser(spd.getCpsInfo().getSuppliser());
		}
		List<PromInfoDto> promInfos = promotionService.findBySuppliser(queryDto);
		spd.setPromInfos(promInfos);
		List<CouponInfoDto> couponInfos = couponService.findBySuppliser(queryDto);
		spd.setCouponInfos(couponInfos);
		
		// 判断三个属性是否都存在，只要三个存在一个，则设置containAnyPromotions为true，如果都不存在则设置为containAnyPromotions为false
		if(spd.getCpsInfo() == null && spd.getCouponInfos().size() == 0
				&& spd.getPromInfos().size() == 0){
			spd.setContainAnyPromotions(false);
		}else{
			spd.setContainAnyPromotions(true);
		}
		return spd;
	}

	@Override
	public List<SuppliserDto> findAllSuppliser() {
		return template.query(SQL_findAllSuppliser, new SuppliserAndGroupMapper());
	}

	public List<SuppliserDto> querySupplier(String keyword){
		return template.query(" select name, logo, url from t_cms_suppliser where name like '%" + keyword + "%'", new SuppliserInfoMapper());
	}
	
	private class SuppliserInfoMapper implements RowMapper<SuppliserDto>{

		@Override
		public SuppliserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			SuppliserDto dto = new SuppliserDto();
			dto.setName(rs.getString("name"));
			dto.setLogo(rs.getString("logo"));
			dto.setUrl(rs.getString("url"));
			return dto;
		}
		
	}
	
	@Override
	public Map<String, SuppliserPromotionsDto> findAllSuppliserPromotion() {
		Map<String, List<CouponInfoDto>> mapCoupon = couponService.findAllValidCoupon();
		Map<String, CPSInfoDto> mapCPS = cpsService.findAllCPSInfo();
		Map<String, List<PromInfoDto>> mapProm = promotionService.findAllValidPromInfo();
		List<SuppliserDto> allSupplisers = findAllSuppliser();
		Map<String, SuppliserPromotionsDto> mapSP = new HashMap<String, SuppliserPromotionsDto>(allSupplisers.size());
		
		for(SuppliserDto suppliser : allSupplisers){
			SuppliserPromotionsDto spd = new SuppliserPromotionsDto();
			if(mapCoupon.containsKey(suppliser.getName())){
				spd.setContainAnyPromotions(true);
				spd.setCouponInfos(mapCoupon.get(suppliser.getName()));
			}
			if(mapCPS.containsKey(suppliser.getName())){
				spd.setContainAnyPromotions(true);
				spd.setCpsInfo(mapCPS.get(suppliser.getName()));
			}
			if(mapProm.containsKey(suppliser.getName())){
				spd.setContainAnyPromotions(true);
				spd.setPromInfos(mapProm.get(suppliser.getName()));
			}
			
			if(spd.isContainAnyPromotions()){
				mapSP.put(suppliser.getName(), spd);
			}
			spd.setSuppliser(suppliser);
		}
		
		return mapSP;
	}

	@Override
	public List<SuppliserDto> findByCountry(String country) {
		final String __country = country;		
		return template.query(new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstat = conn.prepareStatement(SQL_findByCountry);
				pstat.setString(1, __country);
				return pstat;
			}
			
		}, new SuppliserMapper());
	}

}
