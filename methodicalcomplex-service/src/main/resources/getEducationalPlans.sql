with epF as
(
select ep.id_educational_plan id_ep
       , epm.id_paragraph id_par_main
       , ep.d_start
from educational_plan_ ep
     , paragraphs_status_ p
     , dependences_lists_ dl
     , paragraphs_status_ pm
     , educational_plan_ epm
where ep.id_paragraph = p.id_paragraph
      and ep.consider_in_loading = 1
      and p.id_order = dl.id_o_dependent(+)
      and dl.id_o_main = pm.id_order(+)
      and pm.id_paragraph = epm.id_paragraph(+)
)

, comp as
(
select id_discipline_plan
       , listagg(abbreviation, ' ') within group (order by abbreviation)  as comps
from
(
select sp2.id_discipline_plan
       , sc2.abbreviation
from ep_competences_ epc2
     , speciality_competences_ sc2
     , structure_plan_ sp2
where epc2.id_discipline_plan = sp2.id_discipline_plan
      and epc2.id_s_competence = sc2.id_s_competence
group by sp2.id_discipline_plan
       , sc2.abbreviation
union
select sp2.id_dp_main
       , sc2.abbreviation
from ep_competences_ epc2
     , speciality_competences_ sc2
     , structure_plan_ sp2
where epc2.id_discipline_plan = sp2.id_discipline_plan
      and epc2.id_s_competence = sc2.id_s_competence
      and sp2.id_dp_main is not null
group by sp2.id_dp_main
       , sc2.abbreviation
)
group by id_discipline_plan
)


select distinct
(
select sum(vd2.hours)
from volume_discipline_ vd2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
) hours
       ,
(
select nvl(sum(vd2.hours),0)
from volume_discipline_ vd2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
      and vd2.idk_lesson = 1
) lec_hours
       ,
(
select nvl(sum(vd2.hours),0)
from volume_discipline_ vd2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
      and vd2.idk_lesson = 6
) lab_hours
       ,
(
select nvl(sum(vd2.hours),0)
from volume_discipline_ vd2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
      and vd2.idk_lesson = 7
) pr_hours
       ,
(
select nvl(sum(vd2.hours),0)
from volume_discipline_ vd2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
      and vd2.idk_lesson in (8, 50926)
) ksr_hours
       ,
(
select nvl(sum(vd2.hours),0)
from volume_discipline_ vd2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
      and vd2.idk_lesson in (20)
) sr_hours
       ,
(
select listagg(max(kl2.abbreviation), ' ') within group (order by kl2.abbreviation)
from volume_discipline_ vd2
     , kind_lesson_ kl2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
      and vd2.idk_lesson = kl2.idk_lesson
      and vd2.idk_lesson in
(
2     -- экзамены
, 3     -- зачеты
, 17    -- дифференцированные зачеты

, 10    -- контрольные работы
, 23    -- ПК1
, 24    -- ПК2
, 4     -- рефераты
, 11    -- курсовые проекты
, 18    -- курсовая работа
, 20020 -- аттестация
, 50946 -- РГР
, 51086 -- Эссэ
)
group by kl2.abbreviation
) cert
       , comp.comps
/*       ,
(
select --regexp_replace(listagg(sc2.abbreviation, ' ') within group (order by sc2.abbreviation),'([А-Яа-я\.]+[- ]{0,1}([0-9\.]{1,}))( \1)+', '\1')
listagg(sc2.abbreviation, ' ') within group (order by sc2.abbreviation) -- 31.12.2015
from ep_competences_ epc2
     , speciality_competences_ sc2
     , structure_plan_ sp2
where epc2.id_discipline_plan = sp2.id_discipline_plan
      and (epc2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
      and epc2.id_s_competence = sc2.id_s_competence
) comp*/
       , nvl(ps.identifier, ps.temp_identifier) identifier
       , ep.d_start
       , ep.course || '-' || ep.course_end || ' (' ||
(
select listagg(max(vd2.semester), ', ') within group (order by vd2.semester)
from volume_discipline_ vd2
     , structure_plan_ sp2
where vd2.id_discipline_plan = sp2.id_discipline_plan
      and (vd2.id_discipline_plan = sp.id_discipline_plan
          or sp2.id_dp_main = sp.id_discipline_plan)
group by vd2.semester
) || ')' course
       , kd.name dis_kind
       , decode(sp.idt_discipline, 4, 'Базовый', 'Выборочный') dis_type
       , sp.id_discipline_plan
       , ep.id_educational_plan
       , sp.idk_discipline
       , nvl(sp.idt_discipline, 2)
       , nvl(
(
select 'ФГОС 3+ - '
from parameters_paper_ pp
where pp.id_paragraph = ep.id_paragraph -- 12.04.2016
      and pp.idk_par_paper = 10 -- 04.05.2017
), decode(sd.type_of_use, 13, 'ФГОС 3 - ', 14, 'ФГОС 3 - ', 'ФГОС 2 - ')) || sd.fl_identifier as FGOS
       , ep.d_end

from structure_plan_ sp
     , educational_plan_ ep
     , paragraphs_status_ ps
     , kind_discipline_ kd
     , comp
     , edu_specialities_ sd

where sp.id_educational_plan = ep.id_educational_plan
      and ps.cancellation is null
      and ep.id_paragraph = ps.id_paragraph
      and kd.idk_discipline(+) = sp.idk_discipline
      and ps.idk_order = 30
      and sp.id_discipline =
      (
      select cs.id_discipline
      from complex_specialities_ cs
      where id_complex_specialities = ?
      )
      and sp.id_discipline_plan = comp.id_discipline_plan(+)
      and sd.id_d_specialitie = ep.id_d_specialitie
      and sd.d_start =
(
select nvl(max(case when ep.d_start between sda.d_start and nvl(sda.d_end, ep.d_start) then sda.d_start end), max(sda.d_start))
from sd_attributes_ sda
where sda.id_d_specialitie = sd.id_d_specialitie
group by sda.id_d_specialitie
)
      and (sp.id_d = ?
          or ? =
-- или есть оперативные планы с этой кафедрой
(
select regexp_substr(listagg(sp2.id_d, ',') within group (order by epf.d_start desc), '[[:digit:]]{1,}')
from epf
     , structure_plan_ sp2
where epf.id_ep = sp2.id_educational_plan
      and epf.id_par_main = ps.id_paragraph
      and sp2.id_discipline = sp.id_discipline
      and nvl(sp2.idk_discipline, -1) = nvl(sp.idk_discipline, -1)
      and nvl(sp2.idt_discipline, -1) = nvl(sp.idt_discipline, -1)
))
      and ep.id_d_specialitie = ?
      and (? is null
          or not exists
(
select epmc.id_educational_plan
from ep_mc_ epmc
where epmc.id_complex_specialities = ?
)
          or ep.id_educational_plan in
(
select epmc.id_educational_plan
from ep_mc_ epmc
where epmc.id_complex_specialities = ?
))