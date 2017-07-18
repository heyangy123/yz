package com.item.util;

import java.util.HashMap;

public class QueryParams {

	private QueryParams() {
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder extends HashMap<String, Object>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private Builder(){};
	}
}
