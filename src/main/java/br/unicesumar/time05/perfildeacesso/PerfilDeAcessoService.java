package br.unicesumar.time05.perfildeacesso;

import java.util.Collections;
import java.util.List;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PerfilDeAcessoService {
    
    @Autowired
    private PerfilDeAcessoRepository repo;
    
    public List<PerfilDeAcesso> getPerfisDeAcesso(){
        return Collections.unmodifiableList(repo.findAll());
    }
    
    public PerfilDeAcesso getPerfilDeAcesso(Long aId){
        return repo.findOne(aId);
    }
    
    public void salvarPerfilDeAcesso(PerfilDeAcesso aPerfilDeAcesso){
        repo.save(aPerfilDeAcesso);
    }
    
    public void removerPerfilDeAcesso(Long aId){
        repo.delete(aId);
    }
    
    public void alterarPerfilDeAcesso(PerfilDeAcesso aPerfilDeAcesso){
        repo.save(aPerfilDeAcesso);
    }
}
