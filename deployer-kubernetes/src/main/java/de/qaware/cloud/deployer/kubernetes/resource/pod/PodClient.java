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
package de.qaware.cloud.deployer.kubernetes.resource.pod;

import de.qaware.cloud.deployer.kubernetes.resource.api.delete.options.DeleteOptions;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Pod interface which will be used by retrofit to create a pod client.
 */
interface PodClient {

    /**
     * Returns the http response for a request to the pod resource with the specified name and namespace.
     *
     * @param name      The pod name.
     * @param namespace The pod's namespace.
     * @return The server's http response.
     */
    @GET("api/v1/namespaces/{namespace}/pods/{name}")
    Call<ResponseBody> get(@Path("name") String name, @Path("namespace") String namespace);

    /**
     * Creates the specified pod.
     *
     * @param namespace      The namespace of the new pod.
     * @param podDescription The request body which contains the pod.
     * @return The server's http response.
     */
    @POST("api/v1/namespaces/{namespace}/pods")
    Call<ResponseBody> create(@Path("namespace") String namespace, @Body RequestBody podDescription);

    /**
     * Deletes the pod resource with the specified name.
     *
     * @param name          The pod's name.
     * @param namespace     The namespace of the pod.
     * @param deleteOptions The delete options.
     * @return The server's http response.
     */
    @HTTP(method = "DELETE", path = "api/v1/namespaces/{namespace}/pods/{name}", hasBody = true)
    Call<ResponseBody> delete(@Path("name") String name, @Path("namespace") String namespace, @Body DeleteOptions deleteOptions);
}
