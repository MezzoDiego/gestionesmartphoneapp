package it.prova.gestionesmartphoneapp.service.smartphone;

import java.util.List;

import it.prova.gestionesmartphoneapp.dao.smartphone.SmartphoneDAO;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;

public interface SmartphoneService {
	
	public List<Smartphone> listAll() throws Exception;

	public Smartphone caricaSingoloElemento(Long id) throws Exception;
	
	public Smartphone caricaSingoloElementoEagerApps(Long id) throws Exception;

	public void aggiorna(Smartphone smartphoneInstance) throws Exception;

	public void inserisciNuovo(Smartphone smartphoneInstance) throws Exception;

	public void rimuovi(Long idSmartphone) throws Exception;
	
	public void rimuoviTuttiGliSmartphoneDallaTabellaDiJoin() throws Exception;
	
	public void rimuoviSmartphoneDallaTabellaDiJoin(Long idSmartphone) throws Exception;

	public void aggiungiApp(Smartphone smartphoneInstance, App appInstance) throws Exception;
	
	// per injection
		public void setSmartphoneDAO(SmartphoneDAO smartphoneDAO);
}
