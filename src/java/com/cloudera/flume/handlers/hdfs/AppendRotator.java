package com.cloudera.flume.handlers.hdfs;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudera.flume.conf.FlumeConfiguration;

/**
 * A helper class to store the roll tag and count for each file in HDFS.
 * The file is a full path HDFS file with all escaped fields being replace 
 * with real values. For example, a file here can be
 * /user/hive/warehouse/apache_log_table/log_type=%{log_type}/date=%{date}/%H-%{rolltag}.gz
 * After replacing the escaped fields, above file will be 
 * /user/hive/warehouse/apache_log_table/log_type=ACCESS/date=20130320/09-rolltag1.gz
 * A {@link LinkedHashMap} is used here to store the map of path to rolltag and count. 
 * Hash key is the path without rolltag, value is the rolltag and count.
 * Each time the count of the lines for this file reaches the limit 
 * "dfs.append.event.count" in {@link FlumeConfiguration}, the rolltag will 
 * be renewed and counter is reset.
 */
public class AppendRotator {
	static final Logger LOG = LoggerFactory.getLogger(AppendRotator.class);
	
	/**
	 * A class stores the rolltag (use appendTag here to distinguish with the 
	 * rolltag used in non-appending manner) and the line count 
	 */
	class RotationHelper {
		
		private String appendTag;
		private AtomicLong appendCounter;
		
		private volatile boolean shouldRotate = false;
		
		public RotationHelper () {
			rotate();
		}
		
		public void rotate() {
			appendTag = Long.toString(System.currentTimeMillis()) + "-"
				      + Long.toString(System.nanoTime());
			appendCounter = new AtomicLong();
		}
		
		public long incr() {
			return appendCounter.getAndIncrement();
		}
		
		public String getAppendTag() {
			//CheckAndRotate();
			return this.appendTag;
		}
		
		public long getAppendCount() {
			//CheckAndRotate();
			return this.appendCounter.get();			
		}
		
		public void CheckAndRotate() {
			if ( this.shouldRotate == true ) {
				rotate();
			}
		}
	}
	
	private final int CACHE_SIZE_MAX = FlumeConfiguration.get().getInt(
			"flume.append.path.cache.size", 10000);
	
	/**
	 * {@link LinkedHashMap} sorted by _access_, override {@link removeEldestEntry } 
	 * to evict the oldest keys when reaching the limit.
	 */
	private Map<String, RotationHelper> cacheMap = 
			new LinkedHashMap<String, RotationHelper>(16, .75F, true) {
	    protected boolean removeEldestEntry(Map.Entry eldest) {
	    	boolean remove = size() > CACHE_SIZE_MAX;
	    	if ( remove ) {
	    		LOG.info("Remove cache entry: " + eldest.getKey().toString());
	    	}
	        return remove;
	    }
	};
	
	protected Map<String, RotationHelper> rotateMap = Collections.synchronizedMap(cacheMap);
	
	public AppendRotator() {		
	}
	
	public synchronized boolean contains(String path) {
		boolean exist = true;
		if ( ! rotateMap.containsKey(path) ) {
			exist = false;
		}
		return exist;
	}
	
	public synchronized void checkAndAdd(String path) {
		if ( ! contains(path) ) {
			rotateMap.put(path, new RotationHelper());
		}
	}

	public synchronized void setAppendTag(String path) {
		rotateMap.put(path, new RotationHelper());
	}
	
	public synchronized String getAppendTag(String path) {
		checkAndAdd(path);
		RotationHelper rhelper = rotateMap.get(path);
		return rhelper.getAppendTag();
	}
	
	public synchronized long getAppendCount(String path) {
		checkAndAdd(path);
		RotationHelper rhelper = rotateMap.get(path);
		return rhelper.getAppendCount();
	}
	
	public synchronized void incr(String path) {
		checkAndAdd(path);
		rotateMap.get(path).incr();
	}
	
	public synchronized void rotate(String path) {
		checkAndAdd(path);
		rotateMap.get(path).rotate();
	}
	
	public synchronized void rotateAll() {
		for ( String path : this.rotateMap.keySet() ) {
			this.rotateMap.get(path).rotate();
		}
	}
	
	public synchronized void clear() {
		this.rotateMap.clear();
	}
}
