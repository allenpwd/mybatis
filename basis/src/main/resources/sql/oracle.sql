-- 清空表
DROP TABLE "db_user";
DROP TABLE "db_dept";


CREATE TABLE "DB_DEPT"
(	"ID" NUMBER,
"DEPT_NAME" VARCHAR2(20) NOT NULL ENABLE,
 CONSTRAINT "SYS_C0022671" PRIMARY KEY ("ID")
)


CREATE TABLE "DB_USER"
(	"ID" NUMBER,
"USER_NAME" VARCHAR2(50) NOT NULL ENABLE,
"AGE" NUMBER,
"STATUS" NUMBER,
"CREATE_AT" TIMESTAMP (6),
"DEPT_ID" NUMBER,
"MSG" BLOB,
 CONSTRAINT "SYS_C0022673" PRIMARY KEY ("ID"),
 CONSTRAINT "pk_user_dept" FOREIGN KEY ("DEPT_ID")
  REFERENCES "HR"."DB_DEPT" ("ID") ON DELETE SET NULL ENABLE
)

-- 自增序列
create sequence USER_SEQ
increment by 1
start with 1


-- 存储过程：分页查询用户
CREATE OR REPLACE
procedure
my_procedure(
  p_page_num in int, p_page_size in int, p_total out int, p_data out sys_refcursor
) as
begin
  select count(1) into p_total from pwd_user;
  open p_data for
    select * from (select rownum rn, t.* from db_user t where rownum<=(p_page_num*p_page_size))
    where rn>=(p_page_num-1)*p_page_size;
end my_procedure;


INSERT INTO "DB_DEPT"("ID", "DEPT_NAME") VALUES ('1', '开发部');
INSERT INTO "DB_USER"("ID", "USER_NAME", "AGE", "STATUS", "CREATE_AT", "DEPT_ID", "MSG") VALUES ('1', '门那粒沙', '20', '0', TO_TIMESTAMP('2020-06-06 20:40:32.592000', 'SYYYY-MM-DD HH24:MI:SS:FF6'), '1', HEXTORAW('C5A3B1C6'));
