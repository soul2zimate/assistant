/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.set.assistant.data.payload;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

import org.jboss.jbossset.bugclerk.Severity;
import org.jboss.jbossset.bugclerk.Violation;
import org.jboss.set.aphrodite.domain.IssueStatus;
import org.jboss.set.aphrodite.domain.IssueType;

/**
 * @author wangc
 *
 */
public class PayloadIssue {
    private URL link;
    private String label;
    private String summary;
    private IssueStatus status;
    private IssueType type;
    private Map<String, String> flags;
    private Collection<Violation> violations;
    private Severity maxSeverity;

    public PayloadIssue(URL link, String label, String summary, IssueStatus status, IssueType type, Map<String, String> flags,
            Collection<Violation> violations) {
        this.link = link;
        this.label = label;
        this.summary = summary;
        this.status = status;
        this.type = type;
        this.flags = flags;
        this.violations = violations;
        this.maxSeverity = violations.stream().map(violation -> violation.getLevel()).reduce((severity1, severity2) -> maxSeverity(severity1, severity2)).orElse(null);
    }

    private Severity maxSeverity(Severity s1, Severity s2) {
        if (s1 == Severity.BLOCKER || s2 == Severity.BLOCKER)
            return Severity.BLOCKER;
        if (s1 == Severity.CRITICAL || s2 == Severity.CRITICAL)
            return Severity.CRITICAL;
        if (s1 == Severity.MAJOR || s2 == Severity.MAJOR)
            return Severity.MAJOR;
        if (s1 == Severity.MINOR || s2 == Severity.MINOR)
            return Severity.MINOR;
        return Severity.TRIVIAL;
    }

    public URL getLink() {
        return link;
    }

    public String getLabel() {
        return label;
    }

    public String getSummary() {
        return summary;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public IssueType getType() {
        return type;
    }

    public Map<String, String> getFlags() {
        return flags;
    }

    public Collection<Violation> getViolations() {
        return violations;
    }

    public Severity getMaxSeverity() {
        return maxSeverity;
    }
}