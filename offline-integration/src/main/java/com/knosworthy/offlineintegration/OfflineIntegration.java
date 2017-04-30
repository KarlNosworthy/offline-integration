/*
 * MIT License
 *
 * Copyright (c) 2017 Karl Nosworthy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.knosworthy.offlineintegration;

import org.springframework.context.annotation.Configuration;

/**
 *
 */
public interface OfflineIntegration {
	
	@Configuration
	public static class OfflineIntegrationConfiguration extends SpringContextJPAConfiguration {

		private static final String DefaultOfflineIntegrationDialectClassName = "org.hibernate.dialect.H2Dialect";
		private static final String DefaultOfflineIntegrationDataSourceDriverName = "org.h2.Driver";
		private static final String DefaultOfflineIntegrationDataSourceURL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

		@Override
		public String getDataSourceDriverClassName() {
			return DefaultOfflineIntegrationDataSourceDriverName;
		}

		@Override
		public String getDataSourceURL() {
			return DefaultOfflineIntegrationDataSourceURL;
		}

		@Override
		public String getDataSourceUsername() {
			return "";
		}

		@Override
		public String getDataSourcePassword() {
			return "";
		}

		@Override
		public String[] getEntityPackagesToScan() {
			return new String[] {""};
		}

		@Override
		public String getDialectPropertyClassName() {
			return DefaultOfflineIntegrationDialectClassName;
		}
	}
}