package com.b5m.goods.promotions.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import com.b5m.goods.promotions.dto.CouponInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.ICouponService;
import com.b5m.goods.promotions.service.mapper.CouponInfoMapper;

@Service
public class CouponServiceImpl implements ICouponService {

	@Resource(name="jdbcTemplate")
	private JdbcTemplate template;
	
	private final String SQL_findBySuppliserId = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
				.append(",").append(DtoSqlColumns.COL_CMS_COUPONS).append(" from t_cms_suppliser as s left join t_cms_coupon as c on s.id = c.suppliser ")
				.append("where s.id=? and c.release='YES' and (c.validTimeFlg='FOREVER' or (c.startValidTime<=? and c.endValidTime>=?)) ")
				.append("order by c.updateTime desc").toString();
	
	private final String SQL_findBySuppliserName = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
				.append(",").append(DtoSqlColumns.COL_CMS_COUPONS).append(" from t_cms_suppliser as s left join t_cms_coupon as c on s.id = c.suppliser ")
				.append("where s.name=? and c.release='YES' and (c.validTimeFlg='FOREVER' or (c.startValidTime<=? and c.endValidTime>=?)) ")
				.append("order by c.updateTime desc").toString();
	
	private final String SQL_findAllValidCoupon = new StringBuilder("select ").append(DtoSqlColumns.COL_CMS_SUPPLISER)
				.append(",").append(DtoSqlColumns.COL_CMS_COUPONS).append(" from t_cms_suppliser as s left join t_cms_coupon as c on s.id = c.suppliser ")
				.append("where c.release='YES' and (c.validTimeFlg='FOREVER' or (c.startValidTime<=? and c.endValidTime>=?)) ")
				.append("order by c.updateTime desc").toString();
	
	@Override
	public List<CouponInfoDto> findBySuppliser(SuppliserDto suppliser) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		final String nowDateStr = format.format(new Date());
		if(suppliser.getId() != null){
			final int suppliserId = suppliser.getId();
			return template.query(new PreparedStatementCreator(){

				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement pstat = conn.prepareStatement(SQL_findBySuppliserId);
					pstat.setInt(1, suppliserId);
					pstat.setString(2, nowDateStr);
					pstat.setString(3, nowDateStr);
					return pstat;
				}
				
			}, new CouponInfoMapper());
		}else if(suppliser.getName() != null){
			final String name = suppliser.getName();
			return template.query(new PreparedStatementCreator(){

				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement pstat = conn.prepareStatement(SQL_findBySuppliserName);
					pstat.setString(1, name);
					pstat.setString(2, nowDateStr);
					pstat.setString(3, nowDateStr);
					return pstat;
				}
				
			}, new CouponInfoMapper());
		}
		return new ArrayList<CouponInfoDto>();
	}
	
	private Map<String, List<CouponInfoDto>> convertToMap(List<CouponInfoDto> coupons){
		Map<String, List<CouponInfoDto>> mapCoupons = new HashMap<String, List<CouponInfoDto>>(coupons.size());
		List<CouponInfoDto> suppliserCoupons = null;
		String lastSuppliser = "";
		for(CouponInfoDto coupon : coupons){
			if(!lastSuppliser.equals(coupon.getSuppliser().getName())){
				if(null != suppliserCoupons){
					mapCoupons.put(lastSuppliser, suppliserCoupons);
				}
				lastSuppliser = coupon.getSuppliser().getName();
				suppliserCoupons = new ArrayList<CouponInfoDto>();
			}
			suppliserCoupons.add(coupon);
		}
		if(null != suppliserCoupons){
			mapCoupons.put(suppliserCoupons.get(0).getSuppliser().getName(), suppliserCoupons);
		}
		return mapCoupons;
	}

	@Override
	public Map<String, List<CouponInfoDto>> findAllValidCoupon() {	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		final String nowDateStr = format.format(new Date());
		return convertToMap(template.query(new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement pstat = conn.prepareStatement(SQL_findAllValidCoupon);
				pstat.setString(1, nowDateStr);
				pstat.setString(2, nowDateStr);
				return pstat;
			}
			
		}, new CouponInfoMapper()));
	}

}
