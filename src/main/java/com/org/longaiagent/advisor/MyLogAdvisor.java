package com.org.longaiagent.advisor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * 	lombok 依赖引入 ≠ 注解处理器生效
 * 	•	annotationProcessorPaths 要写对
 * 	•	不要在 spring-boot-maven-plugin 里排除 Lombok
 * 	•	IDEA 也要开启 Annotation Processing
 */
@Slf4j
public class MyLogAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        observeAfter(advisedResponse);
        return advisedResponse;
    }

    private void observeAfter(AdvisedResponse advisedResponse) {
         log.info("AI Response:{}", advisedResponse.response().getResult().getOutput().getText());
    }

    private AdvisedRequest before(AdvisedRequest advisedRequest) {
        log.info("AI Request:{}", advisedRequest.userText());
        return advisedRequest;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {

        advisedRequest = before(advisedRequest);

        Flux<AdvisedResponse> advisedResponseFlux = chain.nextAroundStream(advisedRequest);

        return  new MessageAggregator().aggregateAdvisedResponse(advisedResponseFlux, this::observeAfter);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
