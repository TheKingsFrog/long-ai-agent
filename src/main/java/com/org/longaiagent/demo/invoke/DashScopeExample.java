package com.org.longaiagent.demo.invoke;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class DashScopeExample {
    public static void main(String[] args) {
        // 从环境变量读取 API Key
        String apiKey = ApiKey.apiKey;

        // ==== 构建 messages ====
        JSONArray messages = new JSONArray()
                .put(new JSONObject()
                        .set("role", "system")
                        .set("content", "You are a helpful assistant."))
                .put(new JSONObject()
                        .set("role", "user")
                        .set("content", "通过Http调用成功了，请鼓励我继续学习AI编程"));

        // ==== 构建 input ====
        JSONObject input = new JSONObject()
                .set("messages", messages);

        // ==== 构建 parameters ====
        JSONObject parameters = new JSONObject()
                .set("result_format", "message");

        // ==== 构建最终请求体 ====
        JSONObject bodyJson = new JSONObject()
                .set("model", "qwen-plus")
                .set("input", input)
                .set("parameters", parameters);

        // ==== 发送 POST 请求 ====
        HttpResponse response = HttpRequest.post("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(ContentType.JSON.getValue())
                .body(bodyJson.toString()) // 转成 JSON 字符串
                .execute();

        // 输出结果
        System.out.println("Status: " + response.getStatus());
        System.out.println("Body: " + response.body());
    }

}