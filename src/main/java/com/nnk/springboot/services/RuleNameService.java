package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Use Jpa
 * List<RuleName> findAll();
 * <p>
 * void save(RuleName ruleName);
 * <p>
 * Optional<RuleName> findById(Integer id);
 * <p>
 * void deleteById(Integer id);
 * <p>
 * void delete(RuleName ruleName);
 */
@Service
public interface RuleNameService {
  List<RuleName> findAll();

  void save(RuleName ruleName);

  Optional<RuleName> findById(Integer id);

  void deleteById(Integer id);

  void delete(RuleName ruleName);
}
