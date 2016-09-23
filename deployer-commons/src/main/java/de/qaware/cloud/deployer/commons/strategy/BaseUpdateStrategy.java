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
package de.qaware.cloud.deployer.commons.strategy;

import de.qaware.cloud.deployer.commons.error.ResourceException;
import de.qaware.cloud.deployer.commons.resource.Resource;

/**
 * Implements a basic version of the update strategy. Meaning that all resources not included in the resources list
 * stay untouched. All included resources are updated or created.
 */
public abstract class BaseUpdateStrategy {

    /**
     * Deploys the specified resources. If the resource already exists, it will be updated.
     *
     * @param resource The resource to deploy.
     * @throws ResourceException If an error during updating or deployment occurs.
     */
    public void deploy(Resource resource) throws ResourceException {
        if (resource.exists()) {
            resource.update();
        } else {
            resource.create();
        }
    }
}
