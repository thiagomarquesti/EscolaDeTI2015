//Criar relatório de Listagem 
    Indígenas, 
    visitantes, 
    Famílias, 
com Filtros idade(crianca,adolescente), data de chegada e data de saída.

//buscar pessoas fisicas e juridicas
SELECT *
  FROM pessoa
  left join pessoa_fisica
    on pessoa_fisica.idpessoa = pessoa.idpessoa
  left JOIN  pessoa_juridica
    ON pessoa_juridica.idpessoa = pessoa.idpessoa 

//terras indigenas
SELECT 
  u.descricao,
  u.sigla,
  c.descricao,
  t.nometerra,
  t.cidade_codigoibge
FROM terraindigena t
  inner join cidade c
     on c.codigoibge = t.cidade_codigoibge
  inner join uf u
     on u.codigoestado = c.estado_codigoestado
  where u.sigla = :pUF

//retorna Indigenas
SELECT 
  I.codigoassindi,
  I.codigosus,
  I.datanascimento,
  I.cpf,
  I.escolaridade,
  I.nome,
  I.genero,
  e.descricao as Etnia,
  t.telefone as Telefone,
  ti.nometerra
FROM indigena I
 INNER JOIN etnia e
    ON e.idetnia = I.etnia_idetnia
 INNER JOIN telefone t
    ON t.idtelefone = I.telefone_idtelefone
 INNER JOIN terraindigena ti
    ON ti.idterraindigena = I.terraindigena_idterraindigena
  
//retorna Familia
SELECT f.nomefamilia,
       f.telefone,
       ir.nome as representanteFamilia
 FROM familia f
 inner join indigena ir
    on ir.codigoassindi = f.idrepresentante
