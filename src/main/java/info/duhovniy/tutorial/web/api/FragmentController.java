package info.duhovniy.tutorial.web.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.duhovniy.tutorial.model.Fragment;
import info.duhovniy.tutorial.service.FragmentService;

@RestController
public class FragmentController {

	@Autowired
	@Qualifier("HsqlFragmentService")
	private FragmentService fragmentService;

	@RequestMapping(value = "/api/fragments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Fragment>> getFragments() {
		return new ResponseEntity<Collection<Fragment>>(fragmentService.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/fragments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fragment> getFragment(@PathVariable("id") Long id) {
		Fragment fragment = fragmentService.findOne(id);
		if (fragment == null)
			return new ResponseEntity<Fragment>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Fragment>(fragment, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/fragments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fragment> createFragment(@RequestBody Fragment fragment) {
		Fragment createdFragment = fragmentService.create(fragment);
		return new ResponseEntity<Fragment>(createdFragment, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/fragments", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fragment> updateFragment(@RequestBody Fragment fragment) {
		Fragment updatedFragment = fragmentService.update(fragment);
		if (updatedFragment == null)
			return new ResponseEntity<Fragment>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Fragment>(updatedFragment, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/fragments/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Fragment> deleteFragment(@PathVariable("id") Long id) {
		fragmentService.delete(id);
		return new ResponseEntity<Fragment>(HttpStatus.NO_CONTENT);
	}
}