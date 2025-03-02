package co.edu.uniquindio.ingesis.restful.repositories;

import co.edu.uniquindio.ingesis.restful.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface IUserRepository extends PanacheRepository<User> {

}