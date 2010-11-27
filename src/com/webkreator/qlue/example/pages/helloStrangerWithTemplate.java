/* 
 * Qlue Web Application Framework
 * Copyright 2009 Ivan Ristic <ivanr@webkreator.com>
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
package com.webkreator.qlue.example.pages;

import com.webkreator.qlue.Page;
import com.webkreator.qlue.QlueParameter;
import com.webkreator.qlue.view.View;

/**
 * An example page that has one parameter and creates
 * output using a template.
 */
public class helloStrangerWithTemplate extends Page {
	
	@QlueParameter
	public String name;
	
	/**
	 * This method doesn't really need to do much since
	 * the presentation logic handles everything. In fact,
	 * we could have omitted it altogether since the overridden
	 * method does the same thing.
	 */
	@Override
	public View onGet() throws Exception {
		throw new Exception("Tesst");
		//return new DefaultView();
	}
}