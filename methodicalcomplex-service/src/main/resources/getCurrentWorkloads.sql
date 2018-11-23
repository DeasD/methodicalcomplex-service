select kl.name
       , elu.semester
       , nvl(elu.hours,0) hours
       , nvl(elu.hours_interactive,0) i_hours
       , elu.idk_lesson
       , elu.id_educational_load_umk
       , elu.id_methodical_complex
from educational_load_umk_ elu
     , kind_lesson_ kl
where elu.idk_lesson = kl.idk_lesson
     and elu.id_methodical_complex = ?
