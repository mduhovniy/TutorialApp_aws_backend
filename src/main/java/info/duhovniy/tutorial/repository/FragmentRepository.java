package info.duhovniy.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.duhovniy.tutorial.model.Fragment;

@Repository
public interface FragmentRepository extends JpaRepository<Fragment, Long> {

}
