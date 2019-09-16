/*
 * Copyright 2019 vmadalin.com
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

package com.vmadalin.android.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vmadalin.android.di.scopes.AppScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * Class factory to create our custom view com.vmadalin.dynamicfeatures.characterdetail.models
 */
@Suppress("UNCHECKED_CAST")
@AppScope
class ViewModelFactory @Inject
constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            // Check if there is a subclass of ViewModel of type T
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        try {
            return creator?.get() as T
        } catch (e: Exception) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
    }
}
