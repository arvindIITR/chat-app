package com.arvind.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
@CrossOrigin
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOriginPatterns("*").withSockJS();

        /*RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        registry.addEndpoint("/chat")
                .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("http://localhost:8080", "http://localhost:8080/chat");*/

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app")
                .enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("admin")
                .setClientPasscode("admin")
                .setSystemLogin("admin")
                .setSystemPasscode("admin");

    }
}
