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
package com.webkreator.qlue.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * When applications use web.xml configuration to handle requests
 * that match a suffix (e.g., .html) they don't get directory
 * requests (those that end with forward slash characters). This
 * servlet filter will detect directory requests and expand them,
 * adding the default page name (e.g., index.html). Once this change
 * is made, the servlet container will correctly route the request.
 */
public class WelcomeFilter implements Filter {

	public static final String DEFAULT_PAGE = "DEFAULT_PAGE";

	private String defaultPage = "index.html";

	private Log log = LogFactory.getLog(WelcomeFilter.class);

	@Override
	public void init(FilterConfig filterConfig) {
		String s = filterConfig.getInitParameter(DEFAULT_PAGE);
		if (s != null) {
			defaultPage = s;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Only perform redirection if we have been configured
		// with the name of the default page
		if (defaultPage != null) {
			String path = ((HttpServletRequest) request).getRequestURI();

			// If the path has a forward slash character at the end,
			// we want to redirect such access to a default page
			if (path.endsWith("/")) {
				String newPath = path + defaultPage;

				if (log.isDebugEnabled()) {
					log.debug("WelcomeFilter redirecting " + path + " to "
							+ newPath);
				}

				request.getRequestDispatcher(newPath)
						.forward(request, response);
				
				return;
			} else {
				// If there is no forward slash at the end we check if it might
				// be a folder access; we assume that it is if there are not
				// dots anywhere in the path
				if (path.indexOf('.') == -1) {
					((HttpServletResponse) response).sendRedirect(path + "/");
					
					return;
				}
			}
		}

		// No redirection; allow request through
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Nothing to do here, but we still
		// have to provide an implementation
	}
}