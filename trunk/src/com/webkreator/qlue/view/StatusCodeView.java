/* 
 * Qlue Web Application Framework
 * Copyright 2009-2011 Ivan Ristic <ivanr@webkreator.com>
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
package com.webkreator.qlue.view;

import com.webkreator.qlue.Page;
import com.webkreator.qlue.TransactionContext;
import com.webkreator.qlue.util.WebUtil;

public class StatusCodeView implements View {

	private int status;

	private String message;

	public StatusCodeView(int status) {
		this.status = status;
		this.message = WebUtil.getStatusMessage(status);
		if (this.message == null) {
			this.message = "Error";
		}
	}

	public StatusCodeView(int status, String message) {
		this.status = status;
		this.message = message;
	}

	@Override
	public void render(TransactionContext context, Page page) throws Exception {
		context.response.sendError(status, message);
		WebUtil.writeMessage(context, message);
	}
}
