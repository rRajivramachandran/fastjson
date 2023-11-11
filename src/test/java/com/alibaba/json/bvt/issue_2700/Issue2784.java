package com.alibaba.json.bvt.issue_2700;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class Issue2784 extends TestCase {
    public void test_for_issue() throws Exception {
        Model m = new Model();
        m.time = waitUntilZeroBeyondMillis();

        String str = JSON.toJSONString(m);
        assertEquals("{\"time\":"
                + m.time.atZone(JSON.defaultTimeZone.toZoneId()).toInstant().toEpochMilli()
                + "}", str);
        Model m1 = JSON.parseObject(str, Model.class);
        assertEquals(m.time, m1.time);
    }

    private static LocalDateTime waitUntilZeroBeyondMillis() throws InterruptedException {
        while (true) {
            LocalDateTime currentTime = LocalDateTime.now();
            ZonedDateTime zoned = currentTime.atZone(JSON.defaultTimeZone.toZoneId());

            System.out.println("SEC:"+zoned.toInstant().getNano() % 1000000);
            if (zoned.toInstant().getNano() % 1000000 == 0 ) {
                return currentTime;
            }

            // Wait for a short interval before checking again
            Thread.sleep(10);
        }
    }

    public void test_for_issue_1() throws Exception {
        Model m = new Model();
        m.ztime = ZonedDateTime.now();
        String str = JSON.toJSONString(m);
        assertEquals("{\"ztime\":"
                + m.ztime.toInstant().toEpochMilli()
                + "}", str);

        Model m1 = JSON.parseObject(str, Model.class);
        assertEquals(m.ztime.toInstant().toEpochMilli(), m1.ztime.toInstant().toEpochMilli());
    }

    public void test_for_issue_2() throws Exception {
        Model m = new Model();
        m.time1 = java.time.LocalDateTime.now();
        String str = JSON.toJSONString(m);
        assertEquals("{\"time1\":"
                + m.time1.atZone(JSON.defaultTimeZone.toZoneId()).toEpochSecond()
                + "}", str);

        Model m1 = JSON.parseObject(str, Model.class);
        assertEquals(m.time1.atZone(JSON.defaultTimeZone.toZoneId()).toEpochSecond()
                , m1.time1.atZone(JSON.defaultTimeZone.toZoneId()).toEpochSecond());
    }

    public void test_for_issue_3() throws Exception {
        Model m = new Model();
        m.ztime1 = ZonedDateTime.now();
        String str = JSON.toJSONString(m);
        assertEquals("{\"ztime1\":"
                + m.ztime1.toEpochSecond()
                + "}", str);

        Model m1 = JSON.parseObject(str, Model.class);
        assertEquals(m.ztime1.toEpochSecond()
                , m1.ztime1.toEpochSecond());
    }

    public void test_for_issue_4() throws Exception {
        Model m = new Model();
        m.date = new Date();
        String str = JSON.toJSONString(m);
        assertEquals("{\"date\":"
                + m.date.getTime()
                + "}", str);

        Model m1 = JSON.parseObject(str, Model.class);
        assertEquals(m.date.getTime()
                , m1.date.getTime());
    }

    public void test_for_issue_5() throws Exception {
        Model m = new Model();
        m.date1 = new Date();
        String str = JSON.toJSONString(m);
        assertEquals("{\"date1\":"
                + (m.date1.getTime() / 1000)
                + "}", str);

        Model m1 = JSON.parseObject(str, Model.class);
        assertEquals(m.date1.getTime() / 1000
                , m1.date1.getTime() / 1000);
    }

    public void test_for_issue_6() throws Exception {
        Model m = new Model();
        m.date1 = new Date();
        String str = JSON.toJSONString(m);
        assertEquals("{\"date1\":"
                + (m.date1.getTime() / 1000)
                + "}", str);

        Model m1 = JSON.parseObject(str, Model.class);
        assertEquals(m.date1.getTime() / 1000
                , m1.date1.getTime() / 1000);
    }

    public void test_for_issue_7() throws Exception {
        Model m = JSON.parseObject("{\"time2\":20190714121314}", Model.class);
        assertEquals(m.time2, LocalDateTime.of(2019, 7, 14, 12, 13, 14));
    }

    public static class Model {
        @JSONField(format = "millis")
        public LocalDateTime time;

        @JSONField(format = "millis")
        public ZonedDateTime ztime;

        @JSONField(format = "unixtime")
        public LocalDateTime time1;

        @JSONField(format = "unixtime")
        public ZonedDateTime ztime1;

        @JSONField(format = "millis")
        public Date date;

        @JSONField(format = "unixtime")
        public Date date1;

        @JSONField(format = "yyyyMMddHHmmss")
        public LocalDateTime time2;
    }
}
