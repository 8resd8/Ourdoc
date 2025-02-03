package com.ssafy.ourdoc.data.setting;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.PriorityConstructorArbitraryIntrospector;

public class FixtureMonkeyConfig {

	private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
		.defaultNotNull(true)
		.objectIntrospector(PriorityConstructorArbitraryIntrospector.INSTANCE)
		.build();
}
