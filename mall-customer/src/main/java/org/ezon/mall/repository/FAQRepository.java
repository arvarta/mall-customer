package org.ezon.mall.repository;

import java.util.List;

import org.ezon.mall.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {

	List<FAQ> findByOrderByFaqIdDesc();

}
