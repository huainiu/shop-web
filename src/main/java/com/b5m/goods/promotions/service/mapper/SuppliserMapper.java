package com.b5m.goods.promotions.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.b5m.common.env.Sf1Constants;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.dto.SuppliserGroupDto;

public class SuppliserMapper implements RowMapper<SuppliserDto> {

	@Override
	public SuppliserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		SuppliserDto suppliser = new SuppliserDto();
		suppliser.setCarriage(rs.getString("carriage"));
		suppliser.setClientService(rs.getString("clientService"));
		suppliser.setCode(rs.getString("code"));
		suppliser.setCommentSum(rs.getInt("commentSum"));
		suppliser.setHotRate(rs.getInt("hotRate"));
		suppliser.setId(rs.getInt("id"));
		suppliser.setIncludeState(rs.getString("includeState"));
		suppliser.setLogo(rs.getString("logo"));
		suppliser.setName(rs.getString("name"));
		suppliser.setRemark(rs.getString("remark"));
		suppliser.setServiceRate(rs.getInt("serviceRate"));
		suppliser.setSpecialService(rs.getString("specialService"));
		suppliser.setSumRate(rs.getInt("sumRate"));
		suppliser.setSuppliserDesc(rs.getString("suppliserDesc"));
		suppliser.setTransRate(rs.getInt("transRate"));
		suppliser.setUrl(rs.getString("url"));
		suppliser.setValidState(rs.getString("validState"));
		suppliser.setIsMall(rs.getInt("isMall"));
		suppliser.setMallId(rs.getInt("mallId"));
		suppliser.setIcon(rs.getString("suppliserIcon"));
		suppliser.setCountry(rs.getString("country"));
		suppliser.setBroadbuy(rs.getString("broadbuy"));
		suppliser.setPayType(rs.getString("payType"));
		suppliser.setLanuage(rs.getString("language"));
		suppliser.setMall(rs.getString("mall"));
		SuppliserGroupDto group = new SuppliserGroupDto();
		group.setId(rs.getInt("groupId"));
		suppliser.setGroup(group);
		
		String country = suppliser.getCountry();
        if (null != country && !"".equals(country.trim())) {
            String[] countryArr = country.split(",");
            for (String str : countryArr) {
                if (Sf1Constants.SUPPLISER_COUNTRY_KOREA.equals(str.trim())) {
                    suppliser.setIsKoreaSpl(true);
                }
            }
        }
        
		return suppliser;
	}

}
