package com.hungnv.TheCoffeeHouse.service;
import com.hungnv.TheCoffeeHouse.dto.ImageDTO;
import com.hungnv.TheCoffeeHouse.dto.StoreDTO;
import com.hungnv.TheCoffeeHouse.exception.ErrorImageException;
import com.hungnv.TheCoffeeHouse.exception.MissingParameterException;
import com.hungnv.TheCoffeeHouse.exception.ProductNotFoundException;
import com.hungnv.TheCoffeeHouse.exception.StoreNotFoundException;
import com.hungnv.TheCoffeeHouse.model.ImageStore;
import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.model.Stores;
import com.hungnv.TheCoffeeHouse.repository.ImageStoreRepository;
import com.hungnv.TheCoffeeHouse.repository.StoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoresRepository storesRepository;
    private final ImageStoreRepository imageStoreRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public StoreDTO.BaseStoreResponse create(StoreDTO.StoreRequest body) throws IOException {
        Stores store = Stores.builder()
                .nameStore(body.getNameStore())
                .address(body.getAddress())
                .description(body.getDescription())
                .cityId(body.getCityId())
                .shortDescription(body.getShortDescription())
                .mapLink(body.getMapLink())
                .mapHTML(body.getMapHTML())
                .build();
        Stores savedStore = storesRepository.save(store);

        List<ImageStore> imageStores = new ArrayList<>();
        for (String imgBase64 : body.getImage()) {
            ImageDTO.ImageResponse res = cloudinaryService.uploadImage(imgBase64, "uploads_store");
            ImageStore imageStore = ImageStore.builder()
                    .image(res.getSecureUrl())
                    .cloudId(res.getPublicId())
                    .storeId(savedStore.getId())
                    .build();
            imageStores.add(imageStore);
        }
        imageStoreRepository.saveAll(imageStores);
        return new StoreDTO.BaseStoreResponse(
                savedStore.getId(),
                savedStore.getNameStore(),
                savedStore.getAddress(),
                savedStore.getDescription(),
                savedStore.getCityId(),
                savedStore.getShortDescription(),
                savedStore.getMapLink(),
                savedStore.getMapHTML(),
                imageStores
        );
    }

    @Transactional
    public void delete(Long storeId) throws IOException {
        Stores store = storesRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException("Store with id " + storeId + " not found"));
        List<ImageStore> imageStores = imageStoreRepository.findAllByStoreId(store.getId());
        for (ImageStore imageStore : imageStores) {
            cloudinaryService.deleteImage(imageStore.getCloudId());
        }
        imageStoreRepository.deleteAll(imageStores);
        storesRepository.delete(store);
    }

    @Transactional
    public StoreDTO.BaseStoreResponse update(StoreDTO.StoreRequestWithId data) throws IOException {
        if (data == null) {
            throw new MissingParameterException("Missing parameter");
        }
        Stores store = storesRepository.findById(data.getId()).orElseThrow(() -> new StoreNotFoundException("Store with id " + data.getId() + " not found"));
        //Xóa ảnh cũ
        List<ImageStore> imageStores = imageStoreRepository.findAllByStoreId(store.getId());
        for (ImageStore imageStore : imageStores) {
            cloudinaryService.deleteImage(imageStore.getCloudId());
        }
        imageStoreRepository.deleteAll(imageStores);

        //Cập nhật mới
        store.setNameStore(data.getNameStore());
        store.setAddress(data.getAddress());
        store.setCityId(data.getCityId());
        store.setDescription(data.getDescription());
        store.setShortDescription(data.getShortDescription());
        store.setMapHTML(data.getMapHTML());
        store.setMapLink(data.getMapLink());
        storesRepository.save(store);

        //Upload ảnh mới
        List<ImageStore> newImageStores = new ArrayList<>();
        for (String imageData : data.getImage()) {
            ImageDTO.ImageResponse res = cloudinaryService.uploadImage(imageData, "uploads_store");

            ImageStore imageStore = ImageStore.builder()
                    .image(res.getSecureUrl())
                    .cloudId(res.getPublicId())
                    .storeId(store.getId())
                    .build();
            newImageStores.add(imageStore);
        }
        imageStoreRepository.saveAll(newImageStores);
        return new StoreDTO.BaseStoreResponse(
                store.getId(),
                store.getNameStore(),
                store.getAddress(),
                store.getDescription(),
                store.getCityId(),
                store.getShortDescription(),
                store.getMapLink(),
                store.getMapHTML(),
                newImageStores
        );
    }


    public Optional<StoreDTO.StoreResponseDTO> getAllStoreByCity(String city, int page, int itemsPerPage, int limit) {
        List<Stores> stores;
        List<StoreDTO.StoreResponse> storeDTOList = new ArrayList<>();

        // Có pagination
        if (limit == 0 && page > 0 && itemsPerPage > 0) {
            Pageable pageable = PageRequest.of(page - 1, itemsPerPage);
            Page<Stores> storePage;

            if (city.equals("ALL")) {
                storePage = storesRepository.findAll(pageable);
            } else {
                storePage = storesRepository.findByCityId(city, pageable);
            }

            // Tạo StoreDTO và gắn thêm ảnh
            storeDTOList = storePage.getContent().stream().map(store -> {
                List<ImageStore> images = imageStoreRepository.findAllByStoreId(store.getId());
                StoreDTO.StoreResponse res = StoreDTO.StoreResponse.builder()
                        .id(store.getId())
                        .nameStore(store.getNameStore())
                        .address(store.getAddress())
                        .description(store.getDescription())
                        .cityId(store.getCityId())
                        .shortDescription(store.getShortDescription())
                        .mapHTML(store.getMapHTML())
                        .mapLink(store.getMapLink())
                        .cityName(store.getCityData().getValueVn())
                        .image(images)
                        .build();
                return res;
            }).collect(Collectors.toList());

            return Optional.of(new StoreDTO.StoreResponseDTO(storeDTOList, storePage.getTotalPages()));
        }

        // Không có pagination, chỉ có limit
        if (limit > 0) {
            if (city.equals("ALL")) {
                stores = storesRepository.findTopN(limit);
            } else {
                stores = storesRepository.findTopNByCityId(city, limit);
            }

            // Tạo StoreDTO và gắn thêm ảnh
            storeDTOList = stores.stream().map(store -> {
                List<ImageStore> images = imageStoreRepository.findAllByStoreId(store.getId());
                StoreDTO.StoreResponse res = StoreDTO.StoreResponse.builder()
                        .id(store.getId())
                        .nameStore(store.getNameStore())
                        .address(store.getAddress())
                        .description(store.getDescription())
                        .cityId(store.getCityId())
                        .shortDescription(store.getShortDescription())
                        .mapHTML(store.getMapHTML())
                        .mapLink(store.getMapLink())
                        .image(images)
                        .build();
                return res;
            }).collect(Collectors.toList());

            return Optional.of(new StoreDTO.StoreResponseDTO(storeDTOList, 1)); // Mặc định là 1 trang
        }

        return Optional.empty();
    }

    public StoreDTO.StoreResponse get(Long storeId){
        Stores store = storesRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException("Store with id " + storeId + " not found"));
        List<ImageStore> imageStoresList = imageStoreRepository.findAllByStoreId(store.getId());
        if(imageStoresList.isEmpty()){
            throw new ErrorImageException("Error get images");
        }
        return new StoreDTO.StoreResponse(
                store.getId(),
                store.getNameStore(),
                store.getAddress(),
                store.getDescription(),
                store.getCityId(),
                store.getShortDescription(),
                store.getMapLink(),
                store.getMapHTML(),
                store.getCityData().getValueVn(),
                imageStoresList
        );
    }


}


