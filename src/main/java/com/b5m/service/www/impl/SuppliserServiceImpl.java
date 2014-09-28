package com.b5m.service.www.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.Suppliser;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.service.www.SuppliserService;

@Service("newSuppliserService")
public class SuppliserServiceImpl implements SuppliserService {

	@Autowired
	@Qualifier("dao")
	private Dao dao;
	
	@Override
	public List<Suppliser> listSuppliser() {
		return dao.queryAll(Suppliser.class);
	}
	
	@Override
	public void saveSuppliser(Suppliser suppliser) {
		suppliser.setCreateTime(new Date());
		suppliser.setUpdateTime(new Date());
		dao.insert(suppliser);
	}

	@Override
	public void removeSuppliser(Long id) {
		dao.delete(id);
	}

	@Override
	public void updateSuppliser(Suppliser suppliser) {
		dao.update(suppliser);
	}
	
	@Override
	public Suppliser querySuppliserById(Long id){
		return dao.get(Suppliser.class, id);
	}

	@Override
	public Suppliser getSuppliserByName(String name) {
		List<Suppliser> supplisers = dao.query(Suppliser.class, Cnd.where("name", Op.EQ, name));
		if(supplisers.isEmpty()) return null;
		return supplisers.get(0);
	}
	
}
