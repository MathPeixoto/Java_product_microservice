package com.mpeixoto.product.services;

import com.mpeixoto.product.exception.InternalServerErrorException;
import com.mpeixoto.product.exception.NotFoundException;
import com.mpeixoto.product.mapper.ProductMapper;
import com.mpeixoto.product.mapper.SupplierMapper;
import com.mpeixoto.product.persistence.domain.ProductEntity;
import com.mpeixoto.product.persistence.domain.SupplierEntity;
import com.mpeixoto.product.persistence.repository.ProductRepository;
import com.mpeixoto.product.persistence.repository.SupplierRepository;
import com.mpeixoto.product.web.model.CategoryDto;
import com.mpeixoto.product.web.model.ProductDto;
import com.mpeixoto.product.web.model.QueryProduct;
import com.mpeixoto.product.web.model.SupplierDto;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import static java.util.Comparator.comparing;

/**
 * Class responsible for being the intermediary between the verifyIfProductsAreSold endpoint and the
 * repository.
 *
 * @author mpeixoto
 */
@Slf4j
@Service
public class ProductService {
    private static final String RANGE_OF_RANDOM_NUMERIC = "7";
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final SupplierMapper supplierMapper;

    /**
     * Default constructor of the class.
     *
     * @param productRepository  Type: ProductRepository
     * @param productMapper      Type: ProductMapper
     * @param supplierRepository Type: SupplierRepository
     * @param supplierMapper     Type: SupplierMapper
     */
    public ProductService(
            ProductRepository productRepository,
            ProductMapper productMapper,
            SupplierRepository supplierRepository,
            SupplierMapper supplierMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    /**
     * Method responsible for updating a product given its product id.
     *
     * @param productId  Type: String
     * @param productDto Type: ProductDto
     * @return the product that was updated containing its supplier
     */
    public ProductDto updateAProduct(String productId, ProductDto productDto) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        if (productEntity == null) {
            throw new NotFoundException(
                    "The product with the product id: " + productId + " does not exist");
        }

        ProductEntity productEntityMapped = productSettings(productEntity, productDto, productId);

        productEntityMapped = productRepository.save(productEntityMapped);
        return productMapper.productEntityToProductDto(productEntityMapped);
    }

    /**
     * Method responsible for retrieving a product from database given its productId.
     *
     * @param fetchSupplier Type: boolean
     * @param productId     Type: String
     * @return The product that was retrieved from database
     */
    public ProductDto retrieveAProduct(boolean fetchSupplier, String productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);

        if (productEntity == null) {
            throw new NotFoundException("The required product does not exist");
        }

        ProductDto productDto = productMapper.productEntityToProductDto(productEntity);
        if (!fetchSupplier) {
            productDto.setSupplierDto(null);
        }
        return productDto;
    }

    /**
     * Method responsible for adding a new product in the database.
     *
     * @param productDto Type: ProductDto
     * @return The product that was inserted in the database
     */
    public ProductDto addProduct(ProductDto productDto) {
        ProductEntity productEntity = productRepository.findByName(productDto.getName());
        if (productEntity != null) {
            return productMapper.productEntityToProductDto(productEntity);
        }
        productDto.setProductId(generateProductId());
        SupplierEntity supplierEntity = checkSupplier(productDto.getSupplierDto());
        productEntity = productMapper.productDtoToProductEntity(productDto);
        productEntity.setSupplierEntity(supplierEntity);
        productEntity = productRepository.save(productEntity);
        return productMapper.productEntityToProductDto(productEntity);
    }

    /**
     * Method responsible for verifying which items are sold.
     *
     * @param queryProduct Type: QueryProduct
     * @return A list of ProductDto, containing the products that are sold
     */
    public List<ProductDto> retrieveProducts(QueryProduct queryProduct) {
        CategoryDto category = queryProduct.getCategory();
        List<String> productsIds = queryProduct.getIds();
        String name = queryProduct.getName();
        String sort = queryProduct.getSort();
        Integer limit = queryProduct.getLimit();
        Integer offset = queryProduct.getOffset();
        boolean fetchSuppliers = queryProduct.getFetchSuppliers();

        List<ProductEntity> productEntityList =
                productRepository
                        .findAllProducts(limit, offset)
                        .<InternalServerErrorException>orElseThrow(
                                () -> {
                                    throw new InternalServerErrorException("Database have not responded well");
                                });

        List<ProductDto> productDtoList =
                productEntityList.stream()
                        .map(productMapper::productEntityToProductDto)
                        .toList();

        fetchSuppliers(fetchSuppliers, productDtoList);
        productDtoList = sortList(sort, productDtoList);

        if (!Strings.isBlank(name)) {
            productDtoList =
                    productsFilter(productDto -> productDto.getName().equalsIgnoreCase(name), productDtoList);
        }
        if (category != null) {
            productDtoList =
                    productsFilter(productDto -> productDto.getCategory().equals(category), productDtoList);
        }
        if (productsIds != null && !productsIds.isEmpty()) {
            productDtoList =
                    productsFilter(
                            productDto ->
                                    (productsIds.stream().anyMatch(id -> productDto.getProductId().equals(id))),
                            productDtoList);
        }

        return productDtoList;
    }

    private List<ProductDto> productsFilter(
            Predicate<ProductDto> predicate, List<ProductDto> productDtoList) {
        return productDtoList.stream().filter(predicate).toList();
    }

    private List<ProductDto> sortList(String sort, List<ProductDto> productDtoList) {
        final Comparator<ProductDto> productDtoComparatorName = comparing(ProductDto::getName);
        final Comparator<ProductDto> productDtoComparatorPrice = comparing(ProductDto::getPrice);
        final Stream<ProductDto> productDtoStream = productDtoList.stream();

        return switch (sort.toLowerCase()) {
            case "desc.price" -> productDtoStream.sorted(productDtoComparatorPrice.reversed()).toList();
            case "asc.price" -> productDtoStream.sorted(productDtoComparatorPrice).toList();
            case "desc.name" -> productDtoStream.sorted(productDtoComparatorName.reversed()).toList();
            default -> productDtoStream.sorted(productDtoComparatorName).toList();
        };
    }

    private void fetchSuppliers(boolean fetchSuppliers, List<ProductDto> productDtoList) {
        if (!fetchSuppliers) {
            productDtoList.forEach(productDto -> productDto.setSupplierDto(null));
        }
    }

    private String generateProductId() {
        String idNumber = RandomStringUtils.randomNumeric(Integer.parseInt(RANGE_OF_RANDOM_NUMERIC));
        return "PRD" + idNumber;
    }

    private String generateSupplierId() {
        String idNumber = RandomStringUtils.randomNumeric(Integer.parseInt(RANGE_OF_RANDOM_NUMERIC));
        return "SUP" + idNumber;
    }

    private SupplierEntity checkSupplier(SupplierDto supplierDto) {
        SupplierEntity supplierEntity;
        if (supplierDto.getSupplierId() == null) { // searching by a name
            supplierEntity = supplierRepository.findByName(supplierDto.getName());
            if (supplierEntity == null) { // searching by a supplier that does not exist in database
                return SupplierEntity.builder()
                        .supplierId(generateSupplierId())
                        .name(supplierDto.getName())
                        .build();
            }
        } else { // searching by a SupplierId
            return supplierRepository.findBySupplierId(supplierDto.getSupplierId());
        }
        return supplierEntity;
    }

    private ProductEntity productSettings(
            ProductEntity productEntity, ProductDto productDto, String productId) {
        SupplierEntity supplierEntity = productEntity.getSupplierEntity();
        SupplierDto supplierDto = supplierMapper.supplierEntityToSupplierDto(supplierEntity);

        Integer supplierId = supplierEntity.getId();
        Integer id = productEntity.getId();

        productDto.setSupplierDto(supplierDto);

        ProductEntity productEntityMapped = productMapper.productDtoToProductEntity(productDto);
        productEntityMapped.getSupplierEntity().setId(supplierId);
        productEntityMapped.setId(id);
        productEntityMapped.setProductId(productId);

        return productEntityMapped;
    }
}
