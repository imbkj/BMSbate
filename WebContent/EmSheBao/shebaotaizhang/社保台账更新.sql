/* *********����ǰ׼��********
--�߼����
SELECT count(*),'����ϵͳ�ڲ�����|' FROM EmShebaoupdate WHERE ownmonth=201703 and Esiu_IfStop=0
SELECT count(*),'�籣̨�˺�����|' FROM EmShebaoSZSI WHERE ownmonth=201703

SELECT count(*),'Ա�������Ϊ����|' FROM EmShebaoSZSI WHERE GID is null and ownmonth=201703
SELECT count(*),'��˾�����Ϊ����|' FROM EmShebaoSZSI WHERE CID is null  and ownmonth=201703
SELECT count(*),'����״̬��Ϊ����|' FROM EmShebaoSZSI WHERE single is null  and ownmonth=201703
SELECT count(*),'���֤������Ϊ����|' FROM EmShebaoSZSI WHERE idcard is null  and ownmonth=201703
SELECT count(*),'������Ϊ����|' FROM EmShebaoSZSI WHERE name is null  and ownmonth=201703
SELECT count(*),'������Ϊ����|' FROM EmShebaoSZSI WHERE hj is null  and ownmonth=201703
SELECT count(*),'�α�������Ϊ����|' FROM EmShebaoSZSI WHERE yl is null or yltype is null or gs is null or sye is null or syu is null
SELECT count(*),'�ɽ������Ϊ����|' FROM EmShebaoSZSI WHERE ylcp is null or ylop is null or jlop is null or jlcp is null or  gscp is null or syecp is null or syucp is null
SELECT count(*),'������Ա�������Ϊ����|' FROM EmShebaoSZSIBJ WHERE gid is null  and ownmonth=201703


--����û���ϴ��ɹ������֤��������ҽ�����͡�����
SELECT max(id)id,computerid,lbid,sex,idcard,hj,radix,yl,yltype,gs,sye,syu into #a
FROM EmShebaoSZSIFee WHERE DATEDIFF(M,addtime,GETDATE())=0 and addtime is not null
group by computerid,lbid,sex,idcard,hj,radix,yl,yltype,gs,sye,syu

update EmShebaoSZSI set lbid=b.lbid,sex=b.sex,idcard=b.idcard,hj=b.hj,radix=b.radix,
yl=b.yl,yltype=b.yltype,gs=b.gs,sye=b.sye,syu=b.syu
FROM EmShebaoSZSI a,#a b WHERE a.idcard is null and a.computerid=b.computerid

drop table #a


--���㲹�����ɽ�
update EmShebaoBJ set emsb_overdue=round((emsb_totalcp+emsb_totalop)*5/10000*(1+datediff(day,emsb_overduedate,emsb_dptime)),2) 
where ownmonth=201703 and emsb_Ifdeclare=1
update emshebaobj set emsb_overdue=0 where emsb_overdue<0 and ownmonth=201703

--ҽ�Ʋ���
update EmShebaoBJJL set esbj_overdue=round((esbj_totalcp+esbj_totalop)*5/10000*(1+datediff(day,esbj_overduedate,esbj_dptime)),2) 
where ownmonth=201703 and esbj_Ifdeclare=1
update EmShebaoBJJL set esbj_overdue=0 where esbj_overdue<0 and ownmonth=201703


--��鲹����һ�µ�����
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
and a.emsb_startmonth=convert(int,substring(b.essb_month,1,4)+right(replace('00'+substring(b.essb_month,6,2),'��',''),2))
where a.emsb_totalcp<>b.essb_totalcp and a.emsb_totalop=b.essb_totalop
	
--1��	�������ᷢ���籣�����ɽ�Ľ�������167120��ί�л�����391055����ǩ�������˻��������ɽ����BMS��emshebaobj������ɽ�Ա�һ�£�sql��䣺
--��ǩ���ģ�
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=0
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=0
--ί�л��ģ�
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=2
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=2
--����ģ�
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=3
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=3
--��ǲ�ģ�
select sum(emsb_overdue) from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1 and emsb_single=4
select sum(esbj_overdue) from EmShebaoBJjl where ownmonth=201703 and esbj_ifdeclare=1 and esbj_single=4

--������ֲ�һ�����Ļ���΢����emshebaobj���emsb_overdue�ֶΣ�ֱ���������һ��Ϊֹ��

2��	��BMSϵͳ������ϵͳ����->ϵͳ̨�˸��£��鿴�籣�͹��������������Լ��˵��.
�ֹ��ڱ��������ֱ���籣�͹����������ʾ�����������Լ��ͨ�������޸��籣��emshebaoszsi
*/



--=========================����1=======================================================
--�籣�����⣬�μ���������ʾ���μ�
update emshebaoszsi set syu='�μ�' where syucp<>0
update emshebaoszsi set syu='���μ�' where syucp=0

--�籣�����⣬����ס�������μ�
update emshebaoszsi set house='���μ�'

--�籣�����⣬ʧҵ�α������ʾ����
update EmShebaoSZSI set syecp=0 where sye='���μ�'

--�籣�����⣬ʧҵ�α������ʾ����
--update EmShebaoSZSI set syecp=30 where sye='�μ�'
--update EmShebaoSZSI set syeop=15 where sye='�μ�'

--�籣�����⣬��ѧ��ҽ��
update EmShebaoSZSI set yltype='��ѧ��' where yltype='һ��' and jlcp=0
--=========================����1=======================================================




--=========================����2����ȷ�����ɽ���ִ�У�=================================
/*
--�����籣�������ɽ�
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
and B.emsb_startmonth=convert(int,substring(essb_month,1,4)+right(replace('00'+substring(essb_month,6,2),'��',''),2)) 
and b.emsb_totalop=EmShebaoSZSIBJ.essb_totalop
*/
--�����籣�������ɽ�(����)
UPDATE EmShebaoSZSIBJ SET ESSB_OVERDUE=B.M FROM (select GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,
sum(isnull(emsb_Totalcp,0))emsb_Totalcp,sum(isnull(emsb_Totalop,0))emsb_Totalop,sum(isnull(emsb_Overdue,0))m,emsb_startmonth
from
(select GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,emsb_Totalcp,emsb_Totalop,
emsb_Overdue,emsb_startmonth from EmShebaoBJ where ownmonth=201703 and emsb_ifdeclare=1)a
group by GID,Ownmonth,emsb_name,emsb_idcard,emsb_Computerid,emsb_startmonth)B 
WHERE EmShebaoSZSIBJ.GID=B.GID AND EmShebaoSZSIBJ.OWNMONTH=B.OWNMONTH  
and B.emsb_startmonth=convert(int,substring(essb_month,1,4)+right(replace('00'+substring(essb_month,6,2),'��',''),2)) 
and b.emsb_totalop=EmShebaoSZSIBJ.essb_totalop

--�����籣�������ɽ�(ҽ��)
UPDATE EmShebaoSZSIBJ SET ESSB_OVERDUE=B.M FROM (select GID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,
sum(isnull(esbj_Totalcp,0))emsb_Totalcp,sum(isnull(esbj_Totalop,0))emsb_Totalop,sum(isnull(esbj_Overdue,0))m,esbj_startmonth
from
(select GID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,esbj_Totalcp,esbj_Totalop,
esbj_Overdue,esbj_startmonth from EmShebaoBJJL where ownmonth=201703 and esbj_ifdeclare=1)a
group by GID,Ownmonth,esbj_name,esbj_idcard,esbj_Computerid,esbj_startmonth)B 
WHERE EmShebaoSZSIBJ.GID=B.GID AND EmShebaoSZSIBJ.OWNMONTH=B.OWNMONTH  
and B.esbj_startmonth=convert(int,substring(essb_month,1,4)+right(replace('00'+substring(essb_month,6,2),'��',''),2)) 
and b.emsb_totalop=EmShebaoSZSIBJ.essb_totalop


update emshebaoszsibj set essb_overdue=0 where essb_overdue is null and ownmonth=201703

update emshebaoszsibj set essb_total=essb_totalcp+essb_totalop+isnull(essb_overdue,0) where ownmonth=201703
--=========================����2=======================================================





--=========================����3����ע�������·ݵı仯��=================================
--����update����Ϣ
insert into emshebaoupdatesim (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syuop,esiu_syecp,esiu_syeop,esiu_gscp,esiu_gsop,esiu_housecp,esiu_houseop,esiu_totalcp,esiu_totalop,esiu_ifinure,esiu_remark,esiu_addname,esiu_addtime,esiu_single,esiu_worker,esiu_officialrank,esiu_hand,esiu_folk,esiu_ifstop,esiu_ifsame,esiu_client,esiu_lbid) 
select cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syuop,esiu_syecp,esiu_syeop,esiu_gscp,esiu_gsop,esiu_housecp,esiu_houseop,esiu_totalcp,esiu_totalop,esiu_ifinure,esiu_remark,esiu_addname,esiu_addtime,esiu_single,esiu_worker,esiu_officialrank,esiu_hand,esiu_folk,esiu_ifstop,esiu_ifsame,esiu_client,esiu_lbid from emshebaoupdate

--select top 1 * from PubOwnmonth where ownmonth>201002

--ɾ���������ߵ���Ա����������Ϣ
delete from emshebaoupdate where ownmonth=201703 and esiu_ifstop<2

--select * from emshebaoupdate
--szsi����update��
insert into emshebaoupdate (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syuop,esiu_syecp,esiu_syeop,esiu_gscp,esiu_gsop,esiu_housecp,esiu_houseop,esiu_totalcp,esiu_totalop,esiu_ifinure,esiu_addname,esiu_addtime,esiu_single,esiu_ifstop,esiu_ifsame,esiu_lbid) 
select cid,gid,ownmonth,company,name,computerid,idcard,hj,radix,yl,gs,sye,syu,yltype,house,ylcp,ylop,jlcp,jlop,syucp,0,syecp,syeop,gscp,0,housecp,0,totalcp,totalop,0,'ϵͳ',getdate(),single,0,0,lbid from emshebaoszsi

--szsiд��shebao��
insert into emshebao (cid,gid,ownmonth,esiu_name,esiu_idcard,esiu_computerid,esiu_lbid,esiu_radix,esiu_hj,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ylcp,esiu_ylop,esiu_jlcp,esiu_jlop,esiu_syucp,esiu_syecp,esiu_gscp,esiu_housecp,esiu_totalcp,esiu_totalop,esiu_addtime,esiu_single,esiu_syeop) 
select cid,gid,ownmonth,name,idcard,computerid,lbid,radix,hj,yl,gs,sye,syu,yltype,house,ylcp,ylop,jlcp,jlop,syucp,syecp,gscp,housecp,totalcp,totalop,getdate(),single,syeop from emshebaoszsi

 --�����±����ߵ��˸ĳɵ��µ�
update emshebaoupdate set ownmonth=201704 where ownmonth<=201703

--=========================����3=======================================================



--=========================����4����ע�������·ݵı仯��=================================
--�����Ա������Ϣ״̬(��ǰ��ְ��ְ����Ա)
--update embase set emba_state=1 where emba_state=2 and emba_indate<='2010-9-15'

--update embase set emba_state=0 where emba_state=3 and emba_outdate<='2010-9-15'

--�����籣��ְ���
--insert into emshebaoupdate (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ifinure,esiu_addtime,esiu_single,esiu_ifstop,esiu_ifsame)
--select cid,gid,ownmonth,emsc_company,emsc_name,emsc_computerid,emsc_idcard,emsc_hj,emsc_radix,emsc_yl,emsc_gs,emsc_sye,emsc_syu,emsc_yltype,emsc_house,0,getdate(),emsc_single,0,0 from emshebaochange where ownmonth=201111 and (emsc_change='����' or emsc_change='����') and emsc_ifdeclare<>3

--insert into emshebaoupdate (cid,gid,ownmonth,esiu_company,esiu_name,esiu_computerid,esiu_idcard,esiu_hj,esiu_radix,esiu_yl,esiu_gs,esiu_sye,esiu_syu,esiu_yltype,esiu_house,esiu_ifinure,esiu_addtime,esiu_single,esiu_ifstop,esiu_ifsame)
--select cid,gid,ownmonth,emsc_company,emsc_name,emsc_computerid,emsc_idcard,emsc_hj,emsc_radix,emsc_yl,emsc_gs,emsc_sye,emsc_syu,emsc_yltype,emsc_house,0,getdate(),emsc_single,3,0 from emshebaochange where ownmonth=201111 and (emsc_change='����' or emsc_change='����') and emsc_ifdeclare=3

--�����޸Ĺ��ʱ��
update emshebaoupdate set esiu_radix=emsc_radix from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ownmonth=201704 and emsc_change='�޸Ĺ���' and emsc_ifdeclare<>3

--���µ����޸ı��
update emshebaoupdate set esiu_radix=emsc_radix,esiu_hj=emsc_hj,esiu_yl=emsc_yl,esiu_gs=emsc_gs,esiu_sye=emsc_sye,esiu_syu=emsc_syu,esiu_house=emsc_house,esiu_yltype=emsc_yltype from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ownmonth=201704 and emsc_change='�����޸�' and emsc_ifdeclare<>3

--����ͣ����Ա���(�˻������걨��ͣ������Ҳ�����ڲ�״̬)
update emshebaoupdate set esiu_ifstop=1 from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ownmonth=201704 and emsc_change='ͣ��' and emsc_ifdeclare<>3 AND ((emsc_Remark<>'�籣ת��' and emsc_remark<>'��ҽ������') or emsc_remark is null)

update emshebaoupdate set esiu_ifstop=1 from emshebaochange 
where emshebaoupdate.gid=emshebaochange.gid and emshebaochange.ID in(
select ID from emshebaochange a left join 
(select smwr_tid from View_Message where syme_state=1 and  smwr_table='EmSheBaoChange' and syme_content like '%�籣��ϵͳ��ʾԱ������%' or syme_content like '%������%')b
on a.ID=b.smwr_tid
 where Ownmonth=201704 and emsc_Change='ͣ��' and emsc_IfDeclare=3 and smwr_tid is not null)


--���½������
update emshebaoupdate set esiu_hj=escs_changehj from emshebaochangeszsi 
where emshebaoupdate.gid=emshebaochangeszsi.gid and emshebaochangeszsi.ownmonth>=201703 and emshebaoupdate.ownmonth=201704 and escs_ifdeclare=1 and escs_change='�������'

update emshebaoupdate set esiu_yltype='һ��' where esiu_hj='���ڳ���' and esiu_yltype='����'

--=========================����4����ע�������·ݵı仯��=================================


--=========================����5=======================================================
--ƥ��update���㷨id
update EmShebaoUpdate set Esiu_hj='�������' where Esiu_hj='����ũ��'

update EmShebaoUpdate set Esiu_formulaid=ef.id
from EmShebaoUpdate eu inner join EmShebaoFormula ef 
on ef.id<8 and eu.Esiu_hj=ef.emsf_hj and eu.Esiu_YL=emsf_yl and Esiu_GS=emsf_gs and Esiu_YLType=emsf_yltype and Esiu_Sye=emsf_sye and Esiu_Syu=emsf_syu
where Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=2
where Esiu_YLType in('һ��','��ѧ��') and Esiu_hj='���ڳ���' and Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=3
where Esiu_YLType in('һ��','��ѧ��') and Esiu_hj='�������' and Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=4
where Esiu_YLType='����' and Esiu_hj='�������' and Esiu_formulaid is null

update EmShebaoUpdate set Esiu_formulaid=5
where Esiu_YLType='����' and Esiu_hj='�������' and Esiu_formulaid is null
--=========================����5=======================================================



--=========================����6=======================================================

--����ͬ���ˡ�ʧҵ�����Ƿ�����
select coco_shebao,coco_Injury,Compo,coco_sb_sye,sye,a.* 
--update CoCompact set coco_Injury=b.Compo,coco_sb_sye=b.sye
 from CoCompact a,EmShebaoCompo b where a.coco_shebao in('���ǿ���(ί��)','���ǿ���') and coco_shebao=b.shebao
and (coco_Injury<>b.Compo or coco_sb_sye<>b.sye)


select coco_shebao,coco_Injury,cosb_business_per,coco_sb_sye,sye,a.* 
--update CoCompact set coco_Injury=b.cosb_business_per,coco_sb_sye=b.sye,coco_shebaoid=cosb_sorid
 from CoCompact a,
(select cid,cosb_sorid,(0.01-cosb_unemployment_per)*100 as sye,cosb_business_per from CoShebao 
where cosb_state=1 and cid!=0 and cid is not null and cosb_sorid is not null 
and cosb_business_per is not null and cosb_unemployment_per is not null)b
where a.coco_shebao in('��������') and a.cid=b.cid
and (coco_Injury<>b.cosb_business_per or coco_sb_sye<>b.sye or coco_shebaoID is null or coco_shebaoID=0)


--����update���ϼ�
--=========================����6=======================================================

--����7��ʱȡ��
--=========================����7=======================================================
--����̨�˺󣬸���emshebaoszsibj���������ɽ��ֶ�
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
--=========================����7=======================================================