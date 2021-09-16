package com.herringbone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ticker")
public interface TickerRepository extends JpaRepository<Ticker, Long> {
    List<Ticker> findBySymbolOrAlias(String symbol, String alias);
    List<Ticker> findBySymbol(String symbol);
}
