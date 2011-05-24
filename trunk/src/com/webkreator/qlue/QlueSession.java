/* 
 * Qlue Web Application Framework
 * Copyright 2009,2010 Ivan Ristic <ivanr@webkreator.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webkreator.qlue;

import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;

/**
 * Represents a user session. This class implements the basics
 * needed by the framework, but applications will typically inherit
 * it to add additional functionality.
 */
public class QlueSession {

	private QlueApplication qlueApp;

	private Locale locale = Locale.ENGLISH;

	private MessageSource messageSource;

	private String nonce;

	private Integer developmentMode = null;

	/**
	 * Initialize a new user session.
	 */
	public QlueSession(QlueApplication qlueApp) {
		this.qlueApp = qlueApp;

		// Generate session nonce
		generateNonce();
	}

	/**
	 * Get the nonce for this session.
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * Generate a new nonce for this session. Nonces
	 * are used as part of the CSRF defense.
	 */
	private void generateNonce() {
		Random random = new SecureRandom();

		byte[] randomBytes = new byte[16];
		random.nextBytes(randomBytes);

		nonce = new String(Hex.encodeHex(randomBytes));
	}

	/**
	 * Retrieve session development mode.
	 * 
	 * @return
	 */
	public Integer getDevelopmentMode() {
		return developmentMode;
	}

	/**
	 * Set session development mode.
	 * 
	 * @param developmentMode
	 */
	public void setDevelopmentMode(Integer developmentMode) {
		if ((developmentMode != QlueConstants.DEVMODE_DISABLED)
				&& (developmentMode != QlueConstants.DEVMODE_ENABLED)
				&& (developmentMode != QlueConstants.DEVMODE_ONDEMAND)) {
			throw new RuntimeException(
					"Qlue: Invalid development mode setting: "
							+ developmentMode);
		}

		this.developmentMode = developmentMode;
	}

	public void writeDevelopmentInformation(PrintWriter out) {
		out.println(" Nonce: " + nonce);
		out.println(" Development mode: " + developmentMode);
	}

	/**
	 * Retrieve this session's message source. Using per-session message sources
	 * allows us to offer different languages and locales to each user.
	 * 
	 * @return
	 */
	public MessageSource getMessageSource() {
		if (messageSource == null) {
			messageSource = qlueApp.getMessageSource(locale);
		}

		return messageSource;
	}
}