package com.b5m.service.www.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.b5m.bean.entity.Word;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.WordService;
import com.b5m.web.filter.KeywordFilter;

@Service("wordServiceImpl")
public class WordServiceImpl implements WordService {
	
	@Autowired
	@Qualifier("dao")
	private Dao dao;

	public List<Word> queryAll() {
		return dao.queryAll(Word.class);
	}

	@Override
	public void addWord(Word word) {
		Word res = queryWordByKey(word.getWord());
		if (res == null) {
			dao.insert(word);
			KeywordFilter.addKeyWord(word.getWord());
		} else {
			if (res.getIsDelete() == 1) {
				res.setIsDelete(0);
				dao.update(res);
			}
		}
	}

	@Override
	public void update(Word word) {
		Word res = queryWordByKey(word.getWord());
		if (res == null)
			dao.update(word);
	}

	@Override
	public void delete(Long id) {
		Word word = new Word();
		word.setId(id);
		word.setIsDelete(1);
		dao.update(word);
	}

	@Override
	public Word queryWord(String word) {
		List<Word> list = dao.query(Word.class, Cnd.where("word", Op.LIKE, word));
		if (list.isEmpty()) return null;
		else return list.get(0);
	}

	@Override
	public Word queryWordByKey(String word) {
		List<Word> list = dao.query(Word.class, Cnd.where("word", Op.EQ, word));
		if (list.isEmpty()) return null;
		else return list.get(0);
	}

	@Override
	public Word getWord(Long id) {
		return dao.get(Word.class, id);
	}

	@Override
	public PageView<Word> queryWordPage(String word, PageCnd cnd) {
		return dao.queryPage(Word.class, Cnd._new().add("word", Op.LIKE, word).asc("id"), cnd);
	}

}
