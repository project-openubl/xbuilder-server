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
package org.openublpe.xmlbuilder.apicore.utils;

import javax.ws.rs.core.HttpHeaders;

public class MediaTypeMatcher {

    public static boolean isHtmlRequest(HttpHeaders headers) {
        for (javax.ws.rs.core.MediaType m : headers.getAcceptableMediaTypes()) {
            if (!m.isWildcardType() && m.isCompatible(javax.ws.rs.core.MediaType.TEXT_HTML_TYPE)) {
                return true;
            }
        }
        return false;
    }

}
