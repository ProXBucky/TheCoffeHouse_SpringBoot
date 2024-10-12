package com.hungnv.TheCoffeeHouse.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hungnv.TheCoffeeHouse.dto.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;


    public ImageDTO.ImageResponse uploadImage(String base64Image, String folder) throws IOException {
        String imageData = base64Image.split(",")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(imageData);

        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "folder", folder
        );
        Map uploadResult = cloudinary.uploader().upload(decodedBytes, uploadParams);
        // Trả về đối tượng ImageResponse với secure_url và public_id
        String secureUrl = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");
        return new ImageDTO.ImageResponse(secureUrl, publicId);
    }

    public String deleteImage(String publicId) throws IOException {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return (String) result.get("result");
        } catch (IOException e) {
            System.err.println("Có lỗi xảy ra khi xóa ảnh: " + e.getMessage());
            throw e;
        }
    }

}