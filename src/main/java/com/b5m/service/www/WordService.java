package com.b5m.service.www;

import java.util.List;

import com.b5m.bean.entity.Word;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;

public interface WordService {

	List<Word> queryAll();
	
	void addWord(Word word);
	
	void update(Word word);
	
	void delete(Long id);
	
	Word queryWord(String word);

	PageView<Word> queryWordPage(String word, PageCnd cnd);

	Word getWord(Long id);

	Word queryWordByKey(String word);
}
