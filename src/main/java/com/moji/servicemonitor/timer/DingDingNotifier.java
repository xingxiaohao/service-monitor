package com.moji.servicemonitor.timer;

import com.alibaba.fastjson.JSONObject;
import com.moji.servicemonitor.entity.Entrance;
import com.moji.servicemonitor.mapper.ServiceMapper;
import com.moji.servicemonitor.util.DingDingMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钉钉定时通知
 *
 * @author wenhao.xing
 * @date 2020/8/26
 */

@Configuration
@EnableScheduling
@Slf4j
public class DingDingNotifier {
    @Autowired
    ServiceMapper mapper;

    @Autowired
    DingDingMessageUtil util;

    @Scheduled(cron = "0 0 */1 * * ?")
    public void notifier () {
        List<Entrance> services = mapper.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long currentTime = System.currentTimeMillis();
        ArrayList<Map<String, String>> resultList = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        str.append("【服务提醒】:以下服务即将到期，请及时处理\n");
        for (Entrance service : services) {
            Map<String, String> resultMap = new HashMap<>();
            Long endTime = service.getEndTime();
            Long startTime = service.getStartTime();
            //判断是否需要发送消息
            if (null != endTime && null != startTime &&
                    currentTime > startTime && currentTime <= endTime
                    && endTime-currentTime <= 86400000 * 3) {
                String formatEndTime = null;
                try {
                    formatEndTime  = sdf.format(service.getEndTime());
                }catch (Exception e){
                    log.error("日期转换异常：{}",service.getEndTime());
                }
                resultMap.put("服务名称", service.getName());
                resultMap.put("到期时间",formatEndTime);
                resultList.add(resultMap);
            }
        }
        String s = JSONObject.toJSONString(resultList);
        str.append("【详情】："+s);
        util.sendMessage(str.toString());
    }
}
