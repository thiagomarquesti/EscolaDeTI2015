package br.unicesumar.time05.itemacesso;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemAcessoRepository extends JpaRepository<ItemAcesso, Long>{
    
}
