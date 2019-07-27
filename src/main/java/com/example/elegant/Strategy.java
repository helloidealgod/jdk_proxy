package com.example.elegant;

import java.math.BigDecimal;

public interface Strategy {
    BigDecimal getPay(Integer type);
}
