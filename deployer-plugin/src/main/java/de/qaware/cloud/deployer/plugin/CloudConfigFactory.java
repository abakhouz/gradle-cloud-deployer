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
package de.qaware.cloud.deployer.plugin;

import de.qaware.cloud.deployer.commons.config.cloud.CloudConfig;
import de.qaware.cloud.deployer.commons.config.cloud.SSLConfig;

/**
 * Creates a cloud config using the specified extension.
 */
public final class CloudConfigFactory {

    /**
     * UTILITY.
     */
    private CloudConfigFactory() {
    }

    /**
     * Creates a cloud config using the specified extension.
     *
     * @param extension The extension which contains all information.
     * @return The created cloud config.
     */
    public static CloudConfig create(DeployerExtension extension) {
        CloudConfig cloudConfig = new CloudConfig(extractBaseUrl(extension), extractUpdateStrategy(extension));
        SSLConfig sslConfig = extractSSLConfig(extension);
        cloudConfig.setSslConfig(sslConfig);
        cloudConfig.setUsername(extension.getUsername());
        cloudConfig.setPassword(extension.getPassword());
        cloudConfig.setToken(extension.getToken());
        return cloudConfig;
    }

    /**
     * Extracts the base url from the extension.
     *
     * @param extension The extension.
     * @return The base url.
     */
    private static String extractBaseUrl(DeployerExtension extension) {
        String baseUrl = extension.getBaseUrl();
        if (baseUrl != null && !baseUrl.isEmpty()) {
            return baseUrl;
        } else {
            throw new IllegalArgumentException("Please specify a base url");
        }
    }

    /**
     * Extracts the update strategy from the extension.
     *
     * @param extension The extension.
     * @return The extracted update strategy.
     */
    private static String extractUpdateStrategy(DeployerExtension extension) {
        String updateStrategy = extension.getUpdateStrategy();
        return updateStrategy != null && !updateStrategy.isEmpty() ? updateStrategy : "HARD";
    }

    /**
     * Extracts the ssl config from the extension.
     *
     * @param extension The extension.
     * @return The extracted ssl config.
     */
    private static SSLConfig extractSSLConfig(DeployerExtension extension) {
        SSLConfig sslConfig;
        if (extension.isTrustAll()) {
            sslConfig = new SSLConfig(true);
        } else if (extension.getCertificate() != null && !extension.getCertificate().isEmpty()) {
            sslConfig = new SSLConfig(extension.getCertificate());
        } else {
            sslConfig = new SSLConfig(false);
        }
        return sslConfig;
    }
}
