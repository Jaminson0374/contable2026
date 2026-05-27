package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ProductMapper {

    @Mapping(target = "productTypeId",
            expression = "java(e.getProductType() != null ? e.getProductType().getId() : null)")
    @Mapping(target = "productStateId",
            expression = "java(e.getProductState() != null ? e.getProductState().getId() : null)")
    @Mapping(target = "brandId",
            expression = "java(e.getBrand() != null ? e.getBrand().getId() : null)")
    @Mapping(target = "modelId",
            expression = "java(e.getModel() != null ? e.getModel().getId() : null)")
    @Mapping(target = "categoryId",
            expression = "java(e.getCategory() != null ? e.getCategory().getId() : null)")
    @Mapping(target = "groupId",
            expression = "java(e.getProductGroup() != null ? e.getProductGroup().getId() : null)")
    @Mapping(target = "unitOfMeasureId",
            expression = "java(e.getUnitOfMeasure() != null ? e.getUnitOfMeasure().getId() : null)")
    @Mapping(target = "incomeAccountId",
            expression = "java(e.getIncomeAccount() != null ? e.getIncomeAccount().getId() : null)")
    @Mapping(target = "inventoryAccountId",
            expression = "java(e.getInventoryAccount() != null ? e.getInventoryAccount().getId() : null)")
    @Mapping(target = "costOfSalesAcctId",
            expression = "java(e.getCostOfSalesAcct() != null ? e.getCostOfSalesAcct().getId() : null)")
    @Mapping(target = "perishable", source = "perishable")
    @Mapping(target = "warehouses", ignore = true)
    @Mapping(target = "suppliers", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    @Mapping(target = "priceEntries", ignore = true)
    @Mapping(target = "presentations", ignore = true)
    Product toDomain(ProductEntity e);

    @Mapping(target = "productType", ignore = true)
    @Mapping(target = "productState", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "productGroup", ignore = true)
    @Mapping(target = "unitOfMeasure", ignore = true)
    @Mapping(target = "incomeAccount", ignore = true)
    @Mapping(target = "inventoryAccount", ignore = true)
    @Mapping(target = "costOfSalesAcct", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductEntity toEntityBase(Product domain);
}
