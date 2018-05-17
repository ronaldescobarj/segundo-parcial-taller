package com.ucbcba.demo.Controllers;

import com.ucbcba.demo.entities.City;
import com.ucbcba.demo.entities.Photo;
import com.ucbcba.demo.entities.Restaurant;
import com.ucbcba.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.util.List;

@Controller
public class RestaurantController {

    private RestaurantService restaurantService;
    private CategoryService categoryService;
    private CityService cityService;
    private PhotoService photoService;
    private LevelPriceService levelPriceService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Autowired
    public void setLevelPriceService(LevelPriceService levelPriceService) {
        this.levelPriceService = levelPriceService;
    }


    @RequestMapping(value = "admin/restaurants", method = RequestMethod.GET)
    public String listAllRestaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.listAllRestaurants());
        return "restaurants";
    }

    @RequestMapping(value = "admin/restaurant/new")
    public String newRestaurant(Model model) {
        model.addAttribute("restaurantCategories", categoryService.listAllCategories());
        model.addAttribute("cities", cityService.listAllCities());
        model.addAttribute("levelPrices", levelPriceService.listAllLevelPrices());
        model.addAttribute("restaurant", new Restaurant());
        return "newRestaurant";
    }

    @RequestMapping(value = "admin/restaurant/save", method = RequestMethod.POST)
    public String saveRestaurant(Restaurant restaurant, @RequestParam("file") MultipartFile[] files) throws IOException {
        restaurantService.saveRestaurant(restaurant);
        byte[] pixel;
        for(int i=0;i<files.length;i++)
        {
            if (!files[i].isEmpty()) {
                pixel = files[i].getBytes();
                Photo photo = new Photo();
                photo.setRestaurant(restaurant);
                photo.setPhoto(pixel);
                photoService.savePhoto(photo);
            }
        }
        return "redirect:/admin/restaurants";
    }

    @RequestMapping("admin/restaurant/{id}")
    String showRestaurant(@PathVariable Integer id, Model model) throws UnsupportedEncodingException {
        model.addAttribute("restaurant", restaurantService.getRestaurant(id));
        List restaurantPhotos= new ArrayList();
        List<Photo> photos = (List<Photo>)photoService.listAllPhotosById(id);
        byte[] encodeBase64;
        String base64Encoded;
        for(int i=0;i<photos.size();i++)
        {
            encodeBase64 = Base64.encode(photos.get(i).getPhoto());
            base64Encoded = new String(encodeBase64,"UTF-8");
            restaurantPhotos.add(base64Encoded);
        }
        model.addAttribute("photos", restaurantPhotos );
        return "ShowRestaurant";
    }

    @RequestMapping(value = "admin/restaurant/edit/{id}")
    String editRestaurant(@PathVariable Integer id, Model model) throws UnsupportedEncodingException {
        model.addAttribute("restaurant", restaurantService.getRestaurant(id));
        model.addAttribute("restaurantCategories", categoryService.listAllCategories());
        model.addAttribute("cities", cityService.listAllCities());
        model.addAttribute("levelPrices", levelPriceService.listAllLevelPrices());
        List restaurantPhotos= new ArrayList();
        List<Photo> photos = (List<Photo>)photoService.listAllPhotosById(id);
        byte[] encodeBase64;
        String base64Encoded;
        for(int i=0;i<photos.size();i++)
        {
            encodeBase64 = Base64.encode(photos.get(i).getPhoto());
            base64Encoded = new String(encodeBase64,"UTF-8");
            restaurantPhotos.add(base64Encoded);
        }
        model.addAttribute("photos", restaurantPhotos );
        model.addAttribute("images",photoService.listAllPhotosById(id));
        return "restaurantForm";
    }

    @RequestMapping(value = "admin/restaurant/delete/{id}")
    String delete(@PathVariable Integer id, Model model) {
        restaurantService.deleteRestaurant(id);
        return "redirect:/admin/restaurants";
    }

    @RequestMapping("/restaurant/delete/photo/{id}")
    String deletePhoto(@PathVariable Integer id) {
        Photo photo = photoService.getPhoto(id);
        photoService.deletePhoto(id);
        return "redirect:/admin/restaurant/edit/"+ photo.getRestaurant().getId();
    }


}
