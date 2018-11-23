select sc.abbreviation
       , sc.name
       , sc.id_s_competence
from speciality_competences_ sc
     , edu_specialities_ sd
     , complex_specialities_ cs
where sc.id_speciality = sd.id_speciality
      and cs.id_d_specialitie = sd.id_d_specialitie
      and cs.id_complex_specialities = ?
      and (1 is null
          or (sc.id_s_competence not in
(
select mcc.id_s_competence
from mc_competences_ mcc
where mcc.id_complex_specialities = ?
)
             and sc.id_s_competence in
(
select epc.id_s_competence
from ep_competences_ epc
where epc.id_discipline_plan = ?
)
))
