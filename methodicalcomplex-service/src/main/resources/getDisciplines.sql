select name
       , sem
       , decode(?, 1, max(knowledge), '') knowledge
       , decode(?, 1, max(ability), '') ability
       , decode(?, 1, max(skill), '') skill
       , id_discipline
from
(
select name
       , sem
       , knowledge
       , ability
       , skill
       , id_discipline
from
(
select d.name
       , 
(
select decode(?, 1, min(elu2.semester), max(elu2.semester))
from educational_load_umk_ elu2
where elu2.id_methodical_complex = cs.id_methodical_complex
) sem
       , t.sem sem_2
       , mcc.id_s_competence /*добавлено 28.10.2015 на примере 6327, иначе не влезало в текст!*/
       , to_char(substr(sys_xmlagg(xmlelement(id,mcc.knowledge)).extract('/ROWSET/ID/text()').getclobval(), 1, 2000)) knowledge
       , to_char(substr(sys_xmlagg(xmlelement(id,mcc.ability)).extract('/ROWSET/ID/text()').getclobval(), 1, 2000)) ability
       , to_char(substr(sys_xmlagg(xmlelement(id,mcc.skill)).extract('/ROWSET/ID/text()').getclobval(), 1, 2000)) skill
       , d.id_discipline
from complex_specialities_ cs
     , methodical_complex_ mc
     , mc_competences_ mcc
     , disciplines_ d
     ,
(
select nvl(?, decode(?, 1, min(elu.semester), max(elu.semester))) sem
       , cs.id_d_specialitie
       , cs.id_complex_specialities
from complex_specialities_ cs
     , educational_load_umk_ elu
     , methodical_complex_ mc
where cs.id_complex_specialities = ?
      and cs.id_methodical_complex = elu.id_methodical_complex
      and cs.id_methodical_complex = mc.id_methodical_complex
group by nvl(cs.id_discipline, mc.id_discipline)
         , cs.id_d_specialitie
         , cs.id_complex_specialities
) t
where mc.id_methodical_complex = cs.id_methodical_complex
      and mcc.id_complex_specialities(+) = cs.id_complex_specialities
      and nvl(cs.id_discipline, mc.id_discipline) = d.id_discipline
      and t.id_complex_specialities != cs.id_complex_specialities
      and cs.id_d_specialitie = t.id_d_specialitie
      and mc.idk_umk = 1000
 /*Берем только дисциплины из прикрепленных планов*/
      and (cs.id_complex_specialities in
(
select e2.id_complex_specialities
from ep_mc_ e
     , ep_mc_ e2
where e.id_complex_specialities = ?
      and e2.id_educational_plan = e.id_educational_plan
)
          or not exists
(
select 'x'
from ep_mc_ e
where e.id_complex_specialities = ?
)
)
group by d.name
         , t.sem
         , d.id_discipline
         , cs.id_methodical_complex
         , mcc.id_s_competence /*добавлено 28.10.2015 на примере 6327*/
)
where (sem < sem_2 and ? = 1)
      or (sem > sem_2 and ? = 2)

union 
select name
       , sem
       , null
       , null
       , null
       , id_discipline
from
(
select d.name
       , 
(
select decode(?, 1, min(vd2.semester), max(vd2.semester))
from volume_discipline_ vd2
where vd2.id_discipline_plan = sp.id_discipline_plan
) sem
       , t.sem sem_2
       , d.id_discipline
from educational_plan_ ep
     , structure_plan_ sp
     , paragraphs_status_ ps
     , disciplines_ d
     ,
(
select nvl(?, decode(?, 1, min(elu.semester), max(elu.semester))) sem
       , cs.id_d_specialitie
from complex_specialities_ cs
     , educational_load_umk_ elu
     , methodical_complex_ mc
where cs.id_complex_specialities = ?
      and cs.id_methodical_complex = elu.id_methodical_complex
      and cs.id_methodical_complex = mc.id_methodical_complex
group by nvl(cs.id_discipline, mc.id_discipline)
         , cs.id_d_specialitie
         , cs.id_complex_specialities
) t
where ep.id_d_specialitie = t.id_d_specialitie
      and ep.id_educational_plan = sp.id_educational_plan
      and ep.id_paragraph = ps.id_paragraph
      and ps.idk_order = 30
      and sp.id_discipline = d.id_discipline
      and nvl(ep.d_end, sysdate) >= sysdate
/*      and nvl(sp.idk_discipline, -1) not in (53311)
----------------------------------------------------------------- */
      and (ep.id_educational_plan in
(
select e.id_educational_plan
from ep_mc_ e
where e.id_complex_specialities = ?
)
          or not exists
(
select 'x'
from ep_mc_ e
where e.id_complex_specialities = ?
)
)
/*
      and sp.id_discipline not in
(
select cs.id_discipline
from ep_mc_ e
     , ep_mc_ e2
     , complex_specialities_ cs
where e.id_complex_specialities = 296524
      and e2.id_educational_plan = e.id_educational_plan
      and cs.id_complex_specialities = e2.id_complex_specialities
)
*/
)
where (sem < sem_2 and ? = 1)
      or (sem > sem_2 and ? = 2)
      
union all

select d.name
       , 0
       , ''
       , ''
       , ''
       , d.id_discipline
from disciplines_ d
where ? = 0
      and d.name is not null
)
group by name
         , sem
		 , id_discipline