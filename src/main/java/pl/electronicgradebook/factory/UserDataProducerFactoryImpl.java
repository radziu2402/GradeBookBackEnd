package pl.electronicgradebook.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.electronicgradebook.model.Role;
import pl.electronicgradebook.producer.UserDataProducer;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDataProducerFactoryImpl implements UserDataProducerFactory {

    private final List<UserDataProducer> userDataProducers;

    @Override
    public UserDataProducer get(Role role) {
        return userDataProducers.stream().filter(producer -> producer.supports(role)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Role not implemented"));
    }
}
