package oonoz.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oonoz.domain.Supplier;
import oonoz.exception.PlayerAlreadyExistException;
import oonoz.exception.PlayerNotExistException;
import oonoz.exception.WrongInformationException;
import oonoz.manager.impl.SupplierManagerImpl;
import oonoz.util.CheckUserInformation;
import oonoz.util.MailService;

@Service
public class SupplierService {

	@Autowired
	private SupplierManagerImpl supplierManager;


	@Autowired
	private MailService mailService;

	@Autowired
	private CheckUserInformation checkUserInformation;

	/**
	 * Sign-up a new supplier.
	 * 
	 * @param player
	 *            Contains player's information
	 * @throws WrongInformationException
	 *             If one of the information about the player is wrong.
	 * @throws PlayerAlreadyExistException
	 *             If the player which is signing-up already exist.
	 * @throws MessagingException
	 */
	public void signUp(Supplier supplier)
			throws WrongInformationException, PlayerAlreadyExistException, MessagingException {

		checkUserInformation.checkUsername(supplier.getUsername());
		checkUserInformation.checkPassword(supplier.getPassword());
		checkUserInformation.checkMail(supplier.getMail());
		checkUserInformation.checkLastName(supplier.getLastName());
		checkUserInformation.checkFirstName(supplier.getFirstName());
		checkUserInformation.checkBirthDate(supplier.getBirthDate());

		if (supplier.getIsPrivateIndividual() != null && !supplier.getIsPrivateIndividual()) {
			checkUserInformation.checkSiretNumber(supplier.getSiretNumber());
			checkUserInformation.checkCompanyAddress(supplier.getCompanyAddress());
			checkUserInformation.checkCompanyName(supplier.getCompanyName());
		} else {
			supplier.setIsPrivateIndividual(true);
			supplier.setSiretNumber(null);
			supplier.setCompanyAddress(null);
			supplier.setCompanyName(null);
		}
		supplier.setIsValid(false);
		supplier.setIsActive(false);
		supplier.setIsSupplier(true);
		String hashPassword = checkUserInformation.hashPassword(supplier.getPassword());
		if (hashPassword != null) {
			supplier.setPassword(hashPassword);
			supplierManager.create(supplier);
			mailService.sendValidationMail(supplier);
		} else {
			throw new WrongInformationException("Password invalid");
		}

	}

	/**
	 * Update supplier.
	 *
	 * @param player
	 *            the player
	 * @throws WrongInformationException
	 *             the wrong information exception
	 * @throws PlayerNotExistException
	 */
	// TODO use spring security authentication principal
	public void updateSupplier(Supplier supplier) throws WrongInformationException, PlayerNotExistException {

		checkUserInformation.checkUsername(supplier.getUsername());
		// checkUserInformation.checkPassword(supplier.getPassword());
		checkUserInformation.checkMail(supplier.getMail());
		checkUserInformation.checkLastName(supplier.getLastName());
		checkUserInformation.checkFirstName(supplier.getFirstName());
		checkUserInformation.checkBirthDate(supplier.getBirthDate());

		Supplier newSupplier= (Supplier)supplierManager.findById(supplier.getIdPlayer());
		
		if (newSupplier != null) {
			newSupplier.setUsername(supplier.getUsername());
			newSupplier.setLastName(supplier.getLastName());
			newSupplier.setFirstName(supplier.getFirstName());
			newSupplier.setMail(supplier.getMail());
			newSupplier.setBirthDate(supplier.getBirthDate());
			newSupplier.setIsActive(supplier.getIsActive());
			newSupplier.setIsPrivateIndividual(supplier.getIsPrivateIndividual());
			newSupplier.setIsValid(supplier.getIsValid());
			newSupplier.setIsSupplier(supplier.getIsSupplier());

			if (newSupplier.getIsPrivateIndividual() != null && !newSupplier.getIsPrivateIndividual()) {
				checkUserInformation.checkSiretNumber(supplier.getSiretNumber());
				checkUserInformation.checkCompanyAddress(supplier.getCompanyAddress());
				checkUserInformation.checkCompanyName(supplier.getCompanyName());
				newSupplier.setCompanyAddress(supplier.getCompanyAddress());
				newSupplier.setCompanyName(supplier.getCompanyName());
				newSupplier.setSiretNumber(supplier.getSiretNumber());

			}

			supplierManager.update(newSupplier);
		} else {
			throw new PlayerNotExistException("The player does not exist !");
		}
	}

}
