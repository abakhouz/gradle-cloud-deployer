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
package de.qaware.cloud.deployer.dcos.logging;

import de.qaware.cloud.deployer.commons.logging.DeployerMessageBundle;

/**
 * A class which offers all relevant log messages for this project.
 */
public final class DCOSMessageBundle {

    /**
     * A bundle which contains all dcos messages.
     */
    public static final DeployerMessageBundle DCOS_MESSAGE_BUNDLE = new DeployerMessageBundle("dcos-log-messages");

    /**
     * UTILITY.
     */
    private DCOSMessageBundle() {
    }
}
