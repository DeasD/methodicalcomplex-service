with res as
(
select eL.id_edition
      ,max(eL.RSalton) RSalton
from
(
 select e.id_edition
        ,1 RSalton
 from edition_ e
 where (? Is null or ? = '%' or ? = '*')
 Union
 select e.id_edition
        ,SCORE(1) RSalton
 from edition_ e
 where not ((? is null or ?  = '%' or ?  = '*'))
           and contains(e.Name
                       ,nvl(p_library_.Get_Where_Params(trim(?)
                                                       ,2
                                                       ,1
                                                       ,''
                                                       ,1
                                                       ,''
                                                       ,1
                                                       ,''
                                                       ,1
                                                       ,''
                                                       ,1
                                                       ,''
                                                       ,1
                                                       ,''
                                                       ,1)
                           ,'null')
                       ,1) > 0
) eL
 group By eL.id_edition
)
Select e.Name
      ,nvl(nvl(e.data_responsibility, p_library_.Get_Data_Resp(e.id_edition, 1, 0))
         ||
         Case
           when e.data_responsibility_next is not null
             then '; ' || e.data_responsibility_next
           when p_library_.Get_Data_Resp(e.id_edition, 2, 0) is null
             then ''
           when regexp_like(p_library_.Get_Data_Resp(e.id_edition, 2, 0),'^Ред(*)')
             then '; Под ' || p_library_.Get_Data_Resp(e.id_edition, 2, 0)
           else '; ' || p_library_.Get_Data_Resp(e.id_edition, 2, 0)
         End
         ,
(
Select listagg(w.surname || Decode(w.Name, '', '', ' ') || w.Name ||
                   Decode(w.patronymic, '', '', ' ') || w.patronymic, '; ') within group (order by
pe.index_record)
From participation_in_edition_ pe
              ,writers_ w
         Where pe.id_edition = e.id_edition
           And pe.id_author = w.id_author
           And pe.id_author = w.id_author
)
) as Authors
       -- Год издания!
      ,(Select --listagg (tg.year_edition, '; ') within group (order by tg.year_edition)
               max(tg.year_edition)
          From target_given_editions_ tg
         Where tg.id_edition = e.id_edition) Publish_Year
      ,(Select listagg(ph.name, '; ') within group (order by ph.name)
          From target_given_editions_ tg
               , publishing_houses_ ph
         Where tg.id_edition = e.id_edition
           And tg.id_publishing_house = ph.id_publishing_house) Publishers
      , ke.name kind_edition
      , p.place_name
      , e.id_edition
From res
     , edition_ e
     , kind_edition_ ke
     ,
(
select r.id_edition
       , listagg(ts_.Get_DivisionName(r.id_d, sysdate, 1, 1, 2, 2) || ' (' || r.note || ')', '; ')
         within group (order by 1) as place_name
from
(
select ce.id_edition
       , psg.id_d
       , psg.note
from res
     , copy_edition_ ce
     , registration_units_goods_ ru
     , place_storage_goods_ psg
where res.id_edition = ce.id_edition
      and ce.id_registration_units_goods = ru.id_registration_units_goods
      and ru.id_p_end is null -- не списана!
      and ru.id_registration_units_goods = psg.id_registration_units_goods
group by ce.id_edition
       , psg.id_d
       , psg.note
) r
group by r.id_edition
) p

Where res.id_edition = e.id_edition
      and e.idk_edition = ke.idk_edition
and res.id_edition = p.id_edition(+)