package com.tdh.gps.console.service.api;

import java.util.List;
import java.util.Set;

/**
 * 
 * @ClassName: RedisService  
 * @Description: (redis服务接口类)  
 * @author wxf
 * @date 2018年11月16日 下午12:21:14  
 *
 */
public interface RedisService {

	Long isLimit(String key, long expireTime, int limitTimes);

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) ;

	/**
	 * 写入缓存设置时效时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) ;

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) ;

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) ;

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) ;

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) ;

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) ;

	/**
	 * 哈希 添加
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, Object hashKey, Object value) ;

	/**
	 * 哈希获取数据
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey) ;

	/**
	 * 列表添加
	 * 
	 * @param k
	 * @param v
	 */
	public void lPush(String k, Object v);

	/**
	 * 列表获取
	 * 
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 */
	public List<Object> lRange(String k, long l, long l1) ;

	/**
	 * 集合添加
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value);

	/**
	 * 集合获取
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) ;

	/**
	 * 有序集合添加
	 * 
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void zAdd(String key, Object value, double scoure) ;

	/**
	 * 有序集合获取
	 * 
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1);

	/**
	 *  添加集合
	 * @param key
	 * @param list
	 * @param expireTime
	 * @param <T>
	 * @return
	 */
	<T>boolean addList(String key, List<T> list, long expireTime);

	/**
	 *  获取 list 集合， 根据指定的下标
	 * @param key
	 * @param startIndex
	 * @param endIndex
	 * @param <T>
	 * @return
	 */
	<T> List<T> getRange(String key, long startIndex, long endIndex);
}
