package com.food_delivery.DishPatch.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.food_delivery.DishPatch.DTOs.RestaurantMenuDTO;
import com.food_delivery.DishPatch.Exceptions.MenuItemAlreadyPresentException;
import com.food_delivery.DishPatch.models.Category;
import com.food_delivery.DishPatch.models.MenuItem;
import com.food_delivery.DishPatch.models.Restaurant;
import com.food_delivery.DishPatch.models.RestaurantMenu;
import com.food_delivery.DishPatch.repositories.RestaurantMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.food_delivery.DishPatch.models.RestaurantMenu.Availability;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.time.Instant;
import java.util.Date;

@Service
public class RestaurantMenuService {
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final CategoryService categoryService;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public RestaurantMenuService(RestaurantMenuRepository restaurantMenuRepository,
                                 RestaurantService restaurantService,
                                 MenuItemService menuItemService,
                                 CategoryService categoryService) {
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
        this.categoryService = categoryService;
    }

    public void uploadFile(MultipartFile file, String fileName){
        File fileObj = convertMultiPartFileToFile(file);
        s3Client.putObject(bucketName, fileName, fileObj);
        fileObj.delete();
    }

    public String generatePresignedUrl(String fileName) {
        Date expiration = Date.from(Instant.now().plusSeconds(3600)); // 1 hour
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(request);
        return url.toString();
    }


    public byte [] getFile(String fileName){
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String addMenuItem(RestaurantMenuDTO dto){
        MenuItem menuItem = menuItemService.getMenuItem(dto.getItemName())
                .orElseGet(() -> menuItemService.addMenuItemInternal(dto.getItemName()));
        if(restaurantMenuRepository.findByMenuitemIdAndRestaurantId(menuItem.getId(), dto.getRestaurantId()).isPresent()){
            throw new MenuItemAlreadyPresentException("Menu-Item Already Present. Cannot create new one.");
        }
        RestaurantMenu newItem = new RestaurantMenu();
        try {
            Restaurant restaurant = restaurantService.getRestaurant(dto.getRestaurantId());

            Category category = categoryService.getCategory(dto.getCategoryName())
                    .orElseGet(() -> categoryService.addCategoryInternal(dto.getCategoryName()));

            String fileName = restaurant.getId()+"_"+menuItem.getName();

            newItem.setRestaurant(restaurant);
            newItem.setMenuitem(menuItem);
            newItem.setCategory(category);
            newItem.setPrice(dto.getPrice());
            if(dto.getAvailability()!=null)
                newItem.setAvailability(Availability.valueOf(dto.getAvailability()));
            try {
                uploadFile(dto.getImageFile(), fileName);
                newItem.setImageUrl(fileName);
            }
            catch (Exception e){
                throw new FileSystemException("Cannot Upload File to the DB");
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("Cannot Create Restaurant Menu Item");
        }
        restaurantMenuRepository.save(newItem);
        return "Successfully Created";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return convertedFile;
    }

    @Transactional(readOnly = true)
    public RestaurantMenuDTO getMenuItem(Long restaurantId, Long itemId){
        RestaurantMenu item = restaurantMenuRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        if (!item.getRestaurant().getId().equals(restaurantId)) {
            throw new IllegalArgumentException("Item does not belong to this restaurant");
        }

        String presignedUrl = generatePresignedUrl(item.getImageUrl());

        return RestaurantMenuDTO.from(item, presignedUrl);
    }
}
