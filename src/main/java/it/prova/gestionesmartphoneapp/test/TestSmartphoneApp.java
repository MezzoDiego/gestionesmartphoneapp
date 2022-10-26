package it.prova.gestionesmartphoneapp.test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;
import it.prova.gestionesmartphoneapp.service.MyServiceFactory;
import it.prova.gestionesmartphoneapp.service.app.AppService;
import it.prova.gestionesmartphoneapp.service.smartphone.SmartphoneService;

public class TestSmartphoneApp {

	public static void main(String[] args) {

		SmartphoneService smartphoneServiceInstance = MyServiceFactory.getSmartphoneServiceInstance();
		AppService appServiceInstance = MyServiceFactory.getAppServiceInstance();

		try {
			System.out.println("##################################################################################");
			// testInserimentoNuovoSmartphone(smartphoneServiceInstance);
			System.out.println("##################################################################################");
			// testInserimentoNuovaApp(appServiceInstance);
			System.out.println("##################################################################################");
			// testAggiornamentoVersioneOSSmartphone(smartphoneServiceInstance);
			System.out.println("##################################################################################");
			// testAggiornamentoVersioneAppEDataAggiornamento(appServiceInstance);
			System.out.println("##################################################################################");
			testInstallazioneApp(appServiceInstance, smartphoneServiceInstance);
			System.out.println("##################################################################################");
			System.out.println("##################################################################################");
			System.out.println("##################################################################################");

			
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserimentoNuovoSmartphone(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoSmartphone inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoSmartphone fallito ");

		// reset tabella
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		System.out.println(".......testInserimentoNuovoSmartphone fine: PASSED.............");
	}

	private static void testInserimentoNuovaApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovaApp inizio.............");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaApp fallito ");

		// reset tabella
		appServiceInstance.rimuovi(appInstance.getId());

		System.out.println(".......testInserimentoNuovaApp fine: PASSED.............");
	}

	private static void testAggiornamentoVersioneOSSmartphone(SmartphoneService smartphoneServiceInstance)
			throws Exception {
		System.out.println(".......testAggiornamentoVersioneOSSmartphone inizio.............");

		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneOSSmartphone fallito: smartphone non inserito. ");

		// esecuzione query aggiornamento
		String vecchiaVersione = smartphoneInstance.getVersioneOS();
		smartphoneInstance.setVersioneOS("Android 2.15.2");
		smartphoneServiceInstance.aggiorna(smartphoneInstance);

		if (smartphoneInstance.getVersioneOS().equals(vecchiaVersione))
			throw new RuntimeException(
					"testAggiornamentoVersioneOSSmartphone FAILED: si e'verificato un errore durante la modifica della versione.");

		// reset tabella
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());

		System.out.println(".......testAggiornamentoVersioneOSSmartphone fine: PASSED.............");

	}

	private static void testAggiornamentoVersioneAppEDataAggiornamento(AppService appServiceInstance) throws Exception {

		System.out.println(".......testAggiornamentoVersioneAppEDataAggiornamento inizio.............");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneAppEDataAggiornamento FAILED: app non inserita! ");

		// esecuzione update
		String vecchiaVersione = appInstance.getVersione();
		Date vecchiaData = appInstance.getDataUltimoAggiornamento();
		appInstance.setVersione("6.13.0");
		appInstance.setDataUltimoAggiornamento(new Date());
		appServiceInstance.aggiorna(appInstance);

		if (appInstance.getVersione().equals(vecchiaVersione)
				|| appInstance.getDataUltimoAggiornamento().equals(vecchiaData))
			throw new RuntimeException(
					"testAggiornamentoVersioneAppEDataAggiornamento FAILED: update non eseguito correttamente.");

		// reset tabella
		appServiceInstance.rimuovi(appInstance.getId());

		System.out.println(".......testAggiornamentoVersioneAppEDataAggiornamento fine: PASSED.............");

	}

	private static void testInstallazioneApp(AppService appServiceInstance, SmartphoneService smartphoneServiceInstance)
			throws Exception {

		System.out.println(".......testInstallazioneApp inizio.............");
		
		// creo smartphone e lo inserisco
		Smartphone smartphoneInstance = new Smartphone("Samsung", "Galaxy a5", 700, "Android 2.15.1");
		smartphoneServiceInstance.inserisciNuovo(smartphoneInstance);

		// verifica corretto inserimento
		if (smartphoneInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneOSSmartphone fallito: smartphone non inserito. ");

		// creo app e la inserisco
		Date dataInstallazione = new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2022");
		Date dataAggiornamento = new SimpleDateFormat("dd-MM-yyyy").parse("19-05-2022");

		App appInstance = new App("Instagram", dataInstallazione, dataAggiornamento, "6.12.2");
		appServiceInstance.inserisciNuovo(appInstance);

		// verifica corretto inserimento
		if (appInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneAppEDataAggiornamento FAILED: app non inserita! ");

		//installazione della app nello smartphone
		smartphoneServiceInstance.aggiungiApp(smartphoneInstance, appInstance);
		
		//verifica corretta installazione ricaricando smartphone con strategia eager
		Smartphone smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(smartphoneInstance.getId());
		
		if(smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testInstallazioneApp FAILED: installazione non avvenuta correttamente.");
		
		//reset tabelle
		smartphoneServiceInstance.rimuoviTuttiGliSmartphoneDallaTabellaDiJoin();
		smartphoneServiceInstance.rimuovi(smartphoneInstance.getId());
		appServiceInstance.rimuovi(appInstance.getId());
		
		System.out.println(".......testInstallazioneApp fine: PASSED.............");
		
	}

}
