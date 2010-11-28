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
package com.webkreator.canoe;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;

public class CanoeReferenceInsertionHandler implements
		ReferenceInsertionEventHandler {

	public static final String SAFE_REFERENCE_PREFIX = "$_x.";

	protected Canoe qlueWriter;

	public CanoeReferenceInsertionHandler(Canoe qlueWriter) {
		this.qlueWriter = qlueWriter;
	}

	@Override
	public Object referenceInsert(String arg0, Object arg1) {
		if (arg0.startsWith(SAFE_REFERENCE_PREFIX)) {
			return arg1;
		}

		if (arg1 == null) {
			return null;
		}

		switch (qlueWriter.currentContext()) {
		case Canoe.CTX_HTML:
			return HtmlEncoder.encodeForHTML(arg1.toString());
		case Canoe.CTX_JS:
			return HtmlEncoder.encodeForJavaScript(arg1.toString());
		case Canoe.CTX_URI:
			return HtmlEncoder.encodeForURL(arg1.toString());
		case Canoe.CTX_CSS:
			return HtmlEncoder.encodeForCSS(arg1.toString());
		case Canoe.CTX_SUPPRESS:
		default:
			// Do nothing
			break;
		}

		// Suppressed output
		return new String("");
	}
}
