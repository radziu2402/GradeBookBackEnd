package pl.electronicgradebook.factory;

import pl.electronicgradebook.model.Role;
import pl.electronicgradebook.producer.UserDataProducer;

public interface UserDataProducerFactory {

    UserDataProducer get(Role role);
}
