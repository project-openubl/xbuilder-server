package org.openublpe.xmlbuilder.core.models.input.constraints;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * check both basic constraints and high level ones.
 * high level constraints are not checked if basic constraints fail
 */
@GroupSequence({Default.class, HighLevelGroupValidation.class})
public interface CompleteValidation {
}
