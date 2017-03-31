/* *********更新前准备********
--逻辑检查
SELECT count(*),'当月系统在册人数|' FROM EmShebaoupdate WHERE ownmonth=201703 and Esiu_IfStop=0
SELECT count(*),'社保台账后人数|' FROM EmShebaoSZSI WHERE ownmonth=201703

SELECT count(*),'员工编号有为空项|' FROM EmShebaoSZSI WHERE GID is null and ownmonth=201703
SELECT count(*),'公司编号有为空项|' FROM EmShebaoSZSI WHERE CID is null  and ownmonth=201703
SELECT count(*),'开户状态有为空项|' FROM EmShebaoSZSI WHERE single is null  and ownmonth=201703
SELECT count(*),'身份证号码有为空项|' FROM EmShebaoSZSI WHERE idcard is null  and ownmonth=201703
SELECT count(*),'姓名有为空项|' FROM EmShebaoSZSI WHERE name is null  and ownmonth=201703
SELECT count(*),'户籍有为空项|' FROM EmShebaoSZSI WHERE hj is null  and ownmonth=201703
SELECT count(*),'参保险种有为空项|' FROM EmShebaoSZSI WHERE yl is null or yltype is null or gs is null or sye is null or syu is null
SELECT count(*),'缴交金额有为空项|' FROM EmShebaoSZSI WHERE ylcp is null or ylop is null or jlop is null or jlcp is null or  gscp is null or syecp is null or syucp is null
SELECT count(*),'补缴中员工编号有为空项|' FROM EmShebaoSZSIBJ WHERE gid is null  and ownmonth=201703


--更新没有上传成功的身份证、户籍、医疗类型、险种
SELECT max(id)id,computerid,lbid,sex,idcard,hj,radix,yl,yltype,gs,sye,syu into #a
FROM EmShebaoSZSIFee WHERE DATEDIFF(M,addtime,GETDATE())=0 and addtime is not null
group by computerid,lbid,sex,idcard,hj,radix,yl,yltype,gs,sye,syu

update EmShebaoSZSI set lbid=b.lbid,sex=b.sex,idcard=b.idcard,hj=b.hj,radix=b.radix,
yl=b.yl,yltype=b.yltype,gs=b.gs,sye=b.sye,syu=b.syu
FROM EmShebaoSZSI a,#a b WHERE a.idcard is null and a.computerid=b.computerid

drop table #a


--计算补交滞纳金
update EmShebaoBJ set emsb_overdue=round((emsb_totalcp+emsb_totalop)*5/10000*(1+datediff(day,emsb_overduedate,emsb_dptime)),2) 
where ownmonth=201703 and emsb_Ifdeclare=1
update emshebaobj set emsb_overdue=0 where emsb_overdue<0 and ownmonth=201703

--医疗补交
update EmShebaoBJJL set esbj_overdue=round((esbj_totalcp+esbj_totalop)*5/10000*(1+datediff(day,esbj_overduedate,esbj_dptime)),2) 
where ownmonth=201703 and esbj_Ifdeclare=1
update EmShebaoBJJL set esbj_overdue=0 where esbj_overdue<0 and ownmonth=201703


--检查补交金额不一致的数据
select  a.emsb_totalcp,b.essb_totalcp,* 
from (
select GID,CID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,
sum(emsb_Totalcp)emsb_Totalcp,sum(emsb_Totalop)emsb_Totalop,sum(emsb_Overdue)emsb_Overdue,emsb_startmonth
from
(select GID,CID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,
emsb_Totalcp,emsb_Totalop,emsb_Overdue,emsb_startmonth from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1
union all 
select GID,CID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,
esbj_Totalcp,esbj_Totalop,esbj_Overdue,esbj_startmonth
 from EmShebaoBJJL where ownmonth=201703 and esbj_ifdeclare=1)bj
group by GID,CID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,emsb_startmonth	
)a left join (select * from EmShebaoSZSIBJ where ownmonth=201703)b on a.emsb_computerid=b.essb_computerid 
and a.emsb_startmonth=convert(int,substring(b.essb_month,1,4)+right(replace('00'+substring(b.essb_month,6,2),'月',''),2))
where a.emsb_totalcp<>b.essb_totalcp and a.emsb_totalop=b.essb_totalop
	
--1、	福利部会发来社保局滞纳金的截屏，有167120（委托户）和391055（自签）两个账户，有滞纳金金额，与BMS的emshebaobj表的滞纳金对比一下，sql语句：
--自签户的：
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=0
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=0
--委托户的：
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=2
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=2
--外包的：
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=3
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=3
--派遣的：
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=4
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=4

--如果发现不一样金额的话，微调表emshebaobj里的emsb_overdue字段，直至两个金额一样为止。

2、	打开BMS系统，进入系统设置->系统台账更新，查看社保和公积金数据完整性检查说明.
手工在表里调整，直至社保和公积金两项都显示“数据完整性检查通过”。修改社保表emshebaoszsi
*/



--=========================步骤1=======================================================
--社保局问题，参加生育都显示不参加
update emshebaoszsi set syu='参加' where syucp<>0
update emshebaoszsi set syu='不参加' where syucp=0

--社保局问题，所有住房都不参加
update emshebaoszsi set house='不参加'

--社保局问题，失业参保金额显示有误
update EmShebaoSZSI set syecp=0 where sye='不参加'

--社保局问题，失业参保金额显示有误
--update EmShebaoSZSI set syecp=30 where sye='参加'
--update EmShebaoSZSI set syeop=15 where sye='参加'

--社保局问题，大学生医疗
update EmShebaoSZSI set yltype='大学生' where yltype='一档' and jlcp=0
--=========================步骤1=======================================================




--=========================步骤2（先确保滞纳金金额执行）=================================
/*
--更新社保补缴滞纳金
UPDATE EmShebaoSZSIBJ SET ESSB_OVERDUE=B.M FROM (select GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,
sum(isnull(emsb_Totalcp,0))emsb_Totalcp,sum(isnull(emsb_Totalop,0))emsb_Totalop,sum(isnull(emsb_Overdue,0))m,emsb_startmonth
from
(select GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,emsb_Totalcp,emsb_Totalop,
emsb_Overdue,emsb_startmonth from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1
union all 
select GID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,esbj_Totalcp,esbj_Totalop,
esbj_Overdue,esbj_startmonth
 from EmShebaoBJJL where ownmonth=201703 and esbj_ifdeclare=1)a
group by GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,emsb_startmonth)B 
WHERE EmShebaoSZSIBJ.GID=B.GID AND EmShebaoSZSIBJ.OWNMONTH=B.OWNMONTH  
and B.emsb_startmonth=convert(int,substring(essb_month,1,4)+right(replace('00'+substring(essb_month,6,2),'月',''),2)) 
and b.emsb_totalop=EmShebaoSZSIBJ.essb_totalop
*/
--更新社保补缴滞纳金(养老)
UPDATE EmShebaoSZSIBJ SET ESSB_OVERDUE=B.M FROM (select GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,
sum(isnull(emsb_Totalcp,0))emsb_Totalcp,sum(isnull(emsb_Totalop,0))emsb_Totalop,sum(isnull(emsb_Overdue,0))m,emsb_startmonth
from
(select GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,emsb_Totalcp,emsb_Totalop,
emsb_Overdue,emsb_startmonth from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1)a
group by GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,emsb_startmonth)B 
WHERE EmShebaoSZSIBJ.GID=B.GID AND EmShebaoSZSIBJ.OWNMONTH=B.OWNMONTH  
and B.emsb_startmonth=convert(int,substring(essb_month,1,4)+right(replace('00'+substring(essb_month,6,2),'月',''),2)) 
and b.emsb_totalop=EmShebaoSZSIBJ.essb_totalop

--更新社保补缴滞纳金(医疗)
UPDATE EmShebaoSZSIBJ SET ESSB_OVERDUE=B.M FROM (select GID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,
sum(isnull(esbj_Totalcp,0))emsb_Totalcp,sum(isnull(esbj_Totalop,0))emsb_Totalop,sum(isnull(esbj_Overdue,0))m,esbj_startmonth
from
(select GID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,esbj_Totalcp,esbj_Totalop,
esbj_Overdue,esbj_startmonth from EmShebaoBJJL where ownmonth=201703 and esbj_ifdeclare=1)a
group by GID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,esbj_startmonth)B 
WHERE EmShebaoSZSIBJ.GID=B.GID AND EmShebaoSZSIBJ.OWNMONTH=B.OWNMONTH  
and B.esbj_startmonth=convert(int,substring(essb_month,1,4)+right(replace('00'+substring(essb_month,6,2),'月',''),2)) 
and b.emsb_totalop=EmShebaoSZSIBJ.essb_totalop


update emshebaoszsibj set essb_overdue=0 where essb_overdue is null and ownmonth=201703

update emshebaoszsibj set essb_total=essb_totalcp+essb_totalop+isnull(essb_overdue,0) where ownmonth=201703
--=========================步骤2=======================================================





--=========================步骤3（请注意所属月份的变化）=================================
--备份update表信息
insert into emshebaoupdatesim (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syuop,esiu_syecp,esiu_syeop,esiu_gscp,esiu_gsop,esiu_housecp,esiu_houseop,esiu_totalcp,esiu_totalop,esiu_ifinure,esiu_remark,esiu_addname,esiu_addtime,esiu_single,esiu_worker,esiu_officialrank,esiu_hand,esiu_folk,esiu_ifstop,esiu_ifsame,esiu_client,esiu_lbid) 
select cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syuop,esiu_syecp,esiu_syeop,esiu_gscp,esiu_gsop,esiu_housecp,esiu_houseop,esiu_totalcp,esiu_totalop,esiu_ifinure,esiu_remark,esiu_addname,esiu_addtime,esiu_single,esiu_worker,esiu_officialrank,esiu_hand,esiu_folk,esiu_ifstop,esiu_ifsame,esiu_client,esiu_lbid from emshebaoupdate

--select top 1 * from PubOwnmonth where ownmonth>201002

--删除除被调走的人员以外所有信息
delete from emshebaoupdate where ownmonth=201703 and esiu_ifstop<2

--select * from emshebaoupdate
--szsi更新update表
insert into emshebaoupdate (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syuop,esiu_syecp,esiu_syeop,esiu_gscp,esiu_gsop,esiu_housecp,esiu_houseop,esiu_totalcp,esiu_totalop,esiu_ifinure,esiu_addname,esiu_addtime,esiu_single,esiu_ifstop,esiu_ifsame,esiu_lbid) 
select cid,gid,ownmonth,company,name,computerid,idcard,hj,radix,yl,gs,sye,syu,yltype,house,ylcp,ylop,jlcp,jlop,syucp,0,syecp,syeop,gscp,0,housecp,0,totalcp,totalop,0,'系统',getdate(),single,0,0,lbid from emshebaoszsi

--szsi写入shebao表
insert into emshebao (cid,gid,ownmonth,esiu_name,esiu_idcard,esiu_computerid,esiu_lbid,esiu_radix,esiu_hj,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syecp,esiu_gscp,esiu_housecp,esiu_totalcp,esiu_totalop,esiu_addtime,esiu_single,esiu_syeop) 
select cid,gid,ownmonth,name,idcard,computerid,lbid,radix,hj,yl,gs,sye,syu,yltype,house,ylcp,ylop,jlcp,jlop,syucp,syecp,gscp,housecp,totalcp,totalop,getdate(),single,syeop from emshebaoszsi

 --把上月被调走的人改成当月的
update emshebaoupdate set ownmonth=201704 where ownmonth<=201703

--=========================步骤3=======================================================



--=========================步骤4（请注意所属月份的变化）=================================
--变更雇员基本信息状态(提前入职离职的人员)
--update embase set emba_state=1 where emba_state=2 and emba_indate<='2010-9-15'

--update embase set emba_state=0 where emba_state=3 and emba_outdate<='2010-9-15'

--更新社保入职变更
--insert into emshebaoupdate (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ifinure,esiu_addtime,esiu_single,esiu_ifstop,esiu_ifsame)
--select cid,gid,ownmonth,emsc_company,emsc_name,emsc_computerid,emsc_idcard,emsc_hj,emsc_radix,emsc_yl,emsc_gs,emsc_sye,emsc_syu,emsc_yltype,emsc_house,0,getdate(),emsc_single,0,0 from emshebaochange where ownmonth=201111 and (emsc_change='调入' or emsc_change='新增') and emsc_ifdeclare<>3

--insert into emshebaoupdate (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ifinure,esiu_addtime,esiu_single,esiu_ifstop,esiu_ifsame)
--select cid,gid,ownmonth,emsc_company,emsc_name,emsc_computerid,emsc_idcard,emsc_hj,emsc_radix,emsc_yl,emsc_gs,emsc_sye,emsc_syu,emsc_yltype,emsc_house,0,getdate(),emsc_single,3,0 from emshebaochange where ownmonth=201111 and (emsc_change='调入' or emsc_change='新增') and emsc_ifdeclare=3

--更新修改工资变更
update emshebaoupdate set esiu_radix=emsc_radix from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ownmonth=201704 and emsc_change='修改工资' and emsc_ifdeclare<>3

--更新档案修改变更
update emshebaoupdate set esiu_radix=emsc_radix,esiu_hj=emsc_hj,esiu_yl=emsc_yl,esiu_gs=emsc_gs,esiu_sye=emsc_sye,esiu_syu=emsc_syu,esiu_house=emsc_house,esiu_yltype=emsc_yltype from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ownmonth=201704 and emsc_change='档案修改' and emsc_ifdeclare<>3

--更新停交人员变更(退回无需申报的停交数据也更新在册状态)
update emshebaoupdate set esiu_ifstop=1 from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ownmonth=201704 and emsc_change='停交' and emsc_ifdeclare<>3 AND ((emsc_Remark<>'社保转移' and emsc_remark<>'改医疗类型') or emsc_remark is null)

update emshebaoupdate set esiu_ifstop=1 from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ID in(
select ID from emshebaochange a left join 
(select smwr_tid from View_Message where syme_state=1 and  smwr_table='EmSheBaoChange' and syme_content like '%社保局系统显示员工已在%' or syme_content like '%被调走%')b
on a.ID=b.smwr_tid
 where Ownmonth=201704 and emsc_Change='停交' and emsc_IfDeclare=3 and smwr_tid is not null)


--更新交单变更
update emshebaoupdate set esiu_hj=escs_changehj from emshebaochangeszsi 
where emshebaoupdate.gid=emshebaochangeszsi.gid and emshebaochangeszsi.ownmonth>=201703 and emshebaoupdate.ownmonth=201704 and escs_ifdeclare=1 and escs_change='变更户籍'

update emshebaoupdate set esiu_yltype='一档' where esiu_hj='市内城镇' and esiu_yltype='二档'

--=========================步骤4（请注意所属月份的变化）=================================


--=========================步骤5=======================================================
--匹配update表算法id
update EmShebaoUpdate set Esiu_hj='市外城镇' where Esiu_hj='市外农村'

update EmShebaoUpdate set Esiu_formulaid=ef.id
from EmShebaoUpdate eu inner join EmShebaoFormula ef 
on ef.id<8 and eu.Esiu_hj=ef.emsf_hj and eu.Esiu_YL=emsf_yl and Esiu_GS=emsf_gs and Esiu_YLType=emsf_yltype and Esiu_Sye=emsf_sye and Esiu_Syu=emsf_syu
where Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=2
where Esiu_YLType in('一档','大学生') and Esiu_hj='市内城镇' and Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=3
where Esiu_YLType in('一档','大学生') and Esiu_hj='市外城镇' and Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=4
where Esiu_YLType='二档' and Esiu_hj='市外城镇' and Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=5
where Esiu_YLType='三档' and Esiu_hj='市外城镇' and Esiu_formulaid is null
--=========================步骤5=======================================================



--=========================步骤6=======================================================

--检查合同工伤、失业比例是否正常
select coco_shebao,coco_Injury,Compo,coco_sb_sye,sye,a.* 
--update CoCompact set coco_Injury=b.Compo,coco_sb_sye=b.sye
 from CoCompact a,EmShebaoCompo b where a.coco_shebao in('中智开户(委托)','中智开户') and coco_shebao=b.shebao
and (coco_Injury<>b.Compo or coco_sb_sye<>b.sye)


select coco_shebao,coco_Injury,cosb_business_per,coco_sb_sye,sye,a.* 
--update CoCompact set coco_Injury=b.cosb_business_per,coco_sb_sye=b.sye,coco_shebaoid=cosb_sorid
 from CoCompact a,
(select cid,cosb_sorid,(0.01-cosb_unemployment_per)*100 as sye,cosb_business_per from CoShebao 
where cosb_state=1 and cid!=0 and cid is not null and cosb_sorid is not null 
and cosb_business_per is not null and cosb_unemployment_per is not null)b
where a.coco_shebao in('独立开户') and a.cid=b.cid
and (coco_Injury<>b.cosb_business_per or coco_sb_sye<>b.sye or coco_shebaoID is null or coco_shebaoID=0)


--更新update金额合计
--=========================步骤6=======================================================

--步骤7暂时取消
--=========================步骤7=======================================================
--更新台账后，更新emshebaoszsibj补交表滞纳金字段
/*

declare @ownmonth int
set @ownmonth=201703

update EmShebaoSZSIBJ set essb_Overdue=b.emsb_Overdue from EmShebaoSZSIBJ a,
(select id,emsb_Overdue from 
(select GID,MAX(id)id from EmShebaoSZSIBJ where ownmonth=@ownmonth group by gid)a
left join 
(select gid,SUM(emsb_Overdue)emsb_Overdue from (
select GID,emsb_Overdue from EmShebaoBJ where emsb_Ifdeclare=1 and Ownmonth=@ownmonth
union all 
select GID,esbj_Overdue from emshebaobjjl where esbj_Ifdeclare=1 and Ownmonth=@ownmonth
)sb group by gid)b on a.GID=b.GID)b
where a.ownmonth=@ownmonth and a.ID=b.id

update EmShebaoSZSIBJ set essb_Overdue=0 where ownmonth=201703 and essb_Overdue is null

update EmShebaoSZSIBJ set essb_total=essb_totalcp+essb_totalop+essb_Overdue where ownmonth=201703 

*/
--=========================步骤7=======================================================