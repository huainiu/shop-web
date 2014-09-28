package com.b5m.goods.promotions.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.b5m.goods.promotions.dto.CPSChannelDto;
import com.b5m.goods.promotions.dto.CPSInfoDto;

public class CPSInfoMapper implements RowMapper<CPSInfoDto> {
	private final SuppliserMapper suppliserMapper = new SuppliserMapper();
	
	private CPSChannelDto mapRow2CPSChannel(ResultSet rs, int rowNum) throws SQLException{
		CPSChannelDto channel = new CPSChannelDto();
		channel.setId(rs.getInt("channelId"));
		channel.setLink(rs.getString("channelLink"));
		channel.setName(rs.getString("channelName"));
		return channel;
	}
	
	@Override
	public CPSInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CPSInfoDto cpsInfo = new CPSInfoDto();
		cpsInfo.setSuppliser(suppliserMapper.mapRow(rs, rowNum));
		cpsInfo.setChannel(mapRow2CPSChannel(rs, rowNum));
		cpsInfo.setActionId(rs.getLong("actionId"));
		cpsInfo.setCommisionRatio(rs.getString("commisionRatio"));
		cpsInfo.setCustomLink(rs.getString("customLink"));
		cpsInfo.setDescription(rs.getString("description"));
		cpsInfo.setId(rs.getInt("cpsId"));
		cpsInfo.setLink(rs.getString("cpsLink"));
		cpsInfo.setState(rs.getString("state"));
		return cpsInfo;
	}

}
