package it.prova.televisoredaowithservices.dao.televisore;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.dao.IBaseDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public interface TelevisoreDAO extends IBaseDAO<Televisore> {
	public List<Televisore> findAllTelevisionBetweenDate(Date primo, Date secondo) throws Exception;

	public Televisore findTelevisionBigger() throws Exception;

	public List<String> brandsNameLastSixMonth(Date input) throws Exception;
}
