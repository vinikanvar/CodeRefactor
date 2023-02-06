/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.mail;

import java.util.Locale;
import com.google.common.base.Optional;
import javax.activation.DataSource;

/**
 * @version $Id: 3a17a8abbaee2776ff1d44876f8d66025043e9e4 $
 */
public class MailTemplateServer extends Mail {

    private String template;

    private Optional<Locale> locale = Optional.absent();

    @Override
    public MailTemplate from(String from) {
        super.from(from);
        return this;
    }

    @Override
    public MailTemplate to(String... to) {
        super.to(to);
        return this;
    }

    @Override
    public MailTemplate cc(String... cc) {
        super.cc(cc);
        return this;
    }

    @Override
    public MailTemplate bcc(String... bcc) {
        super.bcc(bcc);
        return this;
    }

    @Override
    public MailTemplate subject(String subject) {
        super.subject(subject);
        return this;
    }

    @Override
    public MailTemplate text(String subject) {
        super.text(subject);
        return this;
    }

    @Override
    public MailTemplate html(String subject) {
        super.html(subject);
        return this;
    }

    @Override
    public MailTemplate addAttachment(MailAttachment attachment) {
        super.addAttachment(attachment);
        return this;
    }

    public MailTemplate template(String template) {
        this.template = template;
        return this;
    }

    public MailTemplate locale(Locale locale) {
        this.locale = Optional.fromNullable(locale);
        return this;
    }

    public String template() {
        return template;
    }

    public Optional<Locale> locale() {
        return locale;
    }
}

