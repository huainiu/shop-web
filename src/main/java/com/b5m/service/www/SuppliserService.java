package com.b5m.service.www;

import java.util.List;

import com.b5m.bean.entity.Suppliser;

public interface SuppliserService {

	List<Suppliser> listSuppliser();
	
	void saveSuppliser(Suppliser suppliser);
	
	void removeSuppliser(Long id);
	
	void updateSuppliser(Suppliser suppliser);
	
	Suppliser querySuppliserById(Long id);
	
	Suppliser getSuppliserByName(String name);
	
}
