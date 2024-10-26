package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.service.SupplierService;

/**
 *
 * @author yushi
 */
public class SupplierController implements ISupplierController {
    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Business logic for CRUD operations
    @Override
    public Supplier createSupplier(Supplier newSupplier) {
        if (newSupplier == null) {
            System.err.println("Error: Supplier cannot be null");
            return null;
        }
        return supplierService.createSupplier(newSupplier);
    }

    @Override
    public Supplier updateSupplier(Supplier updateSupplier) {
        if (updateSupplier == null) {
            System.err.println("Error: Supplier cannot be null");
            return null;
        }
        return supplierService.updateSupplier(updateSupplier);
    }

    @Override
    public Supplier getSupplierByID(String supplierID) {
        if (supplierID == null || supplierID.isEmpty()) {
            System.err.println("Error: SupplierID cannot be null or empty");
            return null;
        }
        return supplierService.getSupplierByID(supplierID);
    }

    @Override
    public boolean deleteSupplier(String supplierID) {
        if (supplierID == null || supplierID.isEmpty()) {
            System.err.println("Error: SupplierID cannot be null or empty");
            return false;
        }
        return supplierService.deleteSupplier(supplierID);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @Override
    public boolean haveLinkedProduct(String supplierID) {
        return supplierService.haveLinkedProduct(supplierID);
    }

}
