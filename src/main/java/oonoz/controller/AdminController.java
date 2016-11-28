package oonoz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import oonoz.domain.Player;
import oonoz.domain.Supplier;
import oonoz.dto.converter.PlayerDtoConverter;
import oonoz.dto.converter.SupplierDtoConverter;
import oonoz.dto.model.PlayerDto;
import oonoz.dto.model.SupplierDto;
import oonoz.exception.PlayerNotExistException;
import oonoz.exception.WrongInformationException;
import oonoz.service.PlayerService;
import oonoz.service.SupplierService;
import oonoz.util.FilteredSearch;
import oonoz.util.StringResponse;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	/** The player service. */
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private SupplierService supplierService;
	
	/** The player dto converter. */
	@Autowired
	private PlayerDtoConverter playerDtoConverter;
	
	@Autowired
	private SupplierDtoConverter supplierDtoConverter;
	
	@RequestMapping(value = "/filteredSearch", method = RequestMethod.POST)
    public ResponseEntity<Page<PlayerDto>> filteredSearch(@RequestBody FilteredSearch filteredSearch){
    	  
		try{
			return new ResponseEntity<>(playerService.filteredSearch(filteredSearch), HttpStatus.OK);
		}
		catch(WrongInformationException e){
			logger.error("The page number must be greater or equal than zero !",e);
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
    	 
    }
    
    @RequestMapping(value = "/updatePlayer", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@RequestBody PlayerDto playerDto){
    	    	    	    	
    	Player player=playerDtoConverter.convertToEntity(playerDto);
    	try{
    		playerService.updatePlayer(player);
    	}
    	catch(PlayerNotExistException e){
    		logger.error("The player does not exist !",e);
    		return new ResponseEntity<>("The supplier does not exist !", HttpStatus.BAD_REQUEST);
    	}
    	catch(WrongInformationException e){
    		logger.error("Information about player are not valid !",e);
    		return new ResponseEntity<>("Information about supplier are not valid !", HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<>("", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/updateSupplier", method = RequestMethod.PUT)
    public ResponseEntity<String> updateSupplier(@RequestBody SupplierDto supplierDto){
    	    	    	
    	Supplier supplier=supplierDtoConverter.convertToEntity(supplierDto);
    	try{
    		supplierService.updateSupplier(supplier);
    	}
    	catch(PlayerNotExistException e){
    		logger.error("The supplier does not exist !",e);
    		return new ResponseEntity<>("The supplier does not exist !", HttpStatus.BAD_REQUEST);
    	}
    	catch(WrongInformationException e){
    		logger.error("Information about supplier are not valid !",e);
    		return new ResponseEntity<>("Information about supplier are not valid !", HttpStatus.BAD_REQUEST);
    	}
    		
    	 return new ResponseEntity<>("", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestParam(value = "idPlayer", defaultValue = "") Long idPlayer) throws PlayerNotExistException{
    	
    	try{
    		playerService.deletePlayer(idPlayer);
    	}
    	catch(PlayerNotExistException e){
    		logger.error("The user does not exist !",e);
    		return new ResponseEntity<>("The user does not exist !", HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<>("", HttpStatus.OK);
    }

	/**
	 * Authenticate users.
	 *
	 * @param request
	 *            the request
	 * @return the response entity
	 */
	@RequestMapping(value = "/getSupplierRequest", method = RequestMethod.GET)
	public ResponseEntity<List<Supplier>> getSupplierRequest(HttpServletRequest request) {

		supplierService.getSupplierRequest();
		return new ResponseEntity<>(supplierService.getSupplierRequest(), HttpStatus.OK);
	}

	/**
	 * Authenticate users.
	 *
	 * @param request
	 *            the request
	 * @return the response entity
	 */
	@RequestMapping(value = "/refuseSupplierRequest", method=RequestMethod.DELETE)
	public ResponseEntity<StringResponse> refuseSupplierRequest(@RequestParam(value = "idPlayer", defaultValue = "") Long idPlayer){
//	public ResponseEntity<List<Supplier>> refuseSupplierRequest(HttpServletRequest request) {
		supplierService.refuseSupplierRequest(idPlayer);
		StringResponse response = new StringResponse();
		response.setResponse("La demande a été refusée");
		return  ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * 
	 * @param idPlayer
	 * @return
	 */
	@RequestMapping(value = "/acceptSupplierRequest", method=RequestMethod.POST)
	public ResponseEntity<StringResponse> acceptSupplierRequest(@RequestParam(value = "idPlayer", defaultValue = "") Long idPlayer){
		supplierService.acceptSupplierRequest(idPlayer);
		StringResponse response = new StringResponse();
		response.setResponse("La demande a été acceptée");
		return  ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
