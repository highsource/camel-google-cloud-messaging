package org.hisrc.camel.component.gcm;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GcmProducer extends DefaultProducer {

	private static final Logger LOG = LoggerFactory.getLogger(GcmProducer.class);

	public GcmProducer(GcmEndpoint endpoint) {
		super(endpoint);
	}

	@Override
	public GcmEndpoint getEndpoint() {
		return (GcmEndpoint) super.getEndpoint();
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		final GcmConfiguration configuration = getEndpoint().getConfiguration();

		final Sender sender = new Sender(configuration.getApiKey());
		final Message.Builder messageBuilder = new Message.Builder();

		final String collapseKey = configuration.getCollapseKey();
		if (collapseKey != null) {
			messageBuilder.collapseKey(collapseKey);
		}
		final Integer timeToLive = configuration.getTimeToLive();
		if (timeToLive != null) {
			messageBuilder.timeToLive(timeToLive.intValue());
		}
		final Boolean delayWhileIdle = configuration.isDelayWhileIdle();
		if (delayWhileIdle != null) {
			messageBuilder.delayWhileIdle(delayWhileIdle.booleanValue());
		}
		final String restrictedPackageName = configuration.getRestrictedPackageName();
		if (restrictedPackageName != null) {
			messageBuilder.restrictedPackageName(restrictedPackageName);
		}
		final Boolean dryRun = configuration.isDryRun();
		if (dryRun != null) {
			messageBuilder.dryRun(dryRun.booleanValue());
		}

		final Object bodyObject = exchange.getIn().getBody();
		if (bodyObject instanceof String) {
			messageBuilder.addData(configuration.getStringBodyDataKey(), (String) bodyObject);
		} else {
			@SuppressWarnings("unchecked")
			final Map<Object, Object> map = exchange.getIn().getBody(Map.class);
			if (map != null) {
				for (Map.Entry<Object, Object> entry : map.entrySet()) {
					final Object key = entry.getKey();
					final Object value = entry.getValue();
					if (key != null && value != null) {
						messageBuilder.addData(key.toString(), value.toString());
					}
				}
			}
		}

		final Message message = messageBuilder.build();

		final int retries = configuration.getRetries();

		String to = exchange.getIn().getHeader(GcmConstants.TO, String.class);
		if (to == null) {
			to = getEndpoint().getName();
		}

		final Result result;
		if (retries == 0) {
			LOG.trace("Sending message [{}] from exchange [{}] to [{}] with no retries...",
					new Object[] { message, exchange, to });
			result = sender.sendNoRetry(message, to);
		} else {
			LOG.trace("Sending message [{}] from exchange [{}] to [{}] with {} retries...",
					new Object[] { message, exchange, to, retries });
			result = sender.send(message, to, retries);
		}
		LOG.trace("Received result [{}]", result);

	}

	@Override
	public String toString() {
		return "GcmProducer[" + URISupport.sanitizeUri(getEndpoint().getEndpointUri()) + "]";
	}

}
