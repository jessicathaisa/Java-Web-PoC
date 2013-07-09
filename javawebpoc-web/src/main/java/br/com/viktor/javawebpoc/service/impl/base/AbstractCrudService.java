package br.com.viktor.javawebpoc.service.impl.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.viktor.javawebpoc.entity.base.AbstractEntity;
import br.com.viktor.javawebpoc.exception.EntityExistsException;
import br.com.viktor.javawebpoc.exception.EntityNotExistException;
import br.com.viktor.javawebpoc.exception.InvalidArgumentException;
import br.com.viktor.javawebpoc.exception.NullArgumentException;
import br.com.viktor.javawebpoc.service.contract.base.AbstractCrudContract;

public abstract class AbstractCrudService<T extends AbstractEntity> implements
		AbstractCrudContract<T> {

	private JpaRepository<T, Long> repository;

	protected JpaRepository<T, Long> getRepository() {
		return repository;
	}

	public AbstractCrudService(JpaRepository<T, Long> repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void save(T entity) throws EntityExistsException,
			NullArgumentException, InvalidArgumentException {

		checkIfNull(entity);

		checkIfValid(entity);

		checkBussinessKey(entity);

		repository.save(entity);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void delete(T entity) throws EntityNotExistException {
		repository.delete(entity);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void update(T entity) throws EntityNotExistException {
		repository.save(entity);

	}

	@Transactional(readOnly = true)
	public List<T> list() {
		return repository.findAll();
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public T get(T entity) throws EntityNotExistException {
		return repository.findOne(entity.getId());
	}

	@Override
	public T find(long id) throws EntityNotExistException {
		T t = repository.findOne(id);
		if (t == null)
			throw new EntityNotExistException();
		return t;
	}

	@Override
	public void delete(Long id) throws EntityNotExistException {
		T t = repository.findOne(id);
		if (t == null)
			throw new EntityNotExistException();
		repository.delete(t);
	}

	protected abstract void checkIfValid(T entity) throws InvalidArgumentException;

	protected abstract void checkBussinessKey(T entity)
			throws EntityExistsException;
	
	private void checkIfNull(T entity) throws NullArgumentException {
		if (entity == null)
			throw new NullArgumentException("Entity Cannot be null.", "entity");
	}
}
