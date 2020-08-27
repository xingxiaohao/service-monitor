package com.moji.servicemonitor;


import com.moji.servicemonitor.entity.Entrance;
import com.moji.servicemonitor.mapper.ServiceMapper;
import com.moji.servicemonitor.timer.DingDingNotifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceMonitorApplication.class)
public class ServiceMonitorApplicationTests {

//    @Autowired
//    ServiceMapper mapper;
//
//    @Autowired
//    DingDingNotifier notifier;

    @Test
    public void contextLoads() {
//        DingDingNotifier notifier = new DingDingNotifier();
//        notifier.notifier();
    }


}
