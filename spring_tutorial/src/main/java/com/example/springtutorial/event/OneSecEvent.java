package com.example.springtutorial.event;

import org.springframework.context.ApplicationEvent;

public class OneSecEvent  extends ApplicationEvent {
	//イベント処理を初期化
	public OneSecEvent(Object source) {
		super(source);
	}

}
