package uz.humoyun.configservice.service;

import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.event.PathDestinationFactory;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import uz.humoyun.configservice.model.PropertyDto;
import uz.humoyun.configservice.model.PropertyItem;
import uz.humoyun.configservice.repository.PropertyRepository;

import java.util.List;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final ApplicationEventPublisher publisher;
    private final BusProperties propoerties;

    public PropertyService(PropertyRepository propertyRepository, ApplicationEventPublisher publisher, BusProperties propoerties) {
        this.propertyRepository = propertyRepository;
        this.publisher = publisher;
        this.propoerties = propoerties;
    }

    public PropertyDto findByAppLication(String application) {
        List<PropertyItem> items = propertyRepository.findByApplication(application);
        return new PropertyDto(items);
    }

    public void update(String application, PropertyDto dto) {
        propertyRepository.update(application, dto);
        publisher.publishEvent(
                new RefreshRemoteApplicationEvent(
                        this, propoerties.getId(), new PathDestinationFactory().getDestination(application)
                )
        );
    }
}
