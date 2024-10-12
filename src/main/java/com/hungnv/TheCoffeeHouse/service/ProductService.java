package com.hungnv.TheCoffeeHouse.service;

import com.hungnv.TheCoffeeHouse.dto.ImageDTO;
import com.hungnv.TheCoffeeHouse.dto.ProductDTO;
import com.hungnv.TheCoffeeHouse.exception.ErrorImageException;
import com.hungnv.TheCoffeeHouse.exception.MissingParameterException;
import com.hungnv.TheCoffeeHouse.exception.ProductNotFoundException;
import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public Products create(ProductDTO.ProductCreateRequest data) throws IOException {
        if (data == null) {
            throw new MissingParameterException("Missing parameter");
        }
        ImageDTO.ImageResponse cloudinaryUpload = cloudinaryService.uploadImage(data.getImage(), "uploads_product");
        Products product = Products.builder()
                .name(data.getName())
                .originalPrice(data.getOriginalPrice())
                .categoryId(data.getCategoryId())
                .description(data.getDescription())
                .image(cloudinaryUpload.getSecureUrl())
                .cloudId(cloudinaryUpload.getPublicId())
                .quantitySold(0)
                .build();
        productRepository.save(product);
        return product;
    }

    @Transactional
    public void delete(Long productId) throws IOException {
        Products product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        String res = cloudinaryService.deleteImage(product.getCloudId());
        if (Objects.equals(res, "ok")) {
            productRepository.delete(product);
        } else {
            throw new ErrorImageException("Error upload images");
        }
    }

    @Transactional
    public Products update(ProductDTO.ProductEditRequest data) throws IOException {
        Products product = productRepository.findById(data.getId()).orElseThrow(() -> new ProductNotFoundException("Product with id " + data.getId() + " not found"));
        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setCategoryId(data.getCategoryId());
        product.setOriginalPrice(data.getOriginalPrice());
        String res = cloudinaryService.deleteImage(product.getCloudId());
        System.out.println("check cloud" + res);
        if (Objects.equals(res, "ok")) {
            ImageDTO.ImageResponse imageResponse = cloudinaryService.uploadImage(data.getImage(), "uploads_product");
            product.setImage(imageResponse.getSecureUrl());
            product.setCloudId(imageResponse.getSecureUrl());
            return productRepository.save(product);
        } else {
            throw new ErrorImageException("Error handling images");
        }
    }

    public Optional<ProductDTO.ProductResponseDTO> getAllByCategory(String category, int page, int itemsPerPage, int limit) {
        List<Products> products;

        // Có pagination
        if (limit == 0 && page > 0 && itemsPerPage > 0) {
            Pageable pageable = (Pageable) PageRequest.of(page - 1, itemsPerPage);

            if(category.equals("ALL")) {
                Page<Products> productPage = productRepository.findAll((org.springframework.data.domain.Pageable) pageable);
                return Optional.of(new ProductDTO.ProductResponseDTO(productPage.getContent(), productPage.getTotalPages()));
            } else {
                Page<Products> productPage = productRepository.findByCategoryId(category, pageable);
                return Optional.of(new ProductDTO.ProductResponseDTO(productPage.getContent(), productPage.getTotalPages()));
            }
        }

        // Chỉ có limit
        if (limit > 0) {
            if (category.equals("ALL")) {
                products = productRepository.findTopN(limit);
            } else {
                products = productRepository.findTopNByCategoryId(category, limit);
            }
            return Optional.of(new ProductDTO.ProductResponseDTO(products, 1));
        }

        return Optional.empty();
    }

    public ProductDTO.ProductResponse get(Long productId){
        Products product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        return new ProductDTO.ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategoryId(),
                product.getImage(),
                product.getOriginalPrice(),
                product.getQuantitySold(),
                product.getCloudId(),
                product.getCategoryData()
        );
    }
}
