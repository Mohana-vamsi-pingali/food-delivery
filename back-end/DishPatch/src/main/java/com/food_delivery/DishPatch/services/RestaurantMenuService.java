package com.food_delivery.DishPatch.services;

import com.amazonaws.services.s3.AmazonS3;
import com.food_delivery.DishPatch.DTOs.RestaurantMenuDTO;
import com.food_delivery.DishPatch.models.Category;
import com.food_delivery.DishPatch.models.MenuItem;
import com.food_delivery.DishPatch.models.Restaurant;
import com.food_delivery.DishPatch.models.RestaurantMenu;
import com.food_delivery.DishPatch.repositories.RestaurantMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.food_delivery.DishPatch.models.RestaurantMenu.Availability;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystemException;

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

    public String addMenuItem(RestaurantMenuDTO dto){
        RestaurantMenu newItem = new RestaurantMenu();
        try {
            Restaurant restaurant = restaurantService.getRestaurant(dto.getRestaurantId());

            MenuItem menuItem = menuItemService.getMenuItem(dto.getItemName())
                    .orElseGet(() -> menuItemService.addMenuItemInternal(dto.getItemName()));

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
//            log.error("Error converting multipartFile to file", e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return convertedFile;
    }
}
