package com.example.chatserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/test");
        registry.addEndpoint("/messageIn/**");
//        registry.addEndpoint("/test").withSockJS();
//                .setHeartbeatTime(10_000)
//                .setTaskScheduler(this.scheduler);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
//        registry.enableSimpleBroker("/messageIn");
        registry.enableSimpleBroker("/broadcast");
        registry.setApplicationDestinationPrefixes("/app","/broadcast");
    }
}
