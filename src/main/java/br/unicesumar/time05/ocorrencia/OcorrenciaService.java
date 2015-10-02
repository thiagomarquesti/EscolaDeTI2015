package br.unicesumar.time05.ocorrencia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.indigena.Indigena;
import br.unicesumar.time05.indigena.IndigenaRepository;
import br.unicesumar.time05.indigena.IndigenaService;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class OcorrenciaService extends ServiceBase<Ocorrencia, Long, OcorrenciaRepository> {
    @Autowired
    private IndigenaRepository indigenaRepository;
    
    public OcorrenciaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Ocorrencia.class));
    }

    @Override
    public void salvar(Ocorrencia aEntidade) {
        repository.save(aEntidade);
        Indigena i = (Indigena) indigenaRepository.findOne(aEntidade.getIdIndigena());
        i.setOcorrencia(aEntidade);
        indigenaRepository.save(i);
    }
    
    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute("SELECT idocorrencia,databloqueio,dataocorrencia,descricao "
                + "FROM ocorrencia");
    }
   
}
