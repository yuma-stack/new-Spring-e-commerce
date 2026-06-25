package co.istad.ecommerce.features.product;


import co.istad.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.ecommerce.features.product.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    /**
     * Find Product by pagination
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<ProductResponse> findAll(int  pageNumber, int pageSize);

    //USE BELOW COMMAND (ALSO FOR FINAL PROJECT)
    /**
     * Create a new product
     * @param createProductRequest is requesting data for creating product
     * @return {@link ProductResponse}
     * @Author liep_sokkeang
     * @since 23-June-2026
     */
    ProductResponse createNew(CreateProductRequest createProductRequest);




}