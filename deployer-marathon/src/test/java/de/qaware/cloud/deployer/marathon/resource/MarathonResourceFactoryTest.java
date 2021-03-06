/*
 * Copyright 2016 QAware GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.qaware.cloud.deployer.marathon.resource;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import de.qaware.cloud.deployer.commons.config.environment.EnvironmentConfig;
import de.qaware.cloud.deployer.commons.config.resource.ContentType;
import de.qaware.cloud.deployer.commons.config.util.FileUtil;
import de.qaware.cloud.deployer.commons.error.EnvironmentConfigException;
import de.qaware.cloud.deployer.commons.error.ResourceConfigException;
import de.qaware.cloud.deployer.commons.error.ResourceException;
import de.qaware.cloud.deployer.commons.strategy.Strategy;
import de.qaware.cloud.deployer.marathon.config.resource.MarathonResourceConfig;
import de.qaware.cloud.deployer.marathon.resource.app.AppResource;
import de.qaware.cloud.deployer.marathon.resource.base.MarathonResource;
import de.qaware.cloud.deployer.marathon.resource.group.GroupResource;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static de.qaware.cloud.deployer.commons.logging.CommonsMessageBundle.COMMONS_MESSAGE_BUNDLE;
import static de.qaware.cloud.deployer.marathon.logging.MarathonMessageBundle.MARATHON_MESSAGE_BUNDLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarathonResourceFactoryTest {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(WireMockConfiguration.options().dynamicPort());
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;
    private EnvironmentConfig environmentConfig;

    @Before
    public void setUp() throws ResourceConfigException, ResourceException, EnvironmentConfigException, IOException {
        instanceRule.stubFor(get(urlEqualTo("/service/marathon/ping"))
                .willReturn(aResponse().withStatus(200)));

        environmentConfig = new EnvironmentConfig("test", "http://localhost:" + instanceRule.port(), Strategy.REPLACE);
    }

    @Test
    public void testFactoryCreationWithFailingPing() throws ResourceConfigException {
        boolean exceptionThrown = false;

        instanceRule.resetMappings();
        instanceRule.stubFor(get(urlEqualTo("/service/marathon/ping"))
                .willReturn(aResponse().withStatus(404)));

        try {
            new MarathonResourceFactory(environmentConfig);
        } catch (ResourceException e) {
            exceptionThrown = true;
            assertEquals(COMMONS_MESSAGE_BUNDLE.getMessage("DEPLOYER_COMMONS_ERROR_PING_FAILED", "test", 404), e.getMessage());
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testCreateResourceWithValidApp() throws ResourceException, ResourceConfigException {
        MarathonResourceConfig config = new MarathonResourceConfig("test", ContentType.JSON, FileUtil.readFileContent("/de/qaware/cloud/deployer/marathon/resource/factory/app.json"));
        MarathonResourceFactory resourceFactory = new MarathonResourceFactory(environmentConfig);
        MarathonResource resource = resourceFactory.createResource(config);
        assertTrue(resource instanceof AppResource);
    }

    @Test
    public void testCreateResourceWithValidGroup() throws ResourceException, ResourceConfigException {
        MarathonResourceConfig config = new MarathonResourceConfig("test", ContentType.JSON, FileUtil.readFileContent("/de/qaware/cloud/deployer/marathon/resource/factory/group.json"));
        MarathonResourceFactory resourceFactory = new MarathonResourceFactory(environmentConfig);
        MarathonResource resource = resourceFactory.createResource(config);
        assertTrue(resource instanceof GroupResource);
    }

    @Test
    public void testCreateResourcesWithValidGroupAndApp() throws ResourceException, ResourceConfigException {
        MarathonResourceConfig appConfig = new MarathonResourceConfig("test", ContentType.JSON, FileUtil.readFileContent("/de/qaware/cloud/deployer/marathon/resource/factory/app.json"));
        MarathonResourceConfig groupConfig = new MarathonResourceConfig("test", ContentType.JSON, FileUtil.readFileContent("/de/qaware/cloud/deployer/marathon/resource/factory/group.json"));
        MarathonResourceFactory resourceFactory = new MarathonResourceFactory(environmentConfig);

        List<MarathonResourceConfig> configs = new ArrayList<>();
        configs.add(appConfig);
        configs.add(groupConfig);

        List<MarathonResource> resources= resourceFactory.createResources(configs);
        assertEquals(2, resources.size());
        MarathonResource appResource = resources.get(0);
        assertTrue(appResource instanceof AppResource);
        MarathonResource groupResource = resources.get(1);
        assertTrue(groupResource instanceof GroupResource);
    }

    @Test
    public void testCreateResourceWithUnknownType() throws ResourceException, ResourceConfigException {
        boolean exceptionThrown = false;
        MarathonResourceConfig config = new MarathonResourceConfig("test", ContentType.JSON, FileUtil.readFileContent("/de/qaware/cloud/deployer/marathon/resource/factory/unknown.json"));
        MarathonResourceFactory resourceFactory = new MarathonResourceFactory(environmentConfig);
        try {
            resourceFactory.createResource(config);
        } catch (ResourceException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testCreateResourceWithEmptyContent() throws ResourceException, ResourceConfigException {
        boolean exceptionThrown = false;
        MarathonResourceConfig config = new MarathonResourceConfig("test", ContentType.JSON, FileUtil.readFileContent("/de/qaware/cloud/deployer/marathon/resource/factory/unknown.json"));
        config.setContent("");
        MarathonResourceFactory resourceFactory = new MarathonResourceFactory(environmentConfig);
        try {
            resourceFactory.createResource(config);
        } catch (ResourceException e) {
            exceptionThrown = true;
            assertEquals(MARATHON_MESSAGE_BUNDLE.getMessage("DEPLOYER_MARATHON_ERROR_EMPTY_CONFIG", "test"), e.getMessage());
        }
        assertTrue(exceptionThrown);
    }
}
