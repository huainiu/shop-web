package com.b5m.goods.promotions.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.b5m.goods.promotions.dto.PromInfoDto;

public class PromInfoMapper implements RowMapper<PromInfoDto> {
	
	private final SuppliserMapper suppliserMapper = new SuppliserMapper();
	@Override
	public PromInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PromInfoDto promInfo = new PromInfoDto();
		promInfo.setSuppliser(suppliserMapper.mapRow(rs, rowNum));
		promInfo.setBrandId(rs.getLong("brandId"));
		promInfo.setClickCount(rs.getInt("clickCount"));
		promInfo.setEndTime(rs.getDate("endTime"));
		promInfo.setFeature(rs.getString("feature"));
		promInfo.setGroupId(rs.getLong("groupId"));
		promInfo.setId(rs.getLong("promId"));
		promInfo.setImage(rs.getString("image"));
		promInfo.setLink(rs.getString("link"));
		promInfo.setName(rs.getString("promName"));
		promInfo.setRemark(rs.getString("remark"));
		promInfo.setStartTime(rs.getDate("startTime"));
		promInfo.setTag(rs.getString("tag"));
		promInfo.setTop(rs.getString("top"));
		promInfo.setTopTime(rs.getDate("topTime"));	
		return promInfo;
	}

}
