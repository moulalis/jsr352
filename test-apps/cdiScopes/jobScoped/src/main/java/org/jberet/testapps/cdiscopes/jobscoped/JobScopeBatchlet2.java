/*
 * Copyright (c) 2015 Red Hat, Inc. and/or its affiliates.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Cheng Fang - Initial API and implementation
 */

package org.jberet.testapps.cdiscopes.jobscoped;

import java.lang.annotation.ElementType;
import java.util.List;
import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class JobScopeBatchlet2 extends AbstractBatchlet {
    @Inject
    private Foo fooTypeTarget;

    @Inject
    @Named("METHOD")
    private FooMethodTarget fooMethodTarget;

    @Inject
    @Named("FIELD")
    private FooFieldTarget fooFieldTarget;

    @Inject
    private StepContext stepContext;

    @Override
    public String process() throws Exception {
        final List<String> stepNamesTypeTarget = fooTypeTarget.getStepNames();
        final String stepName = stepContext.getStepName();
        stepNamesTypeTarget.add(stepName + ElementType.TYPE);
        String exitStatus1 = String.join(" ", stepNamesTypeTarget);

        final List<String> stepNamesMethodTarget = fooMethodTarget.getStepNames();
        stepNamesMethodTarget.add(stepName + ElementType.METHOD);
        String exitStatus2 = String.join(" ", stepNamesMethodTarget);

        final List<String> stepNamesFieldTarget = fooFieldTarget.getStepNames();
        stepNamesFieldTarget.add(stepName + ElementType.FIELD);
        String exitStatus3 = String.join(" ", stepNamesFieldTarget);

        if (stepNamesTypeTarget.size() < 2 || stepNamesMethodTarget.size() < 2
                || stepNamesFieldTarget.size() < 2) {
            throw new IllegalStateException(String.format(
            "Expecting size 2, but got stepNamesTypeTarget %s, stepNamesMethodTarget %s, stepNamesFieldTarget %s",
                    stepNamesTypeTarget.size(), stepNamesMethodTarget.size(), stepNamesFieldTarget.size()));
        }

        return String.join(" ", exitStatus1, exitStatus2, exitStatus3);

    }
}
