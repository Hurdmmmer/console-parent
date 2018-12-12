package com.tdh.gps.console.utils;

import java.util.UUID;

/**
 * 
 * @ClassName: GuidGenerator  
 * @Description: (UUID生成器)  
 * @author wxf
 * @date 2018年12月6日 下午2:34:03  
 *
 */
public abstract class GuidGenerator {


    /**
     * private constructor
     */
    private GuidGenerator() {
    }

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}