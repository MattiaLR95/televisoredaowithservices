package it.prova.televisoredaowithservices.dao.televisore;

import java.util.Date;
import java.util.List;

import it.prova.televisoredaowithservices.dao.IBaseDAO;
import it.prova.televisoredaowithservices.model.Televisore;

public interface TelevisoreDAO extends IBaseDAO<Televisore> {
	public List<Televisore> findAllTelevisionBetweenDate(Date primo, Date secondo);

	public Televisore findTelevisionBigger();

	public List<Televisore> brandsNameLastSixMonth(Date input);
}
