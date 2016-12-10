package info.duhovniy.tutorial.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import info.duhovniy.tutorial.model.Fragment;

@Service
@Qualifier("DefaultFragmentService")
public class DefaultFragmentService implements FragmentService {

	private static Long nextId;
	private static Map<Long, Fragment> fragmentMap;

	private static Fragment save(Fragment fragment) {
		if (fragmentMap == null) {
			fragmentMap = new HashMap<Long, Fragment>();
			nextId = 1l;
		}
		// If Update...
		if (fragment.getId() != null) {
			if (fragmentMap.get(fragment.getId()) == null)
				return null;
			fragmentMap.remove(fragment.getId());
			fragmentMap.put(fragment.getId(), fragment);
			return fragment;
		}
		// If Create...
		fragment.setId(nextId);
		nextId++;
		fragmentMap.put(fragment.getId(), fragment);
		return fragment;
	}

	private boolean remove(Long id) {
		Fragment deletedFragment = fragmentMap.remove(id);
		if (deletedFragment == null)
			return false;
		else
			return true;
	}

	static {
		Fragment f1 = new Fragment();
		f1.setType("image");
		f1.setTitle("Lemon cheesecake");
		f1.setPreviewDescription("A cheesecake made of lemon");
		f1.setDetailDescription(
				"Cheesecake is a sweet dessert consisting of one or more layers. The main, and thickest layer, consists of a mixture of soft, fresh cheese (typically cream cheese or ricotta), eggs, and sugar; if there is a bottom layer it often consists of a crust or base made from crushed cookies (or digestive biscuits), graham crackers, pastry, or sponge cake. It may be baked or unbaked (usually refrigerated). Cheesecake is usually sweetened with sugar and may be flavored or topped with fruit, whipped cream, nuts, cookies, fruit sauce, and/or chocolate syrup. Cheesecake can be prepared in many flavors, such as strawberry, pumpkin, key lime, chocolate, Oreo, chestnut, or toffee.");
		f1.setImage(
				"https://raw.githubusercontent.com/filippella/Dagger-Rx-Database-MVP/master/cakes/lemoncheese_cake.jpg");
		save(f1);

		Fragment f2 = new Fragment();
		f2.setType("text");
		f2.setTitle("Victoria sponge");
		f2.setPreviewDescription("Sponge with jam");
		f2.setDetailDescription(
				"The Victoria sponge, also known as the Victoria sandwich or Victorian Cake, was named after Queen Victoria, who was known to enjoy a slice of the sponge cake with her afternoon tea. It is often referred to simply as 'sponge cake', though it contains additional fat. A typical Victoria sponge consists of raspberry jam and whipped double cream or vanilla cream. The jam and cream are sandwiched between two sponge cakes; the top of the cake is not iced or decorated apart from a dusting of icing sugar. A Victoria sponge is made using one of two methods. The traditional method involves creaming caster sugar with fat (usually butter), mixing thoroughly with beaten egg, then folding flour and raising agent into the mixture. The modern method, using an electric mixer or food processor, involves simply whisking all the ingredients together until creamy. Additionally, the modern method typically uses an extra raising agent, and some recipes call for an extra-soft butter or margarine. Both the traditional and modern methods are relatively quick and simple, producing consistent results, making this type of mixture one of the most popular for children and people in a hurry. This basic 'cake' mixture has been made into an endless variety of treats and puddings, including cupcakes, chocolate cake, Eve's pudding, and many others. \n Carrot cake closely resembles a quick bread in method of preparation (all the wet ingredients, such as the eggs and sugar, are mixed, all the dry ingredients are mixed, and the wet are then added to the dry) and final consistency (which is usually denser than a traditional cake and has a coarser crumb). Many carrot cake recipes include optional ingredients, such as kirsch, cinnamon, nuts, pineapple or raisins. The most common icing on carrot cake is icing sugar and lemon juice or icing sugar and kirsch (Europe) and an icing with icing sugar, butter and cream cheese (United States). As the cake is relatively moist, it can be conserved longer than many other types of cakes.");
		f2.setImage(
				"https://raw.githubusercontent.com/filippella/Dagger-Rx-Database-MVP/master/cakes/victoria_sponge.jpg");
		save(f2);
	}

	@Override
	public Collection<Fragment> findAll() {
		Collection<Fragment> fragments = fragmentMap.values();
		return fragments;
	}

	@Override
	@Cacheable(value = "fragments", key = "#id")
	public Fragment findOne(Long id) {
		Fragment fragment = fragmentMap.get(id);
		return fragment;
	}

	@Override
	@CachePut(value = "fragments", key = "#result.id")
	public Fragment create(Fragment fragment) {
		Fragment createdFragment = save(fragment);
		return createdFragment;
	}

	@Override
	@CachePut(value = "fragments", key = "#fragment.id")
	public Fragment update(Fragment fragment) {
		Fragment updatedFragment = save(fragment);
		return updatedFragment;
	}

	@Override
	@CacheEvict(value = "fragments", key = "#id")
	public void delete(Long id) {
		remove(id);
	}

	@Override
	@CacheEvict(value = "fragments", allEntries = true)
	public void evictCache() {
	}

}
