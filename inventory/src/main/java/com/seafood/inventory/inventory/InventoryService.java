package com.seafood.inventory.inventory;

import com.seafood.inventory.enums.SeafoodType;
import com.seafood.inventory.sale.Sale;
import com.seafood.inventory.sale.SaleRepository;
import com.seafood.inventory.sellercatch.Catch;
import com.seafood.inventory.sellercatch.CatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService {

    @Autowired
    private CatchRepository catchRepository;

    @Autowired
    private SaleRepository saleRepository;

}
