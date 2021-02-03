package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rule_name")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleName {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "name")
  @Size(max = 125)
  private String name;

  @Column(name = "description")
  @Size(max = 125)
  private String description;

  @Column(name = "json")
  @Size(max = 125)
  private String json;

  @Column(name = "template")
  @Size(max = 512)
  private String template;

  @Column(name = "sql_str")
  @Size(max = 125)
  private String sqlStr;

  @Column(name = "sql_part")
  @Size(max = 125)
  private String sqlPart;

  public RuleName(String name, String description, String json, String template, String sqlStr,
                  String sqlPart) {
    this.name = name;
    this.description = description;
    this.json = json;
    this.template = template;
    this.sqlStr = sqlStr;
    this.sqlPart = sqlPart;
  }
}
