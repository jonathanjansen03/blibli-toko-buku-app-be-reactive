package com.example.bliblitokobukuappbereactive.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.bliblitokobukuappbereactive.model.metadata.AuditMetadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Transaction.COLLECTION_NAME)
public class Transaction extends AuditMetadata {

  public static final String COLLECTION_NAME = "transactions";

  @Id
  private String id;

  @Version
  private int version;

  private Book book;
  private int qty;
  private LocalDateTime purchasedAt = LocalDateTime.now(ZoneId.of("Etc/GMT+7"));

  public Transaction(Book book, int qty) {
    this.book = book;
    this.qty = qty;
  }
}
