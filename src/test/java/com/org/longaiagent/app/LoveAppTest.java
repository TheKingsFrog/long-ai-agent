package com.org.longaiagent.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Autowired
    private LoveApp loveApp;

    @Test
    void testChat() {

        String chatId = UUID.randomUUID().toString();

        // 第一轮
        String msg1 = "你好，我是洪成隆";
        String answer = loveApp.doChat(msg1, chatId);

        // 第二轮
        String msg2 = "我想追寻爱情";
        answer = loveApp.doChat(msg2, chatId);
        Assertions.assertNotNull(answer);

        // 第三轮
        String msg3 = "快餐时代，能找到真爱吗?还记得我叫什么名字吗？";
        answer = loveApp.doChat(msg3, chatId);
        Assertions.assertNotNull(answer);

    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是Thunder，我想让另一半更爱我，但我不知道该怎么做";
        LoveApp.LoveRecord loveRecord = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveRecord);
    }
}