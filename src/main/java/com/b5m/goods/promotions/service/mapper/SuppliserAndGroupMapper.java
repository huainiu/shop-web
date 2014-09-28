package com.b5m.goods.promotions.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.dto.SuppliserGroupDto;

public class SuppliserAndGroupMapper implements RowMapper<SuppliserDto> {
	private final SuppliserMapper suppliserMaper = new SuppliserMapper();
	@Override
	public SuppliserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		SuppliserDto dto = suppliserMaper.mapRow(rs, rowNum);
		SuppliserGroupDto group = dto.getGroup();
		group.setName(rs.getString("groupName"));
		return dto;
	}

}
