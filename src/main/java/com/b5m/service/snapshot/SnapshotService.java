package com.b5m.service.snapshot;
/**
 * description 
 * 快照保存
 * @Company b5m
 * @author echo
 * @since 2014年1月8日
 */
public interface SnapshotService {
	
	void saveSnapshot(String docId, String collection);
	
}
