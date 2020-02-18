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
package org.openublpe.xmlbuilder.inputdata.generator;

import java.util.Optional;

public interface InputGenerator<T> {

    // This should correspond to the number of test created
    int NUMBER_TEST_INVOICES = 35;
    int NUMBER_TEST_CREDIT_NOTES = 12;
    int NUMBER_TEST_DEBIT_NOTES = 13;
    int NUMBER_TEST_VOIDED_DOCUMENTS = 2;
    int NUMBER_TEST_SUMMARY_DOCUMENTS = 2;

    T getInput();

    default Optional<String> getSnapshot() {
        return Optional.empty();
    }

}
