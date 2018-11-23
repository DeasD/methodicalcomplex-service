begin

insert into ep_mc_(id_educational_plan, id_complex_specialities)
select distinct
       sp.id_educational_plan
       , ?
from structure_plan_ sp
where (sp.id_discipline_plan = ? or sp.id_dp_main = ?)
      and not exists
(
select epmc.id_educational_plan
from ep_mc_ epmc
where epmc.id_complex_specialities = ?
);

for r in
(
select t1.sem semester_1
       , t1.sem2 sem2_1
       , t1.h hours_1
       , t1.hi hours_interactive_1
       , t1.idk_lesson idk_lesson_1
       
       , t2.sem semester_2
       , t2.sem2 sem2_2
       , t2.h hours_2
       , t2.hi hours_interactive_2
       , t2.idk_lesson idk_lesson_2
from
(
select vd.semester sem
       , vd.semester - min(vd.semester) over (partition by 1) + 1 sem2
       , sum(vd.hours) h
       , sum(vd.hours_interactive) hi
       , vd.idk_lesson
from volume_discipline_ vd
     , structure_plan_ sp
where vd.id_discipline_plan = sp.id_discipline_plan
      and (sp.id_discipline_plan = ?
          or sp.id_dp_main = ?)
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
group by vd.semester
         , vd.idk_lesson
) t1
full join
(
select elu.semester sem
       , elu.semester - nvl(nullif(min(elu.semester) over (partition by 1), 0), elu.semester) + 1 sem2
       , elu.hours h
       , elu.hours_interactive hi
       , elu.idk_lesson
from educational_load_umk_ elu
where elu.id_methodical_complex = ?
) t2
on t1.idk_lesson = t2.idk_lesson
   and t1.sem2 = t2.sem2
order by 
case 
 when t1.sem2 is not null and t2.sem2 is not null
  then 1
 when t2.sem2 is not null
  then 2
 when t1.sem2 is not null 
  then 3
end
)
loop

if r.idk_lesson_2 is null
then
insert into educational_load_umk_(id_methodical_complex,idk_lesson,semester,hours,hours_interactive)
values(?, r.idk_lesson_1, r.semester_1, r.hours_1, r.hours_interactive_1);

elsif r.idk_lesson_1 is null
then

delete from loading_dis_sections_ lds
where lds.id_educational_load_umk in
(
select elu.id_educational_load_umk
from educational_load_umk_ elu
where elu.idk_lesson = r.idk_lesson_2
      and elu.semester = r.semester_2
      and elu.hours = r.hours_2
      and nvl(elu.hours_interactive, -1) = nvl(r.hours_interactive_2, -1)
      and elu.id_methodical_complex = ?
);
  
delete from educational_load_umk_ elu
where elu.idk_lesson = r.idk_lesson_2
      and elu.semester = r.semester_2
      and elu.hours = r.hours_2
      and nvl(elu.hours_interactive, -1) = nvl(r.hours_interactive_2, -1)
      and elu.id_methodical_complex = ?;

elsif nvl(r.hours_1, 0) - nvl(r.hours_2, 0) != 0
      or nvl(r.hours_interactive_1, 0) - nvl(r.hours_interactive_2, 0) != 0
      or r.semester_1 != r.semester_2
then
  
update educational_load_umk_ elu
set elu.hours = r.hours_1
    , elu.hours_interactive = r.hours_interactive_1
    , elu.semester = r.semester_1
where elu.idk_lesson = r.idk_lesson_2
      and elu.semester = r.semester_2
      and elu.id_methodical_complex = ?;

end if;

end loop;
commit;
end;
