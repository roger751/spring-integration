/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.xmpp.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser;
import org.springframework.util.StringUtils;

import org.w3c.dom.Element;

/**
 * Parser for 'xmpp:presence-outbound-channel-adapter' element
 * 
 * @author Oleg Zhurakousky
 * @since 2.0
 */
public abstract class AbstractXmppOutboundChannelAdapterParser extends AbstractOutboundChannelAdapterParser {
	
	@Override
	protected AbstractBeanDefinition parseConsumer(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(this.getHandlerClassName());
		String connectionName = element.getAttribute("xmpp-connection");
		if (StringUtils.hasText(connectionName)){
			builder.addConstructorArgReference(connectionName);
		}
		else if (parserContext.getRegistry().containsBeanDefinition(XmppNamespaceHandler.XMPP_CONNECTION_BEAN_NAME)) {
			builder.addConstructorArgReference(XmppNamespaceHandler.XMPP_CONNECTION_BEAN_NAME);
		}
		else {
			throw new BeanCreationException("You must either explicitly define which XMPP connection to use via " +
					"'xmpp-connection' attribute or have default XMPP connection bean registered under the name 'xmppConnection'" +
					"(e.g., <int-xmpp:xmpp-connection .../>). If 'id' is not provided the default will be 'xmppConnection'.");
		}
		
		return builder.getBeanDefinition();
	}
	
	protected abstract String getHandlerClassName();
}
