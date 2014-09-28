package com.b5m.goods.promotions.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.b5m.goods.promotions.dto.CouponInfoDto;

public class CouponInfoMapper implements RowMapper<CouponInfoDto> {
	private final SuppliserMapper suppliserMapper = new SuppliserMapper();
	
	@Override
	public CouponInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CouponInfoDto couponInfo = new CouponInfoDto();
		couponInfo.setSuppliser(suppliserMapper.mapRow(rs, rowNum));
		couponInfo.setAmtForOffset(rs.getDouble("amtForOffset"));
		couponInfo.setOffsetAmt(rs.getDouble("offsetAmt"));
		couponInfo.setBrandId(rs.getLong("brandId"));
		couponInfo.setEndValidTime(rs.getString("endValidTime"));
		couponInfo.setId(rs.getLong("couponId"));
		couponInfo.setImage(rs.getString("image"));
		couponInfo.setIntegral(rs.getInt("integral"));
		couponInfo.setName(rs.getString("couponName"));
		couponInfo.setOffsetType(rs.getString("offsetType"));
		couponInfo.setIsRabate(rs.getString("isRabate"));
		couponInfo.setReceivedCount(rs.getInt("receivedCount"));
		couponInfo.setReceiveLimit(rs.getInt("receiveLimit"));
		couponInfo.setRelease(rs.getString("release"));
		couponInfo.setRemark(rs.getString("remark"));
		couponInfo.setStartValidTime(rs.getString("startValidTime"));
		couponInfo.setTotalCount(rs.getInt("totalCount"));
		couponInfo.setType(rs.getString("type"));
		couponInfo.setUseRange(rs.getString("useRange"));
		couponInfo.setValidTimeFlg(rs.getString("validTimeFlg"));
		return couponInfo;
	}

}
