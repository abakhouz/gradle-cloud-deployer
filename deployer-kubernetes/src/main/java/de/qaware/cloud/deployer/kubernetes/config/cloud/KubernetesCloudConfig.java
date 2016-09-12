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
package de.qaware.cloud.deployer.kubernetes.config.cloud;

import de.qaware.cloud.deployer.commons.config.cloud.CloudConfig;

/**
 * A custom cloud config for kubernetes.
 */
public class KubernetesCloudConfig extends CloudConfig {

    /**
     * The namespace which is used for deployment.
     */
    private final String namespace;

    /**
     * Creates a new kubernetes cloud config.
     *
     * @param baseUrl        The base url of this cloud.
     * @param updateStrategy The update strategy which is used for this cloud.
     * @param namespace      The namespace which is used for deployment.
     */
    public KubernetesCloudConfig(String baseUrl, String updateStrategy, String namespace) {
        super(baseUrl, updateStrategy);
        this.namespace = namespace;
    }

    /**
     * Returns the namespace.
     *
     * @return The namespace.
     */
    public String getNamespace() {
        return namespace;
    }
}
