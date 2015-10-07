//buscar pessoas fisicas e juridicas
SELECT *
  FROM pessoa
  left join pessoa_fisica
    on pessoa_fisica.idpessoa = pessoa.idpessoa
  left JOIN  pessoa_juridica
    ON pessoa_juridica.idpessoa = pessoa.idpessoa 

