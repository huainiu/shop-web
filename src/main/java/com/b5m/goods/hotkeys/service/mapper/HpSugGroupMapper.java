package com.b5m.goods.hotkeys.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.b5m.goods.hotkeys.dto.HpSugGroupDto;

public class HpSugGroupMapper implements RowMapper<HpSugGroupDto> {

	@Override
	public HpSugGroupDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		HpSugGroupDto dto = new HpSugGroupDto();
		dto.setContent(rs.getString("content"));
		dto.setDescription(rs.getString("description"));
		dto.setGroupName(rs.getString("groupName"));
		dto.setId(rs.getInt("id"));
		dto.setKeywords(rs.getString("keywords"));
		dto.setOrderSeq(rs.getInt("orderSeq"));
		dto.setParentId(rs.getInt("parentId"));
		dto.setTitle(rs.getString("title"));
		dto.setUrl(rs.getString("url"));
		return dto;
	}

}
