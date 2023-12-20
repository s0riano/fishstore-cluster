package com.seafood.inventory.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<SaleDTO> findAllSales() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(this::toSaleDTO) // using method reference for consistency
                .collect(Collectors.toList());
    }

    public List<SaleDTO> getSalesByCatchId(UUID catchId) {
        List<Sale> sales = saleRepository.findByCatchEntityCatchId(catchId);
        return sales.stream()
                .map(this::toSaleDTO) // using method reference for consistency
                .collect(Collectors.toList());
    }

    public List<SaleDTO> getSalesBySellerId(UUID sellerId) {
        List<Sale> sales = saleRepository.findByCatchEntityShopId(sellerId);
        return sales.stream()
                .map(this::toSaleDTO) // This assumes the method toSaleDTO is in this class
                .collect(Collectors.toList());
    }

    private SaleDTO toSaleDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setSaleId(sale.getSaleId());
        dto.setCatchId(sale.getCatchId()); // getCatchId() is the method in your Sale entity
        dto.setSeafoodType(sale.getCatchEntity().getSeafoodType().toString()); // assuming seafoodType is an enum
        dto.setKilos(sale.getKilos());
        dto.setSaleDate(sale.getSaleDate());
        return dto;
    }

}
