package com.seafood.inventory.sale;

public class SaleMapper {

    public static SaleDTO mapSaleToDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setSaleId(sale.getSaleId());
        dto.setCatchId(sale.getCatchEntity().getCatchId());
        dto.setSeafoodType(sale.getCatchEntity().getSeafoodType().toString());
        /*dto.setKilosFromCatch(sale.getCatchEntity().getKilos());
        dto.setKilosSold(sale.getKilos());*/
        dto.setSaleDate(sale.getSaleDate());
        return dto;
    }
}
