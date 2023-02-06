/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.accounts.store.memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.validation.Valid;
import org.mayocat.accounts.model.Role;
import org.mayocat.accounts.model.User;
import org.mayocat.accounts.store.UserStore;
import org.mayocat.store.EntityAlreadyExistsException;
import org.mayocat.store.EntityDoesNotExistException;
import org.mayocat.store.InvalidEntityException;
import org.mayocat.store.memory.BaseEntityMemoryStore;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * In-memory implementation of {@link UserStore}
 *
 * @version $Id: 6e3eca029770e9d0d3ff6483acb4e23509a613b7 $
 */
@Component("memory")
public class MemoryUserStoreServer extends BaseEntityMemoryStore<User> implements UserStore {

    private Map<User, List<Role>> userRoles = new HashMap<>();

    private Predicate<User> withUserNameOrEmail(final String userNameOrEmail) {
        return new Predicate<User>() {

            public boolean apply(@Nullable User input) {
                return input.getSlug().equals(userNameOrEmail) || input.getEmail().equals(userNameOrEmail);
            }
        };
    }

    private Predicate<User> withValidationKey(final String validationKey) {
        return new Predicate<User>() {

            public boolean apply(@Nullable User input) {
                return input.getValidationKey().equals(validationKey);
            }
        };
    }

    public User create(@Valid User user, Role initialRole) throws EntityAlreadyExistsException, InvalidEntityException {
        userRoles.put(user, Arrays.asList(initialRole));
        return super.create(user);
    }

    @Override
    public User create(@Valid User user) throws EntityAlreadyExistsException, InvalidEntityException {
        return this.create(user, Role.ADMIN);
    }

    public User findUserByEmailOrUserName(String userNameOrEmail) {
        return FluentIterable.from(findAll(0, 0)).filter(withUserNameOrEmail(userNameOrEmail)).first().orNull();
    }

    public List<Role> findRolesForUser(User user) {
        return userRoles.containsKey(user) ? userRoles.get(user) : new ArrayList<Role>();
    }

    public User findByValidationKey(String validationKey) {
        return FluentIterable.from(findAll(0, 0)).filter(withValidationKey(validationKey)).first().orNull();
    }

    public void updatePassword(User user, String hash) {
        this.findById(user.getId()).setPassword(hash);
    }

    @Override
    public void createPasswordResetRequest(User user, String key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void deletePasswordResetRequest(String key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public User findUserByPasswordResetRequest(String resetKey) {
        throw new RuntimeException("Not implemented");
    }
}

