package com.b5m.cached.template;

/**
 * 资源提取器，将数据源中的数据提取出来。提取出来的数据将放到缓存中。
 * @author jacky
 *
 */
public interface ISourceExtracter<T> {

	/**
	 * 从source中提取数据，并返回出来。
	 * @param args TODO
	 * @param source
	 * @return
	 */
	public T extract(Object... args);
}
