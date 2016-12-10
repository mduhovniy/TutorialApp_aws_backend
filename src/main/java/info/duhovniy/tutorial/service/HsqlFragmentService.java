package info.duhovniy.tutorial.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.duhovniy.tutorial.model.Fragment;
import info.duhovniy.tutorial.repository.FragmentRepository;

@Service
@Qualifier("HsqlFragmentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HsqlFragmentService implements FragmentService {

	private FragmentRepository fragmentRepository;
    private CounterService counterService;

    @Autowired
    public HsqlFragmentService(FragmentRepository fragmentRepository, CounterService counterService) {
        this.fragmentRepository = fragmentRepository;
        this.counterService = counterService;
    }

    @Override
	public Collection<Fragment> findAll() {
		counterService.increment("HsqlFragmentService.findAll");
		Collection<Fragment> fragments = fragmentRepository.findAll();
		return fragments;
	}

	@Override
	@Cacheable(value = "fragments", key = "#id")
	public Fragment findOne(Long id) {
		Fragment fragment = fragmentRepository.findOne(id);
		return fragment;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "fragments", key = "#result.id")
	public Fragment create(Fragment fragment) {
		if (fragment.getId() != null)
			return null;
		Fragment createdFragment = fragmentRepository.save(fragment);

		// illustrate Tx rollback
		// TODO: remove in production
		if (createdFragment.getId() == 4L)
			throw new RuntimeException("Roll me back...");

		return createdFragment;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "fragments", key = "#fragment.id")
	public Fragment update(Fragment fragment) {
		if (fragmentRepository.findOne(fragment.getId()) == null)
			return null;
		Fragment updatedFragment = fragmentRepository.save(fragment);
		return updatedFragment;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "fragments", key = "#id")
	public void delete(Long id) {
		fragmentRepository.delete(id);
	}

	@Override
	@CacheEvict(value = "fragments", allEntries = true)
	public void evictCache() {
	}
}
