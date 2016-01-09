package org.hisrc.camel.component.gcm.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.hisrc.camel.component.gcm.GcmConstants;
import org.junit.Test;

public class GcmEndpointTest extends CamelTestSupport {

	private static final String TEST_GCM_PROPERTIES = "/test.gcm.properties";

	private Properties loadProperties(final String source) throws IOException {
		final Properties properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream(source));
		} catch (Exception e) {
			throw new IOException(e);
		}
		return properties;
	}

	@Test
	public void sendsComplexMessageWithSpecifiedToHeader() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);
		final Map<String, String> body = new HashMap<String, String>();
		body.put("facilityEquipmentnumber", "10213788");
		body.put("stationName", "Arnstadt Hbf");
		body.put("facilityDescription", "Aufzug zu Bstg 2/3");
		body.put("facilityState", "INACTIVE");
		final Map<String, Object> headers = new HashMap<String, Object>();
		headers.put(GcmConstants.TO, "/topics/foo");
		sendBody("direct:foo", body, headers);
		assertMockEndpointsSatisfied();
	}

	@Test
	public void sendsComplexMessageWithoutSpecifiedToHeader() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);
		final Map<String, String> body = new HashMap<String, String>();
		body.put("facilityEquipmentnumber", "10213788");
		body.put("stationName", "Arnstadt Hbf");
		body.put("facilityDescription", "Aufzug zu Bstg 2/3");
		body.put("facilityState", "INACTIVE");
		sendBody("direct:foo", body);
		assertMockEndpointsSatisfied();
	}
	
	@Test
	public void sendsSimpleMessage() throws Exception {
		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMinimumMessageCount(1);
		sendBody("direct:foo", "doo");
		assertMockEndpointsSatisfied();
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		final String apiKey = loadProperties(TEST_GCM_PROPERTIES).getProperty("gcm.apiKey");
		return new RouteBuilder() {
			public void configure() {
				from("direct:foo").to("gcm:///topics/bar?apiKey=" + apiKey).to("mock:result");
			}
		};
	}
}
