


-- 存储过程：分页查询用户
create or replace procedure
my_procedure(
  p_start in int, p_end in int, p_total out int, p_data out sys_refcursor
) as
begin
  select count(1) into p_total from pwd_user;
  open p_data for
    select * from (select rownum rn, t.* from pwd_user t where rownum<=p_end)
    where rn>=p_start;
end my_procedure;