package com.b5m.service.topic;

import java.util.List;

import com.b5m.bean.entity.Info;
import com.b5m.bean.entity.Key;
import com.b5m.bean.entity.Meta;


public interface TopicSEOService {

	List<Info> getInfos(String keyword);

	Info getInfo(Long id);

	List<Key> getKeys();

	String getLink(String name);

	Meta getMeta(String pageName);

}
