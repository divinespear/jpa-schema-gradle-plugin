package io.github.divinespear.model;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Date;

import javax.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Test JPA model
 * 
 * @author divinespear
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "KEY_VALUE_STORE")
@EntityListeners(AuditingEntityListener.class)
public class KeyValueStore {

    @Id
    @Column(name = "STORED_KEY", length = 128)
    private String key;

    @Column(name = "STORED_VALUE", length = 32768)
    private String value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
