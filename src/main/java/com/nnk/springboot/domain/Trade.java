package com.nnk.springboot.domain;

import java.sql.Timestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity for Trade. Id is auto generated by Database.
 * account can't be blank.
 * type can't be blank.
 */
@Entity
@Table(name = "trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "account")
  @Size(max = 30)
  @NotBlank(message = "account can't be empty")
  private String account;

  @Column(name = "type")
  @Size(max = 30)
  @NotBlank(message = "type can't be empty")
  private String type;

  @Column(name = "buy_quantity")
  private Double buyQuantity;

  @Column(name = "sell_quantity")
  private Double sellQuantity;

  @Column(name = "buy_price")
  private Double buyPrice;

  @Column(name = "sell_price")
  private Double sellPrice;

  @Column(name = "benchmark")
  @Size(max = 125)
  private String benchmark;

  @Column(name = "trade_date")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp tradeDate;

  @Column(name = "security")
  @Size(max = 125)
  private String security;

  @Column(name = "status")
  @Size(max = 10)
  private String status;

  @Column(name = "trader")
  @Size(max = 125)
  private String trader;

  @Column(name = "book")
  @Size(max = 125)
  private String book;

  @Column(name = "creation_name")
  @Size(max = 125)
  private String creationName;

  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp creationDate;

  @Column(name = "revision_name")
  @Size(max = 125)
  private String revisionName;

  @Column(name = "revision_date")
  @DateTimeFormat(pattern = "MM/dd/yyyy")
  private Timestamp revisionDate;

  @Column(name = "deal_name")
  @Size(max = 125)
  private String dealName;

  @Column(name = "deal_type")
  @Size(max = 125)
  private String dealType;

  @Column(name = "source_list_id")
  @Size(max = 125)
  private String sourceListId;

  @Column(name = "side")
  @Size(max = 125)
  private String side;

  public Trade(String account, String type) {
    this.account = account;
    this.type = type;
  }
}
