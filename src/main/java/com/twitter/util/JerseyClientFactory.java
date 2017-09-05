package com.twitter.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class JerseyClientFactory {

	private static Client client = null;

	private JerseyClientFactory() {

	}

	public static Client getClient() {
		if (client == null) {
			JacksonJsonProvider provider = new JacksonJsonProvider();
			provider.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
			ClientConfig config = new ClientConfig().register(provider);
			client = ClientBuilder.newClient(config);
		}
		return client;
	}
}
