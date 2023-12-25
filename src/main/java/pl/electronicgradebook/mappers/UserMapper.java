package pl.electronicgradebook.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.electronicgradebook.dto.SignUpDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User signUpToUser(SignUpDto userDto);

    // Dodaj mapowanie ról ręcznie
    default String mapRoles(User user) {
        return user.getRole().name();
    }

    @Mapping(target = "role", expression = "java(mapRoles(user))") // Użyj ręcznie dodanej logiki
    UserDto toUserDtoWithRoles(User user);
}