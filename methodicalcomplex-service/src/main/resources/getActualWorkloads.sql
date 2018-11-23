select t2.id_educational_load_umk
       , nvl(t2.name, t1.name) name
       , nvl(t2.sem, t1.sem) semester
       , nvl(t2.h, 0) - nvl(t1.h, 0) hours
       , nvl(t2.hi, 0) - nvl(t1.hi, 0) hours_interactive
       ,
case
 when t2.idk_lesson is null
  then 'Добавить'
 when t1.idk_lesson is null
  then 'Удалить'
 when nvl(t2.h, 0) - nvl(t1.h, 0) + nvl(t2.hi, 0) - nvl(t1.hi, 0) = 0
  then 'Не изменяется'
 else 'Обновить'
end as Action
      , nvl(t2.idk_lesson,t1.idk_lesson) idk_lesson
from
(
select kl.name
       , vd.semester sem
       , vd.semester - min(vd.semester) over (partition by 1) + 1 sem2
       , sum(vd.hours) h
       , sum(vd.hours_interactive) hi
       , vd.idk_lesson
from volume_discipline_ vd
     , kind_lesson_ kl
     , structure_plan_ sp
where vd.id_discipline_plan = sp.id_discipline_plan
      and (sp.id_discipline_plan = ?
          or sp.id_dp_main = ?)
      and kl.idk_lesson = vd.idk_lesson
      and not exists
(
select 'x'
from parameters_paper_ pp2
     , educational_plan_ ep2
where pp2.id_paragraph = ep2.id_paragraph
      and ep2.id_educational_plan = sp.id_educational_plan
      and pp2.idk_par_paper = 12
      and pp2.value_parameter = 1
      and vd.idk_lesson in (33, 34)
)
group by kl.name
         , vd.semester
         , vd.idk_lesson
) t1
full join
(
select elu.id_educational_load_umk
       , kl.name
       , elu.semester sem
       , elu.semester - nvl(nullif(min(elu.semester) over (partition by 1), 0), elu.semester) + 1 sem2
       , elu.hours h
       , elu.hours_interactive hi
       , elu.idk_lesson
from educational_load_umk_ elu
     , kind_lesson_ kl
where elu.id_methodical_complex =
      (
      select cs.id_methodical_complex
      from complex_specialities_ cs
      where cs.id_complex_specialities = ?
      )
      and kl.idk_lesson = elu.idk_lesson
) t2
on t1.idk_lesson = t2.idk_lesson
   and t1.sem2 = t2.sem2