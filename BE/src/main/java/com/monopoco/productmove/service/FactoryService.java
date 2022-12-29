package com.monopoco.productmove.service;

import java.util.List;

public interface FactoryService {
    public int exportProduct(String distributorAgentName, List<String> productSerial);
}
