/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.attachment.store;

import java.util.Arrays;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mayocat.attachment.model.LoadedAttachment;
import org.mayocat.attachment.store.memory.AttachmentMemoryStore;
import org.mayocat.attachment.model.Attachment;

/**
 * @version $Id: 1ca976bc0d60b8b012fffdff6ecbbf60ee6a3501 $
 */
public class AttachmentMemoryStoreTest {

    private AttachmentStore store;

    @Before
    public void setUpStore() {
        store = new AttachmentMemoryStore();
    }

    @Test
    public void testCreateAttachmentAndGetBySlug() throws Exception {
        LoadedAttachment attachment = new LoadedAttachment();
        attachment.setSlug("my-attachment");
        store.create(attachment);
        Assert.assertSame(attachment, store.findAndLoadBySlug(attachment.getSlug()));
    }

    @Test
    public void testCreateAttachmentAndGetBySlugAndExtension() throws Exception {
        LoadedAttachment attachment = new LoadedAttachment();
        attachment.setSlug("my-attachment");
        attachment.setExtension("txt");
        store.create(attachment);
        Assert.assertSame(attachment, store.findAndLoadBySlugAndExtension(attachment.getSlug(), "txt"));
    }

    @Test
    public void testFindByParentAndExtensions() throws Exception {
        LoadedAttachment attachment1 = new LoadedAttachment();
        attachment1.setSlug("my-attachment1");
        attachment1.setExtension("txt");
        UUID parent = store.create(attachment1).getId();
        LoadedAttachment attachment2 = new LoadedAttachment();
        attachment2.setSlug("my-attachment2");
        attachment2.setExtension("md");
        attachment2.setParentId(parent);
        LoadedAttachment attachment3 = new LoadedAttachment();
        attachment3.setSlug("my-attachment3");
        attachment3.setExtension("ogg");
        store.create(attachment2);
        store.create(attachment3);
        Assert.assertEquals(0, store.findAllChildrenOf(attachment1, Arrays.asList("txt", "docx")).size());
        Assert.assertEquals(0, store.findAllChildrenOf(attachment2, Arrays.asList("md", "docx")).size());
        Assert.assertEquals(1, store.findAllChildrenOf(attachment1, Arrays.asList("md", "docx")).size());
    }

    @Test
    public void testFindByParentIdAndExtensions() throws Exception {
        Attachment attachment1 = new LoadedAttachment();
        attachment1.setSlug("my-attachment1");
        attachment1.setExtension("txt");
        attachment1 = store.create(attachment1);
        Attachment attachment2 = new LoadedAttachment();
        attachment2.setSlug("my-attachment2");
        attachment2.setExtension("md");
        attachment2.setParentId(attachment1.getId());
        Attachment attachment3 = new LoadedAttachment();
        attachment3.setSlug("my-attachment3");
        attachment3.setExtension("ogg");
        attachment2 = store.create(attachment2);
        attachment3 = store.create(attachment3);
        Assert.assertEquals(0, store.findAllChildrenOfParentIds(Arrays.asList(attachment1.getId(), attachment3.getId()), Arrays.asList("txt", "docx")).size());
        Assert.assertEquals(0, store.findAllChildrenOfParentIds(Arrays.asList(attachment2.getId()), Arrays.asList("md", "docx")).size());
        Assert.assertEquals(1, store.findAllChildrenOfParentIds(Arrays.asList(attachment1.getId()), Arrays.asList("md", "docx")).size());
        Assert.assertEquals(1, store.findAllChildrenOfParentIds(Arrays.asList(attachment1.getId(), attachment3.getId()), Arrays.asList("md", "docx")).size());
    }
}
