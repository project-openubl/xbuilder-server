/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.quarkus.freemarker;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.List;
import java.util.Optional;

@ConfigRoot(name = "xml-builder.freemarker", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class FreemarkerBuildConfig {

    /**
     * Comma-separated list of locations to scan recursively for templates. All tree folder from 'locations'
     * will be added as a resource.
     * Unprefixed locations or locations starting with classpath will be processed in the same way.
     */
    @ConfigItem(defaultValue = "freemarker/templates")
    public List<String> locations;

    /**
     * List of canonical Java Class names that are used in the Freemarker Templates. This will help
     * to add your JavaBeans to the list of Quarkus Refection classes. If you are already
     * adding your Classes using @RegisterForReflection or any other method then
     * you don't need to use this property.
     *
     *
     * Tip: If you want to add a nested static classes you can use the something like: org.mycompany.MyClass$MyNestedClass
     */
    @ConfigItem
    public Optional<List<String>> classModels;
}
