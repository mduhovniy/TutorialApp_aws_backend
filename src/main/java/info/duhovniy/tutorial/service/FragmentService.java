package info.duhovniy.tutorial.service;

import java.util.Collection;

import info.duhovniy.tutorial.model.Fragment;

public interface FragmentService {

	Collection<Fragment> findAll();

	Fragment findOne(Long id);

	Fragment create(Fragment fragment);

	Fragment update(Fragment fragment);

	void delete(Long id);

	void evictCache();
}
