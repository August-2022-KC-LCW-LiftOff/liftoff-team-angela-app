package com.ark.demo.controllers;

import com.ark.demo.models.data.RequestRepository;
import com.ark.demo.models.Location;
import com.ark.demo.models.Request;
import com.ark.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("map")
public class MapController {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    AuthenticationController authenticationController;


    @GetMapping()
    public String displayMap(HttpServletRequest request, Model model, @PathParam("sortBy") String sortBy){
        User user = authenticationController.getUserFromSession(request.getSession());
        List<Request> requests = requestRepository.findAllActivePublicEvents();
        if(!isNull(sortBy)){
            switch (sortBy){
                case "title":
                    requests.sort(Comparator.comparing(Request::getTitle));
                    break;
                case "zipcode":
                    requests.sort(Comparator.comparing(Request::getZipcode));
                    break;
                default:
                    requests.sort(Comparator.comparing(Request::getDueDate));
            }
        }
        model.addAttribute("user",user);
        model.addAttribute("locations",getLocationList(requests));
        model.addAttribute("userLocation",getUserLocation(user.getLocation()));
        model.addAttribute("requests",requests);
        return "mapTemplates/showMap";
    }
    private List<Location> getLocationList(List<Request> requestList){
        List<Location> returnList = new ArrayList<>();
        for(Request userRequest:requestList){
            String[] locationArray = userRequest.getLocation().split(",");
            Location location = new Location(Double.parseDouble(locationArray[0]),Double.parseDouble(locationArray[1]));
            returnList.add(adjustLocationForSafety(location));
        }
        return returnList;
    }
    private Location getUserLocation(String locationString){
        String[] locationArray = locationString.split(",");
        Location location = new Location(Double.parseDouble(locationArray[0]),Double.parseDouble(locationArray[1]));
        return location;
    }
    private Location adjustLocationForSafety(Location location){
        Long plusMinus = Math.round(Math.random());
        if(plusMinus == 1){
            location.setLat(location.getLat() + (Math.random()*.01));
            location.setLng(location.getLng() + (Math.random()*.01));
            return location;
        }
        location.setLat(location.getLat() - (Math.random()*.01));
        location.setLng(location.getLng() - (Math.random()*.01));
        return location;
    }
}