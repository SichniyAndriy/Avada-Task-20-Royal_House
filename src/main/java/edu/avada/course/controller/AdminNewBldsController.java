package edu.avada.course.controller;

import edu.avada.course.model.admindto.AdminAddressDto;
import edu.avada.course.model.admindto.AdminNewBuildingDto;
import edu.avada.course.model.entity.NewBuilding.Location;
import edu.avada.course.model.entity.NewBuilding.NewBuildStatus;
import edu.avada.course.service.AdminNewBldsService;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/new-blds")
public class AdminNewBldsController {
    private final AdminNewBldsService adminNewBldsService;

    public AdminNewBldsController(
            @Autowired AdminNewBldsService adminNewBldsService
    ) {
        this.adminNewBldsService = adminNewBldsService;
    }

    @GetMapping
    public String showNewBlds(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ) {
        Page<AdminNewBuildingDto> pageNewBlds = adminNewBldsService.getPageNewBlds(page, size);
        model.addAttribute("newbldPage", pageNewBlds);
        return "admin/new_blds";
    }

    @GetMapping("/show-newbld-card/{id}")
    public String showNewBld(@PathVariable long id, Model model) {
        AdminNewBuildingDto adminNewBuildingDtoById = adminNewBldsService.getNewBldById(id);
        model.addAttribute("newbld", adminNewBuildingDtoById);
        return "admin/new_bld_card";
    }

    @GetMapping("/change-new-bld-status/{id}")
    public ResponseEntity<HttpStatus> changeNewBldStatus(@PathVariable long id) {
        adminNewBldsService.changeNewBldStatusById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNewBld(@PathVariable int id){
        adminNewBldsService.deleteNewBldById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/update-new-bld")
    public ResponseEntity<HttpStatus> addInfographic(
            @RequestBody AdminNewBuildingDto adminNewBuildingDto
    ) {
        adminNewBldsService.updateNewBld(adminNewBuildingDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/banners/add-new")
    @ResponseBody
    public ResponseEntity<String> setBanner(
            @RequestParam("new-banner") MultipartFile multipartFile,
            @RequestParam("path") String path,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("ext") String ext
    ) throws IOException {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        String result = Controllers.saveFile(
                path, multipartFile.getOriginalFilename(), timestamp, ext,
                multipartFile.getBytes()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/infographics/add-new")
    @ResponseBody
    public ResponseEntity<String> setInfographic(
            @RequestParam("new-infographic") MultipartFile multipartFile,
            @RequestParam("path") String path,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("ext") String ext
    ) throws IOException {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        String result = Controllers.saveFile(
                path, multipartFile.getOriginalFilename(), timestamp, ext,
                multipartFile.getBytes()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/panoramas/add-new")
    @ResponseBody
    public ResponseEntity<String> setPanorama(
            @RequestParam("new-panorama") MultipartFile multipartFile,
            @RequestParam("path") String path,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("ext") String ext
    ) throws IOException {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        String result = Controllers.saveFile(
                path, multipartFile.getOriginalFilename(), timestamp, ext,
                multipartFile.getBytes()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add-new")
    public ResponseEntity<HttpStatus> addNewBld(
            @RequestParam("title") String title,
            @RequestParam("address") String address
    ) {
        AdminNewBuildingDto adminNewBuildingDto = new AdminNewBuildingDto();
        adminNewBuildingDto.setTitle(title);
        String[] strings = address.split("[.,:;]");
        AdminAddressDto adminAddressDto = new AdminAddressDto();
        if (strings.length != 3) {
            throw new IllegalArgumentException();
        }
        adminAddressDto.setCity(strings[0].trim());
        adminAddressDto.setStreet(strings[1].trim());
        adminAddressDto.setHouseNumber(strings[2].trim());
        adminNewBuildingDto.setAddress(adminAddressDto);
        adminNewBuildingDto.setStatus(NewBuildStatus.OFF);
        adminNewBuildingDto.setLocation(new Location());
        adminNewBuildingDto.setBanners(new ArrayList<>());
        adminNewBuildingDto.setInfographics(new ArrayList<>());
        adminNewBuildingDto.setUnits(new ArrayList<>());
        adminNewBldsService.add(adminNewBuildingDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
