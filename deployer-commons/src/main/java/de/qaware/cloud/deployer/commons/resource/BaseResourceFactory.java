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
package de.qaware.cloud.deployer.commons.resource;

import de.qaware.cloud.deployer.commons.config.resource.BaseResourceConfig;
import de.qaware.cloud.deployer.commons.error.ResourceException;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a basic resource factory. It creates resources out of configs.
 *
 * @param <ResourceType> The resource type this factory belongs to.
 * @param <ConfigType>   The config type this factory belongs to.
 */
public abstract class BaseResourceFactory<ResourceType extends BaseResource, ConfigType extends BaseResourceConfig> {

    /**
     * The client factory which is used to create the clients for the backend communication.
     */
    private final ClientFactory clientFactory;

    /**
     * The logger which is used for logging.
     */
    private final Logger logger;

    /**
     * Creates a new base resource factory.
     *
     * @param logger        The logger which is used for logging.
     * @param clientFactory The client factory which is used to create the clients for the backend communication.
     */
    public BaseResourceFactory(Logger logger, ClientFactory clientFactory) {
        this.logger = logger;
        this.clientFactory = clientFactory;
    }

    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    /**
     * Creates a list of resources out of the specified configs.
     *
     * @param resourceConfigs The configs which are the sources for the resources.
     * @return A list of resources.
     * @throws ResourceException If an error during resource creation occurs.
     */
    public List<ResourceType> createResources(List<ConfigType> resourceConfigs) throws ResourceException {

        logger.info("Creating resources...");

        List<ResourceType> resources = new ArrayList<>();
        for (ConfigType resourceConfig : resourceConfigs) {
            resources.add(createResource(resourceConfig));
        }

        logger.info("Finished creating resources...");

        return resources;
    }

    /**
     * Creates a resource out of the specified config.
     *
     * @param resourceConfig The config which is the source for the resource.
     * @return The created resource.
     * @throws ResourceException If a error during resource creation occurs.
     */
    public abstract ResourceType createResource(ConfigType resourceConfig) throws ResourceException;
}
