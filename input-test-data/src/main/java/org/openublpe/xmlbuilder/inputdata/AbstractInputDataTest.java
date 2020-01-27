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
package org.openublpe.xmlbuilder.inputdata;

import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.creditNote.CreditNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.InputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.SummaryDocumentInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.VoidedDocumentInputGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public abstract class AbstractInputDataTest {

    protected static List<InvoiceInputModel> INVOICES = new ArrayList<>();
    protected static List<CreditNoteInputModel> CREDIT_NOTES = new ArrayList<>();
    protected static List<DebitNoteInputModel> DEBIT_NOTES = new ArrayList<>();
    protected static List<VoidedDocumentInputModel> VOIDED_DOCUMENTS = new ArrayList<>();
    protected static List<SummaryDocumentInputModel> SUMMARY_DOCUMENTS = new ArrayList<>();

    protected static Map<Object, Optional<String>> SNAPSHOTS = new HashMap<>();
    protected static Map<Object, Class> GENERATORS_CLASSES = new HashMap<>();

    public AbstractInputDataTest() {
        loadInputGenerators();
    }

    private static void loadGenerators(List inputs, Class<? extends InputGenerator> classz) {
        if (inputs.isEmpty()) {
            synchronized (AbstractInputDataTest.class) {
                if (inputs.isEmpty()) {
                    ServiceLoader<? extends InputGenerator> serviceLoader = ServiceLoader.load(classz);
                    for (InputGenerator generator : serviceLoader) {
                        Object input = generator.getInput();
                        inputs.add(input);
                        SNAPSHOTS.put(input, generator.getSnapshot());
                        GENERATORS_CLASSES.put(input, generator.getClass());
                    }
                }
            }
        }
    }

    private static void loadInputGenerators() {
        loadGenerators(INVOICES, InvoiceInputGenerator.class);
        loadGenerators(CREDIT_NOTES, CreditNoteInputGenerator.class);
        loadGenerators(DEBIT_NOTES, DebitNoteInputGenerator.class);
        loadGenerators(VOIDED_DOCUMENTS, VoidedDocumentInputGenerator.class);
        loadGenerators(SUMMARY_DOCUMENTS, SummaryDocumentInputGenerator.class);
    }

    protected String messageInputDataError(Object input, String errorMessage) {
        return "[" + GENERATORS_CLASSES.get(input).getCanonicalName() + "]\n" + errorMessage;
    }

    protected String messageInputDataError(Object input, String xml, String errorMessage) {
        return new StringBuilder()
                .append("\n")
                .append(xml).append("\n")
                .append("CLASS ")
                .append(GENERATORS_CLASSES.get(input).getCanonicalName()).append("\n")
                .append("MESSAGE ").append(errorMessage)
                .toString();
    }

}
