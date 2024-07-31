/*
  Copyright 2022 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
*/

package com.adobe.marketing.mobile.internal.eventhub

import com.adobe.marketing.mobile.Extension
import com.adobe.marketing.mobile.ExtensionApi
import com.adobe.marketing.mobile.ExtensionHelper
import com.adobe.marketing.mobile.internal.CoreConstants
import com.adobe.marketing.mobile.services.Log

// Type extensions for [Extension] to allow for easier usage

/**
 *  Function to initialize Extension with [ExtensionApi]
 */
internal fun Class<out Extension>.initWith(extensionApi: ExtensionApi): Extension? {
    try {
        val extensionConstructor = this.getDeclaredConstructor(ExtensionApi::class.java)
        extensionConstructor.isAccessible = true
        return extensionConstructor.newInstance(extensionApi)
    } catch (ex: Exception) {
        Log.debug(CoreConstants.LOG_TAG, "ExtensionExt", "Initializing Extension $this failed with $ex")
    }

    return null
}

internal fun Class<out Extension>.initWith(extensionApi: ExtensionApi, tenant: Tenant): Extension? {
    try {
        val extensionConstructor = this.getDeclaredConstructor(ExtensionApi::class.java)
        extensionConstructor.isAccessible = true
        return extensionConstructor.newInstance(extensionApi, tenant)
    } catch (ex: Exception) {
        Log.debug(
            CoreConstants.LOG_TAG, "ExtensionExt", "Initializing Extension $this failed with $ex"
        )
    }

    return null
}

/**
 * Property to get Extension name
 */
internal val Extension.extensionName: String?
    get() = ExtensionHelper.getName(this)

/**
 * Property to get Extension version
 */
internal val Extension.extensionVersion: String?
    get() = ExtensionHelper.getVersion(this)

/**
 * Property to get Extension friendly name
 */
internal val Extension.extensionFriendlyName: String?
    get() = ExtensionHelper.getFriendlyName(this)

/**
 * Property to get Extension metadata
 */
internal val Extension.extensionMetadata: Map<String, String>?
    get() = ExtensionHelper.getMetadata(this)

/**
 * Function to notify that the Extension has been unregistered
 */
internal fun Extension.onExtensionUnregistered() {
    ExtensionHelper.notifyUnregistered(this)
}

/**
 * Function to notify that the Extension has been registered
 */
internal fun Extension.onExtensionRegistered() {
    ExtensionHelper.notifyRegistered(this)
}

/**
 * Helper to get extension type name
 */
internal val Class<out Extension>.extensionTypeName
    get() = this.name
