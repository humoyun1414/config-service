package uz.humoyun.configservice.api;

import org.springframework.web.bind.annotation.*;
import uz.humoyun.configservice.model.PropertyDto;
import uz.humoyun.configservice.service.PropertyService;

@RestController
@RequestMapping("/api/config/v1")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/{application}")
    public PropertyDto findByApplication(@PathVariable String application) {
        return propertyService.findByAppLication(application);
    }

    @PutMapping("/{application}")
    public void update(@PathVariable String application,
                       @RequestBody PropertyDto dto) {
        propertyService.update(application, dto);
    }


}
