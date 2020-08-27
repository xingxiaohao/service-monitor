package com.moji.servicemonitor.util;

import com.alibaba.fastjson.JSONObject;
import com.moji.servicemonitor.entity.Entrance;
import com.moji.servicemonitor.entity.Message;
import com.moji.servicemonitor.entity.MessageInfo;
import com.moji.servicemonitor.mapper.ServiceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Component
public class DingDingMessageUtil {

    // 从钉钉群获取的
    @Value("${ding.access_token}")
    public String access_token;

    public void sendMessage(String msg) {
        try {
            Message message = new Message();
            message.setMsgtype("text");
            message.setText(new MessageInfo(msg));
            URL url = new URL("https://oapi.dingtalk.com/robot/send?access_token=" + access_token);
            // 建立 http 连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            String textMessage = JSONObject.toJSONString(message);
            log.info("通知消息：{}",textMessage);
            byte[] data = textMessage.getBytes();
            out.write(data);
            out.flush();
            out.close();
            InputStream in = conn.getInputStream();
            byte[] data1 = new byte[in.available()];
            in.read(data1);
            log.info("钉钉通知完成{}",new String(data1));
        } catch (Exception e) {
            log.info("钉钉通知异常:{}",e.getMessage());
        }
    }


}
