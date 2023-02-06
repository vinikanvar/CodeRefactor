/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package mayoapp.dao;

import java.util.List;
import java.util.UUID;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.cms.news.model.Article;
import org.mayocat.cms.news.store.jdbi.mapper.ArticleMapper;
import org.mayocat.store.rdbms.dbi.argument.DateAsTimestampArgumentFactory;
import org.mayocat.addons.store.dbi.AddonsHelper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterArgumentFactory;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

/**
 * @version $Id: 5d8b2dcb9fdd5e800eda8695ea1c8f64634946a7 $
 */
@RegisterMapper(ArticleMapper.class)
@RegisterArgumentFactory(DateAsTimestampArgumentFactory.class)
@UseStringTemplate3StatementLocator
public abstract class ArticleDAO implements EntityDAO<Article>, Transactional<ArticleDAO>, AddonsDAO<Article> {

    @SqlUpdate
    public abstract void createArticle(@BindBean("article") Article article);

    @SqlUpdate
    public abstract Integer updateArticle(@BindBean("article") Article article);

    @SqlQuery
    public abstract List<Article> findAllPublished(@Bind("tenantId") UUID tenant, @Bind("number") Integer number, @Bind("offset") Integer offset);

    @SqlQuery
    public abstract List<Article> findAllLatestFirst(@Bind("tenantId") UUID tenant, @Bind("number") Integer number, @Bind("offset") Integer offset);

    @SqlQuery
    public abstract Integer countAllPublished(@Bind("tenantId") UUID tenant);

    public void createOrUpdateAddons(Article entity) {
        AddonsHelper.createOrUpdateAddons(this, entity);
    }
}
