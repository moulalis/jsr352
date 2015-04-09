/*
 * Copyright (c) 2013-2014 Red Hat, Inc. and/or its affiliates.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Cheng Fang - Initial API and implementation
 */

package org.jberet.job.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Job extends InheritableJobElement implements Serializable, PropertiesHolder {
    private static final long serialVersionUID = -3566969844084046522L;

    public static final String UNRESTARTABLE = "jberet.unrestartable";

    public static final String JOB_XML_NAME = "jberet.jobXmlName";

    private String restartable;

    private final List<JobElement> jobElements = new ArrayList<JobElement>();

    private String jobXmlName;

    /**
     * Steps and Flows that inherit from a parent (i.e., has a non-null parent attribute). They can be top-level
     * job elements or nested under other job elements.
     */
    final List<InheritableJobElement> inheritingJobElements = new ArrayList<InheritableJobElement>();

    Job(final String id) {
        super(id);
    }

    public String getRestartable() {
        return restartable;
    }

    public boolean getRestartableBoolean() {
        return (restartable == null || restartable.isEmpty()) ? true : Boolean.parseBoolean(restartable);
    }

    void setRestartable(final String restartable) {
        if (restartable != null) {
            this.restartable = restartable;
        }
    }

    /**
     * Sets {@link #jobXmlName} only if it differs from job id.
     *
     * @param jobXmlName the base name of the job xml file, which may be different from job id attribute
     */
    public void setJobXmlName(final String jobXmlName) {
        this.jobXmlName = jobXmlName;
    }

    /**
     * Gets {@link #jobXmlName}, which may be null.
     *
     * @return a non-null value if it differs from {@link #id}; otherwise return null.
     */
    public String getJobXmlName() {
        return jobXmlName;
    }

    public List<JobElement> getJobElements() {
        return jobElements;
    }

    void addJobElement(final JobElement jobElement) {
        jobElements.add(jobElement);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Job job = (Job) o;

        if (!id.equals(job.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public List<Transition> getTransitionElements() {
        throw new IllegalStateException();
    }

    @Override
    public void addTransitionElement(final Transition transition) {
        throw new IllegalStateException();
    }

    public List<InheritableJobElement> getInheritingJobElements() {
        return inheritingJobElements;
    }
}
