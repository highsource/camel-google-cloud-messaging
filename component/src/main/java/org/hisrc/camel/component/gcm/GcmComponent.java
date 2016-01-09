package org.hisrc.camel.component.gcm;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

public class GcmComponent extends UriEndpointComponent {

	private String apiKey;

	public GcmComponent() {
		super(GcmEndpoint.class);
	}

	public GcmComponent(CamelContext context) {
		super(context, GcmEndpoint.class);
	}

	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {

		GcmConfiguration configuration = new GcmConfiguration();
		configuration.setApiKey(getApiKey());
		setProperties(configuration, parameters);

		if (configuration.getApiKey() == null) {
			throw new IllegalArgumentException("GCM apiKey must be specified");
		}

		if (remaining == null || remaining.trim().length() == 0) {
			throw new IllegalArgumentException("Name must be specified.");
		}

		GcmEndpoint endpoint = new GcmEndpoint(uri, this, configuration);
		endpoint.setName(remaining);
		return endpoint;

	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
