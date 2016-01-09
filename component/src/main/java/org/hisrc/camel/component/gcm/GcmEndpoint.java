package org.hisrc.camel.component.gcm;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

/**
 * Represents a GCM endpoint.
 */
@UriEndpoint(scheme = "gcm", title = "Google Cloud Messaging", syntax = "gcm:name", producerOnly = true, label = "google,cloud,mobile,messaging,gcm")
public class GcmEndpoint extends DefaultEndpoint {

	@UriPath
	@Metadata(required = "true")
	private String name;

	@UriParam
	private GcmConfiguration configuration;

	public GcmEndpoint(String uri, GcmComponent component, GcmConfiguration configuration) {
		super(uri, component);
		this.configuration = configuration;
	}

	public Producer createProducer() throws Exception {
		return new GcmProducer(this);
	}

	public Consumer createConsumer(Processor processor) throws Exception {
		throw new UnsupportedOperationException("You cannot receive messages from this endpoint");
	}

	public boolean isSingleton() {
		return true;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public GcmConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GcmConfiguration configuration) {
		this.configuration = configuration;
	}

}
