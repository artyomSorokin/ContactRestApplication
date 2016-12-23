package sorokin.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sorokin.entity.ContactEntity;
import sorokin.service.ContactService;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/hello/contacts", params = {"nameFilter"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<ContactEntity>> getContacts(@RequestParam(value = "nameFilter", required = true, defaultValue = "") String filter) {

        List<ContactEntity> contacts = null;
        ResponseEntity<List<ContactEntity>> responseEntity= null;
        try {

            contacts = filterContactEntity(filter);

            if (contacts == null || contacts.size() == 0) {
                responseEntity =  new ResponseEntity<List<ContactEntity>>(Collections.EMPTY_LIST, HttpStatus.NOT_FOUND);
            }

            else if(filter == null || filter.equals("")){
                responseEntity = new ResponseEntity<List<ContactEntity>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST);
            }
            else{
                responseEntity = new ResponseEntity<List<ContactEntity>>(contacts, HttpStatus.OK);
            }
        }
        catch (Throwable e){
            responseEntity = new ResponseEntity<List<ContactEntity>>(Collections.EMPTY_LIST, HttpStatus.EXPECTATION_FAILED);
        }
        return responseEntity;
    }

    public List<ContactEntity> filterContactEntity(String filter) throws PropertyVetoException, SQLException, IOException {

        List<ContactEntity> contacts = contactService.queryContactEntity();

        final Pattern pattern = Pattern.compile(filter);

        for (Iterator<ContactEntity> it = contacts.iterator(); it.hasNext(); ) {

            final ContactEntity contact = it.next();

            final Matcher matcher = pattern.matcher(contact.getName());

            if (matcher.matches()) {

                it.remove();
            }
        }
        return contacts;
    }

}
