package com.elibrary.rshelfstore.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import com.elibrary.rshelfstore.model.Client;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = -5760424950358975230L;
	
	private Client client;
    private String applicationUrl;

    public RegistrationCompleteEvent(Client client, String applicationUrl){
        super(client);
        this.client = client;
        this.applicationUrl = applicationUrl;
    }
}
