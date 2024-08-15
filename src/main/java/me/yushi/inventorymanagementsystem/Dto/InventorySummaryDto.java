/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.Dto;

import java.util.List;

/**
 *
 * @author yushi
 */
public class InventorySummaryDto implements IInventorySummaryDto {

    private final List<IProductDto> recentProductDtos;
    private final List<ISupplierDto> recenSupplierDtos;
    private final List<ICategoryDto> recentCategoryDtos;
    private final List<IInventoryTransactionDto> recentInventoryTransactionDtos;

    private InventorySummaryDto(Builder builder) {
        this.recentProductDtos = builder.recentProductDtos;
        this.recenSupplierDtos = builder.recenSupplierDtos;
        this.recentCategoryDtos = builder.recentCategoryDtos;
        this.recentInventoryTransactionDtos = builder.recentInventoryTransactionDtos;

    }

    public static class Builder {

        private List<IProductDto> recentProductDtos;
        private List<ISupplierDto> recenSupplierDtos;
        private List<ICategoryDto> recentCategoryDtos;
        private List<IInventoryTransactionDto> recentInventoryTransactionDtos;

        public Builder recentProductDtos(List<IProductDto> recentProductDtos) {

            this.recentProductDtos = recentProductDtos;
            return this;

        }

        public Builder recenSupplierDtos(List<ISupplierDto> recenSupplierDtos) {
            this.recenSupplierDtos = recenSupplierDtos;
            return this;
        }

        public Builder recentCategoryDtos(List<ICategoryDto> recentCategoryDtos) {
            this.recentCategoryDtos = recentCategoryDtos;
            return this;
        }

        public Builder recentInventoryTransactionDtos(List<IInventoryTransactionDto> recentInventoryTransactionDtos) {
            this.recentInventoryTransactionDtos = recentInventoryTransactionDtos;
            return this;
        }

        public InventorySummaryDto build() {
            return new InventorySummaryDto(this);
        }
    }

    @Override
    public List<IProductDto> getRecentProductDtos() {
        return this.recentProductDtos;

    }

    @Override
    public List<ICategoryDto> getRecentCategoryDtos() {
        return this.recentCategoryDtos;
    }

    @Override
    public List<ISupplierDto> getRecentSupplierDtos() {
        return this.recenSupplierDtos;
    }

    @Override
    public List<IInventoryTransactionDto> getRecentInventoryTransactionDtos() {
        return this.recentInventoryTransactionDtos;
    }

}
