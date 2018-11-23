select e.id_e
       , nvl(eh.surname, e.surname) || ' ' || nvl(eh.name, e.name) ||
          (case when eh.id_e is not null and eh.patronymic is null then ''
                when eh.id_e is not null and eh.patronymic is not null then (' ' || eh.patronymic)
                else (' ' || e.patronymic) end) fio
       , nvl(eh.date_of_birth, e.date_of_birth) birthday
from employees_ e
     , employees_history_ eh
where e.id_e = eh.id_e(+)
      and upper(e.surname || ' ' || e.name || ' ' || e.patronymic) like (replace(upper(?),'*','%') || '%')
      group by e.id_e
               , nvl(eh.surname, e.surname) || ' ' || nvl(eh.name, e.name) ||
                 (case when eh.id_e is not null and eh.patronymic is null then ''
                    when eh.id_e is not null and eh.patronymic is not null then (' ' || eh.patronymic)
                    else (' ' || e.patronymic) end)
               , nvl(eh.date_of_birth, e.date_of_birth)