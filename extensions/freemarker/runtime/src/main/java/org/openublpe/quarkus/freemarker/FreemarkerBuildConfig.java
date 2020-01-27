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

@ConfigRoot(name = "xml-builder.freemarker", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class FreemarkerBuildConfig {
    /**
     * Comma-separated list of locations to scan recursively for templates. The location type is determined by its prefix.
     * Unprefixed locations or locations starting with classpath: point to a package on the classpath and may FTL templates.
     * Locations starting with filesystem: point to a directory on the filesystem, may only contain FTL templates and are only
     * scanned recursively down non-hidden directories.
     */
    @ConfigItem
    public List<String> locations;
}
