package ngutestbackend.demo.repository;

import ngutestbackend.demo.model.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InputRepository extends JpaRepository<Input, Integer>{

}
