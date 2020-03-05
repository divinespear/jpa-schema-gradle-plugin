package test.spec.model;

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

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * Test JPA Model
 *
 * @author divinespear
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "MANY_COLUMN_TABLE")
@Getter
@Setter
public class ManyColumns {

  @Id
  @GeneratedValue
  private Long id;

  private String column00;
  private String column01;
  private String column02;
  private String column03;
  private String column04;
  private String column05;
  private String column06;
  private String column07;
  private String column08;
  private String column09;
  private String column10;
  private String column11;
  private String column12;
  private String column13;
  private String column14;
  private String column15;
  private String column16;
  private String column17;
  private String column18;
  private String column19;
  private String column20;
  private String column21;
  private String column22;
  private String column23;
  private String column24;
  private String column25;
  private String column26;
  private String column27;
  private String column28;
  private String column29;
}
