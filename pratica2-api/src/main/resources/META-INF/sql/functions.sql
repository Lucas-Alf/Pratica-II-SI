--select * from historicocargocontrato(2020,2)
CREATE OR REPLACE FUNCTION historicocargocontrato(ano integer, mes integer)
 RETURNS TABLE(id integer, data date, matricula integer, cargoid integer)
 LANGUAGE sql
AS $function$
	select historicocargocontrato.id, historicocargocontrato.data, historicocargocontrato.matricula, historicocargocontrato.cargoid from historicocargocontrato
		where date_part('year', historicocargocontrato.data) <= ano and date_part('month', historicocargocontrato.data) <= mes 
	order by historicocargocontrato.data desc, historicocargocontrato.id desc
		limit 1;
$function$;
	
	
--select * from salario(2020,01)
CREATE OR REPLACE FUNCTION salario(ano integer, mes integer)
 RETURNS TABLE(descricao text, valor decimal(12,2), matricula integer, cargoid integer, data date)
 LANGUAGE sql
AS $function$
	select (tabelasalarial.descricao || ' ' ||faixatabelasalarial.nivel) as descricao, ((faixatabelasalarial.percentual/100) * tabelasalarial.valorbase) as valor,  contrato.matricula, cargoid, data
		from historicocargocontrato(ano,mes) as hiscontrato
	inner join contrato on contrato.matricula = hiscontrato.matricula
	inner join cargo on cargo.id = hiscontrato.cargoid
	inner join faixatabelasalarial  on faixatabelasalarial.id  = cargo.faixatabelasalarialid
	inner join tabelasalarial on tabelasalarial.id = faixatabelasalarial.tabelasalarialid;
$function$;
	
	
 
