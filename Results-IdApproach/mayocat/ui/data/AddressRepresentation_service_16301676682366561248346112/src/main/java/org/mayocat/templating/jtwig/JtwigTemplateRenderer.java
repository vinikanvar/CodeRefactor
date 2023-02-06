/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.templating.jtwig;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import javax.inject.Inject;
import org.mayocat.files.FileManager;
import org.mayocat.templating.TemplateRenderer;
import org.mayocat.templating.TemplateRenderingException;
import org.xwiki.component.annotation.Component;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 5bd7a6d5dd44bfa516527bbbf5ff545a732abb04 $
 */
@Component
public class JtwigTemplateRenderer implements TemplateRenderer {

    @Override
    public void render(Path templatePath, Map<String, Object> data, OutputStream outputStream) throws TemplateRenderingException {
        return;
    }

    @Override
    public String renderAsString(Path templatePath, Map<String, Object> data) throws TemplateRenderingException {
        return (String) null;
    }

    public int id = 0;

    public static JtwigTemplateRenderer getObject(int id) {
        JtwigTemplateRenderer obj = (JtwigTemplateRenderer) new Object();
        obj.id = id;
        return obj;
    }
}

