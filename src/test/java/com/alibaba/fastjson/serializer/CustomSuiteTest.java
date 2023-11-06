package com.alibaba.fastjson.serializer;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.alibaba.json.bvt.support.spring.FastJsonRedisSerializerTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestParse.class,
    FastJsonRedisSerializerTest.class
    })


public class CustomSuiteTest {
}
