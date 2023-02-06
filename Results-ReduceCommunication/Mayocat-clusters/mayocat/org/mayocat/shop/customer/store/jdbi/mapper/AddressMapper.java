/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.customer.store.jdbi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.mayocat.shop.customer.model.Address;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * @version $Id: a0c521541b12cd00006609d0e10380739db4bb49 $
 */
public class AddressMapper implements ResultSetMapper<Address> {

    @Override
    public Address map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        Address address = new Address();
        address.setId((UUID) resultSet.getObject("address_id"));
        address.setCustomerId((UUID) resultSet.getObject("customer_id"));
        address.setCompany(resultSet.getString("company"));
        address.setFullName(resultSet.getString("full_name"));
        address.setStreet(resultSet.getString("street"));
        address.setStreetComplement(resultSet.getString("street_complement"));
        address.setZip(resultSet.getString("zip"));
        address.setCity(resultSet.getString("city"));
        address.setCountry(resultSet.getString("country"));
        address.setType(resultSet.getString("type"));
        address.setNote(resultSet.getString("note"));
        return address;
    }
}
