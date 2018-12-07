select nvl(cc.abbreviation, dc.abbreviation) abbreviation
       , nvl(cc.name, dc.name) name
       , cc.knowledge
       , cc.ability
       , cc.skill
       , nvl(cc.id_s_competence, dc.id_s_competence) id_s_competence
       , cc.id_mc_competences
       ,
case
 when cc.id_s_competence = dc.id_s_competence
  then 'Верно'
 when cc.id_s_competence is null
  then 'Добавить'
 when dc.id_s_competence is null
  then 'Удалить'
end action
from
(
select sc.abbreviation
       , sc.name
       , mcc.knowledge
       , mcc.ability
       , mcc.skill
       , mcc.id_complex_specialities
       , mcc.id_s_competence
       , mcc.id_mc_competences
from speciality_competences_ sc
     , mc_competences_ mcc
where mcc.id_s_competence = sc.id_s_competence
      and mcc.id_complex_specialities = ?
) cc
full join
(
select distinct -- 25.11.2015
       sc.abbreviation
       , sc.name
       , sc.id_s_competence
from ep_competences_ epc
     , speciality_competences_ sc
     , structure_plan_ sp
where epc.id_discipline_plan = sp.id_discipline_plan
      and (epc.id_discipline_plan = ?
          or sp.id_dp_main = ?)
      and epc.id_s_competence = sc.id_s_competence
) dc
on dc.id_s_competence = cc.id_s_competence