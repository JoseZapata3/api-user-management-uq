package co.edu.uniquindio.ingesis.restful.mappers;


import co.edu.uniquindio.ingesis.restful.domain.User;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.restful.dtos.UserRegistrationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password" , expression = "java( io.quarkus.elytron.security.common.BcryptUtil.bcryptHash(userDTO.password()) )")
    User parseOf(UserRegistrationRequest userDTO);

    UserRegistrationResponse toUserResponse(User user);

}