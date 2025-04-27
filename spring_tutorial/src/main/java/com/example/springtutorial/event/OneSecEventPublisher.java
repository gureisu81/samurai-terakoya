package com.example.springtutorial.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class OneSecEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	//1秒経過ごとにイベントを発行
	@Scheduled(fixedRate = 1000)
	public void publishOneSecEvent() {
		OneSecEvent event = new OneSecEvent(this);
		applicationEventPublisher.publishEvent(event);
	}

}
