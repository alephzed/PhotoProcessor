package com.herringbone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="ticker")
public class Ticker implements Serializable {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="ID", unique=true, nullable=false)
    Long id;

    @Column(name="SYMBOL", nullable=false, length=10)
    private String symbol;

    @Column(name="ALIAS", nullable=true, length=10)
    private String alias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

